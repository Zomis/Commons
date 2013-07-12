package net.zomis.custommap.view;

import net.zomis.custommap.view.android.NonLayoutingLayout;
import net.zomis.events.BaseEvent;

public class CustommapLayoutReadyEvent extends BaseEvent {

	private final NonLayoutingLayout	layout;

	public CustommapLayoutReadyEvent(NonLayoutingLayout layout) {
		this.layout = layout;
	}
	
	public NonLayoutingLayout getLayout() {
		return layout;
	}
	
}
