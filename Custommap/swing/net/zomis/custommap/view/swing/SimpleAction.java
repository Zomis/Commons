package net.zomis.custommap.view.swing;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.AbstractAction;

public class SimpleAction extends AbstractAction {

	private ActionListener	action;

	public SimpleAction(String text, ActionListener action) {
		super(text);
		this.action = action;
	}
	
	private static final long	serialVersionUID	= 485200727256498045L;

	@Override
	public void actionPerformed(ActionEvent e) {
		this.action.actionPerformed(e);
	}

}
