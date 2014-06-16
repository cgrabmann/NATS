package at.clemens.nats;

import org.andengine.engine.handler.timer.ITimerCallback;
import org.andengine.engine.handler.timer.TimerHandler;
import org.andengine.entity.sprite.Sprite;
import org.andengine.extension.physics.box2d.PhysicsConnector;
import org.andengine.extension.physics.box2d.PhysicsFactory;
import org.andengine.extension.physics.box2d.PhysicsWorld;
import org.andengine.opengl.texture.region.TextureRegion;

import at.alex.nats.Player;
import at.stefan.nats.EnemyPool;
import at.stefan.nats.GameEnvironment;
import at.stefan.nats.UserData;
import at.stefan.nats.nats;

import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.BodyDef.BodyType;
import com.badlogic.gdx.physics.box2d.FixtureDef;

public class EnemyTypeTwo extends PEnemy {

	private final int resources = 20;

	private final float maxMoveSpeed = 300f;
	private final int acceleration = 10;

	private TextureRegion textur;
	private Sprite enemy;
	// private Rectangle enemy;
	private PhysicsWorld world;
	private Body body;
	private FixtureDef fd;
	private PhysicsConnector pc;
	private TimerHandler th;
	private EnemyPool enemyPool;

	EnemyTypeTwoSmall twoSmall1;
	EnemyTypeTwoSmall twoSmall2;

	public EnemyTypeTwo(GameEnvironment g, TextureRegion textur, nats nats,
			PhysicsWorld world, Player p, EnemyPool enemyPool) {
		super(nats, p, g);
		this.world = world;
		this.textur = textur;
		this.enemyPool = enemyPool;
		// enemy = new Sprite(super.posx, super.posy, this.textur,
		// nats.getVertexBufferObjectManager());
		enemy = new Sprite(0f, 0f, super.game.getEnemyTwoTextureRegion(),
				nats.getVertexBufferObjectManager());
		enemy.setCullingEnabled(true);
		// enemy.setColor(new Color(0f, 0f, 1f));
		enemy.setVisible(false);
		fd = PhysicsFactory.createFixtureDef(0f, 0f, 0f);
		body = PhysicsFactory.createBoxBody(world, enemy, BodyType.DynamicBody,
				fd);
		body.setActive(false);
		body.setAwake(false);
		body.setUserData(new UserData("enemytwo", this));

	}

	@Override
	public void start() {
		super.createStartPos();
		if (!enemy.hasParent()) {
			super.game.getEnemyTwoSpriteGroup().attachChild(enemy);
		}
		pc = new PhysicsConnector(enemy, body, true, true);
		
		th = new TimerHandler(0.050f, true, new ITimerCallback() {

			@Override
			public void onTimePassed(TimerHandler pTimerHandler) {
				// TODO Auto-generated method stub
				move();
				// Log.i("NATS", "movex: " + EnemyTypeTwo.this.getMovex() +
				// " movey: " + EnemyTypeTwo.this.getMovey());
				body.setLinearVelocity(EnemyTypeTwo.this.getMovex() * 0.05f,
						EnemyTypeTwo.this.getMovey() * 0.05f);

				float pRotationRad = (float) Math.atan2(
						(EnemyTypeTwo.super.movex / maxMoveSpeed),
						(EnemyTypeTwo.super.movey / maxMoveSpeed));
				// Log.i("NATSRot", "" + (-pRotationRad));
				body.setTransform(EnemyTypeTwo.super.posx / 32,
						EnemyTypeTwo.super.posy / 32, -pRotationRad);
			}
		});

		// TODO start fly function | alle 15 msec ausführen
		// TODO Timehandler
		body.setTransform(super.posx / 32, super.posy / 32, 0f);
		
		body.setActive(true);
		body.setAwake(true);

		enemy.setVisible(true);

		world.registerPhysicsConnector(pc);
		nats.getEngine().registerUpdateHandler(th);
	}

	@Override
	public void stop() {
		final float x = super.posx;
		final float y = super.posy;
		
		nats.getEngine().runOnUpdateThread(new Runnable() {

			@Override
			public void run() {
				// TODO Auto-generated method stub
				EnemyTypeTwo.super.addRessources(resources);
				EnemyTypeTwo.super.game.getEnemyTwoSpriteGroup().detachChild(
						enemy);
				body.setLinearVelocity(0f, 0f);
				body.setTransform(-500, -340, 0.0f);
				body.setActive(false);
				body.setAwake(false);
				world.unregisterPhysicsConnector(pc);
				nats.getEngine().unregisterUpdateHandler(th);
				enemy.setVisible(false);
				EnemyTypeTwo.this.reset();
				
				twoSmall1 = enemyPool.onAllocateEnemytwoS();
				twoSmall2 = enemyPool.onAllocateEnemytwoS();
	
				twoSmall1.start(x, y);
				twoSmall2.start(x, y);

				nats.getEngine().registerUpdateHandler(
						new TimerHandler(1f, new ITimerCallback() {

							@Override
							public void onTimePassed(TimerHandler pTimerHandler) {
								// TODO Auto-generated method stub
								nats.getEngine().unregisterUpdateHandler(
										pTimerHandler);
								enemyPool.recycleEnemyTwo(EnemyTypeTwo.this);
							}
						}));
			}
		});

	}

	protected void move() {
		float offsetX, offsetY;
		float pPosx, pPosy;

		super.posx = enemy.getX();
		super.posy = enemy.getY();

		if (frozen) {
			super.movex = 0;
			super.movey = 0;
			return;
		}else{
			super.movex = super.smovex;
			super.movey = super.smovey;
		}

		// Log.i("NATS", "pposx: " + super.player.getPosX() + " pposy: " +
		// super.player.getPosY());
		pPosx = super.player.getPosX();
		pPosy = super.player.getPosY();
		offsetX = pPosx - super.posx;
		offsetY = pPosy - super.posy;

		// Adjust move direction X to new player position
		if (offsetX < 0) {
			super.movex -= (super.movex > -this.maxMoveSpeed) ? acceleration
					: 0;
		} else if (offsetX > 0) {
			super.movex += (super.movex < this.maxMoveSpeed) ? acceleration : 0;
		} else if (offsetX == 0 && super.movex != 0) {
			super.movex = (super.movex > 0) ? super.movex - acceleration
					: super.movex + acceleration;
		}

		// Adjust move direction Y to new player position
		if (offsetY < 0) {
			super.movey -= (super.movey > -this.maxMoveSpeed) ? acceleration
					: 0;
		} else if (offsetY > 0) {
			super.movey += (super.movey < this.maxMoveSpeed) ? acceleration : 0;
		} else if (offsetY == 0 && super.movey != 0) {
			super.movey = (super.movey > 0) ? super.movey - acceleration
					: super.movey + acceleration;
		}
		
		super.smovex = super.movex;
		super.smovey = super.movey;

		return;
	}

	public float getPosx() {
		return super.posx;
	}

	public float getPosy() {
		return super.posy;
	}

	public int getResources() {
		return this.resources;
	}

	private int getMovex() {
		return super.movex;
	}

	private int getMovey() {
		return super.movey;
	}

	@Override
	public void deactivate() {
		
		// TODO Auto-generated method stub
		nats.getEngine().runOnUpdateThread(new Runnable() {

			@Override
			public void run() {
				// TODO Auto-generated method stub
				// Log.i("NATS", "stop");
				nats.getEngine().unregisterUpdateHandler(th);
				enemy.setVisible(false);
				body.setLinearVelocity(0f, 0f);
				
				body.setTransform(-500, -340, 0.0f);
				// Log.i("NATS", "stop2");
				body.setActive(false);
				// Log.i("NATS", "stop3");
				body.setAwake(false);
				EnemyTypeTwo.super.game.getEnemyTwoSpriteGroup().detachChild(
						enemy);
				// Log.i("NATS", "stop1");
				
				
				// Log.i("NATS", "stop4");
				// body.setLinearVelocity(0f, 0f);
				
				// Log.i("NATS", "stop5");
				world.unregisterPhysicsConnector(pc);
				// Log.i("NATS", "stop6");
				
				
				EnemyTypeTwo.this.reset();
				// Log.i("NATS", "stop7");
				enemyPool.recycleEnemyTwo(EnemyTypeTwo.this);
			}
		});
	}
	
	@Override
	protected void reset(){
		super.reset();
		this.pc = null;
		this.th = null;
	}
}
