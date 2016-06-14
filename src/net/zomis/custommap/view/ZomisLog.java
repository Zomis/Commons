package net.zomis.custommap.view;

import net.zomis.custommap.CustomFacade;


public abstract class ZomisLog {
	protected String tag = "Zomis";
	
	@Deprecated
	public abstract void v(String tag, String data);
	
	public abstract void d(String tag, String data);
	
	public abstract void i(String tag, String data);
	@Deprecated
	public abstract void w(String tag, String data);
	@Deprecated
	public abstract void e(String tag, String data);

	public void v(String msg) {
		this.v(this.tag, msg);
	}
	public void d(String msg) {
		this.d(this.tag, msg);
	}
	public void i(String msg) {
		this.i(this.tag, msg);
	}
	public void w(String msg) {
		this.w(this.tag, msg);
	}
	public void e(String msg) {
		this.e(this.tag, msg);
	}
	public abstract void e(String msg, Throwable exception);
	
	public static void debug(Object msg) {
		CustomFacade.getLog().d(msg == null ? "null" : msg.toString());
	}
	public static void info(Object msg) {
		CustomFacade.getLog().i(msg == null ? "null" : msg.toString());
	}
	public static void warn(Object msg) {
		CustomFacade.getLog().w(msg == null ? "null" : msg.toString());
	}
	public static void error(Object msg, Throwable e) {
		CustomFacade.getLog().e(String.valueOf(msg), e);
	}
}
