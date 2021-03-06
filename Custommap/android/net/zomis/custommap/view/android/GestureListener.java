package net.zomis.custommap.view.android;

import net.zomis.custommap.CustomFacade;
import net.zomis.custommap.view.android.events.AndroidDoubleTapEvent;
import net.zomis.custommap.view.android.events.AndroidFlingEvent;
import net.zomis.custommap.view.android.events.AndroidScrollEvent;
import net.zomis.custommap.view.android.events.CustommapLongPressMapEvent;
import android.view.GestureDetector.SimpleOnGestureListener;
import android.view.MotionEvent;

public class GestureListener extends SimpleOnGestureListener {
	private IAndroidGameView mapView;
	private boolean returnOnFling = false;		// return true or false here??? - changed on 2012-09-21
	private boolean returnOnScroll = false;		// return true or false here??? - changed on 2012-09-21
	private boolean returnOnDown = false;		// return true or false here??? - changed on 2012-09-21
	private boolean returnOnDoubleTap = false;	// return true or false here??? - changed on 2012-09-21
	
	public GestureListener(IAndroidGameView mapView) {
		this.mapView = mapView;
	}

	@Override
    public boolean onDoubleTap(MotionEvent e) {
		if (!this.mapView.isInputEnabled()) return false;
		
		CustomFacade.getGlobalEvents().executeEvent(new AndroidDoubleTapEvent(e, this.mapView));
    	return this.returnOnDoubleTap;
    }
	@Override
    public boolean onFling(MotionEvent e1, MotionEvent e2, float velocityX, float velocityY) {
		if (!this.mapView.isInputEnabled()) return false;
		
		CustomFacade.getGlobalEvents().executeEvent(new AndroidFlingEvent(e1, e2, velocityX, velocityY, this.mapView));
    	return this.returnOnFling;
    }
	@Override
    public void onLongPress(MotionEvent e) {
		if (!this.mapView.isInputEnabled()) return;
		CustomFacade.getGlobalEvents().executeEvent(new CustommapLongPressMapEvent(e, this.mapView));
    }
	
	@Override
    public boolean onScroll(MotionEvent e1, MotionEvent e2, float distanceX, float distanceY) {
		if (!this.mapView.isInputEnabled()) 
			return false;
		
		if (this.mapView == null) {
			CustomFacade.getLog().w("onScroll map null");
			return false;
		}
		if (e1 == null) {
			CustomFacade.getLog().v("onScroll e1 null - this seems to happen when dragging an object");
			return false;
		}
		if (e2 == null) {
			CustomFacade.getLog().w("onScroll e2 null");
			return false;
		}
		
		CustomFacade.getGlobalEvents().executeEvent(new AndroidScrollEvent(e1, e2, distanceX, distanceY, this.mapView));
    	return this.returnOnScroll;
    }
	@Override
	public boolean onDown(MotionEvent e) {
		if (!this.mapView.isInputEnabled()) return false;
//		CustomFacade.getInst().sendNotification(CustomFacade.USER_TOUCH_DOWN, e);
	    return this.returnOnDown;
	}

}