package net.zomis.custommap;

public class Point {
    protected float x, y;
    
    public Point() {
        this.set(0,0);
    }
    public Point(float coordx, float coordy) {
    	this.set(coordx, coordy);
    }
    @Override
    public boolean equals(Object obj) {
    	if (obj == null) return false;
    	if (!(obj instanceof Point)) return false;
    	
    	Point p = (Point) obj;
    	return p.x == this.x && p.y == this.y;
    }

    public void set(float coordx, float coordy) {
        x = coordx;
        y = coordy;
    }
    public float getX() {
        return x;
    }

    public float getY() {
        return y;
    }

    @Override
    public String toString() {
        return "[" + x + "," + y + "]";
    }

}