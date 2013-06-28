package net.zomis.events;

public interface CancellableEvent {
	public boolean isCancelled();
	public void setCancelled(boolean cancel);
}
