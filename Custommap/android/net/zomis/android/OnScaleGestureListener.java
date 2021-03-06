package net.zomis.android;

/**
 * The listener for receiving notifications when gestures occur.
 * If you want to listen for all the different gestures then implement
 * this interface. If you only want to listen for a subset it might
 * be easier to extend {@link SimpleOnScaleGestureListener}.
 * 
 * An application will receive events in the following order:
 * <ul>
 *  <li>One {@link OnScaleGestureListener#onScaleBegin(IScaleGestureDetector)}
 *  <li>Zero or more {@link OnScaleGestureListener#onScale(IScaleGestureDetector)}
 *  <li>One {@link OnScaleGestureListener#onScaleEnd(IScaleGestureDetector)}
 * </ul>
 */
public interface OnScaleGestureListener {
    /**
     * Responds to scaling events for a gesture in progress.
     * Reported by pointer motion.
     * 
     * @param detector The detector reporting the event - use this to
     *          retrieve extended info about event state.
     * @return Whether or not the detector should consider this event
     *          as handled. If an event was not handled, the detector
     *          will continue to accumulate movement until an event is
     *          handled. This can be useful if an application, for example,
     *          only wants to update scaling factors if the change is
     *          greater than 0.01.
     */
    public boolean onScale(IScaleGestureDetector detector);

    /**
     * Responds to the beginning of a scaling gesture. Reported by
     * new pointers going down.
     * 
     * @param detector The detector reporting the event - use this to
     *          retrieve extended info about event state.
     * @return Whether or not the detector should continue recognizing
     *          this gesture. For example, if a gesture is beginning
     *          with a focal point outside of a region where it makes
     *          sense, onScaleBegin() may return false to ignore the
     *          rest of the gesture.
     */
    public boolean onScaleBegin(IScaleGestureDetector detector);

    /**
     * Responds to the end of a scale gesture. Reported by existing
     * pointers going up.
     * 
     * Once a scale has ended, {@link IScaleGestureDetector#getFocusX()}
     * and {@link IScaleGestureDetector#getFocusY()} will return the location
     * of the pointer remaining on the screen.
     * 
     * @param detector The detector reporting the event - use this to
     *          retrieve extended info about event state.
     */
    public void onScaleEnd(IScaleGestureDetector detector);
}
