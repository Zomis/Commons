package net.zomis.android;

import android.app.Activity;
import android.graphics.Point;
import android.graphics.drawable.Drawable;
import android.os.Build;
import android.view.Display;
import android.view.View;
import android.widget.ImageView;

@SuppressWarnings("deprecation")
public class ZomisCompat {

	public static void getWindowSize(Activity context, Point outSize) {
		Display display = context.getWindowManager().getDefaultDisplay();
		if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.HONEYCOMB_MR2) {
			display.getSize(outSize);
		}
		else {
			outSize.x = display.getWidth();
			outSize.y = display.getHeight();
		}
	}

	public static void setBackgroundDrawable(View view, Drawable drawable) {
		if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN) {
			view.setBackground(drawable);
		}
		else {
			view.setBackgroundDrawable(drawable);
		}
	}

	public static void setAlpha(ImageView image, float f) {
		if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.HONEYCOMB) {
			image.setAlpha(f);
		}
		else {
			image.setAlpha((int) (255 * f));
		}		
	}

}
