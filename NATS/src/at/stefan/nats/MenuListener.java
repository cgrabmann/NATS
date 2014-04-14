package at.stefan.nats;

import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.PopupWindow;

public class MenuListener implements OnClickListener {
	
	nats nats;
	Button new_game, highscores, settings, exit, pause, con, quit;
	PopupWindow popup;
	
	public MenuListener(Button new_game, Button highscores, Button settings, Button exit, nats nats) {
		this.new_game = new_game;
		this.highscores = highscores;
		this.settings = settings;
		this.exit = exit;
		
		this.nats = nats;
	}
	
	public MenuListener(Button pause, nats nats) {
		this.pause = pause;
		this.nats = nats;
	}
	
	public MenuListener(Button con, Button quit, PopupWindow popup, nats nats) {
		this.con = con;
		this.quit = quit;
		this.popup = popup;
		this.nats = nats;
	}

	@Override
	public void onClick(View v) {
		if(v == pause) {
			Log.i("NATS", "Spiel pausieren");
			nats.actualizeSite("pause");
		}else if(v == new_game) {
			// Verweise auf Spielfläche
			Log.i("NATS", "new_game_listener");
			nats.actualizeSite("new_game");
		}else if(v == highscores) {
			//setContentView(R.layout.highscores);
			Log.i("NATS", "highscores_listener");
			nats.actualizeSite("highscores");
		}else if(v == settings) {
			Log.i("NATS", "settings_listener");
			nats.actualizeSite("settings");
		}else if(v == exit) {
			Log.i("NATS", "exit_listener");
			nats.actualizeSite("exit");
		}else if(v == con) {
			nats.actualizeSite("continue");
			popup.dismiss();
		}else if(v == quit) {
			nats.actualizeSite("quit");
			popup.dismiss();
		}
		
	}

}
