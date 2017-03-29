package client;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JLayeredPane;
import javax.swing.JPanel;
import javax.swing.JTextArea;

/**
 * A class for the post-game results display.
 */
@SuppressWarnings("serial")
public class Result extends JPanel {
	private final Color transparentOverlay_1 = new Color(24, 52, 89, 100);
	private JTextArea scores;
	private JLabel win;
	private final ImageIcon ResultTableImage = new ImageIcon(getClass().getResource("/client/resource/ResultTable.png"));
	private boolean mp;

	/**
	 * Create the panel.
	 */
	public Result(boolean b, boolean multiplayer) {
		mp = multiplayer;

		GridBagLayout gridBagLayout = new GridBagLayout();
		gridBagLayout.columnWidths = new int[] { 0, 0, 0, 0 };
		gridBagLayout.rowHeights = new int[] { 0, 0, 0, 0 };
		gridBagLayout.columnWeights = new double[] { 0.0, 1.0, 0.0, Double.MIN_VALUE };
		gridBagLayout.rowWeights = new double[] { 1.0, 1.0, 0.0, Double.MIN_VALUE };
		setLayout(gridBagLayout);

		JLabel lblNewLabel = new JLabel("Game Over");
		lblNewLabel.setFont(new Font("Luminari", Font.BOLD, 72));
		lblNewLabel.setForeground(new Color(255, 237, 175));
		GridBagConstraints gbc_lblNewLabel = new GridBagConstraints();
		gbc_lblNewLabel.insets = new Insets(0, 0, 5, 5);
		gbc_lblNewLabel.gridx = 1;
		gbc_lblNewLabel.gridy = 0;
		add(lblNewLabel, gbc_lblNewLabel);

		JPanel imagePanel = new JPanel() {
			protected void paintComponent(Graphics g) {
				super.paintComponent(g);
				ResultTableImage.paintIcon(this, g, 0, 0);
			}
		};

		imagePanel.setBackground(transparentOverlay_1);

		GridBagConstraints gbc_panel = new GridBagConstraints();
		gbc_panel.gridwidth = 3;
		gbc_panel.insets = new Insets(0, 0, 5, 5);
		gbc_panel.fill = GridBagConstraints.BOTH;
		gbc_panel.gridx = 0;
		gbc_panel.gridy = 1;
		add(imagePanel, gbc_panel);
		GridBagLayout gbl_panel = new GridBagLayout();
		gbl_panel.columnWidths = new int[] { 0, 0 };
		gbl_panel.rowHeights = new int[] { 0, 0, 0 };
		gbl_panel.columnWeights = new double[] { 1.0, Double.MIN_VALUE };
		gbl_panel.rowWeights = new double[] { 0.0, 1.0, Double.MIN_VALUE };
		imagePanel.setLayout(gbl_panel);

		win = new JLabel("You lost!");
		if (b) {
			win.setText("You won!");
		}
		win.setFont(new Font("Marker Felt", Font.BOLD, 52));
		win.setForeground(new Color(165, 11, 11));
		GridBagConstraints gbc_lblNewLabel_1 = new GridBagConstraints();
		gbc_lblNewLabel_1.anchor = GridBagConstraints.SOUTH;
		gbc_lblNewLabel_1.insets = new Insets(0, 0, 5, 0);
		gbc_lblNewLabel_1.gridx = 0;
		gbc_lblNewLabel_1.gridy = 0;
		imagePanel.add(win, gbc_lblNewLabel_1);

		scores = new JTextArea("Scores:\n");
		scores.setPreferredSize(new Dimension(700, 400));
		scores.setBackground(new Color(10, 20, 50, 100));
		scores.setFont(new Font("Marker Felt", Font.PLAIN, 30));
		scores.setForeground(Color.WHITE);
		scores.setTabSize(10);
		GridBagConstraints gbc_textField = new GridBagConstraints();
		gbc_textField.gridx = 0;
		gbc_textField.gridy = 1;
		imagePanel.add(scores, gbc_textField);
		scores.setColumns(30);

		Button btnNewButton = new Button("Back");
		btnNewButton.setFont(new Font("Lucida Grande", Font.PLAIN, 25));
		btnNewButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				if (mp) {
					JFrame jf = (JFrame) getRootPane().getParent();
					jf.getContentPane().setVisible(false);
					ImageIcon background = new ImageIcon(MainPage.class.getResource("/client/resource/background.jpg"));
					JLabel label = new JLabel(background);
					label.setBounds(0, 0, jf.getWidth(), jf.getHeight());
					JLayeredPane layeredPane = new JLayeredPane();
					layeredPane.add(label, new Integer(Integer.MIN_VALUE));
					jf.setLayeredPane(layeredPane);
					jf.setContentPane(MainPage.getLobby());
					JPanel imagePanel = (JPanel) jf.getContentPane();
					imagePanel.setOpaque(false);
					jf.setVisible(true);

				} else {
					JFrame jf = (JFrame) getRootPane().getParent();
					jf.getContentPane().setVisible(false);
					ImageIcon background = new ImageIcon(MainPage.class.getResource("/client/resource/background.jpg"));
					JLabel label = new JLabel(background);
					label.setBounds(0, 0, jf.getWidth(), jf.getHeight());
					JLayeredPane layeredPane = new JLayeredPane();
					layeredPane.add(label, new Integer(Integer.MIN_VALUE));
					jf.setLayeredPane(layeredPane);
					jf.setContentPane(new MainPage());
					JPanel imagePanel = (JPanel) jf.getContentPane();
					imagePanel.setOpaque(false);
					jf.setVisible(true);
				}

			}
		});
		GridBagConstraints gbc_btnNewButton = new GridBagConstraints();
		gbc_btnNewButton.insets = new Insets(0, 0, 0, 5);
		gbc_btnNewButton.gridx = 0;
		gbc_btnNewButton.gridy = 2;
		add(btnNewButton, gbc_btnNewButton);
	}

	/**
	 * Add a string message to the results display. This allows the game object to send the results.
	 * 
	 * @param m
	 *            The message to add.
	 */
	public void addMessage(String m) {
		scores.append(m + "\n");
		repaint();
	}

}
