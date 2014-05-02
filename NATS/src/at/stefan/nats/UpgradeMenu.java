package at.stefan.nats;

import org.andengine.engine.camera.Camera;
import org.andengine.entity.scene.Scene;
import org.andengine.entity.sprite.Sprite;
import org.andengine.entity.text.Text;
import org.andengine.entity.text.TextOptions;
import org.andengine.input.touch.TouchEvent;
import org.andengine.opengl.font.Font;
import org.andengine.opengl.font.FontFactory;
import org.andengine.opengl.texture.TextureOptions;
import org.andengine.opengl.texture.atlas.bitmap.BitmapTextureAtlas;
import org.andengine.opengl.texture.atlas.bitmap.BitmapTextureAtlasTextureRegionFactory;
import org.andengine.opengl.texture.region.ITextureRegion;
import org.andengine.util.adt.align.HorizontalAlign;
import org.andengine.util.adt.color.Color;

import android.graphics.Typeface;
import at.stefan.nats.ProgressBar.AndEngine;
import at.stefan.nats.SceneManager.AllScenes;

public class UpgradeMenu extends Scene {

	boolean touch = false;

	nats nats;
	Camera mainCamera;
	GameEnvironment gameEnvironment;
	SceneManager sceneManager;

	Font myFont;

	BitmapTextureAtlas backgroundBitmapTextureAtlas;
	ITextureRegion backgroundITextureRegion;
	Sprite backgroundSprite;

	BitmapTextureAtlas movespeedBitmapTextureAtlas;
	ITextureRegion movespeedITextureRegion;
	Sprite movespeedSprite;
	ProgressBar movespeedProgressBar;

	BitmapTextureAtlas gunnerBitmapTextureAtlas;
	ITextureRegion gunnerITextureRegion;
	Sprite gunnerSprite;
	ProgressBar gunnerProgressBar;

	BitmapTextureAtlas shieldBitmapTextureAtlas;
	ITextureRegion shieldITextureRegion;
	Sprite shieldSprite;
	ProgressBar shieldProgressBar;

	BitmapTextureAtlas shotfrequenceBitmapTextureAtlas;
	ITextureRegion shotfrequenceITextureRegion;
	Sprite shotfrequenceSprite;
	ProgressBar shotfrequenceProgressBar;

	BitmapTextureAtlas shotspreadingBitmapTextureAtlas;
	ITextureRegion shotspreadingITextureRegion;
	Sprite shotspreadingSprite;
	ProgressBar shotspreadingProgressBar;

	BitmapTextureAtlas stasisfieldBitmapTextureAtlas;
	ITextureRegion stasisfieldITextureRegion;
	Sprite stasisfieldSprite;
	BitmapTextureAtlas stasisfieldCounterBitmapTextureAtlas;
	ITextureRegion stasisfieldCounterITextureRegion;
	Text stasisfieldText;

	BitmapTextureAtlas turboBitmapTextureAtlas;
	ITextureRegion turboITextureRegion;
	Sprite turboSprite;
	Text turboText;

	BitmapTextureAtlas deadlytrailBitmapTextureAtlas;
	ITextureRegion deadlytrailITextureRegion;
	Sprite deadlytrailSprite;
	Text deadlytrailText;

	BitmapTextureAtlas bombBitmapTextureAtlas;
	ITextureRegion bombITextureRegion;
	Sprite bombSprite;
	Text bombText;

	public UpgradeMenu(nats nats, Camera cam, GameEnvironment ge, SceneManager s) {
		this.nats = nats;
		this.mainCamera = cam;
		this.gameEnvironment = ge;
		this.sceneManager = s;

		// this.setBackground(new Background(255, 0, 0));
		this.setBackgroundEnabled(false);
	}

	public void loadUpgradeResources() {
		BitmapTextureAtlasTextureRegionFactory.setAssetBasePath("gfx/");

		backgroundBitmapTextureAtlas = new BitmapTextureAtlas(
				nats.getTextureManager(), 800, 480, TextureOptions.DEFAULT);
		backgroundITextureRegion = BitmapTextureAtlasTextureRegionFactory
				.createFromAsset(backgroundBitmapTextureAtlas,
						nats.getApplicationContext(), "Grau.png", 0, 0);
		backgroundBitmapTextureAtlas.load();

		movespeedBitmapTextureAtlas = new BitmapTextureAtlas(
				nats.getTextureManager(), 100, 100, TextureOptions.DEFAULT);
		movespeedITextureRegion = BitmapTextureAtlasTextureRegionFactory
				.createFromAsset(movespeedBitmapTextureAtlas,
						nats.getApplicationContext(), "100.png", 0, 0);
		movespeedBitmapTextureAtlas.load();
		movespeedProgressBar = new ProgressBar(250,
				nats.getCameraHeight() - 70, 200, 20,
				AndEngine.GLES2_AnchorCenter);
		// movespeedProgressBar.setBackGroundColor(new Color(0.0f, 1.0f, 0.0f));
		// movespeedProgressBar.setForeGroundColor(new Color(0.0f, 0.0f, 1.0f));
		movespeedProgressBar.setIntervall(5);
		// movespeedProgressBar.show();

		gunnerBitmapTextureAtlas = new BitmapTextureAtlas(
				nats.getTextureManager(), 100, 100, TextureOptions.DEFAULT);
		gunnerITextureRegion = BitmapTextureAtlasTextureRegionFactory
				.createFromAsset(gunnerBitmapTextureAtlas,
						nats.getApplicationContext(), "100.png", 0, 0);
		gunnerBitmapTextureAtlas.load();
		gunnerProgressBar = new ProgressBar(250, nats.getCameraHeight() - 190,
				200, 20, AndEngine.GLES2_AnchorCenter);
		// gunnerProgressBar.setBackGroundColor(new Color(0.0f, 1.0f, 0.0f));
		// gunnerProgressBar.setForeGroundColor(new Color(1.0f, 0.0f, 1.0f));
		gunnerProgressBar.setIntervall(5);

		shieldBitmapTextureAtlas = new BitmapTextureAtlas(
				nats.getTextureManager(), 100, 100, TextureOptions.DEFAULT);
		shieldITextureRegion = BitmapTextureAtlasTextureRegionFactory
				.createFromAsset(shieldBitmapTextureAtlas,
						nats.getApplicationContext(), "100.png", 0, 0);
		shieldBitmapTextureAtlas.load();
		shieldProgressBar = new ProgressBar(250, 170, 200, 20,
				AndEngine.GLES2_AnchorCenter);
		// shieldProgressBar.setBackGroundColor(new Color(0.0f, 1.0f, 0.0f));
		// shieldProgressBar.setForeGroundColor(new Color(1.0f, 1.0f, 1.0f));
		shieldProgressBar.setIntervall(5);

		shotfrequenceBitmapTextureAtlas = new BitmapTextureAtlas(
				nats.getTextureManager(), 100, 100, TextureOptions.DEFAULT);
		shotfrequenceITextureRegion = BitmapTextureAtlasTextureRegionFactory
				.createFromAsset(shotfrequenceBitmapTextureAtlas,
						nats.getApplicationContext(), "100.png", 0, 0);
		shotfrequenceBitmapTextureAtlas.load();
		shotfrequenceProgressBar = new ProgressBar(nats.getCameraWidth() - 150,
				nats.getCameraHeight() - 70, 200, 20,
				AndEngine.GLES2_AnchorCenter);
		// shotfrequenceProgressBar.setBackGroundColor(new Color(0.0f, 1.0f,
		// 0.0f));
		// shotfrequenceProgressBar.setForeGroundColor(new Color(0.0f, 1.0f,
		// 1.0f));
		shotfrequenceProgressBar.setIntervall(5);

		shotspreadingBitmapTextureAtlas = new BitmapTextureAtlas(
				nats.getTextureManager(), 100, 100, TextureOptions.DEFAULT);
		shotspreadingITextureRegion = BitmapTextureAtlasTextureRegionFactory
				.createFromAsset(shotspreadingBitmapTextureAtlas,
						nats.getApplicationContext(), "100.png", 0, 0);
		shotspreadingBitmapTextureAtlas.load();
		shotspreadingProgressBar = new ProgressBar(nats.getCameraWidth() - 150,
				nats.getCameraHeight() - 190, 200, 20,
				AndEngine.GLES2_AnchorCenter);
		// shotspreadingProgressBar.setBackGroundColor(new Color(0.0f, 1.0f,
		// 0.0f));
		// shotspreadingProgressBar.setForeGroundColor(new Color(0.5f, 0.5f,
		// 0.5f));
		shotspreadingProgressBar.setIntervall(5);

		myFont = FontFactory.create(nats.getFontManager(),
				nats.getTextureManager(), 256, 256,
				Typeface.create(Typeface.DEFAULT, Typeface.BOLD), 32);
		myFont.load();

		stasisfieldBitmapTextureAtlas = new BitmapTextureAtlas(
				nats.getTextureManager(), 100, 100, TextureOptions.DEFAULT);
		stasisfieldITextureRegion = BitmapTextureAtlasTextureRegionFactory
				.createFromAsset(stasisfieldBitmapTextureAtlas,
						nats.getApplicationContext(), "100.png", 0, 0);
		stasisfieldBitmapTextureAtlas.load();
		stasisfieldText = new Text(100, 100, myFont, "2", new TextOptions(
				HorizontalAlign.CENTER), nats.getVertexBufferObjectManager());

		turboBitmapTextureAtlas = new BitmapTextureAtlas(
				nats.getTextureManager(), 100, 100, TextureOptions.DEFAULT);
		turboITextureRegion = BitmapTextureAtlasTextureRegionFactory
				.createFromAsset(turboBitmapTextureAtlas,
						nats.getApplicationContext(), "100.png", 0, 0);
		turboBitmapTextureAtlas.load();
		turboText = new Text(200, 200, myFont, "1", new TextOptions(
				HorizontalAlign.CENTER), nats.getVertexBufferObjectManager());

		deadlytrailBitmapTextureAtlas = new BitmapTextureAtlas(
				nats.getTextureManager(), 100, 100, TextureOptions.DEFAULT);
		deadlytrailITextureRegion = BitmapTextureAtlasTextureRegionFactory
				.createFromAsset(deadlytrailBitmapTextureAtlas,
						nats.getApplicationContext(), "100.png", 0, 0);
		deadlytrailBitmapTextureAtlas.load();
		deadlytrailText = new Text(300, 300, myFont, "0", new TextOptions(
				HorizontalAlign.CENTER), nats.getVertexBufferObjectManager());

		bombBitmapTextureAtlas = new BitmapTextureAtlas(
				nats.getTextureManager(), 100, 100, TextureOptions.DEFAULT);
		bombITextureRegion = BitmapTextureAtlasTextureRegionFactory
				.createFromAsset(bombBitmapTextureAtlas,
						nats.getApplicationContext(), "100.png", 0, 0);
		bombBitmapTextureAtlas.load();
		bombText = new Text(400, 400, myFont, "2", new TextOptions(
				HorizontalAlign.CENTER), nats.getVertexBufferObjectManager());

	}

	public void loadUpgradeScene() {

		backgroundSprite = new Sprite(nats.getCameraWidth() / 2,
				nats.getCameraHeight() / 2, backgroundITextureRegion,
				nats.getVertexBufferObjectManager());
		backgroundSprite.setAlpha(0.4f);

		movespeedSprite = new Sprite(100, nats.getCameraHeight() - 70,
				movespeedITextureRegion, nats.getVertexBufferObjectManager()) {
			@Override
			public boolean onAreaTouched(TouchEvent pSceneTouchEvent, float X,
					float Y) {
				if (pSceneTouchEvent.isActionUp()) {
					movespeedProgressBar.increaseProgress();
					// gameEnvironment.hideUpgradeMenu();
				}
				return true;
			};
		};

		gunnerSprite = new Sprite(100, nats.getCameraHeight() - 190,
				gunnerITextureRegion, nats.getVertexBufferObjectManager()) {
			@Override
			public boolean onAreaTouched(TouchEvent pSceneTouchEvent, float X,
					float Y) {
				if (pSceneTouchEvent.isActionUp()) {
					gunnerProgressBar.increaseProgress();
					// gameEnvironment.hideUpgradeMenu();
					// sceneManager.switchScene(AllScenes.MAIN_MENU);
				}
				return true;
			};
		};

		shieldSprite = new Sprite(100, 170, shieldITextureRegion,
				nats.getVertexBufferObjectManager()) {
			@Override
			public boolean onAreaTouched(TouchEvent pSceneTouchEvent, float X,
					float Y) {
				if (pSceneTouchEvent.isActionUp()) {
					shieldProgressBar.increaseProgress();
					// gameEnvironment.hideUpgradeMenu();
				}
				return true;
			};
		};

		shotfrequenceSprite = new Sprite(500, nats.getCameraHeight() - 70,
				shotfrequenceITextureRegion,
				nats.getVertexBufferObjectManager()) {
			@Override
			public boolean onAreaTouched(TouchEvent pSceneTouchEvent, float X,
					float Y) {
				if (pSceneTouchEvent.isActionUp()) {
					shotfrequenceProgressBar.increaseProgress();
					// gameEnvironment.hideUpgradeMenu();
					// sceneManager.switchScene(AllScenes.MAIN_MENU);
				}
				return true;
			};
		};

		shotspreadingSprite = new Sprite(500, nats.getCameraHeight() - 190,
				shotspreadingITextureRegion,
				nats.getVertexBufferObjectManager()) {
			@Override
			public boolean onAreaTouched(TouchEvent pSceneTouchEvent, float X,
					float Y) {
				if (pSceneTouchEvent.isActionUp()) {
					shotspreadingProgressBar.increaseProgress();
					// gameEnvironment.hideUpgradeMenu();
				}
				return true;
			};
		};

		stasisfieldSprite = new Sprite(70, 60, stasisfieldITextureRegion,
				nats.getVertexBufferObjectManager()) {
			@Override
			public boolean onAreaTouched(TouchEvent pSceneTouchEvent, float X,
					float Y) {
				if (pSceneTouchEvent.isActionUp()) {
					gameEnvironment.hideUpgradeMenu();
					sceneManager.switchScene(AllScenes.MAIN_MENU);
				}
				return true;
			};
		};

		turboSprite = new Sprite(270, 60, turboITextureRegion,
				nats.getVertexBufferObjectManager()) {
			@Override
			public boolean onAreaTouched(TouchEvent pSceneTouchEvent, float X,
					float Y) {
				if (pSceneTouchEvent.isActionUp()) {
					gameEnvironment.hideUpgradeMenu();
				}
				return true;
			};
		};

		deadlytrailSprite = new Sprite(470, 60, deadlytrailITextureRegion,
				nats.getVertexBufferObjectManager()) {
			@Override
			public boolean onAreaTouched(TouchEvent pSceneTouchEvent, float X,
					float Y) {
				if (pSceneTouchEvent.isActionUp()) {
					gameEnvironment.hideUpgradeMenu();
					sceneManager.switchScene(AllScenes.MAIN_MENU);
				}
				return true;
			};
		};

		bombSprite = new Sprite(670, 60, bombITextureRegion,
				nats.getVertexBufferObjectManager()) {
			@Override
			public boolean onAreaTouched(TouchEvent pSceneTouchEvent, float X,
					float Y) {
				if (pSceneTouchEvent.isActionUp()) {
					gameEnvironment.hideUpgradeMenu();
				}
				return true;
			};
		};

		this.attachChild(backgroundSprite);
		this.attachChild(movespeedSprite);
		movespeedProgressBar.attach(this);
		this.attachChild(gunnerSprite);
		gunnerProgressBar.attach(this);
		this.attachChild(shieldSprite);
		shieldProgressBar.attach(this);
		this.attachChild(shotfrequenceSprite);
		shotfrequenceProgressBar.attach(this);
		this.attachChild(shotspreadingSprite);
		shotspreadingProgressBar.attach(this);
		this.attachChild(stasisfieldSprite);
		this.attachChild(stasisfieldText);
		this.attachChild(turboSprite);
		this.attachChild(deadlytrailSprite);
		this.attachChild(bombSprite);

		// this.setChildScene(info);
		// this.attachChild(info);

		// gameEnvironment.registerTouchArea(continueSprite);
		// this.registerTouchArea(quitSprite);
		gameEnvironment.attachUpgradeMenu(this);
		gameEnvironment.setUpgradeReference(movespeedSprite, gunnerSprite,
				shieldSprite, shotfrequenceSprite, shotspreadingSprite,
				stasisfieldSprite, turboSprite, deadlytrailSprite, bombSprite);

	}
}

/*
 * import android.annotation.SuppressLint; import android.graphics.Point; import
 * android.view.Display; import android.view.Gravity; import
 * android.view.LayoutInflater; import android.view.View; import
 * android.view.ViewGroup; import android.widget.Button; import
 * android.widget.FrameLayout; import android.widget.PopupWindow; import
 * android.widget.RelativeLayout;
 * 
 * public class UpgradeMenu { View view; PopupWindow popup; FrameLayout frame;
 * RelativeLayout rel; Button con, quit; MenuListener ml;
 * 
 * private int width; private int height;
 * 
 * public UpgradeMenu(nats nats) {
 * 
 * LayoutInflater li = LayoutInflater.from(nats.getApplicationContext()); //view
 * =
 * li.inflate(R.layout.pause_480x800hdpi,(ViewGroup)nats.findViewById(R.layout.
 * new_game_480x800hdpi)); view = setUpgradeView(nats.getVersion(), li, nats);
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
 * display.getSize(size); width = size.x; height = size.y; }else { width =
 * display.getWidth(); height = display.getHeight(); }
 * 
 * popup.showAtLocation(rel, Gravity.BOTTOM, 0, 0); popup.update(width, height);
 * }
 * 
 * public void hide() { popup.dismiss(); }
 * 
 * public View setUpgradeView(String version, LayoutInflater li, nats nats) {
 * View view = null;
 * 
 * if(version == "240x320ldpi") { view =
 * li.inflate(R.layout.upgrades_480x800hdpi
 * ,(ViewGroup)nats.findViewById(R.layout.new_game_480x800hdpi)); }else
 * if(version == "240x400ldpi") { view =
 * li.inflate(R.layout.upgrades_480x800hdpi
 * ,(ViewGroup)nats.findViewById(R.layout.new_game_480x800hdpi)); }else
 * if(version == "320x480mdpi") { view =
 * li.inflate(R.layout.upgrades_480x800hdpi
 * ,(ViewGroup)nats.findViewById(R.layout.new_game_480x800hdpi)); }else
 * if(version == "480x800mdpi") { view =
 * li.inflate(R.layout.upgrades_480x800hdpi
 * ,(ViewGroup)nats.findViewById(R.layout.new_game_480x800hdpi)); }else
 * if(version == "480x854mdpi") { view =
 * li.inflate(R.layout.upgrades_480x800hdpi
 * ,(ViewGroup)nats.findViewById(R.layout.new_game_480x800hdpi)); }else
 * if(version == "600x1024mdpi") { view =
 * li.inflate(R.layout.upgrades_480x800hdpi
 * ,(ViewGroup)nats.findViewById(R.layout.new_game_480x800hdpi)); }else
 * if(version == "480x800hdpi") { view =
 * li.inflate(R.layout.upgrades_480x800hdpi
 * ,(ViewGroup)nats.findViewById(R.layout.new_game_480x800hdpi)); }else
 * if(version == "480x854hdpi") { view =
 * li.inflate(R.layout.upgrades_480x800hdpi
 * ,(ViewGroup)nats.findViewById(R.layout.new_game_480x800hdpi)); }else
 * if(version == "720x1280xhdpi") { view =
 * li.inflate(R.layout.upgrades_480x800hdpi
 * ,(ViewGroup)nats.findViewById(R.layout.new_game_480x800hdpi)); }else
 * if(version == "768x1280xhdpi") { view =
 * li.inflate(R.layout.upgrades_480x800hdpi
 * ,(ViewGroup)nats.findViewById(R.layout.new_game_480x800hdpi)); }else
 * if(version == "1200x1920xhdpi") { view =
 * li.inflate(R.layout.upgrades_480x800hdpi
 * ,(ViewGroup)nats.findViewById(R.layout.new_game_480x800hdpi)); }else
 * if(version == "1600x2560xhdpi") { view =
 * li.inflate(R.layout.upgrades_480x800hdpi
 * ,(ViewGroup)nats.findViewById(R.layout.new_game_480x800hdpi)); }else
 * if(version == "1080x1920xxhdpi") { view =
 * li.inflate(R.layout.upgrades_480x800hdpi
 * ,(ViewGroup)nats.findViewById(R.layout.new_game_480x800hdpi)); }
 * 
 * return view; } }
 */
