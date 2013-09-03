package net.zomis.events;

import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;

import net.zomis.custommap.CustomFacade;

import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;

public class EventExecutor {
	private static final Logger logger = LogManager.getLogger("Zomis");
	private Map<Class<? extends IEvent>, Collection<EventHandler>> bindings;
	private Set<EventListener> registeredListeners = new HashSet<EventListener>();
	
	private boolean debug = false;
	public void setDebug(boolean debug) {
		this.debug = debug;
	}
	
	public EventExecutor() {
		this.bindings = new HashMap<Class<? extends IEvent>, Collection<EventHandler>>();
	}
	
	public List<EventHandler> getListenersFor(Class<? extends IEvent> clazz) {
		if (!this.bindings.containsKey(clazz)) return new ArrayList<EventHandler>();
		return new ArrayList<EventHandler>(this.bindings.get(clazz));
	}

	@Deprecated
	public CancellableEvent executeCancellableEvent(CancellableEvent event) {
		if (this != CustomFacade.getGlobalEvents())
			CustomFacade.getGlobalEvents().executeCancellableEvent(event);
		
		if (event instanceof BaseEvent) {
			this.executeEvent((BaseEvent) event);
			if (event.isCancelled()) {
				logger.info(event.toString() + " cancelled!");
			}
		}
		return event;
	}
	
	public <T extends IEvent> T executeEvent(T event) {
		if (this != CustomFacade.getGlobalEvents())
			CustomFacade.getGlobalEvents().executeEvent(event);
		
		Collection<EventHandler> handlers = null;
		
		handlers = this.bindings.get(event.getClass());
		if (handlers == null) {
			if (this.debug) {
				logger.info("Event " + event.getClass().getSimpleName() + " has no handlers.");
			}
			return event;
		}
		
		if (this.debug) {
			logger.info("Event " + event.getClass().getSimpleName() + " has " + handlers.size() + " handlers.");
		}
		for (EventHandler handler : handlers) {
			if (handler == null) continue; // should not happen, but you never know...
			
			try {
				handler.execute(event);
			} catch (Exception e) {
				logger.error(String.format("Error handling event %s in %s: %s", event.toString(), handler.toString(), e.getMessage()), e);
			}
		}
		return event;
	}
	
	public void registerListener(final EventListener listener) {
		logger.info("Register event listener: " + listener);
		if (this.bindings == null) throw new NullPointerException("No bindings on MinesweeperEvents has been created.");
		
		if (this.registeredListeners.contains(listener)) {
			CustomFacade.getLog().w("Listener already registred: " + listener);
			return;
		}
		
		Method[] methods = listener.getClass().getDeclaredMethods();
		this.registeredListeners.add(listener);
		for (final Method method : methods) {
			Event annotation = method.getAnnotation(Event.class);
			if (annotation == null) continue;
			
			Class<?>[] parameters = method.getParameterTypes();
			if (parameters.length != 1) continue;
			
			Class<?> param = parameters[0];
			
			if (!method.getReturnType().equals(void.class)) {
				logger.warn("Ignoring method due to non-void return: " + method.getName());
				continue;
			}
			
			if (IEvent.class.isAssignableFrom(param)) {
				@SuppressWarnings("unchecked")
				Class<? extends IEvent> realParam = (Class<? extends IEvent>) param;

				// Get the collection of all events of this class
				if (!this.bindings.containsKey(realParam)) {
					this.bindings.put(realParam, new HashSet<EventHandler>());
				}
				Collection<EventHandler> set = this.bindings.get(realParam);

				// Create a new event handler and add it to the collection
				logger.info("Add listener method: " + method.getName() + " for event " + realParam.getSimpleName());
				set.add(createEventHandler(method, listener, annotation));
			}
		}
	}
	
	private EventHandler createEventHandler(final Method method, final EventListener listener, final Event annotation) {
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
	public Map<Class<? extends IEvent>, Collection<EventHandler>> getBindings() {
		return new HashMap<Class<? extends IEvent>, Collection<EventHandler>>(bindings);
	}
	public Set<EventListener> getRegisteredListeners() {
		return new HashSet<EventListener>(registeredListeners);
	}
}
