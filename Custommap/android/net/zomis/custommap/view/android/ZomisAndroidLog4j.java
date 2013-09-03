package net.zomis.custommap.view.android;

import java.io.File;
import java.io.IOException;

import org.apache.log4j.Appender;
import org.apache.log4j.AsyncAppender;
import org.apache.log4j.FileAppender;
import org.apache.log4j.Layout;
import org.apache.log4j.Level;
import org.apache.log4j.Logger;
import org.apache.log4j.PatternLayout;
import org.apache.log4j.net.SocketAppender;

import android.os.Environment;

public class ZomisAndroidLog4j {
	private static final String DEFAULT_LAYOUT = "[%d{ISO8601}] %5p %c{1} [%t] %m%n";
	public static void addSocketAppender(String hostname, int port) {
    	Logger root = Logger.getRootLogger();
    	AsyncAppender async = new AsyncAppender();
    	Appender socket = new SocketAppender(hostname, port);
    	async.addAppender(socket);
    	root.addAppender(async);
	}
    public static void log4jConfigure(Level defaultLevel) {
    	Logger root = Logger.getRootLogger();
    	Layout layout = new PatternLayout(DEFAULT_LAYOUT);
    	root.addAppender(new LogCatAppender(layout));
    	root.setLevel(defaultLevel);
    }
    public static void addFileAppender(String filename) {
    	if (filename != null) {
    		try {
    			Appender file = new FileAppender(new PatternLayout(DEFAULT_LAYOUT), new File(Environment.getExternalStorageDirectory(), filename).getAbsolutePath(), true);
    			Logger.getRootLogger().addAppender(file);
    		}
    		catch (IOException e) {
    			Logger.getRootLogger().error("IO Exception for logfile " + e);
    		}
    	}
    }
}
