package test.net.zomis.events;

import net.zomis.events.IEvent;

public class SimpleEvent implements IEvent {

	private final String message;
	
	public SimpleEvent(String message) {
		this.message = message;
	}
	
	public String getMessage() {
		return message;
	}
}
