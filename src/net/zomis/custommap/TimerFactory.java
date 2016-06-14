package net.zomis.custommap;

import net.zomis.custommap.view.ZomisTimer;

public interface TimerFactory {
	ZomisTimer createTimer(int delay, Runnable runnable);
}
