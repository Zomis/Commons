package net.zomis;

import java.util.Iterator;

public class ArrayIterator<E> implements Iterator<E> {

	private final E[] array;
	private int index = -1;
	
	public ArrayIterator(E[] array) {
		this.array = array;
	}
	
	@Override
	public boolean hasNext() {
		return index < array.length - 1;
	}

	@Override
	public E next() {
		index++;
		return array[index];
	}

	@Override
	public void remove() {
		throw new UnsupportedOperationException();
	}

}
