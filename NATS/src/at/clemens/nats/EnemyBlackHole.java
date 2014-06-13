package at.clemens.nats;

import org.andengine.engine.handler.timer.ITimerCallback;
import org.andengine.engine.handler.timer.TimerHandler;
import org.andengine.entity.primitive.Rectangle;
import org.andengine.entity.scene.Scene;
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

public class EnemyBlackHole extends PEnemy{
	
	private int size = 150;
	private final int standardSize = 150;
	
	private final int maxMoveSpeed = 10;
	private TextureRegion textur;
	//private Sprite enemy;
	private Rectangle enemy;
	private PhysicsWorld world;
	private Body body;
	private FixtureDef fd;
	private PhysicsConnector pc;
	private TimerHandler th;
	private EnemyPool enemyPool;

	public EnemyBlackHole(GameEnvironment g, TextureRegion textur, nats nats, PhysicsWorld world, Player p, EnemyPool enemyPool) {
		super(nats, p, g);
		this.world = world;
		this.textur = textur;
		this.size = 0;
		this.enemyPool = enemyPool;
		//enemy = new Sprite(super.posx, super.posy, this.textur, nats.getVertexBufferObjectManager());
		enemy = new Rectangle(0, 0, 100, 100, nats.getVertexBufferObjectManager());
		enemy.setCullingEnabled(true);
		enemy.setVisible(false);
		fd = PhysicsFactory.createFixtureDef(0f, 0f, 0f);
		body = PhysicsFactory.createBoxBody(world, enemy, BodyType.DynamicBody, fd);
		body.setActive(false);
		body.setAwake(false);
		body.setUserData(new UserData("enemyblackhole", this));
		
		th = new TimerHandler(0.050f, true, new ITimerCallback() {

			@Override
			public void onTimePassed(TimerHandler pTimerHandler) {
				// TODO Auto-generated method stub
				if(size == 100){
					// TODO create enemythree
				}else if(size <= 0){
					// TODO destroy it self 
				}else{
					// TODO resize
				}
			}
		});
		
		pc = new PhysicsConnector(enemy, body, true, false);
	}
	
	@Override
	public void start(){
		this.createStartPos(super.game);
		this.size = 50;
		super.game.attachChild(enemy);
		enemy.setVisible(true);
		
		//TODO start fly function | alle 15 msec ausführen
		//TODO Timehandler
		
		body.setActive(true);
		body.setAwake(true);
		
		body.setTransform(super.posx, super.posy, 0f);

		world.registerPhysicsConnector(pc);
		nats.getEngine().registerUpdateHandler(th);
	}
	
	@Override
	protected void createStartPos(Scene pf){
		super.createStartPos(pf);
	}

	@Override
	public void stop(){
		super.game.detachChild(enemy);
		enemy.setVisible(false);
		body.setActive(false);
		body.setAwake(false);
		body.setLinearVelocity(0f, 0f);
		body.setTransform(-500, -340, 0.0f);
		world.unregisterPhysicsConnector(pc);
		nats.getEngine().unregisterUpdateHandler(th);
	}
	
	
	@Override
	public void deactivate() {
		// TODO Auto-generated method stub
		nats.getEngine().runOnUpdateThread(new Runnable() {

			@Override
			public void run() {
				// TODO Auto-generated method stub
				// Log.i("NATS", "stop");
				EnemyBlackHole.super.game.detachChild(enemy);
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
				enemyPool.recycleEnemyBlackHole(EnemyBlackHole.this);
			}
		});
	}
	
	public void increaseSize() {
		this.size += 10;
	}
	
	public void deacreaseSize() {
		this.size -= 5;
	}
	
}
