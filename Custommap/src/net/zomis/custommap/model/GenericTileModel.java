package net.zomis.custommap.model;

/**
 * 
 * @author Zomis
 *
 */
public abstract class GenericTileModel<TM, MM extends GenericMapModel<? extends TM>> implements ITileModel<TM> {
	private int x;
	private int y;
	protected MM map;
	public GenericTileModel(MM map, int x, int y) {
		this.map = map;
		this.x = x;
		this.y = y;
	}
	public GenericTileModel(MM map, GenericTileModel<?, ?> copy) {
		this(map, copy.getX(), copy.getY());
	}
	
	public TM getRelative(int dx, int dy) {
		int nx = x + dx;
		int ny = y + dy;
		if (nx < 0) return null;
		if (ny < 0) return null;
		if (nx >= map.mapWidth) return null;
		if (ny >= map.mapHeight) return null;
		
		return map.pos(nx, ny);
	}
	@Deprecated
	public String getCoordinateHex() {
		int radix = 16;
		return Integer.toString(this.x, radix) + Integer.toString(this.y, radix);
	}
	@Deprecated
	public String getCoordinateString() {
		return String.format("(%d, %d)", this.getX(), this.getY());
	}
	public int getX() { return this.x; }
	public int getY() { return this.y; }
	@Override
	public String toString() {
		return String.format("(Tile %d, %d)", this.x, this.y);
	}
}