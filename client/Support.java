package client;

import java.awt.Color;
import java.awt.Dimension;
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
 * A class for the support panel in the user interface.
 */
@SuppressWarnings("serial")
public class Support extends JPanel {
	private final ImageIcon SupportImage = new ImageIcon(getClass().getResource("/client/resource/Supports.png"));

	/**
	 * Create the support panel.
	 */
	public Support() {
		// FIXME The links on the buttons in this page haven't been finished
		GridBagLayout gridBagLayout = new GridBagLayout();
		gridBagLayout.columnWidths = new int[] { 121, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0 };
		gridBagLayout.rowHeights = new int[] { 0, 0, 30, 0, 26, 60, 60, 60, 60, 62, 15, 0 };
		gridBagLayout.columnWeights = new double[] { 0.0, 1.0, 0.0, 0.0, 0.0, 0.0, 1.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, Double.MIN_VALUE };
		gridBagLayout.rowWeights = new double[] { 0.0, 0.0, 1.0, 1.0, 0.0, 1.0, 1.0, 1.0, 1.0, 1.0, 1.0, Double.MIN_VALUE };
		setLayout(gridBagLayout);

		JLabel lblNewLabel = new JLabel(SupportImage);
		lblNewLabel.setForeground(new Color(255, 237, 175));
		lblNewLabel.setFont(new Font("Luminari", Font.BOLD, 70));
		GridBagConstraints gbc_lblNewLabel = new GridBagConstraints();
		gbc_lblNewLabel.gridheight = 5;
		gbc_lblNewLabel.anchor = GridBagConstraints.SOUTH;
		gbc_lblNewLabel.gridwidth = 15;
		gbc_lblNewLabel.insets = new Insets(0, 0, 5, 0);
		gbc_lblNewLabel.gridx = 0;
		gbc_lblNewLabel.gridy = 0;
		add(lblNewLabel, gbc_lblNewLabel);

		Button btnNewButton = new Button("Guide");
		btnNewButton.setFont(new Font("Lucida Grande", Font.BOLD, 40));
		btnNewButton.setPreferredSize(new Dimension(400, 60));
		btnNewButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				JFrame jf = (JFrame) getRootPane().getParent();
				jf.setContentPane(new Guide(getRootPane().getContentPane()));
				jf.setVisible(true);
				JPanel imagePanel = (JPanel) jf.getContentPane();
				imagePanel.setOpaque(false);
			}
		});
		GridBagConstraints gbc_btnNewButton = new GridBagConstraints();
		gbc_btnNewButton.insets = new Insets(0, 0, 5, 5);
		gbc_btnNewButton.gridwidth = 14;
		gbc_btnNewButton.gridx = 0;
		gbc_btnNewButton.gridy = 5;
		add(btnNewButton, gbc_btnNewButton);

		Button btnNewButton_1 = new Button("Terms of use");
		btnNewButton_1.setFont(new Font("Lucida Grande", Font.BOLD, 40));
		btnNewButton_1.setPreferredSize(new Dimension(400, 60));
		btnNewButton_1.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				JFrame jf = (JFrame) getRootPane().getParent();
				jf.setContentPane(new Terms());
				jf.setVisible(true);
				JPanel imagePanel = (JPanel) jf.getContentPane();
				imagePanel.setOpaque(false);
			}
		});
		GridBagConstraints gbc_btnNewButton_1 = new GridBagConstraints();
		gbc_btnNewButton_1.insets = new Insets(0, 0, 5, 5);
		gbc_btnNewButton_1.gridwidth = 14;
		gbc_btnNewButton_1.gridx = 0;
		gbc_btnNewButton_1.gridy = 6;
		add(btnNewButton_1, gbc_btnNewButton_1);

		Button btnNewButton_2 = new Button("Privacy Policy");
		btnNewButton_2.setFont(new Font("Lucida Grande", Font.BOLD, 40));
		btnNewButton_2.setPreferredSize(new Dimension(400, 60));
		btnNewButton_2.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				JFrame jf = (JFrame) getRootPane().getParent();
				jf.setContentPane(new Policy());
				jf.setVisible(true);
				JPanel imagePanel = (JPanel) jf.getContentPane();
				imagePanel.setOpaque(false);
			}
		});
		GridBagConstraints gbc_btnNewButton_2 = new GridBagConstraints();
		gbc_btnNewButton_2.gridwidth = 14;
		gbc_btnNewButton_2.insets = new Insets(0, 0, 5, 5);
		gbc_btnNewButton_2.gridx = 0;
		gbc_btnNewButton_2.gridy = 7;
		add(btnNewButton_2, gbc_btnNewButton_2);

		// Back to the mainpage
		Button btnNewButton_4 = new Button("Back");
		btnNewButton_4.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				JFrame jf = (JFrame) getRootPane().getParent();
				jf.setContentPane(new MainPage());
				jf.setVisible(true);
				JPanel imagePanel = (JPanel) jf.getContentPane();
				imagePanel.setOpaque(false);
			}
		});
		btnNewButton_4.setFont(new Font("Lucida Grande", Font.PLAIN, 25));
		GridBagConstraints gbc_btnNewButton_4 = new GridBagConstraints();
		gbc_btnNewButton_4.anchor = GridBagConstraints.WEST;
		gbc_btnNewButton_4.insets = new Insets(0, 0, 5, 5);
		gbc_btnNewButton_4.gridx = 1;
		gbc_btnNewButton_4.gridy = 9;
		add(btnNewButton_4, gbc_btnNewButton_4);
	}

}
