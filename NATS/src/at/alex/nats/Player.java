package at.alex.nats;

import org.andengine.entity.scene.Scene;
import org.andengine.entity.sprite.Sprite;

public class Player {
	private int ressources;
	private int permanents[] = new int[5];
	private int usables[] = new int[4];
	private float posX, posY;
	private float speed = 0;
	private float velX, velY;
	
	public Player(Scene scene, Sprite playerSprite) {
		this.ressources = 0;
		for (int i = 0; i <= 3; i++) {
			this.usables[i] = 0;
			this.permanents[i] = 0;
		}
		this.permanents[4] = 0;
		this.usables[0] = 2;
		this.usables[1] = 1;
		this.posX = scene.getHeight()/2 - (playerSprite.getHeight()/2);
		this.posY = scene.getWidth()/2 - (playerSprite.getWidth()/2);
	}
	
	public int getPermanents(int pos) {
		return this.permanents[pos];
	}

	public void setPermanents(int value, int pos) {
		this.permanents[pos] = value;
	}

	public int getUsables(int pos) {
		return this.usables[pos];
	}

	public void setUsables(int value, int pos) {
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
		this.ressources = ressources;
	}
	
	public int getRessources() {
		return this.ressources;
	}
	
	public void setSpeed(int speed) {
		this.speed = speed;
	}
	
	public float getSpeed(){
		return this.speed;
	}
}
