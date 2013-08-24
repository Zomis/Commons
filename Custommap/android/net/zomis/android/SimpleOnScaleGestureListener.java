package net.zomis.android;

/**
 * A convenience class to extend when you only want to listen for a subset
 * of scaling-related events. This implements all methods in
 * {@link OnScaleGestureListener} but does nothing.
 * {@link OnScaleGestureListener#onScale(IScaleGestureDetector)} returns
 * {@code false} so that a subclass can retrieve the accumulated scale
 * factor in an overridden onScaleEnd.
 * {@link OnScaleGestureListener#onScaleBegin(IScaleGestureDetector)} returns
 * {@code true}. 
 */
public class SimpleOnScaleGestureListener implements OnScaleGestureListener {

    public boolean onScale(IScaleGestureDetector detector) {
        return false;
    }

    public boolean onScaleBegin(IScaleGestureDetector detector) {
        return true;
    }

    public void onScaleEnd(IScaleGestureDetector detector) {
        // Intentionally empty
    }
}
