package net.zomis.events;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * Indication that a method should handle an {@link IEvent}<br>
 * The method needs to return {@link Void} and expect exactly one parameter of a type that implements {@link IEvent}.<br>
 * Only methods in classes that implements {@link EventListener} should use this annotation.
 */
@Retention(value = RetentionPolicy.RUNTIME)
@Target(ElementType.METHOD)
public @interface Event {
	/**
	 * Low priority = Executed earlier
	 * 
	 * <p>For use with multi-staged events, a priority of less than zero means <b>before</b> something happens.
	 * 
	 * @return The priority of this event
	 */
	int priority() default 50;
	/**
	 * If true, exceptions caused within this method will be rethrowed, possibly causing program failure as a result
	 * 
	 * If false, exception is logged and then ignored.
	 * 
	 * @return False to swallow exceptions, true to rethrow them
	 */
	boolean propagateExceptions() default true; // TODO: Make it possible for a EventExecutor to disable the possibility to ignore errors entirely, or to always ignore errors.
}
