package net.zomis.events;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;

public class EventHandler implements Comparable<EventHandler> {
	private static final Logger logger = LogManager.getLogger(EventHandler.class);
	
	private final EventListener	listener;
	private final Method	method;
	private final Event	annotation;
	
	public EventHandler(EventListener listener, Method method, Event annotation) {
		this.listener = listener;
		this.method = method;
		this.annotation = annotation;
	}
	
	public Event getAnnotation() {
		return annotation;
	}

	public Method getMethod() {
		return method;
	}
	public EventListener getListener() {
		return listener;
	}
	
	public void execute(IEvent event) {
		try {
			method.invoke(listener, event);
		} catch (IllegalAccessException e1) { // Invoke exceptions
			logger.error("Exception when performing EventHandler " + this.listener + " for event " + event.toString(), e1);
		} catch (IllegalArgumentException e1) {
			logger.error("Exception when performing EventHandler " + this.listener + " for event " + event.toString(), e1);
		} catch (InvocationTargetException e1) {
			logger.error("Exception when performing EventHandler " + this.listener + " for event " + event.toString(), e1);
		} 
	}
	@Override
	public String toString() {
		return "(EventHandler " + this.listener + ": " + method.getName() + ")";
	}

	@Override
	public int compareTo(EventHandler other) {
		int annotation = this.annotation.priority() - other.annotation.priority();
		if (annotation == 0) annotation = this.listener.hashCode() - other.listener.hashCode();
		return annotation == 0 ? this.hashCode() - other.hashCode() : annotation;
//		throw new UnsupportedOperationException("Kids don't try this at home."); // TODO: Fixa. Kolla så att alla också kommer med och inte skriver över varandra.
	}
}
