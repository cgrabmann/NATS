package at.stefan.nats;

import org.andengine.engine.handler.timer.ITimerCallback;
import org.andengine.engine.handler.timer.TimerHandler;
import org.andengine.entity.primitive.Rectangle;
import org.andengine.entity.scene.Scene;
import org.andengine.extension.physics.box2d.PhysicsConnector;
import org.andengine.extension.physics.box2d.PhysicsFactory;
import org.andengine.extension.physics.box2d.PhysicsWorld;

import at.alex.nats.Player;

import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.BodyDef.BodyType;
import com.badlogic.gdx.physics.box2d.FixtureDef;

public class Bullet {
	
	BulletPool bulletPool;

	FixtureDef fd;
	PhysicsConnector pc;
	Rectangle r;
	Scene scene;
	PhysicsWorld physicsWorld;
	Body body;
	nats nats;
	Player player;
	Vector2 einheitsVector;
	float bulletSpeed = 200;
	float shotfrequence;

	TimerHandler th;

	public Bullet(Scene s, Player p, PhysicsWorld pw, nats nats, BulletPool bulletPool) {
		this.scene = s;
		this.bulletPool = bulletPool;
		this.player = p;
		this.physicsWorld = pw;
		this.nats = nats;
		
		shotfrequence = player.getShotFrequence();

		r = new Rectangle(0, 0, 10, 10,
				nats.getVertexBufferObjectManager());
		r.setVisible(false);
		fd = PhysicsFactory.createFixtureDef(0.0f, 0.0f, 0.0f);
		body = PhysicsFactory.createBoxBody(physicsWorld, r,
				BodyType.DynamicBody, fd);
		body.setActive(false);
		body.setAwake(false);
		body.setUserData(new UserData("bullet", this));
		
		pc = new PhysicsConnector(r, body, true, false);
	}

	public void fireBullet(Vector2 direction) {
		

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

		this.activate();
	}
	
	public void sendBulletToPool() {
		this.deactivate();
		bulletPool.onHandleRecycleItem(this);
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

	private void activate() {
		// TODO Auto-generated method stub
		scene.attachChild(r);
		r.setVisible(true);
		body.setActive(true);
		body.setAwake(true);
		
		body.setTransform(player.getPosX() / 32, player.getPosY() / 32,
				(float) Math.PI / 2);
		
		physicsWorld.registerPhysicsConnector(pc);
		nats.getEngine().registerUpdateHandler(th);
		
	}
	
	private void deactivate() {
		scene.detachChild(r);
		r.setVisible(false);
		body.setTransform(-500, -340, 0.0f);
		body.setActive(false);
		body.setAwake(false);
		physicsWorld.unregisterPhysicsConnector(pc);
		nats.getEngine().unregisterUpdateHandler(th);
	}
	
	public void increaseShotFrequence() {
		this.shotfrequence -= 0.005f;
	}
	
}
