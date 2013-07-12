package net.zomis.custommap.view.android;

import android.widget.ImageView;

public class Overlay extends MapPaintable {
	
	public Overlay(IAndroidGameView g) {
		super(g);
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
	public ImageView getImage() {
		return this.image;
	}
}
