package net.zomis.events;

public interface IEventHandler {

	void execute(IEvent event);

	int getPriority();

	EventListener getListener();

}
