package client;

import java.awt.Color;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.GridBagLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.sound.sampled.FloatControl;
import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JSlider;
import javax.swing.border.EmptyBorder;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;

/**
 * A class for the settings panel in the user interface.
 */
@SuppressWarnings("serial")
public class Settings extends JPanel {
	private final JPanel backgroundPane = new JPanel();
	private final ImageIcon SettingImage = new ImageIcon(getClass().getResource("/client/resource/Settings.png"));
	private JLabel lblNewLabel = new JLabel(SettingImage);
	private JLabel lblNewLabel_1 = new JLabel("SoundFX");
	// Controls the volume of the game sound
	private JSlider gameSoundSlider = new JSlider(-40, 6);
	private JLabel lblNewLabel_2 = new JLabel("Music");
	// Controls the volume of the background music
	private JSlider gameMusicSlider = new JSlider(-40, 6);
	private Button btnNewButton_1 = new Button("Credits ");
	private Button btnNewButton_2 = new Button("  Back  ");
	private final Color transparentOverlay = new Color(0, 0, 0, 100);
	private static int MusicValue;
	private static int SoundValue;

	/**
	 * Create the settings panel.
	 */
	public Settings() {
		setLayout(new GridBagLayout());
		backgroundPane.setLayout(new BoxLayout(backgroundPane, BoxLayout.PAGE_AXIS));
		backgroundPane.setSize(WIDTH / 2, HEIGHT / 2);
		backgroundPane.setBackground(transparentOverlay);
		backgroundPane.setBorder(new EmptyBorder(50, 50, 50, 50));

		lblNewLabel.setFont(new Font("Luminari", Font.BOLD, 70));
		lblNewLabel.setAlignmentX(Component.CENTER_ALIGNMENT);
		lblNewLabel_1.setFont(new Font("Lucida Grande", Font.BOLD, 30));
		lblNewLabel_1.setAlignmentX(Component.CENTER_ALIGNMENT);

		lblNewLabel_2.setFont(new Font("Lucida Grande", Font.BOLD, 30));
		lblNewLabel_2.setAlignmentX(Component.CENTER_ALIGNMENT);

		lblNewLabel.setForeground(new Color(255, 237, 175));
		lblNewLabel_1.setForeground(new Color(255, 255, 255, 180));
		lblNewLabel_2.setForeground(new Color(255, 255, 255, 180));

		btnNewButton_1.setAlignmentX(Component.CENTER_ALIGNMENT);
		btnNewButton_2.setAlignmentX(Component.CENTER_ALIGNMENT);

		// Check the credits
		btnNewButton_1.setFont(new Font("Lucida Grande", Font.BOLD, 30));
		btnNewButton_1.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				JFrame jf = (JFrame) getRootPane().getParent();
				jf.getContentPane().setVisible(false);
				jf.setContentPane(new Credits());
				ImageIcon background = new ImageIcon(getClass().getResource("/client/resource/background.jpg"));
				JLabel label = new JLabel(background);
				label.setBounds(0, 0, jf.getWidth(), jf.getHeight());
				JPanel imagePanel = (JPanel) jf.getContentPane();
				imagePanel.setOpaque(false);
				jf.getLayeredPane().add(label, new Integer(Integer.MIN_VALUE));
				jf.setVisible(true);
			}
		});

		// Back to the mainpage
		btnNewButton_2.setFont(new Font("Lucida Grande", Font.BOLD, 30));
		btnNewButton_2.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				JFrame jf = (JFrame) getRootPane().getParent();
				jf.setContentPane(new MainPage());
				jf.setVisible(true);
				JPanel imagePanel = (JPanel) jf.getContentPane();
				imagePanel.setOpaque(false);
			}
		});

		gameSoundSlider.addChangeListener(new ChangeListener() {
			public void stateChanged(ChangeEvent e) {
				GameSound.gameSoundVolume = ((JSlider) e.getSource()).getValue();
				SoundValue = ((JSlider) e.getSource()).getValue();
			}
		});
		gameSoundSlider.setValue(SoundValue);

		gameMusicSlider.addChangeListener(new ChangeListener() {
			public void stateChanged(ChangeEvent e) {
				if (MainPage.play) {
					((FloatControl) MainPage.sound.getBGMClip().getControl(FloatControl.Type.MASTER_GAIN)).setValue(((JSlider) e.getSource()).getValue());
				}
				GameSound.gameMusicVolume = ((JSlider) e.getSource()).getValue();
				MusicValue = ((JSlider) e.getSource()).getValue();
			}
		});
		gameMusicSlider.setValue(MusicValue);
		btnNewButton_1.setPreferredSize(new Dimension(200, 40));
		btnNewButton_2.setPreferredSize(new Dimension(200, 40));
		backgroundPane.add(lblNewLabel);
		backgroundPane.add(Box.createRigidArea(new Dimension(5, 20)));
		backgroundPane.add(lblNewLabel_1);
		backgroundPane.add(gameSoundSlider);
		backgroundPane.add(lblNewLabel_2);
		backgroundPane.add(gameMusicSlider);
		backgroundPane.add(Box.createRigidArea(new Dimension(5, 10)));
		backgroundPane.add(Box.createRigidArea(new Dimension(5, 10)));
		backgroundPane.add(btnNewButton_1);
		backgroundPane.add(Box.createRigidArea(new Dimension(5, 10)));
		backgroundPane.add(btnNewButton_2);
		add(backgroundPane);
	}

}