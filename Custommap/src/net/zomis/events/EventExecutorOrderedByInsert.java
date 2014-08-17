package net.zomis.events;

import java.util.ArrayList;
import java.util.Collection;

public class EventExecutorOrderedByInsert extends EventExecutorGWT {

	@Override
	protected Collection<IEventHandler> createCollection() {
		return new ArrayList<IEventHandler>();
	}

}