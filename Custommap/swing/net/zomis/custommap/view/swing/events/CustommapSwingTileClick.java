package net.zomis.custommap.view.swing.events;

import java.awt.event.MouseEvent;

import net.zomis.custommap.view.swing.TileView;
import net.zomis.events.BaseEvent;

public class CustommapSwingTileClick extends BaseEvent {

	private TileView<?>	tile;
	private MouseEvent	event;

	public CustommapSwingTileClick(TileView<?> tileView, MouseEvent event) {
		this.tile = tileView;
		this.event = event;
	}
	public MouseEvent getEvent() {
		return event;
	}
	public TileView<?> getTile() {
		return tile;
	}
	

}
