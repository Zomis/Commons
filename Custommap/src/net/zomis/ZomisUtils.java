package net.zomis;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.ObjectOutputStream;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Collection;
import java.util.Date;
import java.util.HashSet;
import java.util.Random;

import net.zomis.custommap.CustomFacade;

public class ZomisUtils {
	public static final double EPSILON	= 0.000001;
	public static String DEFAULT_DATE_FORMAT = "yyyy-MM-dd HH:mm:ss";
	
//	public static String format(String format, Object... values) {
//		throw new UnsupportedOperationException();
	// Format: %1: %2 %3 %4 %7 %5 %1 %1. Where the %x is the parameter index in values.
	// Throw IllegalArgumentException if values count not matching
//	}
	public static double nanoToMilli(long nano) {
		return nano / 1000000.0;
	}
	public static String format(Object... values) {
		StringBuilder str = new StringBuilder();
		for (Object obj : values) str.append(obj);
		return str.toString();
	}
	
	public static String date(String dateFormat, Date date) {
		SimpleDateFormat sdf;
		if (dateFormat == null)	sdf = new SimpleDateFormat(DEFAULT_DATE_FORMAT);
		else sdf = new SimpleDateFormat(dateFormat);
		return sdf.format(date);
	}
	public static String date(String string, long datetime) {
		Calendar time = Calendar.getInstance();
		time.setTimeInMillis(datetime);
		return date(string, time.getTime());
	}
	public static String date(String string) {
		return date(string, Calendar.getInstance().getTimeInMillis());
	}
	public static String date() {
		return date(DEFAULT_DATE_FORMAT);
	}
	public static String substr(final String str, int index, int length) {
		if (index < 0) index = str.length() + index;
		
		if (length < 0) length = str.length() + length;
		else length = index + length;
		
//		System.out.println("PreLength " + str + "; Index: " + index + "; Length: " + length);
		if (index > str.length()) return "";
		length = Math.min(length, str.length());
//		System.out.println("PostLength " + str + "; Index: " + index + "; Length: " + length);
		
		return str.substring(index, length);
	}
	public static String substr(final String str, final int index) {
		if (index >= 0)
			return substr(str, index, str.length() - index);
		else return substr(str, index, -index);
	}
	public static String implode(String string, String[] mess_arr) {
		if (mess_arr == null) return null;
		
		String ret = "";
		for (Object str : mess_arr)
		if (str != null) {
			if (ret.length() > 0) ret += string;
			ret += str;
		}
		return ret;
	}
	public static <E> String implodeArr(String string, E[] mess_arr) {
		if (mess_arr == null) return null;
		
		String ret = "";
		for (Object str : mess_arr)
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
		if (hex.length() % 2 != 0) throw new IllegalArgumentException("Hex Length is not divisible by 2");
		
	    StringBuilder output = new StringBuilder();
	    for (int i = 0; i < hex.length(); i += 2) {
	        String str = hex.substring(i, i + 2);
	        output.append((char)Integer.parseInt(str, 16));
	    }
	    return output.toString();
	}

	public static String implode(String string, Iterable<? extends Object> list) {
		if (list == null) return null;
		
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
	
	public static double nCr(int n, int r) {
		if (r > n) return 0;
		if (r < 0) return 0;
		if (r == 0) return 1;
		if (r == n) return 1;
		
		double start = 1;
		
		for (int i = 0; i < r; i++) {
			start = start * (n - i) / (r - i);
		}
		
		return start;
	}
	
	public static String coloredText(String text, int color) {
		return String.format("<font color=\"#%s\">%s</font>", Integer.toString(color, 16), text);
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
	public static double normalized(double value, double min, double range) {
		if (range == 0.0) return 0;
//		return ((value - min) / range - 0.5) * 2;
		return ((value - min) / range);
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
	
	public static long getSeed(Random random) {
		byte[] ba0, ba1, bar;
		try {
			ByteArrayOutputStream baos = new ByteArrayOutputStream(128);
			ObjectOutputStream oos = new ObjectOutputStream(baos);
			oos.writeObject(new Random(0));
			ba0 = baos.toByteArray();
			baos = new ByteArrayOutputStream(128);
			oos = new ObjectOutputStream(baos);
			oos.writeObject(new Random(-1));
			ba1 = baos.toByteArray();
			baos = new ByteArrayOutputStream(128);
			oos = new ObjectOutputStream(baos);
			oos.writeObject(random);
			bar = baos.toByteArray();
		} catch (IOException e) {
			throw new RuntimeException("IOException: " + e);
		}
		if (ba0.length != ba1.length || ba0.length != bar.length)
			throw new RuntimeException("bad serialized length");
		int i = 0;
		while (i < ba0.length && ba0[i] == ba1[i]) {
			i++;
		}
		int j = ba0.length;
		while (j > 0 && ba0[j - 1] == ba1[j - 1]) {
			j--;
		}
		if (j - i != 6)
			throw new RuntimeException("6 differing bytes not found");
		// The constant 0x5DEECE66DL is from
		// http://download.oracle.com/javase/6/docs/api/java/util/Random.html .
		return ((bar[i] & 255L) << 40 | (bar[i + 1] & 255L) << 32 |
				(bar[i + 2] & 255L) << 24 | (bar[i + 3] & 255L) << 16 |
				(bar[i + 4] & 255L) << 8 | (bar[i + 5] & 255L)) ^ 0x5DEECE66DL;
	}
	public static boolean doubleEqual(double a, double b, double epsilon) {
		return Math.abs(a - b) <= epsilon;
	}
}
