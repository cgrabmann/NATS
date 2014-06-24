package at.stefan.nats;

import org.andengine.engine.camera.Camera;
import org.andengine.entity.primitive.Rectangle;
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

import android.content.Context;
import android.content.SharedPreferences;
import android.graphics.Typeface;
import android.util.Log;

public class Highscores extends Scene {

	private boolean isReturnable = true;

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
	String[][] scoreArray = new String[10][2];
	Font ScoreFont;

	SharedPreferences share;

	String scoreNames;
	String scoreTimes;
	Text scoreTextNames;
	Text scoreTextTimes;

	Rectangle center;

	public Highscores(nats nats, Camera cam) {
		this.nats = nats;
		this.mainCamera = cam;
		finals = new Finals();
		center = new Rectangle(400, 240, 2, 2,
				nats.getVertexBufferObjectManager());
		center.setVisible(false);

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

		share = nats.getSharedPreferences(finals.PREFS_HIGHSCORE,
				Context.MODE_PRIVATE);
	}

	public void loadHighscoreScene() {
		highscoresSprite = new Sprite(nats.getCameraWidth() / 2,
				nats.getCameraHeight() / 2, highscoresITextureRegion,
				nats.getVertexBufferObjectManager());

		this.attachChild(highscoresSprite);
		// highscores.setBackground(new Background(0, 0, 255));
		titleSprite = new Sprite(nats.getCameraWidth() / 2,
				nats.getCameraHeight() - titleBitmapTextureAtlas.getHeight()
						/ 2, titleITextureRegion,
				nats.getVertexBufferObjectManager());

		this.attachChild(titleSprite);

		scoreArray[0][0] = "AAAAAAAAAAA";
		scoreArray[0][1] = "11111";
		scoreArray[1][0] = "AAAAAAAAAAA";
		scoreArray[1][1] = "11111";
		scoreArray[2][0] = "AAAAAAAAAAA";
		scoreArray[2][1] = "11111";
		scoreArray[3][0] = "AAAAAAAAAAA";
		scoreArray[3][1] = "11111";
		scoreArray[4][0] = "AAAAAAAAAAA";
		scoreArray[4][1] = "11111";
		scoreArray[5][0] = "AAAAAAAAAAA";
		scoreArray[5][1] = "11111";
		scoreArray[6][0] = "AAAAAAAAAAA";
		scoreArray[6][1] = "11111";
		scoreArray[7][0] = "AAAAAAAAAAA";
		scoreArray[7][1] = "11111";
		scoreArray[8][0] = "AAAAAAAAAAA";
		scoreArray[8][1] = "11111";
		scoreArray[9][0] = "AAAAAAAAAAA";
		scoreArray[9][1] = "11111";

		scoreArray = sortScores(scoreArray);
		scoreNames = getScoreName(scoreArray);
		scoreTimes = getScoreTime(scoreArray);

		ScoreFont = FontFactory.create(nats.getFontManager(),

		nats.getTextureManager(), 256, 256,
				Typeface.create(Typeface.DEFAULT, Typeface.BOLD), 28,
				Color.WHITE.hashCode());
		ScoreFont.load();

		scoreTextNames = new Text((nats.getCameraWidth() / 4) + 80,
				nats.getCameraHeight() - 300, ScoreFont, scoreNames,
				new TextOptions(HorizontalAlign.LEFT),
				nats.getVertexBufferObjectManager());

		scoreTextTimes = new Text(nats.getCameraWidth() - nats.getCameraWidth()
				/ 4, nats.getCameraHeight() - 300, ScoreFont, scoreTimes,
				new TextOptions(HorizontalAlign.LEFT),
				nats.getVertexBufferObjectManager());

		scoreArray[0][0] = share.getString(finals.HIGHSCORE_FIRST_NAME,
				"NO SCORE");
		scoreArray[0][1] = share.getString(finals.HIGHSCORE_FIRST_TIME, "0");
		scoreArray[1][0] = share.getString(finals.HIGHSCORE_SECOND_NAME,
				"NO SCORE");
		scoreArray[1][1] = share.getString(finals.HIGHSCORE_SECOND_TIME, "0");
		scoreArray[2][0] = share.getString(finals.HIGHSCORE_THIRD_NAME,
				"NO SCORE");
		scoreArray[2][1] = share.getString(finals.HIGHSCORE_THIRD_TIME, "0");
		scoreArray[3][0] = share.getString(finals.HIGHSCORE_FOURTH_NAME,
				"NO SCORE");
		scoreArray[3][1] = share.getString(finals.HIGHSCORE_FOURTH_TIME, "0");
		scoreArray[4][0] = share.getString(finals.HIGHSCORE_FIFTH_NAME,
				"NO SCORE");
		scoreArray[4][1] = share.getString(finals.HIGHSCORE_FIFTH_TIME, "0");
		scoreArray[5][0] = share.getString(finals.HIGHSCORE_SIXTH_NAME,
				"NO SCORE");
		scoreArray[5][1] = share.getString(finals.HIGHSCORE_SIXTH_TIME, "0");
		scoreArray[6][0] = share.getString(finals.HIGHSCORE_SEVENTH_NAME,
				"NO SCORE");
		scoreArray[6][1] = share.getString(finals.HIGHSCORE_SEVENTH_TIME, "0");
		scoreArray[7][0] = share.getString(finals.HIGHSCORE_EIGHTH_NAME,
				"NO SCORE");
		scoreArray[7][1] = share.getString(finals.HIGHSCORE_EIGHTH_TIME, "0");
		scoreArray[8][0] = share.getString(finals.HIGHSCORE_NINETH_NAME,
				"NO SCORE");
		scoreArray[8][1] = share.getString(finals.HIGHSCORE_NINETH_TIME, "0");
		scoreArray[9][0] = share.getString(finals.HIGHSCORE_TENTH_NAME,
				"NO SCORE");
		scoreArray[9][1] = share.getString(finals.HIGHSCORE_TENTH_TIME, "0");

		scoreNames = getScoreName(scoreArray);
		scoreTimes = getScoreTime(scoreArray);
		scoreTextNames.setText(scoreNames);
		scoreTextTimes.setText(scoreTimes);

		this.attachChild(scoreTextNames);
		this.attachChild(scoreTextTimes);

		this.attachChild(center);
	}

	public void removeHighscoreScene() {

		// remove
	}

	public Scene getHighscoreScene() {
		return this;
	}

	/*
	 * public void centerCamera() { mainCamera.setChaseEntity(center); }
	 */

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

	public void setNewScore(String s, int i) {
		Log.i("NATS", "integer: " + i);
		scoreArray[0][0] = s;
		scoreArray[0][1] = Integer.toString(i);
		Log.i("NATS", "Integer after toString: " + scoreArray[0][1]);
		sortScores(scoreArray);
		scoreTextNames.setText(getScoreName(scoreArray));
		scoreTextTimes.setText(getScoreTime(scoreArray));

		SharedPreferences.Editor editor = share.edit();
		
		editor.putString(finals.HIGHSCORE_FIRST_NAME, scoreArray[0][0]);
		editor.putString(finals.HIGHSCORE_FIRST_TIME, scoreArray[0][1]);
		
		editor.putString(finals.HIGHSCORE_SECOND_NAME, scoreArray[1][0]);
		editor.putString(finals.HIGHSCORE_SECOND_TIME, scoreArray[1][1]);
		
		editor.putString(finals.HIGHSCORE_THIRD_NAME, scoreArray[2][0]);
		editor.putString(finals.HIGHSCORE_THIRD_TIME, scoreArray[2][1]);
		
		editor.putString(finals.HIGHSCORE_FOURTH_NAME, scoreArray[3][0]);
		editor.putString(finals.HIGHSCORE_FOURTH_TIME, scoreArray[3][1]);
		
		editor.putString(finals.HIGHSCORE_FIFTH_NAME, scoreArray[4][0]);
		editor.putString(finals.HIGHSCORE_FIFTH_TIME, scoreArray[4][1]);
		
		editor.putString(finals.HIGHSCORE_SIXTH_NAME, scoreArray[5][0]);
		editor.putString(finals.HIGHSCORE_SIXTH_TIME, scoreArray[5][1]);
		
		editor.putString(finals.HIGHSCORE_SEVENTH_NAME, scoreArray[6][0]);
		editor.putString(finals.HIGHSCORE_SEVENTH_TIME, scoreArray[6][1]);
		
		editor.putString(finals.HIGHSCORE_EIGHTH_NAME, scoreArray[7][0]);
		editor.putString(finals.HIGHSCORE_EIGHTH_TIME, scoreArray[7][1]);
		
		editor.putString(finals.HIGHSCORE_NINETH_NAME, scoreArray[8][0]);
		editor.putString(finals.HIGHSCORE_NINETH_TIME, scoreArray[8][1]);
		
		editor.putString(finals.HIGHSCORE_TENTH_NAME, scoreArray[9][0]);
		editor.putString(finals.HIGHSCORE_TENTH_TIME, scoreArray[9][1]);

		editor.commit();
	}

	public int getLastHighScore() {
		scoreArray = sortScores(scoreArray);
		return Integer.parseInt(scoreArray[0][1]);
	}

	public int getFirstHighScore() {
		scoreArray = sortScores(scoreArray);
		return Integer.parseInt(scoreArray[9][1]);
	}

	public void setReturnable(boolean b) {
		this.isReturnable = b;
	}

	public boolean isReturnable() {
		return this.isReturnable;
	}

}
