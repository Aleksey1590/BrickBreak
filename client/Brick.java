package client;

import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.Rectangle;
import java.awt.geom.Area;

import javax.swing.ImageIcon;

/**
 * A class representing a brick object.
 */
public class Brick {
	private int x = 10;
	private int y = 10;
	private int width = 50;
	private int height = 25;
	private ImageIcon brick = new ImageIcon(getClass().getResource("/client/resource/brick.png"));
	private ImageIcon health1 = new ImageIcon(getClass().getResource("/client/resource/health1.png"));
	private ImageIcon bonus1 = new ImageIcon(getClass().getResource("/client/resource/bonus.png"));
	private int health = 2;
	private boolean bonus = false;
	private int bonusID = 0;

	/**
	 * Creates a new brick object.
	 * 
	 * @param x
	 *            the x coordinate of the brick
	 * @param y
	 *            the y coordinate of the brick
	 * @param w
	 *            the width of the brick
	 * @param h
	 *            the height of the brick
	 */
	public Brick(int xCoord, int yCoord, int w, int h) {
		x = xCoord;
		y = yCoord;
		width = w;
		height = h;
	}

	// get methods
	/**
	 * @return the x coordinate of the brick
	 */
	public int getX() {
		return x;
	}

	/**
	 * @return the y coordinate of the brick
	 */
	public int getY() {
		return y;
	}

	/**
	 * @return the width of the brick
	 */
	public int getWidth() {
		return width;
	}

	/**
	 * @return the ID of the bonus contained in the brick
	 */
	public boolean getBonus() {
		return bonus;
	}

	/**
	 * @return the ID of the bonus contained in the brick
	 */
	public int getBonusID() {
		return bonusID;
	}

	/**
	 * @return the height of the brick
	 */
	public int getHeight() {
		return height;
	}

	/**
	 * @return the health of the brick
	 */
	public int getHealth() {
		return health;
	}

	/**
	 * @return whether or not the brick is alive
	 */
	public boolean isAlive() {
		return (health > 0);
	}

	/**
	 * @return the rectangular boundary of the brick
	 */
	public Rectangle getBounds() {
		return new Rectangle(x, y, width, height);
	}
	/**
	 * @return the rectangular area of the brick
	 */
	public Area getBoundsArea() {
		return new Area(getBounds());
	}

	/**
	 * @return the lower boundary of the brick
	 */
	public int getBottomYCoord() {
		return y + (height / 2);
	}

	/**
	 * @return the upper boundary of the brick
	 */
	public int getUpperYCoord() {
		return y;
	}

	/**
	 * @return the Western boundary of the brick
	 */
	public int getWestCoord() {
		return (int) x;
	}

	/**
	 * @return the Eastern boundary of the brick
	 */
	public int getEastCoord() {
		return (int) (x + width);
	}

	// set methods
	/**
	 * Sets a new x coordinate for the brick
	 * 
	 * @param newX
	 *            the new brick x coordinate
	 */
	public void setX(int newX) {
		x = newX;
	}

	/**
	 * Sets a new y coordinate for the brick
	 * 
	 * @param newY
	 *            the new brick y coordinate
	 */
	public void setY(int newY) {
		y = newY;
	}

	/**
	 * Sets a new width for the brick
	 * 
	 * @param newWidth
	 *            the new brick width
	 */
	public void setWidth(int newWidth) {
		width = newWidth;
	}

	/**
	 * Sets a new height for the brick
	 * 
	 * @param newHeight
	 *            the new brick height
	 */
	public void setHeight(int newHeight) {
		height = newHeight;
	}

	/**
	 * Sets a new bonus ID to be contained in the brick
	 * 
	 * @param b
	 *            the new brick bonus
	 */
	public void setBonus(boolean b) {
		bonus = b;
	}

	/**
	 * Sets a new bonus ID to be contained in the brick
	 * 
	 * @param i
	 *            the new brick bonus
	 */
	public void setBonusID(int i) {
		bonusID = i;
		if (i != 0) {
			bonus = true;
		} else {
			bonus = false;
		}
	}

	/**
	 * Sets a new health for the brick
	 * 
	 * @param newHealth
	 *            the new brick health
	 */
	public void setHealth(int newHealth) {
		health = newHealth;
	}

	/**
	 * Decrements the brick's health
	 */
	public void hit() {
		health--;
	}

	/**
	 * Draws the brick to the game panel
	 * 
	 * @param g
	 *            the game panel's graphics object
	 */
	public void draw(Graphics2D g) {
		Image BrickImage = brick.getImage();
		Image Health1Image = health1.getImage();
		Image BonusImage = bonus1.getImage();
		if (bonus != false && health == 0) {
			g.drawImage(BonusImage, x, y, width, height, null);
		} else if (health == 2) {
			g.drawImage(BrickImage, x, y, width, height, null);
		} else {
			g.drawImage(Health1Image, x, y, width, height, null);
		}
	}
}
