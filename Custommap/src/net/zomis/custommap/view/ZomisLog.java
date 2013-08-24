package net.zomis.custommap.view;

import net.zomis.custommap.CustomFacade;

public abstract class ZomisLog {
	@Deprecated
	public abstract void v(String tag, String data);
	
	public abstract void d(String tag, String data);
	
	public abstract void i(String tag, String data);
	@Deprecated
	public abstract void w(String tag, String data);
	@Deprecated
	public abstract void e(String tag, String data);

	public void v(String msg) {
		this.v(CustomFacade.getInst().getTag(), msg);
	}
	public void d(String msg) {
		this.d(CustomFacade.getInst().getTag(), msg);
	}
	public void i(String msg) {
		this.i(CustomFacade.getInst().getTag(), msg);
	}
	public void w(String msg) {
		this.w(CustomFacade.getInst().getTag(), msg);
	}
	public void e(String msg) {
		this.e(CustomFacade.getInst().getTag(), msg);
	}
}
