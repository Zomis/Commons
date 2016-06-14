package net.zomis.events;


public class EventHandlerIface<T extends IEvent> implements IEventHandler, EventListener, Comparable<IEventHandler> {

	private final EventConsumer<T> consumer;
	private final int priority;

	public EventHandlerIface(EventConsumer<T> consumer) {
		this(consumer, Event.DEFAULT_PRIORITY);
	}
	
	public EventHandlerIface(EventConsumer<T> handler, int priority) {
		this.consumer = handler;
		this.priority = priority;
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
		return this.priority;
	}
	
	@Override
	public EventListener getListener() {
		return this;
	}
	
	@Override
	public int compareTo(IEventHandler other) {
		// Because we are using a TreeSet to store EventHandlers in, compareTo should never return "equal".
		int compare = 0;
		if (compare == 0)
			compare = this.getPriority() - other.getPriority();
		if (compare == 0) 
			compare = this.getListener().hashCode() - other.getListener().hashCode();
		if (compare == 0)
			compare = this.hashCode() - other.hashCode();
		return compare;
	}
	
}
