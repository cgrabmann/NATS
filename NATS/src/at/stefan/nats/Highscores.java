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
//import org.andengine.opengl.texture.atlas.bitmap.BuildableBitmapTextureAtlas;
import org.andengine.opengl.texture.region.ITextureRegion;
//import org.andengine.opengl.texture.region.ITiledTextureRegion;
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
				nats.getCameraHeight() - titleBitmapTextureAtlas.getHeight()/ 2, titleITextureRegion,
				nats.getVertexBufferObjectManager());

		highscores.attachChild(titleSprite);

		scoreArray [0][0] = "A";
        scoreArray [0][1] = "20";
        scoreArray [1][0] = "A";
        scoreArray [1][1] = "30";
        scoreArray [2][0] = "ft";
        scoreArray [2][1] = "121";
        scoreArray [3][0] = "A";
        scoreArray [3][1] = "0";
        scoreArray [4][0] = "HI";
        scoreArray [4][1] = "120";
        scoreArray [5][0] = "DEFAULT";
        scoreArray [5][1] = "0";
        scoreArray [6][0] = "BC";
        scoreArray [6][1] = "15";
        scoreArray [7][0] = "WWWWWWWWWW";
        scoreArray [7][1] = "3599";
        scoreArray [8][0] = "Q";
        scoreArray [8][1] = "5";
        scoreArray [9][0] = "WMMMMMMMMM";
        scoreArray [9][1] = "671";

        scoreArray = sortScores(scoreArray);
        String scoreNames = getScoreName(scoreArray);
		String scoreTimes = getScoreTime(scoreArray);
        
        ScoreFont = FontFactory.create(nats.getFontManager(),

				nats.getTextureManager(), 256, 256,
				Typeface.create(Typeface.DEFAULT, Typeface.BOLD), 28,
				Color.WHITE.hashCode());
		ScoreFont.load();
		
		Text scoreTextNames = new Text((nats.getCameraWidth() / 4) + 80,
				nats.getCameraHeight() - 300, ScoreFont, scoreNames,
				new TextOptions(HorizontalAlign.LEFT),
				nats.getVertexBufferObjectManager());

		Text scoreTextTimes = new Text(nats.getCameraWidth() - nats.getCameraWidth() / 4,
				nats.getCameraHeight() - 300, ScoreFont, scoreTimes,
				new TextOptions(HorizontalAlign.LEFT),
				nats.getVertexBufferObjectManager());
        
		highscores.attachChild(scoreTextNames);
		highscores.attachChild(scoreTextTimes);
		
		//Input field
		/*
		BitmapTextureAtlasTextureRegionFactory.setAssetBasePath("titles/");
		BuildableBitmapTextureAtlas mBitmapTextureAtlas = new BuildableBitmapTextureAtlas(nats.getTextureManager(), 512, 256, TextureOptions.NEAREST);
		ITiledTextureRegion myTiledTextureRegion = BitmapTextureAtlasTextureRegionFactory.createTiledFromAsset(mBitmapTextureAtlas, nats, "HighscoresTitle.png", 1, 1);
		
		mBitmapTextureAtlas.load();
		
		InputText name = new InputText(400, 240, "Name", "Enter Name", myTiledTextureRegion, ScoreFont, 17, 19, nats.getVertexBufferObjectManager(), nats);
		highscores.attachChild(name);
		highscores.registerTouchArea(name);
		*/
		
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
		for (int i = 0; i < 10; i++) {
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

	public String toTime(int seconds) {
		int sec;
		int min;
		sec = seconds % 60;
		min = seconds / 60;
		return (min < 10 ? ("0" + Integer.toString(min) + " : ") : Integer
				.toString(min) + " : ")
				+ (sec < 10 ? ("0" + Integer.toString(sec)) : Integer
						.toString(sec));
	}

	public String getScoreName(String[][] scores) {
		String scoreString = "";
		int j = 1;
		for (int i = 10; i > 0; i--) {
			if (i > 1)
				scoreString += "  ";
			scoreString += j + ". " + scores[i - 1][0] + "\n";
			j++;
		}
		return scoreString;
	}

	public String getScoreTime(String[][] scores) {
		String scoreString = "";
		for (int i = 10; i > 0; i--) {
			scoreString += toTime(Integer.parseInt(scores[i - 1][1])) + "\n";
		}
		return scoreString;
	}
	
	public int getLastHighScore(){
		scoreArray = sortScores(scoreArray);
		return Integer.parseInt(scoreArray[0][1]);
	}

}
