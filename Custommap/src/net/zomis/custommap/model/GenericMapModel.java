package net.zomis.custommap.model;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Random;

/**
 * 
 * @author Zomis
 *
 */
public abstract class GenericMapModel<TM> implements Iterable<TM> {
	protected List<List<TM>> map;
	protected int mapHeight = 5;
	protected int mapWidth = 5;
	
	public int getMapHeight() { return this.mapHeight; }
	
    public int getMapWidth() { return this.mapWidth; }
    
	public boolean hasMap() { return this.map != null; }
	
	public boolean initMap(int mapWidth, int mapHeight) {
        this.mapWidth = mapWidth;
        this.mapHeight = mapHeight;
        this.map = new ArrayList<List<TM>>(mapWidth);
    	for (int xx = 0; xx < mapWidth; xx++) {
    		this.map.add(xx, new ArrayList<TM>(mapHeight));
    	}

        for (int yy = 0; yy < mapHeight; yy++) {
        	
        	for (int xx = 0; xx < mapWidth; xx++) {
        		TM t = newTile(this, xx, yy);
        		map.get(xx).add(yy, t); // [yy] = t;
        	}
        }
        return true;
    }
	
	public Iterator<TM> iterator() {
		return new MapListIterator<TM>(this.map);
	}

	public abstract TM newTile(GenericMapModel<TM> map, int x, int y);

	public TM pos(int xpos, int ypos) {
		if (map == null) return null;
		if (xpos < 0) return null;
		if (ypos < 0) return null;
		if (xpos >= mapWidth) return null;
		if (ypos >= mapHeight) return null;
		return map.get(xpos).get(ypos);
	}
	private static Random random = new Random();
	
	public TM posRandom() {
		int x = random.nextInt(this.mapWidth);
		int y = random.nextInt(this.mapHeight);
		return pos(x, y);
	}

	public void javaGarbage() {
		if (this.map != null) {
			for (List<?> list : this.map)
				list.clear();
			this.map.clear();
			this.map = null;
		}
	}
}
