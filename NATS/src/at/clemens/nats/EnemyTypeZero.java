package at.clemens.nats;

import java.util.Random;

import org.andengine.entity.scene.Scene;
import org.andengine.entity.sprite.Sprite;
import org.andengine.extension.physics.box2d.PhysicsConnector;
import org.andengine.extension.physics.box2d.PhysicsFactory;
import org.andengine.extension.physics.box2d.PhysicsWorld;
import org.andengine.opengl.texture.region.TextureRegion;

import at.alex.nats.Player;
import at.stefan.nats.nats;

import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.BodyDef.BodyType;
import com.badlogic.gdx.physics.box2d.FixtureDef;

public class EnemyTypeZero extends PEnemy{
	
	private final int maxMoveSpeed = 10;
	private TextureRegion textur;
	private Sprite enemy;
	private Scene games;
	private PhysicsWorld world;
	private Body body;
	private FixtureDef fd;

	public EnemyTypeZero(Scene pf, TextureRegion textur, nats nats, PhysicsWorld world) {
		this.world = world;
		this.games = pf;
		this.textur = textur;
		enemy = new Sprite(super.posx, super.posy, this.textur, nats.getVertexBufferObjectManager());
		fd = PhysicsFactory.createFixtureDef(0f, 0f, 0f);
		body = PhysicsFactory.createBoxBody(world, enemy, BodyType.DynamicBody, fd);
	}
	
	public void start(){
		games.attachChild(enemy);
		world.registerPhysicsConnector(new PhysicsConnector(enemy, body, true, false));
		//TODO start fly function | alle 15 msec ausführen
		//TODO Timehandler
		body.setLinearVelocity(movex, movey);
	}
	
	@Override
	public void createStartPos(Scene pf){
		super.createStartPos(pf);
		super.movex = (maxMoveSpeed/2) + (int)(Math.random() * ((maxMoveSpeed - (maxMoveSpeed/2)) + 1));
		super.movey = (maxMoveSpeed/2) + (int)(Math.random() * ((maxMoveSpeed - (maxMoveSpeed/2)) + 1));
		super.movex *= (getRandomBoolean())?1:-1;
		super.movey *= (getRandomBoolean())?1:-1;
	}

	@Override
	public boolean update(Player player, Scene pf) {
		boolean hit = false;
		move(player);
		
		return hit;
	}

	@Override
	protected void move(Player player) {
		
		return;
	}
	
	private boolean getRandomBoolean(){
		Random r = new Random();
		return r.nextBoolean();
	}

}
