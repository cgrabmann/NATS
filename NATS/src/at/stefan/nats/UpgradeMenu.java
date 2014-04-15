package at.stefan.nats;

import android.annotation.SuppressLint;
import android.graphics.Point;
import android.view.Display;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.PopupWindow;
import android.widget.RelativeLayout;

public class UpgradeMenu {
	View view;
	PopupWindow popup;
	FrameLayout frame;
	RelativeLayout rel;
	Button con, quit;
	MenuListener ml;
	
	private int width;
	private int height;

	public UpgradeMenu(nats nats) {
		
		LayoutInflater li = LayoutInflater.from(nats.getApplicationContext());
		//view = li.inflate(R.layout.pause_480x800hdpi,(ViewGroup)nats.findViewById(R.layout.new_game_480x800hdpi));
		view = setUpgradeView(nats.getVersion(), li, nats);
		popup = new PopupWindow(view);
		
	}
	
	@SuppressLint("NewApi")
	@SuppressWarnings("deprecation")
	public void show(nats nats) {

		frame = (FrameLayout)nats.getWindow().getDecorView().findViewById(android.R.id.content);
		rel = (RelativeLayout)frame.getChildAt(0);
		
		Display display = nats.getWindowManager().getDefaultDisplay();
		if (android.os.Build.VERSION.SDK_INT >= 13) {
			Point size = new Point();
			display.getSize(size);
			width = size.x;
			height = size.y;
		}else {
			width = display.getWidth();
			height = display.getHeight();
		}
		
		popup.showAtLocation(rel, Gravity.BOTTOM, 0, 0);
        popup.update(width, height);
	}
	
	public void hide() {
		popup.dismiss();
	}
	
	public View setUpgradeView(String version, LayoutInflater li, nats nats) {
		View view = null;
		
		if(version == "240x320ldpi") {
			view = li.inflate(R.layout.upgrades_480x800hdpi,(ViewGroup)nats.findViewById(R.layout.new_game_480x800hdpi));
		}else if(version == "240x400ldpi") {
			view = li.inflate(R.layout.upgrades_480x800hdpi,(ViewGroup)nats.findViewById(R.layout.new_game_480x800hdpi));
		}else if(version == "320x480mdpi") {
			view = li.inflate(R.layout.upgrades_480x800hdpi,(ViewGroup)nats.findViewById(R.layout.new_game_480x800hdpi));
		}else if(version == "480x800mdpi") {
			view = li.inflate(R.layout.upgrades_480x800hdpi,(ViewGroup)nats.findViewById(R.layout.new_game_480x800hdpi));
		}else if(version == "480x854mdpi") {
			view = li.inflate(R.layout.upgrades_480x800hdpi,(ViewGroup)nats.findViewById(R.layout.new_game_480x800hdpi));
		}else if(version == "600x1024mdpi") {
			view = li.inflate(R.layout.upgrades_480x800hdpi,(ViewGroup)nats.findViewById(R.layout.new_game_480x800hdpi));
		}else if(version == "480x800hdpi") {
			view = li.inflate(R.layout.upgrades_480x800hdpi,(ViewGroup)nats.findViewById(R.layout.new_game_480x800hdpi));
		}else if(version == "480x854hdpi") {
			view = li.inflate(R.layout.upgrades_480x800hdpi,(ViewGroup)nats.findViewById(R.layout.new_game_480x800hdpi));
		}else if(version == "720x1280xhdpi") {
			view = li.inflate(R.layout.upgrades_480x800hdpi,(ViewGroup)nats.findViewById(R.layout.new_game_480x800hdpi));
		}else if(version == "768x1280xhdpi") {
			view = li.inflate(R.layout.upgrades_480x800hdpi,(ViewGroup)nats.findViewById(R.layout.new_game_480x800hdpi));
		}else if(version == "1200x1920xhdpi") {
			view = li.inflate(R.layout.upgrades_480x800hdpi,(ViewGroup)nats.findViewById(R.layout.new_game_480x800hdpi));
		}else if(version == "1600x2560xhdpi") {
			view = li.inflate(R.layout.upgrades_480x800hdpi,(ViewGroup)nats.findViewById(R.layout.new_game_480x800hdpi));
		}else if(version == "1080x1920xxhdpi") {
			view = li.inflate(R.layout.upgrades_480x800hdpi,(ViewGroup)nats.findViewById(R.layout.new_game_480x800hdpi));
		}
		
		return view;
	}
}
