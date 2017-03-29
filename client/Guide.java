package client;

import java.awt.Color;
import java.awt.Container;
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
 * A class for the guide display panel within the user interface.
 */
@SuppressWarnings("serial")
public class Guide extends JPanel {
	private final ImageIcon GuideImage = new ImageIcon(getClass().getResource("/client/resource/Guide.png"));
	private final ImageIcon GTImage = new ImageIcon(getClass().getResource("/client/resource/GuideTitle.png"));
	private final Container previousContentPane;

	/**
	 * Create the guide panel.
	 */
	public Guide(Container previousPane) {
		previousContentPane = previousPane;

		GridBagLayout gridBagLayout = new GridBagLayout();
		gridBagLayout.columnWidths = new int[] { 0, 0, 0, 0, 0 };
		gridBagLayout.rowHeights = new int[] { 0, 0, 0, 0, 0 };
		gridBagLayout.columnWeights = new double[] { 1.0, 1.0, 1.0, 0.0, Double.MIN_VALUE };
		gridBagLayout.rowWeights = new double[] { 0.0, 1.0, 1.0, 1.0, Double.MIN_VALUE };
		setLayout(gridBagLayout);

		JLabel lblGuide = new JLabel(GTImage);
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
		JLabel label = new JLabel(GuideImage);
		panel.add(label);
		GridBagConstraints gbc_panel = new GridBagConstraints();
		gbc_panel.insets = new Insets(0, 0, 5, 5);
		gbc_panel.fill = GridBagConstraints.BOTH;
		gbc_panel.gridx = 1;
		gbc_panel.gridy = 2;
		add(panel, gbc_panel);

		Button btnNewButton = new Button("Back");
		btnNewButton.setPreferredSize(new Dimension(100, 40));
		btnNewButton.setFont(new Font("Lucida Grande", Font.PLAIN, 25));
		btnNewButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				JFrame jf = (JFrame) getRootPane().getParent();
				jf.setContentPane(previousContentPane);
				jf.setVisible(true);
				JPanel imagePanel = (JPanel) jf.getContentPane();
				imagePanel.setOpaque(false);
			}
		});
		GridBagConstraints gbc_btnNewButton = new GridBagConstraints();
		gbc_btnNewButton.fill = GridBagConstraints.HORIZONTAL;
		gbc_btnNewButton.insets = new Insets(0, 0, 0, 5);
		gbc_btnNewButton.gridx = 0;
		gbc_btnNewButton.gridy = 3;
		add(btnNewButton, gbc_btnNewButton);

		if (previousContentPane instanceof MainPage) {
			Button btnNewButton_1 = new Button("OK");
			btnNewButton_1.setPreferredSize(new Dimension(100, 40));
			btnNewButton_1.setFont(new Font("Lucida Grande", Font.PLAIN, 25));
			btnNewButton_1.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent e) {
					JFrame jf = (JFrame) getRootPane().getParent();
					jf.setContentPane(new LocalMultiplay());
					jf.setVisible(true);
					JPanel imagePanel = (JPanel) jf.getContentPane();
					imagePanel.setOpaque(false);
				}
			});
			GridBagConstraints gbc_btnNewButton_1 = new GridBagConstraints();
			gbc_btnNewButton_1.fill = GridBagConstraints.HORIZONTAL;
			gbc_btnNewButton_1.insets = new Insets(0, 0, 0, 5);
			gbc_btnNewButton_1.gridx = 2;
			gbc_btnNewButton_1.gridy = 3;
			add(btnNewButton_1, gbc_btnNewButton_1);
		}
	}

}
