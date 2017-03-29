package client;

import java.awt.Color;
import java.awt.Font;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Image;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.DefaultListModel;
import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JLayeredPane;
import javax.swing.JList;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.ListSelectionModel;
import javax.swing.SwingConstants;
import javax.swing.border.EmptyBorder;

/**
 * A class for the multiplayer networked lobby within the user interface.
 */
@SuppressWarnings("serial")
public class Lobby extends JPanel {
	private JTextArea chatroom;
	private JList<String> list;
	private DefaultListModel<String> listModel;
	private JLabel myname;
	private JLabel myid;
	private int id;
	private Game game;
	private final ImageIcon LobbyImage = new ImageIcon(getClass().getResource("/client/resource/Lobby.png"));
	private Button start;

	/**
	 * Create the lobby panel.
	 */
	public Lobby(String n) {

		GridBagLayout gridBagLayout = new GridBagLayout();
		gridBagLayout.columnWidths = new int[] { 0, 0, 0, 0 };
		gridBagLayout.rowHeights = new int[] { 0, 0, 0, 0 };
		gridBagLayout.columnWeights = new double[] { 0.0, 1.0, 0.0, Double.MIN_VALUE };
		gridBagLayout.rowWeights = new double[] { 0.0, 1.0, 0.0, Double.MIN_VALUE };
		setLayout(gridBagLayout);

		JLabel lblNewLabel = new JLabel(LobbyImage);
		lblNewLabel.setFont(new Font("Luminari", Font.BOLD, 35));
		lblNewLabel.setForeground(new Color(255, 237, 175));
		GridBagConstraints gbc_lblNewLabel = new GridBagConstraints();
		gbc_lblNewLabel.insets = new Insets(0, 0, 5, 5);
		gbc_lblNewLabel.gridx = 1;
		gbc_lblNewLabel.gridy = 0;
		add(lblNewLabel, gbc_lblNewLabel);

		JPanel panel = new JPanel();
		panel.setBackground(new Color(0, 0, 0, 100));
		panel.setBorder(new EmptyBorder(50, 50, 50, 50));
		GridBagConstraints gbc_panel = new GridBagConstraints();
		gbc_panel.insets = new Insets(0, 0, 5, 5);
		gbc_panel.fill = GridBagConstraints.BOTH;
		gbc_panel.gridx = 1;
		gbc_panel.gridy = 1;
		add(panel, gbc_panel);
		GridBagLayout gbl_panel = new GridBagLayout();
		gbl_panel.columnWidths = new int[] { 0, 0, 0, 0 };
		gbl_panel.rowHeights = new int[] { 0, 0, 0, 0, 0 };
		gbl_panel.columnWeights = new double[] { 1.0, 0.0, 1.0, Double.MIN_VALUE };
		gbl_panel.rowWeights = new double[] { 1.0, 1.0, 1.0, 1.0, Double.MIN_VALUE };
		panel.setLayout(gbl_panel);

		myname = new JLabel("Name:" + n);
		myname.setFont(new Font("Marker Felt", Font.BOLD, 20));
		myname.setForeground(new Color(225, 225, 225, 180));
		GridBagConstraints gbc_lblNewLabel_3 = new GridBagConstraints();
		gbc_lblNewLabel_3.insets = new Insets(0, 0, 5, 5);
		gbc_lblNewLabel_3.gridx = 0;
		gbc_lblNewLabel_3.gridy = 0;
		panel.add(myname, gbc_lblNewLabel_3);

		myid = new JLabel("ID: ");
		myid.setFont(new Font("Marker Felt", Font.BOLD, 25));
		myid.setForeground(new Color(225, 225, 225, 180));
		GridBagConstraints gbc_lblNewLabel_2 = new GridBagConstraints();
		gbc_lblNewLabel_2.insets = new Insets(0, 0, 5, 0);
		gbc_lblNewLabel_2.gridx = 2;
		gbc_lblNewLabel_2.gridy = 0;
		panel.add(myid, gbc_lblNewLabel_2);

		JLabel lblUserList = new JLabel("User List");
		lblUserList.setHorizontalAlignment(SwingConstants.LEFT);
		lblUserList.setForeground(new Color(255, 255, 255, 180));
		lblUserList.setFont(new Font("Marker Felt", Font.BOLD, 30));
		GridBagConstraints gbc_lblUserList = new GridBagConstraints();
		gbc_lblUserList.anchor = GridBagConstraints.WEST;
		gbc_lblUserList.insets = new Insets(0, 0, 5, 5);
		gbc_lblUserList.gridx = 0;
		gbc_lblUserList.gridy = 1;
		panel.add(lblUserList, gbc_lblUserList);

		JLabel lblNewLabel_1 = new JLabel("Chat Room");
		lblNewLabel_1.setHorizontalAlignment(SwingConstants.RIGHT);
		lblNewLabel_1.setForeground(new Color(255, 255, 255, 180));
		lblNewLabel_1.setFont(new Font("Marker Felt", Font.BOLD, 30));
		GridBagConstraints gbc_lblNewLabel_1 = new GridBagConstraints();
		gbc_lblNewLabel_1.anchor = GridBagConstraints.EAST;
		gbc_lblNewLabel_1.insets = new Insets(0, 0, 5, 0);
		gbc_lblNewLabel_1.gridx = 2;
		gbc_lblNewLabel_1.gridy = 1;
		panel.add(lblNewLabel_1, gbc_lblNewLabel_1);

		listModel = new DefaultListModel<String>();
		list = new JList<String>(listModel);
		list.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
		list.setSelectedIndex(0);
		list.setVisibleRowCount(10);
		list.setBackground(new Color(225, 0, 0, 50));
		list.setForeground(new Color(225, 225, 225, 180));
		list.setFont(new Font("Lucida Grande", Font.PLAIN, 25));

		JScrollPane scrollPane = new JScrollPane();
		scrollPane.setBackground(new Color(225, 0, 0, 50));
		scrollPane.setOpaque(false);
		GridBagConstraints gbc_scrollPane = new GridBagConstraints();
		gbc_scrollPane.insets = new Insets(0, 0, 5, 5);
		gbc_scrollPane.fill = GridBagConstraints.BOTH;
		gbc_scrollPane.gridx = 0;
		gbc_scrollPane.gridy = 2;
		panel.add(scrollPane, gbc_scrollPane);
		scrollPane.setViewportView(list);

		JScrollPane scrollPane_1 = new JScrollPane();
		scrollPane.setBackground(new Color(41, 29, 65, 151));
		scrollPane.setOpaque(false);
		GridBagConstraints gbc_scrollPane_1 = new GridBagConstraints();
		gbc_scrollPane_1.insets = new Insets(0, 0, 5, 0);
		gbc_scrollPane_1.fill = GridBagConstraints.BOTH;
		gbc_scrollPane_1.gridx = 2;
		gbc_scrollPane_1.gridy = 2;
		panel.add(scrollPane_1, gbc_scrollPane_1);

		chatroom = new JTextArea();
		chatroom.setBackground(new Color(41, 29, 65, 151));
		chatroom.setForeground(new Color(225, 225, 225, 180));
		chatroom.setRows(10);
		chatroom.setColumns(10);
		chatroom.setEditable(false);
		chatroom.setFont(new Font("Lucida Grande", Font.PLAIN, 25));
		scrollPane_1.setViewportView(chatroom);

		JTextField message = new JTextField("Send a message");
		message.setBackground(new Color(225, 225, 225, 150));
		message.setFont(new Font("Lucida Grande", Font.PLAIN, 25));
		message.setColumns(15);
		GridBagConstraints gbc_textField = new GridBagConstraints();
		// gbc_textField.weightx = 1.;
		gbc_textField.fill = GridBagConstraints.HORIZONTAL;
		gbc_textField.insets = new Insets(0, 0, 0, 5);
		gbc_textField.gridx = 0;
		gbc_textField.gridy = 3;
		panel.add(message, gbc_textField);

		Button btnNewButton_2 = new Button("Send");
		btnNewButton_2.setFont(new Font("Lucida Grande", Font.PLAIN, 30));
		btnNewButton_2.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				Network.sendMessage("CHAT|" + myname.getText().substring(5) + " (" + myid.getText().substring(4) + "):" + message.getText());
			}
		});
		GridBagConstraints gbc_btnNewButton_2 = new GridBagConstraints();
		gbc_btnNewButton_2.gridx = 2;
		gbc_btnNewButton_2.gridy = 3;
		panel.add(btnNewButton_2, gbc_btnNewButton_2);

		Button btnNewButton = new Button("Back");
		btnNewButton.setFont(new Font("Lucida Grande", Font.PLAIN, 30));
		btnNewButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				Network.sendMessage("DISCONNECT|" + id);
				JFrame jf = (JFrame) getRootPane().getParent();
				jf.setContentPane(new MainPage());
				jf.setVisible(true);
				JPanel imagePanel = (JPanel) jf.getContentPane();
				imagePanel.setOpaque(false);
			}
		});
		GridBagConstraints gbc_btnNewButton = new GridBagConstraints();
		gbc_btnNewButton.insets = new Insets(0, 0, 0, 5);
		gbc_btnNewButton.gridx = 0;
		gbc_btnNewButton.gridy = 2;
		add(btnNewButton, gbc_btnNewButton);

		start = new Button("Challenge");
		start.setFont(new Font("Lucida Grande", Font.PLAIN, 25));
		start.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				if (list.isSelectionEmpty()) {
					chatroom.append("Please select a user to challenge first\n");
				} else {
					String selected = list.getSelectedValue();
					selected = selected.substring(selected.indexOf("(") + 1);
					selected = selected.substring(0, selected.indexOf(")"));
					if (list.getSelectedIndex() != -1 && Integer.parseInt(selected) != id) {
						game = new Game(true, 2, 2);
						Network.sendMessage("CHALLENGE|" + selected);
						start.setEnabled(false);
					}
				}

			}
		});
		GridBagConstraints gbc_btnNewButton_1 = new GridBagConstraints();
		gbc_btnNewButton_1.gridx = 2;
		gbc_btnNewButton_1.gridy = 2;
		add(start, gbc_btnNewButton_1);
	}

	/**
	 * Add a message to the chatroom display.
	 * 
	 * @param m
	 *            The string message to add to the chatroom.
	 */
	public void addMessage(String m) {
		chatroom.append(m + "\n");
		repaint();
	}

	/**
	 * Add a username to the list of users.
	 * 
	 * @param m
	 *            The username to add.
	 */
	public void addUser(String m) {
		listModel.addElement(m);
		repaint();
	}

	/**
	 * Clears the list of users.
	 */
	public void clearUser() {
		listModel.clear();
	}

	/**
	 * Set this user's ID in the lobby display.
	 * 
	 * @param i
	 *            The ID.
	 */
	public void setID(int i) {
		id = i;
		myid.setText("ID: " + i);
		repaint();
	}

	/**
	 * @return This user's ID.
	 */
	public int getID() {
		return id;
	}

	/**
	 * @return The game object.
	 */
	public Game getGame() {
		return game;
	}

	/**
	 * Switch from the lobby display to the game display.
	 */
	public void startGame() {
		start.setEnabled(true);
		JFrame jf = (JFrame) getRootPane().getParent();
		ImageIcon background = new ImageIcon(LocalMultiplay.class.getResource("/client/resource/background2.png"));
		JLabel label = new JLabel(new ImageIcon(background.getImage().getScaledInstance(jf.getWidth(), jf.getHeight(), Image.SCALE_DEFAULT)));
		label.setBounds(0, 0, jf.getWidth(), jf.getHeight());
		JLayeredPane layeredPane = new JLayeredPane();
		layeredPane.add(label, new Integer(Integer.MIN_VALUE));
		jf.setLayeredPane(layeredPane);
		jf.setContentPane(game);
		JPanel imagePanel = (JPanel) jf.getContentPane();
		imagePanel.setOpaque(false);
		game.requestFocus();
		jf.setVisible(true);
	}

	/**
	 * Resets the challenge button.
	 */
	public void unchallenge() {
		start.setEnabled(true);
	}
}