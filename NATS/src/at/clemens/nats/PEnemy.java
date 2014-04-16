package at.clemens.nats;

import java.util.Random;

import android.graphics.Canvas;

public abstract class PEnemy {

	int posx, posy;
	int movex, movey;
	int size;
	int speed;
	boolean frozen;
	
	private final int splices = 5;

	public PEnemy(Canvas pf) {
		Random rg = new Random();
		int spawnControll = rg.nextInt(4);
		switch (spawnControll){
		case (0):
			this.posx = pf.getHeight()/splices;
			this.posy = pf.getWidth()/splices;
			break;
		case (1):
			this.posx = pf.getHeight()/splices;
			this.posy = (pf.getWidth()/splices)*(splices-1);
			break;
		case (2):
			this.posx = (pf.getHeight()/splices)*(splices-1);
			this.posy = pf.getWidth()/splices;
			break;
		case (3):
			this.posx = (pf.getHeight()/splices)*(splices-1);
			this.posy = (pf.getWidth()/splices)*(splices-1);
			break;
		default:
			break;
		}
	}
	
	public abstract void update(Player player, Canvas pf);
	protected abstract void move(Player player);

}
