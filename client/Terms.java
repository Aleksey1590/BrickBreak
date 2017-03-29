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
 * A class for displaying the terms of use in the user interface.
 */
@SuppressWarnings("serial")
public class Terms extends JPanel {
	private final ImageIcon TermImage = new ImageIcon(getClass().getResource("/client/resource/terms.png"));
	private final ImageIcon TTImage = new ImageIcon(getClass().getResource("/client/resource/TermsTitle.png"));

	/**
	 * Create the terms panel.
	 */
	public Terms() {
		GridBagLayout gridBagLayout = new GridBagLayout();
		gridBagLayout.columnWidths = new int[] { 0, 0, 0, 0, 0 };
		gridBagLayout.rowHeights = new int[] { 0, 0, 0, 0, 0 };
		gridBagLayout.columnWeights = new double[] { 0.0, 0.0, 0.0, 0.0, Double.MIN_VALUE };
		gridBagLayout.rowWeights = new double[] { 0.0, 0.0, 0.0, 0.0, Double.MIN_VALUE };
		setLayout(gridBagLayout);

		JLabel lblGuide = new JLabel(TTImage);
		lblGuide.setFont(new Font("Luminari", Font.BOLD, 70));
		lblGuide.setForeground(new Color(255, 237, 175));
		GridBagConstraints gbc_lblGuide = new GridBagConstraints();
		gbc_lblGuide.insets = new Insets(0, 0, 5, 5);
		gbc_lblGuide.gridx = 1;
		gbc_lblGuide.gridy = 1;
		add(lblGuide, gbc_lblGuide);

		JPanel panel = new JPanel();
		panel.setBounds(100, 100, 100, 600);
		panel.setBackground(new Color(0, 0, 0, 100));
		JLabel label = new JLabel(TermImage);
		panel.add(label);
		GridBagConstraints gbc_panel = new GridBagConstraints();
		gbc_panel.insets = new Insets(0, 0, 5, 5);
		gbc_panel.fill = GridBagConstraints.BOTH;
		gbc_panel.gridx = 1;
		gbc_panel.gridy = 2;
		add(panel, gbc_panel);

		Button btnNewButton = new Button("Back");
		btnNewButton.setFont(new Font("Lucida Grande", Font.PLAIN, 25));
		btnNewButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				JFrame jf = (JFrame) getRootPane().getParent();
				jf.setContentPane(new Support());
				jf.setVisible(true);
				JPanel imagePanel = (JPanel) jf.getContentPane();
				imagePanel.setOpaque(false);
			}
		});
		GridBagConstraints gbc_btnNewButton = new GridBagConstraints();
		gbc_btnNewButton.insets = new Insets(0, 0, 0, 5);
		gbc_btnNewButton.gridx = 0;
		gbc_btnNewButton.gridy = 3;
		add(btnNewButton, gbc_btnNewButton);
	}

}
