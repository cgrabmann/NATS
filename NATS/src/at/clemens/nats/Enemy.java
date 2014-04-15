package at.clemens.nats;

import java.util.Random;

import android.graphics.Canvas;

public abstract class Enemy {

	int posx, posy;
	int size;
	int speed;
	boolean frozen;

	public Enemy(Canvas pf) {
		Random rg = new Random();
		int spawnControll = rg.nextInt(4);
		switch (spawnControll){
		case (0):
			this.posx = pf.getHeight()/5;
			this.posy = pf.getWidth()/5;
			break;
		case (1):
			this.posx = pf.getHeight()/5;
			this.posy = (pf.getWidth()/5)*4;
			break;
		case (2):
			this.posx = (pf.getHeight()/5)*4;
			this.posy = pf.getWidth()/5;
			break;
		case (3):
			this.posx = (pf.getHeight()/5)*4;
			this.posy = (pf.getWidth()/5)*4;
			break;
		default:
			break;
		}
	}
	
	public abstract void update(Player player, Canvas pf);
	protected abstract void move(Player player);

}
