package at.stefan.nats;

public class Finals {
	
	private static final int MAIN_MENU = 0;
	private static final int NEW_GAME = 1;
	private static final int HIGHSCORES = 2;
	private static final int SETTINGS = 3;
	private static final int EXIT_GAME = 4;
	
	private static final int MOVESPEED = 0;
	private static final int GUNNER = 1;
	private static final int SHIELD = 2;
	private static final int SHOTFREQUENCE = 3;
	private static final int SHOTSPREADING = 4;
	private static final int STASISFIELD = 0;
	private static final int TURBO = 1;
	private static final int DEADLYTRAIL = 2;
	private static final int BOMB = 3;
	
	private static final int CHEAP = 1;
	private static final int MODERATE = 2;
	private static final int EXPENSIVE = 3;
	
	public enum Usables {
		STASISFIELD, TURBO, DEADLYTRAIL, BOMB
	}
	
	public Finals() {
		
	}
	
	public int main_menu() {
		return MAIN_MENU;
	}
	
	public int new_game() {
		return NEW_GAME;
	}
	
	public int highscores() {
		return HIGHSCORES;
	}
	
	public int settings() {
		return SETTINGS;
	}
	
	public int exit_game() {
		return EXIT_GAME;
	}
	
	public int movespeed() {
		return MOVESPEED;
	}
	
	public int gunner() {
		return GUNNER;
	}
	
	public int shield() {
		return SHIELD;
	}
	
	public int shotfrequence() {
		return SHOTFREQUENCE;
	}
	
	public int shotspreading() {
		return SHOTSPREADING;
	}
	
	public int stasisfield() {
		return STASISFIELD;
	}
	
	public int turbo() {
		return TURBO;
	}
	
	public int deadlytrail() {
		return DEADLYTRAIL;
	}
	
	public int bomb() {
		return BOMB;
	}
	
	public int cheap() {
		return CHEAP;
	}
	
	public int moderate() {
		return MODERATE;
	}
	
	public int expensive() {
		return EXPENSIVE;
	}
}
