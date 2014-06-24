package at.stefan.nats;

import org.andengine.engine.LimitedFPSEngine;
import org.andengine.engine.camera.BoundCamera;
import org.andengine.entity.scene.Scene;
import org.andengine.entity.sprite.Sprite;
import org.andengine.opengl.texture.TextureOptions;
import org.andengine.opengl.texture.atlas.bitmap.BitmapTextureAtlas;
import org.andengine.opengl.texture.atlas.bitmap.BitmapTextureAtlasTextureRegionFactory;
import org.andengine.opengl.texture.region.ITextureRegion;

import android.content.Context;
import android.os.Vibrator;
import at.alex.nats.Player;

public class SceneManager {

	nats nats;
	LimitedFPSEngine mEngine;
	BoundCamera mainCamera;

	Vibrator vibrator;

	MainMenu mainMenu;
	Highscores highscores;
	Settings settings;
	Player player;
	GameEnvironment gameEnvironment;
	PauseMenu pauseMenu;
	UpgradeMenu upgradeMenu;
	GameOver gameOver;

	Scene splashScene;

	Finals finals;

	BitmapTextureAtlas splashBitmapTextureAtlas;
	ITextureRegion splashITextureRegion;
	Sprite splashSprite;

	private AllScenes currentScene = AllScenes.MAIN_MENU;

	public enum AllScenes {
		MAIN_MENU, NEW_GAME, HIGHSCORES, SETTINGS, EXIT_GAME, PAUSE, UPGRADE, GAME_OVER
	}

	public SceneManager(nats nats, LimitedFPSEngine mEngine, BoundCamera cam) {
		this.nats = nats;
		this.mEngine = mEngine;
		this.mainCamera = cam;

		finals = new Finals();
	}

	public void loadSplashResources() {
		BitmapTextureAtlasTextureRegionFactory.setAssetBasePath("backgrounds/");
		splashBitmapTextureAtlas = new BitmapTextureAtlas(
				nats.getTextureManager(), 800, 480, TextureOptions.DEFAULT);
		splashITextureRegion = BitmapTextureAtlasTextureRegionFactory
				.createFromAsset(splashBitmapTextureAtlas,
						nats.getApplicationContext(), "Titelbild.png", 0, 0);
		splashBitmapTextureAtlas.load();
	}

	public void initSplashScene() {
		splashScene = new Scene();
		splashSprite = new Sprite(nats.getCameraWidth() / 2,
				nats.getCameraHeight() / 2, splashITextureRegion,
				nats.getVertexBufferObjectManager());
		splashScene.attachChild(splashSprite);
	}

	public void removeSplashScene() {
		splashScene.detachChild(splashSprite);
	}

	public AllScenes getCurrentScene() {
		return currentScene;
	}

	public void switchScene(AllScenes scenes) {
		if (scenes == AllScenes.MAIN_MENU) {
			mEngine.setScene(mainMenu.getMainMenuScene());
			// pauseMenu.unregisterTouch();
			currentScene = AllScenes.MAIN_MENU;
		} else if (scenes == AllScenes.NEW_GAME) {
			mEngine.setScene(gameEnvironment.getGameScene());
			gameEnvironment.startTimer();
			player.playMusic();
			gameEnvironment.showGameHUD();
			mainCamera.setChaseEntity(player.getPlayerBase());
			currentScene = AllScenes.NEW_GAME;
		} else if (scenes == AllScenes.HIGHSCORES) {
			mEngine.setScene(highscores.getHighscoreScene());
			// highscores.centerCamera();
			currentScene = AllScenes.HIGHSCORES;
		} else if (scenes == AllScenes.SETTINGS) {
			mEngine.setScene(settings.getSettingsScene());
			currentScene = AllScenes.SETTINGS;
		} else if (scenes == AllScenes.PAUSE) {
			gameEnvironment.showPauseMenu();
			gameEnvironment.pauseTimer();
			// pauseMenu.registerTouch();
			currentScene = AllScenes.PAUSE;
		} else if (scenes == AllScenes.UPGRADE) {
			gameEnvironment.showUpgradeMenu();
			currentScene = AllScenes.UPGRADE;
		} else if (scenes == AllScenes.GAME_OVER) {
			gameEnvironment.showGameOverMenu();
			mEngine.setScene(gameOver);
			currentScene = AllScenes.GAME_OVER;
		}
	}

	public void loadAllResources() {
		mainMenu = new MainMenu(nats, mainCamera, this);
		highscores = new Highscores(nats, mainCamera);
		settings = new Settings(nats, mainCamera, this);
		player = new Player(nats, this);
		gameEnvironment = new GameEnvironment(nats, mainCamera, this, player);
		pauseMenu = new PauseMenu(nats, mainCamera, gameEnvironment, this);
		upgradeMenu = new UpgradeMenu(nats, mainCamera, gameEnvironment, this,
				player);
		gameOver = new GameOver(nats, mainCamera, gameEnvironment, this);

		vibrator = (Vibrator) nats.getSystemService(Context.VIBRATOR_SERVICE);

		mainMenu.loadMainMenuResources();
		highscores.loadHighscoreResources();
		settings.loadSettingsResources();
		gameEnvironment.loadGameResources();
		pauseMenu.loadPauseResources();
		upgradeMenu.loadUpgradeResources();
		gameOver.loadGameOverResources();

		mainMenu.loadMainMenuScene();
		highscores.loadHighscoreScene();
		settings.loadSettingsScene();
		gameEnvironment.loadGameScene();
		pauseMenu.loadPauseScene();
		upgradeMenu.loadUpgradeScene();
		gameOver.loadGameOverScene();
	}

	public GameEnvironment getGameEnvironment() {
		return this.gameEnvironment;
	}

	public GameOver getGameOver() {
		return this.gameOver;
	}

	public Highscores getHighscores() {
		return this.highscores;
	}

	public Player getPlayer() {
		return this.player;
	}

	public Settings getSettings() {
		return this.settings;
	}
	
	public Vibrator getVibrator() {
		return this.vibrator;
	}

}
