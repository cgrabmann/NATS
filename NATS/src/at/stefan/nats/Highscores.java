package at.stefan.nats;

import org.andengine.engine.camera.Camera;
import org.andengine.entity.scene.Scene;
import org.andengine.entity.sprite.Sprite;
import org.andengine.entity.text.Text;
import org.andengine.entity.text.TextOptions;
import org.andengine.opengl.font.Font;
import org.andengine.opengl.font.FontFactory;
import org.andengine.opengl.texture.TextureOptions;
import org.andengine.opengl.texture.atlas.bitmap.BitmapTextureAtlas;
import org.andengine.opengl.texture.atlas.bitmap.BitmapTextureAtlasTextureRegionFactory;
import org.andengine.opengl.texture.region.ITextureRegion;
import org.andengine.util.adt.align.HorizontalAlign;
import org.andengine.util.adt.color.Color;

import android.graphics.Typeface;

public class Highscores {

	nats nats;
	Camera mainCamera;

	Finals finals;
	MenuListener menuListener;

	BitmapTextureAtlas highscoresBitmapTextureAtlas;
	ITextureRegion highscoresITextureRegion;
	Sprite highscoresSprite;

	BitmapTextureAtlas titleBitmapTextureAtlas;
	ITextureRegion titleITextureRegion;
	Sprite titleSprite;

	Scene highscores;
	
	String[][] scoreArray = new String[10][2];
	Font ScoreFont;

	public Highscores(nats nats, Camera cam) {
		this.nats = nats;
		this.mainCamera = cam;

		finals = new Finals();
	}

	public void loadHighscoreResources() {
		BitmapTextureAtlasTextureRegionFactory.setAssetBasePath("backgrounds/");
		highscoresBitmapTextureAtlas = new BitmapTextureAtlas(
				nats.getTextureManager(), 800, 480, TextureOptions.DEFAULT);
		highscoresITextureRegion = BitmapTextureAtlasTextureRegionFactory
				.createFromAsset(highscoresBitmapTextureAtlas,
						nats.getApplicationContext(), "MainMenu.jpg", 0, 0);
		highscoresBitmapTextureAtlas.load();

		BitmapTextureAtlasTextureRegionFactory.setAssetBasePath("titles/");
		titleBitmapTextureAtlas = new BitmapTextureAtlas(
				nats.getTextureManager(), 560, 120, TextureOptions.DEFAULT);
		titleITextureRegion = BitmapTextureAtlasTextureRegionFactory
				.createFromAsset(titleBitmapTextureAtlas,
						nats.getApplicationContext(), "HighscoresTitle.png", 0,
						0);
		titleBitmapTextureAtlas.load();
	}

	public void loadHighscoreScene() {
		highscores = new Scene();
		highscoresSprite = new Sprite(nats.getCameraWidth() / 2,
				nats.getCameraHeight() / 2, highscoresITextureRegion,
				nats.getVertexBufferObjectManager());
		highscores.attachChild(highscoresSprite);
		//highscores.setBackground(new Background(0, 0, 255));

		titleSprite = new Sprite(nats.getCameraWidth() / 2,
				nats.getCameraHeight() - titleBitmapTextureAtlas.getHeight()
						/ 2, titleITextureRegion,
				nats.getVertexBufferObjectManager());
		highscores.attachChild(titleSprite);
		
		
		scoreArray = sortScores(scoreArray);
		String scores = getScores(scoreArray);
		
		ScoreFont = FontFactory.create(nats.getFontManager(),
				nats.getTextureManager(), 256, 256,
				Typeface.create(Typeface.DEFAULT, Typeface.BOLD), 35,
				Color.WHITE.hashCode());
		ScoreFont.load();

		Text scoreText = new Text(nats.getCameraWidth() / 2,
				nats.getCameraHeight() - 17, ScoreFont, scores,
				new TextOptions(HorizontalAlign.CENTER),
				nats.getVertexBufferObjectManager());
		highscores.attachChild(scoreText);
	}

	public void removeHighscoreScene() {
		// remove
	}

	public Scene getHighscoreScene() {
		return highscores;
	}
	
	public String[][] sortScores(String[][] scoreList) {
		int temp;
		String tempS = "";
		
		for (int i = 0; i < 10; i ++) {
			temp = Integer.parseInt(scoreList[i][1]);
			tempS = scoreList[i][0];
			int j = i;
			while (j > 0 && Integer.parseInt(scoreList[j - 1][1]) > temp) {
				scoreList[j][1] = scoreList[j - 1][1];
				scoreList[j][0] = scoreList[j - 1][0];
				j--;
			}
			scoreList[j][1] = Integer.toString(temp);
			scoreList[j][0] = tempS;
		}
		
		return scoreList;
	}
	
	public String getScores(String[][] scores) {	// provides highscores for Highscores.java
		// converting highscores into better (data)format
		String scoreString = "";
		
		for (int i = 0; i < 10; i++) {
			scoreString += i+1 + " " + scores[i][0] + " " + scores[i][1] + "\n"; 
		}
		return scoreString;
	}
}
