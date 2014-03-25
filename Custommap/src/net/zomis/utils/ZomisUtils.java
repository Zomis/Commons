package net.zomis.utils;

import java.util.Collection;
import java.util.HashSet;

import net.zomis.custommap.CustomFacade;

public class ZomisUtils {
	public static final double EPSILON	= 0.000001;
	
	public static double nanoToMilli(long nano) {
//		return TimeUnit.MILLISECONDS.convert(nano, TimeUnit.NANOSECONDS);
		return nano / 1000000.0;
	}
	@Deprecated
	public static String format(Object... values) {
		StringBuilder str = new StringBuilder();
		for (Object obj : values) str.append(obj);
		return str.toString();
	}
	
	public static String substr(final String str, int index, int length) {
		if (index < 0) 
			index = str.length() + index;
		
		if (length < 0) 
			length = str.length() + length;
		else length = index + length;
		
		if (index > str.length()) 
			return "";
		length = Math.min(length, str.length());
		
		return str.substring(index, length);
	}
	public static String substr(final String str, final int index) {
		if (index >= 0)
			return substr(str, index, str.length() - index);
		else return substr(str, index, -index);
	}
	public static String implode(String string, String[] arr) {
		if (arr == null) 
			return null;
		
		String ret = "";
		for (Object str : arr)
		if (str != null) {
			if (ret.length() > 0) ret += string;
			ret += str;
		}
		return ret;
	}
	public static <E> String implodeArr(String string, E[] arr) {
		if (arr == null) 
			return null;
		
		String ret = "";
		for (Object str : arr)
		if (str != null) {
			if (ret.length() > 0) ret += string;
			ret += str;
		}
		return ret;
	}
	
	public static String echo(Object obj) {
		String str = String.valueOf(obj);
		if (CustomFacade.isInitialized()) CustomFacade.getLog().i(str);
		else System.out.println(str);
		return str;
	}
	public static String dechex(int decimal) {
		return Integer.toString(decimal, 16);
	}
	
	public static String hexToString(String hex) {
		if (hex.length() % 2 != 0) 
			throw new IllegalArgumentException("Hex Length is not divisible by 2");
		
	    StringBuilder output = new StringBuilder();
	    for (int i = 0; i < hex.length(); i += 2) {
	        String str = hex.substring(i, i + 2);
	        output.append((char)Integer.parseInt(str, 16));
	    }
	    return output.toString();
	}

	public static String implode(String string, Iterable<? extends Object> list) {
		if (list == null) 
			return null;
		
		StringBuilder ret = new StringBuilder();
		for (Object str : list)
		if (str != null) {
			if (ret.length() > 0) ret.append(string);
			ret.append(str);
		}
		return ret.toString();
	}
	public static int ensureRange(int low, int value, int max) {
		return Math.max(low, Math.min(value, max));
	}
	public static float ensureRange(float low, float value, float max) {
		return Math.max(low, Math.min(value, max));
	}
	
	public static double NNKKnoDiv(int N, int n, int K, int k) {
		return nCr(K, k) * nCr(N - K, n - k);
	}
	public static double NNKKwithDiv(int N, int n, int K, int k) {
		return NNKKnoDiv(N, n, K, k) / nCr(N, n);
	}
	public static double nPr(int n, int r) {
		double result = 1;
		for (int i = n; i > n - r; i--)
			result = result * i;
		return result;
	}
	public static double factorial(int n) {
		if (n < 0)
			throw new IllegalArgumentException("n must be >= 0 but was " + n);
		if (n <= 1)
			return 1;
		
		double result = n;
		while (--n > 1)
			result *= n;
		return result;
	}
	public static double nCr(int n, int r) {
		if (r > n || r < 0)
			return 0;
		if (r == 0 || r == n)
			return 1;
		
		double start = 1;
		
		for (int i = 0; i < r; i++) {
			start = start * (n - i) / (r - i);
		}
		
		return start;
	}
	
	public static interface RecursiveInterface<E> {
		public boolean performAdd(E from, E to);
		public boolean performRecursive(E from, E to);
		public Collection<E> getRecursiveFields(E field);
	}

	public static <E> void recursiveAdd(Collection<E> collection, E field, RecursiveInterface<E> recursiveCheck) {
		recursiveAdd(collection, field, recursiveCheck, new HashSet<E>());
	}
	
	private static <E> void recursiveAdd(Collection<E> collection, E field, RecursiveInterface<E> recursiveCheck, Collection<E> recursiveChecked) {
		if (recursiveChecked.contains(field)) return;
		recursiveChecked.add(field);
		
		if (field == null) return; // Fix for NPE. 
		
		for (E mf : recursiveCheck.getRecursiveFields(field)) {
			if (mf == null) continue; // Fix for NPE.
			
			if (recursiveCheck.performAdd(field, mf)) {
				collection.add(mf);
			}
			if (recursiveCheck.performRecursive(field, mf)) {
				recursiveAdd(collection, mf, recursiveCheck, recursiveChecked);
			}
		}
	}
	/**
	 * Normalize a value to the range 0..1 (inclusive)
	 * @param value Value to normalize
	 * @param min The minimum of all values
	 * @param range The range of the values (max - min)
	 * @return
	 */
	public static double normalized(double value, double min, double range) {
		if (range == 0.0) return 0;
//		return ((value - min) / range - 0.5) * 2;
		return ((value - min) / range);
	}
	
	/**
	 * Normalize a value to the range -1..1 (inclusive)
	 * @param value Value to normalize
	 * @param min The minimum of all values
	 * @param range The range of the values (max - min)
	 * @return
	 */
	public static double normalizedSigned(double value, double min, double range) {
		if (range == 0.0) return 0;
		return ((value - min) / range - 0.5) * 2;
	}

	
	public static String textAfter(String theString, String after) {
		return ZomisUtils.substr(theString, theString.indexOf(after) + after.length());
	}
	public static String textBefore(String theString, String before) {
		return ZomisUtils.substr(theString, 0, theString.indexOf(before));
	}
	public static String textBetween(String theString, String after, String before) {
		return textBefore(textAfter(theString, after), before);
	}
	public static Class<?> classFor(Object data) {
		return data == null ? null : data.getClass();
	}
	public static boolean doubleEqual(double a, double b, double epsilon) {
		return Math.abs(a - b) <= epsilon;
	}
	public static String capitalize(String str) {
		return substr(str, 0, 1).toUpperCase() + substr(str, 1).toLowerCase();
	}
	
	public static int deckCombos(int cardsInDeck, int numCardTypes, int numOfEachType) {
		if (cardsInDeck < 0)
			return 0;
		if (cardsInDeck == 1)
			return numCardTypes;
		if (numCardTypes == 1)
			return numOfEachType >= cardsInDeck ? 1 : 0;

			int result = 0;
			for (int i = 0; i <= numOfEachType; i++) {
				result += deckCombos(cardsInDeck - i, numCardTypes - 1, numOfEachType);
			}
			return result;
	}
}
