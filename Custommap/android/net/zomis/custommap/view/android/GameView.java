package net.zomis.custommap.view.android;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import net.zomis.custommap.CustomFacade;
import net.zomis.custommap.model.GenericMapModel;
import net.zomis.custommap.model.ITileModel;
import net.zomis.custommap.view.general.TileInterface;
import net.zomis.custommap.view.general.ViewContainer;
import net.zomis.custommap.view.general.ViewObject;
import android.graphics.Rect;
import android.media.MediaPlayer;
import android.media.MediaPlayer.OnCompletionListener;
import android.view.GestureDetector;
import android.view.MotionEvent;
import android.view.ScaleGestureDetector;
import android.view.View;
import android.view.View.OnLongClickListener;
import android.view.View.OnTouchListener;
import android.view.ViewGroup;

public abstract class GameView<TM extends ITileModel<?>> extends ViewContainer<TM> implements IAndroidGameView, OnTouchListener, OnLongClickListener {
	private ViewGroup boardView;
	
	protected GenericMapModel<TM> mapModel;
	public GenericMapModel<TM> getMapModel() { return this.mapModel; }
	public ViewGroup getLayout() { return this.boardView; }
	
	public int spacing = 0;
	protected int tileSize = 64;
	public int getTileSizeReal() { return this.tileSize; }
	public int getTileSizeScaled() {
		return (int) (this.tileSize * this.mScaleFactor);
	}
	public int getMapWidth() { return this.mapModel.getMapWidth(); }
	public int getMapHeight() { return this.mapModel.getMapHeight(); }
	public int bgColor = 0xFF000000;
	
	public List<TileInterface<TM>> map;// test with non-transient
	
	protected float scaleFactorMax = 2.1f;
	
	protected int border = 20;
	
	// GameView: not sure if getMinScaleFactor is correct - but don't fix what isn't broken.
	public float getMinScaleFactor() {
		if (this.map == null) return 0.1f;
		
		int minWH = Math.min(this.boardView.getWidth(), this.boardView.getHeight());
//		CustomFacade.getLog().d("GameView.getMinScaleFactor: minWH = " + minWH + " size is " + boardView.getWidth() + " x " + boardView.getHeight());
//		CustomFacade.getLog().d("GameView.getMinScaleFactor: tileSize = " + this.getTileSizeReal());
//		CustomFacade.getLog().d("GameView.getMinScaleFactor: scrollBounds = " + this.scrollBounds.toString());

		if (this.map != null) {
			int minTileWH = Math.min(this.getMapHeight(), this.getMapWidth());
			float retur = (float) ((-border * 2.0 + minWH) / (this.getTileSizeReal() * minTileWH));
//			CustomFacade.getLog().d("GameView.getMinScaleFactor: return " + retur);

			return retur;
		}
		
		CustomFacade.getLog().e("GameView.getMinScaleFactor: returning default (incorrect) value");
		return 0.3f;
	}
	
	private GestureDetector gestureScanner;
	private GestureListener gestureListener;
	private ScaleGestureDetector mScaleDetector;
	private float mScaleFactor = 1.0f;

	boolean inputEnabled = true;
	public void setAllowInput(boolean allowInput) {
		this.inputEnabled = allowInput;
	}
	public boolean isInputEnabled() {
		return this.inputEnabled;
	}
	
	public float getScaleFactor() {
		return this.mScaleFactor;
	}
	
	public GameView(ViewGroup view, GenericMapModel<TM> model) {
		this.mapModel = model;
		this.boardView = view;
	    
	    if (view != null) 
	    	boardView.setOnLongClickListener(this);
	    
	    // Fix map
	    this.map = new ArrayList<TileInterface<TM>>();
	    this.scrollBounds = new Rect(0, 0, 0, 0);
	    
	    if (model != null && model.hasMap()) {
	    	Iterator<TM> it = model.iterator();
	    	while (it.hasNext()) {
	    		TM tm = it.next();
	    		TileInterface<TM> tv = newTileView(this, tm);
	    		// TODO: Overridable method call in constructor -- use an `initMap` method like the map model?
				map.add(tv);
	    	}
	    	
	    	this.updateScrollBounds(true);
	    }
	    
		// Setup detectors
//	    this.mScaleDetector = new ScaleGestureDetector(view.getContext(), new ScaleListener());
	    this.gestureListener = new GestureListener(this);
	    // send true to GestureDetector constructor to prevent two-finger scrolling (conflicts with zoom)
	    this.gestureScanner = new GestureDetector(view.getContext(), this.gestureListener, view.getHandler(), true);
	    view.setOnTouchListener(this);
	    
	    view.setTag(this);
	}

/*	private class ScaleListener extends ScaleGestureDetector.SimpleOnScaleGestureListener {
		@Override
		public boolean onScale(ScaleGestureDetector detector) {
			if (!inputEnabled) return false;
			mScaleFactor *= detector.getScaleFactor();
			// Don't let the object get too small or too large.
//			mScaleFactor = Math.max(GameView.this.scaleFactorMin, Math.min(mScaleFactor, GameView.this.scaleFactorMax));
			mScaleFactor = Math.max(GameView.this.getMinScaleFactor(), Math.min(mScaleFactor, GameView.this.scaleFactorMax));
//			CustomFacade.getLog().d(CustomFacade.LOG_TAG, "********** Scaling! " + mScaleFactor);
			CustomFacade.getLog().d("Current is at " + detector.getCurrentSpan() + ", previous " + detector.getPreviousSpan());
			CustomFacade.getLog().d("Focus is at " + detector.getFocusX() + ", " + detector.getFocusY());
			CustomFacade.getInst().sendNotification(CustomFacade.USER_SCALE, GameView.this);
			
//			GameView.this.tileSize = (int) (32 * mScaleFactor);
//			GameView.this.repaint();
			GameView.this.resize();
			return true;
		}
	}*/
	public void updateScrollBounds(boolean reset) {
        if (this.map != null) {
        	TileInterface<TM> tv;
        	tv = map.get(0);
        	if (tv == null)
        		return;
        	
        	if (reset) this.scrollBounds = new Rect(tv.getX(), tv.getY(), tv.getX(), tv.getY());
//        	CustomFacade.getLog().d("Up-Left   tv: " + tv.getX() + ", " + tv.getWidth() + ", " + tv.getY() + ", " + tv.getHeight() + " and " + tv.getWidth() * this.getScaleFactor());
        	this.scrollBounds.union(tv.getX(), tv.getY());
        	this.scrollBounds.union(tv.getX() + (int)(tv.getWidth() * this.getScaleFactor()), (int) (tv.getY() + tv.getHeight() * this.getScaleFactor()));
//        	this.scrollBounds.union(tv.getX() + tv.getWidth(), tv.getY() + tv.getHeight());

        	tv = map.get(map.size() - 1);
        	this.scrollBounds.union(tv.getX(), tv.getY());
        	this.scrollBounds.union(tv.getX() + (int)(tv.getWidth() * this.getScaleFactor()), (int) (tv.getY() + tv.getHeight() * this.getScaleFactor()));
//        	CustomFacade.getLog().d("Low-Right tv: " + tv.getX() + ", " + tv.getWidth() + ", " + tv.getY() + ", " + tv.getHeight());
        }
        else {
        	if (reset) this.scrollBounds = new Rect(0, 0, 0, 0);
        }
//        if (reset)
//        	this.scrollBounds.inset(border, border);// make a little border around the map
	}
	
	public void playSound(int resId) {
		// use one MediaPlayer per resource, not one per each time it is played ? It might actually play twice at once
		MediaPlayer mp = MediaPlayer.create(this.boardView.getContext(), resId);
        mp.setOnCompletionListener(new OnCompletionListener() {
            public void onCompletion(MediaPlayer mp) {
                mp.release();
            }
        });
        mp.start();
	}
	
	public TileInterface<TM> newTileView(GameView<TM> view, TM model) {
		return null;
	}
	
	@Override
	public boolean onLongClick(View v) {
		CustomFacade.getLog().w("Method not overriden: GridView.onLongClick");
		return false;
	}
	
	@Override
	public boolean onTouch(View view, MotionEvent event) {
		gestureScanner.onTouchEvent(event);
    	if (mScaleDetector != null) mScaleDetector.onTouchEvent(event);
		return false;
	}

	public abstract void repaint();
	public abstract void resize();
	
	public void addViewToGame(View image, boolean backGround) {
		if (image == null) return;
		if (boardView == null) return;
		
		if (backGround)
			boardView.addView(image, 0);
		else boardView.addView(image);
	}
	
	protected Rect scrollBounds;
	
    public void addViewObject(ViewObject object) {
    	if (object == null) throw new IllegalArgumentException("View object is null");// will this be caught anywhere ?
    	if (!(object.getViewToAdd() instanceof View)) CustomFacade.getLog().e("GameView.addViewObject: View to add is invalid: " + object.toString() + " view to add is " + object.getViewToAdd());
		if (boardView == null) throw new NullPointerException("boardView is null");
		
		if (boardView.indexOfChild((View) object.getViewToAdd()) == -1)
			boardView.addView((View) object.getViewToAdd());
		else CustomFacade.getLog().e("View already exists on GameView: " + object);
    }
    public void removeViewObject(ViewObject object) {
    	if (object == null) return; // throw new IllegalArgumentException("View object is null");
    	if (!(object.getViewToAdd() instanceof View)) CustomFacade.getLog().e("GameView.removeViewObject: View to add is invalid: " + object.toString());
		if (boardView == null) throw new NullPointerException("boardView is null");
		if (boardView != null) 
		
		if (boardView.indexOfChild((View) object.getViewToAdd()) > -1)
			boardView.removeView((View) object.getViewToAdd());
		else CustomFacade.getLog().e("View does not exists on GameView: " + object);
    }
    
	public void setZoom(float f) {
		this.mScaleFactor = f;
		this.resize();
	}
	public Rect getScrollBounds() {
		return this.scrollBounds;
	}
}