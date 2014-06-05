package at.stefan.nats;

import java.util.Stack;

import org.andengine.util.adt.color.Color;

import android.util.Log;
import at.alex.nats.Player;

public class BulletPool {

	Player player;
	GameEnvironment gameEnvironment;
	MaxStepPhysicsWorld world;
	nats nats;
	Stack<Bullet> bulletStorage = new Stack<Bullet>();
	Stack<Bullet> gunnerStorage = new Stack<Bullet>();
	Stack<Trail> trailStorage = new Stack<Trail>();
	Bullet b;
	Trail t;

	public BulletPool(Player p, GameEnvironment g, MaxStepPhysicsWorld pw,
			nats nats) {

		this.player = p;
		this.gameEnvironment = g;
		this.world = pw;
		this.nats = nats;

		/*trailGroup = new SpriteGroup(
				(ITexture) gameEnvironment.getFireBallTextureRegion(), 40,
				nats.getVertexBufferObjectManager());*/

		for (int i = 0; i < 75; i++) {
			bulletStorage.push(new Bullet(this.gameEnvironment, this.player,
					this.world, this.nats, this));
		}

		gunnerStorage.push(new Bullet(this.gameEnvironment, this.player,
				this.world, this.nats, this, new Color(1f, 0f, 0f)));

		for (int i = 0; i < 41; i++) {
			trailStorage.push(new Trail(gameEnvironment, player, world, nats,
					this));
		}
	}

	/**
	 * Called when a Bullet is required but there isn't one in the pool
	 */
	public Bullet onAllocateBullet() {
		// return null;
		if (bulletStorage.empty()) {
			Log.i("Pool", "Create new Bullet");
			b = new Bullet(gameEnvironment, player, world, nats, this);
			// b.activate();
			return b;
		} else {
			Log.i("Pool", "Pop Bullet");
			b = bulletStorage.pop();
			// b.activate();
			return b;
		}

	}

	public Bullet onAllocateGunner() {
		if (gunnerStorage.empty()) {
			Log.i("NATS", "new Gunner");
			b = new Bullet(gameEnvironment, player, world, nats, this,
					new Color(1f, 0f, 0f));
			return b;
		} else {
			Log.i("NATS", "Pop Gunner");
			b = gunnerStorage.pop();
			return b;
		}
	}

	public Trail onAllocateTrail() {
		Log.i("Usables", "onAllocateTrail");
		if (trailStorage.empty()) {
			Log.i("Usables", "onAllocateTrail, empty");
			t = new Trail(gameEnvironment, player, world, nats, this);
			return t;
		} else {
			Log.i("Usables", "onAllocateTrail, pop");
			t = trailStorage.pop();
			return t;
		}
	}

	/**
	 * Called when a Bullet is sent to the pool
	 */
	public void recycleBullet(final Bullet pBullet) {
		// Log.i("Pool", "Push Bullet");
		bulletStorage.push(pBullet);
	}

	public void recycleGunner(final Bullet pBullet) {
		Log.i("NATS", "Push Gunner");
		gunnerStorage.push(pBullet);
	}

	public void recycleTrail(final Trail pTrail) {
		trailStorage.push(pTrail);
	}

	/**
	 * Called just before a Bullet is returned to the caller, this is where you
	 * write your initialize code i.e. set location, rotation, etc.
	 */

}
