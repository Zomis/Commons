package net.zomis.custommap.view.swing;

import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

import net.zomis.custommap.CustomFacade;
import net.zomis.custommap.model.ITileModel;
import net.zomis.custommap.view.general.TileInterface;

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
				String modifiers = "";
				if (event.isControlDown()) modifiers += "CTRL;";
				if (event.isAltDown()) modifiers += "ALT;";
				if (event.isShiftDown()) modifiers += "SHIFT;";
				
				CustomFacade.getInst().sendNotification(CustomFacade.USER_CLICK_TILE, TileView.this, modifiers);
				
//				CustomFacade.getLog().i("Zomis", "Click tile: " + TileView.this.getModel().toString());
//				CustomFacade.getLog().d("Zomis", String.format("Click details: %d, %d", TileView.this.y, TileView.this.getHeight()));
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

	/**
	 * Re-initialize the view.
	 * Does not update the image resource, that needs to be got from the corresponding model.
	 * @param gridView
	 */
	@Deprecated
	public void reinit(ISwingGameView gridView) {
		this.view = gridView;
	}
	public abstract void update();
	
}
