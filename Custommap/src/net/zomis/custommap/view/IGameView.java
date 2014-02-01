package net.zomis.custommap.view;

import net.zomis.custommap.view.general.ViewObject;

public interface IGameView {
	void addViewObject(ViewObject object);
	void removeViewObject(ViewObject object);
	int getTileSizeReal();
	void repaint();
}
