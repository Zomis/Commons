package net.zomis.events;

import java.lang.reflect.Method;
import java.util.Collection;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;
import java.util.TreeSet;

import net.zomis.custommap.CustomFacade;

public class EventExecutor {
	public static final int PRE	= -1;
	public static final int ALL = 0;
	public static final int POST = 1;
	
	private final Map<Class<? extends IEvent>, Collection<EventHandler>> bindings;
	private final Set<EventListener> registeredListeners;
	
	private boolean debug = false;
	public void setDebug(boolean debug) {
		this.debug = debug;
	}
	
	public EventExecutor() {
		this.bindings = new HashMap<Class<? extends IEvent>, Collection<EventHandler>>();
		this.registeredListeners = new HashSet<EventListener>();
	}
	private static final EventHandler[] EMPTYHANDLERS = {};
	
	@Deprecated
	public EventHandler[] getListenersFor(Class<? extends IEvent> clazz) {
        Collection<EventHandler> handlers = this.bindings.get(clazz);
        if (handlers == null || handlers.isEmpty()) 
            return EMPTYHANDLERS; // No handlers so we return an empty list
        return handlers.toArray(new EventHandler[handlers.size()]);
    }		
	
	public <T extends IEvent> T executeEvent(T event, int i) {
//		if (this != CustomFacade.getGlobalEvents())		// TODO: Why am I executing events on both global and local EventExecutor?
//			CustomFacade.getGlobalEvents().executeEvent(event);
		
		Collection<EventHandler> handlers = this.bindings.get(event.getClass());
		if (handlers == null) {
			if (this.debug)
				CustomFacade.getLog().d("Event " + event.getClass().getSimpleName() + " has no handlers.");
			return event;
		}
		if (this.debug)
			CustomFacade.getLog().d("Event " + event.getClass().getSimpleName() + " has " + handlers.size() + " handlers.");
		for (EventHandler handler : handlers) {
			// Basic support for multi-stage events. More can be added later by specifying exactly which priority to be executed - executeEventPre(event, lessThanPriority) for example
			if (i == PRE && handler.getPriority() >= 0)
				continue;
			if (i == POST && handler.getPriority() < 0)
				continue;
			handler.execute(event);
		}
		return event;
	}
	public <T extends IEvent> T executeEvent(T event) {
		return this.executeEvent(event, ALL);
	}
	
	public void registerListener(final EventListener listener) {
		CustomFacade.getLog().v("Register event listener: " + listener);
		
		if (registeredListeners.contains(listener)) {
			CustomFacade.getLog().w("Listener already registred: " + listener);
			return;
		}
		
		Method[] methods = listener.getClass().getDeclaredMethods();
		this.registeredListeners.add(listener);
		for (final Method method : methods) {
			Event annotation = method.getAnnotation(Event.class);
			if (annotation == null) // this is going to happen quite often so speed things up by checking this before getting parameter types and stuff
				continue;
			
			Class<?>[] parameters = method.getParameterTypes();
			if (parameters.length == 1 && 
					method.getReturnType().equals(void.class) && 
					IEvent.class.isAssignableFrom(parameters[0])) {
				@SuppressWarnings("unchecked") // Java just doesn't understand that this actually is a safe cast because of the above if-statement
				Class<? extends IEvent> realParam = (Class<? extends IEvent>) parameters[0];

				if (!this.bindings.containsKey(realParam)) {
					this.bindings.put(realParam, new TreeSet<EventHandler>());
				}
				Collection<EventHandler> eventHandlersForEvent = this.bindings.get(realParam);
				CustomFacade.getLog().v("Add listener method: " + method.getName() + " for event " + realParam.getSimpleName());
				eventHandlersForEvent.add(createEventHandler(listener, method, annotation));
			}
		}
	}
	
	private EventHandler createEventHandler(final EventListener listener, final Method method, final Event annotation) {
		return new EventHandler(listener, method, annotation);
	}

	public void clearListeners() {
		this.bindings.clear();
		this.registeredListeners.clear();
	}

	public void removeListener(EventListener listener) {
		for (Entry<Class<? extends IEvent>, Collection<EventHandler>> ee : bindings.entrySet()) {
			Iterator<EventHandler> it = ee.getValue().iterator();
			while (it.hasNext()) {
				EventHandler curr = it.next();
				if (curr.getListener() == listener) 
					it.remove();
			}
		}
		this.registeredListeners.remove(listener);
	}
	@Deprecated
	public Map<Class<? extends IEvent>, Collection<EventHandler>> getBindings() {
		return new HashMap<Class<? extends IEvent>, Collection<EventHandler>>(bindings);
	}
	public boolean containsListener(EventListener listener) {
		return registeredListeners.contains(listener);
	}
	@Deprecated
	public Set<EventListener> getRegisteredListeners() {
		return new HashSet<EventListener>(registeredListeners);
	}
}
