package net.zomis.custommap.model;

import java.util.Iterator;

public class MapIterator<E> implements Iterator<E> {
	private int x;
	private int y;
	private E[][] array;
//	private E[] xrow;

	public MapIterator(E[][] array) {
		this.array = array;
		this.x = 0;
		this.y = 0;
		if (array != null) {
//			this.xrow = array[0];
		}
	}

	public boolean hasNext() {
		if (this.array == null) return false;
		if (y >= this.array[x].length) return false;
		if (x >= this.array.length) return false;
		
		return true;
	}

	public E next() {
		if (this.array == null) return null;

		if (!this.hasNext()) return null;
		
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
}
