package at.clemens.nats;

import java.util.Random;

import org.andengine.entity.scene.Scene;

import android.graphics.Canvas;
import at.alex.nats.Player;

public abstract class PEnemy {

	float posx, posy;
	int movex, movey;
	int size;
	int speed;
	boolean frozen;
	
	private final int splices = 5;

	public PEnemy(Scene pf) {
		Random rg = new Random();
		int spawnControll = rg.nextInt(4);
		switch (spawnControll){
		case (0):
			this.posx = pf.getX()/splices;
			this.posy = pf.getY()/splices;
			break;
		case (1):
			this.posx = pf.getX()/splices;
			this.posy = (pf.getY()/splices)*(splices-1);
			break;
		case (2):
			this.posx = (pf.getX()/splices)*(splices-1);
			this.posy = pf.getY()/splices;
			break;
		case (3):
			this.posx = (pf.getX()/splices)*(splices-1);
			this.posy = (pf.getY()/splices)*(splices-1);
			break;
		default:
			break;
		}
	}
	
	public abstract boolean update(Player player, Scene pf);
	protected abstract boolean move(Player player);

}
