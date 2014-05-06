package at.alex.nats;

import android.util.Log;


public class Player {
	private long time;
	private int ressources = 50000;
	private int permanents[] = new int[5];
	private int usables[] = new int[4];
	private int posX, posY;
	private int speedX, speedY = 0;
	
	public Player() {
		for (int i = 0; i <= 3; i++) {
			usables[i] = 0;
			permanents[i] = 0;
		}
		permanents[4] = 0;
		usables[0] = 2;
		usables[1] = 1;
	}
	
	public int getPermanents(int pos) {
		Log.i("NATS", "getPermanents an Stelle " + pos);
		return this.permanents[pos];
	}

	public void setPermanents(int value, int pos) {
		Log.i("NATS", "setPermanents an Stelle " + pos + " zu " + value);
		this.permanents[pos] = value;
	}

	public int getUsables(int pos) {
		Log.i("NATS", "getUsables an Stelle " + pos);
		return this.usables[pos];
	}

	public void setUsables(int value, int pos) {
		Log.i("NATS", "setUsables an Stelle " + pos + " zu " + value);
		this.usables[pos] = value;
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
		Log.i("NATS", "setRessources to " + ressources);
		this.ressources = ressources;
	}
	
	public int getRessources() {
		Log.i("NATS", "getRessources: " + this.ressources);
		return this.ressources;
	}
	

	
	public void setSpeedX() {
		speedX = 4;
	}
	
	public void setSpeedY() {
		speedY = 4;
	}
	
	
	
}
