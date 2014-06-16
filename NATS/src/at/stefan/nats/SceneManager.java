package at.stefan.nats;

import org.andengine.engine.LimitedFPSEngine;
import org.andengine.engine.camera.BoundCamera;
import org.andengine.entity.scene.Scene;
import org.andengine.entity.sprite.Sprite;
import org.andengine.opengl.texture.TextureOptions;
import org.andengine.opengl.texture.atlas.bitmap.BitmapTextureAtlas;
import org.andengine.opengl.texture.atlas.bitmap.BitmapTextureAtlasTextureRegionFactory;
import org.andengine.opengl.texture.region.ITextureRegion;

import at.alex.nats.Player;

public class SceneManager {

	nats nats;
	LimitedFPSEngine mEngine;
	BoundCamera mainCamera;

	MainMenu mainMenu;
	Highscores highscores;
	Settings settings;
	Player player;
	GameEnvironment gameEnvironment;
	PauseMenu pauseMenu;
	UpgradeMenu upgradeMenu;

	Scene splashScene;

	Finals finals;

	BitmapTextureAtlas splashBitmapTextureAtlas;
	ITextureRegion splashITextureRegion;
	Sprite splashSprite;

	private AllScenes currentScene = AllScenes.MAIN_MENU;

	public enum AllScenes {
		MAIN_MENU, NEW_GAME, HIGHSCORES, SETTINGS, EXIT_GAME, PAUSE, UPGRADE
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
		splashSprite = new Sprite(nats.getCameraWidth() / 2, nats.getCameraHeight() / 2,
				splashITextureRegion, nats.getVertexBufferObjectManager());
		splashScene.attachChild(splashSprite);
	}

	public void removeSplashScene() {
		splashScene.detachChild(splashSprite);
	}

	public AllScenes getCurrentScene() {
		return currentScene;
	}

	public void switchScene(AllScenes scenes) {
		if(scenes == AllScenes.MAIN_MENU) {
			mEngine.setScene(mainMenu.getMainMenuScene());
			//pauseMenu.unregisterTouch();
			currentScene = AllScenes.MAIN_MENU;
		}else if(scenes == AllScenes.NEW_GAME) {
			mEngine.setScene(gameEnvironment.getGameScene());
			gameEnvironment.startTimer();
			player.playMusic();
			gameEnvironment.showGameHUD();
			currentScene = AllScenes.NEW_GAME;
		}else if(scenes == AllScenes.HIGHSCORES) {
			mEngine.setScene(highscores.getHighscoreScene());
			currentScene = AllScenes.HIGHSCORES;
		}else if(scenes == AllScenes.SETTINGS) {
			mEngine.setScene(settings.getSettingsScene());
			currentScene = AllScenes.SETTINGS;
		}else if(scenes == AllScenes.PAUSE) {
			gameEnvironment.showPauseMenu();
			gameEnvironment.pauseTimer();
			//pauseMenu.registerTouch();
			currentScene = AllScenes.PAUSE;
		}else if(scenes == AllScenes.UPGRADE) {
			gameEnvironment.showUpgradeMenu();
			currentScene = AllScenes.UPGRADE;
		}
	}

	public void loadAllResources() {
		mainMenu = new MainMenu(nats, mainCamera, this);
		highscores = new Highscores(nats, mainCamera);
		settings = new Settings(nats, mainCamera);
		player = new Player(nats);
		gameEnvironment = new GameEnvironment(nats, mainCamera, this, player);
		pauseMenu = new PauseMenu(nats, mainCamera, gameEnvironment, this);
		upgradeMenu = new UpgradeMenu(nats, mainCamera, gameEnvironment, this, player);

		mainMenu.loadMainMenuResources();
		highscores.loadHighscoreResources();
		settings.loadSettingsResources();
		gameEnvironment.loadGameResources();
		pauseMenu.loadPauseResources();
		upgradeMenu.loadUpgradeResources();

		mainMenu.loadMainMenuScene();
		highscores.loadHighscoreScene();
		settings.loadSettingsScene();
		gameEnvironment.loadGameScene();
		pauseMenu.loadPauseScene();
		upgradeMenu.loadUpgradeScene();
	}
	
	public GameEnvironment getGameEnvironment() {
		return this.gameEnvironment;
	}

}
