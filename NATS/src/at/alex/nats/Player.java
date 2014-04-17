package at.alex.nats;

import org.andengine.ui.activity.BaseGameActivity;

public class Player {
	private long time;
	private int ressources;
	private int permanents[] = new int[5];
	private int usables[] = new int[4];
	private int posX, posY;
	private int speedX, speedY = 0;
	BaseGameActivity baseGame;
	
	public Player(BaseGameActivity baseGame) {
		this.baseGame = baseGame;
		for (int i = 0; i <= 3; i++) {
			usables[i] = 0;
			permanents[i] = 0;
		}
		permanents[4] = 0;
		usables[0] = 2;
		usables[1] = 1;
	}
	
	public int[] getPermanents() {
		return this.permanents;
	}

	public void setPermanents(int[] permanents) {
		this.permanents = permanents;
	}

	public int[] getUsables() {
		return this.usables;
	}

	public void setUsables(int[] usables) {
		this.usables = usables;
	}

	public int getPosX() {
		return this.posX;
	}

	public void setPosX(int posX) {
		this.posX = posX;
	}

	public int getPosY() {
		return this.posY;
	}

	public void setPosY(int posY) {
		this.posY = posY;
	}

	public void setRessources(int ressources) {
		this.ressources = ressources;
	}
	
	public int getRessources() {
		return this.ressources;
	}
	

	
	public void setSpeedX() {
		speedX = 4;
	}
	
	public void setSpeedY() {
		speedY = 4;
	}
	
	
	
}
