package at.stefan.nats;

import org.andengine.engine.Engine;
import org.andengine.engine.camera.Camera;
import org.andengine.entity.scene.Scene;
import org.andengine.entity.sprite.Sprite;
import org.andengine.opengl.texture.TextureOptions;
import org.andengine.opengl.texture.atlas.bitmap.BitmapTextureAtlas;
import org.andengine.opengl.texture.atlas.bitmap.BitmapTextureAtlasTextureRegionFactory;
import org.andengine.opengl.texture.region.ITextureRegion;

public class SceneManager {
	
	nats nats;
	Engine mEngine;
	Camera mainCamera;
	
	MainMenu mainMenu;
	Highscores highscores;
	Settings settings;
	GameEnvironment gameEnvironment;
	
	Scene splashScene;
	
	Finals finals;
	
	BitmapTextureAtlas splashBitmapTextureAtlas;
	ITextureRegion splashITextureRegion;
	Sprite splashSprite;

	private AllScenes currentScene = AllScenes.MAIN_MENU;

	public enum AllScenes {
		MAIN_MENU, NEW_GAME, HIGHSCORES, SETTINGS, EXIT_GAME, PAUSE, UPGRADE
	}

	public SceneManager(nats nats, Engine mEngine, Camera cam) {
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
			currentScene = AllScenes.MAIN_MENU;
		}else if(scenes == AllScenes.NEW_GAME) {
			mEngine.setScene(gameEnvironment.getGameScene());
			currentScene = AllScenes.NEW_GAME;
		}else if(scenes == AllScenes.HIGHSCORES) {
			mEngine.setScene(highscores.getHighscoreScene());
			currentScene = AllScenes.HIGHSCORES;
		}else if(scenes == AllScenes.SETTINGS) {
			mEngine.setScene(settings.getSettingsScene());
			currentScene = AllScenes.SETTINGS;
		}else if(scenes == AllScenes.PAUSE) {
			gameEnvironment.showPauseMenu();
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
		gameEnvironment = new GameEnvironment(nats, mainCamera, this);
		
		mainMenu.loadMainMenuResources();
		highscores.loadHighscoreResources();
		settings.loadSettingsResources();
		gameEnvironment.loadGameResources();
		
		mainMenu.loadMainMenuScene();
		highscores.loadHighscoreScene();
		settings.loadSettingsScene();
		gameEnvironment.loadGameScene();
	}
}
