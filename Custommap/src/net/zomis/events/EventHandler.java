package net.zomis.events;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

import net.zomis.custommap.CustomFacade;

public class EventHandler implements Comparable<EventHandler>, IEventHandler {
	private final EventListener	listener;
	private final Method method;
	private final Event	annotation;
	
	protected EventHandler() {
		this(null, null, null);
	}
	
	public EventHandler(EventListener listener, Method method, Event annotation) {
		this.listener = listener;
		this.method = method;
		this.annotation = annotation;
	}
	
	@Override
	public EventListener getListener() {
		return listener;
	}
	
	@Override
	public void execute(IEvent event) {
		try {
			method.invoke(listener, event);
		} catch (IllegalAccessException e1) {
			throw new RuntimeException(errorString(event), e1);
		} catch (IllegalArgumentException e1) {
			throw new RuntimeException(errorString(event), e1);
		} catch (InvocationTargetException e1) {
			CustomFacade.getLog().e(errorString(event), e1.getCause());
			if (annotation.propagateExceptions())
				throw new EventException(errorString(event), e1.getCause());
		} 
	}
	private final String errorString(IEvent event) {
		return "Exception when executing " + method.getName() + " in listener " + listener + " for event " + event;
	}
	
	@Override
	public String toString() {
		return "(EventHandler " + this.listener + ": " + method.getName() + ")";
	}

	public int getPriority() {
		return annotation == null ? Event.DEFAULT_PRIORITY : annotation.priority();
	}
	
	@Override
	public int compareTo(EventHandler other) {
		// Because we are using a TreeSet to store EventHandlers in, compareTo should never return "equal".
		if (this.annotation == null)
			return this.hashCode() - other.hashCode();
		
		int annotation = this.annotation.priority() - other.annotation.priority();
		if (annotation == 0) 
			annotation = this.listener.hashCode() - other.listener.hashCode();
		return annotation == 0 ? this.hashCode() - other.hashCode() : annotation;
	}
}
