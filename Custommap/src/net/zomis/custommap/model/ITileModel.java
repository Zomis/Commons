package net.zomis.custommap.model;

/**
 * 
 * @author Zomis
 *
 */
public interface ITileModel<TM> {
	TM getRelative(int dx, int dy);
	String getCoordinateHex();
	String getCoordinateString();
	int getX();
	int getY();
}