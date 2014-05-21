package at.stefan.nats;

import org.andengine.engine.camera.Camera;
import org.andengine.engine.camera.hud.controls.AnalogOnScreenControl;
import org.andengine.entity.scene.Scene;
import org.andengine.entity.sprite.Sprite;
import org.andengine.opengl.texture.TextureOptions;
import org.andengine.opengl.texture.atlas.bitmap.BitmapTextureAtlas;
import org.andengine.opengl.texture.atlas.bitmap.BitmapTextureAtlasTextureRegionFactory;
import org.andengine.opengl.texture.region.ITextureRegion;

public class Highscores extends Scene {

	nats nats;
	Camera mainCamera;

	Finals finals;
	MenuListener menuListener;

	BitmapTextureAtlas highscoresBitmapTextureAtlas;
	ITextureRegion highscoresITextureRegion;
	Sprite highscoresSprite;

	BitmapTextureAtlas titleBitmapTextureAtlas;
	ITextureRegion titleITextureRegion;
	Sprite titleSprite;
	
	AnalogOnScreenControl leftAnalogOnScreenControl;
	BitmapTextureAtlas leftAnalogAuﬂenBitmapTextureAtlas;
	ITextureRegion leftAnalogAuﬂenITextureRegion;
	BitmapTextureAtlas leftAnalogInnenBitmapTextureAtlas;
	ITextureRegion leftAnalogInnenITextureRegion;

	public Highscores(nats nats, Camera cam) {
		this.nats = nats;
		this.mainCamera = cam;

		finals = new Finals();
	}

	public void loadHighscoreResources() {
		BitmapTextureAtlasTextureRegionFactory.setAssetBasePath("backgrounds/");
		highscoresBitmapTextureAtlas = new BitmapTextureAtlas(
				nats.getTextureManager(), 800, 480, TextureOptions.DEFAULT);
		highscoresITextureRegion = BitmapTextureAtlasTextureRegionFactory
				.createFromAsset(highscoresBitmapTextureAtlas,
						nats.getApplicationContext(), "MainMenu.jpg", 0, 0);
		highscoresBitmapTextureAtlas.load();

		BitmapTextureAtlasTextureRegionFactory.setAssetBasePath("titles/");
		titleBitmapTextureAtlas = new BitmapTextureAtlas(
				nats.getTextureManager(), 560, 120, TextureOptions.DEFAULT);
		titleITextureRegion = BitmapTextureAtlasTextureRegionFactory
				.createFromAsset(titleBitmapTextureAtlas,
						nats.getApplicationContext(), "HighscoresTitle.png", 0,
						0);
		titleBitmapTextureAtlas.load();
		
		
	}

	public void loadHighscoreScene() {
		highscoresSprite = new Sprite(nats.getCameraWidth() / 2,
				nats.getCameraHeight() / 2, highscoresITextureRegion,
				nats.getVertexBufferObjectManager());
		this.attachChild(highscoresSprite);
		//highscores.setBackground(new Background(0, 0, 255));

		titleSprite = new Sprite(nats.getCameraWidth() / 2,
				nats.getCameraHeight() - titleBitmapTextureAtlas.getHeight()
						/ 2, titleITextureRegion,
				nats.getVertexBufferObjectManager());
		this.attachChild(titleSprite);
		
	}

	public void removeHighscoreScene() {
		// remove
	}

	public Scene getHighscoreScene() {
		return this;
	}
}
