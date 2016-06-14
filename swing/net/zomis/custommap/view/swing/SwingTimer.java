package net.zomis.custommap.view.swing;

import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

import net.zomis.custommap.view.ZomisTimer;

public class SwingTimer extends ZomisTimer {
	public SwingTimer(Integer delay, Runnable runnable) { // constructor needs to be dynamically invoked, that's why it's 'Integer' and not 'int'
		super(delay, runnable);
	}

	protected ScheduledExecutorService _timer;

	@Override
	public void start() {
		if (this.runnable == null)
			return;
		if (this._timer == null) 
			this._timer = Executors.newSingleThreadScheduledExecutor();
		this._timer.scheduleAtFixedRate(this.runnable, this.delay, this.delay, TimeUnit.MILLISECONDS);
        this.running = true;
	}

	@Override
	public void stop() {
		if (this._timer != null) 
			this._timer.shutdownNow();
        this.running = false;
        this._timer = null;
	}

	@Override
	public boolean isRunning() {
		return this.running;
	}
	
	@Override
	public void setDelay(int delay) {
		boolean wasRunning = this.isRunning();
		this.stop();
		super.setDelay(delay);
		if (wasRunning)
			this.start();
	}
}
