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

public class EventExecutor implements IEventExecutor {
	// TODO: Thread-Safety: http://chat.stackexchange.com/transcript/message/15332362#15332362
	
	protected final Map<Class<? extends IEvent>, Collection<IEventHandler>> bindings;
	private final Set<EventListener> registeredListeners;
	
	private boolean debug = false;
	
	public void setDebug(boolean debug) {
		this.debug = debug;
	}
	
	public EventExecutor() {
		this.bindings = new HashMap<Class<? extends IEvent>, Collection<IEventHandler>>();
		this.registeredListeners = new HashSet<EventListener>();
	}
	private static final EventHandler[] EMPTYHANDLERS = {};
	
	@Deprecated
	public IEventHandler[] getListenersFor(Class<? extends IEvent> clazz) {
        Collection<IEventHandler> handlers = this.bindings.get(clazz);
        if (handlers == null || handlers.isEmpty()) 
            return EMPTYHANDLERS; // No handlers so we return an empty list
        return handlers.toArray(new IEventHandler[handlers.size()]);
    }		
	
	@Override
	public <T extends IEvent> T executeEvent(T event, int i) {
//		if (this != CustomFacade.getGlobalEvents())		// TODO: Why am I executing events on both global and local EventExecutor?
//			CustomFacade.getGlobalEvents().executeEvent(event);
		
		Collection<IEventHandler> handlers = this.bindings.get(event.getClass());
		if (handlers == null) {
			if (this.debug)
				CustomFacade.getLog().d("Event " + event.getClass().getSimpleName() + " has no handlers.");
			return event;
		}
		if (this.debug)
			CustomFacade.getLog().d("Event " + event.getClass().getSimpleName() + " has " + handlers.size() + " handlers.");
		for (IEventHandler handler : handlers) {
			// Basic support for multi-stage events. More can be added later by specifying exactly which priority to be executed - executeEventPre(event, lessThanPriority) for example
			if (i == PRE && handler.getPriority() >= 0)
				continue;
			if (i == POST && handler.getPriority() < 0)
				continue;
			handler.execute(event);
		}
		return event;
	}
	
	@Override
	public <T extends IEvent> T executeEvent(T event) {
		return this.executeEvent(event, ALL);
	}
	
	@Override
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
				
				registerHandler(realParam, createEventHandler(listener, method, annotation));
			}
		}
	}
	
	@Override
	public void registerHandler(Class<? extends IEvent> realParam, IEventHandler handler) {
		if (!this.bindings.containsKey(realParam)) {
			this.bindings.put(realParam, new TreeSet<IEventHandler>());
		}
		Collection<IEventHandler> eventHandlersForEvent = this.bindings.get(realParam);
		if (debug)
			CustomFacade.getLog().v("Add handler: " + handler + " for event " + realParam.getSimpleName());
		eventHandlersForEvent.add(handler);
	}

	private EventHandler createEventHandler(final EventListener listener, final Method method, final Event annotation) {
		return new EventHandler(listener, method, annotation);
	}

	@Override
	public void clearListeners() {
		this.bindings.clear();
		this.registeredListeners.clear();
	}

	@Override
	public void removeListener(EventListener listener) {
		for (Entry<Class<? extends IEvent>, Collection<IEventHandler>> ee : bindings.entrySet()) {
			Iterator<IEventHandler> it = ee.getValue().iterator();
			while (it.hasNext()) {
				IEventHandler curr = it.next();
				if (curr.getListener() == listener)
					it.remove();
			}
		}
		this.registeredListeners.remove(listener);
	}
	@Deprecated
	public Map<Class<? extends IEvent>, Collection<IEventHandler>> getBindings() {
		return new HashMap<Class<? extends IEvent>, Collection<IEventHandler>>(bindings);
	}
	
	@Override
	public boolean containsListener(EventListener listener) {
		return registeredListeners.contains(listener);
	}
	
	@Deprecated
	public Set<EventListener> getRegisteredListeners() {
		return new HashSet<EventListener>(registeredListeners);
	}

	@Override
	public <T extends IEvent> void registerHandler(Class<? extends T> realParam, EventConsumer<T> handler) {
		registerHandler(realParam, new EventHandlerIface<T>(handler));
	}
}
