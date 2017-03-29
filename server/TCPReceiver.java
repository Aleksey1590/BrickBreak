package server;

import java.io.BufferedReader;
import java.io.IOException;
import java.util.HashMap;

/**
 * A class representing a thread that accepts and processes TCP messages from a particular client.
 */
public class TCPReceiver implements Runnable {
	private BufferedReader in;
	private static HashMap<Integer, User> users;
	private int id;
	private ServerGUI gui;
	private boolean running;

	/**
	 * Creates a new TCPReceiver object.
	 * 
	 * @param input
	 *            The input from the client.
	 * @param u
	 *            The HashMap of users connected to the server.
	 * @param i
	 *            The integer ID of the specific user this object is for.
	 * @param g
	 *            The GUI for the server.
	 * @param sT
	 *            The HashMap of user IDs and their associated sender threads on the server.
	 */
	public TCPReceiver(BufferedReader input, HashMap<Integer, User> u, int i, ServerGUI g) {
		in = input;
		users = u;
		id = i;
		gui = g;
		running = true;
	}

	/**
	 * Process a string message from a client.
	 * 
	 * @param msg
	 *            The message received from the client.
	 */
	private void processMessage(String msg) {
		String[] parts = msg.split("\\|");
		switch (parts[0]) {
		case "NAME":
			users.get(id).setName(parts[1]);
			gui.addMessage("Name received: " + parts[1] + ", for id: " + id);
			String userlist = "USERLIST|";
			for (int j : users.keySet()) {
				userlist += users.get(j).getName();
				userlist += "(";
				userlist += j;
				userlist += ")";
				userlist += "|";
			}
			for (User u : users.values()) {
				u.sendTCPMsg(userlist);
			}
			break;
		case "CHAT":
			for (User u : users.values()) {
				u.sendTCPMsg(msg);
			}
			break;
		case "CHALLENGE":
			int challenger = id;
			int challenged = Integer.parseInt(parts[1]);
			if (!(users.get(challenged).getChallengerID() == challenger) && !users.get(challenged).isReady()) {
				users.get(id).sendTCPMsg("CHAT|That user is currently busy.");
				users.get(id).sendTCPMsg("UNCHALLENGE|");
			} else {
				users.get(id).setReady(false);
				if (users.get(challenged).getChallengerID() == challenger) {
					users.get(challenger).setOpponentID(challenged);
					users.get(challenged).setOpponentID(challenger);
					users.get(challenger).sendTCPMsg("START|0|" + users.get(challenged).getHost().toString().substring(1));
					users.get(challenged).sendTCPMsg("START|1|" + users.get(challenger).getHost().toString().substring(1));
				} else {
					users.get(challenged).setChallengerID(challenger);
					users.get(challenger).setChallengerID(challenged);
					String msgtosend = "CHAT|You have been challenged by ";
					msgtosend += users.get(challenger).getName();
					msgtosend += "(" + challenger + ")";
					users.get(challenged).sendTCPMsg(msgtosend);
					msgtosend = "CHAT|Click their name and hit start to play!";
					users.get(challenged).sendTCPMsg(msgtosend);

				}
			}

			break;
		case "BRICKSX":
			int receiver = users.get(id).getOpponentID();
			users.get(receiver).sendTCPMsg(msg);
			break;
		case "BRICKSH":
			int receiver2 = users.get(id).getOpponentID();
			users.get(receiver2).sendTCPMsg(msg);
			break;
		case "BONUSES":
			int receiver1 = users.get(id).getOpponentID();
			users.get(receiver1).sendTCPMsg(msg);
			break;
		case "DISCONNECT":
			users.remove(id);
			String userlist1 = "USERLIST|";
			for (int j : users.keySet()) {
				userlist1 += users.get(j).getName();
				userlist1 += "(";
				userlist1 += j;
				userlist1 += ")";
				userlist1 += "|";
			}
			for (User u : users.values()) {
				u.sendTCPMsg(userlist1);
			}
			running = false;
			break;
		case "GAMEOVER":
			users.get(id).setChallengerID(-1);
			users.get(id).setOpponentID(-1);
			users.get(id).setReady(true);
			users.get(id).setPlaying(false);
			break;
		}
	}

	/**
	 * The run method for the thread. Repeatedly waits to read a string message from the connected client.
	 */
	public void run() {
		while (running) {
			try {
				String message = in.readLine();
				gui.addMessage("Received message from id: " + id + ", " + message);
				processMessage(message);
			} catch (IOException e) {
				System.err.println("Error: reading message from client: " + id);
				e.printStackTrace();
				users.remove(id);
				String userlist1 = "USERLIST|";
				for (int j : users.keySet()) {
					userlist1 += users.get(j).getName();
					userlist1 += "(";
					userlist1 += j;
					userlist1 += ")";
					userlist1 += "|";
				}
				for (User u : users.values()) {
					u.sendTCPMsg(userlist1);
				}
				running = false;
			}

		}

	}

}
