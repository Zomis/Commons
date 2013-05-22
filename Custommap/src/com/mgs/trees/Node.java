package com.mgs.trees;

import java.util.ArrayList;
import java.util.List;

public class Node<T>{
	private List <Node<T>> childs = new ArrayList<Node<T>>();
	private T content;

	public Node(T content) {
		this.content = content;
	}

	public void addChild (Node<T> child){
		childs.add(child);
	}
	
	public void addChild (Node<T> child, String branchName){
		childs.add(child);
	}

	public List<Node<T>> getChilds() {
		return childs;
	}

	public T getContent() {
		return content;
	}

	public void addChild(T next) {
		childs.add(new Node<T>(next));		
	}

	public Node<T> getChild(int i) {
		return getChilds().get(i);
	}

	@Override
	public String toString() {
		return "Node [content=" + content.toString() + "]";
	}	
}
