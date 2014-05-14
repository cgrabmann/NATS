package at.clemens.nats;

import java.util.Random;

import org.andengine.entity.scene.Scene;
import org.andengine.opengl.texture.region.ITextureRegion;

import at.alex.nats.Player;

public class EnemyTypeZero extends PEnemy{
	
	private final int maxMoveSpeed = 10;
	private final float maxHeight, maxWidth;
	private ITextureRegion textur;

	public EnemyTypeZero(Scene pf, ITextureRegion[] textur) {
		super(pf);
		this.textur = textur[1];
		super.size = 100;
		super.movex = (maxMoveSpeed/2) + (int)(Math.random() * ((maxMoveSpeed - (maxMoveSpeed/2)) + 1));
		super.movey = (maxMoveSpeed/2) + (int)(Math.random() * ((maxMoveSpeed - (maxMoveSpeed/2)) + 1));
		super.movex *= (getRandomBoolean())?1:-1;
		super.movey *= (getRandomBoolean())?1:-1;
		this.maxHeight = pf.getScaleX();
		this.maxWidth = pf.getScaleY();
	}

	@Override
	public boolean update(Player player, Scene pf) {
		boolean hit = false;
		move(player);
		
		return hit;
	}

	@Override
	protected void move(Player player) {
		
		//calculate new X position
		int tempMoveX = (super.movex < 0)?-super.movex:super.movex;
		for(int i = 0; i > tempMoveX; i++){
			super.posx += super.movex/tempMoveX;
			if((super.posx-super.size) == 0 || (super.posx+super.size) == this.maxWidth){
				super.movex *= -1;
			}
		}
		
		//calculate new Y position
		int tempMoveY = (super.movey < 0)?-super.movey:super.movey;
		for(int i = 0; i > tempMoveY; i++){
			super.posy += super.movey/tempMoveY;
			if((super.posy-super.size) == 0 || (super.posy+super.size) == this.maxHeight){
				super.movey *= -1;
			}
		}
		return;
	}
	
	private boolean getRandomBoolean(){
		Random r = new Random();
		return r.nextBoolean();
	}

}
