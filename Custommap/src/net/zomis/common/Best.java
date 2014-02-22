package net.zomis.common;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.Random;

import net.zomis.utils.ZomisList;

public class Best<T> {

	private T best;
	private final List<T> bestList;
	private final Comparator<T> comparator;
	
	public Best() {
		this(null);
	}
	public Best(Comparator<T> comparator) {
		this.comparator = comparator;
		this.bestList = new ArrayList<T>();
	}
	
	public boolean add(T t) {
		int compare;
		if (best == null) {
			best = t;
			return true;
		}
		
		if (comparator == null) {
			@SuppressWarnings("unchecked")
			Comparable<Comparable<?>> a = (Comparable<Comparable<?>>) t;
			Comparable<?> b = (Comparable<?>) best;
			compare = a.compareTo(b);
		}
		else {
			compare = comparator.compare(t, best);
		}
		
		if (compare > 0) {
			best = t;
			bestList.clear();
			bestList.add(t);
		}
		else if (compare == 0) {
			bestList.add(t);
		}
		else return false;
		
		return true;
	}
	
	public T getTheBest() {
		return best;
	}
	
	public T getRandomBest() {
		return ZomisList.getRandom(bestList);
	}
	
	public T getRandomBest(Random random) {
		return ZomisList.getRandom(bestList, random);
	}
	
	public List<T> getAllBest() {
		return new ArrayList<T>(bestList);
	}
	
}
