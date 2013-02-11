package net.zomis.custommap.view.android;

import net.zomis.custommap.CustomFacade;
import android.content.Context;
import android.util.AttributeSet;
import android.view.ViewGroup;

/**
 * A simple layout that do not do any layouting on its own. It can be used as
 * the view for the board on which we layout all the pieces manually.
 * 
 * 
 */
public class NonLayoutingLayout extends ViewGroup {
    public NonLayoutingLayout(Context context, AttributeSet attrs) {
        super(context, attrs);
    }
    public NonLayoutingLayout(Context context) {
        super(context);
    }
/*
    @Override
    public boolean onInterceptTouchEvent(MotionEvent event) {
    	// http://developer.android.com/reference/android/view/ViewGroup.html#onInterceptTouchEvent%28android.view.MotionEvent%29
    	return false;
    }
*/    
    
    @Override
    protected void onLayout(boolean changed, int l, int t, int r, int b) {
        // No layouting.
    }

    /**
     * When this method is called, we know the size of the game field which
     * is needed to compute the geometry of the tiles. Now we can load the
     * game that depends on that geometry.
     */
    @Override
    protected void onSizeChanged(int width, int height, int oldWidth, int oldHeight) {
    	// game.onLoadGame();
    	if (CustomFacade.getInst() == null) {
    		throw new NullPointerException("CustomFacade not initialized when layout has it's size.");
    	}
        CustomFacade.getInst().sendNotification(CustomFacade.GAME_INIT, this);// also sends it as a command
    }
}
