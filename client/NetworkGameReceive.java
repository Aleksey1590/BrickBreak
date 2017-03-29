package client;

import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;

/**
 * A class to represent a thread that receives and processes packets from another client regarding the state of a shared networked game.
 */
public class NetworkGameReceive implements Runnable {
	private Game game;
	private DatagramSocket socket;
	private final static int PACKETSIZE = 10;
	private boolean running = true;

	/**
	 * Creates a new NetworkGameReceive object.
	 * 
	 * @param g
	 *            The game to keep in-sync.
	 * @param s
	 *            The socket to receive packets on.
	 */
	public NetworkGameReceive(Game g, DatagramSocket s) {
		game = g;
		socket = s;
	}

	/**
	 * Processes a DatagramPacket - updates the game state.
	 * 
	 * @param packet
	 *            The received packet to process.
	 */
	private void processPacket(DatagramPacket packet) {
		byte[] data = packet.getData();
		switch (data[0]) {
		case 1:
			int id = data[2];
			int x = ((data[4] & 0xff) << 8) | (data[3] & 0xff);
			game.getPlayer(id).setX(x);
			break;
		case 2:
			int bx = ((data[3] & 0xff) << 8) | (data[2] & 0xff);
			int by = ((data[5] & 0xff) << 8) | (data[4] & 0xff);
			int xVector = data[6];
			int yVector = data[7];
			game.getBall(0).setX(bx);
			game.getBall(0).setY(by);
			game.getBall(0).setXVector(xVector);
			game.getBall(0).setYVector(yVector);
			break;
		case 3:
			int s1 = ((data[3] & 0xff) << 8) | (data[2] & 0xff);
			int s2 = ((data[5] & 0xff) << 8) | (data[4] & 0xff);
			game.getPlayer(0).setScore(s1);
			game.getPlayer(1).setScore(s2);
			break;
		}
	}

	/**
	 * The run method for the thread. Repeatedly receives packets on the socket then sends them to be processed.
	 */
	public void run() {
		while (running) {
			DatagramPacket packet = new DatagramPacket(new byte[PACKETSIZE], PACKETSIZE);
			try {
				socket.receive(packet);
			} catch (IOException e) {
				System.err.println("Error: failed to receive DatagramPacket");
				e.printStackTrace();
			}
			processPacket(packet);
		}
	}

	/**
	 * Stops the infinite loop of the thread.
	 */
	public void stopRunning() {
		running = false;
	}
}