package at.clemens.nats;

import java.util.Random;

import org.andengine.entity.scene.Scene;

import android.util.Log;
import at.alex.nats.Player;
import at.stefan.nats.GameEnvironment;
import at.stefan.nats.nats;

public abstract class PEnemy {

	protected float posx, posy;
	protected int movex, movey;
	protected boolean frozen;
	protected Player player;
	protected nats nats;
	protected GameEnvironment game;
	
	private int spawnControll;
	private final int splices = 5;

	public PEnemy(nats n, Player p, GameEnvironment g) {
		this.nats = n;
		this.player = p;
		this.movex = 0;
		this.movey = 0;
		this.game = g;
		this.spawnControll = 0;
	}
	
	protected void createStartPos(Scene pf){
		//Random r = new Random();
		//int spawnControll = r.nextInt(4);
		double r = Math.random();
		if(r > 0.75) {
			spawnControll = 0;
		}else if(r > 0.5) {
			spawnControll = 1;
		}else if(r > 0.25) {
			spawnControll = 2;
		}else{
			spawnControll = 3;
		}
		
		
		switch (spawnControll){
		case (0):
			//Log.i("NATSSpawn", "Spawn 0");
			this.posx = -200;
			this.posy = -40;
			break;
		case (1):
			//Log.i("NATSSpawn", "Spawn 1");
			this.posx = 1000;
			this.posy = 520;
			break;
		case (2):
			//Log.i("NATSSpawn", "Spawn 2");
			this.posx = -200;
			this.posy = 520;
			break;
		case (3):
			//Log.i("NATSSpawn", "Spawn 3");
			this.posx = 1000;
			this.posy = -40;
			break;
		default:
			break;
		}
	}
	
	protected void addRessources(int r){
		player.addRessources(r);
		game.getRessourcesDisplay().setText(player.getRessourcesForDisplay());
	}
	
	public void setFrozen(boolean b) {
		this.frozen = b;
	}
	
	public abstract void start();
	public abstract void stop();
	public abstract void deactivate();
}
