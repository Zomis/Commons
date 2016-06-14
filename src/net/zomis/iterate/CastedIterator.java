package net.zomis.iterate;

import java.util.Iterator;

@Deprecated
public class CastedIterator<FromClass, ToClass> implements Iterator<ToClass>, Iterable<ToClass> {
	// Can't use `ToClass extends FromClass` because it can be used to implement an iterator which is actually *ToClass super FromClass" -- which cannot be declared in the class
	private Iterator<FromClass> iterator;

	public CastedIterator(Iterator<FromClass> iterator) {
		this.iterator = iterator;
	}
	public CastedIterator(Iterable<FromClass> iterable) {
		this(iterable.iterator());
	}

	public boolean hasNext() {
		return iterator.hasNext();
	}

	@SuppressWarnings("unchecked")
	public ToClass next() {
		return (ToClass) this.iterator.next();
	}

	public void remove() {
		this.iterator.remove();
	}

	@Override
	public Iterator<ToClass> iterator() {
		return this;
	}
}
