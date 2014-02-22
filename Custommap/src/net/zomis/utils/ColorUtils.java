package net.zomis.utils;

public class ColorUtils {

	public static int colorTween(int start, int stop, double percent) {
		int startR = (start & 0xff0000) >> 16;
		int startG = (start & 0x00ff00) >> 8;
		int startB = start & 0x0000ff;
		
		int endR = (stop & 0xff0000) >> 16;
		int endG = (stop & 0x00ff00) >> 8;
		int endB = stop & 0x0000ff;
		
		int realR = (int) ((endR - startR) * percent);
		int realG = (int) ((endG - startG) * percent);
		int realB = (int) ((endB - startB) * percent);
		
		realR += startR;
		realG += startG;
		realB += startB;
		
		return (realR << 16) | (realG << 8) | realB;
	}
	
	public static int getR(int color) {
		return (color & 0xff0000) >> 16;
	}
	public static int getG(int color) {
		return (color & 0x00ff00) >> 8;
	}
	public static int getB(int color) {
		return color & 0x0000ff;
	}

	public static String rgbHex(int r, int g, int b) {
		return str2(r) + str2(g) + str2(b);
	}
	
	public static String str2(int value) {
		String result = Integer.toString(value, 16);
		while (result.length() < 2)
			result = "0" + result;
		return result;
	}

	public static int getHighestRGBComponent(Integer color) {
		int r = (color & 0xff0000) >> 16;
		int g = (color & 0x00ff00) >> 8;
		int b = (color & 0x0000ff);
		
		return Math.max(r, Math.max(g, b));
	}

	public static int getBestComplementaryColor(Integer color) {
		int r = (color & 0xff0000) >> 16;
		int g = (color & 0x00ff00) >> 8;
		int b = (color & 0x0000ff);

		r = (r >= 128 ? 0 : 255);
		g = (g >= 128 ? 0 : 255);
		b = (b >= 128 ? 0 : 255);
		
		return RGB(r, g, b);
	}

	public static int RGB(int r, int g, int b) {
		return (r << 16) | (g << 8) | b;
	}

}
