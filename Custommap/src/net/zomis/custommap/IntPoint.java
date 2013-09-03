package net.zomis.custommap;

public class IntPoint {
    protected int x, y;
    
    public IntPoint() {
		this(0, 0);
	}
    public IntPoint(IntPoint copy) {
        this(copy.x, copy.y);
    }
    public IntPoint(int coordx, int coordy) {
    	this.set(coordx, coordy);
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
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		IntPoint other = (IntPoint) obj;
		if (x != other.x)
			return false;
		if (y != other.y)
			return false;
		return true;
	}
    

    public void set(int coordx, int coordy) {
        x = coordx;
        y = coordy;
    }
    public int getX() {
        return x;
    }

    public int getY() {
        return y;
    }

    @Override
    public String toString() {
        return "[" + x + "," + y + "]";
    }
	public void set(IntPoint value) {
		this.set(value.x, value.y);
	}
	public int distanceSquared(IntPoint other) {
		int dx = other.x - this.x;
		int dy = other.y - this.y;
		return dx*dx + dy*dy;
	}
	public double distance(IntPoint other) {
		return Math.sqrt(distanceSquared(other));
	}

}