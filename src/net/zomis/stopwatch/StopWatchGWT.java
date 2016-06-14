package net.zomis.stopwatch;

import java.util.Date;


public class StopWatchGWT implements StopWatch {

	@Override
	public long time() {
		return new Date().getTime();
	}

}
