package net.zomis.events;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

import net.zomis.custommap.CustomFacade;

public class EventHandler implements IEventHandler {
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
	public int compareTo(IEventHandler other) {
		// Because we are using a TreeSet to store EventHandlers in, compareTo should never return "equal".
		int compare = 0;
		if (compare == 0)
			compare = this.getPriority() - other.getPriority();
		if (compare == 0 && other instanceof EventHandler) 
			compare = this.listener.hashCode() - ((EventHandler)other).listener.hashCode();
		if (compare == 0)
			compare = this.hashCode() - other.hashCode();
		return compare;
	}
}
