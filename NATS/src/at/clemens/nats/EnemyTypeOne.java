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
import at.stefan.nats.UserData;
import at.stefan.nats.nats;

import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.BodyDef.BodyType;
import com.badlogic.gdx.physics.box2d.FixtureDef;

public class EnemyTypeOne extends PEnemy{
	
	private final int maxMoveSpeed = 10;
	private TextureRegion textur;
	//private Sprite enemy;
	private Rectangle enemy;
	private Scene game;
	private PhysicsWorld world;
	private Body body;
	private FixtureDef fd;
	private PhysicsConnector pc;
	private TimerHandler th;
	private EnemyPool enemyPool;

	public EnemyTypeOne(Scene pf, TextureRegion textur, nats nats, PhysicsWorld world, Player p, EnemyPool enemyPool) {
		super(nats);
		this.world = world;
		this.game = pf;
		this.textur = textur;
		super.player = p;
		this.enemyPool = enemyPool;
		//enemy = new Sprite(super.posx, super.posy, this.textur, nats.getVertexBufferObjectManager());
		enemy = new Rectangle(0, 0, 100, 100, nats.getVertexBufferObjectManager());
		enemy.setVisible(false);
		fd = PhysicsFactory.createFixtureDef(0f, 0f, 0f);
		body = PhysicsFactory.createBoxBody(world, enemy, BodyType.DynamicBody, fd);
		body.setActive(false);
		body.setAwake(false);
		body.setUserData(new UserData("enemyzero", this));
		
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
	
	@Override
	public void start(){
		this.createStartPos(game);
		game.attachChild(enemy);
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
		this.move();
	}

	@Override
	public void stop(){
		game.detachChild(enemy);
		enemy.setVisible(false);
		body.setActive(false);
		body.setAwake(false);
		body.setLinearVelocity(0f, 0f);
		body.setTransform(-500, -340, 0.0f);
		world.unregisterPhysicsConnector(pc);
		nats.getEngine().unregisterUpdateHandler(th);
	}
	
	protected void move() {
		float offsetX, offsetY;
		float pPosx, pPosy;
		
		if(frozen){
			super.movex = 0;
			super.movey = 0;
			return;
		}
		
		pPosx = super.player.getPosX();
		pPosy = super.player.getPosY();
		offsetX = pPosx - super.posx;
		offsetY = pPosy - super.posy;
		
		//Adjust move direction X to new player position
		if(offsetX < 0){
			super.movex -= (super.movex > -this.maxMoveSpeed)?1:0;
		}else if(offsetX > 0){
			super.movex += (super.movex < this.maxMoveSpeed)?1:0;
		}else if(offsetX == 0 && super.movex != 0){
			super.movex = (super.movex > 0)?super.movex - 1:super.movex + 1;
		}
		
		//Adjust move direction Y to new player position
		if(offsetY < 0){
			super.movey -= (super.movey > -this.maxMoveSpeed)?1:0;
		}else if(offsetY > 0){
			super.movey += (super.movey < this.maxMoveSpeed)?1:0;
		}else if(offsetY == 0 && super.movey != 0){
			super.movey = (super.movey > 0)?super.movey - 1:super.movey + 1;
		}
		
		return;
	}

}
