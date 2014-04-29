package net.zomis.events;

public interface IEventHandler extends Comparable<IEventHandler> {

	void execute(IEvent event);

	int getPriority();

	EventListener getListener();

}
