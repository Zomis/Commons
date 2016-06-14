package net.zomis.iterate;

import java.util.Iterator;
import java.util.List;

// TODO: Improve MapListIterator for a LinkedList.
public class MapListIterator<E> implements Iterator<E> {
	private int x;
	private int y;
	private List<List<E>> array;

	public MapListIterator(List<List<E>> array) {
		this.array = array;
		this.x = 0;
		this.y = 0;
		if (array != null) {
//			this.xrow = array[0];
		}
	}

	public boolean hasNext() {
		if (this.array == null) return false;
		if (y >= this.array.get(x).size()) return false;
		if (x >= this.array.size()) return false;
		
		return true;
	}

	public E next() {
		if (this.array == null) return null;

		if (!this.hasNext()) return null;
		
		E ev = array.get(x).get(y);
		x++;
		if (x >= array.size()) {
			x = 0;
			y++;
		}
		
		return ev;
	}

	public void remove() {
		throw new UnsupportedOperationException();
	}
}
