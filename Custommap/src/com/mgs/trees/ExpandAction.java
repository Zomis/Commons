package com.mgs.trees;

import java.util.Arrays;
import java.util.Iterator;
import java.util.List;

public class ExpandAction<T> {
	private enum Type {STOP_TREE, STOP_BRANCH, CONTINUE};
	private final Iterator<T> childIterator;
	private final Type type;

	private ExpandAction(Iterator<T> childIterator) {
		this.type = Type.CONTINUE;
		this.childIterator = childIterator;
	}
	
	private ExpandAction(Type type) {
		this.type = type;
		this.childIterator = null;
	}

	public static <S> ExpandAction <S> stopBranch() {
		return new ExpandAction<S>(Type.STOP_BRANCH);
	}
	
	public static <S> ExpandAction<S> stopTree() {
		return new ExpandAction<S>(Type.STOP_TREE);
	}
	
	public static <S> ExpandAction<S> continueWith(S... items) {
		List<S> asList = Arrays.asList(items);
		return continueWith(asList.iterator());
	}
	
	public static <S> ExpandAction<S> continueWith(List<S> items) {
		return continueWith(items.iterator());
	}
	
	public static <S> ExpandAction<S> continueWith(Iterator<S> iterator) {
		return new ExpandAction<S>(iterator);
	}
	
	public Iterator<T> getChildIterator() {
		return childIterator;
	}

	public Type getType() {
		return type;
	}

	public boolean isStopTree() {
		return type == Type.STOP_TREE;
	}

	public boolean isStopBranch() {
		return type == Type.STOP_BRANCH;
	}

}
