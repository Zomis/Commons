package net.zomis.custommap.view.android;

import android.view.MotionEvent;

public class FlingScrollEvent {
	public MotionEvent e1;
	public MotionEvent e2;
	public float velocityX;
	public float velocityY;
	public IAndroidGameView view;
	@Deprecated
	public FlingScrollEvent(MotionEvent e1, MotionEvent e2, float velocityX, float velocityY) {
		this(e1, e2, velocityX, velocityY, null);
	}
	public FlingScrollEvent(MotionEvent e1, MotionEvent e2, float velocityX, float velocityY, IAndroidGameView gameView) {
		this.e1 = e1;
		this.e2 = e2;
		this.velocityX = velocityX;
		this.velocityY = velocityY;
		this.view = gameView;
	}
	
}
