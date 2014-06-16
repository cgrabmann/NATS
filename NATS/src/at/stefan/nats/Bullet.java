package at.stefan.nats;

import org.andengine.engine.handler.timer.ITimerCallback;
import org.andengine.engine.handler.timer.TimerHandler;
import org.andengine.entity.primitive.Rectangle;
import org.andengine.entity.scene.Scene;
import org.andengine.extension.physics.box2d.PhysicsConnector;
import org.andengine.extension.physics.box2d.PhysicsFactory;
import org.andengine.util.adt.color.Color;

import at.alex.nats.Player;

import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.BodyDef.BodyType;
import com.badlogic.gdx.physics.box2d.FixtureDef;

public class Bullet {
	
	private int bulletType = 0;

	BulletPool bulletPool;

	FixtureDef fd;
	PhysicsConnector pc;
	Rectangle r;
	Scene scene;
	MaxStepPhysicsWorld physicsWorld;
	Body body;
	nats nats;
	Player player;
	Vector2 einheitsVector;
	float bulletSpeed = 350;
	float shotfrequence;

	TimerHandler th;

	public Bullet(Scene s, Player p, MaxStepPhysicsWorld pw, nats nats,
			BulletPool bulletPool) {
		this.scene = s;
		this.bulletPool = bulletPool;
		this.player = p;
		this.physicsWorld = pw;
		this.nats = nats;
		
		bulletType = 1;

		//shotfrequence = player.getShotFrequence();

		r = new Rectangle(0, 0, 10, 10, nats.getVertexBufferObjectManager());
		r.setColor(new Color(1f, 1f, 1f));
		r.setCullingEnabled(true);
		// scene.attachChild(r);
		// r.setVisible(true);
		fd = PhysicsFactory.createFixtureDef(3.0f, 2.0f, 0.0f);
		body = PhysicsFactory.createBoxBody(physicsWorld, r,
				BodyType.DynamicBody, fd);
		body.setActive(false);
		body.setAwake(false);
		body.setUserData(new UserData("bullet", this));

		pc = new PhysicsConnector(r, body, true, false);
	}
	
	public Bullet(Scene s, Player p, MaxStepPhysicsWorld pw, nats nats,
			BulletPool bulletPool, Color c) {
		this.scene = s;
		this.bulletPool = bulletPool;
		this.player = p;
		this.physicsWorld = pw;
		this.nats = nats;
		
		bulletType = 2;

		r = new Rectangle(0, 0, 10, 10, nats.getVertexBufferObjectManager());
		r.setColor(c);
		r.setCullingEnabled(true);
		// scene.attachChild(r);
		// r.setVisible(true);
		fd = PhysicsFactory.createFixtureDef(3.0f, 2.0f, 0.0f);
		body = PhysicsFactory.createBoxBody(physicsWorld, r,
				BodyType.DynamicBody, fd);
		body.setActive(false);
		body.setAwake(false);
		body.setUserData(new UserData("bullet", this));

		pc = new PhysicsConnector(r, body, true, false);
	}

	public void fireBullet(Vector2 direction) {
		//Log.i("Bullet", "Fire Bullet");
		// Einheitsvektor berechnen
		float betrag = (float) Math.sqrt(Math.pow(direction.x, 2)
				+ Math.pow(direction.y, 2));
		einheitsVector = new Vector2(direction.x / betrag, direction.y / betrag);

		th = new TimerHandler(0.050f, true, new ITimerCallback() {

			@Override
			public void onTimePassed(TimerHandler pTimerHandler) {
				// TODO Auto-generated method stub
				body.setLinearVelocity(einheitsVector.x * bulletSpeed * 0.05f,
						einheitsVector.y * bulletSpeed * 0.05f);
			}
		});

		// body.setTransform(direction.x * 105, direction.y * 105, 0f);

		this.activate(einheitsVector);
	}

	public void sendBulletToPool() {
		this.deactivate();
		if(bulletType == 1) {
			bulletPool.recycleBullet(this);
		}else if(bulletType == 2) {
			bulletPool.recycleGunner(this);
		}
		
	}

	public void setVisible(boolean b) {
		// TODO Auto-generated method stub
		r.setVisible(b);
	}

	public Body getBody() {
		return body;
	}

	public PhysicsConnector getPhysicsConnector() {
		return pc;
	}

	public TimerHandler getTimerHandler() {
		return th;
	}

	public Bullet getBullet() {
		return this;
	}

	public void activate(Vector2 v) {
		// TODO Auto-generated method stub
		if(!r.hasParent()) {
			scene.attachChild(r);
		}
		//Log.i("Bullet", "Activate Bullet");
		body.setActive(true);
		body.setAwake(true);
		r.setVisible(true);
		
		body.setTransform(player.getPosX() / 32, player.getPosY() / 32,
				(float) Math.PI / 2);

		
		//body.setTransform((player.getPosX() + v.x * 45) / 32,
		//		(player.getPosY() + v.y * 45) / 32, (float) Math.PI / 2);
		// r.setIgnoreUpdate(false);
		

		physicsWorld.registerPhysicsConnector(pc);
		nats.getEngine().registerUpdateHandler(th);

	}

	public void deactivate() {
		nats.getEngine().runOnUpdateThread(new Runnable() {
			
			@Override
			public void run() {
				// TODO Auto-generated method stub
				body.setTransform(-500, -340, 0.0f);
				r.setVisible(false);
				body.setActive(false);
				body.setAwake(false);
				scene.detachChild(r);
				nats.getEngine().unregisterUpdateHandler(th);
				physicsWorld.unregisterPhysicsConnector(pc);
				// r.setIgnoreUpdate(true);
			}
		});
		//Log.i("Bullet", "Deactivate Bullet");
		
	}
	
	public Rectangle getSprite() {
		return this.r;
	}

}
