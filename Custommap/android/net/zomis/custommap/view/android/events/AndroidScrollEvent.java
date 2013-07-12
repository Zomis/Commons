package net.zomis.custommap.view.android.events;

import android.view.MotionEvent;
import net.zomis.custommap.view.android.IAndroidGameView;
import net.zomis.events.BaseEvent;

public class AndroidScrollEvent extends BaseEvent {
	public AndroidScrollEvent(MotionEvent e1, MotionEvent e2, float velocityX, float velocityY, IAndroidGameView mapView) {
		this.e1 = e1;
		this.e2 = e2;
		this.distanceX = velocityX;
		this.distanceY = velocityY;
		this.view = mapView;
	}

	private final MotionEvent e1;
	private final MotionEvent e2;
	private final float distanceX;
	private final float distanceY;
	private final IAndroidGameView view;
	
	
	public MotionEvent getE1() {
		return e1;
	}
	public MotionEvent getE2() {
		return e2;
	}
	public float getDistanceX() {
		return distanceX;
	}
	public float getDistanceY() {
		return distanceY;
	}
	public IAndroidGameView getView() {
		return view;
	}

}
