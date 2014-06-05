package at.stefan.nats;

import java.util.Stack;

import at.alex.nats.Player;
import at.clemens.nats.EnemyBlackHole;
import at.clemens.nats.EnemyTypeOne;
import at.clemens.nats.EnemyTypeTwo;
import at.clemens.nats.EnemyTypeTwoSmall;
import at.clemens.nats.EnemyTypeZero;

public class EnemyPool {

	Player player;
	GameEnvironment gameEnvironment;
	MaxStepPhysicsWorld world;
	nats nats;
	Stack<EnemyTypeZero> zeroStorage = new Stack<EnemyTypeZero>();
	Stack<EnemyTypeOne> oneStorage = new Stack<EnemyTypeOne>();
	Stack<EnemyTypeTwo> twoStorage = new Stack<EnemyTypeTwo>();
	Stack<EnemyTypeTwoSmall> twoSmallStorage = new Stack<EnemyTypeTwoSmall>();
	Stack<EnemyBlackHole> blackStorage = new Stack<EnemyBlackHole>();

	EnemyTypeZero zero;
	EnemyTypeOne one;
	EnemyTypeTwo two;
	EnemyTypeTwoSmall twoS;
	EnemyBlackHole black;

	public EnemyPool(Player p, GameEnvironment g, MaxStepPhysicsWorld pw,
			nats nats) {

		this.player = p;
		this.gameEnvironment = g;
		this.world = pw;
		this.nats = nats;

		for (int i = 0; i < 20; i++) {
			zeroStorage.push(new EnemyTypeZero(gameEnvironment, null, nats,
					world, player, this));
			oneStorage.push(new EnemyTypeOne(gameEnvironment, null, nats,
					world, player, this));
			twoStorage.push(new EnemyTypeTwo(gameEnvironment, null, nats,
					world, player, this));
			twoSmallStorage.push(new EnemyTypeTwoSmall(gameEnvironment, null,
					nats, world, player, this));
		}

		for (int i = 0; i < 5; i++) {
			blackStorage.push(new EnemyBlackHole(gameEnvironment, null, nats,
					world, player, this));
		}
	}

	/**
	 * Called when a Bullet is required but there isn't one in the pool
	 */
	public EnemyTypeZero onAllocateEnemyZero() {
		if (zeroStorage.empty()) {
			zero = new EnemyTypeZero(gameEnvironment, null, nats, world, player, this);
			return zero;
		}else {
			zero = zeroStorage.pop();
			return zero;
		}
	}

	public EnemyTypeOne onAllocateEnemyone() {
		if (oneStorage.empty()) {
			one = new EnemyTypeOne(gameEnvironment, null, nats, world, player, this);
			return one;
		}else {
			one = oneStorage.pop();
			return one;
		}
	}
	
	public EnemyTypeTwo onAllocateEnemytwo() {
		if (twoStorage.empty()) {
			two = new EnemyTypeTwo(gameEnvironment, null, nats, world, player, this);
			return two;
		}else {
			two = twoStorage.pop();
			return two;
		}
	}
	
	public EnemyTypeTwoSmall onAllocateEnemytwoS() {
		if (twoSmallStorage.empty()) {
			twoS = new EnemyTypeTwoSmall(gameEnvironment, null, nats, world, player, this);
			return twoS;
		}else {
			twoS = twoSmallStorage.pop();
			return twoS;
		}
	}
	
	public EnemyBlackHole onAllocateEnemyblack() {
		if (blackStorage.empty()) {
			black = new EnemyBlackHole(gameEnvironment, null, nats, world, player, this);
			return black;
		}else {
			black = blackStorage.pop();
			return black;
		}
	}

	/**
	 * Called when an Enemy is sent to the pool
	 */
	public void recycleEnemyZero(final EnemyTypeZero enemy) {
		// Log.i("Pool", "Push Bullet");
		zeroStorage.push(enemy);
	}
	
	public void recycleEnemyOne(final EnemyTypeOne enemy) {
		// Log.i("Pool", "Push Bullet");
		oneStorage.push(enemy);
	}
	
	public void recycleEnemyTwo(final EnemyTypeTwo enemy) {
		// Log.i("Pool", "Push Bullet");
		twoStorage.push(enemy);
	}
	
	public void recycleEnemyTwoSmall(final EnemyTypeTwoSmall enemy) {
		// Log.i("Pool", "Push Bullet");
		twoSmallStorage.push(enemy);
	}
	
	public void recycleEnemyBlackHole(final EnemyBlackHole enemy) {
		// Log.i("Pool", "Push Bullet");
		blackStorage.push(enemy);
	}

	
}
