package net.zomis.custommap.view.android;

import net.zomis.custommap.CustomFacade;
import net.zomis.custommap.view.ZomisTimer;
import android.os.Handler;

public class AndroidTimer extends ZomisTimer {
	public AndroidTimer(Integer delay, Runnable runnable) {
		super(delay, runnable);
	}

	protected Handler _timer;
	private Runnable mUpdateTimeTask = new Runnable() {
		public void run() {
			if (_timer == null) return;
			if (getRepeats() != 0) {
		        AndroidTimer.this.runnable.run();
		        if (repeats > 0) --repeats;
		        if (_timer != null && getRepeats() != 0) _timer.postDelayed(this, AndroidTimer.this.delay); // this = Runnable
			}
			else stop();
		}
	};

	@Override
	public void start() {
		if (this._timer == null) this._timer = new Handler();
		
		this._timer.removeCallbacks(mUpdateTimeTask);
		this._timer.postDelayed(mUpdateTimeTask, this.delay);
        this.running = true;
        CustomFacade.getLog().i("timerStart!");
	}

	@Override
	public void stop() {
		if (_timer != null) _timer.removeCallbacks(mUpdateTimeTask);
		_timer = null;
        this.running = false;
	}

	@Override
	public boolean isRunning() {
		return this.running;
	}
}
