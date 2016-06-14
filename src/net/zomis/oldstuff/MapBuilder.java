package net.zomis.oldstuff;

import java.util.HashMap;
import java.util.Map;

public class MapBuilder<K, V> {
	public static interface ChangeInterface<V> {
		V getNewValue(V oldValue);
	}
	
	private Map<K, V>	map;

	private MapBuilder(Map<K, V> map) {
		this.map = map;
	}
	
	public MapBuilder<K, V> add(K key, V value) {
		map.put(key, value);
		return this;
	}
	public MapBuilder<K, V> addOrChange(K key, ChangeInterface<V> value) {
		map.put(key, value.getNewValue(map.get(key)));
		return this;
	}
	public Map<K, V> build() {
		return this.map;
	}
	
	public static <K, V> MapBuilder<K, V> newHashMap(Class<K> keyClass, Class<V> valueClass) {
		return new MapBuilder<K, V>(new HashMap<K, V>());
	}
	public static <K, V> MapBuilder<K, V> newHashMap(K key, V value) {
		return new MapBuilder<K, V>(new HashMap<K, V>()).add(key, value);
	}
	
	
	
	public static class SimpleIntegerIncreamentor implements MapBuilder.ChangeInterface<Integer> {
		private int	start;
		private int	increament;
		public SimpleIntegerIncreamentor() {
			this(1, 1);
		}
		public SimpleIntegerIncreamentor(int start, int increament) {
			this.start = start;
			this.increament = increament;
		}
		@Override
		public Integer getNewValue(Integer oldValue) {
			if (oldValue == null) return start;
			else return oldValue + increament;
		}
	}

}
