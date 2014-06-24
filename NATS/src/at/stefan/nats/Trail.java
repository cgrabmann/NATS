package at.stefan.nats;

import org.andengine.engine.handler.timer.ITimerCallback;
import org.andengine.engine.handler.timer.TimerHandler;
import org.andengine.entity.sprite.Sprite;
import org.andengine.extension.physics.box2d.PhysicsConnector;
import org.andengine.extension.physics.box2d.PhysicsFactory;
import org.andengine.opengl.texture.atlas.bitmap.BitmapTextureAtlasTextureRegionFactory;
import org.andengine.opengl.texture.region.ITextureRegion;

import at.alex.nats.Player;

import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.BodyDef.BodyType;
import com.badlogic.gdx.physics.box2d.FixtureDef;

public class Trail {
	
	private float timer = 0f;

	GameEnvironment gameEnvironment;
	Player player;
	MaxStepPhysicsWorld world;
	nats nats;
	BulletPool bulletPool;

	// BitmapTextureAtlas bitmap;
	ITextureRegion texture;
	Sprite sprite;

	FixtureDef fd;
	Body body;
	PhysicsConnector pc;

	public Trail(GameEnvironment g, Player p, MaxStepPhysicsWorld pw,
			nats nats, BulletPool bulletPool) {
		this.gameEnvironment = g;
		this.player = p;
		this.world = pw;
		this.nats = nats;
		this.bulletPool = bulletPool;

		BitmapTextureAtlasTextureRegionFactory.setAssetBasePath("gfx/");
		// bitmap = new BitmapTextureAtlas(nats.getTextureManager(), 50, 50,
		// TextureOptions.DEFAULT);
		// texture = BitmapTextureAtlasTextureRegionFactory.createFromAsset(
		// gameEnvironment.getFireBallBitmap(), nats.getApplicationContext(),
		// "FireBall.png");
		sprite = new Sprite(0, 0, gameEnvironment.getFireBallITextureRegion(),
				nats.getVertexBufferObjectManager());

		sprite.setCullingEnabled(true);

		fd = PhysicsFactory.createFixtureDef(10f, 0f, 0f);
		body = PhysicsFactory.createCircleBody(world, sprite,
				BodyType.StaticBody, fd);

		body.setActive(false);
		body.setAwake(false);
		body.setUserData(new UserData("trail", this));
	}

	public void set(float x, float y) {
		
		pc = new PhysicsConnector(sprite, body, true, true);

		float radians = gameEnvironment.getPlayerBody().getAngle();

		float a = (float) Math.sin(-radians) * -1;
		float b = (float) Math.cos(-radians) * -1;

		x += (b * 40);
		y += (a * 40);

		
		//Log.i("Usables", "X, Y: " + x + ", " + y);
		//body.setFixedRotation(true);

		body.setActive(true);
		body.setAwake(true);
		
		body.setTransform(x / 32, y / 32, 0f);

		gameEnvironment.getFireBallSpriteGroup().attachChild(sprite);
		// gameEnvironment.attachChild(sprite);
		sprite.setVisible(true);

		world.registerPhysicsConnector(pc);

		nats.getEngine().registerUpdateHandler(
				new TimerHandler(0.05f, true, new ITimerCallback() {

					@Override
					public void onTimePassed(TimerHandler pTimerHandler) {
						// TODO Auto-generated method stub
						if(!player.isGamePaused()) {
							if(timer >= 7f) {
								sprite.setVisible(false);
								gameEnvironment.getFireBallSpriteGroup().detachChild(sprite);
								nats.getEngine().unregisterUpdateHandler(pTimerHandler);
								world.unregisterPhysicsConnector(pc);
								body.setActive(false);
								body.setAwake(false);
							}else {
								timer += 0.05f;
							}
						}
					}
				}));
	}

	public void sendTrailToPool() {
		bulletPool.recycleTrail(this);
	}
}
