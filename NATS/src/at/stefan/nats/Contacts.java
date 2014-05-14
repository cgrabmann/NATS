package at.stefan.nats;

import org.andengine.engine.Engine;
import org.andengine.entity.scene.Scene;
import org.andengine.entity.shape.Shape;
import org.andengine.extension.physics.box2d.PhysicsConnector;
import org.andengine.extension.physics.box2d.PhysicsWorld;

import android.util.Log;

import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.Contact;
import com.badlogic.gdx.physics.box2d.ContactImpulse;
import com.badlogic.gdx.physics.box2d.ContactListener;
import com.badlogic.gdx.physics.box2d.Manifold;

public class Contacts implements ContactListener {

	Scene scene;
	PhysicsConnector physicsConnector;
	PhysicsWorld physicsWorld;
	Engine mEngine;

	public Contacts(Scene s, PhysicsWorld pw, Engine e) {
		this.scene = s;
		this.physicsWorld = pw;
		this.mEngine = e;
	}

	/*
	 * final PhysicsConnector physicsConnector =
	 * physicsWorld.getPhysicsConnectorManager
	 * ().findPhysicsConnectorByShape(shape);
	 * 
	 * mEngine.runOnUpdateThread(new Runnable() {
	 * 
	 * @Override public void run() { if (physicsConnector != null) {
	 * physicsWorld.unregisterPhysicsConnector(physicsConnector);
	 * body.setActive(false); physicsWorld.destroyBody(bbody);
	 * scene.detachChild(shape); } } });
	 */

	@Override
	public void beginContact(Contact contact) {
		// TODO Auto-generated method stub
		final Body BodyA = contact.getFixtureA().getBody();
		final Body BodyB = contact.getFixtureB().getBody();

		Log.i("Contact", "new contact");
		UserData b = (UserData) BodyB.getUserData();
		UserData a = (UserData) BodyA.getUserData();

		if (a.getUserString().equals("bullet")
				&& b.getUserString().equals("wall")) {
			Log.i("Contact", "bullet-wall");
			
			final Shape shape = a.getUserShape();
			physicsConnector = a.getUserConnector();
			//physicsConnector.getBody().setTransform(400/32, 240/32, 0f);
			/*mEngine.runOnUpdateThread(new Runnable() {

				@Override
				public void run() {
					if (physicsConnector != null) {
						physicsWorld
								.unregisterPhysicsConnector(physicsConnector);
						BodyA.setActive(false);
						physicsWorld.destroyBody(BodyA);
						scene.detachChild(shape);
					}
				}
			});*/
		} else if (a.getUserString().equals("wall")
				&& b.getUserString().equals("bullet")) {
			Log.i("Contact", "wall-bullet");
			
			final Shape shape = b.getUserShape();
			physicsConnector = b.getUserConnector();
			//physicsConnector.getBody().setTransform(400/32, 240/32, 0f);
			/*mEngine.runOnUpdateThread(new Runnable() {

				@Override
				public void run() {
					if (physicsConnector != null) {
						physicsWorld
								.unregisterPhysicsConnector(physicsConnector);
						BodyB.setActive(false);
						physicsWorld.destroyBody(BodyB);
						scene.detachChild(shape);
					}
				}
			});*/
		}

	}

	@Override
	public void endContact(Contact contact) {
		// TODO Auto-generated method stub

	}

	@Override
	public void preSolve(Contact contact, Manifold oldManifold) {
		// TODO Auto-generated method stub

	}

	@Override
	public void postSolve(Contact contact, ContactImpulse impulse) {
		// TODO Auto-generated method stub

	}

}
