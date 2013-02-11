package net.zomis.custommap;

public class IntPoint {
    protected int x, y;
    
    public IntPoint() {
        this.set(0,0);
    }
    public IntPoint(int coordx, int coordy) {
    	this.set(coordx, coordy);
    }
    @Override
    public boolean equals(Object obj) {
    	if (obj == null) return false;
    	if (!(obj instanceof Point)) return false;
    	
    	Point p = (Point) obj;
    	return p.x == this.x && p.y == this.y;
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

}