package net.zomis;

import java.util.HashMap;

public class IntegerMap<T> extends HashMap<T, Integer> {
	private static final long	serialVersionUID	= -1391393254551128946L;

	public IntegerMap() {
	}
	
	public void addCount(T t) {
		Integer integer = this.get(t);
		if (integer == null) {
			this.put(t, 1);
		}
		else this.put(t, integer + 1);
	}
	
}
