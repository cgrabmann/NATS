package at.ussher.nats;

import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import at.alex.nats.Player;
import at.stefan.nats.nats;

public class Upgrade implements OnClickListener{
	//User user; //User object
	Button bombR, turboR, stasis_fieldR, deadly_trailR, gunnerR, shot_spreadingR, shot_frequenceR, move_speedR, shieldR;
	nats nats;
	Player player;
	private int usable[] = new int[4];
	private int permanent[] = new int[5];
	private int resources;
	
	private int bombP, turboP, stasis_fieldP, deadly_trailP, gunnerP, shot_spreadingP, shot_frequenceP, move_speedP, shieldP;

	@Override
	public void onClick(View v) {
		// TODO Auto-generated method stub
		
	}
	
	/*
	 *Usable
	 * Bomb => 3
	 * turbo => 1
	 * deadly trail => 2
	 * stasis field => 0
	 * 
	 * Permanent
	 * move speed => 0
	 * gunner => 1
	 * shield => 2
	 * shot frequence => 3
	 * shot spreading => 4 
	 * */
	
	/*public Upgrade(nats nats,Player player){
		this.nats = nats;
		this.player = player;
		/*bombR = (Button) nats.findViewById(R.id.bomb);
		turboR = (Button) nats.findViewById(R.id.turbo);
		gunnerR = (Button) nats.findViewById(R.id.gunner);
		move_speedR = (Button) nats.findViewById(R.id.move_speed);
		shieldR = (Button) nats.findViewById(R.id.shield);
		stasis_fieldR = (Button) nats.findViewById(R.id.stasis_field);
		shot_frequenceR = (Button) nats.findViewById(R.id.shot_frequence);
		shot_spreadingR = (Button) nats.findViewById(R.id.shot_spreading);
		deadly_trailR = (Button) nats.findViewById(R.id.deadly_trail);
		
		bombR.setOnClickListener(this); 			
		turboR.setOnClickListener(this);
		gunnerR.setOnClickListener(this);
		move_speedR.setOnClickListener(this);
		shieldR.setOnClickListener(this);
		stasis_fieldR.setOnClickListener(this);
		shot_frequenceR.setOnClickListener(this);
		shot_spreadingR.setOnClickListener(this);
		deadly_trailR.setOnClickListener(this);*/
		/*setValues();
	}
	
	public void onClick(View v){
		if(v == bombR){
			setUpgrade(bombP,this.usable,3,9,1);
			Log.i("NATS", "Bomb");
		}
		else if(v == turboR){
			setUpgrade(turboP,this.usable,1,9,1);
			Log.i("NATS", "turbo");
		}
		else if(v == gunnerR){
			setUpgrade(gunnerP,this.permanent,1,5,0);
			Log.i("NATS", "gunner");
		}
		else if(v == stasis_fieldR){
			setUpgrade(stasis_fieldP,this.usable,0,9,1);
			Log.i("NATS", "stasis_field");
		}
		else if(v == shieldR){
			setUpgrade(shieldP,this.permanent,2,5,0);
			Log.i("NATS", "shield");
		}
		else if(v == shot_frequenceR){
			setUpgrade(shot_frequenceP,this.permanent,3,5,0);
			Log.i("NATS", "shot_frequence");
		}	
		else if(v == shot_spreadingR){
			setUpgrade(shot_spreadingP,this.permanent,4,5,0);
			Log.i("NATS", "shot_spreading");
		}
		else if(v == move_speedR){
			setUpgrade(gunnerP,this.permanent,0,5,0);
			Log.i("NATS", "move_speed");
		}
		else if(v == deadly_trailR){ 
			setUpgrade(gunnerP,this.usable,2,9,1);
			Log.i("NATS","deadly_trail");
		}
	}
	
	/*public void setUpgrade(int upgradePrice,int upgrade[], int index, int max, int flag){
		if(this.resources >= upgradePrice){
			if(upgrade[index] < max && upgrade[index] > 0){
				upgrade[index] ++;
				this.resources -= upgradePrice;
				if(flag == 1){
					this.player.setUsables(upgrade);
				}
				else{
					this.player.setPermanents(upgrade);
				}
			}
			else{
				return;
			}
		}
		else{
			return;
		}
	}
	public void setValues(){
		this.resources = this.player.getRessources();
		this.usable = this.player.getUsables();
		this.permanent = this.player.getPermanents();
	}*/
}



