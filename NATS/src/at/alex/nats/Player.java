package at.alex.nats;

import android.util.Log;

public class Player {

	private long time;
	private int ressources = 50000;
	private int permanents[] = new int[5];
	private int usables[] = new int[4];

	private float posX, posY;
	private float speed = 0;
	private float velX, velY;
	
	public Player() {
		for (int i = 0; i <= 3; i++) {
			this.usables[i] = 0;
			this.permanents[i] = 0;
		}
		this.permanents[4] = 0;
		this.usables[0] = 2;
		this.usables[1] = 1;
		this.posX = 0;
		this.posY = 0;
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

	public float getPosX() {
		return this.posX;
	}

	public void setPosX(float posX) {
		this.posX = posX;
	}

	public float getPosY() {
		return this.posY;
	}

	public void setPosY(float posY) {
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
	
	public void setSpeed(int speed) {
		this.speed = speed;
	}
	
	public float getSpeed(){
		return this.speed;
	}
}
