package net.zomis;

import java.util.Iterator;

public class IndexIterator<E> implements Iterator<IndexIteratorStatus<E>>, Iterable<IndexIteratorStatus<E>>, IndexIteratorStatus<E> {
	
	private final Iterator<E>	it;
	private E	current;
	private int	index;

	public IndexIterator(Iterable<E> iterable) {
		this(iterable.iterator());
	}
	public IndexIterator(Iterator<E> iterator) {
		this.it = iterator;
		this.index = -1;
	}

	@Override
	public Iterator<IndexIteratorStatus<E>> iterator() {
		return this;
	}

	@Override
	public boolean hasNext() {
		return it.hasNext();
	}

	@Override
	public IndexIteratorStatus<E> next() {
		this.current = it.next();
		this.index++;
		return this;
	}

	@Override
	public void remove() {
		it.remove();
	}

	@Override
	public int getIndex() {
		return this.index;
	}

	@Override
	public E getValue() {
		return this.current;
	}

	@Override
	public boolean isLastItem() {
		return !this.hasNext();
	}

}
