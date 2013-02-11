package net.zomis.custommap.view.swing;

import java.util.Timer;
import java.util.TimerTask;

import net.zomis.custommap.CustomFacade;
import net.zomis.custommap.view.ZomisTimer;

import com.fasterxml.jackson.annotation.JsonIgnore;

public class SwingTimer extends ZomisTimer {
	public SwingTimer(Integer delay, Runnable runnable) {
		super(delay, runnable);
	}

	@JsonIgnore protected Timer _timer;
	@JsonIgnore private TimerTask mUpdateTimeTask = new TimerTask() {
		@Override
		public void run() {
	        SwingTimer.this.runnable.run();
			_timer.schedule(this, SwingTimer.this.delay);
		}
	};

	@Override
	public void start() {
		if (this._timer == null) this._timer = new Timer();
		this._timer.schedule(mUpdateTimeTask, this.delay);
        this.running = true;
        CustomFacade.getLog().i("Zomis", "timerStart!");
	}

	@Override
	public void stop() {
		if (this._timer == null) this._timer = new Timer();
		
        this.running = false;
	}

	@Override
	public boolean isRunning() {
		return this.running;
	}
	

}
