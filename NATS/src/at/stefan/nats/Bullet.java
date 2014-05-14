package at.stefan.nats;

import org.andengine.engine.handler.timer.ITimerCallback;
import org.andengine.engine.handler.timer.TimerHandler;
import org.andengine.entity.primitive.Rectangle;
import org.andengine.entity.scene.Scene;
import org.andengine.entity.sprite.AnimatedSprite;
import org.andengine.extension.physics.box2d.PhysicsConnector;
import org.andengine.extension.physics.box2d.PhysicsFactory;
import org.andengine.extension.physics.box2d.PhysicsWorld;
import org.andengine.opengl.texture.TextureOptions;
import org.andengine.opengl.texture.atlas.bitmap.BitmapTextureAtlasTextureRegionFactory;
import org.andengine.opengl.texture.atlas.bitmap.BuildableBitmapTextureAtlas;
import org.andengine.opengl.texture.region.ITiledTextureRegion;
import org.andengine.opengl.texture.region.TextureRegion;

import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.BodyDef.BodyType;
import com.badlogic.gdx.physics.box2d.FixtureDef;

public class Bullet {

	TextureRegion textureRegion;
	FixtureDef fd;
	PhysicsConnector pc;
	Rectangle r;
	Vector2 direction;
	Vector2 einheitsVector;
	PhysicsWorld physicsWorld;
	Body body;
	nats nats;
	AnimatedSprite bulletAnimatedSprite;
	
	private float startPosX;
	private float startPosY;
	
	BuildableBitmapTextureAtlas bulletBuildableBitmapTextureAtlas;
	ITiledTextureRegion bulletITiledTextureRegion;
	
	TimerHandler th;
	
	/*public Bullet(TextureRegion textureRegion) {
		this.textureRegion = textureRegion;
	}
	
	public void fireBullet() {
		
		// Einheitsvektor berechnen
		float betrag = (float) Math.sqrt(Math.pow(direction.x, 2)
				+ Math.pow(direction.y, 2));
		einheitsVector = new Vector2(direction.x / betrag, direction.y / betrag);

	}*/

	public Bullet(float posX, float posY, Vector2 direction, PhysicsWorld pw,
			nats nats) {
		this.startPosX = posX;
		this.startPosY = posY;
		this.direction = direction;
		this.physicsWorld = pw;
		this.nats = nats;

		
		  bulletBuildableBitmapTextureAtlas = new BuildableBitmapTextureAtlas(
		  nats.getTextureManager(), 10, 10, TextureOptions.DEFAULT);
		  bulletITiledTextureRegion = BitmapTextureAtlasTextureRegionFactory
		  .createTiledFromAsset(bulletBuildableBitmapTextureAtlas,
		  nats.getApplicationContext(), "Bullet.png", 1, 1);
		  bulletBuildableBitmapTextureAtlas.load();
		  
		  bulletAnimatedSprite = new AnimatedSprite(startPosX, startPosY,
		  bulletITiledTextureRegion, nats.getVertexBufferObjectManager());
		 
		r = new Rectangle(startPosX, startPosY, 10, 10,
				nats.getVertexBufferObjectManager());

		fd = PhysicsFactory.createFixtureDef(0.0f, 0.0f, 0.0f);
		body = PhysicsFactory.createBoxBody(physicsWorld, r,
				BodyType.DynamicBody, fd);

		pc = new PhysicsConnector(r, body, true, false);
		// body.setUserData("bullet");
		body.setUserData(new UserData("bullet", r, pc));
		// body.setUserData("bullet");
		// body = PhysicsFactory.createCircleBody(physicsWorld,
		// bulletAnimatedSprite, BodyType.DynamicBody, fd);
		body.setTransform(startPosX / 32, startPosY / 32, 0f);
		// body = PhysicsFactory.createBoxBody(physicsWorld, bullet,
		// BodyType.DynamicBody, fd);

		th = new TimerHandler(0.05f, true, new ITimerCallback() {

			@Override
			public void onTimePassed(TimerHandler pTimerHandler) {
				// TODO Auto-generated method stub
				body.setLinearVelocity(einheitsVector.x * 20 * 0.05f,
						einheitsVector.y * 20 * 0.05f);
			}
		});
	}

	public void fireBullet(Scene s) {
		s.attachChild(r);
		physicsWorld.registerPhysicsConnector(pc);
		//physicsWorld.unregisterPhysicsConnector(pc);
		// Einheitsvektor berechnen
		float betrag = (float) Math.sqrt(Math.pow(direction.x, 2)
				+ Math.pow(direction.y, 2));
		einheitsVector = new Vector2(direction.x / betrag, direction.y / betrag);

		nats.getEngine().registerUpdateHandler(th);

	}

	public void stopBullet() {
		nats.getEngine().unregisterUpdateHandler(th);
		r.setVisible(false);
	}

	public void setIgnoreUpdate(boolean b) {
		// TODO Auto-generated method stub
		
	}

	public void setVisible(boolean b) {
		// TODO Auto-generated method stub
		
	}

	public void reset() {
		// TODO Auto-generated method stub
		
	}

}
