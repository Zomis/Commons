package net.zomis.custommap.model;

/**
 * 
 * @author Zomis
 *
 */
public interface ITileModel<TM> {
	TM getRelative(int dx, int dy);
	int getX();
	int getY();
}