package net.zomis.events;

public interface CancellableEvent extends IEvent {
	public boolean isCancelled();
	public void setCancelled(boolean cancel);
}
