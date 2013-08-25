package net.zomis.custommap.view.swing;

import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

import net.zomis.custommap.CustomFacade;
import net.zomis.custommap.model.ITileModel;
import net.zomis.custommap.view.general.TileInterface;
import net.zomis.custommap.view.swing.events.CustommapSwingTileClick;

/**
 * common for Pentacolor, Minesweeper Flags Extreme, Signalgame (and more?)
 * handle click
 * draw something
 * 
 * @see TileModel
 **/
public abstract class TileView<TM extends ITileModel<TM>> extends MapPaintable implements TileInterface<TM> {
	protected TM model;
	
	public TileView(ISwingGameView mapView, TM model) {
		super(mapView);
		this.model = model;
		
		this.x = this.model.getX() * mapView.getTileSizeReal();
		this.y = this.model.getY() * mapView.getTileSizeReal();
	//	CustomFacade.getLog().v("TileView", String.format("new tile %d %d - pixel %d %d", this.model.getX(), this.model.getY(), this.x, this.y));
		
		this.updatePosition();
		this.image.addMouseListener(new MouseListener() {
			
			@Override
			public void mouseReleased(MouseEvent arg0) {
			}
			
			@Override
			public void mousePressed(MouseEvent arg0) {
			}
			
			@Override
			public void mouseExited(MouseEvent arg0) {
			}
			
			@Override
			public void mouseEntered(MouseEvent arg0) {
			}
			
			@Override
			public void mouseClicked(MouseEvent event) {
				CustomFacade.getLog().i("Execute click event");
				CustomFacade.getGlobalEvents().executeEvent(new CustommapSwingTileClick(TileView.this, event));
			}
		});
	}
	
	public TM getModel() {
		return this.model;
	}
	public int getX() { return this.x; }
	public int getY() { return this.y; }
	public int getXPos() { return this.model.getX(); }
	public int getYPos() { return this.model.getY(); }

	public abstract void update();
	
}
