package at.clemens.nats;

import org.andengine.engine.handler.timer.ITimerCallback;
import org.andengine.engine.handler.timer.TimerHandler;
import org.andengine.entity.primitive.Rectangle;
import org.andengine.entity.scene.Scene;
import org.andengine.extension.physics.box2d.PhysicsConnector;
import org.andengine.extension.physics.box2d.PhysicsFactory;
import org.andengine.extension.physics.box2d.PhysicsWorld;
import org.andengine.opengl.texture.region.TextureRegion;
import org.andengine.util.adt.color.Color;

import at.alex.nats.Player;
import at.stefan.nats.EnemyPool;
import at.stefan.nats.UserData;
import at.stefan.nats.nats;

import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.BodyDef.BodyType;
import com.badlogic.gdx.physics.box2d.FixtureDef;

public class EnemyTypeTwoSmall extends PEnemy {

	private final int maxMoveSpeed = 200;
	private TextureRegion textur;
	// private Sprite enemy;
	private Rectangle enemy;
	private Scene game;
	private PhysicsWorld world;
	private Body body;
	private FixtureDef fd;
	private PhysicsConnector pc;
	private TimerHandler th;
	private EnemyPool enemyPool;

	private float movex, movey = 0;

	Player player;

	public EnemyTypeTwoSmall(Scene pf, TextureRegion textur, nats nats,
			PhysicsWorld world, Player p, EnemyPool enemyPool) {
		super(nats);
		this.world = world;
		this.game = pf;
		this.textur = textur;
		this.player = p;
		this.enemyPool = enemyPool;
		// enemy = new Sprite(super.posx, super.posy, this.textur,
		// nats.getVertexBufferObjectManager());
		enemy = new Rectangle(0, 0, 20, 20, nats.getVertexBufferObjectManager());
		enemy.setColor(new Color(1f, 0f, 0f));
		enemy.setVisible(false);
		fd = PhysicsFactory.createFixtureDef(0f, 0f, 0f);
		body = PhysicsFactory.createBoxBody(world, enemy, BodyType.DynamicBody,
				fd);
		body.setActive(false);
		body.setAwake(false);
		body.setUserData(new UserData("enemytwosmall", this));

		th = new TimerHandler(0.050f, true, new ITimerCallback() {

			@Override
			public void onTimePassed(TimerHandler pTimerHandler) {
				// TODO Auto-generated method stub
				move();
				body.setLinearVelocity(getMovex(), getMovey());
			}
		});

		pc = new PhysicsConnector(enemy, body, true, false);
	}

	public void start(float x, float y) {
		// this.createStartPos(game);
		game.attachChild(enemy);
		enemy.setVisible(true);

		// TODO start fly function | alle 15 msec ausführen
		// TODO Timehandler

		body.setActive(true);
		body.setAwake(true);

		body.setTransform(x / 32, y / 32, 0f);

		world.registerPhysicsConnector(pc);
		nats.getEngine().registerUpdateHandler(th);
	}

	@Override
	protected void createStartPos(Scene pf) {
		super.createStartPos(pf);
		this.move();
	}

	@Override
	public void stop() {
		nats.getEngine().runOnUpdateThread(new Runnable() {

			@Override
			public void run() {
				// TODO Auto-generated method stub
				game.detachChild(enemy);
				enemy.setVisible(false);
				body.setActive(false);
				body.setAwake(false);
				body.setLinearVelocity(0f, 0f);
				body.setTransform(-500, -340, 0.0f);
				world.unregisterPhysicsConnector(pc);
				nats.getEngine().unregisterUpdateHandler(th);

				nats.getEngine().registerUpdateHandler(
						new TimerHandler(1f, new ITimerCallback() {

							@Override
							public void onTimePassed(TimerHandler pTimerHandler) {
								// TODO Auto-generated method stub
								nats.getEngine().unregisterUpdateHandler(
										pTimerHandler);
								enemyPool
										.recycleEnemyTwoSmall(EnemyTypeTwoSmall.this);
							}
						}));
			}
		});

	}

	protected void move() {
		float offsetX, offsetY;
		float pPosx, pPosy;

		if (frozen) {
			movex = 0;
			movey = 0;
			return;
		}

		pPosx = player.getPosX();
		pPosy = player.getPosY();
		offsetX = pPosx - posx;
		offsetY = pPosy - posy;

		// Adjust move direction X to new player position
		if (offsetX < 0) {
			movex -= (movex > -maxMoveSpeed) ? 1 : 0;
		} else if (offsetX > 0) {
			movex += (movex < maxMoveSpeed) ? 1 : 0;
		} else if (offsetX == 0 && movex != 0) {
			movex = (movex > 0) ? movex - 1 : movex + 1;
		}

		// Adjust move direction Y to new player position
		if (offsetY < 0) {
			movey -= (movey > -maxMoveSpeed) ? 1 : 0;
		} else if (offsetY > 0) {
			movey += (movey < maxMoveSpeed) ? 1 : 0;
		} else if (offsetY == 0 && movey != 0) {
			movey = (movey > 0) ? movey - 1 : movey + 1;
		}

		return;
	}

	@Override
	public void start() {
		// TODO Auto-generated method stub

	}

}
