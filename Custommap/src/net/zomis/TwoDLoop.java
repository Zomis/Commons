package net.zomis;

import java.util.Iterator;

import net.zomis.custommap.model.IteratorStatus;

public class TwoDLoop implements Iterator<IteratorStatus>, Iterable<IteratorStatus>, IteratorStatus {

	private int x;
	private int y;
	private int	startX;
	private int	endX;
	private int	endY;
	private int	lastIndexX;
	private int	lastIndexY;
	
	public TwoDLoop(int startX, int startY, int endX, int endY) {
		this.startX = startX;
		this.x = startX;
		this.y = startY;
		this.endX = endX;
		this.endY = endY;
	}
	
//	public TwoDLoop(int centerX, int centerY, int range) {
//	}
	
	@Override
	public int getLastIndexX() {
		return this.lastIndexX;
	}

	@Override
	public int getLastIndexY() {
		return this.lastIndexY;
	}

	@Override
	public boolean isNextLineBreak() {
		return this.lastIndexX == this.endX;
	}

	@Override
	public Iterator<IteratorStatus> iterator() {
		return this;
	}

	@Override
	public boolean hasNext() {
		if (y > this.endY) return false;
		if (x > this.endX) return false;
		return true;
	}

	@Override
	public IteratorStatus next() {
		if (!this.hasNext()) throw new IllegalStateException("Doesn't have next");

		this.lastIndexX = x;
		this.lastIndexY = y;
		this.x++;
		if (x > this.endX) {
			x = this.startX;
			y++;
		}

		return this;
	}

	@Override
	public void remove() {
		throw new UnsupportedOperationException();
	}

}
