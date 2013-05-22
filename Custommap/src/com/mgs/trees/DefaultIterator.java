package com.mgs.trees;

import java.util.Iterator;
import java.util.List;

public class DefaultIterator <T> implements IteratorStrategy<T> {

	@Override
	public Iterator<Node<T>> createIterator(Tree<T> tree) {
		return new MyIterator<T>(tree.getRootNode());
	}
	
	public static class MyIterator<T> implements Iterator<Node<T>>{
		private Node<T> toBeNext;

		public MyIterator(Node<T> rootNode) {
			toBeNext = rootNode;
		}

		@Override
		public boolean hasNext() {
			return toBeNext != null;
		}

		@Override
		public Node<T> next() {
			Node<T> toReturn = toBeNext;
			toBeNext = getNext(toBeNext);
			return toReturn;
		}

		private Node<T> getNext(Node<T> previous) {
			List<Node<T>> childs = previous.getChilds();
			
			return childs == null || childs.size() == 0 ? null : childs.get(0);
		}

		@Override
		public void remove() {
			throw new UnsupportedOperationException();
		}

	}

}
