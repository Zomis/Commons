package net.zomis.mazes;

public abstract class MazeHelper<Maze> {
	public abstract boolean isFreeSpace(Maze maze, int x, int y);
	public abstract boolean isWall(Maze maze, int x, int y);
	public abstract int mapWidth(Maze maze);
	public abstract int mapHeight(Maze maze);
	
	public boolean inZone(Maze maze, int x, int y) {
		return x >= 0 && y >= 0 && x < this.mapWidth(maze) && y < this.mapHeight(maze);
	}

}
