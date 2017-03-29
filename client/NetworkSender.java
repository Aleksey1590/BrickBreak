package client;

import java.io.DataOutputStream;
import java.io.IOException;
import java.util.concurrent.BlockingQueue;

/**
 * A class to represent a thread that waits for then sends TCP messages to the server.
 */
public class NetworkSender implements Runnable {
	private BlockingQueue<String> queue;
	private DataOutputStream out;

	/**
	 * Creates a new NetworkSender object.
	 * 
	 * @param o
	 *            The DataOutputStream to the server.
	 * @param q
	 *            The queue of messages to send to the server.
	 */
	public NetworkSender(DataOutputStream o, BlockingQueue<String> q) {
		queue = q;
		out = o;
	}

	/**
	 * The run method for the thread. Waits for messages to appear on the message queue, then sends these to the server via TCP.
	 */
	public void run() {
		String msg;
		while (true) {
			try {
				msg = queue.take();
				System.out.println("Found message to send: " + msg);
				out.writeBytes(msg);
				System.out.println("Message sent");
			} catch (InterruptedException e) {
				// do nothing
			} catch (IOException e) {
				System.err.println("Error: unable to send message to client");
				e.printStackTrace();
				break;
			}

		}

	}

}
