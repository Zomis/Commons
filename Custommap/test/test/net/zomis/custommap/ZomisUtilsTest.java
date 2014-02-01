package test.net.zomis.custommap;

import static org.junit.Assert.*;
import net.zomis.ZomisUtils;

import org.junit.Test;

public class ZomisUtilsTest {
	
	@Test
	public void combinatorics() {
		assertDouble(120, ZomisUtils.factorial(5));
		assertDouble(120, ZomisUtils.nPr(5, 5));
		assertDouble(1, ZomisUtils.nPr(5, 0));
		assertDouble(70, ZomisUtils.nCr(8, 4));
	}
	
	public static void assertDouble(double expected, double actual) {
		assertEquals(expected, actual, 0.001);
	}

}
