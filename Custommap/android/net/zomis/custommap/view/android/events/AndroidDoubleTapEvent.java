package net.zomis.custommap.view.android.events;

import net.zomis.custommap.view.android.IAndroidGameView;
import net.zomis.events.BaseEvent;
import android.view.MotionEvent;

public class AndroidDoubleTapEvent extends BaseEvent {

	private MotionEvent	event;
	private IAndroidGameView	mapView;

	public AndroidDoubleTapEvent(MotionEvent e, IAndroidGameView mapView) {
		this.event = e;
		this.mapView = mapView;
	}
	
	public MotionEvent getEvent() {
		return event;
	}
	public IAndroidGameView getMapView() {
		return mapView;
	}

}
