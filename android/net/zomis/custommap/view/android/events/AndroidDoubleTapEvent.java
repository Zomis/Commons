package net.zomis.custommap.view.android.events;

import net.zomis.custommap.view.android.IAndroidGameView;
import net.zomis.events.IEvent;
import android.view.MotionEvent;

public class AndroidDoubleTapEvent implements IEvent {

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
