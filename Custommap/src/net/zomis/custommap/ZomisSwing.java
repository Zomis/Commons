package net.zomis.custommap;

import net.zomis.custommap.view.SwingLog;
import net.zomis.events.EventExecutor;
import net.zomis.events.IEventExecutor;

public class ZomisSwing {

	public static void setup() {
		if (CustomFacade.isInitialized())
			return;
		new CustomFacade(new SwingLog());
		CustomFacade.getInst().setEventFactory(new EventFactory() {
			@Override
			public IEventExecutor createEventExecutor() {
				return new EventExecutor();
			}
		});
	}
	
}
