package net.zomis.mazes;

import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;

import net.zomis.common.IntPoint;


public class MazeAnalyze<Maze> {

	private final Maze zone;

	private final int[] neighbors;
/*
###########
#1#1#111#1#
#1#1###1#1#
#1#1132211#
#1###3#####
#1#1172211#
#1#1#1#1#1#
#1#1#1#1#1#
#1#1#####1#
#111#11111#
###########

###########
#1#1#123#1#
#2#2###4#2#
#3#3450943#
#4###6##### <-- 16
#5#56 4309# <--- 16 + 14
#6#4#2#2#8# <--- 2
#7#3#1#1#7#
#8#2#####6#
#901#12345#
###########

###########
#1#1#111#1#
#1#1###1#1#
#1#1132211#
#1###3#####
#1#1172211#
#1#1#1#1#1#
#1#1#1#1#1#
#1#1#####1#
#111#11111#
###########

 */
	@Deprecated
	int[][] findFieldUsages() { // see comments above for what I tried to do.
		int[][] usage = new int[helper.mapWidth(zone)][helper.mapHeight(zone)];
		List<IntPoint> deadEnds = this.getPointsWithNeighbors(1);
//		Stack<IntPoint> next = new Stack<IntPoint>();
		
		for (IntPoint de : deadEnds) {
			usage[de.getX()][de.getY()] = 1;
		}
		
		return usage;
	}
	
	private MazeHelper<Maze> helper;
	
	public MazeAnalyze(Maze zone, MazeHelper<Maze> helper) {
		this.zone = zone;
		this.neighbors = new int[12];
		this.helper = helper;
	}
	
	public int[] getNeighbors() {
		return Arrays.copyOf(neighbors, neighbors.length);
	}
	
	public MazeAnalyze<Maze> analyze() {
		for (int ix = 0; ix < helper.mapWidth(zone); ix++) {
			for (int iy = 0; iy < helper.mapHeight(zone); iy++) {
				int currn = getNeighborsFor(ix, iy);
			    if (currn >= 0) neighbors[currn]++;
			}
		}
		
		int count = 0;
		int sum = 0;
		for (int i = 0; i <= this.getMaxNeighbors(); i++) {
			count += neighbors[i];
			sum += neighbors[i] * i;
		}
		
		neighbors[10] = count;
		neighbors[11] = sum;
		return this;
	}
	private int getMaxNeighbors() {
		return 4;
	}

	public List<IntPoint> getPointsWithNeighbors(int num) {
		List<IntPoint> result = new LinkedList<IntPoint>();
		for (int ix = 0; ix < helper.mapWidth(zone); ix++) {
			for (int iy = 0; iy < helper.mapHeight(zone); iy++) {
				int currn = getNeighborsFor(ix, iy);
				if (currn == num) 
				   	result.add(new IntPoint(ix, iy));
			}
		}
		
		return result;
	}

	public int getNeighborsFor(int ix, int iy) {
		int currn = 0;
		if (!helper.isWall(zone, ix, iy)) {
		    for (int x2 = ix-1; x2 <= ix+1; x2++) 
		    for (int y2 = iy-1; y2 <= iy+1; y2++)
		    if (helper.inZone(zone, x2, y2))
		    if (Math.abs(x2 - ix) + Math.abs(y2 - iy) == 1)
		    if (helper.isFreeSpace(zone, x2, y2))
		    	currn++;
				    
		    return currn;
		}
		return -1;
	}

	
}
