package net.zomis.custommap.view.android;

import java.io.File;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.ColorMatrix;
import android.graphics.ColorMatrixColorFilter;
import android.graphics.Paint;
import android.util.DisplayMetrics;
import android.view.View;
import android.view.View.MeasureSpec;

public class AndroidUtils {
	public static int dpToPixels(int dp, Context context ) {
    	DisplayMetrics metrics = context.getResources().getDisplayMetrics();
		return dp * (metrics.densityDpi / 160);
	}

	public static File findUnusedFilename(File saveDirectory, String name, String extension) {
		File file;
		String str = "";
		int i = 0;
		do {
			file = new File(saveDirectory, name + str + extension);
			str = Integer.toString(i++);
		}
		while (file.exists());
		
		return file;
	}

	public static Bitmap loadBitmapFromView(View v) {
//		CustomFacade.getLog().i("View width " + v.getWidth() + " layoutparam width " + v.getLayoutParams().width);
		int w = v.getWidth(); // v.getLayoutParams().width
		int h = v.getHeight(); // v.getLayoutParams().height
		Bitmap b = Bitmap.createBitmap(w, h, Bitmap.Config.ARGB_8888);
		Canvas c = new Canvas(b);
		v.layout(0, 0, w, h);
		v.draw(c);
		return b;
	}

	public static Bitmap screenshot(View view) {
		view.setDrawingCacheEnabled(true);
		Bitmap b = view.getDrawingCache();
		if (b != null) return b;
		else {
			view.setDrawingCacheEnabled(true);
			// this is the important code :)  
			// Without it the view will have a dimension of 0,0 and the bitmap will be null          
			view.measure(MeasureSpec.makeMeasureSpec(0, MeasureSpec.UNSPECIFIED), 
			            MeasureSpec.makeMeasureSpec(0, MeasureSpec.UNSPECIFIED));
			view.layout(0, 0, view.getMeasuredWidth(), view.getMeasuredHeight()); 
			view.buildDrawingCache(true);
			Bitmap cache = view.getDrawingCache();
			if (cache == null) {
				return AndroidUtils.loadBitmapFromView(view);
			}
			else {
				b = Bitmap.createBitmap(cache);
				view.destroyDrawingCache();
				view.setDrawingCacheEnabled(false); // clear drawing cache
				return b;
			}
		}
	}
	/**
	 * untested
	 * @param original
	 * @return
	 */
	public static Bitmap toGrayscale(Bitmap original) {
		return bitmapFilter(original, (ColorMatrixColorFilter) null);
	}
	public static Bitmap bitmapFilter(Bitmap original, float[] matrix) {
		return bitmapFilter(original, new ColorMatrixColorFilter(matrix));
	}
	public static Bitmap bitmapFilter(Bitmap original, ColorMatrixColorFilter cmcf) {
        Bitmap bmpGrayscale = Bitmap.createBitmap(original.getWidth(), original.getHeight(), Bitmap.Config.ARGB_8888);
        Canvas c = new Canvas(bmpGrayscale);
        Paint paint = new Paint();
        if (cmcf == null) {
        	ColorMatrix cm = new ColorMatrix();
            cm.setSaturation(0);
            cmcf = new ColorMatrixColorFilter(cm);
        }
        paint.setColorFilter(cmcf);
        c.drawBitmap(original, 0, 0, paint);
        return bmpGrayscale;
    }
	
}
