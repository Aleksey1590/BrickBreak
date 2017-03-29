package client;

import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.Shape;
import java.awt.geom.AffineTransform;
import java.awt.geom.Rectangle2D;

import javax.swing.ImageIcon;

/**
 * A class representing a player in the (triangle) game and their paddle object.
 */
public class TrianglePlayer extends Player {
	double angle = 0;
	int i = 0;
	private ImageIcon paddle = new ImageIcon(getClass().getResource("/client/resource/paddle.png"));

	/**
	 * Creates a triangle player object
	 * 
	 * @param playerSpeed
	 *            the speed of the player
	 * @param location
	 *            the location of the player
	 * @param x
	 *            the x-coord of the player
	 * @param y
	 *           the y-coord of the player
	 */
	public TrianglePlayer(int playerSpeed, String location, int x, int y) {
		super(x, y, playerSpeed, location);
		if (location == "S") {
			angle = Math.toRadians(15);
		} else if (location == "NW") {
			angle = Math.toRadians(135);
		} else if (location == "NE") {
			angle = Math.toRadians(75);
		}
	}
	/**
	 * @return the rotated rectangular boundary of the player
	 */
	public Shape getBounds() {
		AffineTransform transform = new AffineTransform();
		transform.rotate(angle, getX(), getY());
		Rectangle2D rect = new Rectangle2D.Double(getX(), getY(), getWidth(), getHeight());
		Shape rotatedRect = transform.createTransformedShape(rect);
		return rotatedRect;
	}

	/**
	 * Updates the location of the player based on its direction and location
	 */
	@Override
	public void update() {
		// these are not flexible
		// setting the unchanging vector to move along the line, using the angle that it needs to travel along
		if (getLocation() == "S") {
			setX(getX() + getVector());
			setY((int) ((MainPage.HEIGHT - (MainPage.HEIGHT * Math.tan(angle))) + (getX() * Math.tan(angle))) - 68);
		} else if (getLocation() == "NW") {
			setY(getY() + getVector());
			setX((int) (MainPage.HEIGHT / 4 + ((MainPage.HEIGHT - (MainPage.HEIGHT * Math.tan(Math.toRadians(15)))) - (getY() * Math.tan(Math.toRadians(45)))) + 30));
		} else if (getLocation() == "NE") {
			setY(getY() + getVector());
			setX((int) ((MainPage.HEIGHT / 4 + (MainPage.HEIGHT - (MainPage.HEIGHT * Math.tan(Math.toRadians(15)))) + (getY() * Math.tan(Math.toRadians(15))))));
		}
	}

	/**
	 * Draws the player to the game panel
	 * 
	 * @param g
	 *            the game panel's graphics object
	 */
	@Override
	public void draw(Graphics2D g) {
		Image PlayerImage = paddle.getImage();
		AffineTransform transform = new AffineTransform();
		transform.rotate(angle, getX(), getY());
		g.setTransform(transform);
		g.drawImage(PlayerImage, getX(), getY(), getWidth(), getHeight(), null);
		// reset transformation after use, prevents bugs from graphics re-use
		transform.rotate(-angle, getX(), getY());
		g.setTransform(transform);
	}

}
