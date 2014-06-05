package at.stefan.nats;

import org.andengine.engine.handler.timer.ITimerCallback;
import org.andengine.engine.handler.timer.TimerHandler;

import com.badlogic.gdx.math.Vector2;

import android.util.Log;
import at.alex.nats.Player;
import at.clemens.nats.EnemyTypeTwo;
import at.clemens.nats.EnemyTypeZero;

public class TimeHandler implements ITimerCallback {

	private final int enemymulti = 7;
	private int spawnResources = 50;
	
	private int secs = 0;
	private int mins = 0;
	private int counterShield = 0;
	private int counterGunner = 0;
	private int waveCounter = 0;
	private String s = "00";
	private String m = "00";
	
	private int chanceZero = 2;
	private int chanceOne = 1;
	private int chanceTwo = 0;
	private int chanceFour = 0;
	
	private int totalChance = 3;
	
	GameEnvironment gameEnvironment;
	Player player;
	EnemyPool enemyPool;
	
	EnemyTypeZero zero;
	EnemyTypeTwo two;
	
	public TimeHandler(GameEnvironment ge, Player p, EnemyPool enemyPool) {
		this.gameEnvironment = ge;
		this.player = p;
		this.enemyPool = enemyPool;
		
		// TODO Auto-generated constructor stub
	}
	
	@Override
	public void onTimePassed(TimerHandler pTimerHandler) {
		// TODO Auto-generated method stub
		secs++;
		if (secs == 60) {
			mins++;
			switch(mins) {
			case 1:
				chanceZero = 3;
				chanceOne = 2;
				chanceTwo = 1;
				break;
			case 2:
				chanceZero = 4;
				chanceOne = 3;
				chanceTwo = 2;
				chanceFour = 1;
				break;
			case 3:
				chanceZero = 3;
				chanceOne = 4;
				chanceTwo = 2;
				chanceFour = 1;
				break;
			case 4:
				chanceZero = 2;
				chanceOne = 4;
				chanceTwo = 3;
				chanceFour = 1;
				break;
			case 5:
				chanceZero = 2;
				chanceOne = 4;
				chanceTwo = 4;
				chanceFour = 2;
				break;
			case 6:
				chanceZero = 2;
				chanceOne = 3;
				chanceTwo = 4;
				chanceFour = 3;
				break;
			case 7:
				chanceZero = 1;
				chanceOne = 3;
				chanceTwo = 4;
				chanceFour = 4;
				break;
			case 8:
				chanceZero = 1;
				chanceOne = 2;
				chanceTwo = 3;
				chanceFour = 4;
				break;
			case 9:
				chanceZero = 1;
				chanceOne = 2;
				chanceTwo = 3;
				chanceFour = 6;
				break;
			case 10:
				chanceZero = 0;
				chanceOne = 0;
				chanceTwo = 1;
				chanceFour = 1;
				break;
			default:
				break;
			}
			secs = 0;
			totalChance = chanceZero + chanceOne + chanceTwo + chanceFour;
		}
		if (secs < 10) {
			s = "0" + secs;
		} else {
			s = "" + secs;
		}

		if (mins < 10) {
			m = "0" + mins;
		} else {
			m = "" + mins;
		}
		// Log.i("NATS", "Update Time");
		gameEnvironment.getHighScore().setText(m + ":" + s);
		
		// Gegner spawnen
		if(secs % 4 == 0) {
			Log.i("NATS", "Enemy Start");
			two = enemyPool.onAllocateEnemytwo();
			two.start();
		}
		
		
		/*if(waveCounter >= enemymulti*(mins+1)){
			int spawn = spawnResources;
			while(spawn > 0) {
				double rand = Math.random();
				if(rand < chanceZero/totalChance) {		// Gegner 0 spawnen
					zero = enemyPool.onAllocateEnemyZero();
					zero.start();
					spawn -= zero.getResources();
				}else if(rand < (chanceZero+chanceOne)/totalChance) {	// gegner 1
					
				}else if(rand < (chanceZero+chanceOne+chanceTwo)/totalChance) {	// gegner 2
					
				}else {		// gegner 4
					
				}
			}
			waveCounter = 0;
		}else {
			waveCounter++;
		}*/
		

		if (!player.getShield()) {
			if (counterShield >= player.getTimeToShield()) {
				player.activateShield();
				gameEnvironment.getPlayerBaseSprite().setAlpha(1f);
			} else {
				counterShield++;
			}
		}

		if (counterGunner >= player.getTimeToGunner()) {
			Log.i("NATS", "Gunner");
			counterGunner = 0;
			// Hier den Gegner ausfindig machen, sobald sie
			// implementiert sind
			Bullet b = gameEnvironment.getBulletPool().onAllocateGunner();
			b.fireBullet(new Vector2(0f, 1f));
		} else {
			counterGunner++;
		}
	}
	
	public void reset() {
		secs = 0;
		mins = 0;
		counterShield = 0;
		counterGunner = 0;
		s = "00";
		m = "00";
	}
	
}
