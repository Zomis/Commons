package net.zomis.events;


public abstract class EventHandlerGWT<T extends IEvent> implements IEventHandler, EventListener, Comparable<IEventHandler> {

	public EventHandlerGWT() {
		super();
	}
	
	@SuppressWarnings("unchecked")
	@Override
	public final void execute(IEvent event) {
		this.executeEvent((T) event);
	}

	public abstract void executeEvent(T event);

	@Override
	public String toString() {
		return "EventHandlerGWT@" + hashCode();
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
