package client;

import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Polygon;
import java.awt.geom.Area;
import java.awt.geom.Line2D;

/**
 * A class for the triangle game object, which displays the game on-screen, holds game-data performs all calculations and processes user input.
 */
@SuppressWarnings("serial")
public class TriangleGame extends Game {
	private Line2D SouthLine;
	private Line2D NorthEastLine;
	private Line2D NorthWestLine;
	private int collisionTicks = 3000;

	/**
	 * Creates a new game (triangle map - 3 players)
	 * 
	 * @param mp
	 *            Whether or not the game is multiplayer.
	 * @param player
	 *            The number of players.
	 * @param nolevel
	 *			  The level (the difficulty of the game).
	 *    
	*/
	public TriangleGame(boolean mp, int player, int nolevel) {
		super(mp, player, nolevel);
	}

	/**
	 * Creates the paddle objects.
	 */
	@Override
	public void createPlayers() {
		createLines();
		TrianglePlayer player = new TrianglePlayer(6, "S", getXMidpoint(SouthLine), getYMidpoint(SouthLine) - 18);
		TrianglePlayer aiplayer1 = new TrianglePlayer(getLevel() + 1, "NW", getXMidpoint(NorthWestLine), getYMidpoint(NorthWestLine));
		TrianglePlayer aiplayer2 = new TrianglePlayer(getLevel() + 1, "NE", getXMidpoint(NorthEastLine), getYMidpoint(NorthEastLine));
		players.put(0, player);
		players.get(0).setAI(false);
		players.put(1, aiplayer1);
		players.put(2, aiplayer2);
	}

	/**
	 * Creates the ball object.
	 */
	@Override
	public void createBalls() {
		balls.put(0, new Ball(500, 300, 2, 2));
	}

	/**
	 * Creates the brick object.
	 */
	@Override
	public void addBricks() {
		createBricks(7, 4);
	}

	/**
	 * Creates all bricks.
	 * 
	 * @param numOfRows
	 *            The number of rows of bricks to create
	 * @param levels
	 * 			The number of levels to create
	 */
	public void createBricks(int numOfRows, int levels) {
		int rotateAngle = 15;
		int height = 50;
		double padding = 500 - (numOfRows * height) / 2;
		double startX = MainPage.WIDTH / 2, startY = padding;
		TriangleBrick brickStart = null;

		if (!bricks.isEmpty()) {
			bricks.clear();
		}

		for (int i = 0; i < numOfRows; i++) {
			brickStart = new TriangleBrick(startX, startY, 50);
			bricks.put(bricks.size(), brickStart);
			double xDiff = brickStart.getP3().getX() - brickStart.getP2().getX();
			for (int j = 1; j <= i; j++) {
				TriangleBrick brick = new TriangleBrick(startX + xDiff * j, startY, 50);
				bricks.put(bricks.size(), brick);
			}
			startY += height;
			startX = brickStart.getP2().getX();
		}
		TriangleBrick.bricksRotate(bricks, rotateAngle, brickStart.getP2());
	}

	// paint method
		/**
		 * Paints the game objects on-screen
		 */
	@Override
	public void paint(Graphics g) {
		Graphics2D g2d = (Graphics2D) g;
		super.paint(g2d);
		g2d.draw(SouthLine);
		g2d.draw(NorthEastLine);
		g2d.draw(NorthWestLine);
	}

	/**
	 * Sets the movement for AI players.
	 */
	@Override
	public void setAILocation() {
		for (Player player : players.values()) {
			if (player.isAI()) {
				boolean following = false;
				String location = player.getLocation();
				if (!following) {
					if (location == "NW") {
						if (balls.get(0).getY() > (player.getY()) + (player.getWidth() / 2)) {
							player.setVector(player.getSpeed());
						} else {
							player.setVector(-player.getSpeed());
						}
					} else if (location == "S") {
						if (balls.get(0).getX() > (player.getX() + (player.getWidth() / 2))) {
							player.setVector(player.getSpeed());
						} else {
							player.setVector(-player.getSpeed());
						}
					} else if (location == "NE") {
						if (balls.get(0).getY() > (player.getY()) + (player.getWidth() / 2)) {
							player.setVector(player.getSpeed());
						} else {
							player.setVector(-player.getSpeed());
						}
					}
					following = true;
				}

			}
		}
	}

	/**
	 * Updates the balls' locations and detects and processes collisions.
	 */
	public void updateBalls() {
		collisionTicks++;
		for (Ball balli : balls.values()) {
			// Brick collisions
			for (Brick brick : bricks.values()) {
				if (brick.getBoundsArea().intersects(balli.getBounds())) {
					sound.brickHitSound();
					balli.setYVector((-1) * balli.getYVector());
					// System.out.println("Brick Collision detected");
					brick.hit();
					if (balli.getLastHit() >= 0 && !brick.isAlive()) {
						// Gain score when break the brick
						players.get(balli.getLastHit()).gainScore(1);
						// Check is there any bonus of the brick
					}
					break;
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
					if (player.getLocation() == "NE" || player.getLocation() == "NW") {
						if ((player.isAI()) && (getRandom().nextBoolean())) {
						} else {
							if (balli.getY() - (yPlayer + endsWidth) < 10) {
								balli.setYVector((-1) * balli.getYVector());
							} else if ((balli.getY() > ((yPlayer + wPlayer) - endsWidth)) && (balli.getYVector() < 0)) {
								balli.setYVector((-1) * balli.getYVector());
							}
						}
						balli.setXVector((-1) * balli.getXVector());
					} else {
						if ((player.isAI()) && (getRandom().nextBoolean())) {
						} else {
							if (balli.getX() - (xPlayer + endsWidth) < 10) {
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
							// System.out.println("Ball And Ball 1 Collision detected");
							balli.setXVector((-1) * balli.getXVector());
						}
						if (balli.getYVector() != ballx.getYVector()) {
							// System.out.println("Ball And Ball 2 Collision detected");
							balli.setYVector((-1) * balli.getYVector());
						}
					}
				}
			}

			// Meets side of map
			if (SouthLine.intersects(balli.getBounds())) {
				if (collisionTicks >= 40) {
					// System.out.println("South Border Collision detected");
					balli.setXVector(-balli.getXVector());
					balli.setYVector(-balli.getYVector());
					collisionTicks = 0;
				}
			} else if (NorthEastLine.intersects(balli.getBounds())) {
				if (collisionTicks >= 40) {
					// System.out.println("North East Border Collision detected");
					balli.setXVector(-balli.getXVector());
					balli.setYVector(-balli.getYVector());
					collisionTicks = 0;
				}
			} else if (NorthWestLine.intersects(balli.getBounds())) {
				if (collisionTicks >= 40) {
					balli.setXVector(-balli.getXVector());
					balli.setYVector(-balli.getYVector());
					// System.out.println("North West Border Collision detected");
					collisionTicks = 0;
				}
			} else if (balli.getX() + balli.getXVector() > getWidth() - balli.getDiameter()) {
				// System.out.println("Collision out map boundaries [EAST] detected");
				balli.setXVector(-Math.abs(balli.getXVector()));
			}

			else if (balli.getX() + balli.getXVector() < 0) {
				// System.out.println("Collision out map boundaries [NORTH] detected");
				balli.setXVector(Math.abs(balli.getXVector()));
			} else if (balli.getY() + balli.getYVector() < 0) {

				// System.out.println("Collision out map boundaries [WEST] detected");
				balli.setYVector(Math.abs(balli.getYVector()));
			} else if (balli.getY() + balli.getYVector() > getHeight() - balli.getDiameter()) {
				// System.out.println("Collision out map boundaries [SOUTH] detected");
				balli.setYVector(-Math.abs(balli.getYVector()));
			}
			balli.update();
		}
	}

	/**
	 * Create the boundary lines for the map
	 */
	public void createLines() {
		int gameHeight = MainPage.HEIGHT;
		double rotatedHeight = gameHeight * Math.tan(Math.toRadians(15));
		SouthLine = new Line2D.Double(gameHeight + gameHeight / 4, gameHeight, gameHeight / 4, gameHeight - rotatedHeight);
		NorthWestLine = new Line2D.Double(gameHeight - rotatedHeight + gameHeight / 4, 0, gameHeight / 4, gameHeight - rotatedHeight);
		NorthEastLine = new Line2D.Double(gameHeight - rotatedHeight + gameHeight / 4, 0, gameHeight + gameHeight / 4, gameHeight);
	}

	/**
	 * Return the x midpoint of a given line
	 * 
	 * @param line
	 *            the line to find the midpoint of
	 * @return the x value of the midpoint
	 * 			
	 */
	public int getXMidpoint(Line2D line) {
		int x1 = (int) line.getX1();
		int x2 = (int) line.getX2();
		return Math.round((x1 + x2) / 2);
	}

	/**
	 * Return the y midpoint of a given line
	 * 
	 * @param line
	 *            the line to find the midpoint of
	 * @return the y value of the midpoint
	 * 			
	 */
	public int getYMidpoint(Line2D line) {
		int y1 = (int) line.getY1();
		int y2 = (int) line.getY2();
		return Math.round((y1 + y2) / 2);
	}

	/**
	 * Return if 2 areas intersect
	 * 
	 * @param a1
	 *            the first area
	 * @param a2
	 * 			  the second area
	 * @return if they intersect
	 * 			
	 */
	public static boolean intersectsArea(Area a1, Area a2) {
		Area tempA1 = (Area) a1.clone();
		Area tempA2 = (Area) a2.clone();

		tempA1.intersect(tempA2);

		return (!tempA1.isEmpty());
	}

	/**
	 * transform a line into a polygon
	 * 
	 * @param l
	 *            the line
	 * @return the area created by the line as a polygon
	 * 			
	 */
	public static Area lineToArea(Line2D l) {
		Polygon shape = new Polygon();
		shape.addPoint((int) l.getX1(), (int) l.getY1());
		shape.addPoint((int) l.getX1() + 1, (int) l.getY1() + 1);
		shape.addPoint((int) l.getX2(), (int) l.getY2());
		shape.addPoint((int) l.getX2() + 1, (int) l.getY2() + 1);

		return new Area(shape);
	}
	
	/**
	 * Return if a line and an area intersect
	 * 
	 * @param l
	 *            the line
	 * @param a2
	 * 			  the area
	 * @return if they intersect
	 * 			
	 */
	public static boolean lineIntersectsArea(Line2D l, Area a2) {
		Area tempA1 = lineToArea(l);
		Area tempA2 = (Area) a2.clone();

		tempA1.intersect(tempA2);

		return (!tempA1.isEmpty());
	}

}
