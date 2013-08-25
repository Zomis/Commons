package net.zomis.custommap.view;

import net.zomis.custommap.view.android.NonLayoutingLayout;
import net.zomis.events.IEvent;

public class CustommapLayoutReadyEvent implements IEvent {

	private final NonLayoutingLayout	layout;

	public CustommapLayoutReadyEvent(NonLayoutingLayout layout) {
		this.layout = layout;
	}
	
	public NonLayoutingLayout getLayout() {
		return layout;
	}
	
}
