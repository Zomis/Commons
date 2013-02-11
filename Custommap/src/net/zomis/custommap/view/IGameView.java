package net.zomis.custommap.view;

import net.zomis.custommap.view.general.ViewObject;

public interface IGameView {
	void scroll(float distanceX, float distanceY);
	void addViewObject(ViewObject object);
	void removeViewObject(ViewObject object);
	@Deprecated
	int getTileSize();
	int getTileSizeReal();
	
	
	int getMapWidth();
	int getMapHeight();
	abstract void repaint();
	
	
}
