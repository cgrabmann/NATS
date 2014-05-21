package at.stefan.nats;

import java.util.Stack;

import org.andengine.entity.scene.Scene;
import org.andengine.extension.physics.box2d.PhysicsWorld;
import org.andengine.util.adt.pool.GenericPool;

import android.util.Log;
import at.alex.nats.Player;

public class BulletPool extends GenericPool<Bullet> {

	Player player;
	Scene gameScene;
	PhysicsWorld world;
	nats nats;
	Stack<Bullet> bulletStorage = new Stack<Bullet>();

	public BulletPool(Player p, Scene s, PhysicsWorld pw, nats nats) {

		this.player = p;
		this.gameScene = s;
		this.world = pw;
		this.nats = nats;

		for (int i = 0; i < 20; i++) {
			bulletStorage.push(new Bullet(this.gameScene, this.player, this.world, this.nats,
					this));
		}
	}

	/**
	 * Called when a Bullet is required but there isn't one in the pool
	 */
	@Override
	protected Bullet onAllocatePoolItem() {
		// return null;
		if(bulletStorage.empty() == true) {
			Log.i("Pool", "Create new Bullet");
			return new Bullet(gameScene, player, world, nats, this);
		}else {
			Log.i("Pool", "Pop Bullet");
			return bulletStorage.pop();
		}
		
	}

	/**
	 * Called when a Bullet is sent to the pool
	 */
	@Override
	protected void onHandleRecycleItem(final Bullet pBullet) {
		Log.i("Pool", "Push Bullet");
		bulletStorage.push(pBullet);
	}

	/**
	 * Called just before a Bullet is returned to the caller, this is where you
	 * write your initialize code i.e. set location, rotation, etc.
	 */
	@Override
	protected void onHandleObtainItem(final Bullet pBullet) {
		//pBullet.reset();
	}
}
