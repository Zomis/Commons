package net.zomis.iterate;

import java.util.Iterator;


public class MapIterator<E> implements Iterator<E>, IteratorStatus {
	
	private int x;
	private int y;
	private final E[][] array;
	private int	lastIndexX;
	private int	lastIndexY;

	public int getLastIndexX() {
		return lastIndexX;
	}
	public int getLastIndexY() {
		return lastIndexY;
	}
	
	public MapIterator(E[][] array) {
		this.array = array;
		this.x = 0;
		this.y = 0;
	}

	public void reset() {
		this.x = 0;
		this.y = 0;
	}
	
	public boolean hasNext() {
		if (this.array == null) return false;
		if (y >= this.array[x].length) return false;
		if (x >= this.array.length) return false;
		
		return true;
	}

	public E next() {
		if (this.array == null) throw new NullPointerException("Array is null");
		if (!this.hasNext()) throw new IllegalStateException("Doesn't have next");
		this.lastIndexX = x;
		this.lastIndexY = y;
		E ev = array[x][y];
		x++;
		if (x >= array.length) {
			x = 0;
			y++;
		}
		
		return ev;
	}

	public void remove() {
		throw new UnsupportedOperationException();
	}
	public boolean isNextLineBreak() {
		return (getLastIndexX() == array.length - 1);
	}
}
