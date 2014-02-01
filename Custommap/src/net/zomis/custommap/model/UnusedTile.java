package net.zomis.custommap.model;


public class UnusedTile implements ITileModel<UnusedTile> {
	private UnusedTile() {}
	@Override
	public UnusedTile getRelative(int dx, int dy) {
		return null;
	}

	@Override
	public int getX() {
		return 0;
	}

	@Override
	public int getY() {
		return 0;
	}
	@Override
	public void javaGarbage() {
	}
}
