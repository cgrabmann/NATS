package at.stefan.nats;

import javax.microedition.khronos.opengles.GL10;

import org.andengine.engine.camera.Camera;
import org.andengine.engine.camera.hud.controls.AnalogOnScreenControl;
import org.andengine.engine.camera.hud.controls.AnalogOnScreenControl.IAnalogOnScreenControlListener;
import org.andengine.engine.camera.hud.controls.BaseOnScreenControl;
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

public class GameEnvironment extends Scene implements IAnalogOnScreenControlListener {

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

	AnalogOnScreenControl leftAnalogOnScreenControl;
	BitmapTextureAtlas leftAnalogAuﬂenBitmapTextureAtlas;
	ITextureRegion leftAnalogAuﬂenITextureRegion;
	BitmapTextureAtlas leftAnalogInnenBitmapTextureAtlas;
	ITextureRegion leftAnalogInnenITextureRegion;

	AnalogOnScreenControl rightAnalogOnScreenControl;
	BitmapTextureAtlas rightAnalogBitmapTextureAtlas;
	ITextureRegion rightAnalogITextureRegion;

	Sprite pause[] = new Sprite[2];
	Sprite upgrade[] = new Sprite[9];

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
						nats.getApplicationContext(), "Game.png", 0, 0);
		gameBitmapTextureAtlas.load();

		BitmapTextureAtlasTextureRegionFactory.setAssetBasePath("gfx/");
		pauseBitmapTextureAtlas = new BitmapTextureAtlas(
				nats.getTextureManager(), 70, 70, TextureOptions.DEFAULT);
		pauseITextureRegion = BitmapTextureAtlasTextureRegionFactory
				.createFromAsset(pauseBitmapTextureAtlas,
						nats.getApplicationContext(), "Pause.png", 0, 0);
		pauseBitmapTextureAtlas.load();

		updateBitmapTextureAtlas = new BitmapTextureAtlas(
				nats.getTextureManager(), 70, 70, TextureOptions.DEFAULT);
		updateITextureRegion = BitmapTextureAtlasTextureRegionFactory
				.createFromAsset(updateBitmapTextureAtlas,
						nats.getApplicationContext(), "Update.png", 0, 0);
		updateBitmapTextureAtlas.load();

		leftAnalogAuﬂenBitmapTextureAtlas = new BitmapTextureAtlas(
				nats.getTextureManager(), 200, 200, TextureOptions.DEFAULT);
		leftAnalogAuﬂenITextureRegion = BitmapTextureAtlasTextureRegionFactory
				.createFromAsset(leftAnalogAuﬂenBitmapTextureAtlas,
						nats.getApplicationContext(), "JoystickAusen.png", 0,
						0);
		leftAnalogAuﬂenBitmapTextureAtlas.load();
		
		leftAnalogInnenBitmapTextureAtlas = new BitmapTextureAtlas(
				nats.getTextureManager(), 150, 150, TextureOptions.DEFAULT);
		leftAnalogInnenITextureRegion = BitmapTextureAtlasTextureRegionFactory
				.createFromAsset(leftAnalogInnenBitmapTextureAtlas,
						nats.getApplicationContext(), "JoystickInnen.png", 0,
						0);
		leftAnalogInnenBitmapTextureAtlas.load();
		leftAnalogOnScreenControl = new AnalogOnScreenControl(100, 100,
				mainCamera, leftAnalogAuﬂenITextureRegion,
				leftAnalogInnenITextureRegion, 0.5f,
				nats.getVertexBufferObjectManager(), this);
		
		//leftAnalogOncScreenControl.
	}

	public void loadGameScene() {

		// pause = new Scene();
		// upgrade = new Scene();

		this.attachChild(new Entity()); // First Layer
		this.attachChild(new Entity()); // Second Layer
		this.attachChild(new Entity()); // Third Layer

		gameSprite = new Sprite(nats.getCameraWidth() / 2,
				nats.getCameraHeight() / 2, gameITextureRegion,
				nats.getVertexBufferObjectManager());

		this.getChildByIndex(GAME_LAYER).attachChild(gameSprite);
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
		
		leftAnalogOnScreenControl.getControlBase().setBlendFunction(GL10.GL_SRC_ALPHA, GL10.GL_ONE_MINUS_SRC_ALPHA);
        leftAnalogOnScreenControl.getControlBase().setAlpha(0.5f);
        leftAnalogOnScreenControl.getControlBase().setScaleCenter(0, 128);
        leftAnalogOnScreenControl.getControlBase().setScale(1.25f);
        leftAnalogOnScreenControl.getControlKnob().setScale(1.25f);
        leftAnalogOnScreenControl.refreshControlKnobPosition();

		this.registerTouchArea(pauseSprite);
		this.registerTouchArea(updateSprite);

		this.getChildByIndex(GAME_LAYER).attachChild(pauseSprite);
		this.getChildByIndex(GAME_LAYER).attachChild(updateSprite);
		
		//((Scene) this.getChildByIndex(GAME_LAYER)).setChildScene(leftAnalogOnScreenControl);
		mainCamera.setHUD(leftAnalogOnScreenControl);

		// loadPauseLayer();
		// loadUpgradeLayer();

		this.getChildByIndex(PAUSE_LAYER).setVisible(false);
		this.getChildByIndex(UPGRADE_LAYER).setVisible(false);
		this.getChildByIndex(PAUSE_LAYER).setAlpha(0.5f);
	}

	public void removeGameScene() {
		// remove
	}

	public Scene getGameScene() {
		return this;
	}

	public void showPauseMenu() {
		Log.i("NATS", "showPauseMenu");
		this.getChildByIndex(PAUSE_LAYER).setVisible(true);
		this.unregisterTouchArea(pauseSprite);
		this.unregisterTouchArea(updateSprite);
		// game.registerTouchArea(pTouchArea)
	}

	public void hidePauseMenu() {
		Log.i("NATS", "hidePauseMenu");
		this.getChildByIndex(PAUSE_LAYER).setVisible(false);
		this.unregisterPauseTouch();
		this.registerTouchArea(pauseSprite);
		this.registerTouchArea(updateSprite);
	}

	public void showUpgradeMenu() {
		Log.i("NATS", "showUpgradeMenu");
		this.getChildByIndex(UPGRADE_LAYER).setVisible(true);
		this.unregisterTouchArea(pauseSprite);
		this.unregisterTouchArea(updateSprite);
	}

	public void hideUpgradeMenu() {
		Log.i("NATS", "hideUpgradeMenu");
		this.getChildByIndex(UPGRADE_LAYER).setVisible(false);
		this.unregisterUpgradeTouch();
		this.registerTouchArea(pauseSprite);
		this.registerTouchArea(updateSprite);
	}

	public void registerPauseTouch() {
		this.registerTouchArea(pause[0]);
		this.registerTouchArea(pause[1]);
	}

	public void unregisterPauseTouch() {
		this.unregisterTouchArea(pause[0]);
		this.unregisterTouchArea(pause[1]);
	}

	public void registerUpgradeTouch() {
		this.registerTouchArea(upgrade[0]);
		this.registerTouchArea(upgrade[1]);
		this.registerTouchArea(upgrade[2]);
		this.registerTouchArea(upgrade[3]);
		this.registerTouchArea(upgrade[4]);
		this.registerTouchArea(upgrade[5]);
		this.registerTouchArea(upgrade[6]);
		this.registerTouchArea(upgrade[7]);
		this.registerTouchArea(upgrade[8]);
	}

	public void unregisterUpgradeTouch() {
		this.unregisterTouchArea(upgrade[0]);
		this.unregisterTouchArea(upgrade[1]);
		this.unregisterTouchArea(upgrade[2]);
		this.unregisterTouchArea(upgrade[3]);
		this.unregisterTouchArea(upgrade[4]);
		this.unregisterTouchArea(upgrade[5]);
		this.unregisterTouchArea(upgrade[6]);
		this.unregisterTouchArea(upgrade[7]);
		this.unregisterTouchArea(upgrade[8]);
	}

	public void attachPauseMenu(Scene pauseMenu) {
		this.getChildByIndex(PAUSE_LAYER).attachChild(pauseMenu);
	}

	public void attachUpgradeMenu(Scene upgradeMenu) {
		this.getChildByIndex(UPGRADE_LAYER).attachChild(upgradeMenu);
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

	@Override
	public void onControlChange(BaseOnScreenControl pBaseOnScreenControl,
			float pValueX, float pValueY) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void onControlClick(AnalogOnScreenControl pAnalogOnScreenControl) {
		// TODO Auto-generated method stub
		
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
