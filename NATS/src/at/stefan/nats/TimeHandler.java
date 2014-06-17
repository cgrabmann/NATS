package at.stefan.nats;

import java.util.Iterator;

import org.andengine.engine.handler.timer.ITimerCallback;
import org.andengine.engine.handler.timer.TimerHandler;

import android.util.Log;
import at.alex.nats.Player;
import at.clemens.nats.EnemyTypeOne;
import at.clemens.nats.EnemyTypeTwo;
import at.clemens.nats.EnemyTypeZero;

import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Body;

public class TimeHandler implements ITimerCallback {
	
	private boolean active = false;

	private final int enemymulti = 7;
	private int spawnResources = 50;

	private int secs = 0;
	private int mins = 0;
	private int counterShield = 0;
	private int counterGunner = 0;
	private int waveCounter = 0;
	private String s = "00";
	private String m = "00";

	private double chanceZero = 2;
	private double chanceOne = 1;
	private double chanceTwo = 0;
	private double chanceFour = 0;

	private double totalChance = 3;

	GameEnvironment gameEnvironment;
	Player player;
	EnemyPool enemyPool;

	EnemyTypeZero zero;
	EnemyTypeOne one;
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
		if(this.active) {
			secs++;
			if (secs == 60) {
				mins++;
				switch (mins) {
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
			/*
			if (secs % 10 == 0) {
				Log.i("NATS", "Enemy Start");
				two = enemyPool.onAllocateEnemytwo();
				two.start();
			}*/

			/*
			if (waveCounter >= enemymulti * (mins + 1)) {
				int spawn = spawnResources;
				while (spawn > 0) {
					double rand = Math.random();
					/*Log.i("NATS", "random: " + rand);
					Log.i("NATS", "Chance zero: " + (chanceZero) / totalChance);
					Log.i("NATS", "Chance one: " + (chanceZero + chanceOne)
							/ totalChance);
					Log.i("NATS", "Chance two: "
							+ (chanceZero + chanceOne + chanceTwo) / totalChance);
					Log.i("NATS", "Chance Four: "
							+ (chanceZero + chanceOne + chanceTwo + chanceFour)
							/ totalChance);*/
					/*if (rand < chanceZero / totalChance) { // Gegner 0 spawnen
						zero = enemyPool.onAllocateEnemyZero();
						zero.start();
						spawn -= zero.getResources();
						//Log.i("NATS", "EnemyZero spawned");
					} else if (rand < (chanceZero + chanceOne) / totalChance) { // gegner
																				// 1
						one = enemyPool.onAllocateEnemyone();
						one.start();
						spawn -= one.getResources();
						//Log.i("NATS", "EnemyOne spawned");
					} else if (rand < (chanceZero + chanceOne + chanceTwo)
							/ totalChance) { // gegner 2
						two = enemyPool.onAllocateEnemytwo();
						two.start();
						spawn -= two.getResources();
						//Log.i("NATS", "EnemyTwo spawned");
					} else { // gegner 4
						//Log.i("NATS", "EnemyFour spawned");
					}
				}
				waveCounter = 0;
				spawnResources += 20;
			} else {
				waveCounter++;
			}*/

			if (!player.isShieldActivated()) {
				if (counterShield >= player.getTimeToShield()) {
					player.activateShield();
					gameEnvironment.getPlayerBaseSprite().setAlpha(1f);
					counterShield = 0;
				} else {
					counterShield++;
				}
			}

			if (counterGunner >= player.getTimeToGunner()) {
				Log.i("NATS", "Gunner");
				counterGunner = 0;
				// Hier den Gegner ausfindig machen, sobald sie
				// implementiert sind
				Iterator<Body> allBodies = gameEnvironment.getPhysicsWorld()
						.getBodies();
				Bullet b = gameEnvironment.getBulletPool().onAllocateGunner();
				b.fireBullet(new Vector2(0f, 1f));
			} else {
				counterGunner++;
			}
		}
	}

	public void reset() {
		active = false;
		secs = 0;
		mins = 0;
		counterShield = 0;
		counterGunner = 0;
		waveCounter = 0;
		chanceZero = 2;
		chanceOne = 1;
		chanceTwo = 0;
		chanceFour = 0;
		totalChance = 3;
		spawnResources = 50;
		s = "00";
		m = "00";
	}
	
	public void setActive(boolean b) {
		this.active = b;
	}

}
