package server;

import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.HashMap;

/**
 * A class representing a thread that accepts TCP connections from clients.
 */
public class ServerAccepter implements Runnable {
	private ServerSocket lobbySocket;
	private HashMap<Integer, User> users;
	private ServerGUI gui;

	/**
	 * Creates a new ServerAccepter object.
	 * 
	 * @param ls
	 *            The server socket.
	 * @param u
	 *            The HashMap of users connected to the server.
	 * @param g
	 *            The GUI for the server.
	 */
	public ServerAccepter(ServerSocket ls, HashMap<Integer, User> u, ServerGUI g) {
		lobbySocket = ls;
		users = u;
		gui = g;
	}

	/**
	 * The run method for the the thread. Repeatedly waits for connections from clients then sets up TCPReceiver and TCPSender threads for them, and adds them to the user-list. Runs until interrupted or the server is closed.
	 */
	public void run() {
		int i = 0;
		while (true) {
			Socket connectionSocket;
			try {
				connectionSocket = lobbySocket.accept();
				BufferedReader in = new BufferedReader(new InputStreamReader(connectionSocket.getInputStream()));
				DataOutputStream out = new DataOutputStream(connectionSocket.getOutputStream());
				gui.addMessage("Accepted new connection, id: " + i);
				users.put(i, new User(i, connectionSocket.getInetAddress()));
				users.get(i).sendTCPMsg("ID|" + i);

				TCPSender ts = new TCPSender(out, users, gui, i);
				Thread tsThread = new Thread(ts);
				tsThread.start();
				gui.addMessage("Sender thread started for client: " + i);

				TCPReceiver tr = new TCPReceiver(in, users, i, gui);
				Thread trThread = new Thread(tr);
				trThread.start();
				gui.addMessage("Receiver thread started for client: " + i);

				gui.addMessage("Done with setting up id: " + i);
				i++;
			} catch (IOException e) {
				System.err.println("Error: setting up connection from new client.");
				e.printStackTrace();
				break;
			}
		}
	}

}
