package net.zomis.custommap.view.android;

import net.zomis.custommap.CustomFacade;
import android.view.ViewGroup;
import android.view.ViewParent;

public class CustommapCenterScrollCommand {
	public static float center(float width, float parentWidth) {
		return (width - parentWidth) / 2;
	}
	public static void centerScroll(IAndroidGameView view) {
		centerScroll(view, false);
	}
	public static void centerScroll(IAndroidGameView view, boolean useParentSize) {
		
//		view.updateScrollBounds(true);
//		view.getLayout().getWidth();
		
/*		left == right --> x == right --> x == parentWidth - (x + width)
 *      2x = parentWidth - width
 *      x = (parentWidth - width) / 2
 */
		ViewGroup grpSize = view.getLayout();
		ViewParent parent = grpSize.getParent();
		if (useParentSize && parent instanceof ViewGroup) {
			grpSize = (ViewGroup) grpSize.getParent();
		}
		
		int scrollToX = (grpSize.getWidth()  - view.getScrollBounds().width()) / 2;
		int scrollToY = (grpSize.getHeight() - view.getScrollBounds().height()) / 2;
		
		CustomFacade.getLog().d(String.format("CenterScroll Bounds: %s", view.getScrollBounds().toString()));
		CustomFacade.getLog().d(String.format("CenterScroll to %d, %d. Width x Height %d %d. TileSize %d", scrollToX, scrollToY, grpSize.getWidth(), grpSize.getHeight(), 
				view.getTileSizeScaled()));
		view.getLayout().scrollTo(-scrollToX, -scrollToY);
		
	}
	
	
}
