package com.mgs.trees;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

public class Tree <T> {
	private Node<T> rootNode;

	public Tree(T seed) {
		rootNode = new Node<T> (seed);
	}

	public Tree(Node<T> rootNode) {
		this.rootNode = rootNode;
	}

	public List<Node<T>> getNodes (IteratorStrategy<T> navigationStrategy){
		List<Node<T>> toReturn = new ArrayList<Node<T>>();
		Iterator<Node<T>> iterator = navigationStrategy.createIterator(this);
		while (iterator.hasNext()){
			toReturn.add(iterator.next());
		}
		return toReturn;
	}


	public Node<T> getRootNode() {
		return rootNode;
	}
}
