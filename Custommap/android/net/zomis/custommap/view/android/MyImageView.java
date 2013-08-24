package net.zomis.custommap.view.android;

import net.zomis.custommap.view.general.ViewObject;
import android.content.Context;
import android.graphics.Canvas;
import android.util.AttributeSet;
import android.widget.ImageView;

/**
 * An ImageView that takes care of it's own drawing using a custom draw function.
 * 
 * Implements ViewObject
 * 
 * @see ViewObject
 * 
 */
public class MyImageView extends ImageView implements ViewObject {
	public static interface ImageDraw {
		void onDraw(MyImageView myImageView, Canvas canvas);
	}
	
	protected IAndroidGameView view;
	private ImageDraw drawer;
	public MyImageView(IAndroidGameView map, boolean backGround) {
		super(map.getLayout().getContext());
		this.view = map;
		map.addViewToGame(this, backGround);
	}

	public MyImageView(Context context) {
		super(context);
	}
	public MyImageView(Context context, AttributeSet attributes, int anInt) {
		super(context, attributes, anInt);
	}
	public MyImageView(Context context, AttributeSet attributes) {
		super(context, attributes);
	}

	public Object getViewToAdd() {
		return this;
	}
	
	public void removeView() { this.view.removeViewObject(this); }
	
  	@Override
	public void onDraw(Canvas canvas) {
  		if (this.drawer == null) super.onDraw(canvas);
  		else this.drawer.onDraw(this, canvas);
  	}

	public void setDrawer(ImageDraw drawer) {
		this.drawer = drawer;
	}
}
