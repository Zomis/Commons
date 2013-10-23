package net.zomis.mazes;

import java.util.LinkedList;
import java.util.List;

import net.zomis.AStar;
import net.zomis.custommap.IntPoint;

public class AStarPath2<Maze> extends AStar<IntPoint> {

	private Maze	maze;
	private MazeHelper<Maze>	helper;
	private IntPoint goal;

	public AStarPath2(Maze maze, MazeHelper<Maze> helper, IntPoint goal) {
		this.maze = maze;
		this.helper = helper;
		this.goal = goal;
		if (!helper.isFreeSpace(maze, goal.getX(), goal.getY())) throw new AssertionError();
		if (helper.isWall(maze, goal.getX(), goal.getY())) throw new AssertionError();
	}
	
	@Override
	protected boolean isGoal(IntPoint node) {
		return this.goal.equals(node);
	}

	@Override
	protected Double g(IntPoint from, IntPoint to) {
		if (from.equals(to)) return Double.valueOf(0);
		return Double.valueOf(1);
	}

	@Override
	protected Double h(IntPoint from, IntPoint to) {
		return Double.valueOf(Math.abs(goal.getX() - to.getX()) + Math.abs(goal.getY() - to.getY()));
	}

	@Override
	protected List<IntPoint> generateSuccessors(IntPoint node) {
		List<IntPoint> neighbors = new LinkedList<IntPoint>();
		int x = node.getX();
		int y = node.getY();
//		new PointIterator(new IntPoint(x, y), false);
		for (int x2 = x - 1; x2 <= x + 1; x2++) {
			for (int y2 = y - 1; y2 <= y + 1; y2++) {
				if (Math.abs(x2 - x) + Math.abs(y2 - y) == 1) {
					if (helper.inZone(maze, x2, y2) && !helper.isWall(maze, x2, y2)) {
						neighbors.add(new IntPoint(x2, y2));
					}
				}
			}
		}
		return neighbors;
	}

}
