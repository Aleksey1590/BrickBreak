package server;

import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;

@SuppressWarnings("serial")
/**
 * A class representing a basic GUI to start and stop a BrickBreak server.
 */
public class ServerGUI extends JFrame {
	private JButton start;
	private JButton stop;
	private JLabel host;
	private JLabel port;
	private JTextArea messages;
	private GridLayout layout;

	/**
	 * Creates a new ServerGUI.
	 * 
	 * @param h
	 *            The hostname (the network address that the server is running on).
	 * @param p
	 *            The port the server is running on.
	 */
	public ServerGUI(String h, String p) {
		super("BrickBreak Server");
		setSize(300, 400);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		layout = new GridLayout(3, 1);
		setLayout(layout);

		JPanel info = new JPanel();
		info.setLayout(new GridLayout(1, 2));

		host = new JLabel("Local IP: " + h.substring(h.indexOf("/") + 1));
		info.add(host);

		port = new JLabel("Port: " + p);
		info.add(port);

		add(info);

		JPanel buttons = new JPanel();
		buttons.setLayout(new GridLayout(1, 2));

		start = new JButton("Start");
		start.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent actionEvent) {
				if (Server.startServer()) {
					start.setEnabled(false);
					stop.setEnabled(true);
				}

			}
		});
		buttons.add(start);

		stop = new JButton("Stop");
		stop.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent actionEvent) {
				Server.stopServer();
				start.setEnabled(true);
				stop.setEnabled(false);
			}
		});
		stop.setEnabled(false);
		buttons.add(stop);

		add(buttons);

		messages = new JTextArea();
		messages.setRows(10);
		messages.setColumns(10);
		JScrollPane sp = new JScrollPane(messages);
		add(sp);
	}

	/**
	 * Open the GUI - make it visible to the user.
	 */
	public void launch() {
		setVisible(true);
	}

	/**
	 * Add a string message to the output text-area of the GUI.
	 * 
	 * @param m
	 *            The message to add to the GUI.
	 */
	public void addMessage(String m) {
		messages.append(m + "\n");
		repaint();
	}

}
