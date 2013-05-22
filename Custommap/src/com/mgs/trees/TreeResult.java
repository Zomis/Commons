package com.mgs.trees;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class TreeResult<T> {
	public enum BuildResultType {
		STOP_BRANCH, STOP_TREE, INTERRUPT, BUILD_COMPLETE
	}

	private Tree<T> tree;
	private List<Node<T>> lastBranch;
	private boolean buildInterrupted;
	private BuildResultType buildResultType;

	private TreeResult(Tree<T> tree, List<Node<T>> lastBranch, boolean buildInterrupted, BuildResultType buildResultType) {
		this.tree = tree;
		this.lastBranch = lastBranch;
		this.buildInterrupted = buildInterrupted;
		this.buildResultType = buildResultType;
	}
	
	static <S> TreeResult <S> buildCompleted (Node<S> startingFrom, List<Node<S>> lastBranch){
		return new TreeResult<S> (new Tree<S> (startingFrom), lastBranch, false, BuildResultType.BUILD_COMPLETE);
	}
	
	@SuppressWarnings("unchecked")
	static <S> TreeResult <S> stopBranch (S stopOn){
		return new TreeResult<S> (new Tree<S> (stopOn), Arrays.asList(new Node<S> (stopOn)), false, BuildResultType.STOP_BRANCH);
	}
	
	@SuppressWarnings("unchecked")
	static <S> TreeResult <S> stopTree (S stopOn){
		return new TreeResult<S> (new Tree<S> (stopOn), Arrays.asList(new Node<S> (stopOn)), true, BuildResultType.STOP_TREE);
	}
	
	static <S> TreeResult <S> interrupt (Node<S> startingFrom, List<Node<S>> lastBranch){
		return new TreeResult<S> (new Tree<S> (startingFrom), lastBranch, true, BuildResultType.STOP_TREE);
	}

	public Tree<T> getTree() {
		return tree;
	}

	public List<Node<T>> getLastNodeBranch() {
		return lastBranch;
	}
	
	public List<T> getLastBranch() {
		List<T> toReturn = new ArrayList<T>();
		for (Node<T> node : lastBranch) {
			toReturn.add(node.getContent());
		}
		return toReturn;
	}

	public boolean isBuildInterrupted() {
		return buildInterrupted;
	}

	public BuildResultType getResultType() {
		return buildResultType;
	}

}
