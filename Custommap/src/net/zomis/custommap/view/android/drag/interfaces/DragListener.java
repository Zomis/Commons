package net.zomis.custommap.view.android.drag.interfaces;

/**
 * Interface to receive notifications when a drag starts or stops
 */
public interface DragListener {

		/**
		 * A drag has begun
		 * 
		 * @param source
		 *            An object representing where the drag originated
		 * @param info
		 *            The data associated with the object that is being dragged
		 * @param dragAction
		 *            The drag action: either
		 *            {@link DragController#DRAG_ACTION_MOVE} or
		 *            {@link DragController#DRAG_ACTION_COPY}
		 */
		public void onDragStart(DragSource source, Object info, int dragAction);

		/**
		 * The drag has eneded
		 */
		public void onDragEnd();
	}

