package net.zomis.custommap.view.android.drag;


import net.zomis.custommap.view.android.NonLayoutingLayout;

import org.apache.log4j.Logger;

import android.content.Context;
import android.util.AttributeSet;
import android.view.KeyEvent;
import android.view.MotionEvent;
import android.view.View;
/**
 * The implementation of dispatchKeyEvent etc. is needed to make the DragController work properly.
 * @author Zomis
 *
 */
public class DragLayout extends NonLayoutingLayout {
	private static Logger logger = Logger.getLogger(DragLayout.class);
	
	private DragController mDragController;
	public DragLayout(Context context, AttributeSet attrs) {
		super(context, attrs);
	}

	@Override
	public boolean dispatchKeyEvent(KeyEvent event) {
		if (mDragController == null) throw new NullPointerException("Did you forget to call setDragController?");
		return mDragController.dispatchKeyEvent(event)
				|| super.dispatchKeyEvent(event);
	}

	@Override
	public boolean onInterceptTouchEvent(MotionEvent ev) {
		if (mDragController == null) throw new NullPointerException("Did you forget to call setDragController?");
		boolean retur = mDragController.onInterceptTouchEvent(ev);
		logger.trace("DragLayout onInterceptTouchEvent, returning " + retur);
		return retur;
	}

	@Override
	public boolean onTouchEvent(MotionEvent ev) {
//		super.onTouchEvent(ev);// ?
		if (mDragController == null) throw new NullPointerException("Did you forget to call setDragController?");
		boolean retur = mDragController.onTouchEvent(ev);
//		logger.trace("DragLayout onTouchEvent, returning " + retur);
		return retur;
	}

	@Override
	public boolean dispatchUnhandledMove(View focused, int direction) {
		if (mDragController == null) throw new NullPointerException("Did you forget to call setDragController?");
		logger.debug("DragLayout dispatchUnhandledMove");
		return mDragController.dispatchUnhandledMove(focused, direction);
	}

	public void setDragController(DragController dragController) {
		this.mDragController = dragController;
	}
}
