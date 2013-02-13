package net.zomis.custommap.view.android;

import net.zomis.custommap.CustomFacade;
import net.zomis.custommap.view.ZomisTimer;
import android.os.Handler;

import com.fasterxml.jackson.annotation.JsonIgnore;

public class AndroidTimer extends ZomisTimer {
	public AndroidTimer(Integer delay, Runnable runnable) {
		super(delay, runnable);
	}

	@JsonIgnore protected Handler _timer;
	@JsonIgnore private Runnable mUpdateTimeTask = new Runnable() {
		public void run() {
	        AndroidTimer.this.runnable.run();
			_timer.postDelayed(this, AndroidTimer.this.delay); // this = Runnable
		}
	};

	@Override
	public void start() {
		if (this._timer == null) this._timer = new Handler();
		
		this._timer.removeCallbacks(mUpdateTimeTask);
		this._timer.postDelayed(mUpdateTimeTask, this.delay);
        this.running = true;
        CustomFacade.getLog().i("Zomis", "timerStart!");
	}

	@Override
	public void stop() {
		if (_timer == null) _timer = new Handler();
        _timer.removeCallbacks(mUpdateTimeTask);
        this.running = false;
	}

	@Override
	public boolean isRunning() {
		return this.running;
	}
}