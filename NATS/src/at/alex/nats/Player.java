package at.alex.nats;

import java.io.IOException;

import org.andengine.audio.music.Music;
import org.andengine.audio.music.MusicFactory;
import org.andengine.audio.sound.SoundFactory;
import org.andengine.entity.sprite.Sprite;
import org.andengine.opengl.texture.TextureOptions;
import org.andengine.opengl.texture.atlas.bitmap.BitmapTextureAtlas;
import org.andengine.opengl.texture.atlas.bitmap.BitmapTextureAtlasTextureRegionFactory;
import org.andengine.opengl.texture.region.ITextureRegion;

import at.stefan.nats.nats;

public class Player {

	// private long time;
	private int ressources = 50000;
	private int permanents[] = new int[5];
	private int usables[] = new int[4];

	private float posX, posY;
	// private float velX, velY;

	private boolean shooting = true;
	private boolean turbo = false;

	// Upgrades
	private int speed = 0;
	private int shotfrequence = 10;
	private boolean shield = false;
	private int timeToShield = 45;
	private int shotspreading = 0;
	private int timeToGunner = 20;

	nats nats;

	Music music;

	BitmapTextureAtlas playerBaseBitmapTextureAtlas;
	ITextureRegion playerBaseITextureRegion;
	Sprite playerBaseSprite;

	BitmapTextureAtlas playerBitmapTextureAtlas;
	ITextureRegion playerITextureRegion;
	Sprite playerSprite;

	BitmapTextureAtlas flameBitmapTextureAtlas;
	ITextureRegion flameITextureRegion;
	Sprite flameSprite;

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

		SoundFactory.setAssetBasePath("sfx/");
		try {
			music = MusicFactory.createMusicFromAsset(nats.getMusicManager(),
					nats.getApplicationContext(), "PumpAbDasBier.mp3");
			music.setLooping(true);
			music.setVolume(1.0f, 1.0f);
		} catch (IOException e) {
			e.printStackTrace();
		}

		BitmapTextureAtlasTextureRegionFactory.setAssetBasePath("gfx/");

		playerBaseBitmapTextureAtlas = new BitmapTextureAtlas(
				nats.getTextureManager(), 76, 76, TextureOptions.DEFAULT);
		playerBaseITextureRegion = BitmapTextureAtlasTextureRegionFactory
				.createFromAsset(playerBaseBitmapTextureAtlas,
						nats.getApplicationContext(), "Protector.png", 0, 0);
		playerBaseBitmapTextureAtlas.load();
		playerBaseSprite = new Sprite(posX, posY, playerBaseITextureRegion,
				nats.getVertexBufferObjectManager());

		playerBitmapTextureAtlas = new BitmapTextureAtlas(
				nats.getTextureManager(), 80, 120, TextureOptions.DEFAULT);
		playerITextureRegion = BitmapTextureAtlasTextureRegionFactory
				.createFromAsset(playerBitmapTextureAtlas,
						nats.getApplicationContext(), "Spaceshuttle.png", 0, 0);
		playerBitmapTextureAtlas.load();
		playerSprite = new Sprite(playerBaseSprite.getWidth() / 2,
				playerBaseSprite.getHeight() / 3 * 2, playerITextureRegion,
				nats.getVertexBufferObjectManager());

		flameBitmapTextureAtlas = new BitmapTextureAtlas(
				nats.getTextureManager(), 60, 60, TextureOptions.DEFAULT);
		flameITextureRegion = BitmapTextureAtlasTextureRegionFactory
				.createFromAsset(flameBitmapTextureAtlas,
						nats.getApplicationContext(), "Flammen.png", 0, 0);
		flameBitmapTextureAtlas.load();
		flameSprite = new Sprite(playerSprite.getWidth() / 2, -30,
				flameITextureRegion, nats.getVertexBufferObjectManager());

		playerSprite.setZIndex(10);
		flameSprite.setZIndex(5);
		playerBaseSprite.setAlpha(0f);
		playerSprite.attachChild(flameSprite);
		playerBaseSprite.attachChild(playerSprite);
	}

	public int getPermanents(int pos) {
		// Log.i("NATS", "getPermanents an Stelle " + pos);
		return this.permanents[pos];
	}

	public void setPermanents(int value, int pos) {
		// Log.i("NATS", "setPermanents an Stelle " + pos + " zu " + value);
		this.permanents[pos] = value;
	}

	public int getUsables(int pos) {
		// Log.i("NATS", "getUsables an Stelle " + pos);
		return this.usables[pos];
	}

	public void setUsables(int value, int pos) {
		// Log.i("NATS", "setUsables an Stelle " + pos + " zu " + value);
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
	
	public float getFlamePosX() {
		return flameSprite.getX();
	}
	
	public float getFlamePosY() {
		return flameSprite.getY();
	}

	public void setRessources(int ressources) {
		// Log.i("NATS", "setRessources to " + ressources);
		this.ressources = ressources;
	}
	
	public void addRessources(int i) {
		this.ressources += i;
	}

	public int getRessources() {
		// Log.i("NATS", "getRessources: " + this.ressources);
		return this.ressources;
	}

	public String getRessourcesForDisplay() {
		if (this.ressources > 9999999) {
			return "" + this.ressources;
		} else if (this.ressources > 999999) {
			return "0" + this.ressources;
		} else if (this.ressources > 99999) {
			return "00" + this.ressources;
		} else if (this.ressources > 9999) {
			return "000" + this.ressources;
		} else if (this.ressources > 999) {
			return "0000" + this.ressources;
		} else if (this.ressources > 99) {
			return "00000" + this.ressources;
		} else if (this.ressources > 9) {
			return "000000" + this.ressources;
		} else {
			return "0000000" + this.ressources;
		}
	}

	public void increaseSpeed() {
		this.speed += 1;
	}

	public int getSpeed() {
		return this.speed;
	}

	public Sprite getPlayerBase() {
		return playerBaseSprite;
	}

	public Sprite getPlayer() {
		return playerSprite;
	}

	public Sprite getPlayerFlame() {
		return flameSprite;
	}

	public float getShotFrequence() {
		return shotfrequence;
	}

	public void increaseShotFrequence() {
		this.shotfrequence--;
	}

	public void activateShield() {
		this.shield = true;
	}

	public void removeShield() {
		this.shield = false;
	}

	public void increaseShield() {
		this.timeToShield -= 5;
	}

	public int getTimeToShield() {
		return timeToShield;
	}

	public boolean getShield() {
		return shield;
	}

	public int getShotSpreading() {
		return shotspreading;
	}

	public void increaseShotSpreading() {
		this.shotspreading++;
	}

	public int getTimeToGunner() {
		return timeToGunner;
	}

	public void increaseGunner() {
		this.timeToGunner -= 2;
	}

	public boolean isShootingAllowed() {
		return shooting;
	}

	public void setShootingAllowed(boolean b) {
		this.shooting = b;
	}
	
	public void activateTurbo() {
		this.turbo = true;
	}
	
	public void deactivateTurbo() {
		this.turbo = false;
	}
	
	public boolean isTurboActivated() {
		return this.turbo;
	}
	
	public void playMusic() {
		if(music != null && !music.isPlaying()) {
			this.music.play();
		}
	}
	
	public void pauseMusic() {
		if(music != null && music.isPlaying()) {
			this.music.pause();
		}
	}
	
	public void resumeMusic() {
		if(music != null && !music.isPlaying()) {
			this.music.resume();
		}
	}
}