package client;

import java.awt.Color;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.GridLayout;
import java.awt.Image;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JRadioButton;
import javax.swing.SwingConstants;
import javax.swing.border.EmptyBorder;

/**
 * A class for the main menu of the game.
 */
@SuppressWarnings("serial")
public class MainPage extends JPanel {
	private final JPanel loginPane = new JPanel();
	private final ImageIcon settingIcon = new ImageIcon(getClass().getResource("/client/resource/setting.png"));
	private final ImageIcon supportIcon = new ImageIcon(getClass().getResource("/client/resource/support.png"));
	private final ImageIcon gameTitle = new ImageIcon(getClass().getResource("/client/resource/gameTitle.png"));
	private final ImageIcon button = new ImageIcon(getClass().getResource("/client/resource/LocalGame.png"));
	private final ImageIcon onlinebutton = new ImageIcon(getClass().getResource("/client/resource/OnlineGame.png"));
	private final ImageIcon Exitbutton = new ImageIcon(getClass().getResource("/client/resource/Exit.png"));
	private final Color transparentOverlay = new Color(0, 0, 0, 100);
	public static GameSound sound = new GameSound();
	public static boolean play = false;
	public static int top;
	public static int bottom;
	public static int left;
	public static int right;
	public static int WIDTH = 1200;
	public static int HEIGHT = 750;
	private static Lobby lobby;

	/**
	 * Create the panel.
	 */
	public MainPage() {
		setLayout(new GridBagLayout());
		setSize(1200, 800);
		loginPane.setLayout(new GridLayout(4, 0, 0, 0));
		loginPane.setSize(WIDTH / 2, HEIGHT / 2);
		loginPane.setBackground(transparentOverlay);
		loginPane.setBorder(new EmptyBorder(50, 50, 50, 50));

		final JLabel gameTitleLabel = new JLabel(gameTitle);
		gameTitleLabel.setAlignmentX(Component.CENTER_ALIGNMENT);
		// Get image for the support button
		// Link to the Support Page
		final JButton supportButton = new JButton(supportIcon);
		supportButton.setBorderPainted(false);
		supportButton.setContentAreaFilled(false);
		supportButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				JFrame jf = (JFrame) getRootPane().getParent();
				jf.setContentPane(new Support());
				jf.setVisible(true);
				JPanel imagePanel = (JPanel) jf.getContentPane();
				imagePanel.setOpaque(false);
			}
		});
		supportButton.setPreferredSize(new Dimension(80, 70));

		// Get the image for setting button
		// Link to the setting page
		final JButton settingsButton = new JButton(settingIcon);
		settingsButton.setBorderPainted(false);
		settingsButton.setContentAreaFilled(false);
		settingsButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				JFrame jf = (JFrame) getRootPane().getParent();
				jf.setContentPane(new Settings());
				jf.setVisible(true);
				JPanel imagePanel = (JPanel) jf.getContentPane();
				imagePanel.setOpaque(false);
			}
		});
		settingsButton.setPreferredSize(new Dimension(80, 70));
		settingsButton.setOpaque(false);

		Button LocalButton = new Button(button);
		LocalButton.setFocusPainted(false);
		LocalButton.setBorderPainted(false);
		LocalButton.setContentAreaFilled(false);
		LocalButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				JFrame jf = (JFrame) getRootPane().getParent();
				jf.setContentPane(new Guide(jf.getContentPane()));
				jf.setVisible(true);
				JPanel imagePanel = (JPanel) jf.getContentPane();
				imagePanel.setOpaque(false);
			}

		});

		Button OnlineButton = new Button(onlinebutton);
		OnlineButton.setFocusPainted(false);
		OnlineButton.setBorderPainted(false);
		OnlineButton.setContentAreaFilled(false);
		OnlineButton.setFont(new Font("Marker Felt", Font.BOLD, 40));
		OnlineButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				JFrame jf = (JFrame) getRootPane().getParent();
				jf.setContentPane(new OnlineMultiplay());
				jf.setVisible(true);
				JPanel imagePanel = (JPanel) jf.getContentPane();
				imagePanel.setOpaque(false);
			}
		});

		Button ExitButton = new Button(Exitbutton);
		ExitButton.setFocusPainted(false);
		ExitButton.setBorderPainted(false);
		ExitButton.setContentAreaFilled(false);
		ExitButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				int i = JOptionPane.showConfirmDialog(null, "Are you sure you want to exit the game?", "Confirm exit", JOptionPane.YES_NO_OPTION);
				if (i == JOptionPane.YES_OPTION) {
					System.exit(0);
				}
			}
		});

		JRadioButton rdbtnNewRadioButton = new JRadioButton("Music");
		rdbtnNewRadioButton.setOpaque(false);
		rdbtnNewRadioButton.setFocusable(false);
		rdbtnNewRadioButton.setFont(new Font("Lucida Grande", Font.PLAIN, 19));
		rdbtnNewRadioButton.setForeground(Color.GRAY);
		rdbtnNewRadioButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				play = !play;
				if (play) {
					sound.playBGM();
				} else {
					sound.getBGMClip().stop();
					sound.getBGMClip().close();
				}
			}
		});
		rdbtnNewRadioButton.setPreferredSize(new Dimension(100, 70));
		rdbtnNewRadioButton.setSelected(play);

		loginPane.add(gameTitleLabel, SwingConstants.CENTER);
		loginPane.add(LocalButton);
		loginPane.add(OnlineButton);
		loginPane.add(ExitButton);

		GridBagConstraints l = new GridBagConstraints();
		l.anchor = GridBagConstraints.CENTER;
		l.gridx = 50;
		l.gridy = 50;
		l.weightx = 100;
		l.weighty = 100;

		GridBagConstraints a = new GridBagConstraints();
		a.anchor = GridBagConstraints.NORTHEAST;
		a.gridx = 100;
		a.gridy = 0;

		GridBagConstraints c = new GridBagConstraints();
		c.anchor = GridBagConstraints.SOUTHEAST;
		c.gridx = 100;
		c.gridy = 100;

		GridBagConstraints d = new GridBagConstraints();
		d.anchor = GridBagConstraints.SOUTHWEST;
		d.gridx = 0;
		d.gridy = 100;

		add(loginPane, l);
		add(rdbtnNewRadioButton, a);
		add(supportButton, c);
		add(settingsButton, d);
	}

	/**
	 * Launch the GUI.
	 * 
	 * @param args
	 *            Arguments passed to the program.
	 */
	public static void main(String[] args) {
		JFrame frame = new JFrame("BrickBreak");
		frame.setResizable(false);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.setContentPane(new MainPage());
		frame.setBounds(100, 0, WIDTH, HEIGHT);
		((JPanel) frame.getContentPane()).setOpaque(false);
		// set the background
		// The image is from https://www.flickr.com/photos/53807034@N05/5788998529/in/photostream/
		// License https://creativecommons.org/licenses/by/2.0/
		ImageIcon background = new ImageIcon(MainPage.class.getResource("/client/resource/background.jpg"));
		JLabel label = new JLabel(new ImageIcon(background.getImage().getScaledInstance(frame.getWidth(), frame.getHeight(), Image.SCALE_DEFAULT)));
		label.setBounds(0, 0, frame.getWidth(), frame.getHeight());
		frame.getLayeredPane().add(label, new Integer(Integer.MIN_VALUE));
		frame.setVisible(true);
		Insets a = frame.getInsets();
		top = a.top;
		bottom = a.bottom;
		left = a.left;
		right = a.right;
	}

	/**
	 * Stores the lobby object in this static class so it can be retrieved once a networked game has completed.
	 * 
	 * @param l
	 *            The multiplayer lobby when connected to a server.
	 */
	public static void setLobby(Lobby l) {
		lobby = l;
	}

	/**
	 * @return The lobby object.
	 */
	public static Lobby getLobby() {
		return lobby;
	}

}
