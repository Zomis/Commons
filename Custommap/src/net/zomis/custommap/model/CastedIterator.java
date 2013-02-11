package net.zomis.custommap.model;

import java.util.Iterator;

public class CastedIterator<E, Q> implements Iterator<Q>, Iterable<Q> {
	private Iterator<E> iterator;

	public CastedIterator(Iterator<E> iterator) {
		this.iterator = iterator;
	}

	public boolean hasNext() {
		return iterator.hasNext();
	}

	@SuppressWarnings("unchecked")
	public Q next() {
		return (Q) this.iterator.next();
	}

	public void remove() {
		this.iterator.remove();
	}

	@Override
	public Iterator<Q> iterator() {
		return this;
	}
}
