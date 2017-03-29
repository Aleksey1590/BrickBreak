package client;

import java.awt.Color;
import java.awt.Graphics2D;

public class AIHelper extends Player {
	private Player belongsToPlayer;
	private int myID;
	private Game g;

	/**
	 * Creates an AIHelper in the game that seeks to disrupt a specific user-controlled player.
	 * @param p The player that the AIHelper is assigned to.
	 * @param g The game object that the AIHelper is 
	 */
	public AIHelper(Player p, Game g) {
		super(p.getX(), p.getY(), 2, p.getLocation());
		this.g = g;
		this.belongsToPlayer = p;
		setAI(true);
		myID = 10;
		while (g.getPlayerList().containsKey(myID)) {
			myID++;
		}
		g.getPlayerList().put(myID, this);
	}

	@Override
	public void gainScore(int score) {
		if (belongsToPlayer instanceof AIHelper) {
			belongsToPlayer.gainScore(score);
		} else {
			belongsToPlayer.gainScore(score * 1000);
		}
	}

	public void deleteAIHelper() {
		g.getPlayerList().remove(myID);
	}

	@Override
	public void draw(Graphics2D g) {
		g.setColor(new Color(0, 0, 255, 170));
		if (belongsToPlayer.getLocation() == "N" || belongsToPlayer.getLocation() == "S") {
			g.fillRoundRect(getX(), getY(), getWidth(), getHeight(), 18, 18);
		} else {
			g.fillRoundRect(getX(), getY(), getWidth(), getHeight(), 18, 18);
		}
	}
	
	@Override
	public void setBonus(int bonus) {
		belongsToPlayer.setBonus(bonus);
	}
	
	@Override
	public int getBonus() {
		return belongsToPlayer.getBonus();
	}
	
	/**
	 * @return the ID of the object.
	 */
	public int getMyID() {
		return myID;
	}
}
