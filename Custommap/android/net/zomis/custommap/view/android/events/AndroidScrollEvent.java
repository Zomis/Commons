package net.zomis.custommap.view.android.events;

import net.zomis.custommap.view.android.IAndroidGameView;
import net.zomis.events.IEvent;
import android.view.MotionEvent;

public class AndroidScrollEvent implements IEvent {
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
	@Override
	public String toString() {
		return String.format("AndroidScrollEvent %f, %f on view %s", this.getDistanceX(), this.getDistanceY(), this.getView());
	}
	public float getDistanceLength() {
		return (float) Math.sqrt(distanceX * distanceX + distanceY * distanceY);
	}
	public float getAngleRadians() {
		return (float) Math.atan2(this.distanceY, this.distanceX);
	}
}
