package net.zomis.custommap.view.swing;

import java.awt.Graphics;
import javax.swing.JPanel;

import net.zomis.custommap.view.general.ViewObject;

public abstract class MyImageView extends JPanel implements ViewObject {
	private static final long serialVersionUID = -9204348375174860457L;

	protected ISwingGameView view;
	public MyImageView(ISwingGameView map, boolean backGround) {
		super();
		this.view = map;
		if (!backGround)
			map.addViewObject(this);
		else map.addViewToGame(this, backGround);
	}

	public Object getViewToAdd() {
		return this;
	}
	
    
    @Override
    public void paintComponent(Graphics g) {
        super.paintComponent(g);
        this.onDraw(g);
    }

	protected abstract void onDraw(Graphics g);

	public void removeView() {
		this.view.removeViewObject(this);
	}
}
