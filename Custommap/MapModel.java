package net.zomis.custommap.model;

import java.util.Iterator;
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
@Deprecated
public abstract class MapModel implements Iterable<TileModel> {
	@Deprecated
	public static final int STOPPED = 0;// was supposed to correspond to active = false in MFE, making the field unclickable.
	public static final int PAUSED = 1;// interaction possible but not auto-updating
	public static final int AUTOPAINT = 2;// auto-updating
	
//	@JsonIgnore public int mode = 0;
	@JsonIgnore protected int mapWidth = 5;
	@JsonIgnore protected int mapHeight = 5;
	
	public int getMapWidth() { return this.mapWidth; }
	public int getMapHeight() { return this.mapHeight; }

	// TODO Replace with generics, MapModel<E>
	@JsonIgnore protected TileModel[][] map;// test with non-transient
	
	public boolean hasMap() { return this.map != null; }
	
	public Iterator<TileModel> iterator() {
		return new MapIterator<TileModel>(this.map);
	}
	
	public abstract TileModel newTile(MapModel map, int x, int y);
	
	@JsonProperty protected long _moveDelay = 100;
	
    public boolean initMap(int mapWidth, int mapHeight) {
        this.mapWidth = mapWidth;
        this.mapHeight = mapHeight;
        this.map = new TileModel[mapWidth][mapHeight];

        for (int yy = 0; yy < mapWidth; yy++) {
        	for (int xx = 0; xx < mapWidth; xx++) {
        		TileModel t = newTile(this, xx, yy);
        		map[xx][yy] = t;
        	}
        }
//        CustomFacade.getLog().d("Zomis", "GridView initMap complete.");
        return true;
    }
    
	public MapModel() {
//        timerStart();
//        setMode(AUTOPAINT);
	}
	
/*	private void update() {
//		if (mode != AUTOPAINT) return;// if timer is not running
		onTick();
//		CustomFacade.getInst().sendNotification(CustomFacade.GAME_UPDATE, this);
	}*/
	
	public void onTick() {}// called on timer
	
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
	
	public TileModel posRandom() {
		int x = random.nextInt(this.mapWidth);
		int y = random.nextInt(this.mapHeight);
		return pos(x, y);
	}

	public TileModel pos(int xpos, int ypos) {
		if (map == null) return null;
		if (xpos < 0) return null;
		if (ypos < 0) return null;
		if (xpos >= mapWidth) return null;
		if (ypos >= mapHeight) return null;
		return map[xpos][ypos];
	}

	public void onGameLoaded() {
		// called from NonLayoutingLayout -- has determined size and stuff
	}
	
	@JsonIgnore protected ZomisTimer _timer;
	protected void setTimer(ZomisTimer timer) { this._timer = timer; }
	protected ZomisTimer getTimer() {
		return this._timer;
	}
	@JsonIgnore public int getMapTotalSize() {
		return this.getMapWidth() * this.getMapHeight();
	}
}
