package client;

import java.awt.Color;

import java.awt.Font;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.SwingConstants;

/**
 * A class for the display that allows users to connect to a server.
 */
@SuppressWarnings("serial")
public class OnlineMultiplay extends JPanel {
	private final Color transparentOverlay_1 = new Color(0, 0, 0, 100);
	private final ImageIcon OnlineImage = new ImageIcon(getClass().getResource("/client/resource/GameSetup.png"));

	/**
	 * Create the panel.
	 */
	public OnlineMultiplay() {

		GridBagLayout gridBagLayout = new GridBagLayout();
		gridBagLayout.columnWidths = new int[] { 76, 352, 60, 0 };
		gridBagLayout.rowHeights = new int[] { 0, 0, 0, 0, 0, 0 };
		gridBagLayout.columnWeights = new double[] { 1.0, 1.0, 1.0, Double.MIN_VALUE };
		gridBagLayout.rowWeights = new double[] { 0.0, 1.0, 1.0, 1.0, 1.0, Double.MIN_VALUE };
		setLayout(gridBagLayout);

		JLabel lblNewLabel = new JLabel(OnlineImage);
		lblNewLabel.setFont(new Font("Luminari", Font.BOLD, 70));
		lblNewLabel.setForeground(new Color(255, 237, 175));
		GridBagConstraints gbc_lblNewLabel = new GridBagConstraints();
		gbc_lblNewLabel.insets = new Insets(0, 0, 5, 5);
		gbc_lblNewLabel.gridx = 1;
		gbc_lblNewLabel.gridy = 1;
		add(lblNewLabel, gbc_lblNewLabel);

		JLabel connectionStatus = new JLabel("Waiting to connect");
		connectionStatus.setFont(new Font("Luminari", Font.BOLD, 20));
		connectionStatus.setForeground(new Color(255, 237, 175));
		GridBagConstraints gbc_connectionStatus = new GridBagConstraints();
		gbc_connectionStatus.insets = new Insets(0, 0, 5, 5);
		gbc_connectionStatus.gridx = 1;
		gbc_connectionStatus.gridy = 3;
		add(connectionStatus, gbc_connectionStatus);

		JPanel panel = new JPanel();
		panel.setBackground(transparentOverlay_1);
		GridBagConstraints gbc_panel = new GridBagConstraints();
		gbc_panel.gridheight = 2;
		gbc_panel.insets = new Insets(0, 0, 5, 5);
		gbc_panel.fill = GridBagConstraints.BOTH;
		gbc_panel.gridx = 1;
		gbc_panel.gridy = 2;
		add(panel, gbc_panel);
		GridBagLayout gbl_panel = new GridBagLayout();
		gbl_panel.columnWidths = new int[] { 0, 0, 0 };
		gbl_panel.rowHeights = new int[] { 0, 0, 0 };
		gbl_panel.columnWeights = new double[] { 1.0, 1.0, Double.MIN_VALUE };
		gbl_panel.rowWeights = new double[] { 1.0, 1.0, Double.MIN_VALUE };
		panel.setLayout(gbl_panel);

		JLabel host = new JLabel("Host");
		host.setForeground(Color.GRAY);
		host.setFont(new Font("Lucida Grande", Font.PLAIN, 30));
		GridBagConstraints gbc_lblNewLabel_1 = new GridBagConstraints();
		gbc_lblNewLabel_1.anchor = GridBagConstraints.SOUTHEAST;
		gbc_lblNewLabel_1.insets = new Insets(0, 0, 5, 5);
		gbc_lblNewLabel_1.gridx = 0;
		gbc_lblNewLabel_1.gridy = 0;
		panel.add(host, gbc_lblNewLabel_1);

		JTextField server = new JTextField();
		server.setFont(new Font("Lucida Grande", Font.PLAIN, 30));
		server.setHorizontalAlignment(SwingConstants.CENTER);
		GridBagConstraints gbc_textField = new GridBagConstraints();
		gbc_textField.anchor = GridBagConstraints.SOUTHWEST;
		gbc_textField.insets = new Insets(0, 0, 5, 0);
		gbc_textField.gridx = 1;
		gbc_textField.gridy = 0;
		panel.add(server, gbc_textField);
		server.setColumns(10);

		JLabel uname = new JLabel("Username");
		uname.setForeground(Color.GRAY);
		uname.setFont(new Font("Lucida Grande", Font.PLAIN, 30));
		GridBagConstraints gbc_lblNewLabel_2 = new GridBagConstraints();
		gbc_lblNewLabel_2.anchor = GridBagConstraints.NORTHEAST;
		gbc_lblNewLabel_2.insets = new Insets(0, 0, 0, 5);
		gbc_lblNewLabel_2.gridx = 0;
		gbc_lblNewLabel_2.gridy = 1;
		panel.add(uname, gbc_lblNewLabel_2);

		JTextField name = new JTextField();
		name.setHorizontalAlignment(SwingConstants.CENTER);
		name.setFont(new Font("Lucida Grande", Font.PLAIN, 30));
		GridBagConstraints gbc_textField_1 = new GridBagConstraints();
		gbc_textField_1.anchor = GridBagConstraints.NORTHWEST;
		gbc_textField_1.gridx = 1;
		gbc_textField_1.gridy = 1;
		panel.add(name, gbc_textField_1);
		name.setColumns(10);

		Button btnNewButton_1 = new Button("Connect");
		btnNewButton_1.setFont(new Font("Lucida Grande", Font.PLAIN, 25));
		btnNewButton_1.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				connectionStatus.setText("Trying to connect");
				if (name.getText().equals("")) {
					connectionStatus.setText("Please enter a name");
				} else {
					Lobby lobby = new Lobby(name.getText());
					MainPage.setLobby(lobby);
					if (Network.connect(server.getText(), name.getText(), lobby)) {
						JFrame jf = (JFrame) getRootPane().getParent();
						jf.setContentPane(lobby);
						jf.setVisible(true);
						JPanel imagePanel = (JPanel) jf.getContentPane();
						imagePanel.setOpaque(false);
					} else {
						connectionStatus.setText("Couldn't connect to server");
					}
				}

			}
		});

		Button btnNewButton = new Button("Back");
		btnNewButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				JFrame jf = (JFrame) getRootPane().getParent();
				jf.setContentPane(new MainPage());
				jf.setVisible(true);
				JPanel imagePanel = (JPanel) jf.getContentPane();
				imagePanel.setOpaque(false);
			}
		});
		btnNewButton.setFont(new Font("Lucida Grande", Font.PLAIN, 25));
		GridBagConstraints gbc_btnNewButton = new GridBagConstraints();
		gbc_btnNewButton.insets = new Insets(0, 0, 0, 5);
		gbc_btnNewButton.gridx = 0;
		gbc_btnNewButton.gridy = 4;
		add(btnNewButton, gbc_btnNewButton);
		GridBagConstraints gbc_btnNewButton_1 = new GridBagConstraints();
		gbc_btnNewButton_1.gridx = 2;
		gbc_btnNewButton_1.gridy = 4;
		add(btnNewButton_1, gbc_btnNewButton_1);
	}

}
