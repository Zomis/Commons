package net.zomis.custommap;

import net.zomis.events.IEventExecutor;

public interface EventFactory {
	IEventExecutor createEventExecutor();
}
