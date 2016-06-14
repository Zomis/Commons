package net.zomis.custommap;

public class PointDouble {
    protected double x, y;
    
    public PointDouble() {
        this.set(0,0);
    }
    public PointDouble(double coordx, double coordy) {
    	this.set(coordx, coordy);
    }
    @Override
    public boolean equals(Object obj) {
    	if (obj == null) return false;
    	if (!(obj instanceof PointDouble)) return false;
    	
    	PointDouble p = (PointDouble) obj;
    	return p.x == this.x && p.y == this.y;
    }

    public void set(double coordx, double coordy) {
        x = coordx;
        y = coordy;
    }
    public double getX() {
        return x;
    }

    public double getY() {
        return y;
    }

    @Override
    public String toString() {
        return "[" + x + "," + y + "]";
    }

}