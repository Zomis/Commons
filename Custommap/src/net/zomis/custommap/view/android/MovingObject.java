package net.zomis.custommap.view.android;

import net.zomis.custommap.model.MovingObjectModel;

import org.puremvc.java.interfaces.IFunction;
import org.puremvc.java.interfaces.INotification;
import org.puremvc.java.interfaces.IObserver;

/**
 * @author Zomis
 * @deprecated Not in use yet.
 */
@Deprecated
public class MovingObject extends Overlay implements IObserver, IFunction {

	private MovingObjectModel model;
	public MovingObject(IAndroidGameView g, MovingObjectModel model) {
		super(g);
		this.model = model;
//		View.getInstance().registerObserver(CustomFacade.GAME_TIMER, this);
	}
	
//	protected abstract ViewObject getMoveTarget();
//	protected abstract int getMoveTargetTicksLeft();
	
	// Only used if getMoveTarget() returns null
//	protected abstract float getSpeedX();
//	protected abstract float getSpeedY();
	
	public void move() {
		
	}

	@Override
	public void setNotifyMethod(IFunction notifyMethod) {
	}

	@Override
	public void setNotifyContext(Object notifyContext) {
		this.model = (MovingObjectModel) notifyContext;
	}

	@Override
	public void notifyObserver(INotification notification) {
	}

	@Override
	public boolean compareNotifyContext(Object object) {
		return (this.model == object);
	}

	@Override
	public void onNotification(INotification notification) {
		if (this.compareNotifyContext(notification.getBody())) this.move();
	}
	
}
