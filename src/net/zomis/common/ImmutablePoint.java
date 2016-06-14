package net.zomis.common;

import net.zomis.interfaces.HasPos;

public class ImmutablePoint implements HasPos {

	private final int x;
	private final int y;

	public ImmutablePoint(HasPos pos) {
		this(pos.getX(), pos.getY());
	}
	
	public ImmutablePoint(int x, int y) {
		this.x = x;
		this.y = y;
	}

	@Override
	public int getX() {
		return x;
	}

	@Override
	public int getY() {
		return y;
	}
	
    @Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + x;
		result = prime * result + y;
		return result;
	}
	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (!(obj instanceof HasPos))
			return false;
		HasPos other = (HasPos) obj;
		return x == other.getX() && y == other.getY();
	}
	
    public ImmutablePoint dxdy(int dx, int dy) {
    	return new ImmutablePoint(x + dx, y + dy);
    }
    
    public ImmutablePoint dxdy(HasPos delta) {
    	return new ImmutablePoint(x + delta.getX(), y + delta.getY());
    }
    
    @Override
    public String toString() {
        return "(" + x + ", " + y + ")";
    }
}
