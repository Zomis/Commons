package net.zomis;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Comparator;
import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Random;
import java.util.SortedSet;
import java.util.TreeSet;
/**
 * Provides convenience-Methods for ArrayLists, such as filter and getRandom
 * @author Zomis
 *
 * @param <E>
 * @see #filter(FilterInterface)
 * @see #getRandom()
 */
public class ZomisList {
	private static Random random = new Random();
	
	public static interface FilterInterface<E> {
		/**
		 * @param obj Element to check
		 * @return True if element should be kept, false otherwise.
		 */
		boolean shouldKeep(E obj);
	}
	public static class IsClassFilter implements FilterInterface<Object> {

		private Class<?> clazz;
		
		public IsClassFilter(Class<?> clazz) {
			this.clazz = clazz;
		}
		
		@Override
		public boolean shouldKeep(Object obj) {
			return obj == null ? false : clazz.isAssignableFrom(obj.getClass());
		}
		
	}
	
	public static interface GetValueInterface<E> {
		double getValue(E obj);
	}
	
	public static <E> List<E> getAllExtreme(Iterable<E> all, double startValue, GetValueInterface<E> getValue) {
		List<E> result = new ArrayList<E>();
		
		double maxValue = startValue;
		for (E e : all) {
			double newValue = getValue.getValue(e);
			if (newValue > maxValue) {
				maxValue = newValue;
				result.clear();
				result.add(e);
			}
			else if (newValue == maxValue) {
				result.add(e);
			}
		}
		
		return result;
	}
	public static interface ConversionInterface<E, F> {
		F convert(E e);
	}
	public static <E, F> ArrayList<F> convert(List<E> list, ConversionInterface<E, F> conversion) {
		ArrayList<F> alist = new ArrayList<F>(list.size());
		for (E e : list) {
			alist.add(conversion.convert(e));
		}
		return alist;
	}
	
	public static <E> E getRandom(List<E> list) {
		return getRandom(list, random);
	}
	public static <E> E getRandom(List<E> list, Random random) {
		if (list.isEmpty()) return null;
		if (random == null) random = ZomisList.random;
		return list.get(random.nextInt(list.size()));
	}

	
	public static <E> E getRandom(E[] list) {
		return getRandom(list, random);
	}
	public static <E> E getRandom(E[] list, Random random) {
		if (list.length == 0) return null;
		if (random == null) random = ZomisList.random;
		return list[random.nextInt(list.length)];
	}
	
	public static <E> void filter(Collection<E> list, FilterInterface<? super E> filter) {
		Iterator<E> it = list.iterator();
		while (it.hasNext()) {
			E i = it.next();
			if (!filter.shouldKeep(i)) it.remove();
		}
	}
	public static <E> LinkedList<E> filter2(Collection<E> list, FilterInterface<? super E> filter) {
		LinkedList<E> list2 = new LinkedList<E>(list);
//		for (FilterInterface<E> filter : filters)
		filter(list2, filter);
		return list2;
	}
	public static <E> List<E> getAll(Iterable<E> list, FilterInterface<? super E> filter) {
		// TODO: Is there any real difference between ZomisList.filter2 and ZomisList.getAll ?
		List<E> result = new LinkedList<E>();
		for (E e : list)
			if (filter.shouldKeep(e)) result.add(e);
		return result;
	}
	@Deprecated
	public static <E> boolean contains(Iterable<E> list, FilterInterface<E> filter) {
		return !getAll(list, filter).isEmpty();
	}
	public static <E> int getSize(Iterable<E> list, FilterInterface<E> filter) {
		return getAll(list, filter).size();
	}
	
	@Deprecated
	public static interface LogInterface {
		public void log(String string);
	}
	
	@Deprecated
	public static <E> void logArray(Iterable<E> list, LogInterface logFunction) {
		for (E e : list)
			logFunction.log(e == null ? "null" : e.toString());
	}
	
	public static interface GetKeyInterface<Element, Key> {
		Key getKey(Element e);
	}
	
	public static <Element, Key> Map<Key, List<Element>> getMapFor(Iterable<Element> iterable, GetKeyInterface<Element, Key> getKeyInterface) {
		Map<Key, List<Element>> result = new HashMap<Key, List<Element>>();
		
		for (Element e : iterable) {
			Key key = getKeyInterface.getKey(e);
			if (key == null) continue;
			
			if (!result.containsKey(key)) {
				List<Element> arrList = new ArrayList<Element>();
				arrList.add(e);
				result.put(key, arrList);
			}
			else {
				List<Element> arrList = result.get(key);
				arrList.add(e);
			}
		}
		
		return result;
		
	}
	
	public static <K, V extends Comparable<? super V>> SortedSet<Map.Entry<K, V>> entriesSortedByValues(Map<K, V> map, final boolean descending) {
		SortedSet<Map.Entry<K, V>> sortedEntries = new TreeSet<Map.Entry<K, V>>(
			new Comparator<Map.Entry<K, V>>() {
				@Override
				public int compare(Map.Entry<K, V> e1, Map.Entry<K, V> e2) {
					int res;
					if (descending)	res = e1.getValue().compareTo(e2.getValue());
					else res = e2.getValue().compareTo(e1.getValue());
					return res != 0 ? -res : 1; // Special fix to preserve items with equal values
				}
		    }
		);
		sortedEntries.addAll(map.entrySet());
		return sortedEntries;
	}

	public static <E> E getOrNull(List<E> list, int index) {
		if (list.size() > index)
			return list.get(index);
		else return null;
	}
	public static <A, B extends A> List<B> getSubclass(List<A> list, Class<B> clazz) {
		List<B> result = new LinkedList<B>();
		for (A unit : list) {
			if (clazz.isAssignableFrom(unit.getClass()))
				result.add(clazz.cast(unit));
		}
		return result;
	}

	public static class FilterOR<E> implements FilterInterface<E> {
		private List<FilterInterface<E>> filters = new LinkedList<ZomisList.FilterInterface<E>>();
		public FilterOR() {
		}
		public FilterOR<E> addFilter(FilterInterface<E> filter) {
			this.filters.add(filter);
			return this;
		}
		
		@Override
		public boolean shouldKeep(E obj) {
			for (FilterInterface<E> filter : filters)
				if (filter.shouldKeep(obj))
					return true;
			return false;
		}
	}
	public static class FilterAND<E> implements FilterInterface<E> {
		private List<FilterInterface<E>> filters = new LinkedList<ZomisList.FilterInterface<E>>();
		public FilterAND() {
		}
		public FilterAND<E> addFilter(FilterInterface<E> filter) {
			this.filters.add(filter);
			return this;
		}
		
		@Override
		public boolean shouldKeep(E obj) {
			for (FilterInterface<E> filter : filters)
				if (!filter.shouldKeep(obj))
					return false;
			return true;
		}
	}
	public static class FilterXOR<E> implements FilterInterface<E> {
		private final FilterInterface<E> filterA;
		private final FilterInterface<E> filterB;
		public FilterXOR(FilterInterface<E> filterA, FilterInterface<E> filterB) {
			this.filterA = filterA;
			this.filterB = filterB;
		}
		
		@Override
		public boolean shouldKeep(E obj) {
			return filterA.shouldKeep(obj) ^ filterB.shouldKeep(obj);
		}
	}
	
}
