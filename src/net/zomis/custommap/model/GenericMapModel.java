package net.zomis.custommap.model;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Random;

import net.zomis.interfaces.Garbagable;
import net.zomis.iterate.IndexIterator;
import net.zomis.iterate.IndexIteratorStatus;
import net.zomis.iterate.MapListIterator;

/**
 * 
 * @author Zomis
 *
 */
public abstract class GenericMapModel<TM extends Garbagable> implements Iterable<TM> {
	protected List<List<TM>> map;
	private int mapHeight = 5;
	private int mapWidth = 5;
	
	protected void setMapWidth(int mapWidth) {
		this.mapWidth = mapWidth;
	}
	protected void setMapHeight(int mapHeight) {
		this.mapHeight = mapHeight;
	}
	
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

	protected abstract TM newTile(GenericMapModel<TM> map, int x, int y);

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
	
	protected void changeSize(int newWidth, int newHeight) {
		int numAddWidth = newWidth - getMapWidth();
		
		if (numAddWidth >= 0) {
			ensureExistingHeights(newHeight);
			for (int i = 0; i < numAddWidth; i++) {
				List<TM> list = new ArrayList<TM>(newHeight);
				for (int yy = 0; yy < newHeight; yy++) {
					TM tm = newTile(this, getMapWidth() + i, yy);
					list.add(tm);
				}
				map.add(list);
			}
		}
		else {
			for (int i = 0; i < -numAddWidth; i++) {
				List<TM> list = map.get(newWidth + i);
				for (TM bt : list)
					bt.javaGarbage();
				list.clear();
			}
			for (int i = 0; i < -numAddWidth; i++) {
				map.remove(map.size() - 1);
			}
			ensureExistingHeights(newHeight);
		}
		this.mapWidth = newWidth;
		this.mapHeight = newHeight;
	}

	private void ensureExistingHeights(int newHeight) {
		int numAddHeight = newHeight - getMapHeight();
		if (numAddHeight >= 0) {
			for (IndexIteratorStatus<List<TM>> ee : new IndexIterator<List<TM>>(map)) {
				for (int yy = 0; yy < numAddHeight; yy++) {
					TM tm = newTile(this, ee.getIndex(), getMapHeight() + yy);
					ee.getValue().add(tm);
				}
			}
		}
		else {
			for (List<TM> list : map) {
				for (int i = 0; i < -numAddHeight; i++) {
					TM bt = list.remove(list.size() - 1);
					bt.javaGarbage();
				}
			}
		}
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
