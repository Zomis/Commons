package net.zomis.events;

public interface EventConsumer<T> {

	void executeEvent(T event);
	
}
