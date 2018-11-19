package test.common.utils;

import java.io.PrintWriter;
import java.io.StringWriter;
import org.junit.Assert;
import lombok.extern.slf4j.Slf4j;

@Slf4j
public class Comparator {

	public static void check(String message,String expected, String actual) {
		String actualResult = " Actual Result: ";
		String expResult = " Expected Result: ";
		String msg = message + " " + actualResult + actual + " is displayed " + expResult + expected + " is expected";
		
		Assert.assertEquals(msg, expected, actual);
		log.info(actual.equals(expected) ? "PASS :" + msg : "FAIL :" + msg);

	}
	
	public static void check(String expected, String actual) {
		check("", expected, actual);

	}

	public static void checkSearch(String expected, String actual, String modality1) {
		String actualResult = " Actual : ";
		String expResult = " Expected : ";
		String msg = "Number of Exams for " + modality1 + actualResult + actual + expResult + expected;

		log.info(actual.equals(expected) ? "PASS " + msg : "FAIL " + msg);
		Assert.assertEquals(msg, expected, actual);
	}
	
	public static void check(String message, int expected, int actual) {
		String actualResult = " Actual Result: ";
		String expResult = " Expected Result: ";
		String msg = actualResult + actual + " is displayed " + expResult + expected + " is expected";

		log.info((actual == expected) ? "PASS" + message + msg : "FAIL" + message + msg);

		if (actual != expected) {
			log.error("Test case Failed");
		}
		Assert.assertEquals(message, expected, actual);
	}

	public static void check(int expected, int actual) {
		check("",expected, actual);
	}

	public static void check(float expected, float actual) {
		String actualResult = " Actual Result: ";
		String expResult = " Expected Result: ";
		String msg = actualResult + actual + " is displayed " + expResult + expected + " is expected";

		log.info((actual == expected) ? "PASS" + msg : "FAIL" + msg);

		if (actual != expected) {
			log.error("Test case Failed");
		}

		Assert.assertEquals(expected, actual, 0.001);

	}

	public static void check(double expected, double actual) {
		String actualResult = " Actual Result: ";
		String expResult = " Expected Result: ";
		String msg = actualResult + actual + " is displayed " + expResult + expected + " is expected";

		log.info((actual == expected) ? "PASS" + msg : "FAIL" + msg);

		if (actual != expected) {
			log.error("Test case Failed");
		}

		Assert.assertEquals(expected, actual, 0.001);
	}

	public static void check(String message, boolean expected, boolean actual) {

		Assert.assertEquals(expected, actual);
		log.info(expected == actual ? "PASS " +message+": Condition is True" : "FAIL" +message+ ": Condition is not met, its a fail");
	}

	public static void check(boolean expected, boolean actual) {
		check("", expected, actual);
	}

	public static boolean match(String expected, String actual, String fieldName) {
		boolean result = expected.equals(actual);
		log.info((result ? " PASS " : " FAIL ") + " Verified " + fieldName + " value - Expected: " + expected
				+ " Actual: " + actual);
		return result;
	}

	public static boolean match(int expected, int actual, String fieldName) {
		boolean result = (expected == actual);
		log.info((result ? " PASS " : " FAIL ") + " Verified " + fieldName + " value - Expected: " + expected
				+ " Actual: " + actual);
		return result;
	}

	public static boolean match(boolean expected, boolean actual, String fieldName) {
		boolean result = (expected == actual);
		log.info((result ? " PASS " : " FAIL ") + " Verified " + fieldName + " value - Expected: " + expected
				+ " Actual: " + actual);
		return result;
	}

	public static void info(String message) {
		log.info(message);
	}

	public static void error(String message) {
		log.error(message);
	}

	public static void error(Object message) {
		log.error(message.toString());
	}

	public static void error(StackTraceElement message) {
		log.error(message.toString());
	}

	public static void error(StackOverflowError message) {
		log.error(message.toString());
	}

	public static void debug(String message) {
		log.debug(message.toString());
	}

	public static void debug(Exception e) {
		StringWriter errors = new StringWriter();

		e.printStackTrace(new PrintWriter(errors));
		log.debug(errors.toString());
	}

}