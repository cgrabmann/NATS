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
import org.andengine.opengl.texture.atlas.bitmap.BuildableBitmapTextureAtlas;
import org.andengine.opengl.texture.region.ITextureRegion;
import org.andengine.opengl.texture.region.ITiledTextureRegion;
import org.andengine.util.adt.align.HorizontalAlign;
import org.andengine.util.adt.color.Color;

import android.graphics.Typeface;
import at.stefan.nats.SceneManager.AllScenes;

public class GameOver extends Scene{
	private boolean gameover = false;
	private int score;
	
	nats nats;
	Camera mainCamera;
	GameEnvironment gameEnvironment;
	SceneManager sceneManager;

	BitmapTextureAtlas backgroundBitmapTextureAtlas;
	ITextureRegion backgroundITextureRegion;
	Sprite backgroundSprite;
	
	BuildableBitmapTextureAtlas mBitmapTextureAtlas;
	ITiledTextureRegion myTiledTextureRegion;
	
	BitmapTextureAtlas endGameBitmapTextureAtlas;
	ITextureRegion endGameITextureRegion;
	Sprite endGameSprite;
	
	public GameOver(nats nats, BoundCamera cam, GameEnvironment ge, SceneManager s) {
		this.nats = nats;
		this.mainCamera = cam;
		this.gameEnvironment = ge;
		this.sceneManager = s;
	}
	
	public void loadGameOverResources() {
		BitmapTextureAtlasTextureRegionFactory.setAssetBasePath("gfx/");

		backgroundBitmapTextureAtlas = new BitmapTextureAtlas(
				nats.getTextureManager(), 800, 480, TextureOptions.DEFAULT);
		backgroundITextureRegion = BitmapTextureAtlasTextureRegionFactory
				.createFromAsset(backgroundBitmapTextureAtlas,
						nats.getApplicationContext(), "Grau.png", 0, 0);
		backgroundBitmapTextureAtlas.load();
		
		BitmapTextureAtlasTextureRegionFactory.setAssetBasePath("titles/");
		mBitmapTextureAtlas = new BuildableBitmapTextureAtlas(nats.getTextureManager(), 512, 256, TextureOptions.NEAREST);
		myTiledTextureRegion = BitmapTextureAtlasTextureRegionFactory.createTiledFromAsset(mBitmapTextureAtlas, nats, "HighscoresTitle.png", 1, 1);
		
		mBitmapTextureAtlas.load();
		
		endGameBitmapTextureAtlas = new BitmapTextureAtlas(
				nats.getTextureManager(), 310, 150, TextureOptions.DEFAULT);
		endGameITextureRegion = BitmapTextureAtlasTextureRegionFactory
				.createFromAsset(endGameBitmapTextureAtlas,
						nats.getApplicationContext(), "Quit.png", 0, 0);
		endGameBitmapTextureAtlas.load();
		
	}
	
	public void loadGameOverScene() {
		backgroundSprite = new Sprite(400,
				240, backgroundITextureRegion,
				nats.getVertexBufferObjectManager());
		backgroundSprite.setAlpha(0.4f);
	}
	
	public void showGameOver(){
		
		Font myFont = FontFactory.create(nats.getFontManager(),

				nats.getTextureManager(), 256, 256,
				Typeface.create(Typeface.DEFAULT, Typeface.BOLD), 28,
				Color.WHITE.hashCode());
		myFont.load();
		
		if(isNewHighScore()){
			
			String title = "New high score:  " + Integer.toString(score);
			
			InputText name = new InputText(400, 240, title, "Enter Name", myTiledTextureRegion, myFont, 17, 19, nats.getVertexBufferObjectManager(), nats);
			this.attachChild(name);
			this.registerTouchArea(name);
		}
		else {
			
			String gameOverTitle = "Game Over. Your score is:  " + Integer.toString(score); 
			
			Text gameOverText = new Text(400,
				240, myFont, gameOverTitle,
					new TextOptions(HorizontalAlign.LEFT),
					nats.getVertexBufferObjectManager());
			this.attachChild(gameOverText);
			
			endGameSprite = new Sprite(400, 100, endGameITextureRegion,
					nats.getVertexBufferObjectManager()) {
				@Override
				public boolean onAreaTouched(TouchEvent pSceneTouchEvent, float X,
						float Y) {
					if (pSceneTouchEvent.isActionUp()) {
						gameEnvironment.leaveGame();
						sceneManager.switchScene(AllScenes.MAIN_MENU);
					}
					return true;
				};
			};
			//show name, high score and a button to return to main menu.
		}
		
		gameEnvironment.attachToHUDPause(backgroundSprite);
		gameEnvironment.attachToHUDPause(endGameSprite);
		//gameEnvironment.setPauseReference(continueSprite, quitSprite);
		
	}
	
	public boolean isNewHighScore(){
		return false;
	}
	
	
	
		// this.setBackground(new Background(0.5f, 0.5f, 0.5f, 0.5f));
		
}
