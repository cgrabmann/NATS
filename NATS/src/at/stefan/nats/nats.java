package at.stefan.nats;

import android.app.Activity;
import android.os.Bundle;
import android.util.Log;
import android.view.KeyEvent;
import android.widget.Button;
import at.stefan.nats.R;

public class nats extends Activity {
	private String actualSite = "main_menu";
	private boolean pauseActivated = false;
	
	Button new_game;
	Button highscores;
	Button settings;
	Button exit;
	Button pause;
	
	MenuListener ml;
	PauseMenu pm;

	public void onCreate(Bundle savedInstanceState) {
		
		super.onCreate(savedInstanceState);
		setContentView(R.layout.main_menu);
		
		elementSearchMain();
	}
	
	public boolean onKeyDown(int keyCode, KeyEvent event) {
		if(keyCode == KeyEvent.KEYCODE_BACK) {
			if(actualSite != "main_menu") {
				actualSite = "main_menu";
				if(pauseActivated == true) {
					pauseActivated = false;
					pm.hide();
				}
				setContentView(R.layout.main_menu);
				elementSearchMain();
				Log.i("NATS", "back to main_menu");
			}
			//setContentView(R.layout.main_menu_linear);
			return true;
		}
		
		return super.onKeyDown(keyCode, event);
	}
	
	public void actualizeSite(String s) {
		this.actualSite = s;
		if(actualSite == "new_game") {
			setContentView(R.layout.new_game);
			//Log.i("NATS", Integer.toString(this.get))
			elementSearchGame();
			Log.i("NATS", "new game");
			// Go to new game
		}else if(actualSite == "continue") {
			// Spiel weiterlaufen lassen
		}else if(actualSite == "quit") {
			setContentView(R.layout.main_menu);
			elementSearchMain();
		}else if(actualSite == "highscores") {
			setContentView(R.layout.highscores);
			Log.i("NATS", "go to highscores");
		}else if(actualSite == "settings") {
			setContentView(R.layout.settings);
			Log.i("NATS", "settings");
		}else if(actualSite == "exit") {
			Log.i("NATS", "Beende App");
			nats.this.finish();
		}else if(actualSite == "pause") {
			Log.i("NATS", "in nats, pause");
			if(pauseActivated == false) {
				pm = new PauseMenu(this);
				pm.show(this);
				Log.i("NATS", "show pause");
				pauseActivated = true;
			}else {
				pm.hide();
				Log.i("NATS", "hide pause");
				pauseActivated = false;
			}
			
		}
		//System.out.println(actualSite); 
	}
	
	public void elementSearchMain() {
		new_game = (Button)findViewById(R.id.new_game);
		highscores = (Button)findViewById(R.id.highscores);
		settings = (Button)findViewById(R.id.settings);
		exit = (Button)findViewById(R.id.exit);
		
		ml = new MenuListener(new_game, highscores, settings, exit, this);
		
		new_game.setOnClickListener(ml);
		highscores.setOnClickListener(ml);
		settings.setOnClickListener(ml);
		exit.setOnClickListener(ml);
	}
	
	public void elementSearchGame() {
		pause = (Button)findViewById(R.id.pause);
		ml = new MenuListener(pause, this);
		pause.setOnClickListener(ml);
		Log.i("NATS", "CHANGE");
	}
}
