/*package at.stefan.nats;

import org.andengine.opengl.texture.region.TextureRegion;
import org.andengine.util.adt.pool.GenericPool;

public class BulletPool extends GenericPool<Bullet> {
	private TextureRegion mTextureRegion;

	public BulletPool(TextureRegion pTextureRegion) {
		if (pTextureRegion == null) {
			// Need to be able to create a Sprite so the Pool needs to have a
			// TextureRegion
			throw new IllegalArgumentException(
					"The texture region must not be NULL");
		}
		mTextureRegion = pTextureRegion;
	}

	/**
	 * Called when a Bullet is required but there isn't one in the pool
	 */
	//@Override
	/*protected Bullet onAllocatePoolItem() {
		//return null;
		//return new Bullet(mTextureRegion);
	}*/

	/**
	 * Called when a Bullet is sent to the pool
	 */
	/*@Override
	protected void onHandleRecycleItem(final Bullet pBullet) {
		pBullet.setIgnoreUpdate(true);
		pBullet.setVisible(false);
	}

	/**
	 * Called just before a Bullet is returned to the caller, this is where you
	 * write your initialize code i.e. set location, rotation, etc.
	 */
	/*@Override
	protected void onHandleObtainItem(final Bullet pBullet) {
		pBullet.reset();
	}
}
*/