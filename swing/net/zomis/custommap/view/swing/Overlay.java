package net.zomis.custommap.view.swing;


public class Overlay extends MapPaintable {
	
	public Overlay(ISwingGameView gameView) {
		super(gameView);
	}
	public Overlay(ISwingGameView gameView, boolean background) {
		super(gameView);
		if (gameView != null) {
			gameView.removeViewObject(this);
			gameView.addViewToGame(this.getViewToAdd(), !background);// swing bug is causing background = true to be false... 
		}
	}
	public int getX() {
		return this.x;
	}
	public void setX(int x) {
		this.x = x;
	}
	public int getY() {
		return this.y;
	}
	public void setY(int y) {
		this.y = y;
	}
}
