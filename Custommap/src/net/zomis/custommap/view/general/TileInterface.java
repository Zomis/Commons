package net.zomis.custommap.view.general;

import net.zomis.custommap.view.IGameView;

public interface TileInterface<TM> extends ViewObject {
	public TM getModel();
	public IGameView getMap();
	public int getX();
	public int getY();
	public int getXPos();
	public int getYPos();
	public void update();
	public void updatePosition();
}
