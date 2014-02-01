package net.zomis.custommap.view;

import org.apache.log4j.Layout;
import org.apache.log4j.Level;
import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;
import org.apache.log4j.PatternLayout;

public class Log4jLog extends ZomisLog {
	public static final String DEFAULT_LAYOUT = "[%d{ISO8601}] %5p %c{1} [%t] %m%n";
	public static final String DETAILED_LAYOUT = "[%d{ISO8601}] %5p %c{1} [%t] %l %m%n";
	public static Layout getLayout(String lay) {
		return new PatternLayout(lay == null ? Log4jLog.DEFAULT_LAYOUT : lay);
	}
	
	private Logger	logger;
	private final String	callerFQCN;
	
	@Deprecated
	public Log4jLog() {
		this("Zomis");
	}
	public Log4jLog(String logger) {
		this.logger = LogManager.getLogger(logger);
		this.callerFQCN = ZomisLog.class.getCanonicalName();
	}
	
	
	@Override
	public void v(String tag, String data) {
		logger.log(callerFQCN, Level.TRACE, data, null);
//		logger.trace(data);
	}

	@Override
	public void d(String tag, String data) {
		logger.log(callerFQCN, Level.DEBUG, data, null);
	}

	@Override
	public void i(String tag, String data) {
		logger.log(callerFQCN, Level.INFO, data, null);
	}

	@Override
	public void w(String tag, String data) {
		logger.log(callerFQCN, Level.WARN, data, null);
	}

	@Override
	public void e(String tag, String data) {
		logger.log(callerFQCN, Level.ERROR, data, null);
	}
	@Override
	public void e(String msg, Throwable exception) {
		logger.log(Level.ERROR, msg, exception);
	}
}
