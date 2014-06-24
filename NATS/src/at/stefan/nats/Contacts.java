package at.stefan.nats;

import org.andengine.engine.LimitedFPSEngine;
import org.andengine.entity.scene.Scene;
import org.andengine.extension.physics.box2d.PhysicsConnector;

import at.alex.nats.Player;
import at.clemens.nats.EnemyTypeZero;
import at.clemens.nats.PEnemy;
import at.stefan.nats.SceneManager.AllScenes;

import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.Contact;
import com.badlogic.gdx.physics.box2d.ContactImpulse;
import com.badlogic.gdx.physics.box2d.ContactListener;
import com.badlogic.gdx.physics.box2d.Manifold;

public class Contacts implements ContactListener {

	Scene scene;
	PhysicsConnector physicsConnector;
	MaxStepPhysicsWorld physicsWorld;
	LimitedFPSEngine mEngine;

	Player player;
	SceneManager sceneManager;

	public Contacts(Scene s, MaxStepPhysicsWorld pw, LimitedFPSEngine e,
			Player player, SceneManager sc) {
		this.scene = s;
		this.physicsWorld = pw;
		this.mEngine = e;
		this.player = player;
		this.sceneManager = sc;
	}

	@Override
	public void beginContact(Contact contact) {
		// TODO Auto-generated method stub
		/*Body A = contact.getFixtureA().getBody();
		Body B = contact.getFixtureB().getBody();

		UserData a = (UserData) A.getUserData();
		UserData b = (UserData) B.getUserData();
		
		if(a.getUserString().equals("player") && b.getUserObject() instanceof PEnemy) {
			sceneManager.switchScene(AllScenes.GAME_OVER);
		}else if(b.getUserString().equals("player") && a.getUserObject() instanceof PEnemy) {
			sceneManager.switchScene(AllScenes.GAME_OVER);
		}*/
	}

	@Override
	public void endContact(Contact contact) {
		// TODO Auto-generated method stub

	}

	@Override
	public void preSolve(Contact contact, Manifold oldManifold) {
		// TODO Auto-generated method stub
		//contact.getFixtureB().
		Body A = contact.getFixtureA().getBody();
		Body B = contact.getFixtureB().getBody();

		UserData a = (UserData) A.getUserData();
		UserData b = (UserData) B.getUserData();

		// Player - Bullet
		if (a.getUserString().equals("player")
				&& b.getUserString().equals("bullet")) {
			contact.setEnabled(false);
		} else if (b.getUserString().equals("player")
				&& a.getUserString().equals("bullet")) {
			contact.setEnabled(false);
		}

		// Enemy - Enemy
		else if (a.getUserObject() instanceof PEnemy
				&& b.getUserObject() instanceof PEnemy) {
			contact.setEnabled(false);
		}

		// Bullet - Wall
		else if (a.getUserString().equals("bullet")
				&& b.getUserString().equals("wallNS")) {
			((Bullet) a.getUserObject()).sendBulletToPool();
		} else if (b.getUserString().equals("bullet")
				&& a.getUserString().equals("wallNS")) {
			((Bullet) b.getUserObject()).sendBulletToPool();
		} else if (a.getUserString().equals("bullet")
				&& b.getUserString().equals("wallEW")) {
			((Bullet) a.getUserObject()).sendBulletToPool();
		} else if (b.getUserString().equals("bullet")
				&& a.getUserString().equals("wallEW")) {
			((Bullet) b.getUserObject()).sendBulletToPool();
		}

		// Bullet - Enemy
		else if (a.getUserString().equals("bullet")
				&& b.getUserObject() instanceof PEnemy) {
			((Bullet) a.getUserObject()).sendBulletToPool();
			((PEnemy) b.getUserObject()).stop();
		} else if (b.getUserString().equals("bullet")
				&& a.getUserObject() instanceof PEnemy) {
			((Bullet) b.getUserObject()).sendBulletToPool();
			((PEnemy) a.getUserObject()).stop();
		}

		// Player - Trail
		else if (a.getUserString().equals("player")
				&& b.getUserString().equals("trail")) {
			contact.setEnabled(false);
		} else if (b.getUserString().equals("player")
				&& a.getUserString().equals("trail")) {
			contact.setEnabled(false);
		}
		
		// Trail - Trail
		else if (a.getUserString().equals("trail")
				&& b.getUserString().equals("trail")) {
			contact.setEnabled(false);
		}
		
		// Bullet - Trail
		else if (a.getUserString().equals("bullet")
				&& b.getUserString().equals("trail")) {
			contact.setEnabled(false);
		} else if (b.getUserString().equals("bullet")
				&& a.getUserString().equals("trail")) {
			contact.setEnabled(false);
		}
		
		// Trail - Enemy
		else if (a.getUserObject() instanceof PEnemy
				&& b.getUserString().equals("trail")) {
			((PEnemy) a.getUserObject()).stop();
		} else if (b.getUserObject() instanceof PEnemy
				&& a.getUserString().equals("trail")) {
			((PEnemy) b.getUserObject()).stop();
		}
		
		// PlayerZero - Wall
		else if (a.getUserString().equals("enemyzero")
				&& b.getUserString().equals("wallNS")) {
			((EnemyTypeZero) a.getUserObject()).colisionNS();
		} else if (b.getUserString().equals("enemyzero")
				&& a.getUserString().equals("wallNS")) {
			((EnemyTypeZero) b.getUserObject()).colisionNS();
		} else if (a.getUserString().equals("enemyzero")
				&& b.getUserString().equals("wallEW")) {
			((EnemyTypeZero) a.getUserObject()).colisionWE();
		} else if (b.getUserString().equals("enemyzero")
				&& a.getUserString().equals("wallEW")) {
			((EnemyTypeZero) b.getUserObject()).colisionWE();
		}
		
		// Player - Enemy
		else if (a.getUserObject() instanceof PEnemy
				&& b.getUserString().equals("player")) {
			if(player.isTurboActivated()) {
				contact.setEnabled(false);
				((PEnemy)a.getUserObject()).stop();
			}else if(player.isShieldActivated()) {
				contact.setEnabled(false);
				player.getPlayerBase().setAlpha(0f);
				player.removeShield();
				((PEnemy)a.getUserObject()).stop();
			}else {
				// Spieler zerstören
				//sceneManager.switchScene(AllScenes.GAME_OVER);
			}
		} else if (b.getUserObject() instanceof PEnemy
				&& a.getUserString().equals("player")) {
			if(player.isTurboActivated()) {
				contact.setEnabled(false);
				((PEnemy)b.getUserObject()).stop();
			}else if(player.isShieldActivated()) {
				contact.setEnabled(false);
				player.getPlayerBase().setAlpha(0f);
				player.removeShield();
				((PEnemy)b.getUserObject()).stop();
			}else {
				// Spieler zerstören
				//sceneManager.switchScene(AllScenes.GAME_OVER);
			}
		}

	}

	@Override
	public void postSolve(Contact contact, ContactImpulse impulse) {
		// TODO Auto-generated method stub

	}

}
