package com.mgs.trees;



public interface ExpandTreeStrategy<T> {
	public ExpandAction<T> expand (T toExpand);
}
