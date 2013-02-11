package net.zomis.custommap.view.android;

import net.zomis.custommap.CustomFacade;

import org.puremvc.java.interfaces.ICommand;
import org.puremvc.java.interfaces.INotification;
import org.puremvc.java.patterns.command.SimpleCommand;

public class CustommapCenterScrollCommand extends SimpleCommand implements ICommand {
	@Override
	public void execute(INotification notification) {
		IAndroidGameView view = null;
		if (notification.getBody() instanceof FlingScrollEvent) {
			FlingScrollEvent event = (FlingScrollEvent) notification.getBody();
			view = event.view;
		}
		else if (notification.getBody() instanceof GameView) {
			view = (IAndroidGameView) notification.getBody();
		}
		else {
			CustomFacade.getLog().e("Unexpected type of notification body: " + notification.getBody());
			return;
		}
		
//		view.updateScrollBounds(true);
		view.getLayout().getWidth();
		
/*		left == right
 * 		x = parentWidth - (x + width)
 *      2x = parentWidth - width
 *      x = (parentWidth - width) / 2
 */
		int scrollToX = (view.getLayout().getWidth() - view.getScrollBounds().width()) / 2;
		int scrollToY = (view.getLayout().getHeight() - view.getScrollBounds().height()) / 2;
		
		CustomFacade.getLog().v(String.format("CenterScroll Bounds: %s", view.getScrollBounds().toString()));
		CustomFacade.getLog().v(String.format("CenterScroll to %d, %d. Width x Height %d %d. TileSize %d", scrollToX, scrollToY, view.getLayout().getWidth(), view.getLayout().getHeight(), view.getTileSizeScaled()));
		view.getLayout().scrollTo(-scrollToX, -scrollToY);
	}
}
