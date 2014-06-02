package at.stefan.nats;

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
import org.andengine.entity.sprite.batch.SpriteGroup;
import org.andengine.entity.text.Text;
import org.andengine.entity.text.TextOptions;
import org.andengine.extension.physics.box2d.PhysicsConnector;
import org.andengine.extension.physics.box2d.PhysicsFactory;
import org.andengine.input.touch.TouchEvent;
import org.andengine.opengl.font.Font;
import org.andengine.opengl.font.FontFactory;
import org.andengine.opengl.texture.TextureOptions;
import org.andengine.opengl.texture.atlas.bitmap.BitmapTextureAtlas;
import org.andengine.opengl.texture.atlas.bitmap.BitmapTextureAtlasTextureRegionFactory;
import org.andengine.opengl.texture.atlas.bitmap.BuildableBitmapTextureAtlas;
import org.andengine.opengl.texture.atlas.bitmap.source.IBitmapTextureAtlasSource;
import org.andengine.opengl.texture.atlas.buildable.builder.BlackPawnTextureAtlasBuilder;
import org.andengine.opengl.texture.atlas.buildable.builder.ITextureAtlasBuilder.TextureAtlasBuilderException;
import org.andengine.opengl.texture.region.ITextureRegion;
import org.andengine.util.adt.align.HorizontalAlign;
import org.andengine.util.adt.color.Color;

import android.graphics.Typeface;
import android.util.Log;
import at.alex.nats.Player;
import at.stefan.nats.SceneManager.AllScenes;

import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.BodyDef.BodyType;
import com.badlogic.gdx.physics.box2d.FixtureDef;

public class GameEnvironment extends Scene {

	private int secs = 0;
	private int mins = 0;
	private int counterShot = 0;
	private int counterShield = 0;
	private int counterGunner = 0;

	Sprite items[] = new Sprite[2];

	nats nats;
	BoundCamera mainCamera;
	PauseMenu pauseMenu;
	SceneManager sceneManager;

	UpgradeMenu upgradeMenu;
	Contacts contactListener;
	Usables usables;

	HUD HUDGame;
	HUD HUDPause;
	HUD HUDUpgrade;
	HUD HUDEmpty;
	Text resources;
	Text highscore;
	Font HUDGameFont;

	MaxStepPhysicsWorld world;

	Rectangle leftBorder;
	Rectangle upperBorder;
	Rectangle rightBorder;
	Rectangle lowerBorder;

	Rectangle usable1;
	Rectangle usable2;

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
	// Body playerBody;

	Sprite playerBaseSprite;
	Body playerBaseBody;

	Body leftBorderBody;
	Body upperBorderBody;
	Body rightBorderBody;
	Body lowerBorderBody;

	TimerHandler th;
	TimerHandler cameraTimerHandler;

	BulletPool bulletPool;
	// SpriteGroup bulletSpriteGroup;

	BitmapTextureAtlas smallStasisfieldBitmapTextureAtlas;
	ITextureRegion smallStasisfieldITextureRegion;
	Sprite smallStasisfieldSprite;

	BitmapTextureAtlas smallTurboBitmapTextureAtlas;
	ITextureRegion smallTurboITextureRegion;
	Sprite smallTurboSprite;

	BitmapTextureAtlas smallDeadlytrailBitmapTextureAtlas;
	ITextureRegion smallDeadlytrailITextureRegion;
	Sprite smallDeadlytrailSprite;

	BitmapTextureAtlas smallBombBitmapTextureAtlas;
	ITextureRegion smallBombITextureRegion;
	Sprite smallBombSprite;

	BuildableBitmapTextureAtlas fireBallBitmapTextureAtlas;
	ITextureRegion fireBallITextureRegion;
	SpriteGroup fireBallSpriteGroup;

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
		contactListener = new Contacts(this, world, nats.getEngine(), player);

		this.setTouchAreaBindingOnActionDownEnabled(true);
	}

	public void loadGameResources() {
		// world = new PhysicsWorld(new Vector2(0.0f, 0.0f), true);

		world = new MaxStepPhysicsWorld(30, new Vector2(0f, 0f), false, 5, 3);
		world.setContactListener(contactListener);

		// playerSprite = player.getPlayer();
		playerBaseSprite = player.getPlayerBase();

		FixtureDef fd = PhysicsFactory.createFixtureDef(25.0f, 5.0f, 0.0f);

		/*
		 * ArrayList<Vector2> triangle = new ArrayList<Vector2>(); final float
		 * halfWidth = playerSprite.getWidth() * 0.5f / 32; final float
		 * halfHeight = playerSprite.getHeight() * 0.5f / 32; triangle.add(new
		 * Vector2(-halfWidth, -halfHeight)); triangle.add(new
		 * Vector2(halfWidth, -halfHeight)); triangle.add(new Vector2(0,
		 * halfHeight));
		 */

		playerBaseBody = PhysicsFactory.createCircleBody(world,
				playerBaseSprite, BodyType.DynamicBody, fd);
		// playerBody = PhysicsFactory.createTrianglulatedBody(world,
		// playerSprite, triangle, BodyType.KinematicBody, fd);

		playerBaseBody.setUserData(new UserData("player", this.player));
		// playerBody.setUserData("player");

		mainCamera.setChaseEntity(playerBaseSprite);
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

				if (!player.getShield()) {
					if (counterShield >= player.getTimeToShield()) {
						player.activateShield();
						playerBaseSprite.setAlpha(1f);
					} else {
						counterShield++;
					}
				}

				if (counterGunner >= player.getTimeToGunner()) {
					Log.i("NATS", "Gunner");
					counterGunner = 0;
					// Hier den Gegner ausfindig machen, sobald sie
					// implementiert sind
					Bullet b = bulletPool.onAllocateGunner();
					b.fireBullet(new Vector2(0f, 1f));
				} else {
					counterGunner++;
				}

			}
		});

		FixtureDef wall = PhysicsFactory.createFixtureDef(10.0f, 0.0f, 0.0f);

		leftBorder = new Rectangle(-295, 240, 10, 760,
				nats.getVertexBufferObjectManager());
		leftBorder.setColor(new Color(0.1f, 0.1f, 0.1f));
		leftBorder.setAlpha(0.7f);
		// leftBorderBody = PhysicsFactory.createLineBody(world, -290, -140,
		// -290,
		// 620, wall);
		leftBorderBody = PhysicsFactory.createBoxBody(world, leftBorder,
				BodyType.StaticBody, wall);
		leftBorderBody.setUserData(new UserData("wallEW", leftBorder));
		// leftBorderBody.setUserData("wall");

		rightBorder = new Rectangle(1095, 240, 10, 760,
				nats.getVertexBufferObjectManager());
		rightBorder.setColor(new Color(0.1f, 0.1f, 0.1f));
		rightBorder.setAlpha(0.7f);
		// rightBorderBody = PhysicsFactory.createLineBody(world, 1090, -140,
		// 1090, 620, wall);
		rightBorderBody = PhysicsFactory.createBoxBody(world, rightBorder,
				BodyType.StaticBody, wall);
		rightBorderBody.setUserData(new UserData("wallEW", rightBorder));
		// rightBorderBody.setUserData("wall");

		upperBorder = new Rectangle(400, -135, 1400, 10,
				nats.getVertexBufferObjectManager());
		upperBorder.setColor(new Color(0.1f, 0.1f, 0.1f));
		upperBorder.setAlpha(0.7f);
		// upperBorderBody = PhysicsFactory.createLineBody(world, 100, -130,
		// 1100,
		// -130, wall);
		upperBorderBody = PhysicsFactory.createBoxBody(world, upperBorder,
				BodyType.StaticBody, wall);
		upperBorderBody.setUserData(new UserData("wallNS", upperBorder));
		// upperBorderBody.setUserData("wall");

		lowerBorder = new Rectangle(400, 615, 1400, 10,
				nats.getVertexBufferObjectManager());
		lowerBorder.setColor(new Color(0.1f, 0.1f, 0.1f));
		lowerBorder.setAlpha(0.7f);
		// lowerBorderBody = PhysicsFactory.createLineBody(world, 100, 610,
		// 1100,
		// 610, wall);
		lowerBorderBody = PhysicsFactory.createBoxBody(world, lowerBorder,
				BodyType.StaticBody, wall);
		lowerBorderBody.setUserData(new UserData("wallNS", lowerBorder));
		// lowerBorderBody.setUserData("wall");

		smallStasisfieldBitmapTextureAtlas = new BitmapTextureAtlas(
				nats.getTextureManager(), 28, 28, TextureOptions.DEFAULT);
		smallStasisfieldITextureRegion = BitmapTextureAtlasTextureRegionFactory
				.createFromAsset(smallStasisfieldBitmapTextureAtlas,
						nats.getApplicationContext(), "Stasisfield_Klein.png",
						0, 0);
		smallStasisfieldBitmapTextureAtlas.load();

		smallTurboBitmapTextureAtlas = new BitmapTextureAtlas(
				nats.getTextureManager(), 28, 28, TextureOptions.DEFAULT);
		smallTurboITextureRegion = BitmapTextureAtlasTextureRegionFactory
				.createFromAsset(smallTurboBitmapTextureAtlas,
						nats.getApplicationContext(), "Turbo_Klein.png", 0, 0);
		smallTurboBitmapTextureAtlas.load();

		smallDeadlytrailBitmapTextureAtlas = new BitmapTextureAtlas(
				nats.getTextureManager(), 28, 28, TextureOptions.DEFAULT);
		smallDeadlytrailITextureRegion = BitmapTextureAtlasTextureRegionFactory
				.createFromAsset(smallDeadlytrailBitmapTextureAtlas,
						nats.getApplicationContext(), "Deadlytrail_Klein.png",
						0, 0);
		smallDeadlytrailBitmapTextureAtlas.load();

		smallBombBitmapTextureAtlas = new BitmapTextureAtlas(
				nats.getTextureManager(), 28, 28, TextureOptions.DEFAULT);
		smallBombITextureRegion = BitmapTextureAtlasTextureRegionFactory
				.createFromAsset(smallBombBitmapTextureAtlas,
						nats.getApplicationContext(), "Bomb_Klein.png", 0, 0);
		smallBombBitmapTextureAtlas.load();

		// bulletSpriteGroup = new SpriteGroup(0, 0, pTexture, 50,
		// nats.getVertexBufferObjectManager());

		BitmapTextureAtlasTextureRegionFactory.setAssetBasePath("gfx/");
		fireBallBitmapTextureAtlas = new BuildableBitmapTextureAtlas(
				nats.getTextureManager(), 50, 50, TextureOptions.BILINEAR);
		fireBallITextureRegion = BitmapTextureAtlasTextureRegionFactory
				.createFromAsset(fireBallBitmapTextureAtlas,
						nats.getApplicationContext(), "FireBall.png");

		try {
			fireBallBitmapTextureAtlas
					.build(new BlackPawnTextureAtlasBuilder<IBitmapTextureAtlasSource, BitmapTextureAtlas>(
							0, 0, 0));
			fireBallBitmapTextureAtlas.load();
		} catch (TextureAtlasBuilderException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			Log.i("NATS", "fireballbitmap not working");
		}

		fireBallSpriteGroup = new SpriteGroup(0, 0, fireBallBitmapTextureAtlas,
				42, nats.getVertexBufferObjectManager());

		bulletPool = new BulletPool(player, this, world, nats);

		usables = new Usables(nats, this, world, player, bulletPool);

		//debug = new DebugRenderer(world, nats.getVertexBufferObjectManager());
		//this.attachChild(debug);
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
				if (pSceneTouchEvent.isActionDown()) {
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
				if (pSceneTouchEvent.isActionDown()) {
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

		usable1 = new Rectangle(nats.getCameraWidth() - 200, 80, 30, 30,
				nats.getVertexBufferObjectManager());
		usable1.setColor(new Color(0.0f, (float) 128 / 255, (float) 128 / 255,
				0.5f));
		usable2 = new Rectangle(nats.getCameraWidth() - 90, 180, 30, 30,
				nats.getVertexBufferObjectManager());
		usable2.setColor(new Color(0.0f, (float) 128 / 255, (float) 128 / 255,
				0.5f));

		leftAnalogOnScreenControl = new AnalogOnScreenControl(90, 80,
				mainCamera, leftAnalogAuﬂenITextureRegion,
				leftAnalogInnenITextureRegion, 0.05f,
				nats.getVertexBufferObjectManager(),
				new IAnalogOnScreenControlListener() {

					@Override
					public void onControlChange(
							BaseOnScreenControl pBaseOnScreenControl,
							float pValueX, float pValueY) {

						if (Math.abs(pValueX) < 0.1f
								&& Math.abs(pValueY) < 0.1f) {
							player.getPlayerFlame().setVisible(false);
							playerBaseBody.setLinearVelocity(0, 0);
							// Log.i("NATS",
							// "Radians: "+playerBaseBody.getAngle());
							// Log.i("NATS",
							// "Angle: "+(playerBaseBody.getAngle()*180/Math.PI));
						} else {
							int speed = 10 + 1 * player.getSpeed();
							player.getPlayerFlame().setVisible(true);
							playerBaseBody.setLinearVelocity(pValueX * speed,
									pValueY * speed);

							player.setPosX(playerBaseSprite.getX());
							player.setPosY(playerBaseSprite.getY());

							float pRotationRad = (float) Math.atan2(pValueX,
									pValueY);
							playerBaseBody.setTransform(player.getPosX() / 32,
									player.getPosY() / 32, -pRotationRad);
						}

						// int speed = 10 + 1 * player.getPermanents(finals
						// .movespeed());

						// }else {

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
				0.05f, nats.getVertexBufferObjectManager(),
				new IAnalogOnScreenControlListener() {

					@Override
					public void onControlChange(
							BaseOnScreenControl pBaseOnScreenControl,
							float pValueX, float pValueY) {
						// TODO Auto-generated method stub

						if (player.isShootingAllowed()
								&& (Math.abs(pValueX) > 0.5f || Math
										.abs(pValueY) > 0.5f)) {
							// Log.i("NATS", "Created bullet");
							if (counterShot >= player.getShotFrequence()) {
								double rand = Math.random();
								if (player.getShotSpreading() == 0) {
									Bullet b = bulletPool.onAllocateBullet();
									b.fireBullet(new Vector2(pValueX, pValueY));
								} else if (player.getShotSpreading() == 1) {
									if (rand < 0.25) {
										Bullet b = bulletPool
												.onAllocateBullet();
										b.fireBullet(new Vector2(
												pValueX - 0.015f,
												pValueY - 0.015f));
										Bullet a = bulletPool
												.onAllocateBullet();
										a.fireBullet(new Vector2(
												pValueX + 0.015f,
												pValueY + 0.015f));
									} else {
										Bullet b = bulletPool
												.onAllocateBullet();
										b.fireBullet(new Vector2(pValueX,
												pValueY));
									}
								} else if (player.getShotSpreading() == 2) {
									if (rand < 0.5) {
										Bullet b = bulletPool
												.onAllocateBullet();
										b.fireBullet(new Vector2(
												pValueX - 0.015f,
												pValueY - 0.015f));
										Bullet a = bulletPool
												.onAllocateBullet();
										a.fireBullet(new Vector2(
												pValueX + 0.015f,
												pValueY + 0.015f));
									} else {
										Bullet b = bulletPool
												.onAllocateBullet();
										b.fireBullet(new Vector2(pValueX,
												pValueY));
									}
								} else if (player.getShotSpreading() == 3) {
									if (rand < 0.75) {
										Bullet b = bulletPool
												.onAllocateBullet();
										b.fireBullet(new Vector2(
												pValueX - 0.015f,
												pValueY - 0.015f));
										Bullet a = bulletPool
												.onAllocateBullet();
										a.fireBullet(new Vector2(
												pValueX + 0.015f,
												pValueY + 0.015f));
									} else {
										Bullet b = bulletPool
												.onAllocateBullet();
										b.fireBullet(new Vector2(pValueX,
												pValueY));
									}
								} else if (player.getShotSpreading() == 4) {
									Bullet b = bulletPool.onAllocateBullet();
									b.fireBullet(new Vector2(pValueX - 0.015f,
											pValueY - 0.015f));
									Bullet a = bulletPool.onAllocateBullet();
									a.fireBullet(new Vector2(pValueX + 0.015f,
											pValueY + 0.015f));
								} else {
									if (rand < 0.25) {
										Bullet c = bulletPool
												.onAllocateBullet();
										c.fireBullet(new Vector2(
												pValueX - 0.025f,
												pValueY - 0.025f));
										Bullet b = bulletPool
												.onAllocateBullet();
										b.fireBullet(new Vector2(pValueX,
												pValueY));
										Bullet a = bulletPool
												.onAllocateBullet();
										a.fireBullet(new Vector2(
												pValueX + 0.025f,
												pValueY + 0.025f));
									} else {
										Bullet b = bulletPool
												.onAllocateBullet();
										b.fireBullet(new Vector2(
												pValueX - 0.015f,
												pValueY - 0.015f));
										Bullet a = bulletPool
												.onAllocateBullet();
										a.fireBullet(new Vector2(
												pValueX + 0.015f,
												pValueY + 0.015f));
									}
								}

								counterShot = 0;
							} else {
								counterShot++;
							}

						} else {

						}

					}

					@Override
					public void onControlClick(
							AnalogOnScreenControl pAnalogOnScreenControl) {
						// TODO Auto-generated method stub

					}
				});
		// rightAnalogOnScreenControl

		smallStasisfieldSprite = new Sprite(nats.getCameraWidth() - 200, 80,
				smallStasisfieldITextureRegion,
				nats.getVertexBufferObjectManager()) {
			@Override
			public boolean onAreaTouched(TouchEvent pSceneTouchEvent, float X,
					float Y) {
				if (pSceneTouchEvent.isActionDown()) {
					Log.i("Usable", "smallStasis");
					if (!usables.stasisFieldIsActivated()) {
						usables.stasisfield();
					}
				}
				return true;
			};
		};
		smallTurboSprite = new Sprite(nats.getCameraWidth() - 90, 180,
				smallTurboITextureRegion, nats.getVertexBufferObjectManager()) {
			@Override
			public boolean onAreaTouched(TouchEvent pSceneTouchEvent, float X,
					float Y) {
				if (pSceneTouchEvent.isActionDown()) {
					Log.i("Usable", "smallTurbo");
					if (!usables.turboIsActivated()) {
						usables.turbo(playerBaseBody);
					}
				}
				return true;
			};
		};
		smallDeadlytrailSprite = new Sprite(-500, -400,
				smallDeadlytrailITextureRegion,
				nats.getVertexBufferObjectManager()) {
			@Override
			public boolean onAreaTouched(TouchEvent pSceneTouchEvent, float X,
					float Y) {
				if (pSceneTouchEvent.isActionDown()) {
					Log.i("Usable", "smallDeadly");
					if (!usables.deadlyTrailIsActivated()) {
						usables.deadlytrail();
					}
				}
				return true;
			};
		};
		smallBombSprite = new Sprite(-500, -400, smallBombITextureRegion,
				nats.getVertexBufferObjectManager()) {
			@Override
			public boolean onAreaTouched(TouchEvent pSceneTouchEvent, float X,
					float Y) {
				if (pSceneTouchEvent.isActionDown()) {
					Log.i("Usable", "smallBomb");
					if (!usables.bombIsActivated()) {
						usables.bomb();
					}
				}
				return true;
			};
		};

		items[0] = smallStasisfieldSprite;
		items[1] = smallTurboSprite;

		HUDGame.attachChild(pauseSprite);
		HUDGame.attachChild(updateSprite);
		HUDGame.attachChild(headerSprite);
		HUDGame.attachChild(resources);
		HUDGame.attachChild(highscore);
		HUDGame.attachChild(usable1);
		HUDGame.attachChild(smallStasisfieldSprite);
		HUDGame.attachChild(usable2);
		HUDGame.attachChild(smallTurboSprite);

		HUDGame.setChildScene(leftAnalogOnScreenControl);
		leftAnalogOnScreenControl.setChildScene(rightAnalogOnScreenControl);

		// playerSprite.setVisible(true);
		// this.attachChild(playerSprite);
		// playerSprite.setChildrenVisible(true);
		this.attachChild(playerBaseSprite);
		// playerBaseSprite.attachChild(playerSprite);

		// PhysicsWorld
		this.registerUpdateHandler(world);

		world.registerPhysicsConnector(new PhysicsConnector(playerBaseSprite,
				playerBaseBody, true, true));
		// world.registerPhysicsConnector(new PhysicsConnector(playerSprite,
		// playerBody, true, true));
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

<<<<<<< HEAD
		//debug = new DebugRenderer(world, nats.getVertexBufferObjectManager());
		//this.attachChild(debug);
=======
		this.attachChild(fireBallSpriteGroup);

		// debug = new DebugRenderer(world,
		// nats.getVertexBufferObjectManager());
		// debug.setAlpha(0f);
		// this.attachChild(debug);
>>>>>>> refs/heads/master
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
		if (items[0] != null) {
			HUDGame.registerTouchArea(items[0]);
		}
		if (items[1] != null) {
			HUDGame.registerTouchArea(items[1]);
		}
		this.setCameraChasing();
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
		if (items[0] != null) {
			HUDGame.unregisterTouchArea(items[0]);
		}
		if (items[1] != null) {
			HUDGame.unregisterTouchArea(items[1]);
		}
	}

	public void showPauseMenu() {
		Log.i("NATS", "showPauseMenu");
		mainCamera.setHUD(HUDPause);
		this.hideGameHUD();
		// this.unsetCameraChasing();
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
		// this.setCameraChasing();
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
		this.unsetCameraChasing();
		playerBaseBody.setTransform(player.getPosX() / 32,
				player.getPosY() / 32, 0.0f);
		this.resetTimer();
		this.unregisterPauseTouch();
	}

	public void showUpgradeMenu() {
		Log.i("NATS", "showUpgradeMenu");
		this.hideGameHUD();
		mainCamera.setHUD(HUDUpgrade);
		this.unsetCameraChasing();
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
		this.setCameraChasing();
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

	public void registerUpgradeSprite(Sprite s) {
		HUDUpgrade.registerTouchArea(s);
	}

	public void unregisterUpgradeSprite(Sprite s) {
		HUDUpgrade.unregisterTouchArea(s);
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

	public void setCameraChasing() {
		// this.registerUpdateHandler(cameraTimerHandler);
	}

	public void unsetCameraChasing() {
		// this.unregisterUpdateHandler(cameraTimerHandler);
	}

	public void startTimer() {
		nats.getEngine().registerUpdateHandler(th);
		player.playMusic();
	}

	public void pauseTimer() {
		nats.getEngine().unregisterUpdateHandler(th);
		player.pauseMusic();
	}

	public void resetTimer() {
		this.mins = 0;
		this.secs = 0;
		highscore.setText("00:00");
		player.resumeMusic();
	}

	public HUD getHUDUpgrade() {
		return HUDUpgrade;
	}

	/*
	 * private Bullet getBullet() { return
	 * bulletPool.onHandleObtainItem(pBullet); }
	 */

	public void setUsable1(int usable) {
		Log.i("Usable", "setUsable1");
		if (usable == -1) {
			if (items[0] != null) {
				Log.i("Usable", "-1");
				HUDGame.detachChild(items[0]);
				// this.unregisterTouchArea(items[0]);
				items[0] = null;
			}
		} else if (usable == finals.stasisfield()) {
			if (items[0] == null) {
				Log.i("Usable", "stasisfield");
				items[0] = smallStasisfieldSprite;
				HUDGame.attachChild(items[0]);
				items[0].setPosition(nats.getCameraWidth() - 200, 80);
				// this.registerTouchArea(items[0]);
			}
		} else if (usable == finals.turbo()) {
			if (items[0] == null) {
				Log.i("Usable", "turbo");
				items[0] = smallTurboSprite;
				HUDGame.attachChild(items[0]);
				items[0].setPosition(nats.getCameraWidth() - 200, 80);
				// this.registerTouchArea(items[0]);
			}
		} else if (usable == finals.deadlytrail()) {
			if (items[0] == null) {
				Log.i("Usable", "deadlytrail");
				items[0] = smallDeadlytrailSprite;
				HUDGame.attachChild(items[0]);
				items[0].setPosition(nats.getCameraWidth() - 200, 80);
				// this.registerTouchArea(items[0]);
			}
		} else if (usable == finals.bomb()) {
			if (items[0] == null) {
				Log.i("Usable", "bomb");
				items[0] = smallBombSprite;
				HUDGame.attachChild(items[0]);
				items[0].setPosition(nats.getCameraWidth() - 200, 80);
				// this.registerTouchArea(items[0]);
			}
		}
	}

	public void setUsable2(int usable) {
		Log.i("Usable", "setUsable2");
		if (usable == -1) {
			Log.i("Usable", "-1");
			if (items[1] != null) {
				HUDGame.detachChild(items[1]);
				// this.unregisterTouchArea(items[1]);
				items[1] = null;
			}
		} else if (usable == finals.stasisfield()) {
			if (items[1] == null) {
				Log.i("Usable", "stasisfield");
				items[1] = smallStasisfieldSprite;
				HUDGame.attachChild(items[1]);
				items[1].setPosition(nats.getCameraWidth() - 90, 180);
				// this.registerTouchArea(items[1]);
			}
		} else if (usable == finals.turbo()) {
			if (items[1] == null) {
				Log.i("Usable", "turbo");
				items[1] = smallTurboSprite;
				HUDGame.attachChild(items[1]);
				items[1].setPosition(nats.getCameraWidth() - 90, 180);
				// this.registerTouchArea(items[1]);
			}
		} else if (usable == finals.deadlytrail()) {
			if (items[1] == null) {
				Log.i("Usable", "deadlytrail");
				items[1] = smallDeadlytrailSprite;
				HUDGame.attachChild(items[1]);
				items[1].setPosition(nats.getCameraWidth() - 90, 180);
				// this.registerTouchArea(items[1]);
			}
		} else if (usable == finals.bomb()) {
			if (items[1] == null) {
				Log.i("Usable", "bomb");
				items[1] = smallBombSprite;
				HUDGame.attachChild(items[1]);
				items[1].setPosition(nats.getCameraWidth() - 90, 180);
				// this.registerTouchArea(items[1]);
			}
		}
	}

	public Body getPlayerBody() {
		return playerBaseBody;
	}

	public BuildableBitmapTextureAtlas getFireBallBitmap() {
		return fireBallBitmapTextureAtlas;
	}

	public ITextureRegion getFireBallITextureRegion() {
		return fireBallITextureRegion;
	}

	public SpriteGroup getFireBallSpriteGroup() {
		return fireBallSpriteGroup;
	}

}
