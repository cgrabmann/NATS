package at.stefan.nats;

import org.andengine.engine.handler.timer.ITimerCallback;
import org.andengine.engine.handler.timer.TimerHandler;
import org.andengine.entity.primitive.Rectangle;
import org.andengine.entity.sprite.Sprite;
import org.andengine.opengl.texture.TextureOptions;
import org.andengine.opengl.texture.atlas.bitmap.BitmapTextureAtlas;
import org.andengine.opengl.texture.atlas.bitmap.BitmapTextureAtlasTextureRegionFactory;
import org.andengine.opengl.texture.region.ITextureRegion;
import org.andengine.util.adt.color.Color;

import android.util.Log;
import at.alex.nats.Player;

import com.badlogic.gdx.physics.box2d.Body;

public class Usables {
	
	private float i = 0.5f;
	private float j = 0.5f;
	
	private boolean bigExplosion = false;
	private boolean smallExplosion = false;

	private int counterTurbo = 0;
	private int counterDeadly = 0;

	private boolean stasisfield = false;
	private boolean turbo = false;
	private boolean deadlytrail = false;
	private boolean bomb = false;

	nats nats;
	GameEnvironment gameEnvironment;
	MaxStepPhysicsWorld world;
	Player player;
	BulletPool bulletPool;

	BitmapTextureAtlas stasisFieldBitmap;
	ITextureRegion stasisFieldTexture;
	Sprite stasisFieldSprite;

	BitmapTextureAtlas bombBitmap;
	ITextureRegion bombTexture;
	Sprite bombSprite1;
	Sprite bombSprite2;
	Sprite bombSprite3;
	Sprite bombSprite4;
	Sprite bombSprite5;

	Rectangle rec;

	public Usables(nats nats, GameEnvironment gameEnvironment,
			MaxStepPhysicsWorld world, Player player, BulletPool bulletPool) {
		this.nats = nats;
		this.gameEnvironment = gameEnvironment;
		this.world = world;
		this.player = player;
		this.bulletPool = bulletPool;

		BitmapTextureAtlasTextureRegionFactory.setAssetBasePath("gfx/");
		stasisFieldBitmap = new BitmapTextureAtlas(nats.getTextureManager(),
				1600, 960, TextureOptions.DEFAULT);
		stasisFieldTexture = BitmapTextureAtlasTextureRegionFactory
				.createFromAsset(stasisFieldBitmap,
						nats.getApplicationContext(), "Stasis.png", 0, 0);
		stasisFieldBitmap.load();

		stasisFieldSprite = new Sprite(400, 240, stasisFieldTexture,
				nats.getVertexBufferObjectManager());
		stasisFieldSprite.setAlpha(0.3f);
		stasisFieldSprite.setZIndex(2);

		bombBitmap = new BitmapTextureAtlas(nats.getTextureManager(), 100, 100,
				TextureOptions.BILINEAR);
		bombTexture = BitmapTextureAtlasTextureRegionFactory.createFromAsset(
				bombBitmap, nats.getApplicationContext(), "Explosion.png", 0, 0);
		bombBitmap.load();

		bombSprite1 = new Sprite(0, 0, bombTexture,
				nats.getVertexBufferObjectManager());
		bombSprite2 = new Sprite(0, 0, bombTexture,
				nats.getVertexBufferObjectManager());
		bombSprite3 = new Sprite(0, 0, bombTexture,
				nats.getVertexBufferObjectManager());
		bombSprite4 = new Sprite(0, 0, bombTexture,
				nats.getVertexBufferObjectManager());
		bombSprite5 = new Sprite(0, 0, bombTexture,
				nats.getVertexBufferObjectManager());
	}

	public void stasisfield() {
		Log.i("Usables", "Stasisfield");
		this.stasisfield = true;

		// Hier Stasisfield aktivieren

		gameEnvironment.attachChild(stasisFieldSprite);
		nats.getEngine().registerUpdateHandler(
				new TimerHandler(5f, new ITimerCallback() {

					@Override
					public void onTimePassed(TimerHandler pTimerHandler) {
						// TODO Auto-generated method stub
						Log.i("Usables", "Stasisfield Ende");

						// Hier stasisField Aktionen rückgängig machen

						gameEnvironment.detachChild(stasisFieldSprite);
						stasisfield = false;
						nats.getEngine().unregisterUpdateHandler(pTimerHandler);
					}
				}));
	}

	public void turbo(final Body body) {
		player.setShootingAllowed(false);
		player.activateTurbo();

		final float x;
		final float y;

		turbo = true;

		// Log.i("Usables", "Rotation: " + player.getPlayer().getRotation());
		float radians = gameEnvironment.getPlayerBody().getAngle();

		// Log.i("Usables", "radians: "+ radians);
		// Log.i("Usables", "angle: "+ angle);

		x = (float) Math.sin(-radians);
		y = (float) Math.cos(-radians);

		// Log.i("Usables", "cos: " + x);
		// Log.i("Usables", "sin: " + y);

		nats.getEngine().registerUpdateHandler(
				new TimerHandler(0.05f, true, new ITimerCallback() {

					@Override
					public void onTimePassed(TimerHandler pTimerHandler) {
						// TODO Auto-generated method stub
						body.setLinearVelocity(x * 2500f, y * 2500f);
						if (counterTurbo >= 10) {
							turbo = false;
							player.setShootingAllowed(true);
							player.deactivateTurbo();
							nats.getEngine().unregisterUpdateHandler(
									pTimerHandler);
							counterTurbo = 0;
						}
						counterTurbo++;
					}
				}));
	}

	public void deadlytrail() {
		deadlytrail = true;

		nats.getEngine().registerUpdateHandler(
				new TimerHandler(0.35f, true, new ITimerCallback() {

					@Override
					public void onTimePassed(TimerHandler pTimerHandler) {
						// TODO Auto-generated method stub
						Log.i("Usables", "allocate trail");
						Trail t = bulletPool.onAllocateTrail();
						/*
						 * Log.i("Usables", "set trail; X,Y: " +
						 * player.getFlamePosX() + "," + player.getFlamePosY());
						 */
						t.set(player.getPosX(), player.getPosY());

						if (counterDeadly >= 40) {
							nats.getEngine().unregisterUpdateHandler(
									pTimerHandler);
							counterDeadly = 0;
							nats.getEngine().registerUpdateHandler(
									new TimerHandler(7f, new ITimerCallback() {

										@Override
										public void onTimePassed(
												TimerHandler pTimerHandler) {
											// TODO Auto-generated method stub
											nats.getEngine()
													.unregisterUpdateHandler(
															pTimerHandler);
											deadlytrail = false;
										}
									}));
						}

						counterDeadly++;
					}
				}));
	}

	public void bomb() {
	 
		// Hier alle Gegner "pausieren"
		
		bombSprite1.setPosition(400f, 240f);
		gameEnvironment.attachChild(bombSprite1);
		
		nats.getEngine().registerUpdateHandler(new TimerHandler(0.02f, true, new ITimerCallback() {
			
			@Override
			public void onTimePassed(TimerHandler pTimerHandler) {
				// TODO Auto-generated method stub
				bombSprite1.setScale(i);
				i += 0.16f;
				
				if(i >= 11.20) {
					nats.getEngine().unregisterUpdateHandler(pTimerHandler);
					nats.getEngine().registerUpdateHandler(new TimerHandler(1f, new ITimerCallback() {
						
						@Override
						public void onTimePassed(TimerHandler pTimerHandler) {
							// TODO Auto-generated method stub
							nats.getEngine().unregisterUpdateHandler(pTimerHandler);
							gameEnvironment.detachChild(bombSprite1);
							gameEnvironment.detachChild(bombSprite2);
							gameEnvironment.detachChild(bombSprite3);
							gameEnvironment.detachChild(bombSprite4);
							gameEnvironment.detachChild(bombSprite5);
							i = 0.5f;
							j = 0.5f;
							smallExplosion = false;
						}
					}));
					
					// Hier die Gegner entfernen
				}
				
				if(i >= 6.4) {
					if(!smallExplosion) {
						smallExplosion = true;
						bombSprite2.setPosition(0, 480);
						bombSprite2.setRotation(225f);
						bombSprite3.setPosition(0, 0);
						bombSprite3.setRotation(135f);
						bombSprite4.setPosition(800, 480);
						bombSprite4.setRotation(315f);
						bombSprite5.setPosition(800, 0);
						bombSprite5.setRotation(45f);
						
						gameEnvironment.attachChild(bombSprite2);
						gameEnvironment.attachChild(bombSprite3);
						gameEnvironment.attachChild(bombSprite4);
						gameEnvironment.attachChild(bombSprite5);
					}
					
					bombSprite2.setScale(j);
					bombSprite3.setScale(j);
					bombSprite4.setScale(j);
					bombSprite5.setScale(j);
					
					j += 0.16f;
				}
			}
		}));
		
	}

	public boolean stasisFieldIsActivated() {
		return stasisfield;
	}

	public boolean turboIsActivated() {
		return turbo;
	}

	public boolean deadlyTrailIsActivated() {
		return deadlytrail;
	}

	public boolean bombIsActivated() {
		return bomb;
	}

}
