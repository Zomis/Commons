package net.zomis.custommap.controller;

import net.zomis.custommap.CustomFacade;
import net.zomis.custommap.view.android.FlingScrollEvent;
import net.zomis.custommap.view.android.IAndroidGameView;
import net.zomis.custommap.view.android.NonLayoutingLayout;

import org.puremvc.java.interfaces.ICommand;
import org.puremvc.java.interfaces.INotification;
import org.puremvc.java.patterns.command.SimpleCommand;

import android.view.View;
import android.widget.ImageView;


/**
 * This class should send a message to a Proxy telling the Model to update.
 * Then either the Proxy or the Command itself (probably the Proxy?) should send a notification to the corresponding Mediator, telling it to update.
 * 
 * @author Zomis
 *
 */
@Deprecated
public class CustommapScrollCommand extends SimpleCommand implements ICommand {
	public void execute(INotification notification) {
    	FlingScrollEvent fse = (FlingScrollEvent) notification.getBody();
    	IAndroidGameView view = fse.view;
    	
    	View last = view.getLastTouchedView();
    	if (last instanceof NonLayoutingLayout) {
    		CustomFacade.getLog().v("Zomis", "Scroll like an NonLayoutingLayout");
        	view.scroll(fse.velocityX, fse.velocityY);
    	}
    	else if (last instanceof ImageView) {
    		CustomFacade.getLog().v("Zomis", "Scroll like an ImageView");
    		view.scroll(fse.e1.getX() - fse.e2.getX(), fse.e1.getY() - fse.e2.getY());// very good on a TileView, scrolling nothing or a lot on the view itself
    	}
    	else {
    		CustomFacade.getLog().e("Zomis", "Unexpected last view, using default (same as ImageView): " + last.toString());
    		view.scroll(fse.e1.getX() - fse.e2.getX(), fse.e1.getY() - fse.e2.getY());// very good on a TileView, scrolling nothing or a lot on the view itself
    	}
    	
//		SignalmapMediator.getSignalMapView().updateGatePositions();
	}
}