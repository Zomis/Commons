package net.zomis.events;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class EventHandler {
	private static final Logger logger = LoggerFactory.getLogger(EventHandler.class);
	
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
		return "(" + this.listener + ": " + method.getName() + ")";
	}

}
