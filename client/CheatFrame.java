package client;

import java.awt.BorderLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.border.EmptyBorder;

/**
 * A class for a GUI interface to allow the user to apply cheats to the game.
 */
@SuppressWarnings("serial")
public class CheatFrame extends JFrame {
	private JPanel contentPane;
	private JTextField textField;
	private JTextField textField_1;
	private JTextField textField_2;
	private JTextField textField_3;
	private JTextField textField_4;
	private JTextField textField_5;
	private JTextField textField_6;
	private JTextField textField_7;
	private JButton btnNewButton;
	private JButton btnOk;
	private JButton btnOk_1;
	private JButton btnNewButton_1;
	private JButton btnOk_2;
	private JButton btnOk_3;
	private JButton btnOk_4;

	private int PlayerID_1;
	private int PlayerID_2;
	private int increaseWidth;
	private int decreaseWidth;
	private int increaseBrick;
	private int decreaseBrick;
	private int increaseBall;
	private int decreaseBall;

	private CheatEngine cheatEngine = new CheatEngine();
	public Game game;

	/**
	 * Creates a new CheatFrame object.
	 */
	public CheatFrame() {
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 400, 270);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		contentPane.setLayout(new BorderLayout(0, 0));
		setContentPane(contentPane);

		JPanel panel = new JPanel();
		contentPane.add(panel, BorderLayout.CENTER);

		JLabel lblPlayerId = new JLabel("Player ID:");
		panel.add(lblPlayerId);

		// get playerID
		textField = new JTextField("0");
		panel.add(textField);
		textField.setColumns(2);

		JLabel lblNewLabel = new JLabel("Increase Player Width:");
		panel.add(lblNewLabel);

		// get increased width
		textField_1 = new JTextField("0");
		panel.add(textField_1);
		textField_1.setColumns(2);

		// Click on this Button to set the playerId and increase playerWidth
		btnOk = new JButton("Ok");
		btnOk.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				try {
					PlayerID_1 = Integer.parseInt(textField.getText());
					increaseWidth = Integer.parseInt(textField_1.getText());
					cheatEngine.increasePlayerWidth(PlayerID_1, increaseWidth, game.getPlayerList());
				} catch (NumberFormatException numExc) {

				}
			}
		});
		panel.add(btnOk);

		JLabel lblPlayerId_1 = new JLabel("Player ID:");
		panel.add(lblPlayerId_1);

		// get playerID
		textField_2 = new JTextField("0");
		panel.add(textField_2);
		textField_2.setColumns(2);

		JLabel lblNewLabel_1 = new JLabel("Decrease Player Width:");
		panel.add(lblNewLabel_1);

		// get decreased width
		textField_3 = new JTextField("0");
		panel.add(textField_3);
		textField_3.setColumns(2);

		// click on this button to set playerID and decreased width
		btnOk_1 = new JButton("Ok");
		btnOk_1.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				try {
					PlayerID_2 = Integer.parseInt(textField_2.getText());
					decreaseWidth = Integer.parseInt(textField_3.getText());
					cheatEngine.decreasePlayerWidth(PlayerID_2, decreaseWidth, game.getPlayerList());
				} catch (NumberFormatException numExc) {

				}
			}
		});
		panel.add(btnOk_1);

		JLabel lblBallId = new JLabel("Increase Brick Size:");
		panel.add(lblBallId);

		// get increased brick size
		textField_4 = new JTextField("0");
		panel.add(textField_4);
		textField_4.setColumns(10);

		// click on this button to set increased brick size
		btnNewButton_1 = new JButton("Ok");
		panel.add(btnNewButton_1);
		btnNewButton_1.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				try {
					increaseBrick = Integer.parseInt(textField_4.getText());
					cheatEngine.increaseBrickSize(increaseBrick, game.getBrickList());
				} catch (NumberFormatException numExc) {

				}
			}
		});

		JLabel lblDecreaseBrickSize = new JLabel("Decrease Brick Size:");
		panel.add(lblDecreaseBrickSize);

		// get decreased brick size
		textField_5 = new JTextField("0");
		panel.add(textField_5);
		textField_5.setColumns(10);

		// click on this button to set decreased brick size
		btnOk_2 = new JButton("Ok");
		btnOk_2.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				try {
					decreaseBrick = Integer.parseInt(textField_5.getText());
					cheatEngine.decreaseBrickSize(decreaseBrick, game.getBrickList());
				} catch (NumberFormatException numExc) {

				}
			}
		});
		panel.add(btnOk_2);

		JLabel lblNewLabel_2 = new JLabel("Increase Ball Size:");
		panel.add(lblNewLabel_2);

		// get increased ball size
		textField_6 = new JTextField("0");
		panel.add(textField_6);
		textField_6.setColumns(10);

		// click on this button to set increased ball size
		btnOk_3 = new JButton("Ok");
		btnOk_3.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				try {
					increaseBall = Integer.parseInt(textField_6.getText());
					cheatEngine.increaseBallSize(increaseBall, game.getBallList());
				} catch (NumberFormatException numExc) {

				}
			}
		});
		panel.add(btnOk_3);

		JLabel lblDreaseBallSize = new JLabel("Drease Ball Size:");
		panel.add(lblDreaseBallSize);

		// get decreased ball size
		textField_7 = new JTextField("0");
		panel.add(textField_7);
		textField_7.setColumns(10);

		// click on this button to set decreased ball size
		btnOk_4 = new JButton("Ok");
		btnOk_4.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				try {
					decreaseBall = Integer.parseInt(textField_7.getText());
					cheatEngine.decreaseBallSize(decreaseBall, game.getBallList());
				} catch (NumberFormatException numExc) {

				}
			}
		});
		panel.add(btnOk_4);

		// click on this button to ass ball
		btnNewButton = new JButton("Add Ball");
		btnNewButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				try {
					cheatEngine.addBall(game.getBallList());
				} catch (NumberFormatException numExc) {

				}
			}
		});
		panel.add(btnNewButton);
	}

}
