package client;

import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.DatagramSocket;
import java.net.Socket;
import java.net.SocketException;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingQueue;

/**
 * A static class holding methods and variables for connecting to a server.
 */
public class Network {
	private static Socket socket;
	private static BufferedReader in;
	private static DataOutputStream out;
	private static DatagramSocket gameSocket;
	private static BlockingQueue<String> queue = new LinkedBlockingQueue<String>();
	private static Lobby lobby;
	private static String serveraddress;
	private static NetworkGameSend ngs;
	private static NetworkGameReceive ngr;

	/**
	 * Makes a (TCP) connection to a server. Starts two threads - one to receive and process messages from the server, one to send messages to the server.
	 * 
	 * @param sa
	 *            The address of the server to connect to.
	 * @param name
	 *            The name of the user to send to the server.
	 * @param l
	 *            The lobby object, once connected to the given server.
	 * @return Returns whether or not a connection to the server was successfully made.
	 */
	public static boolean connect(String sa, String name, Lobby l) {
		lobby = l;
		serveraddress = sa;
		try {
			System.out.println("Trying to setup socket");
			socket = new Socket(serveraddress, 4432);
			System.out.println("Socket setup");
			in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
			out = new DataOutputStream(socket.getOutputStream());

			sendMessage("NAME|" + name);
			System.out.println("Put name on message queue");

			NetworkListener nl = new NetworkListener(in, lobby);
			Thread nlThread = new Thread(nl);
			nlThread.start();

			NetworkSender ns = new NetworkSender(out, queue);
			Thread nsThread = new Thread(ns);
			nsThread.start();

			System.out.println("Done");

		} catch (IOException e) {
			System.err.println("Error: unable to setup socket or input/output streams to server");
			return false;
		}

		return true;
	}

	/**
	 * To be run once a networked game has started. Connects to another client (the opponent) via UDP and starts two threads to keep the games in-sync.
	 * 
	 * @param opponentAddress
	 * @return
	 */
	public static boolean connectGame(String opponentAddress) {
		try {
			if (lobby.getGame().getID() == 0) {
				gameSocket = new DatagramSocket(4434);
			} else {
				gameSocket = new DatagramSocket(4435);
			}

			ngs = new NetworkGameSend(lobby.getGame(), gameSocket, opponentAddress, lobby.getID());
			Thread ngsThread = new Thread(ngs);
			ngsThread.start();
			System.out.println("NetworkGameSend Thread started");

			ngr = new NetworkGameReceive(lobby.getGame(), gameSocket);
			Thread ngrThread = new Thread(ngr);
			ngrThread.start();
			System.out.println("NetworkGameReceive Thread started");

		} catch (SocketException e) {
			System.err.println("Error: failed to setup DatagramSocket");
			e.printStackTrace();
			return false;
		}
		return true;
	}

	/**
	 * Sends a string message via TCP to the server.
	 * 
	 * @param m
	 *            The string message to send to the server.
	 */
	public static void sendMessage(String m) {
		queue.add(m);
		queue.add("\n");
	}

	public static void endGame() {
		ngs.stopRunning();
		ngr.stopRunning();
		gameSocket.close();
	}

}
