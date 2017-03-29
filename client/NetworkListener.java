package client;

import java.io.BufferedReader;
import java.io.IOException;

/**
 * A class to represent a thread that receives and processes TCP messages from the server.
 */
public class NetworkListener implements Runnable {
	private BufferedReader in;
	private Lobby lobby;

	/**
	 * Creates a new NetworkListener object.
	 * 
	 * @param i
	 *            The input from the server.
	 * @param l
	 *            The lobby while connected to the server.
	 */
	public NetworkListener(BufferedReader i, Lobby l) {
		in = i;
		lobby = l;
	}

	/**
	 * Processes a string message.
	 * 
	 * @param msg
	 *            The string message received from the server.
	 */
	private void processMessage(String msg) {
		String[] parts = msg.split("\\|");
		switch (parts[0]) {
		case "ID":
			lobby.setID(Integer.parseInt(parts[1]));
			break;
		case "USERLIST":
			lobby.clearUser();
			for (int i = 1; i < parts.length; i++) {
				lobby.addUser(parts[i]);
			}
			break;
		case "CHAT":
			lobby.addMessage(parts[1]);
			break;
		case "START":
			lobby.startGame();
			lobby.getGame().setID(Integer.parseInt(parts[1]));
			lobby.getGame().sendBonuses();
			Network.connectGame(parts[2]);
			lobby.getGame().startGame();
			break;
		case "BRICKSH":
			lobby.getGame().getBrick(Integer.parseInt(parts[1])).setHealth(1);
			break;
		case "BRICKSX":
			lobby.getGame().getBrick(Integer.parseInt(parts[1])).setHealth(0);
			;
			break;
		case "BONUSES":
			for (int i = 1; i < parts.length - 1; i += 2) {
				lobby.getGame().getBrick(Integer.parseInt(parts[i])).setBonusID(Integer.parseInt(parts[i + 1]));
			}
			break;
		case "UNCHALLENGE":
			lobby.unchallenge();
			break;
		}
	}

	/**
	 * The run method for the thread. Waits to receive a message from the server, then sends it to be processed.
	 */
	public void run() {
		while (true) {
			try {
				String message = in.readLine();
				System.out.println("Received message from server: " + message);
				processMessage(message);
			} catch (IOException e) {
				System.err.println("Error: reading message from server");
				e.printStackTrace();
			}
		}
	}

}
