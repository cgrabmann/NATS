package at.stefan.nats;

import org.andengine.engine.camera.Camera;
import org.andengine.entity.scene.Scene;
import org.andengine.entity.sprite.Sprite;
import org.andengine.input.touch.TouchEvent;
import org.andengine.opengl.texture.TextureOptions;
import org.andengine.opengl.texture.atlas.bitmap.BitmapTextureAtlas;
import org.andengine.opengl.texture.atlas.bitmap.BitmapTextureAtlasTextureRegionFactory;
import org.andengine.opengl.texture.region.ITextureRegion;

import at.stefan.nats.SceneManager.AllScenes;

public class PauseMenu extends Scene {

	boolean touch = false;

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

	BitmapTextureAtlas quitBitmapTextureAtlas;
	ITextureRegion quitITextureRegion;
	Sprite quitSprite;

	public PauseMenu(nats nats, Camera cam, GameEnvironment ge, SceneManager s) {
		this.nats = nats;
		this.mainCamera = cam;
		this.gameEnvironment = ge;
		this.sceneManager = s;

		// this.setBackground(new Background(0.5f, 0.5f, 0.5f, 0.5f));
		this.setBackgroundEnabled(false);
	}

	public void loadPauseResources() {
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

		quitBitmapTextureAtlas = new BitmapTextureAtlas(
				nats.getTextureManager(), 310, 150, TextureOptions.DEFAULT);
		quitITextureRegion = BitmapTextureAtlasTextureRegionFactory
				.createFromAsset(quitBitmapTextureAtlas,
						nats.getApplicationContext(), "Quit.png", 0, 0);
		quitBitmapTextureAtlas.load();
	}

	public void loadPauseScene() {
		backgroundSprite = new Sprite(nats.getCameraWidth() / 2,
				nats.getCameraHeight() / 2, backgroundITextureRegion,
				nats.getVertexBufferObjectManager());
		backgroundSprite.setAlpha(0.4f);

		continueSprite = new Sprite(200, 100, continueITextureRegion,
				nats.getVertexBufferObjectManager()) {
			@Override
			public boolean onAreaTouched(TouchEvent pSceneTouchEvent, float X,
					float Y) {
				if (pSceneTouchEvent.isActionUp()) {
					// execute action
					// Log.i("NATS", "Update");
					// hidePauseMenu();
					// hideUpgradeMenu();
					gameEnvironment.hidePauseMenu();
				}
				return true;
			};
		};

		quitSprite = new Sprite(600, 100, quitITextureRegion,
				nats.getVertexBufferObjectManager()) {
			@Override
			public boolean onAreaTouched(TouchEvent pSceneTouchEvent, float X,
					float Y) {
				if (pSceneTouchEvent.isActionUp()) {
					// execute action
					// Log.i("NATS", "Update");
					// hidePauseMenu();
					// hideUpgradeMenu();
					gameEnvironment.hidePauseMenu();
					sceneManager.switchScene(AllScenes.MAIN_MENU);
				}
				return true;
			};
		};

		this.attachChild(backgroundSprite);
		this.attachChild(continueSprite);
		this.attachChild(quitSprite);

		// gameEnvironment.registerTouchArea(continueSprite);
		// this.registerTouchArea(quitSprite);
		gameEnvironment.attachPauseMenu(this);
		gameEnvironment.setPauseReference(continueSprite, quitSprite);

	}

	/*
	 * public void registerTouch() { gameEnvironment.registerPauseTouch();
	 * this.touch = true; }
	 * 
	 * public void unregisterTouch() { if(this.touch == true) {
	 * gameEnvironment.unregisterPauseTouch(); this.touch = false; } }
	 */

	/*
	 * public boolean getTouch() { return touch; }
	 * 
	 * public void setTouch(boolean t) { this.touch = t; }
	 */

}

/*
 * import android.annotation.SuppressLint; import android.graphics.Point; import
 * android.util.Log; import android.view.Display; import android.view.Gravity;
 * import android.view.LayoutInflater; import android.view.View; import
 * android.widget.Button; import android.widget.FrameLayout; import
 * android.widget.PopupWindow; import android.widget.RelativeLayout;
 * 
 * public class PauseMenu {
 * 
 * PopupWindow popup; FrameLayout frame; RelativeLayout rel; Button con, quit;
 * View view; MenuListener ml; private int width; private int height;
 * 
 * public PauseMenu(nats nats) {
 * 
 * LayoutInflater li = LayoutInflater.from(nats.getApplicationContext()); //view
 * =
 * li.inflate(R.layout.pause_480x800hdpi,(ViewGroup)nats.findViewById(R.layout.
 * new_game_480x800hdpi)); view = setPauseView(nats.getVersion(), li, nats);
 * popup = new PopupWindow(view);
 * 
 * }
 * 
 * @SuppressLint("NewApi")
 * 
 * @SuppressWarnings("deprecation") public void show(nats nats) {
 * 
 * frame =
 * (FrameLayout)nats.getWindow().getDecorView().findViewById(android.R.id
 * .content); rel = (RelativeLayout)frame.getChildAt(0);
 * 
 * Display display = nats.getWindowManager().getDefaultDisplay(); if
 * (android.os.Build.VERSION.SDK_INT >= 13) { Point size = new Point();
 * display.getSize(size); width = size.x; height = size.y; String h = "height: "
 * + height; String w = "width: " + width; Log.i("NATS", h+w); }else { width =
 * display.getWidth(); height = display.getHeight(); }
 * 
 * popup.showAtLocation(rel, Gravity.BOTTOM, 0, 0); popup.update(width, height);
 * 
 * con = (Button)view.findViewById(R.id.con); quit =
 * (Button)view.findViewById(R.id.quit);
 * 
 * ml = new MenuListener(con, quit, popup, nats);
 * 
 * con.setOnClickListener(ml); quit.setOnClickListener(ml); }
 * 
 * public void hide() { popup.dismiss(); }
 * 
 * public View setPauseView(String version, LayoutInflater li, nats nats) { View
 * view = null;
 * 
 * if(version == "240x320ldpi") { view =
 * li.inflate(R.layout.pause_480x800hdpi,(ViewGroup
 * )nats.findViewById(R.layout.new_game_480x800hdpi)); }else if(version ==
 * "240x400ldpi") { view =
 * li.inflate(R.layout.pause_480x800hdpi,(ViewGroup)nats.
 * findViewById(R.layout.new_game_480x800hdpi)); }else if(version ==
 * "320x480mdpi") { view =
 * li.inflate(R.layout.pause_480x800hdpi,(ViewGroup)nats.
 * findViewById(R.layout.new_game_480x800hdpi)); }else if(version ==
 * "480x800mdpi") { view =
 * li.inflate(R.layout.pause_480x800hdpi,(ViewGroup)nats.
 * findViewById(R.layout.new_game_480x800hdpi)); }else if(version ==
 * "480x854mdpi") { view =
 * li.inflate(R.layout.pause_480x800hdpi,(ViewGroup)nats.
 * findViewById(R.layout.new_game_480x800hdpi)); }else if(version ==
 * "600x1024mdpi") { view =
 * li.inflate(R.layout.pause_480x800hdpi,(ViewGroup)nats
 * .findViewById(R.layout.new_game_480x800hdpi)); }else if(version ==
 * "480x800hdpi") { view =
 * li.inflate(R.layout.pause_480x800hdpi,(ViewGroup)nats.
 * findViewById(R.layout.new_game_480x800hdpi)); }else if(version ==
 * "480x854hdpi") { view =
 * li.inflate(R.layout.pause_480x800hdpi,(ViewGroup)nats.
 * findViewById(R.layout.new_game_480x800hdpi)); }else if(version ==
 * "720x1280xhdpi") { view =
 * li.inflate(R.layout.pause_480x800hdpi,(ViewGroup)nats
 * .findViewById(R.layout.new_game_480x800hdpi)); }else if(version ==
 * "768x1280xhdpi") { view =
 * li.inflate(R.layout.pause_480x800hdpi,(ViewGroup)nats
 * .findViewById(R.layout.new_game_480x800hdpi)); }else if(version ==
 * "1200x1920xhdpi") { view =
 * li.inflate(R.layout.pause_480x800hdpi,(ViewGroup)nats
 * .findViewById(R.layout.new_game_480x800hdpi)); }else if(version ==
 * "1600x2560xhdpi") { view =
 * li.inflate(R.layout.pause_480x800hdpi,(ViewGroup)nats
 * .findViewById(R.layout.new_game_480x800hdpi)); }else if(version ==
 * "1080x1920xxhdpi") { view =
 * li.inflate(R.layout.pause_480x800hdpi,(ViewGroup)nats
 * .findViewById(R.layout.new_game_480x800hdpi)); }
 * 
 * return view; }
 * 
 * }
 */
