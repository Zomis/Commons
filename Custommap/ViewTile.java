package net.zomis.custommap.view.general;

import net.zomis.custommap.model.TileModel;

@Deprecated
public interface ViewTile extends ViewObject {
	public TileModel getModel();
	public ViewContainer getMap();
	public int getX();
	public int getY();
	public int getXPos();
	public int getYPos();
	public void update();
	public void updatePosition();
}
