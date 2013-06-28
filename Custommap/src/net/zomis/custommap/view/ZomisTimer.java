package net.zomis.custommap.view;

public abstract class ZomisTimer {
	protected Runnable runnable;
	public ZomisTimer(Integer delay, Runnable runnable) {
		this.setDelay(delay);
		this.runnable = runnable;
	}
	protected int delay;
	protected boolean running;
	protected int repeats = -1;
	public void setDelay(int delay) {
		this.delay = delay;
	}
	public abstract boolean isRunning();
	public abstract void start();
	public abstract void stop();
	public void setRepeats(int repeatCounter) {
		this.repeats = repeatCounter;
	}
	public int getRepeats() {
		return repeats;
	}
}
