package test.net.zomis.events;

import static org.junit.Assert.*;
import net.zomis.events.Event;
import net.zomis.events.EventException;
import net.zomis.events.EventExecutor;
import net.zomis.events.EventListener;

import org.junit.Before;
import org.junit.Test;

public class EventTest implements EventListener {
	
	private EventExecutor events;
	private int messages;
	
	@Before
	public void before() {
		this.events = new EventExecutor();
		this.events.registerListener(this);
	}
	
	@Test
	public void test() {
		assertEquals(0, messages);
		this.events.executeEvent(new SimpleEvent("Message"));
		assertEquals(1, messages);
	}
	@Test(expected = EventException.class)
	public void testFail() {
		assertEquals(0, messages);
		this.events.executeEvent(new SimpleEvent(null));
		fail();
	}
	
	@Event(propagateExceptions = false)
	public void onSimpleEvent(SimpleEvent event) {
		messages++;
	}
	@Event(propagateExceptions = true)
	public void onCrashEvent(SimpleEvent event) {
		if (event.getMessage() == null)
			throw new NullPointerException();
	}

}
