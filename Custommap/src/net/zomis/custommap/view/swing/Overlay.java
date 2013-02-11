package net.zomis.custommap.view.swing;

import com.fasterxml.jackson.annotation.JsonProperty;

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
	@JsonProperty
	public int getX() {
		return this.x;
	}
	@JsonProperty
	public void setX(int x) {
		this.x = x;
	}
	@JsonProperty
	public int getY() {
		return this.y;
	}
	@JsonProperty
	public void setY(int y) {
		this.y = y;
	}
}
