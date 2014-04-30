package at.stefan.nats;

import org.andengine.entity.scene.menu.MenuScene;
import org.andengine.entity.scene.menu.MenuScene.IOnMenuItemClickListener;
import org.andengine.entity.scene.menu.item.IMenuItem;

import android.util.Log;
import at.stefan.nats.SceneManager.AllScenes;


public class MenuListener implements IOnMenuItemClickListener {
	
	Finals finals;
	nats nats;
	SceneManager sceneManager;
	
	public MenuListener(nats nats, SceneManager s) {
		finals = new Finals();
		this.nats = nats;
		this.sceneManager = s;
	}

	@Override
	public boolean onMenuItemClicked(MenuScene pMenuScene, IMenuItem pMenuItem,
			float pMenuItemLocalX, float pMenuItemLocalY) {
		
		if(pMenuItem.getID() == finals.new_game()) {
			Log.i("NATS", "new game");
			sceneManager.switchScene(AllScenes.NEW_GAME);
			//return true;
		}else if(pMenuItem.getID() == finals.highscores()) {
			Log.i("NATS", "highscores");
			sceneManager.switchScene(AllScenes.HIGHSCORES);
			//return true;
		}else if(pMenuItem.getID() == finals.settings()) {
			Log.i("NATS", "settings");
			sceneManager.switchScene(AllScenes.SETTINGS);
			//return true;
		}else if(pMenuItem.getID() == finals.exit_game()) {
			Log.i("NATS", "exit game");
			//nats.onDestroy();
			nats.finish();
			//return true;
		}
		
		return false;
	}
	
	
	
	
	
	
	
	
	
	
	
	
	
	/*@Override
	public boolean onMenuItemClicked(MenuScene pMenuScene, IMenuItem pMenuItem, float pMenuItemLocalX, float pMenuItemLocalY)
	{
	    handleButtonActions();
	    
	    switch(pMenuItem.getID())
	    {
	        case 0:
	            //action
	            return true;
	        case 1:
	            //action
	            return true;
	        default:
	            return false;
	    }
	}*/
	
	/*nats nats;
	Button new_game, highscores, settings, exit, pause, upgrade, con, quit;
	PopupWindow popup;
	
	public MenuListener(Button new_game, Button highscores, Button settings, Button exit, nats nats) {
		this.new_game = new_game;
		this.highscores = highscores;
		this.settings = settings;
		this.exit = exit;
		
		this.nats = nats;
	}
	
	public MenuListener(Button pause, Button upgrade, nats nats) {
		this.pause = pause;
		this.upgrade = upgrade;
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
		}else if(v == upgrade) {
			nats.actualizeSite("upgrade");
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
		
	}*/

}
