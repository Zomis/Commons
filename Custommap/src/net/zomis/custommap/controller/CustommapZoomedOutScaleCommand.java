package net.zomis.custommap.controller;

import net.zomis.custommap.view.android.IAndroidGameView;

import org.puremvc.java.interfaces.ICommand;
import org.puremvc.java.interfaces.INotification;
import org.puremvc.java.patterns.command.SimpleCommand;

public class CustommapZoomedOutScaleCommand extends SimpleCommand implements ICommand {
	@Override
	public void execute(INotification notification) {
		IAndroidGameView view = (IAndroidGameView) notification.getBody();
		view.setZoom(view.getMinScaleFactor());
		// view.resize() is automatically called after this command.
	}
}
