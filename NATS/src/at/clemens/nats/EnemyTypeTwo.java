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

import android.util.Log;
import at.alex.nats.Player;
import at.stefan.nats.EnemyPool;
import at.stefan.nats.UserData;
import at.stefan.nats.nats;

import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.BodyDef.BodyType;
import com.badlogic.gdx.physics.box2d.FixtureDef;

public class EnemyTypeTwo extends PEnemy{
	

	private final int resources = 20;

	private final int maxMoveSpeed = 150;
	private final int acceleration = 5;

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
	
	EnemyTypeTwoSmall twoSmall1;
	EnemyTypeTwoSmall twoSmall2;

	public EnemyTypeTwo(Scene pf, TextureRegion textur, nats nats, PhysicsWorld world, Player p, EnemyPool enemyPool) {
		super(nats, p);
		this.world = world;
		this.game = pf;
		this.textur = textur;
		this.enemyPool = enemyPool;
		//enemy = new Sprite(super.posx, super.posy, this.textur, nats.getVertexBufferObjectManager());
		enemy = new Rectangle(0, 0, 60, 60, nats.getVertexBufferObjectManager());
		enemy.setColor(new Color(0f, 0f, 1f));
		enemy.setVisible(false);
		fd = PhysicsFactory.createFixtureDef(0f, 0f, 0f);
		body = PhysicsFactory.createBoxBody(world, enemy, BodyType.DynamicBody, fd);
		body.setActive(false);
		body.setAwake(false);
		body.setUserData(new UserData("enemytwo", this));
		
		th = new TimerHandler(0.050f, true, new ITimerCallback() {

			@Override
			public void onTimePassed(TimerHandler pTimerHandler) {
				// TODO Auto-generated method stub
				move();
				Log.i("NATS", "movex: " + EnemyTypeTwo.this.getMovex() + " movey: " + EnemyTypeTwo.this.getMovey());
				body.setLinearVelocity(EnemyTypeTwo.this.getMovex(), EnemyTypeTwo.this.getMovey());
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
		
		body.setTransform(super.posx/32, super.posy/32, 0f);

		world.registerPhysicsConnector(pc);
		nats.getEngine().registerUpdateHandler(th);
	}
	
	@Override
	protected void createStartPos(Scene pf){
		super.createStartPos(pf);
	}

	@Override
	public void stop(){
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
				
				twoSmall1 = enemyPool.onAllocateEnemytwoS();
				twoSmall2 = enemyPool.onAllocateEnemytwoS();
				
				twoSmall1.start(enemy.getX(), enemy.getY());
				twoSmall2.start(enemy.getX(), enemy.getY());
				
				nats.getEngine().registerUpdateHandler(new TimerHandler(1f, new ITimerCallback() {
					
					@Override
					public void onTimePassed(TimerHandler pTimerHandler) {
						// TODO Auto-generated method stub
						nats.getEngine().unregisterUpdateHandler(pTimerHandler);
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
		
		if(frozen){
			super.movex = 0;
			super.movey = 0;
			return;
		}
		
		Log.i("NATS", "pposx: " + super.player.getPosX() + " pposy: " + super.player.getPosY());
		pPosx = super.player.getPosX();
		pPosy = super.player.getPosY();
		offsetX = pPosx - super.posx;
		offsetY = pPosy - super.posy;
		
		//Adjust move direction X to new player position
		if(offsetX < 0){
			super.movex -= (super.movex > -this.maxMoveSpeed)?acceleration:0;
		}else if(offsetX > 0){
			super.movex += (super.movex < this.maxMoveSpeed)?acceleration:0;
		}else if(offsetX == 0 && super.movex != 0){
			super.movex = (super.movex > 0)?super.movex - acceleration:super.movex + acceleration;
		}
		
		//Adjust move direction Y to new player position
		if(offsetY < 0){
			super.movey -= (super.movey > -this.maxMoveSpeed)?acceleration:0;
		}else if(offsetY > 0){
			super.movey += (super.movey < this.maxMoveSpeed)?acceleration:0;
		}else if(offsetY == 0 && super.movey != 0){
			super.movey = (super.movey > 0)?super.movey - acceleration:super.movey + acceleration;
		}
		
		return;
	}

	public float getPosx(){
		return super.posx;
	}
	
	public float getPosy(){
		return super.posy;
	}
	

	public int getResources() {
		return this.resources;
	}
	
	private int getMovex(){
		return super.movex;
	}
	
	private int getMovey(){
		return super.movey;
	}
}
