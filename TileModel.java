package net.zomis.custommap.model;

/**
 * 
 * @author Zomis
 *
 */
@Deprecated
public class TileModel {
	protected int xpos;
	protected int ypos;
	protected MapModel map;
	public TileModel(MapModel map, int x, int y) {
		this.map = map;
		this.xpos = x;
		this.ypos = y;
	}
	public TileModel(MapModel map, TileModel copy) {
		this(map, copy.getX(), copy.getY());
	}
	
	public TileModel getRelative(int dx, int dy) {
		int nx = xpos + dx;
		int ny = ypos + dy;
		if (nx < 0) return null;
		if (ny < 0) return null;
		if (nx >= map.mapWidth) return null;
		if (ny >= map.mapHeight) return null;
		
		return map.map[nx][ny];
	}
	public String getCoordinateHex() {
		int radix = 16;
		return Integer.toString(this.xpos, radix) + Integer.toString(this.ypos, radix);
	}
	public String getCoordinateString() {
		return String.format("(%d, %d)", this.getX(), this.getY());
	}
	protected void setPos(int x, int y) {
		this.xpos = x;
		this.ypos = y;
	}
	public int getX() { return this.xpos; }
	public int getY() { return this.ypos; }
	@Override
	public String toString() {
		return String.format("(Tile %d, %d)", this.xpos, this.ypos);
	}
}