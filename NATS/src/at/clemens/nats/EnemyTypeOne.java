package at.clemens.nats;

import org.andengine.entity.scene.Scene;

public class EnemyTypeOne extends PEnemy{
	
	private final int maxMoveSpeed = 10;
	private final float maxHeight, maxWidth;

	public EnemyTypeOne(Scene pf) {
		super(pf);
		super.size = 100;
		super.movex = 0;
		super.movey = 0;
		this.maxHeight = pf.getX();
		this.maxWidth = pf.getY();
	}

	@Override
	public boolean update(Player player, Scene pf) {
		boolean hit = false;
		hit = move(player);
		
		return hit;
	}

	@Override
	protected boolean move(Player player) {
		float offsetX, offsetY;
		float pPosx, pPosy;
		boolean hit = false;
		
		pPosx = player.getposy();
		pPosy = player.getposx();
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
		
		return hit;
	}

}
