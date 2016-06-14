package net.zomis.android.incomplete;

public class ZoomModule {

	// TODO: Fix proper Zoom functionality in GameViews.
//	private final OnScaleGestureListener mScaleGestureListener = new SimpleOnScaleGestureListener() {
//		private float	focusX;
//		private float	focusY;
//		@Override
//		public boolean onScale(IScaleGestureDetector detector) {
//			CustomFacade.getLog().i("----------------------------------------------------------");
//			PentacolorDoubleTap.scale(PentacolorMapView.this, detector.getScaleFactor(), detector.getFocusX(), detector.getFocusY());
////			PentacolorDoubleTap.scale(PentacolorMapView.this, detector.getScaleFactor(), this.focusX, this.focusY);
//
///*
//	obekanta: postScrollX, postScrollY
//	bekanta: focusPointX, focusPointY, preZoom, postZoom
//
//pre zoom focus pos = post zoom focus pos
//pre zoom focus pos = preScroll pos + fp pos
//post zoom focus pos = pre zoom focus pos * zoom factor change
//correction on scroll = post zoom focus pos - pre zoom focus pos
//*/
//			return true;
//		}
//		@Override
//		public boolean onScaleBegin(IScaleGestureDetector detector) {
//			CustomFacade.getLog().i("Scale begin: " + detector.getFocusX() + ", " + detector.getFocusY());
//			this.focusX = detector.getFocusX() / PentacolorMapView.this.getScaleFactor();
//			this.focusY = detector.getFocusY() / PentacolorMapView.this.getScaleFactor();
//			return super.onScaleBegin(detector);
//		}
//	};

}
