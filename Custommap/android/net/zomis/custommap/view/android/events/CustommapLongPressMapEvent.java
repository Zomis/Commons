package net.zomis.custommap.view.android.events;

import android.view.MotionEvent;
import net.zomis.custommap.view.android.IAndroidGameView;
import net.zomis.events.BaseEvent;

public class CustommapLongPressMapEvent extends BaseEvent {

	private final IAndroidGameView	gameView;
	private final MotionEvent	motionEvent;

	public CustommapLongPressMapEvent(MotionEvent motionEvent, IAndroidGameView view) {
		this.gameView = view;
		this.motionEvent = motionEvent;
	}
	public IAndroidGameView getGameView() {
		return gameView;
	}
	public MotionEvent getMotionEvent() {
		return motionEvent;
	}
	
}
