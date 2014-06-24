package at.stefan.nats;

import org.andengine.engine.camera.Camera;
import org.andengine.entity.scene.Scene;
import org.andengine.entity.sprite.AnimatedSprite;
import org.andengine.entity.sprite.Sprite;
import org.andengine.entity.text.Text;
import org.andengine.input.touch.TouchEvent;
import org.andengine.opengl.font.Font;
import org.andengine.opengl.font.FontFactory;
import org.andengine.opengl.texture.TextureOptions;
import org.andengine.opengl.texture.atlas.bitmap.BitmapTextureAtlas;
import org.andengine.opengl.texture.atlas.bitmap.BitmapTextureAtlasTextureRegionFactory;
import org.andengine.opengl.texture.region.ITextureRegion;
import org.andengine.opengl.texture.region.ITiledTextureRegion;
import org.andengine.util.adt.color.Color;

import android.graphics.Typeface;

public class Settings extends Scene {

	nats nats;
	Camera mainCamera;
	SceneManager sceneManager;

	Finals finals;
	MenuListener menuListener;

	BitmapTextureAtlas settingsBitmapTextureAtlas;
	ITextureRegion settingsITextureRegion;
	Sprite settingsSprite;

	BitmapTextureAtlas titleBitmapTextureAtlas;
	ITextureRegion titleITextureRegion;
	Sprite titleSprite;

	Font settingsFont;
	Text volumeText;
	Text vibrationText;

	BitmapTextureAtlas soundBitmapTextureAtlas;
	ITiledTextureRegion soundITiledTextureRegion;
	AnimatedSprite soundAnimatedSprite;

	BitmapTextureAtlas sliderBitmapTextureAtlas;
	ITextureRegion sliderITextureRegion;
	BitmapTextureAtlas thumbBitmapTextureAtlas;
	ITextureRegion thumbITextureRegion;

	Slider slider;

	public Settings(nats nats, Camera cam, SceneManager sc) {
		this.nats = nats;
		this.mainCamera = cam;
		this.sceneManager = sc;

		finals = new Finals();
	}

	public void loadSettingsResources() {
		BitmapTextureAtlasTextureRegionFactory.setAssetBasePath("backgrounds/");
		settingsBitmapTextureAtlas = new BitmapTextureAtlas(
				nats.getTextureManager(), 800, 480, TextureOptions.DEFAULT);
		settingsITextureRegion = BitmapTextureAtlasTextureRegionFactory
				.createFromAsset(settingsBitmapTextureAtlas,
						nats.getApplicationContext(), "MainMenu.jpg", 0, 0);
		settingsBitmapTextureAtlas.load();

		BitmapTextureAtlasTextureRegionFactory.setAssetBasePath("titles/");
		titleBitmapTextureAtlas = new BitmapTextureAtlas(
				nats.getTextureManager(), 560, 120, TextureOptions.DEFAULT);
		titleITextureRegion = BitmapTextureAtlasTextureRegionFactory
				.createFromAsset(titleBitmapTextureAtlas,
						nats.getApplicationContext(), "SettingsTitle.png", 0, 0);
		titleBitmapTextureAtlas.load();

		settingsFont = FontFactory.create(nats.getFontManager(),
				nats.getTextureManager(), 256, 256,
				Typeface.create(Typeface.DEFAULT, Typeface.BOLD), 50,
				Color.RED.hashCode());
		settingsFont.load();

		BitmapTextureAtlasTextureRegionFactory.setAssetBasePath("buttons/");
		soundBitmapTextureAtlas = new BitmapTextureAtlas(
				nats.getTextureManager(), 400, 80);
		soundITiledTextureRegion = BitmapTextureAtlasTextureRegionFactory
				.createTiledFromAsset(soundBitmapTextureAtlas,
						nats.getApplicationContext(), "Sound.png", 0, 0, 4, 1);
		soundBitmapTextureAtlas.load();

		sliderBitmapTextureAtlas = new BitmapTextureAtlas(
				nats.getTextureManager(), 500, 30);
		sliderITextureRegion = BitmapTextureAtlasTextureRegionFactory
				.createFromAsset(sliderBitmapTextureAtlas,
						nats.getApplicationContext(), "Slider.png", 0, 0);
		sliderBitmapTextureAtlas.load();
		thumbBitmapTextureAtlas = new BitmapTextureAtlas(
				nats.getTextureManager(), 20, 35);
		thumbITextureRegion = BitmapTextureAtlasTextureRegionFactory
				.createFromAsset(thumbBitmapTextureAtlas,
						nats.getApplicationContext(), "Thumb.png", 0, 0);
		thumbBitmapTextureAtlas.load();
	}

	public void loadSettingsScene() {
		settingsSprite = new Sprite(nats.getCameraWidth() / 2,
				nats.getCameraHeight() / 2, settingsITextureRegion,
				nats.getVertexBufferObjectManager());
		this.attachChild(settingsSprite);
		// settings.setBackground(new Background(0, 0, 255));

		titleSprite = new Sprite(nats.getCameraWidth() / 2,
				nats.getCameraHeight() - titleBitmapTextureAtlas.getHeight()
						/ 2, titleITextureRegion,
				nats.getVertexBufferObjectManager());
		this.attachChild(titleSprite);

		volumeText = new Text(260, 320, settingsFont, "Volume: ",
				nats.getVertexBufferObjectManager());
		this.attachChild(volumeText);

		soundAnimatedSprite = new AnimatedSprite(460, 320,
				soundITiledTextureRegion, nats.getVertexBufferObjectManager()) {
			@Override
			public boolean onAreaTouched(TouchEvent pSceneTouchEvent, float X,
					float Y) {
				if (pSceneTouchEvent.isActionUp()) {
					if (this.getCurrentTileIndex() != 0) {
						this.setCurrentTileIndex(0);
						sceneManager.getPlayer().setVolume(0f);
						slider.setVolume(0f);
					} else {
						this.setCurrentTileIndex(3);
						sceneManager.getPlayer().setVolume(0.75f);
						slider.setVolume(0.75f);
					}

				}
				return true;
			};
		};
		// soundAnimatedSprite.setCurrentTileIndex(0);
		this.attachChild(soundAnimatedSprite);
		this.registerTouchArea(soundAnimatedSprite);

		slider = new Slider(400, 240, this, sceneManager, sliderITextureRegion,
				thumbITextureRegion, nats.getVertexBufferObjectManager());
		float volume = sceneManager.getPlayer().getVolume();
		slider.setVolume(volume);
		
		if (volume > 0.75f) {
			soundAnimatedSprite.setCurrentTileIndex(3);
		} else if (volume > 0.40f) {
			soundAnimatedSprite.setCurrentTileIndex(2);
		} else if (volume > 0.05f) {
			soundAnimatedSprite.setCurrentTileIndex(1);
		} else {
			soundAnimatedSprite.setCurrentTileIndex(0);
		}
		
		this.attachChild(slider);

		vibrationText = new Text(270, 145, settingsFont, "Vibration: ",
				nats.getVertexBufferObjectManager());
		this.attachChild(vibrationText);
	}

	public void removeSettingsScene() {
		// remove
	}

	public Scene getSettingsScene() {
		return this;
	}

	public Slider getSlider() {
		return this.slider;
	}

	public AnimatedSprite getSoundSprite() {
		return this.soundAnimatedSprite;
	}
}
