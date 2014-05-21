package at.alex.nats;

import org.andengine.entity.sprite.Sprite;
import org.andengine.opengl.texture.TextureOptions;
import org.andengine.opengl.texture.atlas.bitmap.BitmapTextureAtlas;
import org.andengine.opengl.texture.atlas.bitmap.BitmapTextureAtlasTextureRegionFactory;
import org.andengine.opengl.texture.region.ITextureRegion;

import at.stefan.nats.nats;

public class Player {

	private long time;
	private int ressources = 50000;
	private int permanents[] = new int[5];
	private int usables[] = new int[4];

	private float posX, posY;
	private float speed = 0;
	private float velX, velY;
	
	private float shotfrequence = 0.40f;

	nats nats;

	BitmapTextureAtlas playerBitmapTextureAtlas;
	ITextureRegion playerITextureRegion;
	Sprite playerSprite;

	public Player(nats nats) {
		this.nats = nats;

		this.posX = 400;
		this.posY = 240;

		for (int i = 0; i <= 3; i++) {
			this.usables[i] = 0;
			this.permanents[i] = 0;
		}
		this.permanents[4] = 0;
		this.usables[0] = 2;
		this.usables[1] = 1;

		BitmapTextureAtlasTextureRegionFactory.setAssetBasePath("gfx/");
		playerBitmapTextureAtlas = new BitmapTextureAtlas(
				nats.getTextureManager(), 80, 120, TextureOptions.DEFAULT);
		playerITextureRegion = BitmapTextureAtlasTextureRegionFactory
				.createFromAsset(playerBitmapTextureAtlas,
						nats.getApplicationContext(), "Spaceshuttle.png", 0, 0);
		playerBitmapTextureAtlas.load();
		playerSprite = new Sprite(posX, posY, playerITextureRegion,
				nats.getVertexBufferObjectManager());
	}

	public int getPermanents(int pos) {
		//Log.i("NATS", "getPermanents an Stelle " + pos);
		return this.permanents[pos];
	}

	public void setPermanents(int value, int pos) {
		//Log.i("NATS", "setPermanents an Stelle " + pos + " zu " + value);
		this.permanents[pos] = value;
	}

	public int getUsables(int pos) {
		//Log.i("NATS", "getUsables an Stelle " + pos);
		return this.usables[pos];
	}

	public void setUsables(int value, int pos) {
		//Log.i("NATS", "setUsables an Stelle " + pos + " zu " + value);
		this.usables[pos] = value;
	}

	public float getPosX() {
		return this.posX;
	}

	public void setPosX(float posX) {
		this.posX = posX;
	}

	public float getPosY() {
		return this.posY;
	}

	public void setPosY(float posY) {
		this.posY = posY;
	}

	public void setRessources(int ressources) {
		//Log.i("NATS", "setRessources to " + ressources);
		this.ressources = ressources;
	}

	public int getRessources() {
		//Log.i("NATS", "getRessources: " + this.ressources);
		return this.ressources;
	}
	
	public String getRessourcesForDisplay() {
		if(this.ressources > 9999999) {
			return ""+this.ressources;
		}else if(this.ressources > 999999) {
			return "0"+this.ressources;
		}else if(this.ressources > 99999) {
			return "00"+this.ressources;
		}else if(this.ressources > 9999) {
			return "000"+this.ressources;
		}else if(this.ressources > 999) {
			return "0000"+this.ressources;
		}else if(this.ressources > 99) {
			return "00000"+this.ressources;
		}else if(this.ressources > 9) {
			return "000000"+this.ressources;
		}else {
			return "0000000"+this.ressources;
		}
	}

	public void setSpeed(int speed) {
		this.speed = speed;
	}

	public float getSpeed() {
		return this.speed;
	}
	
	public Sprite getPlayer() { 
		return playerSprite;
	}
	
	public float getShotFrequence() {
		return shotfrequence;
	}
	
	public void increaseShotFrequence() {
		this.shotfrequence -= 0.03f;
	}
}
