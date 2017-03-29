package client;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Container;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.RenderingHints;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Random;
import java.util.concurrent.ConcurrentHashMap;

import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JLayeredPane;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JRadioButton;
import javax.swing.Timer;

/**
 * A class for the (rectangular) game object, which displays the game on-screen, holds game-data performs all calculations and processes user input.
 */
@SuppressWarnings("serial")
public class Game extends JPanel implements ActionListener, KeyListener {
	public static GameSound sound = new GameSound();
	private final GameParams params = new GameParams();
	protected final ConcurrentHashMap<Integer, Player> players = new ConcurrentHashMap<Integer, Player>();
	protected final ConcurrentHashMap<Integer, Ball> balls = new ConcurrentHashMap<Integer, Ball>();
	protected final ConcurrentHashMap<Integer, Brick> bricks = new ConcurrentHashMap<Integer, Brick>();
	private final ConcurrentHashMap<Integer, Bonus> bonus = new ConcurrentHashMap<Integer, Bonus>();
	private final CheatFrame cheatFrame = new CheatFrame();
	private static final int TICK_RATE = 10;
	private final Timer t = new Timer(TICK_RATE, this);
	private int myID = 0;
	private int bonusID = 0;
	private final boolean[] keys = new boolean[65535];
	private boolean isMP = false;
	private int noPlayers = 0;
	private int level;
	public static final int EASY = 1;
	public static final int MEDIUM = 2;
	public static final int HARD = 3;
	private final Random random = new Random();
	private static boolean pause = false;
	protected static boolean cheats = false;
	public static boolean backClick = false;
	public static boolean soundplay = true;

	/**
	 * Creates a new game (rectangular map - 2 to 4 players).
	 * 
	 * @param mp
	 *            Whether or not the game is multiplayer.
	 * @param player
	 *            The number of players.
	 * @param nolevel
	 *            The level (the difficulty of the game).
	 */
	public Game(boolean mp, int player, int nolevel) {
		isMP = mp;
		noPlayers = player;
		level = nolevel;
		GameParams.initializeParams();

		switch (nolevel) {
		case 1:
			GameParams.levelOneParams();
			break;
		case 2:
			GameParams.levelTwoParams();
			break;
		case 3:
			GameParams.levelThreeParams();
			break;
		default:
			GameParams.levelOneParams();
			break;
		}

		layoutStuff();
		addBricks();
		setFocusable(true);
		addKeyListener(this);
		createPlayers();
		createBalls();
		if (cheats) {
			cheatFrame.setVisible(true);
			cheatFrame.game = this;
		}
	}

	// get methods
	/**
	 * @return The ID of the paddle this player controls in-game.
	 */
	public int getID() {
		return myID;
	}

	/**
	 * @param i
	 *            The ID of the player object to return.
	 * @return The player object.
	 */
	public Player getPlayer(int i) {
		return players.get(i);
	}

	/**
	 * @return The HashMap of players objects.
	 */
	public ConcurrentHashMap<Integer, Player> getPlayerList() {
		return players;
	}

	/**
	 * @param i
	 *            The ID of the ball object to return.
	 * @return The ball object.
	 */
	public Ball getBall(int i) {
		return balls.get(i);
	}

	/**
	 * @return The HashMap of ball objects.
	 */
	public ConcurrentHashMap<Integer, Ball> getBallList() {
		return balls;
	}

	/**
	 * @param i
	 *            The ID of the brick object to return.
	 * @return The brick object.
	 */
	public Brick getBrick(int i) {
		return bricks.get(i);
	}

	/**
	 * @return The HashMap of brick objects.
	 */
	public ConcurrentHashMap<Integer, Brick> getBrickList() {
		return bricks;
	}

	/**
	 * @return Whether or not the game is multiplayer.
	 */
	public boolean isMP() {
		return isMP;
	}

	/**
	 * @return The level (difficulty) of the game.
	 */
	public int getLevel() {
		return level;
	}

	/**
	 * @return The random object in the game.
	 */
	public Random getRandom() {
		return random;
	}

	// set methods
	/**
	 * Sets the human player's in-game ID (chooses which paddle he controls).
	 * 
	 * @param i
	 *            The ID of the paddle for the human player to control.
	 */
	public void setID(int i) {
		myID = i;
	}

	/**
	 * Starts the game.
	 */
	public void startGame() {
		t.start();
	}

	/**
	 * Sends a message to the server detailing a brick collision.
	 * 
	 * @param i
	 *            The ID of the brick that was hit.
	 */
	public void sendBrickHit(int i) {
		String msg = "";
		if (bricks.get(i).getHealth() == 0) {
			msg += "BRICKSX|";
		} else {
			msg += "BRICKSH|";
		}
		msg += i + "|";
		Network.sendMessage(msg);

	}

	/**
	 * Sends a message to the server detailing which bricks start with bonuses.
	 */
	public void sendBonuses() {
		if (myID == 0) {
			String msg = "BONUSES|";
			for (Integer key : bricks.keySet()) {
				msg += key;
				msg += "|";
				msg += bricks.get(key).getBonusID();
				msg += "|";
			}
			Network.sendMessage(msg);
		}
	}

	/**
	 * Loads the brick data from the external parameter file.
	 */
	public void addBricks() {
		bricks.clear();
		createHorizontalBricks(params.getBricksMap(true), params.getStartingXCoord(true), params.getStartingYCoord(true), params.getBrickWidth(true), params.getBrickHeight(true));
		createVerticalBricks(params.getBricksMap(false), params.getStartingXCoord(false), params.getStartingYCoord(false), params.getBrickWidth(false), params.getBrickHeight(false));
	}

	/**
	 * Sets up the panel components on-screen.
	 */
	public void layoutStuff() {
		setLayout(new BorderLayout());
		Button btnBack = new Button("Back");
		btnBack.setFont(new Font("Lucida Grande", Font.PLAIN, 25));
		btnBack.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				int i = JOptionPane.showConfirmDialog(null, "Are you sure you want to go back to the main page?", "Warning", JOptionPane.YES_NO_OPTION);
				if (i == JOptionPane.YES_OPTION) {
					backClick = true;
					pause = false;
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
					t.stop();
					if (cheats) {
						cheatFrame.setVisible(false);
					}
				}
			}
		});
		Button btnNewButton = new Button("Exit");
		btnNewButton.setFont(new Font("Lucida Grande", Font.PLAIN, 25));
		btnNewButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				int i = JOptionPane.showConfirmDialog(null, "Are you sure you want to exit the game?", "Confirm exit", JOptionPane.YES_NO_OPTION);
				if (i == JOptionPane.YES_OPTION) {
					Network.sendMessage("DISCONNECT|");
					System.exit(0);
				} else {
					JFrame jf = (JFrame) getRootPane().getParent();
					jf.getContentPane().requestFocus();
				}
			}
		});

		// button to pause the music.
		JRadioButton pauseRadioButton = new JRadioButton("Pause");
		pauseRadioButton.setOpaque(false);
		pauseRadioButton.setFont(new Font("Lucida Grande", Font.PLAIN, 19));
		pauseRadioButton.setForeground(Color.GRAY);
		pauseRadioButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				pause();
			}
		});
		pauseRadioButton.setPreferredSize(new Dimension(100, 70));

		JPanel backButtonPanel = new JPanel();
		backButtonPanel.setLayout(new BorderLayout());
		backButtonPanel.add(btnNewButton, BorderLayout.EAST);
		backButtonPanel.add(btnBack, BorderLayout.WEST);
		backButtonPanel.setOpaque(false);
		add(backButtonPanel, BorderLayout.SOUTH);

		JPanel panel = new JPanel();
		panel.setLayout(new BorderLayout());
		panel.add(pauseRadioButton, BorderLayout.EAST);
		panel.setOpaque(false);
		add(panel, BorderLayout.NORTH);
	}

	/**
	 * Pauses the game.
	 */
	public void pause() {
		JFrame jf = (JFrame) getRootPane().getParent();
		Container con = jf.getLayeredPane();
		con.setLayout(null);
		PausePanel pausePanel = new PausePanel();
		pausePanel.setBackground(new Color(9, 15, 31, 150));
		pausePanel.setBounds(100, 100, 1000, 500);
		boolean temp = pause;
		pause = !pause;
		boolean temp1 = MainPage.play;
		if (pause) {
			t.stop();
			con.add(pausePanel, new Integer(Integer.MAX_VALUE));
			jf.validate();
			jf.repaint();
			if (temp1 == true) {
				MainPage.sound.getBGMClip().stop();
			}
		} else if (temp && !pause) {
			con.remove(0);
			jf.validate();
			jf.repaint();
			jf.getContentPane().requestFocus();
			t.restart();
			if (temp1 == true) {
				MainPage.sound.getBGMClip().start();
			}
		}
	}

	/**
	 * Creates the paddle objects.
	 */
	public void createPlayers() {
		players.put(0, new Player(MainPage.WIDTH / 2, MainPage.HEIGHT - MainPage.top - MainPage.bottom - 18, level + 1, "S"));
		players.put(1, new Player(MainPage.WIDTH / 2, 0, level + 1, "N"));
		players.get(0).setAI(false);
		if (isMP)
			players.get(1).setAI(false);

		if (noPlayers == 3) {
			players.put(2, new Player(MainPage.WIDTH / 2 - 18, MainPage.HEIGHT / 2, level + 1, "E"));
		} else if (noPlayers == 4) {
			players.put(2, new Player(MainPage.WIDTH - MainPage.left - MainPage.right - 18, MainPage.HEIGHT / 2, level + 1, "E"));
			players.put(3, new Player(0, MainPage.HEIGHT / 2, level + 1, "W"));
		}
	}

	/**
	 * Creates the ball object.
	 */
	public void createBalls() {
		createBall(0, 550, 600, level + 1, level + 1);
	}

	/**
	 * A method for adding a ball to the HashMap.
	 * 
	 * @param id
	 *            The ID associated with the ball.
	 * @param startX
	 *            The starting x-coordinate.
	 * @param startY
	 *            The starting y-coordinate.
	 * @param vectorX
	 *            The starting x-vector.
	 * @param vectorY
	 *            The starting y-vector.
	 */
	public void createBall(int id, int startX, int startY, int vectorX, int vectorY) {
		balls.put(id, new Ball(startX, startY, vectorX, vectorY));
	}

	/**
	 * A method for adding a bonus to the HashMap.
	 * 
	 * @param id
	 *            The ID associated with the bonus.
	 * @param xCoord
	 *            The starting x-coordinate.
	 * @param yCoord
	 *            The starting y-coordinate.
	 * @param xVector
	 *            The starting x-vector.
	 * @param yVector
	 *            The starting y-vector.
	 * @param brickID
	 *            The ID of the brick from which the bonus spawned.
	 */
	public void createBonus(int id, int xCoord, int yCoord, int xVector, int yVector, int brickID) {
		bonus.put(id, new Bonus(xCoord, yCoord, xVector, yVector, bricks.get(brickID).getBonusID()));

	}

	/**
	 * Creates all the horizontal bricks.
	 * 
	 * @param map
	 *            The list of bricks to create.
	 * @param startX
	 *            The starting x-coordinate for the bricks.
	 * @param startY
	 *            The starting y-coordinate for the bricks.
	 * @param width
	 *            The width of the bricks.
	 * @param height
	 *            The height of the bricks.
	 */
	public void createHorizontalBricks(ArrayList<String> map, int startX, int startY, int width, int height) {
		for (int i = 0; i < map.size(); i++) {
			for (int j = 0; j < map.get(i).length(); j++) {
				if (map.get(i).charAt(j) == '1') {
					Brick brick = new Brick(startX + (width * (j + 1)), startY + (height * (i + 1)), width, height);
					bricks.put(bricks.size(), brick);
					int rn = random.nextInt(3);
					if (rn < 1) {
						int rn2 = random.nextInt(6);
						brick.setBonus(true);
						switch (rn2) {
						case 0:
							brick.setBonusID(Bonus.DECREASESPEED);
							break;
						case 1:
							brick.setBonusID(Bonus.WIDTHBONUS);
							break;
						case 2:
							brick.setBonusID(Bonus.SCOREBONUS);
							break;
						case 3:
							brick.setBonusID(Bonus.NWIDTHBONUS);
							break;
						case 4:
							brick.setBonusID(Bonus.LOSESCOREBONUS);
							break;
						case 5:
							brick.setBonusID(Bonus.INCREASESPEED);
						}
					}
				}
			}
		}
	}

	/**
	 * Creates all the vertical bricks.
	 * 
	 * @param map
	 *            The list of bricks to create.
	 * @param startX
	 *            The starting x-coordinate for the bricks.
	 * @param startY
	 *            The starting y-coordinate for the bricks.
	 * @param width
	 *            The width of the bricks.
	 * @param height
	 *            The height of the bricks.
	 */
	public void createVerticalBricks(ArrayList<String> map, int startX, int startY, int width, int height) {
		// bricks.clear();
		for (int i = 0; i < map.size(); i++) {
			for (int j = 0; j < map.get(i).length(); j++) {
				if (map.get(i).charAt(j) == '1') {
					Brick brick = new Brick(startX + ((width + 6) * (j + 1)), startY + (height * (i + 1)), width, height);
					bricks.put(bricks.size(), brick);

				}
			}
		}
	}

	// paint method
	/**
	 * Paints the game objects on-screen
	 */
	public void paint(Graphics g) {
		super.paint(g);
		Graphics2D g2d = (Graphics2D) g;
		g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
		for (Ball b : balls.values()) {
			b.draw(g2d);
		}
		for (Bonus b : bonus.values()) {
			b.draw(g2d);
		}
		for (Brick b : bricks.values()) {
			b.draw(g2d);
		}
		for (Integer key : players.keySet()) {
			Player p = players.get(key);
			p.draw(g2d);
			g2d.setColor(new Color(255, 255, 255, 180));
			g2d.setFont(new Font("", Font.BOLD, 13));
			if (key == 0 || key == 1) {
				g2d.drawString("Score:" + p.getScore(), p.getX() - 100, p.getY() + 12);
			}
			if (key == 2) {
				g2d.drawString("Score:" + p.getScore(), p.getX() - 60, p.getY() - 15);
			}
			if (key == 3) {
				g2d.drawString("Score:" + p.getScore(), p.getX(), p.getY() - 15);
			}
		}
	}

	/**
	 * Runs when the game is over.
	 */
	public void gameOver() {
		if (isMP) {
			Network.sendMessage("GAMEOVER|");
			Network.endGame();
		}
		boolean won = true;
		int myscore = players.get(myID).getScore();
		for (Player p : players.values()) {
			if (p.getScore() > myscore) {
				won = false;
			}
		}
		JFrame jf = (JFrame) getRootPane().getParent();
		Result results = new Result(won, isMP);
		jf.setContentPane(results);
		jf.setVisible(true);
		JPanel imagePanel = (JPanel) jf.getContentPane();
		imagePanel.setOpaque(false);
		for (int i : players.keySet()) {
			if (i == myID) {
				results.addMessage("You : " + players.get(i).getScore());
			} else {
				results.addMessage("Opponent : " + players.get(i).getScore());
			}

		}
	}

	/**
	 * Sets the movement for AI players.
	 */
	public void setAILocation() {
		for (Player player : players.values()) {
			if (player.isAI()) {
				// if there is a ball within half the screen from the player,
				// move towards the ball
				// Doesnt work for multiple balls yet
				boolean following = false;
				String location = player.getLocation();
				for (Ball balli : balls.values()) {
					if (!following && t.isRunning()) {
						if (location == "N") {
							if (balli.getY() < (getParent().getHeight()) / 2) {
								if (balls.get(0).getX() > (player.getX()) + (player.getWidth() / 2)) {
									player.setVector(player.getSpeed());
								} else {
									player.setVector(-player.getSpeed());
								}
								following = true;
							} else {
								player.setVector(0);
							}
						} else if (location == "S") {
							if (balli.getY() > (getParent().getHeight()) / 2) {
								if (balls.get(0).getX() > (player.getX() + (player.getWidth() / 2))) {
									player.setVector(player.getSpeed());
								} else {
									player.setVector(-player.getSpeed());
								}
								following = true;
							} else {
								player.setVector(0);
							}
						} else if (location == "E") {
							if (balli.getX() > (getParent().getWidth()) / 2) {
								if (balls.get(0).getY() > (player.getY()) + (player.getWidth() / 2)) {
									player.setVector(player.getSpeed());
								} else {
									player.setVector(-player.getSpeed());
								}
								following = true;
							} else {
								player.setVector(0);
							}
						} else if (location == "W") {
							if (balli.getX() < (getParent().getWidth()) / 2 && t.isRunning()) {
								if (balls.get(0).getY() > (player.getY()) + (player.getWidth() / 2)) {
									player.setVector(player.getSpeed());
								} else {
									player.setVector(-player.getSpeed());
								}
								following = true;
							} else {
								player.setVector(0);
							}
						}
					}
				}
			}
		}
	}

	/**
	 * Removes bricks that have 0 health and spawns bonuses in networked games when necessary.
	 */
	public void updateBricks() {
		Iterator<Map.Entry<Integer, Brick>> entries = bricks.entrySet().iterator();
		while (entries.hasNext()) {
			Map.Entry<Integer, Brick> entry = entries.next();
			if (!entry.getValue().isAlive()) {
				if (isMP) {
					Brick brick = entry.getValue();
					if (brick.getBonus() == true && balls.get(0).getLastHit() >= 0) {
						// create bonus ball
						Ball ball = balls.get(0);
						int xCoord = brick.getX();
						int yCoord = brick.getY();
						int xVect = 0;
						int yVect = 0;
						if (players.get(ball.getLastHit()).getLocation() == "N") {
							yVect = -1;
						} else if (players.get(ball.getLastHit()).getLocation() == "S") {
							yVect = 1;
						} else if (players.get(ball.getLastHit()).getLocation() == "E") {
							xVect = 1;
						} else if (players.get(ball.getLastHit()).getLocation() == "W") {
							xVect = -1;
						}
						createBonus(bonusID, xCoord, yCoord, xVect, yVect, entry.getKey());
						bonusID++;

						brick.setBonus(false);

					}
				}
				entries.remove();
			}
		}
	}

	/**
	 * Updates the paddle locations and processes key presses.
	 */
	public void updatePlayers() {
		for (Integer key : players.keySet()) {
			Player p = players.get(key);
			if (p.getBonusTime() != 0) {
				p.reduceBonusTime(TICK_RATE);
				if (p.getBonusTime() == 0) {
					p.removeBonus();
				}
			}
			if (p.getLocation() == "N" || p.getLocation() == "S") {
				if (p.isAI()) {
					if (p.getNewX() > 0 && p.getNewX() < getWidth() - p.getWidth()) {
						p.update();
					}
				} else {
					if (key == myID) {
						boolean keyLeft = keys[KeyEvent.VK_LEFT];
						boolean keyRight = keys[KeyEvent.VK_RIGHT];
						boolean keyX = keys[KeyEvent.VK_X];
						if (keyLeft && !keyRight) {
							p.setVector(-p.getSpeed());
						} else if (keyRight && !keyLeft) {
							p.setVector(p.getSpeed());
						} else {
							p.setVector(0);
						}
						if (p.getNewX() > 0 && p.getNewX() < getWidth() - p.getWidth()) {
							p.update();
						}
						if (keyX) {
							if (isMP) {
								for (Integer bkey : bricks.keySet()) {
									Network.sendMessage("BRICKSX|" + bkey);
								}
							}
							bricks.clear();
						}
					}
				}
			} else {
				if (p.isAI()) {
					if (p.getNewY() > 0 && p.getNewY() < getHeight() - p.getHeight()) {
						p.update();
					}
				} else {
					if (key == myID) {
						boolean keyLeft = keys[KeyEvent.VK_LEFT];
						boolean keyRight = keys[KeyEvent.VK_RIGHT];
						if (keyLeft && !keyRight) {
							p.setVector(-p.getSpeed());
						} else if (keyRight && !keyLeft) {
							p.setVector(p.getSpeed());
						} else {
							p.setVector(0);
						}
						if (p.getNewY() > 0 && p.getNewY() < getHeight() - p.getHeight()) {
							p.update();
						}
					}
				}
			}
		}
	}

	/**
	 * Updates the balls' locations and detects and processes collisions.
	 */
	synchronized public void updateBalls() {
		for (Ball balli : balls.values()) {
			// Brick collisions
			if (!isMP || (isMP && myID == 0)) {
				for (Integer brickID : bricks.keySet()) {
					Brick brick = bricks.get(brickID);
					if (brick.getBounds().intersects(balli.getBounds())) {

						int ballEast = balli.getX() + (balli.getDiameter());
						int brickWest = brick.getWestCoord();
						int ballWest = balli.getX();
						int brickEast = brick.getEastCoord();
						if (Math.abs(ballEast - brickWest) < 10 || Math.abs(ballWest - brickEast) < 10) {
							balli.setXVector((-1) * balli.getXVector());
						} else {
							balli.setYVector((-1) * balli.getYVector());
						}
						if (soundplay) {
							sound.brickHitSound();
						}
						brick.hit();
						if (isMP) {
							sendBrickHit(brickID);
						}
						if (balli.getLastHit() >= 0 && !brick.isAlive()) {
							// Gain score when break the brick
							players.get(balli.getLastHit()).gainScore(50);
							// Check is there any bonus of the brick
							if (brick.getBonus() == true && !isMP) {
								// create bonus ball
								int xCoord = brick.getX();
								int yCoord = brick.getY();
								int xVect = 0;
								int yVect = 0;
								if (players.get(balli.getLastHit()).getLocation() == "N") {
									yVect = -1;
								} else if (players.get(balli.getLastHit()).getLocation() == "S") {
									yVect = 1;
								} else if (players.get(balli.getLastHit()).getLocation() == "E") {
									xVect = 1;
								} else if (players.get(balli.getLastHit()).getLocation() == "W") {
									xVect = -1;
								}
								createBonus(bonusID, xCoord, yCoord, xVect, yVect, brickID);
								bonusID++;

								brick.setBonus(false);

							}
						}
						break;
					}
				}
			}

			// Player collisions
			for (Integer key : players.keySet()) {
				Player player = players.get(key);
				if (player.getBounds().intersects(balli.getBounds())) {
					if (soundplay) {
						sound.playerHitSound();
					}
					balli.setLastHit(key);
					int xPlayer = player.getX();
					int yPlayer = player.getY();
					int wPlayer = player.getWidth();
					// integer division rounds down
					// divisor determines how much of the ends are 'reflective'
					int endsWidth = wPlayer / 4;
					// only reflects in x if its going in the opposite direction
					if (player.getLocation() == "E" || player.getLocation() == "W") {
						if ((player.isAI()) && (random.nextBoolean())) {
							int n = random.nextInt(2);
							n = n - 1;
							if (n != 0) {
								balli.setYVector((n) * balli.getYVector());
							}
						} else {
							if ((balli.getY() < (yPlayer + endsWidth)) && (balli.getYVector() > 0)) {
								balli.setYVector((-1) * balli.getYVector());
							} else if ((balli.getY() > ((yPlayer + wPlayer) - endsWidth)) && (balli.getYVector() < 0)) {
								balli.setYVector((-1) * balli.getYVector());
							}
						}
						balli.setXVector((-1) * balli.getXVector());
					} else {
						if ((player.isAI()) && (random.nextBoolean())) {
							int n = random.nextInt(2);
							n = n - 1;
							if (n != 0) {
								balli.setXVector((n) * balli.getXVector());
							}
						} else {
							if ((balli.getX() < (xPlayer + endsWidth)) && (balli.getXVector() > 0)) {
								balli.setXVector((-1) * balli.getXVector());
							} else if ((balli.getX() > ((xPlayer + wPlayer) - endsWidth)) && (balli.getXVector() < 0)) {
								balli.setXVector((-1) * balli.getXVector());
							}
						}
						balli.setYVector((-1) * balli.getYVector());
					}
					break;
				}

			}
			// Ball Collisions with each other
			for (Ball ballx : balls.values()) {
				if (balli != ballx) {
					if (balli.getBounds().intersects(ballx.getBounds())) {
						if (balli.getXVector() != ballx.getXVector()) {
							balli.setXVector((-1) * balli.getXVector());
							ballx.setXVector((-1) * ballx.getXVector());
						}
						if (balli.getYVector() != ballx.getYVector()) {
							balli.setYVector((-1) * balli.getYVector());
							ballx.setYVector((-1) * ballx.getYVector());
						}
					}
				}
			}

			// Meets side of map
			if (balli.getX() + balli.getXVector() > getWidth() - balli.getDiameter() - 5) {
				// Find East Player
				for (Integer key : players.keySet()) {
					Player player = players.get(key);
					if (player.getLocation().equals("E")) {
						// Deduct 2 points when player misses the ball in his
						// goals.
						player.gainScore(-20);
					}
				}
				balli.setXVector(-Math.abs(balli.getXVector()));
			} else if (balli.getX() + balli.getXVector() < 5) {
				// Find West Player
				for (Integer key : players.keySet()) {
					Player player = players.get(key);
					if (player.getLocation().equals("W")) {
						// Deduct 2 points when player misses the ball in his
						// goals.
						player.gainScore(-20);
					}
				}
				balli.setXVector(Math.abs(balli.getXVector()));
			} else if (balli.getY() + balli.getYVector() < 5) {
				// If Meets North Map boundary
				// Find North Player
				for (Integer key : players.keySet()) {
					Player player = players.get(key);
					if (player.getLocation().equals("N")) {
						// Deduct 2 points when player misses the ball in his
						// goals.

						player.gainScore(-20);
					}
				}
				balli.setYVector(Math.abs(balli.getYVector()));
			} else if (balli.getY() + balli.getYVector() > getHeight() - balli.getDiameter() - 5) {
				// If Meets South Map boundary
				// Find South Player
				for (Integer key : players.keySet()) {
					Player player = players.get(key);
					if (player.getLocation().equals("S")) {
						// Deduct 2 points when player misses the ball in his
						// goals.
						player.gainScore(-20);
					}
				}
				balli.setYVector(-Math.abs(balli.getYVector()));
			}
			balli.update();
		}
	}

	/**
	 * Applies a bonus to a player.
	 * 
	 * @param id
	 *            The ID of the player.
	 * @param bonusId
	 *            The ID of the bonus.
	 */
	public void applyBonus(int id, int bonusId) {
		int bonusType = bonus.get(bonusId).getBonusType();
		if (bonusType == 1 || bonusType == 2 || bonusType == 5) {
			sound.goodBonusSound();
			players.get(id).setBonus(bonusId);
			players.get(id).applyBonus(bonus.get(bonusId).getBonusType());
		}

		else if (bonusType == 3 || bonusType == 4 || bonusType == 6) {
			sound.badBonusSound();
			players.get(id).setBonus(bonusId);
			players.get(id).applyBonus(bonus.get(bonusId).getBonusType());
		}

	}

	/**
	 * Updates the bonuses that are currently present in-game.
	 */
	synchronized public void updateBonuses() {
		for (Iterator<Map.Entry<Integer, Bonus>> bonuskey = bonus.entrySet().iterator(); bonuskey.hasNext();) {
			Entry<Integer, Bonus> entry = bonuskey.next();
			for (Integer pkey : players.keySet()) {
				Player p = players.get(pkey);
				if (p.getBounds().intersects(entry.getValue().getBounds())) {
					applyBonus(pkey, entry.getKey());
					bonuskey.remove();
				}
			}

			entry.getValue().update();
		}

	}

	/**
	 * Methods to update the game state. Runs every 10ms.
	 */
	public void actionPerformed(ActionEvent arg0) {
		if (bricks.isEmpty()) {
			t.stop();
			gameOver();
		}
		updateBalls();
		updateBonuses();
		updatePlayers();
		setAILocation();
		updateBricks();
		repaint();
	}

	/**
	 * Records key presses.
	 */
	@Override
	public void keyPressed(KeyEvent e) {
		keys[e.getKeyCode()] = true;
		boolean keyP = keys[KeyEvent.VK_P];
		if (keyP) {
			pause();
		}
	}

	/**
	 * Records key releases.
	 */
	@Override
	public void keyReleased(KeyEvent e) {
		keys[e.getKeyCode()] = false;
	}

	/**
	 * Does nothing.
	 */
	@Override
	public void keyTyped(KeyEvent e) {
	}

}
