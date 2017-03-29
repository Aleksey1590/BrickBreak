package server;

import java.io.DataOutputStream;
import java.io.IOException;
import java.util.HashMap;
import java.util.concurrent.BlockingQueue;

/**
 * A class representing a thread that waits for then sends TCP messages to a particular client.
 */
public class TCPSender implements Runnable {
	private HashMap<Integer, User> users;
	private BlockingQueue<String> queue;
	private DataOutputStream out;
	private ServerGUI gui;
	private int id;
	private boolean running = true;

	/**
	 * Creates a new TCPSender object.
	 * 
	 * @param o
	 *            The DataOutputStream to the connected client.
	 * @param u
	 *            The list of users connected to the server.
	 * @param g
	 *            The GUI for the server.
	 * @param i
	 *            The ID of the user this thread is sending to.
	 */
	public TCPSender(DataOutputStream o, HashMap<Integer, User> u, ServerGUI g, int i) {
		users = u;
		queue = u.get(i).getQueue();
		out = o;
		gui = g;
		id = i;
	}

	/**
	 * The run method for the thread. Repeatedly waits for messages to appear on the message queue then sends these to the client via the DataOutputStream.
	 */
	public void run() {
		while (running) {
			try {
				String msg = queue.take();
				out.writeBytes(msg);
				gui.addMessage("Sent message: " + msg);
			} catch (InterruptedException e) {
				// do nothing
			} catch (IOException e) {
				System.err.println("Error: unable to send message to client");
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
