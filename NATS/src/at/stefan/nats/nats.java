package at.stefan.nats;

import java.io.IOException;

import org.andengine.engine.Engine;
import org.andengine.engine.camera.BoundCamera;
import org.andengine.engine.handler.timer.ITimerCallback;
import org.andengine.engine.handler.timer.TimerHandler;
import org.andengine.engine.options.EngineOptions;
import org.andengine.engine.options.ScreenOrientation;
import org.andengine.engine.options.resolutionpolicy.FillResolutionPolicy;
import org.andengine.entity.scene.Scene;
import org.andengine.ui.activity.BaseGameActivity;

import android.view.KeyEvent;
import at.stefan.nats.SceneManager.AllScenes;

public class nats extends BaseGameActivity {

	BoundCamera mainCamera;
	public static final int CAMERA_WIDTH = 800;
	public static final int CAMERA_HEIGHT = 480;

	SceneManager sceneManager;

	Finals finals;

	@Override
	public EngineOptions onCreateEngineOptions() {
		// TODO Auto-generated method stub
		mainCamera = new BoundCamera(0, 0, 800, 480);
		EngineOptions options = new EngineOptions(true,
				ScreenOrientation.LANDSCAPE_FIXED, new FillResolutionPolicy(),
				mainCamera);
		options.getRenderOptions().setDithering(true);

		return options;
	}

	@Override
	public void onCreateResources(
			OnCreateResourcesCallback pOnCreateResourcesCallback)
			throws IOException {
		// TODO Auto-generated method stub
		sceneManager = new SceneManager(this, mEngine, mainCamera);
		finals = new Finals();

		sceneManager.loadSplashResources();

		pOnCreateResourcesCallback.onCreateResourcesFinished();
	}

	@Override
	public void onCreateScene(OnCreateSceneCallback pOnCreateSceneCallback)
			throws IOException {
		// TODO Auto-generated method stub
		sceneManager.initSplashScene();

		pOnCreateSceneCallback.onCreateSceneFinished(sceneManager.splashScene);
	}

	@Override
	public void onPopulateScene(Scene pScene,
			OnPopulateSceneCallback pOnPopulateSceneCallback)
			throws IOException {
		// TODO Auto-generated method stub
		mEngine.registerUpdateHandler(new TimerHandler(3f,
				new ITimerCallback() {
					public void onTimePassed(final TimerHandler pTimerHandler) {
						mEngine.unregisterUpdateHandler(pTimerHandler);
						sceneManager.loadAllResources();
						sceneManager.removeSplashScene();
						// mEngine.setScene(mainScene);
						sceneManager.switchScene(AllScenes.MAIN_MENU);
					}

				}));

		pOnPopulateSceneCallback.onPopulateSceneFinished();
	}

	@Override
	public boolean onKeyDown(int keyCode, KeyEvent event) {
		if (keyCode == KeyEvent.KEYCODE_BACK) {
			if (sceneManager.getCurrentScene() != AllScenes.MAIN_MENU) {
				if (sceneManager.getCurrentScene() == AllScenes.PAUSE
						|| sceneManager.getCurrentScene() == AllScenes.UPGRADE) {
					sceneManager.switchScene(AllScenes.NEW_GAME);
				}
				/*
				 * if(sceneManager.getCurrentScene() == AllScenes.HIGHSCORES) {
				 * // unload }else if(sceneManager.getCurrentScene() ==
				 * AllScenes.SETTINGS) { // unload }else
				 * if(sceneManager.getCurrentScene() == AllScenes.NEW_GAME) { //
				 * unload }
				 */
				sceneManager.switchScene(AllScenes.MAIN_MENU);
			} else {
				nats.this.finish();
			}
		}
		return false;
	}

	@Override
	protected void onDestroy() {
		super.onDestroy();

		if (this.isGameLoaded()) {
			System.exit(0);
		}
	}

	public int getCameraWidth() {
		return CAMERA_WIDTH;
	}

	public int getCameraHeight() {
		return CAMERA_HEIGHT;
	}
	
	public Engine getEngine() {
		return this.mEngine;
	}
}

/*
 * private String actualSite = "main_menu"; private boolean pauseActivated =
 * false; private boolean upgradeActivated = false; private static final int
 * HEIGHT = 0; private static final int WIDTH = 1; private String version;
 * 
 * //private int XML;
 * 
 * Button new_game; Button highscores; Button settings; Button exit; Button
 * pause; Button upgrade;
 * 
 * MenuListener ml; PauseMenu pm; UpgradeMenu um;
 * 
 * public void onCreate(Bundle savedInstanceState) {
 * 
 * super.onCreate(savedInstanceState);
 * 
 * // explore device density and size version = findSuitableXML();
 * 
 * elementSearchMain(); }
 * 
 * public boolean onKeyDown(int keyCode, KeyEvent event) { if(keyCode ==
 * KeyEvent.KEYCODE_BACK) { if(actualSite == "upgrade") {
 * setContentView(R.layout.new_game_480x800hdpi); upgradeActivated = false;
 * um.hide(); elementSearchGame(); }else if(actualSite != "main_menu") {
 * actualSite = "main_menu"; if(pauseActivated == true) { pauseActivated =
 * false; pm.hide(); } setMainMenu(); elementSearchMain(); } return true; }
 * 
 * return super.onKeyDown(keyCode, event); }
 * 
 * public void actualizeSite(String s) { this.actualSite = s;
 * 
 * if(actualSite == "pause") { Log.i("NATS", "in nats, pause");
 * if(pauseActivated == false) { pm = new PauseMenu(this); pm.show(this);
 * Log.i("NATS", "show pause"); pauseActivated = true; }else { pm.hide();
 * Log.i("NATS", "hide pause"); pauseActivated = false; }
 * 
 * }else if(actualSite == "upgrade") { if(upgradeActivated == false) { um = new
 * UpgradeMenu(this); um.show(this); upgradeActivated = true; }else { um.hide();
 * upgradeActivated = false; } //setUpgradeMenu();
 * //setContentView(R.layout.upgrades_480x800hdpi); }else if(actualSite ==
 * "new_game") { setContentView(R.layout.new_game_480x800hdpi); //Log.i("NATS",
 * Integer.toString(this.get)) elementSearchGame(); Log.i("NATS", "new game");
 * // Go to new game }else if(actualSite == "continue") { actualSite =
 * "new_game"; pauseActivated = false; // Spiel weiterlaufen lassen }else
 * if(actualSite == "quit") { //setContentView(R.layout.main_menu_480x800hdpi);
 * setMainMenu(); elementSearchMain(); pauseActivated = false; }else
 * if(actualSite == "highscores") {
 * setContentView(R.layout.highscores_480x800hdpi); Log.i("NATS",
 * "go to highscores"); }else if(actualSite == "settings") {
 * setContentView(R.layout.settings_480x800hdpi); Log.i("NATS", "settings");
 * }else if(actualSite == "exit") { Log.i("NATS", "Beende App");
 * nats.this.finish(); } //System.out.println(actualSite); }
 * 
 * private void elementSearchMain() { new_game =
 * (Button)findViewById(R.id.new_game); highscores =
 * (Button)findViewById(R.id.highscores); settings =
 * (Button)findViewById(R.id.settings); exit = (Button)findViewById(R.id.exit);
 * 
 * ml = new MenuListener(new_game, highscores, settings, exit, this);
 * 
 * new_game.setOnClickListener(ml); highscores.setOnClickListener(ml);
 * settings.setOnClickListener(ml); exit.setOnClickListener(ml); }
 * 
 * private void elementSearchGame() { pause =
 * (Button)findViewById(R.id.pause_background); upgrade =
 * (Button)findViewById(R.id.upgrade); ml = new MenuListener(pause, upgrade,
 * this); pause.setOnClickListener(ml); upgrade.setOnClickListener(ml); }
 * 
 * private int getDeviceDensity() { return
 * this.getResources().getDisplayMetrics().densityDpi; }
 * 
 * @SuppressWarnings("deprecation")
 * 
 * @SuppressLint("NewApi") private int[] getDeviceSize() { int[] device = new
 * int[2]; Display display = this.getWindowManager().getDefaultDisplay(); if
 * (android.os.Build.VERSION.SDK_INT >= 13) { Point size = new Point();
 * display.getSize(size); device[HEIGHT] = size.y; device[WIDTH] = size.x; }else
 * { device[WIDTH] = display.getWidth(); device[HEIGHT] = display.getHeight(); }
 * 
 * return device; }
 * 
 * private String findSuitableXML() { int[] device = getDeviceSize(); String
 * version = ""; switch(getDeviceDensity()) { case DisplayMetrics.DENSITY_LOW:
 * if(device[HEIGHT] <= 240 && device[WIDTH] <= 320) { version = "240x320ldpi";
 * setContentView(R.layout.main_menu_240x320ldpi); }else if(device[HEIGHT] <=
 * 240 && device[WIDTH] <= 432) { version = "240x400ldpi";
 * setContentView(R.layout.main_menu_240x400ldpi); } Log.i("NATS", "ldpi");
 * break; case DisplayMetrics.DENSITY_MEDIUM: if(device[HEIGHT] <= 320 &&
 * device[WIDTH] <= 480) { version = "320x480mdpi";
 * setContentView(R.layout.main_menu_320x480mdpi); }else if(device[HEIGHT] <=
 * 480 && device[WIDTH] <= 800) { version = "480x800mdpi";
 * setContentView(R.layout.main_menu_480x800mdpi); }else if(device[HEIGHT] <=
 * 480 && device[WIDTH] <= 854) { version = "480x854mdpi";
 * setContentView(R.layout.main_menu_480x854mdpi); }else if(device[HEIGHT] <=
 * 600 && device[WIDTH] <= 1024) { version = "600x1024mdpi";
 * setContentView(R.layout.main_menu_600x1024mdpi); } Log.i("NATS", "mdpi");
 * break; case DisplayMetrics.DENSITY_HIGH: if(device[HEIGHT] <= 480 &&
 * device[WIDTH] <= 800) { version = "480x800hdpi";
 * setContentView(R.layout.main_menu_480x800hdpi); }else if(device[HEIGHT] <=
 * 480 && device[WIDTH] <= 854) { version = "480x854hdpi";
 * setContentView(R.layout.main_menu_480x800hdpi); } Log.i("NATS", "hdpi");
 * break; case DisplayMetrics.DENSITY_XHIGH: if(device[HEIGHT] <= 720 &&
 * device[WIDTH] <= 1280) { version = "720x1280xhdpi";
 * setContentView(R.layout.main_menu_720x1280xhdpi); }else if(device[HEIGHT] <=
 * 768 && device[WIDTH] <= 1280) { version = "768x1280xhdpi";
 * setContentView(R.layout.main_menu_768x1280xhdpi); }else if(device[HEIGHT] <=
 * 1200 && device[WIDTH] <= 1920) { version = "1200x1920xhdpi";
 * setContentView(R.layout.main_menu_1200x1920xhdpi); }else if(device[HEIGHT] <=
 * 1600 && device[WIDTH] <= 2560) { version = "1600x2560xhdpi";
 * setContentView(R.layout.main_menu_1600x2560xhdpi); } Log.i("NATS", "xhdpi");
 * break; case DisplayMetrics.DENSITY_XXHIGH: version = "1080x1920xxhdpi";
 * setContentView(R.layout.main_menu_1080x1920xxhdpi); Log.i("NATS", "xxhdpi");
 * break; default: version = "480x800hdpi";
 * setContentView(R.layout.main_menu_480x800hdpi); }
 * 
 * return version; }
 * 
 * private void setMainMenu() { if(version == "240x320ldpi") {
 * 
 * }else if(version == "240x400ldpi") {
 * setContentView(R.layout.main_menu_240x320ldpi); }else if(version ==
 * "320x480mdpi") { setContentView(R.layout.main_menu_240x400ldpi); }else
 * if(version == "480x800mdpi") {
 * setContentView(R.layout.main_menu_480x800mdpi); }else if(version ==
 * "480x854mdpi") { setContentView(R.layout.main_menu_480x854mdpi); }else
 * if(version == "600x1024mdpi") {
 * setContentView(R.layout.main_menu_600x1024mdpi); }else if(version ==
 * "480x800hdpi") { setContentView(R.layout.main_menu_480x800hdpi); }else
 * if(version == "480x854hdpi") {
 * setContentView(R.layout.main_menu_480x800hdpi); }else if(version ==
 * "720x1280xhdpi") { setContentView(R.layout.main_menu_720x1280xhdpi); }else
 * if(version == "768x1280xhdpi") {
 * setContentView(R.layout.main_menu_768x1280xhdpi); }else if(version ==
 * "1200x1920xhdpi") { setContentView(R.layout.main_menu_1200x1920xhdpi); }else
 * if(version == "1600x2560xhdpi") {
 * setContentView(R.layout.main_menu_1600x2560xhdpi); }else if(version ==
 * "1080x1920xxhdpi") { setContentView(R.layout.main_menu_1080x1920xxhdpi); } }
 * 
 * public String getVersion() { return version; }
 */

