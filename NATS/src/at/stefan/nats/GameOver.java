package at.stefan.nats;

import org.andengine.engine.camera.BoundCamera;
import org.andengine.engine.camera.Camera;
import org.andengine.entity.scene.Scene;
import org.andengine.entity.sprite.Sprite;
import org.andengine.input.touch.TouchEvent;
import org.andengine.opengl.texture.TextureOptions;
import org.andengine.opengl.texture.atlas.bitmap.BitmapTextureAtlas;
import org.andengine.opengl.texture.atlas.bitmap.BitmapTextureAtlasTextureRegionFactory;
import org.andengine.opengl.texture.region.ITextureRegion;

import at.stefan.nats.SceneManager.AllScenes;

public class GameOver extends Scene {

	//boolean touch = false;

	nats nats;
	Camera mainCamera;
	GameEnvironment gameEnvironment;
	SceneManager sceneManager;

	BitmapTextureAtlas backgroundBitmapTextureAtlas;
	ITextureRegion backgroundITextureRegion;
	Sprite backgroundSprite;

	BitmapTextureAtlas continueBitmapTextureAtlas;
	ITextureRegion continueITextureRegion;
	Sprite continueSprite;

	public GameOver(nats nats, BoundCamera cam, GameEnvironment ge, SceneManager s) {
		this.nats = nats;
		this.mainCamera = cam;
		this.gameEnvironment = ge;
		this.sceneManager = s;

		// this.setBackground(new Background(0.5f, 0.5f, 0.5f, 0.5f));
		//this.setBackgroundEnabled(false);
	}

	public void loadGameOverResources() {
		BitmapTextureAtlasTextureRegionFactory.setAssetBasePath("gfx/");

		backgroundBitmapTextureAtlas = new BitmapTextureAtlas(
				nats.getTextureManager(), 800, 480, TextureOptions.DEFAULT);
		backgroundITextureRegion = BitmapTextureAtlasTextureRegionFactory
				.createFromAsset(backgroundBitmapTextureAtlas,
						nats.getApplicationContext(), "Grau.png", 0, 0);
		backgroundBitmapTextureAtlas.load();

		continueBitmapTextureAtlas = new BitmapTextureAtlas(
				nats.getTextureManager(), 310, 150, TextureOptions.DEFAULT);
		continueITextureRegion = BitmapTextureAtlasTextureRegionFactory
				.createFromAsset(continueBitmapTextureAtlas,
						nats.getApplicationContext(), "Continue.png", 0, 0);
		continueBitmapTextureAtlas.load();
	}

	public void loadGameOverScene() {
		backgroundSprite = new Sprite(400,
				240, backgroundITextureRegion,
				nats.getVertexBufferObjectManager());
		backgroundSprite.setAlpha(0.4f);

		continueSprite = new Sprite(400, 150, continueITextureRegion,
				nats.getVertexBufferObjectManager()) {
			@Override
			public boolean onAreaTouched(TouchEvent pSceneTouchEvent, float X,
					float Y) {
				if (pSceneTouchEvent.isActionUp()) {
					// wenn Highscore dann
					
					// ansonsten...
					gameEnvironment.leaveGame();
					sceneManager.switchScene(AllScenes.MAIN_MENU);
					//GameOver.this.unregisterTouchArea(continueSprite);
				}
				return true;
			};
		};

		this.attachChild(backgroundSprite);
		this.attachChild(continueSprite);
		this.registerTouchArea(continueSprite);
	}
	
	public void reset() {
		
	}
	
}