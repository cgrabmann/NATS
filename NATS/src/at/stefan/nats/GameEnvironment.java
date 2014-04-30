package at.stefan.nats;

import org.andengine.engine.camera.Camera;
import org.andengine.entity.Entity;
import org.andengine.entity.scene.Scene;
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
	PauseMenu pauseMenu;
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

	Sprite pause[] = new Sprite[2];
	Sprite upgrade[] = new Sprite[9];

	Scene game;

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

	}

	public void loadGameScene() {
		game = new Scene();

		// pause = new Scene();
		// upgrade = new Scene();

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
					GameEnvironment.this.registerPauseTouch();
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
					GameEnvironment.this.registerUpgradeTouch();
					sceneManager.switchScene(AllScenes.UPGRADE);
				}
				return true;
			};
		};

		game.registerTouchArea(pauseSprite);
		game.registerTouchArea(updateSprite);

		game.getChildByIndex(GAME_LAYER).attachChild(pauseSprite);
		game.getChildByIndex(GAME_LAYER).attachChild(updateSprite);

		// loadPauseLayer();
		// loadUpgradeLayer();

		game.getChildByIndex(PAUSE_LAYER).setVisible(false);
		game.getChildByIndex(UPGRADE_LAYER).setVisible(false);
	}

	public void removeGameScene() {
		// remove
	}

	public Scene getGameScene() {
		return game;
	}

	public void showPauseMenu() {
		Log.i("NATS", "showPauseMenu");
		game.getChildByIndex(PAUSE_LAYER).setVisible(true);
		// game.registerTouchArea(pTouchArea)
	}

	public void hidePauseMenu() {
		Log.i("NATS", "hidePauseMenu");
		game.getChildByIndex(PAUSE_LAYER).setVisible(false);
		this.unregisterPauseTouch();
	}

	public void showUpgradeMenu() {
		Log.i("NATS", "showUpgradeMenu");
		game.getChildByIndex(UPGRADE_LAYER).setVisible(true);
	}

	public void hideUpgradeMenu() {
		Log.i("NATS", "hideUpgradeMenu");
		game.getChildByIndex(UPGRADE_LAYER).setVisible(false);
		this.unregisterUpgradeTouch();
	}

	public void registerPauseTouch() {
		game.registerTouchArea(pause[0]);
		game.registerTouchArea(pause[1]);
	}

	public void unregisterPauseTouch() {
		game.unregisterTouchArea(pause[0]);
		game.unregisterTouchArea(pause[1]);
	}

	public void registerUpgradeTouch() {
		game.registerTouchArea(upgrade[0]);
		game.registerTouchArea(upgrade[1]);
		game.registerTouchArea(upgrade[2]);
		game.registerTouchArea(upgrade[3]);
		game.registerTouchArea(upgrade[4]);
		game.registerTouchArea(upgrade[5]);
		game.registerTouchArea(upgrade[6]);
		game.registerTouchArea(upgrade[7]);
		game.registerTouchArea(upgrade[8]);
	}

	public void unregisterUpgradeTouch() {
		game.unregisterTouchArea(upgrade[0]);
		game.unregisterTouchArea(upgrade[1]);
		game.unregisterTouchArea(upgrade[2]);
		game.unregisterTouchArea(upgrade[3]);
		game.unregisterTouchArea(upgrade[4]);
		game.unregisterTouchArea(upgrade[5]);
		game.unregisterTouchArea(upgrade[6]);
		game.unregisterTouchArea(upgrade[7]);
		game.unregisterTouchArea(upgrade[8]);
	}

	public void attachPauseMenu(Scene pauseMenu) {
		game.getChildByIndex(PAUSE_LAYER).attachChild(pauseMenu);
	}

	public void attachUpgradeMenu(Scene upgradeMenu) {
		game.getChildByIndex(UPGRADE_LAYER).attachChild(upgradeMenu);
	}

	public void setPauseReference(Sprite con, Sprite quit) {
		this.pause[0] = con;
		this.pause[1] = quit;
	}

	public void setUpgradeReference(Sprite a, Sprite b, Sprite c, Sprite d,
			Sprite e, Sprite f, Sprite g, Sprite h, Sprite i) {
		this.upgrade[0] = a;
		this.upgrade[1] = b;
		this.upgrade[2] = c;
		this.upgrade[3] = d;
		this.upgrade[4] = e;
		this.upgrade[5] = f;
		this.upgrade[6] = g;
		this.upgrade[7] = h;
		this.upgrade[8] = i;
	}

	/*
	 * public void hideUpgradeMenu() {
	 * game.getChildByIndex(UPGRADE_LAYER).setVisible(false);
	 * game.unregisterTouchArea(continueSprite);
	 * game.unregisterTouchArea(quitSprite); }
	 * 
	 * 
	 * 
	 * public void showUpgradeMenu() {
	 * game.getChildByIndex(UPGRADE_LAYER).setVisible(true);
	 * game.registerTouchArea(continueSprite);
	 * game.registerTouchArea(quitSprite); }
	 */

	/*
	 * private void loadPauseLayer() { pause.setBackground(new Background(255,
	 * 0, 0, 0));
	 * 
	 * pause.attachChild(continueSprite); pause.attachChild(quitSprite);
	 * 
	 * //pause.setBackgroundEnabled(true); pause.setAlpha(0.5f);
	 * 
	 * //pause.registerTouchArea(continueSprite);
	 * //pause.registerTouchArea(quitSprite);
	 * 
	 * game.getChildByIndex(PAUSE_LAYER).attachChild(pause); }
	 */

	/*
	 * private void loadUpgradeLayer() { upgrade.setBackground(new Background(0,
	 * 255, 0, 255)); //upgrade.setBackgroundEnabled(true);
	 * upgrade.setAlpha(0.5f); /*upgrade.attachChild(continueSprite);
	 * upgrade.attachChild(quitSprite);
	 * 
	 * upgrade.registerTouchArea(continueSprite);
	 * upgrade.registerTouchArea(quitSprite);
	 */

	/*
	 * game.getChildByIndex(UPGRADE_LAYER).attachChild(upgrade); }
	 */
}
