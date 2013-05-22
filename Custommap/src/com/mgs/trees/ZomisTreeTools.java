package com.mgs.trees;

import java.util.List;

public class ZomisTreeTools {
	private ZomisTreeTools() {}
	
	public static interface RecursiveScan<E> {
		boolean onScanNode(Node<E> node);
		void onNoChilds(Node<E> node);
	}
	
	public static <E> void childRecursive(RecursiveScan<E> callback, Node<E> node) {
		if (!callback.onScanNode(node)) return;
		
		List<Node<E>> childs = node.getChilds();
		if (childs.isEmpty()) {
			callback.onNoChilds(node);
		}
		else {
			for (Node<E> child : childs) {
				childRecursive(callback, child);
			}
		}
	}

}
