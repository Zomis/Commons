package net.zomis.stopwatch;

import java.util.concurrent.TimeUnit;

public class StopWatchI {

	private StopWatch	time;
	private TimeUnit	timeUnit;
	private long	startAt;
	private long	stopAt;

	public StopWatchI(StopWatch timeHelp, TimeUnit timeUnit) {
		this.time = timeHelp;
		this.timeUnit = timeUnit;
	}
	
	public void start() {
		this.startAt = time.time();
	}
	
	public void stop() {
		this.stopAt = time.time();
	}
	
	public long getTimeInMillis() {
		if (startAt == 0 || stopAt == 0 || stopAt < startAt)
			throw new IllegalStateException("Timer not completed properly.");
		
		long time = stopAt - startAt;
		return TimeUnit.MILLISECONDS.convert(time, timeUnit);
	}
	
}
