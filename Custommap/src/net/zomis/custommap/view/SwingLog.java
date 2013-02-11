package net.zomis.custommap.view;

import java.util.logging.Level;

public class SwingLog extends ZomisLog {
	@Override
	public void v(String tag, String data) {
		Log(Level.FINER, tag, data);
	}

	@Override
	public void d(String tag, String data) {
		Log(Level.FINE, tag, data);
	}

	@Override
	public void i(String tag, String data) {
		Log(Level.INFO, tag, data);
	}

	@Override
	public void w(String tag, String data) {
		Log(Level.WARNING, tag, data);
	}

	@Override
	public void e(String tag, String data) {
		Log(Level.SEVERE, tag, data);
	}

	public void Log(Level type, String tag, String str) {
//		Logger log = Logger.getLogger(tag);
		
		if (str.isEmpty())
			System.out.println(type + " ");
		else System.out.println(type + " " + String.format("[%s] %s", Thread.currentThread().getName() + ": " + tag, str));
	}
}
