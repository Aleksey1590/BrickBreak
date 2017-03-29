package client;

import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.Rectangle;
import java.awt.Shape;

import javax.swing.ImageIcon;

/**
 * A class representing a player in the game and their paddle object.
 */
public class Player {
	private int x;
	private int y;
	private int width = 100;
	private int height = 18;
	private int vector;
	private int speed = 2;
	private int score = 1000;
	private int bonus;
	private int bonusTime;
	private boolean isAI = true;
	private String location;
	private ImageIcon paddle = new ImageIcon(getClass().getResource("/client/resource/paddle.png"));
	private ImageIcon paddle2 = new ImageIcon(getClass().getResource("/client/resource/paddle2.png"));
	public static final int WIDTHBONUS = 1;
	public static final int SCOREBONUS = 2;
	public static final int NWIDTHBONUS = 3;
	public static final int LOSESCOREBONUS = 4;
	public static final int INCREASESPEED = 5;
	public static final int DECREASESPEED = 6;
	public static final int WIDTHBONUSTIME = 2500;

	/**
	 * Creates a player object
	 * 
	 * @param xC
	 *            the x coordinate of the player
	 * @param yC
	 *            the y coordinate of the player
	 * @param s
	 *            the speed of the player
	 * @param l
	 *            the location of the player
	 */
	public Player(int xC, int yC, int s, String l) {
		speed = s;
		location = l;
		x = xC;
		y = yC;
		if (location == "W" || location == "E") {
			width = 18;
			height = 100;
		}
	}

	// get methods
	/**
	 * @return the x coordinate of the player
	 */
	public int getX() {
		return x;
	}

	/**
	 * @return the y coordinate of the player
	 */
	public int getY() {
		return y;
	}

	/**
	 * @return the width of the player
	 */
	public int getWidth() {
		return width;
	}

	/**
	 * @return the height of the player
	 */
	public int getHeight() {
		return height;
	}

	/**
	 * @return the vector of the player
	 */
	public int getVector() {
		return vector;
	}

	/**
	 * @return the speed of the player
	 */
	public int getSpeed() {
		return speed;
	}

	/**
	 * @return the score of the player
	 */
	public int getScore() {
		return score;
	}

	/**
	 * @return the bonus status of the player
	 */
	public int getBonus() {
		return bonus;
	}

	/**
	 * @return the remaining bonus time of the player
	 */
	public int getBonusTime() {
		return bonusTime;
	}

	/**
	 * @return whether or not the player is an AI
	 */
	public boolean isAI() {
		return isAI;
	}

	/**
	 * @return the location of the player
	 */
	public String getLocation() {
		return location;
	}

	/**
	 * @return the rectangular boundary of the player
	 */
	public Shape getBounds() {
		return new Rectangle(x, y, width, height);
	}

	/**
	 * @return the x coordinate of the player after the next update
	 */
	public int getNewX() {
		return (x + vector);
	}

	/**
	 * @return the y coordinate of the player after the next update
	 */
	public int getNewY() {
		return (y + vector);
	}

	// set methods
	/**
	 * Sets a new x coordinate for the player
	 * 
	 * @param newXCoord
	 *            the new x coordinate of the player
	 */
	public void setX(int newXCoord) {
		x = newXCoord;
	}

	/**
	 * Sets a new y coordinate for the player
	 * 
	 * @param newYCoord
	 *            the new y coordinate of the player
	 */
	public void setY(int newYCoord) {
		y = newYCoord;
	}

	/**
	 * Sets a new width for the player
	 * 
	 * @param newWidth
	 *            the new width of the player
	 */
	public void setWidth(int newWidth) {
		width = newWidth;
	}

	/**
	 * Sets a new height for the player
	 * 
	 * @param newHeight
	 *            the new height of the player
	 */
	public void setHeight(int newHeight) {
		height = newHeight;
	}

	/**
	 * Sets a new vector for the player
	 * 
	 * @param v
	 *            the new vector of the player
	 */
	public void setVector(int v) {
		vector = v;
	}

	/**
	 * Sets a new speed for the player
	 * 
	 * @param newSpeed
	 *            the new speed of the player
	 */
	public void setSpeed(int newSpeed) {
		if ((newSpeed > 0) && (newSpeed <= 8)) {
			speed = newSpeed;
		}
	}

	/**
	 * Sets a new score for the player
	 * 
	 * @param s
	 *            the new score of the player
	 */
	public void setScore(int s) {
		score = s;
	}

	/**
	 * Increments the player's score
	 * 
	 * @param s
	 *            the increment to be given, before bonus affects are calculated
	 */
	public void gainScore(int s) {
		if ((score + s) >= 0) {
			score += s;
		}
	}

	/**
	 * Sets a new bonus status for the player
	 * 
	 * @param b
	 *            the new bonus status
	 */
	public void setBonus(int b) {
		bonus = b;
	}

	/**
	 * Sets a new bonus remaining time for the player
	 * 
	 * @param t
	 *            the time remaining on the player's bonus
	 */
	public void setBonusTime(int t) {
		bonusTime = t;
	}

	/**
	 * Decreases the player's bonus time
	 * 
	 * @param t
	 *            the amount to decrease the bonus time by
	 */
	public void reduceBonusTime(int t) {
		bonusTime -= t;
	}

	/**
	 * Sets whether or not the player is AI controlled
	 * 
	 * @param b
	 *            whether the player is AI controlled
	 */
	public void setAI(boolean b) {
		isAI = b;
		if (b == false) {
			speed = 6;
		}
	}

	/**
	 * Sets the compass location of the player (N, S, E or W)
	 * 
	 * @param l
	 *            the compass location of the player
	 */
	public void setLocation(String l) {
		location = l;
	}

	/**
	 * Applies the current bonus to the player
	 */
	public void applyBonus(int b) {
		if (b == WIDTHBONUS && (location.equals("N") || location.equals("S"))) {
			if (x + 200 > MainPage.WIDTH) {
				x = 600;
			}
			width = 200;
		} else if (b == WIDTHBONUS && (location.equals("E") || location.equals("W"))) {
			if (y + 200 > MainPage.HEIGHT) {
				y = 600;
			}
			height = 200;

		} else if (b == SCOREBONUS) {
			gainScore(5);
		} else if (b == LOSESCOREBONUS) {
			gainScore(-5);
		} else if (b == NWIDTHBONUS && (location.equals("N") || location.equals("S"))) {
			if (width == 200) {
				if (x + 200 > MainPage.WIDTH) {
					x = 600;
				}
				width = 100;
			} else {
				if (x + 200 > MainPage.WIDTH) {
					x = 600;
				}
				width = 50;
			}
		} else if (b == NWIDTHBONUS && (location.equals("E") || location.equals("W"))) {
			if (height == 200) {
				if (y + 200 > MainPage.WIDTH) {
					y = 600;
				}
				height = 100;
			}

			else {
				if (y + 200 > MainPage.WIDTH) {
					y = 600;
				}
				height = 50;
			}
		} else if (b == INCREASESPEED) {
			// System.out.println("Increase speed: " + speed);
			setSpeed(speed + 2);
		} else {
			// System.out.println("Decrease speed: " + speed);
			setSpeed(speed - 2);

		}
	}

	/**
	 * Removes the current bonus from the player
	 */
	public void removeBonus() {
		if (bonus == Player.WIDTHBONUS && (location.equals("N") || location.equals("S"))) {
			width = 100;
		} else if (bonus == Player.WIDTHBONUS && (location.equals("E") || location.equals("W"))) {
			height = 100;
		}
		bonus = 0;
	}

	/**
	 * Updates the location of the player based on its direction and location
	 */
	public void update() {
		if (location == "N" || location == "S") {
			x += vector;
		} else {
			y += vector;
		}
	}

	/**
	 * Draws the player to the game panel
	 * 
	 * @param g
	 *            the game panel's graphics object
	 */
	public void draw(Graphics2D g) {
		Image PlayerImage = paddle.getImage();
		Image PlayerImage2 = paddle2.getImage();
		if (location == "N" || location == "S") {
			g.drawImage(PlayerImage, x, y, width, height, null);
		} else {
			g.drawImage(PlayerImage2, x, y, width, height, null);
		}
	}

}