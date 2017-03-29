package server;

import java.io.IOException;
import java.net.InetAddress;
import java.net.ServerSocket;
import java.net.UnknownHostException;
import java.util.HashMap;

/**
 * A class that runs a static server that enables clients to connect to a lobby and play games of BrickBreak. Accepts TCP connections from clients on port 4432.
 */
public class Server {
	private static ServerSocket lobbySocket;
	private final static HashMap<Integer, User> users = new HashMap<Integer, User>(); // List of connected clients
	private static final int port = 4432;
	private static Thread accepterThread;
	private static ServerGUI gui;

	/**
	 * Launches the GUI for the server.
	 * 
	 * @param args
	 *            Arguments passed to the program - these are ignored.
	 */
	public static void main(String args[]) {
		System.out.println("Server starting");
		try {
			gui = new ServerGUI(InetAddress.getLocalHost().toString(), "" + port);
			gui.launch();
			System.out.println("GUI launched");
		} catch (UnknownHostException e) {
			System.err.println("Error: couldn't get local host address");
			e.printStackTrace();
		}
	}

	/**
	 * Initialises the socket and starts a thread for the server to start accepting connections from clients.
	 * 
	 * @return Returns boolean - whether or not the socket was successfully setup.
	 */
	public static boolean startServer() {
		try {
			lobbySocket = new ServerSocket(port);
		} catch (IOException e) {
			System.err.println("Error: Failed to setup ServerSocket on port " + port + ".");
			gui.addMessage("Failed to setup ServerSocket");
			gui.addMessage("Is the port in use?");
			return false;
		}
		gui.addMessage("ServerSocket (TCP) setup");
		ServerAccepter accepter = new ServerAccepter(lobbySocket, users, gui);
		accepterThread = new Thread(accepter);
		accepterThread.start();
		gui.addMessage("Now accepting connections from clients");

		return true;
	}

	/**
	 * Interrupts the thread that accepts connections from clients and closes the server socket.
	 */
	public static void stopServer() {
		accepterThread.interrupt();
		try {
			lobbySocket.close();
		} catch (IOException e) {
			System.err.println("Error: failed to close lobbySocket");
			e.printStackTrace();
			System.exit(1);
		}
	}

}