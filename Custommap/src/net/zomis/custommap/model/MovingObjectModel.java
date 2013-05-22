package net.zomis.custommap.model;

// TODO: Implement MovingObjectModel for: Signalgame - Signals, LoE - Players, Vectorgame - Objects
public abstract class MovingObjectModel {
	protected int x;
	protected int y;
	
//	protected abstract ViewObject getMoveTarget();
	protected abstract int getMoveTargetTicksLeft();
	
	// Only used if getMoveTarget() returns null
	protected abstract float getSpeedX();
	protected abstract float getSpeedY();

	
}
