package at.stefan.nats;

import java.util.ArrayList;
import org.andengine.engine.camera.BoundCamera;
import org.andengine.engine.camera.hud.HUD;
import org.andengine.engine.camera.hud.controls.AnalogOnScreenControl;
import org.andengine.engine.camera.hud.controls.AnalogOnScreenControl.IAnalogOnScreenControlListener;
import org.andengine.engine.camera.hud.controls.BaseOnScreenControl;
import org.andengine.engine.handler.timer.ITimerCallback;
import org.andengine.engine.handler.timer.TimerHandler;
import org.andengine.entity.Entity;
import org.andengine.entity.primitive.Rectangle;
import org.andengine.entity.scene.Scene;
import org.andengine.entity.sprite.Sprite;
import org.andengine.entity.text.Text;
import org.andengine.entity.text.TextOptions;
import org.andengine.extension.debugdraw.DebugRenderer;
import org.andengine.extension.physics.box2d.PhysicsConnector;
import org.andengine.extension.physics.box2d.PhysicsFactory;
import org.andengine.extension.physics.box2d.PhysicsWorld;
import org.andengine.input.touch.TouchEvent;
import org.andengine.opengl.font.Font;
import org.andengine.opengl.font.FontFactory;
import org.andengine.opengl.texture.TextureOptions;
import org.andengine.opengl.texture.atlas.bitmap.BitmapTextureAtlas;
import org.andengine.opengl.texture.atlas.bitmap.BitmapTextureAtlasTextureRegionFactory;
import org.andengine.opengl.texture.region.ITextureRegion;
import org.andengine.util.adt.align.HorizontalAlign;
import org.andengine.util.adt.color.Color;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.BodyDef.BodyType;
import com.badlogic.gdx.physics.box2d.FixtureDef;

import android.graphics.Typeface;
import android.util.Log;
import at.alex.nats.Player;
import at.stefan.nats.SceneManager.AllScenes;

public class GameEnvironment extends Scene {

	private int secs = 0;
	private int mins = 0;

	nats nats;
	BoundCamera mainCamera;
	PauseMenu pauseMenu;
	SceneManager sceneManager;

	UpgradeMenu upgradeMenu;
	Contacts contactListener;

	HUD HUDGame;
	HUD HUDPause;
	HUD HUDUpgrade;
	HUD HUDEmpty;
	Text resources;
	Text highscore;
	Font HUDGameFont;

	PhysicsWorld world;
	DebugRenderer debug;

	Rectangle leftBorder;
	Rectangle upperBorder;
	Rectangle rightBorder;
	Rectangle lowerBorder;

	Finals finals;
	MenuListener menuListener;

	BitmapTextureAtlas gameBitmapTextureAtlas;
	ITextureRegion gameITextureRegion;
	Sprite gameSprite;

	BitmapTextureAtlas pauseBitmapTextureAtlas;
	ITextureRegion pauseITextureRegion;
	Sprite pauseSprite;

	BitmapTextureAtlas updateBitmapTextureAtlas;
	ITextureRegion updateITextureRegion;
	Sprite updateSprite;

	BitmapTextureAtlas headerBitmapTextureAtlas;
	ITextureRegion headerITextureRegion;
	Sprite headerSprite;

	AnalogOnScreenControl leftAnalogOnScreenControl;
	BitmapTextureAtlas leftAnalogAuﬂenBitmapTextureAtlas;
	ITextureRegion leftAnalogAuﬂenITextureRegion;
	BitmapTextureAtlas leftAnalogInnenBitmapTextureAtlas;
	ITextureRegion leftAnalogInnenITextureRegion;

	AnalogOnScreenControl rightAnalogOnScreenControl;
	BitmapTextureAtlas rightAnalogAuﬂenBitmapTextureAtlas;
	ITextureRegion rightAnalogAuﬂenITextureRegion;
	BitmapTextureAtlas rightAnalogInnenBitmapTextureAtlas;
	ITextureRegion rightAnalogInnenITextureRegion;

	Sprite pause[] = new Sprite[2];
	Sprite upgrade[] = new Sprite[11];

	Player player;
	Sprite playerSprite;
	Body playerBody;

	Body leftBorderBody;
	Body upperBorderBody;
	Body rightBorderBody;
	Body lowerBorderBody;

	TimerHandler th;

	public GameEnvironment(nats nats, BoundCamera cam, SceneManager s, Player p) {
		this.nats = nats;
		this.mainCamera = cam;
		this.sceneManager = s;
		this.player = p;

		finals = new Finals();
		HUDGame = new HUD();
		HUDPause = new HUD();
		HUDUpgrade = new HUD();
		HUDEmpty = new HUD();
		contactListener = new Contacts(this, world, nats.getEngine());
	}

	/*public GameEnvironment(nats nats, SceneManager s, Player p) {
		this.nats = nats;
		// this.mainCamera = cam;
		this.sceneManager = s;
		this.player = p;

		finals = new Finals();
		HUDGame = new HUD();
		contactListener = new Contacts();
	}*/

	public void loadGameResources() {
		world = new PhysicsWorld(new Vector2(0.0f, 0.0f), false);
		world.setContactListener(contactListener);

		playerSprite = player.getPlayer();
		FixtureDef fd = PhysicsFactory.createFixtureDef(0.0f, 0.0f, 0.0f);

		ArrayList<Vector2> triangle = new ArrayList<Vector2>();
		final float halfWidth = playerSprite.getWidth() * 0.5f / 32;
		final float halfHeight = playerSprite.getHeight() * 0.5f / 32;
		triangle.add(new Vector2(-halfWidth, -halfHeight));
		triangle.add(new Vector2(halfWidth, -halfHeight));
		triangle.add(new Vector2(0, halfHeight));

		/*
		 * Log.i("RAD", "Vector1: " + triangle.get(0).x + "," +
		 * triangle.get(0).y + ";"); Log.i("RAD", "Vector2: " +
		 * triangle.get(1).x + "," + triangle.get(1).y + ";"); Log.i("RAD",
		 * "Vector3: " + triangle.get(2).x + "," + triangle.get(2).y + ";");
		 */

		playerBody = PhysicsFactory.createTrianglulatedBody(world,
				playerSprite, triangle, BodyType.DynamicBody, fd);

		// playerBody = PhysicsFactory.createBoxBody(world, playerSprite,
		// BodyType.DynamicBody, fd);
		playerBody.setUserData(new UserData("player", playerSprite));
		//playerBody.setUserData("player");

		mainCamera.setChaseEntity(playerSprite);
		mainCamera.setBounds(-400, -240, 1200, 720);
		mainCamera.setBoundsEnabled(true);
		// before = new Vector2(0, 1);

		BitmapTextureAtlasTextureRegionFactory.setAssetBasePath("backgrounds/");
		gameBitmapTextureAtlas = new BitmapTextureAtlas(
				nats.getTextureManager(), 1600, 960, TextureOptions.DEFAULT);
		gameITextureRegion = BitmapTextureAtlasTextureRegionFactory
				.createFromAsset(gameBitmapTextureAtlas,
						nats.getApplicationContext(), "Game.png", 0, 0);
		gameBitmapTextureAtlas.load();

		BitmapTextureAtlasTextureRegionFactory.setAssetBasePath("gfx/");
		pauseBitmapTextureAtlas = new BitmapTextureAtlas(
				nats.getTextureManager(), 70, 70, TextureOptions.DEFAULT);
		pauseITextureRegion = BitmapTextureAtlasTextureRegionFactory
				.createFromAsset(pauseBitmapTextureAtlas,
						nats.getApplicationContext(), "Pause.png", 0, 0);
		pauseBitmapTextureAtlas.load();

		updateBitmapTextureAtlas = new BitmapTextureAtlas(
				nats.getTextureManager(), 70, 70, TextureOptions.DEFAULT);
		updateITextureRegion = BitmapTextureAtlasTextureRegionFactory
				.createFromAsset(updateBitmapTextureAtlas,
						nats.getApplicationContext(), "Update.png", 0, 0);
		updateBitmapTextureAtlas.load();

		headerBitmapTextureAtlas = new BitmapTextureAtlas(
				nats.getTextureManager(), 300, 65, TextureOptions.DEFAULT);
		headerITextureRegion = BitmapTextureAtlasTextureRegionFactory
				.createFromAsset(headerBitmapTextureAtlas,
						nats.getApplicationContext(), "Header.png", 0, 0);
		headerBitmapTextureAtlas.load();

		// leftAnalogOncScreenControl.
		BitmapTextureAtlasTextureRegionFactory.setAssetBasePath("gfx/");
		leftAnalogAuﬂenBitmapTextureAtlas = new BitmapTextureAtlas(
				nats.getTextureManager(), 100, 100, TextureOptions.DEFAULT);
		leftAnalogAuﬂenITextureRegion = BitmapTextureAtlasTextureRegionFactory
				.createFromAsset(leftAnalogAuﬂenBitmapTextureAtlas,
						nats.getApplicationContext(), "JoystickAussen.png", 0,
						0);
		leftAnalogAuﬂenBitmapTextureAtlas.load();

		leftAnalogInnenBitmapTextureAtlas = new BitmapTextureAtlas(
				nats.getTextureManager(), 60, 60, TextureOptions.DEFAULT);
		leftAnalogInnenITextureRegion = BitmapTextureAtlasTextureRegionFactory
				.createFromAsset(leftAnalogInnenBitmapTextureAtlas,
						nats.getApplicationContext(), "JoystickInnen.png", 0, 0);
		leftAnalogInnenBitmapTextureAtlas.load();

		rightAnalogAuﬂenBitmapTextureAtlas = new BitmapTextureAtlas(
				nats.getTextureManager(), 100, 100, TextureOptions.DEFAULT);
		rightAnalogAuﬂenITextureRegion = BitmapTextureAtlasTextureRegionFactory
				.createFromAsset(rightAnalogAuﬂenBitmapTextureAtlas,
						nats.getApplicationContext(), "JoystickAussen.png", 0,
						0);
		rightAnalogAuﬂenBitmapTextureAtlas.load();

		rightAnalogInnenBitmapTextureAtlas = new BitmapTextureAtlas(
				nats.getTextureManager(), 60, 60, TextureOptions.DEFAULT);
		rightAnalogInnenITextureRegion = BitmapTextureAtlasTextureRegionFactory
				.createFromAsset(rightAnalogInnenBitmapTextureAtlas,
						nats.getApplicationContext(), "JoystickInnen.png", 0, 0);
		rightAnalogInnenBitmapTextureAtlas.load();

		HUDGameFont = FontFactory.create(nats.getFontManager(),
				nats.getTextureManager(), 256, 256,
				Typeface.create(Typeface.DEFAULT, Typeface.BOLD), 35,
				Color.WHITE.hashCode());
		HUDGameFont.load();

		resources = new Text(nats.getCameraWidth() / 2,
				nats.getCameraHeight() - 17, HUDGameFont, "SSSSSSSS",
				new TextOptions(HorizontalAlign.CENTER),
				nats.getVertexBufferObjectManager());

		highscore = new Text(nats.getCameraWidth() / 2,
				nats.getCameraHeight() - 46, HUDGameFont, "SSSSSSSS",
				new TextOptions(HorizontalAlign.CENTER),
				nats.getVertexBufferObjectManager());

		resources.setText(player.getRessourcesForDisplay());
		resources.setColor(new Color(0, 0, 0));
		highscore.setText("00:00");
		highscore.setColor(new Color(0, 0, 0));

		th = new TimerHandler(1f, true, new ITimerCallback() {
			String m;
			String s;

			@Override
			public void onTimePassed(TimerHandler pTimerHandler) {
				// TODO Auto-generated method stub
				secs++;
				if (secs == 60) {
					mins++;
					secs = 0;
				}
				if (secs < 10) {
					s = "0" + secs;
				} else {
					s = "" + secs;
				}

				if (mins < 10) {
					m = "0" + mins;
				} else {
					m = "" + mins;
				}
				// Log.i("NATS", "Update Time");
				highscore.setText(m + ":" + s);
			}
		});

		FixtureDef wall = PhysicsFactory.createFixtureDef(0.0f, 0.0f, 0.0f);

		leftBorder = new Rectangle(-295, 240, 10, 760,
				nats.getVertexBufferObjectManager());
		leftBorder.setColor(new Color(0.1f, 0.1f, 0.1f));
		leftBorder.setAlpha(0.7f);
		leftBorderBody = PhysicsFactory.createBoxBody(world, leftBorder,
				BodyType.StaticBody, wall);
		leftBorderBody.setUserData(new UserData("wall", leftBorder));
		//leftBorderBody.setUserData("wall");
		
		rightBorder = new Rectangle(1095, 240, 10, 760,
				nats.getVertexBufferObjectManager());
		rightBorder.setColor(new Color(0.1f, 0.1f, 0.1f));
		rightBorder.setAlpha(0.7f);
		rightBorderBody = PhysicsFactory.createBoxBody(world, rightBorder,
				BodyType.StaticBody, wall);
		rightBorderBody.setUserData(new UserData("wall", rightBorder));
		//rightBorderBody.setUserData("wall");

		upperBorder = new Rectangle(400, -135, 1400, 10,
				nats.getVertexBufferObjectManager());
		upperBorder.setColor(new Color(0.1f, 0.1f, 0.1f));
		upperBorder.setAlpha(0.7f);
		upperBorderBody = PhysicsFactory.createBoxBody(world, upperBorder,
				BodyType.StaticBody, wall);
		upperBorderBody.setUserData(new UserData("wall", upperBorder));
		//upperBorderBody.setUserData("wall");

		lowerBorder = new Rectangle(400, 615, 1400, 10,
				nats.getVertexBufferObjectManager());
		lowerBorder.setColor(new Color(0.1f, 0.1f, 0.1f));
		lowerBorder.setAlpha(0.7f);
		lowerBorderBody = PhysicsFactory.createBoxBody(world, lowerBorder,
				BodyType.StaticBody, wall);
		lowerBorderBody.setUserData(new UserData("wall", lowerBorder));
		//lowerBorderBody.setUserData("wall");
	}

	public void loadGameScene() {

		gameSprite = new Sprite(nats.getCameraWidth() / 2,
				nats.getCameraHeight() / 2, gameITextureRegion,
				nats.getVertexBufferObjectManager());

		this.attachChild(gameSprite);

		// game.setBackground(new Background(0, 0, 255));

		pauseSprite = new Sprite(nats.getCameraWidth() - 50,
				nats.getCameraHeight() - 35, pauseITextureRegion,
				nats.getVertexBufferObjectManager()) {
			@Override
			public boolean onAreaTouched(TouchEvent pSceneTouchEvent, float X,
					float Y) {
				if (pSceneTouchEvent.isActionUp()) {
					// execute action
					// Log.i("NATS", "Pause");
					// leftAnalogOnScreenControl.setVisible(false);
					GameEnvironment.this.registerPauseTouch();
					sceneManager.switchScene(AllScenes.PAUSE);
				}
				return true;
			};
		};
		updateSprite = new Sprite(50, nats.getCameraHeight() - 35,
				updateITextureRegion, nats.getVertexBufferObjectManager()) {
			@Override
			public boolean onAreaTouched(TouchEvent pSceneTouchEvent, float X,
					float Y) {
				if (pSceneTouchEvent.isActionUp()) {
					// execute action
					// Log.i("NATS", "Update");
					GameEnvironment.this.registerUpgradeTouch();
					sceneManager.switchScene(AllScenes.UPGRADE);
					upgradeMenu.actualizeResources();
				}
				return true;
			};
		};

		headerSprite = new Sprite(nats.getCameraWidth() / 2,
				nats.getCameraHeight() - 32, headerITextureRegion,
				nats.getVertexBufferObjectManager());
		headerSprite.setAlpha(0.55f);

		leftAnalogOnScreenControl = new AnalogOnScreenControl(90, 80,
				mainCamera, leftAnalogAuﬂenITextureRegion,
				leftAnalogInnenITextureRegion, 0.05f,
				nats.getVertexBufferObjectManager(),
				new IAnalogOnScreenControlListener() {

					@Override
					public void onControlChange(
							BaseOnScreenControl pBaseOnScreenControl,
							float pValueX, float pValueY) {

						// if(Math.abs(pValueX) > 0.1 && Math.abs(pValueY) >
						// 0.1) {
						int speed = 10 + 1 * player.getPermanents(finals
								.movespeed());

						player.setPosX(playerSprite.getX());
						player.setPosY(playerSprite.getY());
						float pRotationRad = (float) Math.atan2(pValueX,
								pValueY);
						playerBody.setTransform(player.getPosX() / 32,
								player.getPosY() / 32, -pRotationRad);

						playerBody.setLinearVelocity(pValueX * speed, pValueY
								* speed);
						// }
					}

					@Override
					public void onControlClick(
							AnalogOnScreenControl pAnalogOnScreenControl) {
						// TODO Auto-generated method stub
						// playerSprite.setRotation(-15.0f);

					}

				});

		rightAnalogOnScreenControl = new AnalogOnScreenControl(
				nats.getCameraWidth() - 90, 80, mainCamera,
				rightAnalogAuﬂenITextureRegion, rightAnalogInnenITextureRegion,
				3f, nats.getVertexBufferObjectManager(),
				new IAnalogOnScreenControlListener() {

					@Override
					public void onControlChange(
							BaseOnScreenControl pBaseOnScreenControl,
							float pValueX, float pValueY) {
						// TODO Auto-generated method stub
						Log.i("NATS", "Created bullet");
						Bullet b = new Bullet(player.getPosX(),
								player.getPosY(),
								new Vector2(pValueX, pValueY), world, nats);
						b.fireBullet(GameEnvironment.this);
					}

					@Override
					public void onControlClick(
							AnalogOnScreenControl pAnalogOnScreenControl) {
						// TODO Auto-generated method stub

					}
				});

		HUDGame.attachChild(pauseSprite);
		HUDGame.attachChild(updateSprite);
		HUDGame.attachChild(headerSprite);
		HUDGame.attachChild(resources);
		HUDGame.attachChild(highscore);

		HUDGame.setChildScene(leftAnalogOnScreenControl);
		leftAnalogOnScreenControl.setChildScene(rightAnalogOnScreenControl);

		this.attachChild(playerSprite);

		// PhysicsWorld
		this.registerUpdateHandler(world);

		world.registerPhysicsConnector(new PhysicsConnector(playerSprite,
				playerBody, true, true));
		world.registerPhysicsConnector(new PhysicsConnector(leftBorder,
				leftBorderBody, true, false));
		world.registerPhysicsConnector(new PhysicsConnector(rightBorder,
				rightBorderBody, true, false));
		world.registerPhysicsConnector(new PhysicsConnector(upperBorder,
				upperBorderBody, true, false));
		world.registerPhysicsConnector(new PhysicsConnector(lowerBorder,
				lowerBorderBody, true, false));

		this.attachChild(leftBorder);
		this.attachChild(upperBorder);
		this.attachChild(rightBorder);
		this.attachChild(lowerBorder);

		debug = new DebugRenderer(world, nats.getVertexBufferObjectManager());
		this.attachChild(debug);
	}

	public void removeGameScene() {
		// remove
	}

	public Scene getGameScene() {
		return this;
	}

	public void showGameHUD() {
		// HUDGame.setVisible(true);
		// HUDGame.setChildScene(leftAnalogOnScreenControl);
		// leftAnalogOnScreenControl.setChildScene(rightAnalogOnScreenControl);
		mainCamera.setHUD(HUDGame);
		leftAnalogOnScreenControl.registerTouchArea(leftAnalogOnScreenControl
				.getControlBase());
		rightAnalogOnScreenControl.registerTouchArea(rightAnalogOnScreenControl
				.getControlBase());
		HUDGame.registerTouchArea(pauseSprite);
		HUDGame.registerTouchArea(updateSprite);
	}

	public void hideGameHUD() {
		// HUDGame.getChildByIndex(GAME_LAYER).setVisible(false);
		/*
		 * SmartList<ITouchArea> a = leftAnalogOnScreenControl.getTouchAreas();
		 * for (int i = 0; i < a.size(); i++) { final ITouchArea item =
		 * a.get(i); Log.i("NATS", "HIde "+item); }
		 */
		// mainCamera.getHUD().detachSelf();

		leftAnalogOnScreenControl.clearTouchAreas();
		rightAnalogOnScreenControl.clearTouchAreas();
		HUDGame.unregisterTouchArea(pauseSprite);
		HUDGame.unregisterTouchArea(updateSprite);
	}

	public void showPauseMenu() {
		Log.i("NATS", "showPauseMenu");
		mainCamera.setHUD(HUDPause);
		this.hideGameHUD();
		// HUDGame.getChildByIndex(PAUSE_LAYER).setVisible(true);
		// this.getChildByIndex(PAUSE_LAYER).setVisible(true);
		HUDGame.unregisterTouchArea(pauseSprite);
		HUDGame.unregisterTouchArea(updateSprite);
		// game.registerTouchArea(pTouchArea)
	}

	public void hidePauseMenu() {
		Log.i("NATS", "hidePauseMenu");
		// mainCamera.getHUD().detachSelf();
		this.showGameHUD();
		this.startTimer();
		// this.getChildByIndex(PAUSE_LAYER).setVisible(false);
		// HUDGame.getChildByIndex(PAUSE_LAYER).setVisible(false);
		this.unregisterPauseTouch();
		HUDGame.registerTouchArea(pauseSprite);
		HUDGame.registerTouchArea(updateSprite);
	}

	public void leaveGame() {
		Log.i("NATS", "leaveGame");
		mainCamera.setHUD(HUDEmpty);
		player.setPosX(400f);
		player.setPosY(240f);
		playerBody.setTransform(player.getPosX() / 32, player.getPosY() / 32,
				0.0f);
		this.resetTimer();
		this.unregisterPauseTouch();
	}

	public void showUpgradeMenu() {
		Log.i("NATS", "showUpgradeMenu");
		this.hideGameHUD();
		mainCamera.setHUD(HUDUpgrade);
		// this.getChildByIndex(UPGRADE_LAYER).setVisible(true);
		// HUDGame.getChildByIndex(UPGRADE_LAYER).setVisible(true);
		HUDGame.unregisterTouchArea(pauseSprite);
		HUDGame.unregisterTouchArea(updateSprite);
	}

	public void hideUpgradeMenu() {
		Log.i("NATS", "hideUpgradeMenu");
		// mainCamera.getHUD().detachSelf();
		resources.setText(player.getRessourcesForDisplay());
		this.showGameHUD();
		// this.getChildByIndex(UPGRADE_LAYER).setVisible(false);
		// HUDGame.getChildByIndex(UPGRADE_LAYER).setVisible(false);
		this.unregisterUpgradeTouch();
		HUDGame.registerTouchArea(pauseSprite);
		HUDGame.registerTouchArea(updateSprite);
	}

	public void registerPauseTouch() {
		HUDPause.registerTouchArea(pause[0]);
		HUDPause.registerTouchArea(pause[1]);
	}

	public void unregisterPauseTouch() {
		HUDPause.unregisterTouchArea(pause[0]);
		HUDPause.unregisterTouchArea(pause[1]);
	}

	public void registerUpgradeTouch() {
		HUDUpgrade.registerTouchArea(upgrade[0]);
		HUDUpgrade.registerTouchArea(upgrade[1]);
		HUDUpgrade.registerTouchArea(upgrade[2]);
		HUDUpgrade.registerTouchArea(upgrade[3]);
		HUDUpgrade.registerTouchArea(upgrade[4]);
		HUDUpgrade.registerTouchArea(upgrade[5]);
		HUDUpgrade.registerTouchArea(upgrade[6]);
		HUDUpgrade.registerTouchArea(upgrade[7]);
		HUDUpgrade.registerTouchArea(upgrade[8]);
		HUDUpgrade.registerTouchArea(upgrade[9]);
		HUDUpgrade.registerTouchArea(upgrade[10]);
	}

	public void unregisterUpgradeTouch() {
		HUDUpgrade.unregisterTouchArea(upgrade[0]);
		HUDUpgrade.unregisterTouchArea(upgrade[1]);
		HUDUpgrade.unregisterTouchArea(upgrade[2]);
		HUDUpgrade.unregisterTouchArea(upgrade[3]);
		HUDUpgrade.unregisterTouchArea(upgrade[4]);
		HUDUpgrade.unregisterTouchArea(upgrade[5]);
		HUDUpgrade.unregisterTouchArea(upgrade[6]);
		HUDUpgrade.unregisterTouchArea(upgrade[7]);
		HUDUpgrade.unregisterTouchArea(upgrade[8]);
		HUDUpgrade.unregisterTouchArea(upgrade[9]);
		HUDUpgrade.unregisterTouchArea(upgrade[10]);
	}

	public void attachToHUDPause(Entity e) {
		// this.getChildByIndex(PAUSE_LAYER).attachChild(pauseMenu);
		HUDPause.attachChild(e);
	}

	public void attachToHUDUpgrade(Entity e) {
		// this.getChildByIndex(UPGRADE_LAYER).attachChild(upgradeMenu);
		HUDUpgrade.attachChild(e);
	}

	public void setPauseReference(Sprite con, Sprite quit) {
		this.pause[0] = con;
		this.pause[1] = quit;
	}

	public void setUpgradeReference(Sprite a, Sprite b, Sprite c, Sprite d,
			Sprite e, Sprite f, Sprite g, Sprite h, Sprite i, Sprite j,
			Sprite k, UpgradeMenu um) {
		this.upgrade[0] = a;
		this.upgrade[1] = b;
		this.upgrade[2] = c;
		this.upgrade[3] = d;
		this.upgrade[4] = e;
		this.upgrade[5] = f;
		this.upgrade[6] = g;
		this.upgrade[7] = h;
		this.upgrade[8] = i;
		this.upgrade[9] = j;
		this.upgrade[10] = k;

		this.upgradeMenu = um;
	}

	public void startTimer() {
		nats.getEngine().registerUpdateHandler(th);
	}

	public void pauseTimer() {
		nats.getEngine().unregisterUpdateHandler(th);
	}

	public void resetTimer() {
		this.mins = 0;
		this.secs = 0;
		highscore.setText("00:00");
	}

	public HUD getHUDUpgrade() {
		return HUDUpgrade;
	}

}
