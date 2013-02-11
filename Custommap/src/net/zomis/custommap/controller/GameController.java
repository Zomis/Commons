package net.zomis.custommap.controller;

import org.puremvc.java.interfaces.ICommand;
import org.puremvc.java.interfaces.INotification;
import org.puremvc.java.patterns.command.SimpleCommand;

/**
 * This class should send a message to a Proxy telling the Model to update.
 * Then either the Proxy or the Command itself (probably the Proxy?) should send a notification to the corresponding Mediator, telling it to update.
 * 
 * @author Zomis
 *
 */
@Deprecated
public class GameController extends SimpleCommand implements ICommand {
	public void execute(INotification notification) {
    //	Log.i("PureMVC", "GameController execute");
//    	MapModel mm = (MapModel) notification.getBody();
//    	mm.onTick();
//    	GameView gv = (GameView) notification.getBody();
//    	gv.getMapModel().onTick();
	}
}