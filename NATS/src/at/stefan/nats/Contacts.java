package at.stefan.nats;

import org.andengine.engine.LimitedFPSEngine;
import org.andengine.entity.scene.Scene;
import org.andengine.extension.physics.box2d.PhysicsConnector;

import android.util.Log;
import at.alex.nats.Player;

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

	public Contacts(Scene s, MaxStepPhysicsWorld pw, LimitedFPSEngine e,
			Player player) {
		this.scene = s;
		this.physicsWorld = pw;
		this.mEngine = e;
	}

	@Override
	public void beginContact(Contact contact) {
		// TODO Auto-generated method stub
		final Body BodyA = contact.getFixtureA().getBody();
		final Body BodyB = contact.getFixtureB().getBody();

		// Log.i("Bullet", "new contact");
		UserData b = (UserData) BodyB.getUserData();
		UserData a = (UserData) BodyA.getUserData();

		/*
		 * else if (a.getUserString().equals("player") &&
		 * b.getUserString().equals("wall")) { ((Player)
		 * a.getUserObject()).setShootingAllowed(false); } else if
		 * (a.getUserString().equals("wall") &&
		 * b.getUserString().equals("player")) { ((Player)
		 * b.getUserObject()).setShootingAllowed(false); }
		 */

	}

	@Override
	public void endContact(Contact contact) {
		// TODO Auto-generated method stub

		/*
		 * final Body BodyA = contact.getFixtureA().getBody(); final Body BodyB
		 * = contact.getFixtureB().getBody();
		 * 
		 * UserData b = (UserData) BodyB.getUserData(); UserData a = (UserData)
		 * BodyA.getUserData();
		 * 
		 * if (a.getUserString().equals("player") &&
		 * b.getUserString().equals("wall")) { ((Player)
		 * a.getUserObject()).setShootingAllowed(true); } else if
		 * (a.getUserString().equals("wall") &&
		 * b.getUserString().equals("player")) { ((Player)
		 * b.getUserObject()).setShootingAllowed(true); }
		 */
	}

	@Override
	public void preSolve(Contact contact, Manifold oldManifold) {
		// TODO Auto-generated method stub
		Body A = contact.getFixtureA().getBody();
		Body B = contact.getFixtureB().getBody();

		UserData a = (UserData) A.getUserData();
		UserData b = (UserData) B.getUserData();

		if (a.getUserString().equals("player")
				|| b.getUserString().equals("player")) {
			if (a.getUserString().equals("bullet")
					|| b.getUserString().equals("bullet")) {
				contact.setEnabled(false);
			} else if (a.getUserString().equals("enemy")
					|| b.getUserString().equals("enemy")) {
				if (player.isTurboActivated()) {
					contact.setEnabled(false);

				} else {
					// Player dies
				}
			} else if (a.getUserString().equals("trail")
					|| b.getUserString().equals("trail")) {
				contact.setEnabled(false);
			}
		}

		else if (a.getUserString().equals("bullet")
				&& b.getUserString().equals("wall")) {
			// Log.i("Contact", "bullet-wall");

			contact.setEnabled(false);
			((Bullet) a.getUserObject()).sendBulletToPool();
		} else if (a.getUserString().equals("wall")
				&& b.getUserString().equals("bullet")) {
			// Log.i("Contact", "wall-bullet");

			contact.setEnabled(false);
			((Bullet) b.getUserObject()).sendBulletToPool();
		}

		else if (a.getUserString().equals("trail")
				&& b.getUserString().equals("trail")) {
			contact.setEnabled(false);
		}

		else if (a.getUserString().equals("bullet")
				&& b.getUserString().equals("trail")) {
			contact.setEnabled(false);
		} else if (b.getUserString().equals("bullet")
				&& a.getUserString().equals("trail")) {
			contact.setEnabled(false);
		}

	}

	@Override
	public void postSolve(Contact contact, ContactImpulse impulse) {
		// TODO Auto-generated method stub

	}

}
