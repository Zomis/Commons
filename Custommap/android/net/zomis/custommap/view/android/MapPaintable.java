package net.zomis.custommap.view.android;

// import com.fasterxml.jackson.annotation.JsonProperty;

import java.lang.reflect.Field;

import net.zomis.custommap.CustomFacade;
import net.zomis.custommap.view.general.ViewObject;
import android.content.Context;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.View.OnLongClickListener;
import android.view.View.OnTouchListener;
/**
 * Target: Android
 * 
 * Represents something that can be drawn on the map
 * Contains an ImageView and a reference to a GameView.
 * 
 * Known subclasses: Overlay
 * @see Overlay
 * @see MyImageView
 */
public class MapPaintable implements ViewObject, OnTouchListener {// extends View, Drawable, or nothing?
	protected transient MyImageView image;
	// TODO: try to avoid MapPaintable.size property? Use TileSize on GameView instead, when possible
	private transient int size;	// is this really needed as a property of MapPaintable ??

	protected transient IAndroidGameView view;
	protected int x;
	protected int y;

	
	public MapPaintable(IAndroidGameView g) {
		this.view = g;
		//	CustomFacade.getLog().d("Zomis", "MapPaintable-Init");
		if (g != null) {
			if (g.getLayout() != null) {
				if (g.getLayout().getContext() != null) {
					image = new MyImageView(g.getLayout().getContext());
					image.setOnTouchListener(this);
//					image.setOnLongClickListener(this);
				}
			}
			view.addViewObject(this);
			this.setSize(g.getTileSizeScaled());
		}
//		updatePosition();
		/*
		 * Constructors must not invoke overridable methods, directly or
		 * indirectly. If you violate this rule, program failure will result.
		 * The superclass constructor runs before the subclass constructor, so
		 * the overriding method in the subclass will be invoked before the
		 * subclass constructor has run. If the overriding method depends on any
		 * initialization performed by the subclass constructor, the method will
		 * not behave as expected.
		 */
	}
	public int getHeight() {
		return this.size;
	}
	/**
	 * Gets the GameView this object is painted on.
	 * Try reducing the usage of this method. Use Commands through the pure MVC framework when possible.
	 * Try to allow MapPaintable without a map assigned to it.
	 * @see GameView
	 */
	public IAndroidGameView getMap() {
		return view;
	}
	public int getSize() {
		return this.size;
	}

	public int getTileSize() {
    	if (this.getMap() == null) return this.size;
		return this.getMap().getTileSizeScaled();
	}
	
    public Object getViewToAdd() {
		return this.image;
	}

    public int getWidth() {
		return this.size;
	}
    
	public boolean onTouch(View view, MotionEvent event) {
//		event.setLocation(event.x, y);
		//CustomFacade.getLog().v("Zomis", "MapPaintable onTouch " + this.toString() + "; " + event.toString());
		if (event.getActionMasked() == MotionEvent.ACTION_DOWN)
			this.getMap().setPerformClick(true);
		this.getMap().onTouch(view, event);
		return false;// what happens if true is returned here instead? Tejpbit's experience is that it will not be clickable
	}

	public void remove() {
		if (view != null) view.removeViewObject(this);
		view = null;
		image = null;
	}
	public void setImageClick(OnClickListener click) {
		this.image.setOnClickListener(click);
	}
	public void setImageTouch(OnTouchListener onTouch) {
		this.image.setOnTouchListener(onTouch);
	}
	public void setImageLongClick(OnLongClickListener listener) {
		this.image.setOnLongClickListener(listener);
	}
	public void setImageResourceByInt(int resId) {
		image.setImageResource(resId);
	}
	/**
	 * 
	 * @param drawableName drawable name in the drawable directory, or it can begin with `android.R.drawable.` to refer to a built-in Android drawable.
	 * @return 0 on failure. Resource integer on success
	 */
	public void setImageResourceByName(String drawableName) {
		this.setImageResourceByInt(getImageResourceByName(this.view.getLayout().getContext(), drawableName));
	}
	
	public static int getImageResourceByName(Context context, String drawableName) {
		if (drawableName.startsWith("android.R.drawable.")) {
			drawableName = drawableName.substring("android.R.drawable.".length());
			try {
				Field f = android.R.drawable.class.getField(drawableName);
				return f.getInt(null);
			} catch (Exception e) {
				CustomFacade.getLog().e("Field error: " + e.getMessage());
				e.printStackTrace();
				return 0;// Indicate failure
			}
		}
		else {
			return context.getResources().getIdentifier(drawableName , "drawable", context.getPackageName());
		}
	}

	
	public void setPosition(int x, int y) {
		this.x = x;
		this.y = y;
		this.updatePosition();
	}

	public void setPositionCentered(int x, int y) {
		this.setPosition(x - this.getWidth() / 2, y - this.getHeight() / 2);
	}
	public void setSize(int size) {
		this.size = size;
	}
	public void setVisible(boolean visible) {
		this.image.setVisibility(visible ? View.VISIBLE : View.INVISIBLE);
	}
	public void updatePosition() {
    	if (this.view == null) return;
    	if (this.image == null) return;
//    	CustomFacade.getLog().v("Zomis", String.format("updatePosition: pos (%d, %d) size: %d", this.x, this.y, this.getTileSize()));
    	
        final int left = this.x;// this.x and this.y is in pixels, not in tile index. (xpos and ypos are tile index)
        final int top = this.y;
        final int right = left + this.getSize();
        final int bottom = top + this.getSize();
      //   CustomFacade.getLog().v("Zomis", String.format("LTRB %d, %d, %d, %d", left, top, right, bottom));
        this.image.layout(left, top, right, bottom);
    }
}
