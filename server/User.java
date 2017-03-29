package server;

import java.net.DatagramPacket;
import java.net.InetAddress;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingQueue;

/**
 * A class representing a user connected to the server.
 */
public class User {
	private int id;
	private String name = "DEFAULT";
	private int opponentID = -1;
	private int challengerID = -1;
	private boolean isReady = true;
	private boolean isPlaying = false;
	private InetAddress host;
	private BlockingQueue<String> queue;
	private int port;

	/**
	 * Creates a new User object.
	 * 
	 * @param i
	 *            The integer ID of the user.
	 * @param h
	 *            The network address of the user.
	 */
	public User(int i, InetAddress h) {
		id = i;
		host = h;
		queue = new LinkedBlockingQueue<String>();
	}

	// get methods
	/**
	 * Returns this user's ID.
	 * 
	 * @return This user's integer ID.
	 */
	public int getID() {
		return id;
	}

	/**
	 * Returns this user's name.
	 * 
	 * @return This user's name.
	 */
	public String getName() {
		return name;
	}

	/**
	 * Returns the ID of this user's current opponent.
	 * 
	 * @return The ID of this user's current opponent.
	 */
	public int getOpponentID() {
		return opponentID;
	}

	/**
	 * Returns the ID of the user who has challenged this user.
	 * 
	 * @return The ID of the user who has challenged this user.
	 */
	public int getChallengerID() {
		return challengerID;
	}

	/**
	 * Returns whether or not the user is currently ready to play a game.
	 * 
	 * @return Boolean - whether or not the user is currently ready to play a game.
	 */
	public boolean isReady() {
		return isReady;
	}

	/**
	 * Returns whether or not the user is currently playing a game.
	 * 
	 * @return Boolean - whether or not the user is currently playing a game.
	 */
	public boolean isPlaying() {
		return isPlaying;
	}

	/**
	 * Returns the network address of this user.
	 * 
	 * @return The network address of this user.
	 */
	public InetAddress getHost() {
		return host;
	}

	/**
	 * Returns the queue of messages to send to this user.
	 * 
	 * @return The queue of messages to send to this user.
	 */
	public BlockingQueue<String> getQueue() {
		return queue;
	}

	/**
	 * Returns the port this user is connected on.
	 * 
	 * @return The port this user is connected on.
	 */
	public int getPort() {
		return port;
	}

	// set methods
	/**
	 * Sets a new ID for this user.
	 * 
	 * @param i
	 *            The new ID for this user.
	 */
	public void setID(int i) {
		id = i;
	}

	/**
	 * Sets a new name for this user.
	 * 
	 * @param s
	 *            The new name for this user.
	 */
	public void setName(String s) {
		name = s;
	}

	/**
	 * Sets the ID of the user this user is playing against.
	 * 
	 * @param i
	 *            The ID of this user's opponent.
	 */
	public void setOpponentID(int i) {
		opponentID = i;
	}

	/**
	 * Sets the ID of the user that has challenged this user.
	 * 
	 * @param i
	 *            The ID of this user's challenger.
	 */
	public void setChallengerID(int i) {
		challengerID = i;
	}

	/**
	 * Sets whether or not this user is ready to play a game.
	 * 
	 * @param b
	 *            Whether or not this user is ready.
	 */
	public void setReady(boolean b) {
		isReady = b;
	}

	/**
	 * Sets whether or not this user is in-game.
	 * 
	 * @param b
	 *            Whether or not this user is in-game.
	 */
	public void setPlaying(boolean b) {
		isPlaying = b;
	}

	/**
	 * Sets a new network address for this user.
	 * 
	 * @param h
	 *            The new network address for this user.
	 */
	public void setHost(InetAddress h) {
		host = h;
	}

	/**
	 * Assigns a new message queue to this user.
	 * 
	 * @param q
	 *            The new message queue.
	 */
	public void setQueue(BlockingQueue<String> q) {
		queue = q;
	}

	/**
	 * Sets this user's port.
	 * 
	 * @param p
	 *            The port.
	 */
	public void setPort(int p) {
		port = p;
	}

	// extra methods
	/**
	 * Adds a message to this user's message queue.
	 * 
	 * @param msg
	 *            The message to send to this user.
	 */
	public void sendTCPMsg(String msg) {
		queue.add(msg);
		queue.add("\n");
	}

	/**
	 * Makes a DatagramPacket using this user's network address and port, given a string message.
	 * 
	 * @param m
	 *            The string message to turn into a DatagramPacket.
	 * @return The DatagramPacket made from the string message for this user.
	 */
	public DatagramPacket makePacket(String m) {
		byte[] data = m.getBytes();
		DatagramPacket packet = new DatagramPacket(data, data.length, host, port);
		return packet;
	}

	/**
	 * Makes a DatagramPacket using this user's network address and port, given a byte array.
	 * 
	 * @param data
	 *            The byte array to send to this user.
	 * @return The DatagramPacket made from the byte array for this user.
	 */
	public DatagramPacket makePacketFromData(byte[] data) {
		DatagramPacket packet = new DatagramPacket(data, data.length, host, port);
		return packet;
	}

}
