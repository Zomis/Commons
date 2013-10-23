package net.zomis.events;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * Indication that a method should handle a {@link BaseEvent}
 * 
 * The method needs to return {@link Void} and expect exactly one parameter of a type that extends {@link BaseEvent}.
 */
@Retention(value = RetentionPolicy.RUNTIME)
@Target(ElementType.METHOD)
public @interface Event {
	int priority() default 50;
}
