package at.clemens.nats;

import java.util.Random;

import org.andengine.entity.scene.Scene;

import at.alex.nats.Player;
import at.stefan.nats.nats;

public abstract class PEnemy {

	protected float posx, posy;
	protected int movex, movey;
	protected boolean frozen;
	protected Player player;
	protected nats nats;
	
	private final int splices = 5;

	public PEnemy(nats n, Player p) {
		this.nats = n;
		this.player = p;
		this.movex = 0;
		this.movey = 0;
	}
	
	protected void createStartPos(Scene pf){
		Random r = new Random();
		int spawnControll = r.nextInt(4);
		switch (spawnControll){
		case (0):
			this.posx = (pf.getScaleX()-150)/splices;
			this.posy = (pf.getScaleY()-150)/splices;
			break;
		case (1):
			this.posx = (pf.getScaleX()-150)/splices;
			this.posy = ((pf.getScaleY()-150)/splices)*(splices-1);
			break;
		case (2):
			this.posx = ((pf.getScaleX()-150)/splices)*(splices-1);
			this.posy = (pf.getScaleY()-150)/splices;
			break;
		case (3):
			this.posx = ((pf.getScaleX()-150)/splices)*(splices-1);
			this.posy = ((pf.getScaleY()-150)/splices)*(splices-1);
			break;
		default:
			break;
		}
	}
	
	public abstract void start();
	public abstract void stop();

}
