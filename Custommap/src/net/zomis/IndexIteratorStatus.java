package net.zomis;

public interface IndexIteratorStatus<E> {
	int getIndex();
	E getValue();
	boolean isLastItem();
}
