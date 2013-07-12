package net.zomis.custommap.view.android.drag;


import android.content.Context;
import android.util.AttributeSet;
import android.util.Log;
import android.view.KeyEvent;
import android.view.MotionEvent;
import android.view.View;
import net.zomis.custommap.view.android.NonLayoutingLayout;
/**
 * The implementation of dispatchKeyEvent etc. is needed to make the DragController work properly.
 * @author Zomis
 *
 */
public class DragLayout extends NonLayoutingLayout {

	private DragController mDragController;
	public DragLayout(Context context, AttributeSet attrs) {
		super(context, attrs);
	}

	@Override
	public boolean dispatchKeyEvent(KeyEvent event) {
		if (mDragController == null) throw new NullPointerException("Did you forget to call setDragController?");
		Log.d("Zomis", "DragLayout dispatchKeyEvent");
		return mDragController.dispatchKeyEvent(event)
				|| super.dispatchKeyEvent(event);
	}

	@Override
	public boolean onInterceptTouchEvent(MotionEvent ev) {
		
//		super.onTouchEvent(ev);// ?
		if (mDragController == null) throw new NullPointerException("Did you forget to call setDragController?");
		boolean retur = mDragController.onInterceptTouchEvent(ev);
		Log.d("Zomis", "DragLayout onInterceptTouchEvent, returning " + retur);
		return retur;
	}

	@Override
	public boolean onTouchEvent(MotionEvent ev) {
//		super.onTouchEvent(ev);// ?
		if (mDragController == null) throw new NullPointerException("Did you forget to call setDragController?");
		boolean retur = mDragController.onTouchEvent(ev);
//		Log.v("Zomis", "DragLayout onTouchEvent, returning " + retur);
		return retur;
	}

	@Override
	public boolean dispatchUnhandledMove(View focused, int direction) {
		if (mDragController == null) throw new NullPointerException("Did you forget to call setDragController?");
		Log.d("Zomis", "DragLayout dispatchUnhandledMove");
		return mDragController.dispatchUnhandledMove(focused, direction);
	}

	public void setDragController(DragController dragController) {
		this.mDragController = dragController;
	}
}
