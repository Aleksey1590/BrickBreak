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

/**
 * A class for the credits display panel within the user interface.
 */
@SuppressWarnings("serial")
public class Credits extends JPanel {
	private final Color transparentOverlay_1 = new Color(0, 0, 0, 100);
	private final ImageIcon CreditsImage = new ImageIcon(getClass().getResource("/client/resource/Credits.png"));

	/**
	 * Create the credits panel.
	 */
	public Credits() {
		GridBagLayout gridBagLayout = new GridBagLayout();
		gridBagLayout.columnWidths = new int[] { 60, 352, 60, 0 };
		gridBagLayout.rowHeights = new int[] { 0, 0, 0, 0, 0, 0 };
		gridBagLayout.columnWeights = new double[] { 0.0, 1.0, 0.0, Double.MIN_VALUE };
		gridBagLayout.rowWeights = new double[] { 0.0, 1.0, 1.0, 1.0, 0.0, Double.MIN_VALUE };
		setLayout(gridBagLayout);

		JLabel lblNewLabel = new JLabel(CreditsImage);
		lblNewLabel.setFont(new Font("Luminari", Font.BOLD, 70));
		lblNewLabel.setForeground(new Color(255, 237, 175));
		GridBagConstraints gbc_lblNewLabel = new GridBagConstraints();
		gbc_lblNewLabel.insets = new Insets(0, 0, 5, 5);
		gbc_lblNewLabel.gridx = 1;
		gbc_lblNewLabel.gridy = 1;
		add(lblNewLabel, gbc_lblNewLabel);

		JPanel panel = new JPanel();
		panel.setBackground(transparentOverlay_1);
		GridBagConstraints gbc_panel = new GridBagConstraints();
		gbc_panel.insets = new Insets(0, 0, 5, 5);
		gbc_panel.fill = GridBagConstraints.BOTH;
		gbc_panel.gridx = 1;
		gbc_panel.gridy = 2;
		add(panel, gbc_panel);
		GridBagLayout gbl_panel = new GridBagLayout();
		gbl_panel.columnWidths = new int[] { 354, 0 };
		gbl_panel.rowHeights = new int[] { 0, 0, 0, 0, 0, 0, 0, 0, 0 };
		gbl_panel.columnWeights = new double[] { 1.0, Double.MIN_VALUE };
		gbl_panel.rowWeights = new double[] { 1.0, 1.0, 1.0, 1.0, 1.0, 1.0, 1.0, 0.0, Double.MIN_VALUE };
		panel.setLayout(gbl_panel);

		JLabel lblNewLabel_1 = new JLabel("Edward Richards");
		lblNewLabel_1.setForeground(new Color(255, 255, 255, 180));
		lblNewLabel_1.setFont(new Font("Luminari", Font.BOLD, 30));
		GridBagConstraints gbc_lblNewLabel_1 = new GridBagConstraints();
		gbc_lblNewLabel_1.insets = new Insets(0, 0, 5, 0);
		gbc_lblNewLabel_1.gridx = 0;
		gbc_lblNewLabel_1.gridy = 1;
		panel.add(lblNewLabel_1, gbc_lblNewLabel_1);

		JLabel lblNewLabel_2 = new JLabel("Hon Yiu Wong");
		lblNewLabel_2.setForeground(new Color(255, 255, 255, 180));
		lblNewLabel_2.setFont(new Font("Luminari", Font.BOLD, 30));
		GridBagConstraints gbc_lblNewLabel_2 = new GridBagConstraints();
		gbc_lblNewLabel_2.insets = new Insets(0, 0, 5, 0);
		gbc_lblNewLabel_2.gridx = 0;
		gbc_lblNewLabel_2.gridy = 2;
		panel.add(lblNewLabel_2, gbc_lblNewLabel_2);

		JLabel lblNewLabel_3 = new JLabel("Olexiy Dubilet");
		lblNewLabel_3.setForeground(new Color(255, 255, 255, 180));
		lblNewLabel_3.setFont(new Font("Luminari", Font.BOLD, 30));
		GridBagConstraints gbc_lblNewLabel_3 = new GridBagConstraints();
		gbc_lblNewLabel_3.insets = new Insets(0, 0, 5, 0);
		gbc_lblNewLabel_3.gridx = 0;
		gbc_lblNewLabel_3.gridy = 3;
		panel.add(lblNewLabel_3, gbc_lblNewLabel_3);

		JLabel lblNewLabel_4 = new JLabel("Paul Roskell");
		lblNewLabel_4.setForeground(new Color(255, 255, 255, 180));
		lblNewLabel_4.setFont(new Font("Luminari", Font.BOLD, 30));
		GridBagConstraints gbc_lblNewLabel_4 = new GridBagConstraints();
		gbc_lblNewLabel_4.insets = new Insets(0, 0, 5, 0);
		gbc_lblNewLabel_4.gridx = 0;
		gbc_lblNewLabel_4.gridy = 4;
		panel.add(lblNewLabel_4, gbc_lblNewLabel_4);

		JLabel lblNewLabel_5 = new JLabel("Tamara Herbert");
		lblNewLabel_5.setForeground(new Color(255, 255, 255, 180));
		lblNewLabel_5.setFont(new Font("Luminari", Font.BOLD, 30));
		GridBagConstraints gbc_lblNewLabel_5 = new GridBagConstraints();
		gbc_lblNewLabel_5.insets = new Insets(0, 0, 5, 0);
		gbc_lblNewLabel_5.gridx = 0;
		gbc_lblNewLabel_5.gridy = 5;
		panel.add(lblNewLabel_5, gbc_lblNewLabel_5);

		JLabel lblNewLabel_6 = new JLabel("Xuan Chen");
		lblNewLabel_6.setForeground(new Color(255, 255, 255, 180));
		lblNewLabel_6.setFont(new Font("Luminari", Font.BOLD, 30));
		GridBagConstraints gbc_lblNewLabel_6 = new GridBagConstraints();
		gbc_lblNewLabel_6.insets = new Insets(0, 0, 5, 0);
		gbc_lblNewLabel_6.gridx = 0;
		gbc_lblNewLabel_6.gridy = 6;
		panel.add(lblNewLabel_6, gbc_lblNewLabel_6);

		Button btnNewButton = new Button("Back");
		btnNewButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				JFrame jf = (JFrame) getRootPane().getParent();
				jf.setContentPane(new Settings());
				jf.setVisible(true);
				JPanel imagePanel = (JPanel) jf.getContentPane();
				imagePanel.setOpaque(false);
			}
		});
		btnNewButton.setFont(new Font("Lucida Grande", Font.PLAIN, 25));
		GridBagConstraints gbc_btnNewButton = new GridBagConstraints();
		gbc_btnNewButton.anchor = GridBagConstraints.WEST;
		gbc_btnNewButton.insets = new Insets(0, 0, 5, 5);
		gbc_btnNewButton.gridx = 1;
		gbc_btnNewButton.gridy = 3;
		add(btnNewButton, gbc_btnNewButton);
	}

}
