package com.mgs.trees;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;


public class TreeFactory<T> {
	private final ExpandTreeStrategy<T> expandTreeStrategy;
	
	public TreeFactory(ExpandTreeStrategy<T> expandTreeStrategy) {
		this.expandTreeStrategy = expandTreeStrategy;
	}
	
	public TreeResult<T> buildTree(T seed) {
//		System.out.println("Building from seed: " + seed);
		ExpandAction<T> expandActionResult = expandTreeStrategy.expand(seed);
		if (expandActionResult.isStopBranch()) return TreeResult.stopBranch(seed);
		if (expandActionResult.isStopTree()) return TreeResult.stopTree(seed);
		
		Node<T> rootNode = new Node<T> (seed);
		List<Node<T>> currentBranch = new ArrayList<Node<T>>();
		currentBranch.add(rootNode);
		
		Iterator<T> childIterator = expandActionResult.getChildIterator();
		TreeResult<T> lastSubTreeResult = null;
		while (childIterator.hasNext()) {
			Node<T> next = new Node<T> (childIterator.next());
			lastSubTreeResult = buildTree(next.getContent());
			rootNode.addChild(lastSubTreeResult.getTree().getRootNode());
			if (lastSubTreeResult.isBuildInterrupted()) {
				currentBranch.addAll(lastSubTreeResult.getLastNodeBranch());
				return TreeResult.interrupt (rootNode, currentBranch);
			}
		}
		
		if (lastSubTreeResult == null) throw new NullPointerException("Did you continue without any children?"); // return TreeResult.stopBranch(seed); ??
		currentBranch.addAll(lastSubTreeResult.getLastNodeBranch());
		return TreeResult.buildCompleted (rootNode, currentBranch);
	}
}
