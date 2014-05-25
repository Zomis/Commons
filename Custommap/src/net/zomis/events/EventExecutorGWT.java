package net.zomis.events;

import java.util.Collection;
import java.util.Comparator;
import java.util.HashMap;
import java.util.Map;
import java.util.TreeSet;

import net.zomis.custommap.CustomFacade;

public class EventExecutorGWT implements IEventExecutor {

	protected final Map<Class<? extends IEvent>, Collection<IEventHandler>> bindings;
	
	public EventExecutorGWT() {
		this.bindings = new HashMap<Class<? extends IEvent>, Collection<IEventHandler>>();
	}
	
	private static final boolean DEBUG = false;
	
	@Override
	public <T extends IEvent> T executeEvent(T event, int i) {
		Collection<IEventHandler> handlers = this.bindings.get(event.getClass());
		if (handlers == null) {
			if (DEBUG)
				CustomFacade.getLog().d("Event " + event.getClass().getSimpleName() + " has no handlers.");
			return event;
		}
		if (DEBUG)
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
		return executeEvent(event, Event.DEFAULT_PRIORITY);
	}

	@Override
	public void registerListener(EventListener listener) {
		throw new UnsupportedOperationException();
	}

	@Override
	public void registerHandler(Class<? extends IEvent> realParam, IEventHandler handler) {
		if (!this.bindings.containsKey(realParam)) {
			this.bindings.put(realParam, new TreeSet<IEventHandler>(new Comparator<IEventHandler>() {
				@Override
				public int compare(IEventHandler a, IEventHandler b) {
					int compareResult = a.getPriority() - b.getPriority();
					if (compareResult == 0) 
						compareResult = a.hashCode() - b.hashCode();
					return compareResult;
				}
			}));
		}
		Collection<IEventHandler> eventHandlersForEvent = this.bindings.get(realParam);
		CustomFacade.getLog().v("Add handler: " + handler + " for event " + realParam.getSimpleName());
		eventHandlersForEvent.add(handler);
	}

	@Override
	public void clearListeners() {
		this.bindings.clear();
	}

	@Override
	public void removeListener(EventListener listener) {
		throw new UnsupportedOperationException();
	}

	@Override
	public boolean containsListener(EventListener listener) {
		return false;
	}

	@Override
	public <T extends IEvent> void registerHandler(Class<? extends T> realParam, EventConsumer<T> handler) {
		registerHandler(realParam, new EventHandlerIface<T>(handler));
	}

}