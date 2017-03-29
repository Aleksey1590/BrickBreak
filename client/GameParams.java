package client;

import java.io.File;
import java.nio.file.Paths;
import java.util.ArrayList;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

/**
 * A class containing methods to load game data from XML files.
 */
public class GameParams {
	private static final String levelOne = "/client/resource/LevelOneParams.xml";
	private static final String levelTwo = "/client/resource/LevelTwoParams.xml";
	private static final String levelThree = "/client/resource/LevelThreeParams.xml";

	public static int paddleWidth = 0;
	public static int paddleHeight = 0;
	public static int ballAmount = 0;
	public static int ballSize = 0;

	public static int horizontalStartXCoord = 0;
	public static int horizontalStartYCoord = 0;
	public static int horizontalBrickWidth = 0;
	public static int horizontalBrickHeight = 0;
	public static int horizontalBrickHealth = 0;

	public static int verticalStartXCoord = 0;
	public static int verticalStartYCoord = 0;
	public static int verticalBrickWidth = 0;
	public static int verticalBrickHeight = 0;
	public static int verticalBrickHealth = 0;

	public static final ArrayList<String> horizontalBricksMap = new ArrayList<String>();
	public static final ArrayList<String> verticalBricksMap = new ArrayList<String>();

	/**
	 * Load parameters from a given file.
	 * 
	 * @param filePath
	 *            The path to the file.
	 */
	public static void loadParams(String filePath) {
		try {
			File inputFile = new File(Paths.get(GameParams.class.getResource(filePath).toURI()).toString());
			DocumentBuilderFactory dbFactory = DocumentBuilderFactory.newInstance();

			DocumentBuilder dBuilder = dbFactory.newDocumentBuilder();
			Document doc = dBuilder.parse(inputFile);

			doc.getDocumentElement().normalize();

			NodeList paddleParams = doc.getElementsByTagName("paddle");
			NodeList ballParams = doc.getElementsByTagName("ball");

			NodeList horizontalBrickParams = doc.getElementsByTagName("brickHorizontal");
			NodeList verticalBrickParams = doc.getElementsByTagName("brickVertical");

			NodeList horizontalBricksPosition = doc.getElementsByTagName("horizontalBricks");
			NodeList verticalBricksPosition = doc.getElementsByTagName("verticalBricks");

			// Paddle parsing
			for (int temp = 0; temp < paddleParams.getLength(); temp++) {
				Node paddleNode = paddleParams.item(temp);
				if (paddleNode.getNodeType() == Node.ELEMENT_NODE) {
					Element eElement = (Element) paddleNode;
					paddleWidth = Integer.parseInt(eElement.getElementsByTagName("width").item(0).getTextContent());
					paddleHeight = Integer.parseInt(eElement.getElementsByTagName("height").item(0).getTextContent());
				}
			}

			// Ball parsing
			for (int temp = 0; temp < ballParams.getLength(); temp++) {
				Node ballNode = ballParams.item(temp);
				if (ballNode.getNodeType() == Node.ELEMENT_NODE) {
					Element eElement = (Element) ballNode;
					ballSize = Integer.parseInt(eElement.getElementsByTagName("size").item(0).getTextContent());
				}
			}

			// Horizontal Brick parsing
			for (int temp = 0; temp < horizontalBrickParams.getLength(); temp++) {
				Node brickNode = horizontalBrickParams.item(temp);
				if (brickNode.getNodeType() == Node.ELEMENT_NODE) {
					Element eElement = (Element) brickNode;
					horizontalBrickWidth = Integer.parseInt(eElement.getElementsByTagName("width").item(0).getTextContent());
					horizontalBrickHeight = Integer.parseInt(eElement.getElementsByTagName("height").item(0).getTextContent());
					horizontalBrickHealth = Integer.parseInt(eElement.getElementsByTagName("health").item(0).getTextContent());
				}
			}

			// Horizontal bricks map
			for (int temp = 0; temp < horizontalBricksPosition.getLength(); temp++) {
				Node horizontalBrickNode = horizontalBricksPosition.item(temp);
				if (horizontalBrickNode.getNodeType() == Node.ELEMENT_NODE) {
					Element eElement = (Element) horizontalBrickNode;
					horizontalStartXCoord = Integer.parseInt(eElement.getElementsByTagName("startingXCoordinate").item(0).getTextContent());
					horizontalStartYCoord = Integer.parseInt(eElement.getElementsByTagName("startingYCoordinate").item(0).getTextContent());
					for (int i = 1; i < 20; i++) {
						try {
							String rowI = "row" + i;
							horizontalBricksMap.add(eElement.getElementsByTagName(rowI).item(0).getTextContent());
						} catch (Exception e) {
							break;
						}
					}
				}
			}
			// Vertical Brick parsing
			for (int temp = 0; temp < verticalBrickParams.getLength(); temp++) {
				Node brickNode = verticalBrickParams.item(temp);
				if (brickNode.getNodeType() == Node.ELEMENT_NODE) {
					Element eElement = (Element) brickNode;
					verticalBrickWidth = Integer.parseInt(eElement.getElementsByTagName("width").item(0).getTextContent());
					verticalBrickHeight = Integer.parseInt(eElement.getElementsByTagName("height").item(0).getTextContent());
					verticalBrickHealth = Integer.parseInt(eElement.getElementsByTagName("health").item(0).getTextContent());
				}
			}
			// Horizontal bricks map
			for (int temp = 0; temp < verticalBricksPosition.getLength(); temp++) {
				Node verticalBricksNode = verticalBricksPosition.item(temp);
				if (verticalBricksNode.getNodeType() == Node.ELEMENT_NODE) {
					Element eElement = (Element) verticalBricksNode;
					verticalStartXCoord = Integer.parseInt(eElement.getElementsByTagName("startingXCoordinate").item(0).getTextContent());
					verticalStartYCoord = Integer.parseInt(eElement.getElementsByTagName("startingYCoordinate").item(0).getTextContent());
					for (int i = 1; i < 20; i++) {
						try {
							String rowI = "row" + i;
							verticalBricksMap.add(eElement.getElementsByTagName(rowI).item(0).getTextContent());
							// System.out.println(i+(eElement.getElementsByTagName(rowI).item(0).getTextContent()));
						} catch (Exception e) {
							break;
						}
					}
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	/**
	 * Parses the game parameters for level one.
	 */
	public static void levelOneParams() {
		loadParams(levelOne);
	}

	/**
	 * Parses the game parameters for level two.
	 */
	public static void levelTwoParams() {
		loadParams(levelTwo);
	}

	/**
	 * Parses the game parameters for level three.
	 */
	public static void levelThreeParams() {
		loadParams(levelThree);
	}

	/**
	 * @return the paddle width from the XML parameters
	 */
	public int getPaddleWidth() {
		return paddleWidth;
	}

	/**
	 * @return the paddle height from the XML parameters
	 */
	public int getPaddleHeight() {
		return paddleHeight;
	}

	/**
	 * @return the ball amount from the XML parameters
	 */
	public int getBallAmount() {
		return ballAmount;
	}

	/**
	 * @return the ball size from the XML parameters
	 */
	public int getBallSize() {
		return ballSize;
	}

	/**
	 * @return the brick width from the XML parameters
	 */
	public int getBrickWidth(boolean horizontal) {
		return horizontal ? horizontalBrickWidth : verticalBrickWidth;
	}

	/**
	 * @return the brick height from the XML parameters
	 */
	public int getBrickHeight(boolean horizontal) {
		return horizontal ? horizontalBrickHeight : verticalBrickHeight;
	}

	/**
	 * @return the brick health from the XML parameters
	 */
	public int getBrickHealth(boolean horizontal) {
		return horizontal ? horizontalBrickHealth : verticalBrickHealth;
	}

	/**
	 * @return the starting x coordinate from the XML parameters
	 */
	public int getStartingXCoord(boolean horizontal) {
		return horizontal ? horizontalStartXCoord : verticalStartXCoord;
	}

	/**
	 * @return the starting y coordinate from the XML parameters
	 */
	public int getStartingYCoord(boolean horizontal) {
		return horizontal ? horizontalStartYCoord : verticalStartYCoord;
	}

	/**
	 * @return the level map from the XML parameters
	 */
	public ArrayList<String> getBricksMap(boolean horizontal) {
		return horizontal ? horizontalBricksMap : verticalBricksMap;
	}

	/**
	 * Initialises data ready for loading.
	 */
	public static void initializeParams() {
		horizontalBricksMap.clear();
		verticalBricksMap.clear();

		paddleWidth = 0;
		paddleHeight = 0;
		ballAmount = 0;
		ballSize = 0;

		horizontalStartXCoord = 0;
		horizontalStartYCoord = 0;
		horizontalBrickWidth = 0;
		horizontalBrickHeight = 0;
		horizontalBrickHealth = 0;

		verticalStartXCoord = 0;
		verticalStartYCoord = 0;
		verticalBrickWidth = 0;
		verticalBrickHeight = 0;
		verticalBrickHealth = 0;
	}
}
