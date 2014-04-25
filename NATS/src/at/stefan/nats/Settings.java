package at.stefan.nats;

import org.andengine.engine.camera.Camera;
import org.andengine.entity.scene.Scene;
import org.andengine.entity.sprite.Sprite;
import org.andengine.opengl.texture.TextureOptions;
import org.andengine.opengl.texture.atlas.bitmap.BitmapTextureAtlas;
import org.andengine.opengl.texture.atlas.bitmap.BitmapTextureAtlasTextureRegionFactory;
import org.andengine.opengl.texture.region.ITextureRegion;

public class Settings {

	nats nats;
	Camera mainCamera;

	Finals finals;
	MenuListener menuListener;

	BitmapTextureAtlas settingsBitmapTextureAtlas;
	ITextureRegion settingsITextureRegion;
	Sprite settingsSprite;

	BitmapTextureAtlas titleBitmapTextureAtlas;
	ITextureRegion titleITextureRegion;
	Sprite titleSprite;

	Scene settings;

	public Settings(nats nats, Camera cam) {
		this.nats = nats;
		this.mainCamera = cam;

		finals = new Finals();
	}

	public void loadSettingsResources() {
		BitmapTextureAtlasTextureRegionFactory.setAssetBasePath("backgrounds/");
		settingsBitmapTextureAtlas = new BitmapTextureAtlas(
				nats.getTextureManager(), 800, 480, TextureOptions.DEFAULT);
		settingsITextureRegion = BitmapTextureAtlasTextureRegionFactory
				.createFromAsset(settingsBitmapTextureAtlas,
						nats.getApplicationContext(), "Weltraum.png", 0, 0);
		settingsBitmapTextureAtlas.load();

		BitmapTextureAtlasTextureRegionFactory.setAssetBasePath("titles/");
		titleBitmapTextureAtlas = new BitmapTextureAtlas(
				nats.getTextureManager(), 560, 120, TextureOptions.DEFAULT);
		titleITextureRegion = BitmapTextureAtlasTextureRegionFactory
				.createFromAsset(titleBitmapTextureAtlas,
						nats.getApplicationContext(), "SettingsTitle.png", 0, 0);
		titleBitmapTextureAtlas.load();
	}

	public void loadSettingsScene() {
		settings = new Scene();
		settingsSprite = new Sprite(nats.getCameraWidth() / 2,
				nats.getCameraHeight() / 2, settingsITextureRegion,
				nats.getVertexBufferObjectManager());
		settings.attachChild(settingsSprite);
		// settings.setBackground(new Background(0, 0, 255));

		titleSprite = new Sprite(nats.getCameraWidth() / 2,
				nats.getCameraHeight() - titleBitmapTextureAtlas.getHeight()
						/ 2, titleITextureRegion,
				nats.getVertexBufferObjectManager());
		settings.attachChild(titleSprite);
	}

	public void removeSettingsScene() {
		// remove
	}

	public Scene getSettingsScene() {
		return settings;
	}
}
