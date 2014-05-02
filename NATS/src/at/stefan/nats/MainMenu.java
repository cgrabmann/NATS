package at.stefan.nats;

import org.andengine.engine.camera.Camera;
import org.andengine.entity.scene.menu.MenuScene;
import org.andengine.entity.scene.menu.item.IMenuItem;
import org.andengine.entity.scene.menu.item.SpriteMenuItem;
import org.andengine.entity.scene.menu.item.decorator.ScaleMenuItemDecorator;
import org.andengine.entity.sprite.Sprite;
import org.andengine.opengl.texture.TextureOptions;
import org.andengine.opengl.texture.atlas.bitmap.BitmapTextureAtlas;
import org.andengine.opengl.texture.atlas.bitmap.BitmapTextureAtlasTextureRegionFactory;
import org.andengine.opengl.texture.atlas.bitmap.BuildableBitmapTextureAtlas;
import org.andengine.opengl.texture.atlas.bitmap.source.IBitmapTextureAtlasSource;
import org.andengine.opengl.texture.atlas.buildable.builder.BlackPawnTextureAtlasBuilder;
import org.andengine.opengl.texture.atlas.buildable.builder.ITextureAtlasBuilder.TextureAtlasBuilderException;
import org.andengine.opengl.texture.region.ITextureRegion;

public class MainMenu {

	nats nats;
	Camera mainCamera;

	Finals finals;
	MenuListener menuListener;

	BuildableBitmapTextureAtlas newGameBuildableBitmapTextureAtlas;
	ITextureRegion newGameITextureRegion;
	ITextureRegion highscoresITextureRegion;
	ITextureRegion settingsITextureRegion;
	ITextureRegion exitGameITextureRegion;

	BitmapTextureAtlas mainMenuBitmapTextureAtlas;
	ITextureRegion mainMenuITextureRegion;
	Sprite mainMenuSprite;

	MenuScene mainScene;

	public MainMenu(nats nats, Camera mainCamera, SceneManager sceneManager) {
		this.nats = nats;
		this.mainCamera = mainCamera;

		finals = new Finals();
		menuListener = new MenuListener(nats, sceneManager);
	}

	public void loadMainMenuResources() {
		// TODO Auto-generated method stub
		BitmapTextureAtlasTextureRegionFactory.setAssetBasePath("backgrounds/");
		mainMenuBitmapTextureAtlas = new BitmapTextureAtlas(
				nats.getTextureManager(), 800, 480, TextureOptions.DEFAULT);
		mainMenuITextureRegion = BitmapTextureAtlasTextureRegionFactory
				.createFromAsset(mainMenuBitmapTextureAtlas,
						nats.getApplicationContext(), "MainMenu.jpg", 0, 0);
		mainMenuBitmapTextureAtlas.load();

		BitmapTextureAtlasTextureRegionFactory.setAssetBasePath("buttons/");
		newGameBuildableBitmapTextureAtlas = new BuildableBitmapTextureAtlas(
				nats.getTextureManager(), 400, 400, TextureOptions.DEFAULT);
		newGameITextureRegion = BitmapTextureAtlasTextureRegionFactory
				.createFromAsset(newGameBuildableBitmapTextureAtlas,
						nats.getApplicationContext(), "NewGame.png");
		highscoresITextureRegion = BitmapTextureAtlasTextureRegionFactory
				.createFromAsset(newGameBuildableBitmapTextureAtlas,
						nats.getApplicationContext(), "Highscores.png");
		settingsITextureRegion = BitmapTextureAtlasTextureRegionFactory
				.createFromAsset(newGameBuildableBitmapTextureAtlas,
						nats.getApplicationContext(), "Settings.png");
		exitGameITextureRegion = BitmapTextureAtlasTextureRegionFactory
				.createFromAsset(newGameBuildableBitmapTextureAtlas,
						nats.getApplicationContext(), "ExitGame.png");

		try {
			this.newGameBuildableBitmapTextureAtlas
					.build(new BlackPawnTextureAtlasBuilder<IBitmapTextureAtlasSource, BitmapTextureAtlas>(
							0, 1, 0));
			this.newGameBuildableBitmapTextureAtlas.load();
		} catch (TextureAtlasBuilderException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	public void loadMainMenuScene() {
		// TODO Auto-generated method stub

		mainScene = new MenuScene(mainCamera);
		mainMenuSprite = new Sprite(nats.getCameraWidth() / 2,
				nats.getCameraHeight() / 2, mainMenuITextureRegion,
				nats.getVertexBufferObjectManager());
		mainScene.attachChild(mainMenuSprite);
		// mainScene.setBackground(new Background(0, 255, 0));
		// mainScene.setPosition(0, 0);

		final IMenuItem new_game = new ScaleMenuItemDecorator(
				new SpriteMenuItem(finals.new_game(), newGameITextureRegion,
						nats.getVertexBufferObjectManager()), 1.2f, 1);
		final IMenuItem highscores = new ScaleMenuItemDecorator(
				new SpriteMenuItem(finals.highscores(),
						highscoresITextureRegion,
						nats.getVertexBufferObjectManager()), 1.2f, 1);
		final IMenuItem settings = new ScaleMenuItemDecorator(
				new SpriteMenuItem(finals.settings(), settingsITextureRegion,
						nats.getVertexBufferObjectManager()), 1.2f, 1);
		final IMenuItem exit_game = new ScaleMenuItemDecorator(
				new SpriteMenuItem(finals.exit_game(), exitGameITextureRegion,
						nats.getVertexBufferObjectManager()), 1.2f, 1);

		mainScene.addMenuItem(new_game);
		mainScene.addMenuItem(highscores);
		mainScene.addMenuItem(settings);
		mainScene.addMenuItem(exit_game);

		mainScene.buildAnimations();
		mainScene.setBackgroundEnabled(true);

		new_game.setPosition(600, 400);
		highscores.setPosition(600, 300);
		settings.setPosition(600, 200);
		exit_game.setPosition(600, 100);

		mainScene.setOnMenuItemClickListener(menuListener);

		// mainScene.setChildScene(mainScene);
	}

	public void removeMainScene() {
		// mainScene.detachChild(pEntity)
		mainScene.detachChild(mainMenuSprite);
	}

	public MenuScene getMainMenuScene() {
		return mainScene;
	}
}
