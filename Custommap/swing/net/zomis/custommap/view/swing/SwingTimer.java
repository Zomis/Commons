package net.zomis.custommap.view.swing;

import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

import net.zomis.custommap.view.ZomisTimer;

public class SwingTimer extends ZomisTimer {
	public SwingTimer(Integer delay, Runnable runnable) {
		super(delay, runnable);
	}

	protected ScheduledExecutorService _timer;

	@Override
	public void start() {
		if (this._timer == null) this._timer = Executors.newSingleThreadScheduledExecutor();
		this._timer.scheduleAtFixedRate(this.runnable, this.delay, this.delay, TimeUnit.MILLISECONDS);
        this.running = true;
	}

	@Override
	public void stop() {
		if (this._timer != null) this._timer.shutdownNow();
        this.running = false;
	}

	@Override
	public boolean isRunning() {
		return this.running;
	}
	

}
