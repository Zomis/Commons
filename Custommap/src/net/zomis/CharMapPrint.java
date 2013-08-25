package net.zomis;

import java.util.Arrays;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;

import net.zomis.custommap.model.IteratorStatus;
import net.zomis.custommap.model.MapIterator;

public class CharMapPrint {
	private CharMapPrint() {}
	public static interface GetCharInterface<E> {
		char getChar(E e, IteratorStatus iterator);
	}
	public static interface GetCharInterfaceSimple<E> {
		char getChar(E e);
	}

	@Deprecated
	public static <E> List<String> printAsList(int width, int height, Iterator<E> fields, GetCharInterfaceSimple<E> getCharInterface) {
		return Arrays.asList(printAsArray(width, height, fields, getCharInterface));
	}
	
	@Deprecated
	public static <E> String[] printAsArray(int width, int height, Iterator<E> fields, GetCharInterfaceSimple<E> getCharInterface) {
		String[] strs = new String[height];
		StringBuilder str = new StringBuilder();
		int row = 0;
		int count = 0;
		while (fields.hasNext()) {
			str.append(getCharInterface.getChar(fields.next()));
			if (count >= width - 1) {
				strs[row] = str.toString();
				str = new StringBuilder();
				row++;
			}
			count++;
		}
		return strs;
	}
	
	@Deprecated
	public static <E> String[] printAsArray(E[][] fields, GetCharInterfaceSimple<E> getCharInterface) {
		MapIterator<E> it = new MapIterator<E>(fields);
		String[] strs = new String[fields[0].length];
		StringBuilder str = new StringBuilder();
		int i = 0;
		while (it.hasNext()) {
			str.append(getCharInterface.getChar(it.next()));
			if (it.isNextLineBreak()) {
				strs[i] = str.toString();
				str = new StringBuilder();
				i++;
			}
		}
		return strs;
	}
	@Deprecated
	public static <E> List<String> printAsList(E[][] fields, GetCharInterfaceSimple<E> getCharInterface) {
		List<String> strs = new LinkedList<String>();
		MapIterator<E> it = new MapIterator<E>(fields);
		StringBuilder str = new StringBuilder();
		while (it.hasNext()) {
			str.append(getCharInterface.getChar(it.next()));
			if (it.isNextLineBreak()) {
				strs.add(str.toString());
				str = new StringBuilder();
			}
		}
		return strs;
	}
	public static <E> String print(E[][] fields, GetCharInterface<E> getCharInterface) {
		StringBuilder str = new StringBuilder();
		MapIterator<E> it = new MapIterator<E>(fields);
		while (it.hasNext()) {
			str.append(getCharInterface.getChar(it.next(), it));
			if (it.isNextLineBreak())
				str.append('\n');
		}
		return str.toString();
	}

}
