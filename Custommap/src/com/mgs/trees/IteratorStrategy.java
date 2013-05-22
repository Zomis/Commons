package com.mgs.trees;

import java.util.Iterator;

public interface IteratorStrategy<T> {
	public Iterator<Node<T>> createIterator (Tree<T> tree);
}
