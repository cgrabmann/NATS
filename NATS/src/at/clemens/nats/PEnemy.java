package at.clemens.nats;

import java.util.Random;

import org.andengine.entity.scene.Scene;

import at.alex.nats.Player;

public abstract class PEnemy {

	float posx, posy;
	int movex, movey;
	int speed;
	boolean frozen;
	
	private final int splices = 5;

	public PEnemy() {
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
	
	public abstract boolean update(Player player, Scene pf);
	protected abstract void move(Player player);

}
