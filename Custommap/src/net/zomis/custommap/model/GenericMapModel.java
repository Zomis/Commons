package net.zomis.custommap.model;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Random;

import net.zomis.custommap.view.ZomisTimer;

import com.fasterxml.jackson.annotation.JsonAutoDetect;
import com.fasterxml.jackson.annotation.JsonAutoDetect.Visibility;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;

/**
 * 
 * @author Zomis
 *
 */
@JsonAutoDetect(getterVisibility = Visibility.NONE, fieldVisibility=Visibility.NONE, setterVisibility=Visibility.NONE)
public abstract class GenericMapModel<TM> implements Iterable<TM> {
	public static final int AUTOPAINT = 2;// auto-updating
	public static final int PAUSED = 1;// interaction possible but not auto-updating
	@Deprecated
	public static final int STOPPED = 0;// was supposed to correspond to active = false in MFE, making the field unclickable.
	
	@JsonProperty protected long _moveDelay = 100;
	@JsonIgnore protected ZomisTimer _timer;
	
	// Äntligen klart! 2013-01-08 Replace with generics, MapModel<E>
	@JsonIgnore protected List<List<TM>> map;
	@JsonIgnore protected int mapHeight = 5;

	//	@JsonIgnore public int mode = 0;
	@JsonIgnore protected int mapWidth = 5;
	
	/*	protected void setMode(int mode) {
		this.mode = mode;
		if (mode == AUTOPAINT) {
			update();
			timerStart();
			return;
		}
		if (mode == PAUSED) {
			timerStop();
		}
		if (mode == STOPPED) {
			timerStop();
		}
	}*/
	protected Random random = new Random();
	
	public GenericMapModel() {
//        timerStart();
//        setMode(AUTOPAINT);
	}
	
	public int getMapHeight() { return this.mapHeight; }
	
	@JsonIgnore public int getMapTotalSize() {
		return this.getMapWidth() * this.getMapHeight();
	}
	
    public int getMapWidth() { return this.mapWidth; }
    
	protected ZomisTimer getTimer() {
		return this._timer;
	}
	
/*	private void update() {
//		if (mode != AUTOPAINT) return;// if timer is not running
		onTick();
//		CustomFacade.getInst().sendNotification(CustomFacade.GAME_UPDATE, this);
	}*/
	
	public boolean hasMap() { return this.map != null; }
	
	public boolean initMap(int mapWidth, int mapHeight) {
        this.mapWidth = mapWidth;
        this.mapHeight = mapHeight;
        this.map = new ArrayList<List<TM>>(mapWidth);
    	for (int xx = 0; xx < mapWidth; xx++) {
    		this.map.add(xx, new ArrayList<TM>());
    	}

        for (int yy = 0; yy < mapHeight; yy++) {
        	
        	for (int xx = 0; xx < mapWidth; xx++) {
        		TM t = newTile(this, xx, yy);
        		map.get(xx).add(yy, t); // [yy] = t;
        	}
        }
//        CustomFacade.getLog().d("Zomis", "GridView initMap complete.");
        return true;
    }
	
	public Iterator<TM> iterator() {
		return new MapListIterator<TM>(this.map);
	}

	public abstract TM newTile(GenericMapModel<TM> map, int x, int y);

	public void onGameLoaded() {
		// called from NonLayoutingLayout -- has determined size and stuff
	}
	
	public void onTick() {}// called on timer
	public TM pos(int xpos, int ypos) {
		if (map == null) return null;
		if (xpos < 0) return null;
		if (ypos < 0) return null;
		if (xpos >= mapWidth) return null;
		if (ypos >= mapHeight) return null;
		return map.get(xpos).get(ypos);
	}
	public TM posRandom() {
		int x = random.nextInt(this.mapWidth);
		int y = random.nextInt(this.mapHeight);
		return pos(x, y);
	}
	protected void setTimer(ZomisTimer timer) { this._timer = timer; }
}
