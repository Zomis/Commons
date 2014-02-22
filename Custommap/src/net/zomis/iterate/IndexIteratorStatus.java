package net.zomis.iterate;

public interface IndexIteratorStatus<E> {
	int getIndex();
	E getValue();
	boolean isLastItem();
}
