package net.zomis.custommap.model;

import net.zomis.interfaces.Garbagable;

/**
 * 
 * @author Zomis
 *
 */
public interface ITileModel<TM> extends Garbagable {
	TM getRelative(int dx, int dy);
	int getX();
	int getY();
}