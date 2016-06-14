package net.zomis.custommap.view.android.events;

import net.zomis.custommap.view.android.IAndroidGameView;
import net.zomis.events.IEvent;
import android.view.MotionEvent;

public class AndroidFlingEvent implements IEvent {

	public AndroidFlingEvent(MotionEvent e1, MotionEvent e2, float velocityX, float velocityY, IAndroidGameView mapView) {
		this.e1 = e1;
		this.e2 = e2;
		this.velocityX = velocityX;
		this.velocityY = velocityY;
		this.view = mapView;
	}

	private final MotionEvent e1;
	private final MotionEvent e2;
	private final float velocityX;
	private final float velocityY;
	private final IAndroidGameView view;
	
	
	public MotionEvent getE1() {
		return e1;
	}
	public MotionEvent getE2() {
		return e2;
	}
	public float getVelocityX() {
		return velocityX;
	}
	public float getVelocityY() {
		return velocityY;
	}
	public IAndroidGameView getView() {
		return view;
	}
}
