package test.net.zomis.custommap;

import static org.junit.Assert.*;

import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;
import java.util.Random;

import net.zomis.utils.ZomisList;
import net.zomis.utils.ZomisUtils;
import net.zomis.utils.ZomisUtils2;

import org.junit.Test;

public class ZomisUtilsTest {
	
	@Test
	public void hex() {
		assertEquals("AD1", ZomisUtils.hexToString("414431"));
		assertEquals("ad1", ZomisUtils.hexToString("616431"));
	}
	
	@Test
	public void shuffle() {
		List<Integer> list = new LinkedList<Integer>(Arrays.asList(1, 2, 3, 4, 5, 6, 7, 8));
		System.out.println(list);
//		Collections.shuffle(list, new Random(42));
		ZomisList.shuffle(list, new Random(32));
		System.out.println(list);
	}
	
	@Test
	public void md5() {
		assertEquals("098f6bcd4621d373cade4e832627b4f6", ZomisUtils2.md5("test"));
		
	}
	
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
