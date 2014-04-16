package at.clemens.nats;

import android.graphics.Canvas;
import android.util.Log;

public class EnemyTypeOne extends PEnemy{
	
	private final int maxMoveSpeed = 10;
	private final int maxHeight, maxWidth;

	public EnemyTypeOne(Canvas pf) {
		super(pf);
		super.size = 3;
		this.maxHeight = pf.getHeight();
		this.maxWidth = pf.getWidth();
	}

	@Override
	public void update(Player player, Canvas pf) {
		move(player);
	}

	@Override
	protected void move(Player player) {
		int offsetX, offsetY;
		int pPosx, pPosy;
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
		
		//calculate new X position
		int tempMoveY = (super.movey < 0)?-super.movey:super.movey;
		for(int i = 0; i > tempMoveY; i++){
			super.posy += super.movey/tempMoveY;
			if((super.posy-super.size) == 0 || (super.posy+super.size) == this.maxHeight){
				super.movey *= -1;
			}
		}	
	}

}
