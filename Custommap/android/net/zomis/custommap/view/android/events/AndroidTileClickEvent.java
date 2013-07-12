package net.zomis.custommap.view.android.events;

import net.zomis.custommap.view.android.TileView;
import net.zomis.events.BaseEvent;

public class AndroidTileClickEvent extends BaseEvent {

	private TileView<?, ?>	view;

	public AndroidTileClickEvent(TileView<?, ?> tileView) {
		this.view = tileView;
	}
	public TileView<?, ?> getView() {
		return view;
	}

}
