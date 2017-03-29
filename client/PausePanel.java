package client;

import java.awt.Color;
import java.awt.Font;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JLabel;
import javax.swing.JPanel;

/**
 * A class for the panel that is displayed when users pause a game.
 */
@SuppressWarnings("serial")
public class PausePanel extends JPanel {
	private static String Music;
	private static String Sound;

	/**
	 * Create the panel.
	 */
	public PausePanel() {
		GridBagLayout gridBagLayout = new GridBagLayout();
		gridBagLayout.columnWidths = new int[] { 0, 0, 0 };
		gridBagLayout.rowHeights = new int[] { 0, 0, 0 };
		gridBagLayout.columnWeights = new double[] { 1.0, 1.0, Double.MIN_VALUE };
		gridBagLayout.rowWeights = new double[] { 1.0, 1.0, Double.MIN_VALUE };
		setLayout(gridBagLayout);

		JLabel lblNewLabel = new JLabel("Music");
		lblNewLabel.setFont(new Font("Marker Felt", Font.BOLD, 52));
		lblNewLabel.setForeground(new Color(255, 237, 175));
		GridBagConstraints gbc_lblNewLabel = new GridBagConstraints();
		gbc_lblNewLabel.insets = new Insets(0, 0, 5, 5);
		gbc_lblNewLabel.gridx = 0;
		gbc_lblNewLabel.gridy = 0;
		add(lblNewLabel, gbc_lblNewLabel);

		Button btnNewButton = new Button();
		if (MainPage.play) {
			Music = "OFF";
			btnNewButton.setText(Music);
		} else {
			Music = "ON";
			btnNewButton.setText(Music);
		}
		btnNewButton.setFont(new Font("Marker Felt", Font.BOLD, 52));
		btnNewButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				String temp = Music;
				if (temp == "ON") {
					Music = "OFF";
					MainPage.sound.playBGM();
					MainPage.play = true;
					btnNewButton.setText(Music);
				}
				if (temp == "OFF") {
					Music = "ON";
					MainPage.sound.getBGMClip().stop();
					MainPage.play = false;
					btnNewButton.setText(Music);
				}

			}
		});
		GridBagConstraints gbc_btnNewButton = new GridBagConstraints();
		gbc_btnNewButton.anchor = GridBagConstraints.WEST;
		gbc_btnNewButton.insets = new Insets(0, 0, 5, 0);
		gbc_btnNewButton.gridx = 1;
		gbc_btnNewButton.gridy = 0;
		add(btnNewButton, gbc_btnNewButton);

		JLabel lblNewLabel_1 = new JLabel("SoundFX");
		lblNewLabel_1.setFont(new Font("Marker Felt", Font.BOLD, 52));
		lblNewLabel_1.setForeground(new Color(255, 237, 175));
		GridBagConstraints gbc_lblNewLabel_1 = new GridBagConstraints();
		gbc_lblNewLabel_1.insets = new Insets(0, 0, 0, 5);
		gbc_lblNewLabel_1.gridx = 0;
		gbc_lblNewLabel_1.gridy = 1;
		add(lblNewLabel_1, gbc_lblNewLabel_1);

		Button btnNewButton_1 = new Button();
		btnNewButton_1.setFont(new Font("Marker Felt", Font.BOLD, 52));
		if (Game.soundplay) {
			Sound = "OFF";
			btnNewButton_1.setText(Sound);
		} else {
			Sound = "ON";
			btnNewButton_1.setText(Sound);
		}
		btnNewButton_1.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				String temp = Sound;
				if (temp == "ON") {
					Sound = "OFF";
					Game.soundplay = true;
					btnNewButton_1.setText(Sound);
				}
				if (temp == "OFF") {
					Sound = "ON";
					Game.soundplay = false;
					btnNewButton_1.setText(Sound);
				}
			}
		});
		GridBagConstraints gbc_btnNewButton_1 = new GridBagConstraints();
		gbc_btnNewButton_1.anchor = GridBagConstraints.WEST;
		gbc_btnNewButton_1.gridx = 1;
		gbc_btnNewButton_1.gridy = 1;
		add(btnNewButton_1, gbc_btnNewButton_1);
	}

}
