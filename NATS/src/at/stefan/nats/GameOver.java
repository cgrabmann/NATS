package at.stefan.nats;

import org.andengine.engine.camera.BoundCamera;
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
import at.stefan.nats.SceneManager.AllScenes;

public class GameOver extends Scene {

	// boolean touch = false;
	private int newScore = 0;

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

	BitmapTextureAtlas titleBitmapTextureAtlas;
	ITextureRegion titleITextureRegion;
	Sprite titleSprite;

	InputText input;

	Font myFont;
	Text score;

	public GameOver(nats nats, BoundCamera cam, GameEnvironment ge,
			SceneManager s) {
		this.nats = nats;
		this.mainCamera = cam;
		this.gameEnvironment = ge;
		this.sceneManager = s;

		// this.setBackground(new Background(0.5f, 0.5f, 0.5f, 0.5f));
		// this.setBackgroundEnabled(false);
	}

	public void loadGameOverResources() {
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

		BitmapTextureAtlasTextureRegionFactory.setAssetBasePath("titles/");
		titleBitmapTextureAtlas = new BitmapTextureAtlas(
				nats.getTextureManager(), 560, 120, TextureOptions.DEFAULT);
		titleITextureRegion = BitmapTextureAtlasTextureRegionFactory
				.createFromAsset(titleBitmapTextureAtlas,
						nats.getApplicationContext(), "GameOverTitle.png", 0, 0);
		titleBitmapTextureAtlas.load();

		myFont = FontFactory.create(nats.getFontManager(),
				nats.getTextureManager(), 256, 256,
				Typeface.create(Typeface.DEFAULT, Typeface.BOLD), 60,
				Color.RED.hashCode());
		myFont.load();

		input = new InputText(400f, 240f, "New Highscore!", "Enter your Name:",
				titleITextureRegion, myFont, 50, 50,
				nats.getVertexBufferObjectManager(), nats, sceneManager.getHighscores());
	}

	public void loadGameOverScene() {
		backgroundSprite = new Sprite(400, 240, backgroundITextureRegion,
				nats.getVertexBufferObjectManager());
		backgroundSprite.setAlpha(0.4f);

		continueSprite = new Sprite(400, 130, continueITextureRegion,
				nats.getVertexBufferObjectManager()) {
			@Override
			public boolean onAreaTouched(TouchEvent pSceneTouchEvent, float X,
					float Y) {
				if (pSceneTouchEvent.isActionUp()) {
					
					int lastHighscore = sceneManager.getHighscores().getLastHighScore();
					int firstHighscore = sceneManager.getHighscores().getFirstHighScore();
					//int newScore = gameEnvironment.getTimeHandler().getHighscore();

					gameEnvironment.leaveGame();

					GameOver.this.unregisterTouchArea(score);
					
					if (lastHighscore < newScore) {
						sceneManager.switchScene(AllScenes.HIGHSCORES);
						sceneManager.getHighscores().setReturnable(false);
						//input.setHighscore(newScore);
						if (firstHighscore < newScore) {
							input.setTitle("New Highscore!");
						} else {
							input.setTitle("You're in Top 10!");
						}
						input.showTextInput();
					} else {
						sceneManager.switchScene(AllScenes.MAIN_MENU);
						//input.setHighscore(0);
					}

				}
				return true;
			};
		};

		titleSprite = new Sprite(400, 380, titleITextureRegion,
				nats.getVertexBufferObjectManager());

		score = new Text(400, 250, myFont, "Your Score: asdfoefifwefoiaef",
				new TextOptions(HorizontalAlign.CENTER),
				nats.getVertexBufferObjectManager());
		score.setColor(new Color(1, 0, 0));

		/*
		 * stasisfieldText = new Text(90, 30, myFont, "x2", new TextOptions(
		 * HorizontalAlign.CENTER), nats.getVertexBufferObjectManager());
		 * stasisfieldText.setColor(new Color(1, 0, 0));
		 */

		this.attachChild(backgroundSprite);
		this.attachChild(continueSprite);
		this.registerTouchArea(continueSprite);
		this.attachChild(titleSprite);
		backgroundSprite.attachChild(score);
	}

	public void actualizeGameOver(String s, int i) {
		score.setText(s);
		this.newScore = i;
		input.setHighscore(i);
	}

	public void reset() {
		if (score.hasParent()) {
			this.detachChild(score);
		}
		//newScore = 0;
	}
	
	public InputText getInputText() {
		return this.input;
	}
	
}