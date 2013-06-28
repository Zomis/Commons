package net.zomis.android;

import android.view.MotionEvent;

public interface IScaleGestureDetector {

	boolean onTouchEvent(MotionEvent event);

	/**
	 * Returns {@code true} if a two-finger scale gesture is in progress.
	 * @return {@code true} if a scale gesture is in progress, {@code false} otherwise.
	 */
	boolean isInProgress();

	/**
	 * Get the X coordinate of the current gesture's focal point.
	 * If a gesture is in progress, the focal point is directly between
	 * the two pointers forming the gesture.
	 * If a gesture is ending, the focal point is the location of the
	 * remaining pointer on the screen.
	 * If {@link #isInProgress()} would return false, the result of this
	 * function is undefined.
	 * 
	 * @return X coordinate of the focal point in pixels.
	 */
	float getFocusX();

	/**
	 * Get the Y coordinate of the current gesture's focal point.
	 * If a gesture is in progress, the focal point is directly between
	 * the two pointers forming the gesture.
	 * If a gesture is ending, the focal point is the location of the
	 * remaining pointer on the screen.
	 * If {@link #isInProgress()} would return false, the result of this
	 * function is undefined.
	 * 
	 * @return Y coordinate of the focal point in pixels.
	 */
	float getFocusY();

	/**
	 * Return the current distance between the two pointers forming the
	 * gesture in progress.
	 * 
	 * @return Distance between pointers in pixels.
	 */
	float getCurrentSpan();

	/**
	 * Return the previous distance between the two pointers forming the
	 * gesture in progress.
	 * 
	 * @return Previous distance between pointers in pixels.
	 */
	float getPreviousSpan();

	/**
	 * Return the scaling factor from the previous scale event to the current
	 * event. This value is defined as
	 * ({@link #getCurrentSpan()} / {@link #getPreviousSpan()}).
	 * 
	 * @return The current scaling factor.
	 */
	float getScaleFactor();

	/**
	 * Return the time difference in milliseconds between the previous
	 * accepted scaling event and the current scaling event.
	 * 
	 * @return Time difference since the last scaling event in milliseconds.
	 */
	long getTimeDelta();

	/**
	 * Return the event time of the current event being processed.
	 * 
	 * @return Current event time in milliseconds.
	 */
	long getEventTime();
}
