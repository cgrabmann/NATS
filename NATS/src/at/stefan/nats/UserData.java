package at.stefan.nats;

import java.io.Serializable;


public class UserData implements Serializable {

	private static final long serialVersionUID = 1L;

	String userString;
	Object userObject;
	
	public UserData(String userString, Object userObject) {
		this.userString = userString;
		this.userObject = userObject;
	}

	public String getUserString() {
		return userString;
	}
	
	public Object getUserObject() {
		return userObject;
	}
	
}
