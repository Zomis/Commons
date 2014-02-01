package net.zomis.custommap.model;

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