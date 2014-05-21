package at.clemens.nats;

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

public class EnemyTypeOne extends PEnemy{
	
	private final int maxMoveSpeed = 10;
	private TextureRegion textur;
	private Sprite enemy;
	private Scene games;
	private PhysicsWorld world;
	private Body body;
	private FixtureDef fd;

	public EnemyTypeOne(Scene pf, TextureRegion textur, nats nats, PhysicsWorld world) {
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
	public boolean update(Player player, Scene pf) {
		boolean hit = false;
		move(player);
		
		return hit;
	}

	@Override
	protected void move(Player player) {
		float offsetX, offsetY;
		float pPosx, pPosy;
		
		pPosx = player.getPosX();
		pPosy = player.getPosY();
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
