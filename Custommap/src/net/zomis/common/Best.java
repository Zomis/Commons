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
	
	// TODO: Make `Best` class thread-safe
	// TODO: Add unit tests for `Best` class
	// TODO: How to handle null objects in `Best` class?
	
	public Best() {
		this(null);
	}
	public Best(Comparator<T> comparator) {
		this.comparator = comparator;
		this.bestList = new ArrayList<T>();
	}
	
	public boolean add(T object) {
		int compare;
		if (best == null) {
			best = object;
			return true;
		}
		
		if (comparator == null) {
			@SuppressWarnings("unchecked")
			Comparable<Comparable<?>> a = (Comparable<Comparable<?>>) object;
			Comparable<?> b = (Comparable<?>) best;
			compare = a.compareTo(b);
		}
		else {
			compare = comparator.compare(object, best);
		}
		
		if (compare > 0) {
			best = object;
			bestList.clear();
			bestList.add(object);
		}
		else if (compare == 0) {
			bestList.add(object);
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
