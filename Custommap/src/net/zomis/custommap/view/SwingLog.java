package net.zomis.custommap.view;

import java.util.logging.Level;

public class SwingLog extends ZomisLog {
	@Override
	public void v(String tag, String data) {
		log(Level.FINER, tag, data);
	}

	@Override
	public void d(String tag, String data) {
		log(Level.FINE, tag, data);
	}

	@Override
	public void i(String tag, String data) {
		log(Level.INFO, tag, data);
	}

	@Override
	public void w(String tag, String data) {
		log(Level.WARNING, tag, data);
	}

	@Override
	public void e(String tag, String data) {
		log(Level.SEVERE, tag, data);
	}

	public void log(Level type, String tag, String str) {
		if (str.isEmpty())
			System.out.println(type + " ");
		else System.out.println(type + " " + String.format("[%s] %s", Thread.currentThread().getName() + ": " + tag, str));
	}

	@Override
	public void e(String msg, Throwable exception) {
		i(msg);
		exception.printStackTrace();
	}
}
