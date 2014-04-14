package at.stefan.nats;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.graphics.Color;
import android.graphics.Point;
import android.view.Display;
import android.view.Gravity;
import android.view.View;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.PopupWindow;
import android.widget.RelativeLayout;

public class PauseMenu extends Activity {

	PopupWindow popup;
	FrameLayout frame, fpopup;
	RelativeLayout rel;
	Button con, quit;
	FrameLayout.LayoutParams params;
	View view;
	MenuListener ml;
	private int width;
	private int height;
	
	public PauseMenu(nats nats) {
		
		popup = new PopupWindow();
		
		fpopup = new FrameLayout(nats.getApplicationContext());
		
		params = new FrameLayout.LayoutParams(
	            FrameLayout.LayoutParams.WRAP_CONTENT,
	            FrameLayout.LayoutParams.WRAP_CONTENT);
		params.setMargins(5, 5, 5, 5);
		params.gravity = Gravity.CENTER;
		
		fpopup.setBackgroundColor(Color.argb(120, 100, 100, 100));
		
		con = new Button(nats.getApplicationContext());
		con.setText(nats.getResources().getString(R.string.con));
		quit = new Button(nats.getApplicationContext());
		quit.setText(nats.getResources().getString(R.string.quit));
		
		ml = new MenuListener(con, quit, popup, nats);
		
		con.setOnClickListener(ml);
		quit.setOnClickListener(ml);
		
		fpopup.addView(con, params);
		fpopup.addView(quit, params);
		
		popup.setContentView(fpopup);
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
	
}
