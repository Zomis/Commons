package net.zomis.events;

import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import net.zomis.custommap.CustomFacade;

import org.apache.log4j.LogManager;

public class EventExecutor {
	private static final org.apache.log4j.Logger logger = LogManager.getLogger("Zomis");
	private Map<Class<? extends BaseEvent>, Collection<EventHandler>> bindings;
	
	private boolean debug = false;
	public void setDebug(boolean debug) {
		this.debug = debug;
	}
	
	public EventExecutor() {
		this.bindings = new HashMap<Class<? extends BaseEvent>, Collection<EventHandler>>();
	}
	
	public List<EventHandler> getListenersFor(Class<? extends BaseEvent> clazz) {
		if (!this.bindings.containsKey(clazz)) return new ArrayList<EventHandler>();
		return new ArrayList<EventHandler>(this.bindings.get(clazz));
	}

	public CancellableEvent executeCancellableEvent(CancellableEvent event) {
		if (event instanceof BaseEvent) {
			this.executeEvent((BaseEvent) event);
			if (event.isCancelled()) {
				logger.info(event.toString() + " cancelled!");
			}
		}
		return event;
	}
	
	public BaseEvent executeEvent(BaseEvent event) {
		if (this.debug) {
			logger.info("Execute Event: " + event.getClass().getSimpleName());
			CustomFacade.getLog().i("Execute Event: " + event.getClass().getSimpleName());
		}
		if (this.bindings == null) return event;
		
		Collection<EventHandler> handlers = null;
		
		handlers = this.bindings.get(event.getClass());
		if (handlers == null) return event;
		
		if (this.debug) {
			logger.info("Events has " + handlers.size() + " handlers.");
			CustomFacade.getLog().i("Execute Event: " + event.getClass().getSimpleName());
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
	
	private Set<EventListener> registeredListeners = new HashSet<EventListener>();
	
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
			
			if (BaseEvent.class.isAssignableFrom(param)) {
				@SuppressWarnings("unchecked")
				Class<? extends BaseEvent> realParam = (Class<? extends BaseEvent>) param;

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
	}

	public void removeListener(EventListener listener) {
		CustomFacade.getLog().w("// TODO: Fixa. Remove listener.");
	}
	public Map<Class<? extends BaseEvent>, Collection<EventHandler>> getBindings() {
		return new HashMap<Class<? extends BaseEvent>, Collection<EventHandler>>(bindings);
	}
	public Set<EventListener> getRegisteredListeners() {
		return new HashSet<EventListener>(registeredListeners);
	}
}
