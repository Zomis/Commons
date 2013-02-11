package net.zomis.custommap.view.android;

import android.widget.ImageView;

import com.fasterxml.jackson.annotation.JsonProperty;

public class Overlay extends MapPaintable {
	
	public Overlay(IAndroidGameView g) {
		super(g);
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
	public ImageView getImage() {
		return this.image;
	}
}
