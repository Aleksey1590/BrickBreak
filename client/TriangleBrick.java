package client;

import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.Polygon;
import java.awt.geom.Area;
import java.awt.geom.Path2D;
import java.awt.geom.Point2D;
import java.util.concurrent.ConcurrentHashMap;

/**
 * A class representing a brick object in the triangle game.
 */
public class TriangleBrick extends Brick {
	private Point2D p1, p2, p3;
	private Point2D centerPoint;
	private Color color1 = new Color(255, 255, 220, 255);
	private Color color2 = new Color(255, 255, 220, 255);
	private Color brickWithBonus = new Color(237, 82, 7, 255);

	/**
	 * Creates a new triangle brick object.
	 * 
	 * @param centerX
	 *            the x coordinate of the brick
	 * @param centerY
	 *            the y coordinate of the brick
	 * @param height
	 *            the height of the brick
	 */
	public TriangleBrick(double centerX, double centerY, int height) {
		super((int) centerX, (int) centerY, height, height);
		centerPoint = new Point2D.Double(centerX, centerY);
		double x = height / Math.sin(Math.toRadians(60));
		double pToC = x / (2 * Math.sin(Math.toRadians(60)));
		p1 = new Point2D.Double(centerPoint.getX(), centerPoint.getY() - pToC);
		p2 = new Point2D.Double(centerPoint.getX() + (x / 2), centerPoint.getY() + height - pToC);
		p3 = new Point2D.Double(centerPoint.getX() - (x / 2), centerPoint.getY() + height - pToC);
		setHeight(height);
	}
	// get methods
	/**
	 * @return the first corner point brick
	 */
	public Point2D getP1() {
		return p1;
	}
	/**
	 * @return the second corner point brick
	 */
	public Point2D getP2() {
		return p2;
	}
	/**
	 * @return the third corner point brick
	 */
	public Point2D getP3() {
		return p3;
	}
	/**
	 * @return the triangular area of the brick
	 */
	@Override
	public Area getBoundsArea() {
		Polygon shape = new Polygon();
		shape.addPoint((int) p1.getX(), (int) p1.getY());
		shape.addPoint((int) p2.getX(), (int) p2.getY());
		shape.addPoint((int) p3.getX(), (int) p3.getY());
		return new Area(shape);
	}

	/**
	 * Rotate the bricks to the correct angle
	 * 
	 * @param bricks
	 *            the hashmap of all the bricks 
	  * @param theta
	 *            the angle of rotation
	  * @param rotatePoint
	 *            the point at which to rotate the triangle about            
	 */
	public static void bricksRotate(ConcurrentHashMap<Integer, Brick> bricks, double theta, Point2D rotatePoint) {
		theta = theta % 360;
		for (Brick brick : bricks.values()) {
			if (brick instanceof TriangleBrick) {
				TriangleBrick b = (TriangleBrick) brick;
				b.p1 = rotatePoint(b.p1, theta, rotatePoint);
				b.p2 = rotatePoint(b.p2, theta, rotatePoint);
				b.p3 = rotatePoint(b.p3, theta, rotatePoint);
				b.centerPoint = rotatePoint(b.centerPoint, theta, rotatePoint);
				b.setX((int) b.centerPoint.getX());
				b.setY((int) b.centerPoint.getY());
			}
		}
	}

	/**
	 * Find the correct point to rotate the bricks about
	 * 
	 * @param p
	 *            the point to be rotated
	  * @param theta
	 *            the angle of rotation
	  * @param centerPoint
	 *            the center point of the triangle
	 */
	public static Point2D rotatePoint(Point2D p, double theta, Point2D centerPoint) {
		double newX = centerPoint.getX() + (p.getX() - centerPoint.getX()) * Math.cos(Math.toRadians(theta)) - (p.getY() - centerPoint.getY()) * Math.sin(Math.toRadians(theta));
		double newY = centerPoint.getY() + (p.getX() - centerPoint.getX()) * Math.sin(Math.toRadians(theta)) + (p.getY() - centerPoint.getY()) * Math.cos(Math.toRadians(theta));
		return new Point2D.Double(newX, newY);
	}

	/**
	 * Draws the brick to the game panel
	 * 
	 * @param g
	 *            the game panel's graphics object
	 */
	@Override
	public void draw(Graphics2D g) {
		Path2D triPath = new Path2D.Double();
		if (getHeight() == 0) {
			g.setColor(brickWithBonus);
		} else if (getHealth() == 2) {
			g.setColor(color1);
		} else {
			g.setColor(color2);
		}
		triPath.moveTo(p1.getX(), p1.getY());
		triPath.lineTo(p2.getX(), p2.getY());
		triPath.lineTo(p3.getX(), p3.getY());
		triPath.closePath();
		g.fill(triPath);
	}
}