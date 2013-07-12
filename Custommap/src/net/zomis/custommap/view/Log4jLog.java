package net.zomis.custommap.view;

import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;

public class Log4jLog extends ZomisLog {
	private static Logger logger = LogManager.getLogger("Zomis");
	
	@Override
	public void v(String tag, String data) {
		logger.trace(data);
	}

	@Override
	public void d(String tag, String data) {
		logger.debug(data);
	}

	@Override
	public void i(String tag, String data) {
		logger.info(data);
	}

	@Override
	public void w(String tag, String data) {
		logger.warn(data);
	}

	@Override
	public void e(String tag, String data) {
		logger.error(data);
	}
}
