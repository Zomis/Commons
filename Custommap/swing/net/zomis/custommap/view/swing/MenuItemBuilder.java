package net.zomis.custommap.view.swing;

import java.awt.event.ActionListener;

import javax.swing.Action;
import javax.swing.JMenuItem;
import javax.swing.KeyStroke;


public class MenuItemBuilder {

	private final JMenuItem	item;

	public MenuItemBuilder(Action action) {
		item = new JMenuItem(action);
	}
	public MenuItemBuilder(String string, ActionListener actionListener) {
		item = new JMenuItem(new SimpleAction(string, actionListener));
	}
	public JMenuItem getItem() {
		return item;
	}
	public MenuItemBuilder setShortcut(KeyStroke keyStroke) {
		item.setAccelerator(keyStroke);
		return this;
	}
	
}
