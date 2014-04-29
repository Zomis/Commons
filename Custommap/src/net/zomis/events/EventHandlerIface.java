package net.zomis.events;


public class EventHandlerIface<T extends IEvent> implements IEventHandler, EventListener, Comparable<IEventHandler> {

	private final EventConsumer<T> consumer;

	public EventHandlerIface(EventConsumer<T> consumer) {
		this.consumer = consumer;
	}
	
	@SuppressWarnings("unchecked")
	@Override
	public final void execute(IEvent event) {
		this.executeEvent((T) event);
	}

	public void executeEvent(T event) {
		consumer.executeEvent(event);
	}

	@Override
	public int getPriority() {
		return Event.DEFAULT_PRIORITY;
	}
	
	@Override
	public EventListener getListener() {
		return this;
	}
	
	@Override
	public int compareTo(IEventHandler arg0) {
		return this.hashCode() - arg0.hashCode();
	}
	
}
