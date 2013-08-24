package net.zomis.custommap.view.swing;

// import com.fasterxml.jackson.annotation.JsonProperty;

import net.zomis.custommap.view.general.ViewObject;
/**
 * Target: Android
 * 
 * Represents something that can be drawn on the map
 * Contains an ImageView and a reference to a GameView.
 * 
 * Known subclasses: Overlay
 * @see Overlay
 */
public class MapPaintable implements ViewObject {
	protected transient ISwingGameView view;
	protected transient ImagePanel image;

	protected int x;
	protected int y;
	private transient int size;// why is this a property of a MapPaintable ??
	
	public MapPaintable(ISwingGameView gameView) {
		this.view = gameView;
		//	CustomFacade.getLog().d("Zomis", "MapPaintable-Init");
		if (gameView != null) {
			if (gameView.getLayout() != null) {
				image = new ImagePanel(this);
//				image.setOnTouchListener(this);
//				image.setOnLongClickListener(this);
			}
			else throw new AssertionError("GameView layout is null");
			
			gameView.addViewObject(this);
	//		gameView.addViewToGame(this.getViewToAdd(), true);
			updatePosition();
			this.setSize(gameView.getTileSizeReal());
		}
	}
//	protected void onDraw(Canvas canvas) {}
	
	public int setImageResourceByName(String drawableName) {
//		CustomFacade.getLog().i("Zomis", "setImageResourceByName " + drawableName);
		this.image.setImageFromResourceName(drawableName);
		return 0;
	}
	public ISwingGameView getMap() {
		return view;
	}
	
    public void updatePosition() {
    	if (this.view == null) return;
    	if (this.image == null) return;
//    	CustomFacade.getLog().v("Zomis", String.format("updatePosition: pos (%d, %d) size: %d offset (%d, %d)", this.x, this.y, this.getTileSize(), this.view.getOffsetLeft(), this.view.getOffsetTop()));
    	
        final int left = this.view.getOffsetLeft() + this.x;// this.x and this.y is in pixels, not in tile index. (getModel for tile index)
        final int top = this.view.getOffsetTop() + this.y;
        final int right = left + this.getWidth();
        final int bottom = top + this.getHeight();
      //   CustomFacade.getLog().v("Zomis", String.format("LTRB %d, %d, %d, %d", left, top, right, bottom));
      //  CustomFacade.getLog().v("Zomis", "MapPaintable " + this.getWidth() + ", " + this.getHeight());
        this.image.setBounds(left, top, right - left, bottom - top);
    }

    protected int getTileSize() {
		return this.getMap().getTileSizeReal();
	}
    
/*    
	@Override
	public boolean onTouch(View view, MotionEvent event) {
//		event.setLocation(event.x, y);
		
		//CustomFacade.getLog().v("Zomis", "MapPaintable onTouch " + this.toString() + "; " + event.toString());
	   	this.getMap().onTouch(view, event);// gestureScanner.onTouchEvent(event);
	  // 	this.getMap().onTouch(this.getMap().boardView, event);// gestureScanner.onTouchEvent(event);
		return false;// what happens if true is returned here instead ???
	}
  */
	public void remove() {
		view.removeViewObject(this);
		view = null;
		image = null;
	}

/*	@Override
	public boolean onLongClick(View v) {
		CustomFacade.getLog().i("Zomis", "MapPaintable onLongClick" + MapPaintable.this.x);
		return false;
	}*/
	public int getSize() { return this.size; }
	public int getWidth() { return this.size; }
	public int getHeight() { return this.size; }
	public void setSize(int size) {
		this.size = size;
	}

	@Override
	public Object getViewToAdd() {
		return this.image;
	}
}
