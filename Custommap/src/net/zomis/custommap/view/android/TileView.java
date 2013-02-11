package net.zomis.custommap.view.android;

import net.zomis.custommap.CustomFacade;
import net.zomis.custommap.model.ITileModel;
import net.zomis.custommap.view.general.TileInterface;
import android.view.View;
import android.view.View.OnClickListener;

/**
 * Draw something that represents a TileModel
 * 
 * @see TileModel
 **/
public abstract class TileView<TM extends ITileModel<TM>, MM extends GameView<TM>> extends MapPaintable implements OnClickListener, TileInterface<TM> {
	protected TM model;
	protected final MM mapView;
	
	public MM getMapView() {
		return this.mapView;
	}
	
	/**
	 * 
	 * @param mapView
	 * @param model
	 */
	public TileView(MM mapView, TM model) {
		super(mapView);
		this.mapView = mapView;
		this.model = model;
		
		this.x = this.model.getX() * mapView.tileSize;
		this.y = this.model.getY() * mapView.tileSize;
		this.updatePosition();
		if (this.image != null) {
//			this.image.setOnLongClickListener(this);// MapPaintable does this.
			this.image.setOnClickListener(this);
		}
	}
	
	/**
	 * use getTileSize() instead of getSize() when determining position and size
	 */
	@Override
    public void updatePosition() {
    	if (this.view == null) return;
    	if (this.image == null) return;
    	if (this.model == null) return;
    	
    	this.x = this.getXPos() * this.getTileSize();
    	this.y = this.getYPos() * this.getTileSize();
    	
        // this.x and this.y is in pixels, not in tile index. (xpos and ypos are tile index)

        this.image.layout(this.x, this.y, this.x + this.getTileSize(), this.y + this.getTileSize());
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
	public void reinit(GameView<TM> gridView) {
		this.view = gridView;
	}
	public abstract void update();
	
	public void onClick(View view) {
		if (!this.getMap().getPerformClick()) {
			CustomFacade.getLog().i("Zomis", "Map says: Do not perform click");
			return;
		}
		
		// do not do this if map has scrolled recently
		CustomFacade.getInst().sendNotification(CustomFacade.USER_CLICK_TILE, this);
		
//		CustomFacade.getLog().i("Zomis", "Click tile: " + this);
	}
	
	@Override
	public String toString() {
		return "TileView for " + this.model;
	}
}
