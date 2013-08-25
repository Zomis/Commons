package net.zomis.custommap.view.swing;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import javax.swing.JComponent;
import javax.swing.JPanel;

import net.zomis.custommap.CustomFacade;
import net.zomis.custommap.model.GenericMapModel;
import net.zomis.custommap.model.ITileModel;
import net.zomis.custommap.view.general.TileInterface;
import net.zomis.custommap.view.general.ViewContainer;
import net.zomis.custommap.view.general.ViewObject;
/**
 * Should this class extend a PureMVC Observer somehow?
 * @author Zomis
 *
 */
public abstract class GameView<TM extends ITileModel<TM>> extends ViewContainer<TM> implements ISwingGameView {// extends ViewGroup, or View?
	public transient JComponent boardView;
	
	protected GenericMapModel<TM> mapModel;
	public GenericMapModel<TM> getMapModel() { return this.mapModel; }
	public JComponent getLayout() {
		return this.boardView;
	}
	
	public int spacing = 0;
	protected int tileSize = 64;
	public int getTileSize() { return this.tileSize; }
	public int getTileSizeReal() { return this.tileSize; }
	public void setTileSize(int tileSize) { this.tileSize = tileSize; this.repaint(); }
	public int bgColor = 0xFF000000;
	
	public List<TileInterface<TM>> map;// test with non-transient

	protected float scaleFactorMin = 0.1f;
	protected float scaleFactorMax = 10.0f;
	
	private int offsetLeft;
	private int offsetTop;
	
	public GameView(JPanel view, GenericMapModel<TM> model) {
		this.mapModel = model;
		this.boardView = view;
        
        this.map = new ArrayList<TileInterface<TM>>();
        if (model.hasMap()) {
        	Iterator<TM> it = model.iterator();
        	while (it.hasNext()) {
        		TM tv = it.next();
       			map.add(newTileView(this, tv));
       		}
        	newTileView(this, this.getMapModel().pos(0, 0));// workaround for strange Swing-bug, the last tile added is placed behind everything...
        }
	}
	
	public abstract TileInterface<TM> newTileView(ISwingGameView view, TM model);
	
	public void initTileSize(int parentWidth, int parentHeight, int orientation) {
		tileSize = Math.min(parentWidth, parentHeight) / Math.max(this.mapModel.getMapWidth(), this.mapModel.getMapHeight());
    	CustomFacade.getLog().i(String.format("initMap: %d x %d. View size %d x %d. Orientation %d. Tile size %d", 
    			this.mapModel.getMapWidth(), this.mapModel.getMapHeight(), 
    				parentWidth, parentHeight,
    					orientation, tileSize));
    	this.repaint();
	}
	
	public abstract void repaint();
	
	public int getOffsetLeft() {
		return this.offsetLeft;
	}
	public int getOffsetTop() {
		return this.offsetTop;
	}

	public void addViewToGame(Object image, boolean backGround) {
		if (image == null) return;
		if (boardView == null) return;
		
		if (backGround)
			boardView.add((JComponent) image, 0);
		else boardView.add((JComponent) image);
	}
	@Deprecated
	public void scroll(float distanceX, float distanceY) {
		CustomFacade.getLog().v(String.format("Scroll %f, %f", distanceX, distanceY));
		
		this.offsetLeft -= distanceX;
		this.offsetTop -= distanceY;
		
		if (this.offsetLeft < -150) this.offsetLeft = -150;
		if (this.offsetLeft > 200) this.offsetLeft = 200;
		
		if (this.offsetTop < -150) this.offsetTop = -150;
		if (this.offsetTop > 200) this.offsetTop = 200;

		this.repaint();
		for (TileInterface<TM> tv : map) {
			tv.updatePosition();
		}
	}
	
    public void addViewObject(ViewObject object) {
    	if (object.getViewToAdd() == null) CustomFacade.getLog().e("view object is null");
    	if (!(object.getViewToAdd() instanceof JComponent)) CustomFacade.getLog().e("GameView ViewObject is wrong: " + object.toString());
		if (boardView != null) boardView.add((JComponent) object.getViewToAdd());
		
    	
    }
    public void removeViewObject(ViewObject object) {
    	if (object == null) CustomFacade.getLog().e("view object is null");
    	if (!(object.getViewToAdd() instanceof JComponent)) CustomFacade.getLog().e("GameView ViewObject is wrong: " + object.toString());
		if (boardView != null) boardView.remove((JComponent) object.getViewToAdd());
    }

    
}