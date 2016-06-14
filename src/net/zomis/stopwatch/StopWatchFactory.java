package net.zomis.stopwatch;

import java.util.concurrent.TimeUnit;

public class StopWatchFactory {

	private static final StopWatch gwt = new StopWatchGWT();
	private static final StopWatch system = new StopWatchNano();
	
	public static StopWatchI startGWT() {
		StopWatchI watch = new StopWatchI(gwt, TimeUnit.MILLISECONDS);
		watch.start();
		return watch;
	}
	
	public static StopWatchI startSystem() {
		StopWatchI watch = new StopWatchI(system, TimeUnit.NANOSECONDS);
		watch.start();
		return watch;
	}
	
}
