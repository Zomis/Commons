package net.zomis.events;

public interface IEventExecutor {
	public static final int PRE	= -1;
	public static final int ALL = 0;
	public static final int POST = 1;
	
	<T extends IEvent> T executeEvent(T event, int i);

	<T extends IEvent> T executeEvent(T event);

	void registerListener(EventListener listener);

	void registerHandler(Class<? extends IEvent> realParam, IEventHandler handler);
	
	<T extends IEvent> IEventHandler registerHandler(Class<? extends T> realParam, EventConsumer<T> handler);

	<T extends IEvent> IEventHandler registerHandler(Class<? extends T> realParam, EventConsumer<T> handler, int priority);
	
	void clearListeners();

	void removeListener(EventListener listener);

	boolean containsListener(EventListener listener);

	void removeHandler(IEventHandler listener);
	
}
