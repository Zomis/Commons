package net.zomis.custommap.view;

public abstract class ZomisTimer {
	protected Runnable runnable;
	public ZomisTimer(Integer delay, Runnable runnable) {
		this.setDelay(delay);
		this.runnable = runnable;
	}
	protected int delay;
	protected boolean running;
	public void setDelay(int delay) {
		this.delay = delay;
	}
	public abstract boolean isRunning();
	public abstract void start();
	public abstract void stop();
}
