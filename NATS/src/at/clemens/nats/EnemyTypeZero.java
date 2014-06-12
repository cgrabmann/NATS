package at.clemens.nats;

import java.util.Random;

import org.andengine.engine.handler.timer.ITimerCallback;
import org.andengine.engine.handler.timer.TimerHandler;
import org.andengine.entity.primitive.Rectangle;
import org.andengine.entity.scene.Scene;
import org.andengine.entity.sprite.Sprite;
import org.andengine.extension.physics.box2d.PhysicsConnector;
import org.andengine.extension.physics.box2d.PhysicsFactory;
import org.andengine.extension.physics.box2d.PhysicsWorld;
import org.andengine.opengl.texture.region.TextureRegion;

import android.util.Log;
import at.alex.nats.Player;
import at.stefan.nats.EnemyPool;
import at.stefan.nats.GameEnvironment;
import at.stefan.nats.UserData;
import at.stefan.nats.nats;

import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.BodyDef.BodyType;
import com.badlogic.gdx.physics.box2d.FixtureDef;

public class EnemyTypeZero extends PEnemy {

	private final int resources = 10;

	// private float posX = 400f;
	// private float posY = 240f;
	private float moveX = 0f;
	private float moveY = 0f;

	private final int maxMoveSpeed = 200;
	private TextureRegion textur;
	private Sprite enemy;
	// private Rectangle enemy;
	private PhysicsWorld world;
	private Body body;
	private FixtureDef fd;
	private PhysicsConnector pc;
	private TimerHandler th;
	private float smovex, smovey;
	private EnemyPool enemyPool;

	public EnemyTypeZero(GameEnvironment g, TextureRegion textur, nats nats,
			PhysicsWorld world, Player p, EnemyPool enemyPool) {
		super(nats, p, g);
		this.world = world;
		this.textur = textur;
		this.enemyPool = enemyPool;
		frozen = false;
		smovex = super.movex;
		smovey = super.movey;
		// enemy = new Sprite(super.posx, super.posy, this.textur,
		// nats.getVertexBufferObjectManager());
		enemy = new Sprite(0f, 0f, super.game.getEnemyZeroTextureRegion(),
				nats.getVertexBufferObjectManager());
		enemy.setVisible(false);
		fd = PhysicsFactory.createFixtureDef(5f, 4f, 0f);
		body = PhysicsFactory.createBoxBody(world, enemy, BodyType.DynamicBody,
				fd);
		body.setActive(false);
		body.setAwake(false);
		body.setUserData(new UserData("enemyzero", this));

		pc = new PhysicsConnector(enemy, body, true, false);
	}

	@Override
	public void start() {
		this.createStartPos(super.game);
		smovex = moveX;
		smovey = moveY;
		if (enemy.hasParent()) {
			Log.i("NATS", "enemy already attached");
		} else {
			Log.i("NATS", "attach enemy");
			super.game.attachChild(enemy);
		}

		enemy.setVisible(true);

		// TODO start fly function | alle 15 msec ausf�hren
		// TODO Timehandler

		body.setActive(true);
		body.setAwake(true);

		body.setTransform(super.posx / 32, super.posy / 32, 0f);

		th = new TimerHandler(0.050f, true, new ITimerCallback() {

			@Override
			public void onTimePassed(TimerHandler pTimerHandler) {
				// TODO Auto-generated method stub
				// Log.i("NATS", "Move");
				move();
				// Log.i("NATS", "MoveX: "+moveX + ", MoveY: "+moveY);
				body.setLinearVelocity(moveX * 0.05f, moveY * 0.05f);
			}
		});

		world.registerPhysicsConnector(pc);
		nats.getEngine().registerUpdateHandler(th);
	}

	@Override
	protected void createStartPos(Scene pf) {
		super.createStartPos(pf);
		moveX = (maxMoveSpeed / 2)
				+ (int) (Math.random() * ((maxMoveSpeed - (maxMoveSpeed / 2)) + 1));
		moveY = (maxMoveSpeed / 2)
				+ (int) (Math.random() * ((maxMoveSpeed - (maxMoveSpeed / 2)) + 1));
		moveX *= (getRandomBoolean()) ? 1 : -1;
		moveY *= (getRandomBoolean()) ? 1 : -1;

	}

	@Override
	public void stop() {
		nats.getEngine().runOnUpdateThread(new Runnable() {

			@Override
			public void run() {
				// TODO Auto-generated method stub
				EnemyTypeZero.super.addRessources(resources);
				// Log.i("NATS", "stop");
				EnemyTypeZero.super.game.detachChild(enemy);
				// Log.i("NATS", "stop1");
				enemy.setVisible(false);
				// Log.i("NATS", "stop2");
				body.setActive(false);
				// Log.i("NATS", "stop3");
				body.setAwake(false);
				// Log.i("NATS", "stop4");
				// body.setLinearVelocity(0f, 0f);
				body.setTransform(-500, -340, 0.0f);
				// Log.i("NATS", "stop5");
				world.unregisterPhysicsConnector(pc);
				// Log.i("NATS", "stop6");
				nats.getEngine().unregisterUpdateHandler(th);
				// Log.i("NATS", "stop7");
				nats.getEngine().registerUpdateHandler(
						new TimerHandler(1f, new ITimerCallback() {

							@Override
							public void onTimePassed(TimerHandler pTimerHandler) {
								// TODO Auto-generated method stub
								nats.getEngine().unregisterUpdateHandler(
										pTimerHandler);
								enemyPool.recycleEnemyZero(EnemyTypeZero.this);
							}
						}));
				// Log.i("NATS", "stop8");
			}
		});

		/*
		 * Log.i("NATS", "stop"); game.detachChild(enemy); Log.i("NATS",
		 * "stop1"); enemy.setVisible(false); Log.i("NATS", "stop2");
		 * body.setActive(false); Log.i("NATS", "stop3"); body.setAwake(false);
		 * Log.i("NATS", "stop4"); // body.setLinearVelocity(0f, 0f);
		 * body.setTransform(-500, -340, 0.0f); Log.i("NATS", "stop5");
		 * world.unregisterPhysicsConnector(pc); Log.i("NATS", "stop6");
		 * nats.getEngine().unregisterUpdateHandler(th); Log.i("NATS", "stop7");
		 * enemyPool.recycleEnemyZero(this); Log.i("NATS", "stop8");
		 */
	}

	private boolean getRandomBoolean() {
		Random r = new Random();
		return r.nextBoolean();
	}

	protected void move() {
		// TODO Auto-generated method stub
		if (frozen) {
			moveX = 0;
			moveY = 0;
		} else {
			moveX = smovex;
			moveY = smovey;
		}

	}

	public void colisionNS() {
		// Log.i("NATS", "collision NS");
		smovey *= (-1);
	}

	public void colisionWE() {
		// Log.i("NATS", "collision EW");
		smovex *= (-1);
	}

	public int getResources() {
		return this.resources;
	}

}
