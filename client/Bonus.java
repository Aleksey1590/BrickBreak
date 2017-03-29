package client;

import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.Rectangle;
import java.awt.geom.Ellipse2D;

import javax.swing.ImageIcon;

/**
 * A class representing a bonus object.
 */
public class Bonus {
	private int x;
	private int y;
	private int xVector;
	private int yVector;
	private Ellipse2D ballGraphic;
	private Image image;
	private int diameter = 30;
	public static final int WIDTHBONUS = 1;
	public static final int SCOREBONUS = 2;
	public static final int NWIDTHBONUS = 3;
	public static final int LOSESCOREBONUS = 4;
	public static final int INCREASESPEED = 5;
	public static final int DECREASESPEED = 6;
	public int bonus;
	private ImageIcon increaseImage = new ImageIcon(getClass().getResource("/client/resource/increase.png"));
	private ImageIcon decreaseImage = new ImageIcon(getClass().getResource("/client/resource/decrease.png"));
	private ImageIcon gainImage = new ImageIcon(getClass().getResource("/client/resource/gain.png"));
	private ImageIcon lossImage = new ImageIcon(getClass().getResource("/client/resource/loss.png"));
	private ImageIcon speedUpImage = new ImageIcon(getClass().getResource("/client/resource/speedUp.png"));
	private ImageIcon speedDownImage = new ImageIcon(getClass().getResource("/client/resource/speedDown.png"));

	/**
	 * Creates a ball object
	 * 
	 * @param xC
	 *            the x coordinate of the ball
	 * @param yC
	 *            the y coordinate of the ball
	 * @param xV
	 *            the x vector of the ball
	 * @param yV
	 *            the y vector of the ball
	 * @param bonusID
	 *            the ID of the bonus
	 */
	public Bonus(int xC, int yC, int xV, int yV, int bonusID) {
		x = xC;
		y = yC;
		xVector = xV;
		yVector = yV;
		ballGraphic = new Ellipse2D.Double(x, y, diameter, diameter);
		bonus = bonusID;
		switch (bonus) {
		case WIDTHBONUS:
			image = increaseImage.getImage();
			break;
		case SCOREBONUS:
			image = gainImage.getImage();
			break;
		case NWIDTHBONUS:
			image = decreaseImage.getImage();
			break;
		case LOSESCOREBONUS:
			image = lossImage.getImage();
			break;
		case INCREASESPEED:
			image = speedUpImage.getImage();
			break;
		case DECREASESPEED:
			image = speedDownImage.getImage();
			break;
		}
	}

	// get methods
	/**
	 * @return the x coordinate of the ball
	 */
	public int getX() {
		return x;
	}

	/**
	 * @return the y coordinate of the ball
	 */
	public int getY() {
		return y;
	}

	/**
	 * @return the x vector of the ball
	 */
	public int getXVector() {
		return xVector;
	}

	/**
	 * @return the y vector of the ball
	 */
	public int getYVector() {
		return yVector;
	}

	/**
	 * @return the diameter of the ball
	 */
	public int getDiameter() {
		return diameter;
	}

	/**
	 * @return the rectangular boundary of the ball
	 */
	public Rectangle getBounds() {
		return new Rectangle(x, y, diameter, diameter);
	}

	// set methods
	/**
	 * Sets a new x coordinate for the ball
	 * 
	 * @param newX
	 *            the new x coordinate of the ball
	 */
	public void setX(int newX) {
		x = newX;
	}

	/**
	 * Sets a new y coordinate for the ball
	 * 
	 * @param newY
	 *            the new y coordinate of the ball
	 */
	public void setY(int newY) {
		y = newY;
	}

	/**
	 * Sets a new x vector for the ball
	 * 
	 * @param newXV
	 *            the new x vector of the ball
	 */
	public void setXVector(int newXV) {
		xVector = newXV;
	}

	/**
	 * Sets a new y vector for the ball
	 * 
	 * @param newYV
	 *            the new y vector of the ball
	 */
	public void setYVector(int newYV) {
		yVector = newYV;
	}

	/**
	 * Sets a new diameter for the 2D ball
	 * 
	 * @param newDiameter
	 *            the new diameter of the ball
	 */
	public void setDiameter(int newDiameter) {
		diameter = newDiameter;
	}

	/**
	 * Updates the x and y coordinates of a falling bonus based on the bonus's vectors.
	 */
	public void update() {
		x += xVector;
		y += yVector;
	}

	public int getBonusType() {
		return bonus;
	}

	/**
	 * Draws the ball to the game panel
	 * 
	 * @param g
	 *            the game panel's graphics object
	 */
	public void draw(Graphics2D g) {
		// g.setColor(color);
		ballGraphic.setFrame(x, y, diameter, diameter);
		g.fill(ballGraphic);
		g.drawImage(image, x, y, diameter, diameter, null);
	}
}