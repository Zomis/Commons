package net.zomis.utils;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.ObjectOutputStream;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Random;

public class ZomisUtils2 {
	
	public static String md5(String string) {
		return md5(string.getBytes());
	}
	public static String md5(byte[] bytes) {
		if (bytes == null)
			throw new IllegalArgumentException("bytes cannot be null");
		try {
			MessageDigest md = MessageDigest.getInstance("MD5");
			byte[] thedigest = md.digest(bytes);
			return MD5Util.toHEX(thedigest, false);
		}
		catch (NoSuchAlgorithmException e) {
			throw new RuntimeException(e);
		}
	}
	
	public static String DEFAULT_DATE_FORMAT = "yyyy-MM-dd HH:mm:ss";
	
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

	/**
	 * Gets the seed of a {@link Random}-object given it's current state (Note that the seed value changes for each call to one of the Random.next-methods)
	 * @param random Random object
	 * @return The seed of the current state of the {@link Random}-object.
	 */
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
	
	@Deprecated
	public static <T> T objAs(Object object, Class<T> to) {
		if (object == null) return null;
		
        if (to.isAssignableFrom(object.getClass())) {
            return to.cast(object);
        }
        return null;
	}
	public static String coloredText(String text, int color) {
		return String.format("<font color=\"#%s\">%s</font>", Integer.toString(color, 16), text);
	}
	
}
