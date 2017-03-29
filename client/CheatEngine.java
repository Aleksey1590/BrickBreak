package client;

import java.util.ConcurrentModificationException;
import java.util.Random;
import java.util.concurrent.ConcurrentHashMap;

/**
 * A class holding methods that modify the game state, i.e. cheating.
 */
public class CheatEngine {

	// Player cheats
	/**
	 * A cheat to increase a specific player's width
	 * 
	 * @param id
	 *            the player's ID
	 * @param n
	 *            the width increase
	 * @param inputPlayers
	 *            the list of all players
	 * @return the new list of all players
	 */
	public ConcurrentHashMap<Integer, Player> increasePlayerWidth(int id, int n, ConcurrentHashMap<Integer, Player> inputPlayers) {
		try {
			int w = inputPlayers.get(id).getWidth();
			inputPlayers.get(id).setWidth(w + n);
			return inputPlayers;
		} catch (NumberFormatException e) {
			System.out.println("Wrong format in increasePlayerWidth.");
			return inputPlayers;
		} catch (NullPointerException e) {
			return inputPlayers;
		}
	}

	/**
	 * A cheat to decrease a specific player's width
	 * 
	 * @param id
	 *            the player's ID
	 * @param n
	 *            the width increase
	 * @param inputPlayers
	 *            the list of all players
	 * @return the new list of all players
	 */
	public ConcurrentHashMap<Integer, Player> decreasePlayerWidth(int id, int n, ConcurrentHashMap<Integer, Player> inputPlayers) {
		try {
			int w = inputPlayers.get(id).getWidth();
			if (w > 5) {
				inputPlayers.get(id).setWidth(w - n);
			}
			return inputPlayers;
		} catch (NumberFormatException e) {
			System.out.println("Wrong format in decreasePlayerWidth.");
			return inputPlayers;
		} catch (NullPointerException e) {
			return inputPlayers;
		}
	}

	// Brick cheats
	/**
	 * A cheat to increase the width of the first 25 bricks
	 * 
	 * @param n
	 *            the width increase
	 * @param inputBricks
	 *            the list of all bricks
	 * @return the new list of all bricks
	 */
	public ConcurrentHashMap<Integer, Brick> increaseBrickSize(int n, ConcurrentHashMap<Integer, Brick> inputBricks) {
		try {
			for (int i = 0; i < 25; i++) {
				if (inputBricks.get(i) == null) {
				} else if (inputBricks.get(i).isAlive()) {
					int s = inputBricks.get(i).getWidth();
					inputBricks.get(i).setHeight(s + n);
					inputBricks.get(i).setWidth(s + n);
				}
			}
		} catch (NumberFormatException e) {
			System.out.println("Wrong format in increaseBrickSize.");
			return inputBricks;
		} catch (NullPointerException e) {
			return inputBricks;
		}
		return inputBricks;
	}

	/**
	 * A cheat to decrease the width of the first 25 bricks
	 * 
	 * @param n
	 *            the width increase
	 * @param inputBricks
	 *            the list of all bricks
	 * @return the new list of all bricks
	 */
	public ConcurrentHashMap<Integer, Brick> decreaseBrickSize(int n, ConcurrentHashMap<Integer, Brick> inputBricks) {
		try {
			for (int i = 0; i < 25; i++) {
				if (inputBricks.get(i) == null) {
				} else if (inputBricks.get(i).isAlive()) {
					int s = inputBricks.get(i).getWidth();
					inputBricks.get(i).setHeight(s - n);
					inputBricks.get(i).setWidth(s - n);
				}
			}
		} catch (NumberFormatException e) {
			System.out.println("Wrong format in decreaseBrickSize.");
			return inputBricks;
		} catch (NullPointerException e) {
			return inputBricks;
		}
		return inputBricks;
	}

	// Ball cheats
	/**
	 * A cheat to increase the diameter of all balls
	 * 
	 * @param n
	 *            the diameter increase
	 * @param inputBalls
	 *            the list of all balls
	 * @return the new list of all balls
	 */
	public ConcurrentHashMap<Integer, Ball> increaseBallSize(int n, ConcurrentHashMap<Integer, Ball> inputBalls) {
		try {
			for (int i = 0; i <= inputBalls.size(); i++) {
				if (inputBalls.get(i) == null) {
				} else {
					int s = inputBalls.get(i).getDiameter();
					inputBalls.get(i).setDiameter(s + n);
				}
			}
		} catch (NumberFormatException e) {
			return inputBalls;
		} catch (NullPointerException e) {
			return inputBalls;
		}
		return inputBalls;
	}

	/**
	 * A cheat to decrease the diameter of all balls
	 * 
	 * @param n
	 *            the diameter decrease
	 * @param inputBalls
	 *            the list of all balls
	 * @return the new list of all balls
	 */
	public ConcurrentHashMap<Integer, Ball> decreaseBallSize(int n, ConcurrentHashMap<Integer, Ball> inputBalls) {
		try {
			for (int i = 0; i <= inputBalls.size(); i++) {
				if (inputBalls.get(i) == null) {
				} else {
					int s = inputBalls.get(i).getDiameter();
					if (s > 5) {
						inputBalls.get(i).setDiameter(s - n);
					}
				}
			}
		} catch (NumberFormatException e) {
			return inputBalls;
		} catch (NullPointerException e) {
			return inputBalls;
		}
		return inputBalls;
	}

	/**
	 * A cheat to add a new ball to the game
	 * 
	 * @param inputBalls
	 *            the list of all balls
	 * @return the new list of all balls
	 */
	public ConcurrentHashMap<Integer, Ball> addBall(ConcurrentHashMap<Integer, Ball> inputBalls) {
		Random randomGenerator = new Random();
		int size = inputBalls.size();
		int xCoord = randomGenerator.nextInt(500);
		int yCoord = randomGenerator.nextInt(500);
		int xVector = randomGenerator.nextInt(7);
		int yVector = randomGenerator.nextInt(7);

		if (size == 1) {
			inputBalls.put(inputBalls.size(), new Ball(xCoord, yCoord, xVector, yVector));
		} else {
			try {
				inputBalls.put(inputBalls.size() + 1, new Ball(xCoord, yCoord, xVector, yVector));
			} catch (ConcurrentModificationException e) {
				return inputBalls;
			}
		}
		return inputBalls;
	}

}
