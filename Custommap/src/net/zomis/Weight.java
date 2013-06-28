package net.zomis;

import java.util.Collections;
import java.util.List;
import java.util.Random;

public class Weight {
	public interface Weighter<E> {
		double getWeight(E e);
	}
	public interface Weightable {
		double getWeight();
	}
	private static Random	random = new Random();

	@Deprecated
	public static <E> E getRandomWithWeight(List<E> list, Random random, Weighter<E> weight) {
		if (list.isEmpty()) return null;
		if (random == null) random = Weight.random;
		
//		double sumWeight = 0;
//		for (E e : list) {
//			sumWeight += weight.getWeight(e);
//		}
		
		Collections.shuffle(list);
		
		return list.get(random.nextInt(list.size()));
	}
	@Deprecated
	public static <E extends Weightable> E getRandomWithWeight(List<E> list, Random random) {
		if (list.isEmpty()) return null;
		if (random == null) random = Weight.random ;
		return list.get(random.nextInt(list.size()));
	}
}