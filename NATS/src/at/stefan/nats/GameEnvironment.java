package at.stefan.nats;

import org.andengine.engine.camera.Camera;
import org.andengine.entity.Entity;
import org.andengine.entity.scene.Scene;
import org.andengine.entity.scene.background.Background;
import org.andengine.entity.sprite.Sprite;
import org.andengine.input.touch.TouchEvent;
import org.andengine.opengl.texture.TextureOptions;
import org.andengine.opengl.texture.atlas.bitmap.BitmapTextureAtlas;
import org.andengine.opengl.texture.atlas.bitmap.BitmapTextureAtlasTextureRegionFactory;
import org.andengine.opengl.texture.region.ITextureRegion;
import android.util.Log;
import at.stefan.nats.SceneManager.AllScenes;

public class GameEnvironment {

	private final int GAME_LAYER = 0;
	private final int PAUSE_LAYER = 1;
	private final int UPGRADE_LAYER = 2;

	nats nats;
	Camera mainCamera;
	SceneManager sceneManager;

	Finals finals;
	MenuListener menuListener;

	BitmapTextureAtlas gameBitmapTextureAtlas;
	ITextureRegion gameITextureRegion;
	Sprite gameSprite;

	BitmapTextureAtlas pauseBitmapTextureAtlas;
	ITextureRegion pauseITextureRegion;
	Sprite pauseSprite;

	BitmapTextureAtlas updateBitmapTextureAtlas;
	ITextureRegion updateITextureRegion;
	Sprite updateSprite;

	BitmapTextureAtlas continueBitmapTextureAtlas;
	ITextureRegion continueITextureRegion;
	Sprite continueSprite;

	BitmapTextureAtlas quitBitmapTextureAtlas;
	ITextureRegion quitITextureRegion;
	Sprite quitSprite;

	Scene game;
	Scene pause;
	Scene upgrade;

	public GameEnvironment(nats nats, Camera cam, SceneManager s) {
		this.nats = nats;
		this.mainCamera = cam;
		this.sceneManager = s;

		finals = new Finals();
	}

	public void loadGameResources() {
		BitmapTextureAtlasTextureRegionFactory.setAssetBasePath("backgrounds/");
		gameBitmapTextureAtlas = new BitmapTextureAtlas(
				nats.getTextureManager(), 800, 480, TextureOptions.DEFAULT);
		gameITextureRegion = BitmapTextureAtlasTextureRegionFactory
				.createFromAsset(gameBitmapTextureAtlas,
						nats.getApplicationContext(), "Weltraum.png", 0, 0);
		gameBitmapTextureAtlas.load();

		BitmapTextureAtlasTextureRegionFactory.setAssetBasePath("gfx/");
		pauseBitmapTextureAtlas = new BitmapTextureAtlas(
				nats.getTextureManager(), 100, 70, TextureOptions.DEFAULT);
		pauseITextureRegion = BitmapTextureAtlasTextureRegionFactory
				.createFromAsset(pauseBitmapTextureAtlas,
						nats.getApplicationContext(), "Pause.png", 0, 0);
		pauseBitmapTextureAtlas.load();

		updateBitmapTextureAtlas = new BitmapTextureAtlas(
				nats.getTextureManager(), 100, 70, TextureOptions.DEFAULT);
		updateITextureRegion = BitmapTextureAtlasTextureRegionFactory
				.createFromAsset(updateBitmapTextureAtlas,
						nats.getApplicationContext(), "Update.png", 0, 0);
		updateBitmapTextureAtlas.load();

		continueBitmapTextureAtlas = new BitmapTextureAtlas(
				nats.getTextureManager(), 100, 70, TextureOptions.DEFAULT);
		continueITextureRegion = BitmapTextureAtlasTextureRegionFactory
				.createFromAsset(continueBitmapTextureAtlas,
						nats.getApplicationContext(), "Continue.png", 0, 0);
		continueBitmapTextureAtlas.load();

		quitBitmapTextureAtlas = new BitmapTextureAtlas(
				nats.getTextureManager(), 100, 70, TextureOptions.DEFAULT);
		quitITextureRegion = BitmapTextureAtlasTextureRegionFactory
				.createFromAsset(quitBitmapTextureAtlas,
						nats.getApplicationContext(), "Quit.png", 0, 0);
		quitBitmapTextureAtlas.load();
		
		
	}

	public void loadGameScene() {
		game = new Scene();
		
		pause = new Scene();
		upgrade = new Scene();

		game.attachChild(new Entity()); // First Layer
		game.attachChild(new Entity()); // Second Layer
		game.attachChild(new Entity()); // Third Layer

		gameSprite = new Sprite(nats.getCameraWidth() / 2,
				nats.getCameraHeight() / 2, gameITextureRegion,
				nats.getVertexBufferObjectManager());

		game.getChildByIndex(GAME_LAYER).attachChild(gameSprite);
		// game.setBackground(new Background(0, 0, 255));

		pauseSprite = new Sprite(50, nats.getCameraHeight() - 35,
				pauseITextureRegion, nats.getVertexBufferObjectManager()) {
			@Override
			public boolean onAreaTouched(TouchEvent pSceneTouchEvent, float X,
					float Y) {
				if (pSceneTouchEvent.isActionUp()) {
					// execute action
					Log.i("NATS", "Pause");
					
					sceneManager.switchScene(AllScenes.PAUSE);
				}
				return true;
			};
		};
		updateSprite = new Sprite(nats.getCameraWidth() - 50,
				nats.getCameraHeight() - 35, updateITextureRegion,
				nats.getVertexBufferObjectManager()) {
			@Override
			public boolean onAreaTouched(TouchEvent pSceneTouchEvent, float X,
					float Y) {
				if (pSceneTouchEvent.isActionUp()) {
					// execute action
					Log.i("NATS", "Update");
					
					sceneManager.switchScene(AllScenes.UPGRADE);
				}
				return true;
			};
		};

		continueSprite = new Sprite(nats.getCameraWidth() / 2,
				nats.getCameraHeight() / 2 - 20, continueITextureRegion,
				nats.getVertexBufferObjectManager()) {
			@Override
			public boolean onAreaTouched(TouchEvent pSceneTouchEvent, float X,
					float Y) {
				if (pSceneTouchEvent.isActionUp()) {
					// execute action
					Log.i("NATS", "Update");
					hidePauseMenu();
					hideUpgradeMenu();
				}
				return true;
			};
		};
		
		quitSprite = new Sprite(nats.getCameraWidth() / 2,
				nats.getCameraHeight() / 2 - 130, quitITextureRegion,
				nats.getVertexBufferObjectManager()) {
			@Override
			public boolean onAreaTouched(TouchEvent pSceneTouchEvent, float X,
					float Y) {
				if (pSceneTouchEvent.isActionUp()) {
					// execute action
					Log.i("NATS", "Update");
					hidePauseMenu();
					hideUpgradeMenu();
					sceneManager.switchScene(AllScenes.MAIN_MENU);
				}
				return true;
			};
		};

		game.registerTouchArea(pauseSprite);
		game.registerTouchArea(updateSprite);

		game.getChildByIndex(GAME_LAYER).attachChild(pauseSprite);
		game.getChildByIndex(GAME_LAYER).attachChild(updateSprite);
		
		loadPauseLayer();
		loadUpgradeLayer();
		
		game.getChildByIndex(PAUSE_LAYER).setVisible(false);
		game.getChildByIndex(UPGRADE_LAYER).setVisible(false);
	}

	public void removeGameScene() {
		// remove
	}

	public Scene getGameScene() {
		return game;
	}
	
	public void hidePauseMenu() {
		game.getChildByIndex(PAUSE_LAYER).setVisible(false);
		game.registerTouchArea(continueSprite);
		game.registerTouchArea(quitSprite);
	}
	
	public void hideUpgradeMenu() {
		game.getChildByIndex(UPGRADE_LAYER).setVisible(false);
		game.unregisterTouchArea(continueSprite);
		game.unregisterTouchArea(quitSprite);
	}
	
	public void showPauseMenu() {
		game.getChildByIndex(PAUSE_LAYER).setVisible(true);
		game.registerTouchArea(continueSprite);
		game.registerTouchArea(quitSprite);
	}
	
	public void showUpgradeMenu() {
		game.getChildByIndex(UPGRADE_LAYER).setVisible(true);
		game.registerTouchArea(continueSprite);
		game.registerTouchArea(quitSprite);
	}
	
	private void loadPauseLayer() {
		pause.setBackground(new Background(255, 0, 0, 0));
		
		pause.attachChild(continueSprite);
		pause.attachChild(quitSprite);
		
		//pause.setBackgroundEnabled(true);
		pause.setAlpha(0.5f);
		
		//pause.registerTouchArea(continueSprite);
		//pause.registerTouchArea(quitSprite);
		
		game.getChildByIndex(PAUSE_LAYER).attachChild(pause);
	}
	
	private void loadUpgradeLayer() {
		upgrade.setBackground(new Background(0, 255, 0, 255));
		//upgrade.setBackgroundEnabled(true);
		upgrade.setAlpha(0.5f);
		/*upgrade.attachChild(continueSprite);
		upgrade.attachChild(quitSprite);
		
		upgrade.registerTouchArea(continueSprite);
		upgrade.registerTouchArea(quitSprite);*/
		
		game.getChildByIndex(UPGRADE_LAYER).attachChild(upgrade);
	}
}
