package at.stefan.nats;

public class Finals {
	
	private static final int MAIN_MENU = 0;
	private static final int NEW_GAME = 1;
	private static final int HIGHSCORES = 2;
	private static final int SETTINGS = 3;
	private static final int EXIT_GAME = 4;

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
}
