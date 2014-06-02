package at.clemens.nats;

import java.util.Random;

import org.andengine.entity.scene.Scene;

import at.alex.nats.Player;
import at.stefan.nats.nats;

public abstract class PEnemy {

	float posx, posy;
	int movex, movey;
	boolean frozen;
	protected Player player;
	protected nats nats;
	
	private final int splices = 5;

	public PEnemy(nats n) {
		this.nats = n;
		this.movex = 0;
		this.movey = 0;
	}
	
	protected void createStartPos(Scene pf){
		Random r = new Random();
		int spawnControll = r.nextInt(4);
		switch (spawnControll){
		case (0):
			this.posx = pf.getScaleX()/splices;
			this.posy = pf.getScaleY()/splices;
			break;
		case (1):
			this.posx = pf.getScaleX()/splices;
			this.posy = (pf.getScaleY()/splices)*(splices-1);
			break;
		case (2):
			this.posx = (pf.getScaleX()/splices)*(splices-1);
			this.posy = pf.getScaleY()/splices;
			break;
		case (3):
			this.posx = (pf.getScaleX()/splices)*(splices-1);
			this.posy = (pf.getScaleY()/splices)*(splices-1);
			break;
		default:
			break;
		}
	}
	
	public int getMovex(){
		return movex;
	}
	
	public int getMovey(){
		return movey;
	}
	
	public abstract void start();
	public abstract void stop();

}
