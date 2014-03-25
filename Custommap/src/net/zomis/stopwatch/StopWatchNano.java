package net.zomis.stopwatch;

public class StopWatchNano implements StopWatch {

	@Override
	public long time() {
		return System.nanoTime();
	}

	
}
