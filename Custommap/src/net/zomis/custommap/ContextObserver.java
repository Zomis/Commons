package net.zomis.custommap;

import org.puremvc.java.interfaces.IFunction;
import org.puremvc.java.interfaces.INotification;
import org.puremvc.java.patterns.observer.Observer;

@Deprecated
public abstract class ContextObserver extends Observer {
	public ContextObserver(IFunction notify, Object context) {
		super(notify, context);
	}

	@Override
	public void notifyObserver( INotification notification ) {
		if (this.shouldNotify(notification.getBody()) || compareNotifyContext(notification.getBody())) {
			// Should OR be used in this if-statement?
			super.notifyObserver(notification);
		}
	}

	public abstract boolean shouldNotify(Object context);
}
