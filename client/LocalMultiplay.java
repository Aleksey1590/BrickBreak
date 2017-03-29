package client;

import java.awt.Color;
import java.awt.Container;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Image;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.ButtonGroup;
import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JLayeredPane;
import javax.swing.JPanel;
import javax.swing.JRadioButton;
import javax.swing.Timer;

/**
 * A class for the panel that allows users to setup and start a local game.
 */
@SuppressWarnings("serial")
public class LocalMultiplay extends JPanel {
	private final Color transparentOverlay_1 = new Color(225, 0, 0, 50);
	private final Color transparentOverlay_2 = new Color(41, 29, 65, 151);
	private int level;
	public static final int EASY = 1;
	public static final int MEDIUM = 2;
	public static final int HARD = 3;
	private int noPlayers = 2;
	private Game game;
	private final ImageIcon LocalImage = new ImageIcon(getClass().getResource("/client/resource/GameSetup.png"));
	private Timer myTimer;
	private JLabel TimeLabel;
	private static int remainTime = 4;
	private JFrame frame;
	private Container con;

	/**
	 * Create the panel.
	 */
	public LocalMultiplay() {
		GridBagLayout gridBagLayout = new GridBagLayout();
		gridBagLayout.columnWidths = new int[] { 40, 190, 42, 190, 40, 0 };
		gridBagLayout.rowHeights = new int[] { 0, 0, 0, 0, 0, 0, 0 };
		gridBagLayout.columnWeights = new double[] { 0.0, 1.0, 0.0, 1.0, 0.0, Double.MIN_VALUE };
		gridBagLayout.rowWeights = new double[] { 0.0, 1.0, 0.0, 1.0, 0.0, 0.0, Double.MIN_VALUE };
		setLayout(gridBagLayout);

		JRadioButton rdbtnNewRadioButton = new JRadioButton("Cheats");
		rdbtnNewRadioButton.setOpaque(false);
		rdbtnNewRadioButton.setFocusable(false);
		rdbtnNewRadioButton.setForeground(Color.WHITE);
		rdbtnNewRadioButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				Game.cheats = !Game.cheats;
			}
		});
		GridBagConstraints gbc_rdbtnNewRadioButton = new GridBagConstraints();
		gbc_rdbtnNewRadioButton.insets = new Insets(0, 0, 5, 0);
		gbc_rdbtnNewRadioButton.gridx = 4;
		gbc_rdbtnNewRadioButton.gridy = 0;
		add(rdbtnNewRadioButton, gbc_rdbtnNewRadioButton);

		JLabel lblNewLabel = new JLabel(LocalImage);
		lblNewLabel.setForeground(new Color(255, 237, 175));
		lblNewLabel.setFont(new Font("Luminari", Font.BOLD, 70));
		GridBagConstraints gbc_lblNewLabel = new GridBagConstraints();
		gbc_lblNewLabel.gridwidth = 3;
		gbc_lblNewLabel.insets = new Insets(0, 0, 5, 5);
		gbc_lblNewLabel.gridx = 1;
		gbc_lblNewLabel.gridy = 1;
		add(lblNewLabel, gbc_lblNewLabel);

		JLabel lblLevel = new JLabel("Level");
		lblLevel.setForeground(new Color(255, 255, 255, 180));
		lblLevel.setFont(new Font("Marker Felt", Font.BOLD, 50));
		GridBagConstraints gbc_lblLevel = new GridBagConstraints();
		gbc_lblLevel.anchor = GridBagConstraints.WEST;
		gbc_lblLevel.insets = new Insets(0, 0, 5, 5);
		gbc_lblLevel.gridx = 1;
		gbc_lblLevel.gridy = 2;
		add(lblLevel, gbc_lblLevel);

		JLabel lblNoplayer = new JLabel("No.player");
		lblNoplayer.setForeground(new Color(255, 255, 255, 180));
		lblNoplayer.setFont(new Font("Marker Felt", Font.BOLD, 50));
		GridBagConstraints gbc_lblNoplayer = new GridBagConstraints();
		gbc_lblNoplayer.anchor = GridBagConstraints.EAST;
		gbc_lblNoplayer.insets = new Insets(0, 0, 5, 5);
		gbc_lblNoplayer.gridx = 3;
		gbc_lblNoplayer.gridy = 2;
		add(lblNoplayer, gbc_lblNoplayer);

		JPanel panel = new JPanel();
		panel.setBackground(transparentOverlay_1);
		panel.setPreferredSize(new Dimension(230, 400));
		GridBagConstraints gbc_panel = new GridBagConstraints();
		gbc_panel.insets = new Insets(0, 0, 5, 5);
		gbc_panel.fill = GridBagConstraints.HORIZONTAL;
		gbc_panel.gridx = 1;
		gbc_panel.gridy = 3;
		add(panel, gbc_panel);
		GridBagLayout gbl_panel = new GridBagLayout();
		gbl_panel.columnWidths = new int[] { 0, 0 };
		gbl_panel.rowHeights = new int[] { 0, 0, 0, 0, 0, 0, 0 };
		gbl_panel.columnWeights = new double[] { 1.0, Double.MIN_VALUE };
		gbl_panel.rowWeights = new double[] { 0.0, 1.0, 0.0, 1.0, 0.0, 1.0, Double.MIN_VALUE };
		panel.setLayout(gbl_panel);

		// Choose the level of the game
		JRadioButton easyButton = new JRadioButton("Easy");
		easyButton.setOpaque(false);
		easyButton.setForeground(new Color(255, 255, 255, 180));
		easyButton.setFont(new Font("Marker Felt", Font.PLAIN, 45));
		GridBagConstraints gbc_chckbxNewCheckBox = new GridBagConstraints();
		gbc_chckbxNewCheckBox.insets = new Insets(0, 0, 5, 0);
		gbc_chckbxNewCheckBox.gridx = 0;
		gbc_chckbxNewCheckBox.gridy = 1;
		easyButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				level = EASY;
			}
		});
		panel.add(easyButton, gbc_chckbxNewCheckBox);

		JRadioButton mediumButton = new JRadioButton("Medium");
		mediumButton.setOpaque(false);
		mediumButton.setForeground(new Color(255, 237, 175));
		mediumButton.setFont(new Font("Marker Felt", Font.PLAIN, 45));
		GridBagConstraints gbc_chckbxNewCheckBox_1 = new GridBagConstraints();
		gbc_chckbxNewCheckBox_1.insets = new Insets(0, 0, 5, 0);
		gbc_chckbxNewCheckBox_1.gridx = 0;
		gbc_chckbxNewCheckBox_1.gridy = 3;
		mediumButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				level = MEDIUM;
			}
		});
		panel.add(mediumButton, gbc_chckbxNewCheckBox_1);

		JRadioButton hardButton = new JRadioButton("Hard");
		hardButton.setOpaque(false);
		hardButton.setForeground(new Color(225, 0, 0, 170));
		hardButton.setFont(new Font("Marker Felt", Font.PLAIN, 45));
		GridBagConstraints gbc_chckbxNewCheckBox_2 = new GridBagConstraints();
		gbc_chckbxNewCheckBox_2.gridx = 0;
		gbc_chckbxNewCheckBox_2.gridy = 5;
		hardButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				level = HARD;
			}
		});
		panel.add(hardButton, gbc_chckbxNewCheckBox_2);

		// Only can choose one button
		ButtonGroup buttongroup_1 = new ButtonGroup();
		// Set a default button selection, so the game can't be entered with no AI.
		buttongroup_1.add(easyButton);
		buttongroup_1.add(mediumButton);
		buttongroup_1.add(hardButton);
		buttongroup_1.setSelected(easyButton.getModel(), true);

		JPanel panel_1 = new JPanel();
		panel_1.setBackground(transparentOverlay_2);
		panel_1.setPreferredSize(new Dimension(230, 400));
		GridBagConstraints gbc_panel_1 = new GridBagConstraints();
		gbc_panel_1.insets = new Insets(0, 0, 5, 5);
		gbc_panel_1.fill = GridBagConstraints.HORIZONTAL;
		gbc_panel_1.gridx = 3;
		gbc_panel_1.gridy = 3;
		add(panel_1, gbc_panel_1);
		GridBagLayout gbl_panel_1 = new GridBagLayout();
		gbl_panel_1.columnWidths = new int[] { 0, 0 };
		gbl_panel_1.rowHeights = new int[] { 0, 0, 0, 0, 0, 0, 0 };
		gbl_panel_1.columnWeights = new double[] { 1.0, Double.MIN_VALUE };
		gbl_panel_1.rowWeights = new double[] { 0.0, 1.0, 1.0, 1.0, 1.0, 1.0, Double.MIN_VALUE };
		panel_1.setLayout(gbl_panel_1);

		// Choose the number of players
		JRadioButton chckbxNewCheckBox_3 = new JRadioButton("2");
		chckbxNewCheckBox_3.setOpaque(false);
		chckbxNewCheckBox_3.setForeground(new Color(255, 255, 255, 180));
		chckbxNewCheckBox_3.setFont(new Font("Marker Felt", Font.PLAIN, 45));
		GridBagConstraints gbc_chckbxNewCheckBox_3 = new GridBagConstraints();
		gbc_chckbxNewCheckBox_3.insets = new Insets(0, 0, 5, 0);
		gbc_chckbxNewCheckBox_3.gridx = 0;
		gbc_chckbxNewCheckBox_3.gridy = 1;
		chckbxNewCheckBox_3.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				noPlayers = 2;
			}
		});
		panel_1.add(chckbxNewCheckBox_3, gbc_chckbxNewCheckBox_3);

		JRadioButton chckbxNewCheckBox_4 = new JRadioButton("3");
		chckbxNewCheckBox_4.setOpaque(false);
		chckbxNewCheckBox_4.setForeground(new Color(255, 255, 255, 180));
		chckbxNewCheckBox_4.setFont(new Font("Marker Felt", Font.PLAIN, 45));
		GridBagConstraints gbc_chckbxNewCheckBox_4 = new GridBagConstraints();
		gbc_chckbxNewCheckBox_4.insets = new Insets(0, 0, 5, 0);
		gbc_chckbxNewCheckBox_4.gridx = 0;
		gbc_chckbxNewCheckBox_4.gridy = 2;
		chckbxNewCheckBox_4.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				noPlayers = 3;
			}
		});
		panel_1.add(chckbxNewCheckBox_4, gbc_chckbxNewCheckBox_4);

		JRadioButton chckbxNewCheckBox_5 = new JRadioButton("4");
		chckbxNewCheckBox_5.setOpaque(false);
		chckbxNewCheckBox_5.setForeground(new Color(255, 255, 255, 180));
		chckbxNewCheckBox_5.setFont(new Font("Marker Felt", Font.PLAIN, 45));
		GridBagConstraints gbc_chckbxNewCheckBox_5 = new GridBagConstraints();
		gbc_chckbxNewCheckBox_5.insets = new Insets(0, 0, 5, 0);
		gbc_chckbxNewCheckBox_5.gridx = 0;
		gbc_chckbxNewCheckBox_5.gridy = 3;
		chckbxNewCheckBox_5.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				noPlayers = 4;
			}
		});
		panel_1.add(chckbxNewCheckBox_5, gbc_chckbxNewCheckBox_5);

		// Only can choose one button
		ButtonGroup buttongroup_2 = new ButtonGroup();
		buttongroup_2.add(chckbxNewCheckBox_3);
		buttongroup_2.add(chckbxNewCheckBox_4);
		buttongroup_2.add(chckbxNewCheckBox_5);
		buttongroup_2.setSelected(chckbxNewCheckBox_3.getModel(), true);

		Button btnNewButton = new Button("Back");
		btnNewButton.setFont(new Font("Lucida Grande", Font.PLAIN, 25));
		btnNewButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				JFrame jf = (JFrame) getRootPane().getParent();
				jf.setContentPane(new MainPage());
				jf.setVisible(true);
				JPanel imagePanel = (JPanel) jf.getContentPane();
				imagePanel.setOpaque(false);
			}
		});
		GridBagConstraints gbc_btnNewButton = new GridBagConstraints();
		gbc_btnNewButton.insets = new Insets(0, 0, 5, 5);
		gbc_btnNewButton.gridx = 0;
		gbc_btnNewButton.gridy = 4;
		add(btnNewButton, gbc_btnNewButton);

		// link to the game scene
		Button btnNewButton_1 = new Button("Play");
		btnNewButton_1.setFont(new Font("Lucida Grande", Font.PLAIN, 25));
		btnNewButton_1.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				frame = (JFrame) getRootPane().getParent();
				ImageIcon background = new ImageIcon(LocalMultiplay.class.getResource("/client/resource/background2.png"));
				JLabel label = new JLabel(new ImageIcon(background.getImage().getScaledInstance(frame.getWidth(), frame.getHeight(), Image.SCALE_DEFAULT)));
				label.setBounds(0, 0, frame.getWidth(), frame.getHeight());
				JLayeredPane layeredPane = new JLayeredPane();
				layeredPane.add(label, new Integer(Integer.MIN_VALUE));
				frame.setLayeredPane(layeredPane);

				Game.backClick = false;
				if (noPlayers == 3) {
					game = new TriangleGame(false, noPlayers, level);
				} else {
					game = new Game(false, noPlayers, level);
				}
				frame.setContentPane(game);
				con = frame.getLayeredPane();
				con.setLayout(null);
				JPanel TimePanel = new JPanel();
				TimePanel.setBackground(new Color(9, 15, 31, 150));
				TimePanel.setBounds(100, 100, 1000, 500);
				TimeLabel = new JLabel();
				TimeLabel.setForeground(Color.WHITE);
				TimeLabel.setFont(new Font("Luminari", Font.BOLD, 350));
				TimePanel.add(TimeLabel);
				con.add(TimePanel, new Integer(Integer.MAX_VALUE));

				frame.validate();
				frame.repaint();
				myTimer.start();
				JPanel imagePanel = (JPanel) frame.getContentPane();
				imagePanel.setOpaque(false);
				game.requestFocus();
				frame.setVisible(true);
			}
		});
		GridBagConstraints gbc_btnNewButton_1 = new GridBagConstraints();
		gbc_btnNewButton_1.insets = new Insets(0, 0, 5, 0);
		gbc_btnNewButton_1.gridx = 4;
		gbc_btnNewButton_1.gridy = 4;
		add(btnNewButton_1, gbc_btnNewButton_1);

		myTimer = new Timer(1000, new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				if (remainTime <= 0) {
					myTimer.stop();
					con.remove(0);
					frame.validate();
					frame.repaint();
					if (!Game.backClick) {
						game.startGame();
					}
					remainTime = 4;
				} else {
					remainTime--;
					TimeLabel.setText(Integer.toString(remainTime));
					frame.repaint();
				}
			}
		});
	}

}
