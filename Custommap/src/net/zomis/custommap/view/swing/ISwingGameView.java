package net.zomis.custommap.view.swing;

import javax.swing.JComponent;

import net.zomis.custommap.view.IGameView;

public interface ISwingGameView extends IGameView {
	JComponent getLayout();
	int getOffsetLeft();
	int getOffsetTop();
	void addViewToGame(Object image, boolean backGround);
}
