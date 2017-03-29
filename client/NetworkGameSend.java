package client;

import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.net.UnknownHostException;

/**
 * A class to represent a thread that sends data from the game object to another client to keep them in-sync.
 */
public class NetworkGameSend implements Runnable {
	private Game game;
	private DatagramSocket socket;
	private InetAddress host;
	private int id;
	private int opponentPort;
	private boolean running = true;

	/**
	 * Creates a new NetworkGameSend object.
	 * 
	 * @param g
	 *            The game to read data from.
	 * @param s
	 *            The socket to send packets on.
	 * @param h
	 *            The network address to send packets to.
	 * @param i
	 *            My ID (assigned by the server).
	 */
	public NetworkGameSend(Game g, DatagramSocket s, String h, int i) {
		game = g;
		socket = s;
		try {
			host = InetAddress.getByName(h);
		} catch (UnknownHostException e) {
			System.err.println("Error: unable to recognise opponent address");
			e.printStackTrace();
		}
		id = i;
		if (game.getID() == 0) {
			opponentPort = 4435;
		} else {
			opponentPort = 4434;
		}
	}

	/**
	 * The run method for the thread. Every 17ms reads the x coordinate of this user's paddle and sends this to the opponent client. If this user is the 'master' (game id = 0), also sends the coordinates and velocities of the ball, and the scores of both players.
	 */
	public void run() {
		byte[] data;
		DatagramPacket packet;
		while (running) {
			try {
				Thread.sleep(17);
			} catch (InterruptedException e) {
				System.err.println("Error: thread unable to sleep");
				e.printStackTrace();
			}
			Player me = game.getPlayer(game.getID());
			data = new byte[5];
			data[0] = 1;
			data[1] = (byte) id;
			data[2] = (byte) game.getID();
			data[3] = (byte) me.getX();
			data[4] = (byte) (me.getX() >>> 8);
			packet = new DatagramPacket(data, data.length, host, opponentPort);
			try {
				socket.send(packet);
			} catch (IOException e) {
				System.err.println("Error: unable to send DatagramPacket");
				e.printStackTrace();
			}
			if (game.getID() == 0) {
				data = new byte[8];
				data[0] = 2;
				data[1] = (byte) id;
				data[2] = (byte) game.getBall(0).getX();
				data[3] = (byte) (game.getBall(0).getX() >>> 8);
				data[4] = (byte) game.getBall(0).getY();
				data[5] = (byte) (game.getBall(0).getY() >>> 8);
				data[6] = (byte) game.getBall(0).getXVector();
				data[7] = (byte) game.getBall(0).getYVector();
				packet = new DatagramPacket(data, data.length, host, opponentPort);
				try {
					socket.send(packet);
				} catch (IOException e) {
					System.err.println("Error: unable to send ball DatagramPacket");
					e.printStackTrace();
				}

				data = new byte[6];
				data[0] = 3;
				data[1] = (byte) id;
				data[2] = (byte) game.getPlayer(0).getScore();
				data[3] = (byte) (game.getPlayer(0).getScore() >>> 8);
				data[4] = (byte) game.getPlayer(1).getScore();
				data[5] = (byte) (game.getPlayer(1).getScore() >>> 8);
				packet = new DatagramPacket(data, data.length, host, opponentPort);
				try {
					socket.send(packet);
				} catch (IOException e) {
					System.err.println("Error: unable to send score DatagramPacket");
					e.printStackTrace();
				}
			}
		}
	}

	/**
	 * Stops the infinite loop of the thread.
	 */
	public void stopRunning() {
		running = false;
	}
}