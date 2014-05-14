package at.stefan.nats;

import java.io.Serializable;

import org.andengine.entity.shape.Shape;
import org.andengine.extension.physics.box2d.PhysicsConnector;


public class UserData implements Serializable {

	private static final long serialVersionUID = 1L;

	String userString;
	Shape userShape;
	PhysicsConnector userConnector;
	

	public UserData(String userString, Shape userShape) {
		this.userString = userString;
		this.userShape = userShape;
	}
	
	public UserData(String userString, Shape userShape, PhysicsConnector userConnector) {
		this.userString = userString;
		this.userShape = userShape;
		this.userConnector = userConnector;
	}

	public String getUserString() {
		return userString;
	}
	
	public Shape getUserShape() {
		return userShape;
	}
	
	public PhysicsConnector getUserConnector() {
		return userConnector;
	}

	// getter's and setter's

}
