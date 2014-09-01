package net.zomis.events;

import java.lang.reflect.Method;


public class EventExecutorCatching extends EventExecutor {
	
	@Override
	protected EventHandler createEventHandler(EventListener listener, Method method, Event annotation) {
		return new EventHandler(listener, method, annotation, false);
	}
	
}
