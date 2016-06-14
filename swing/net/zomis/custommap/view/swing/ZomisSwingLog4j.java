package net.zomis.custommap.view.swing;

import java.io.File;
import java.io.IOException;

import net.zomis.custommap.view.Log4jLog;

import org.apache.log4j.Appender;
import org.apache.log4j.AsyncAppender;
import org.apache.log4j.ConsoleAppender;
import org.apache.log4j.FileAppender;
import org.apache.log4j.Layout;
import org.apache.log4j.Logger;
import org.apache.log4j.net.SocketAppender;

public class ZomisSwingLog4j {
	
	public static void addSocketAppender(String hostname, int port) {
    	Logger root = Logger.getRootLogger();
    	AsyncAppender async = new AsyncAppender();
    	Appender socket = new SocketAppender(hostname, port);
    	async.addAppender(socket);
    	root.addAppender(async);
	}
	public static void addConsoleAppender(String lay) {
    	Layout layout = Log4jLog.getLayout(lay);
		Logger.getRootLogger().addAppender(new ConsoleAppender(layout));
	}
    public static void addFileAppender(File file, String lay) {
    	if (file != null) {
    		try {
    			Appender fileAppender = new FileAppender(Log4jLog.getLayout(lay), file.getAbsolutePath(), true);
    			Logger.getRootLogger().addAppender(fileAppender);
    		}
    		catch (IOException e) {
    			Logger.getRootLogger().error("IO Exception for logfile " + e);
    		}
    	}
    }
}
