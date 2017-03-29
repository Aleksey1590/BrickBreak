package client;

import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.Rectangle;
import java.awt.geom.Area;
import java.awt.geom.Ellipse2D;

/**
 * A class representing a ball object in the game.
 */
public class Ball {
	private int x;
	private int y;
	private int xVector;
	private int yVector;
	private Ellipse2D ballGraphic;
	private Color color;
	private int diameter = 30;
	private int lastHit = -1;

	/**
	 * Creates a new ball object.
	 * 
	 * @param xC
	 *            the starting x coordinate of the ball
	 * @param yC
	 *            the starting y coordinate of the ball
	 * @param xV
	 *            the starting x vector of the ball
	 * @param yV
	 *            the starting y vector of the ball
	 */
	public Ball(int xC, int yC, int xV, int yV) {
		x = xC;
		y = yC;
		xVector = xV;
		yVector = yV;
		ballGraphic = new Ellipse2D.Double(x, y, diameter, diameter);
		color = new Color(247, 169, 169, 180);
	}

	// get methods
	/**
	 * @return the x coordinate of the ball
	 */
	public int getX() {
		return (int) x;
	}

	/**
	 * @return the y coordinate of the ball
	 */
	public int getY() {
		return (int) y;
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
		return (int) diameter;
	}

	/**
	 * @return the rectangular boundary of the ball
	 */
	public Rectangle getBounds() {
		return new Rectangle(x, y, diameter, diameter);
	}

	/**
	 * @return the rectangular boundary area of the ball
	 */

	public Area getBoundsArea() {
		return new Area(ballGraphic);
	}

	/**
	 * @return the ID of the last player hit by the ball
	 */
	public int getLastHit() {
		return lastHit;
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
	 * Sets the ID of the player last hit by the ball
	 * 
	 * @param i
	 *            the ID of the player last hit by the ball
	 */
	public void setLastHit(int i) {
		lastHit = i;
	}

	/**
	 * Updates the location of the ball based on its velocity
	 */
	public void update() {
		x += xVector;
		y += yVector;
	}

	/**
	 * Draws the ball to the game panel
	 * 
	 * @param g
	 *            the game panel's graphics object
	 */
	public void draw(Graphics2D g) {
		g.setColor(color);
		ballGraphic.setFrame(x, y, diameter, diameter);
		g.fill(ballGraphic);
	}

}
