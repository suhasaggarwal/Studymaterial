package com.thoughtworks.problem1;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

import junit.framework.Assert;

import org.junit.Test;

import com.thoughtworks.problem1.impl.romannumbers.RomanNumberUtils;

/**
 * Junit test case for roman number to Decimal Conversion
 * 
 * @author suhas
 * 
 */
public class RomanConverterTest {

	@Test
	public void test() throws IOException {
		BufferedReader reader = null;

		try {
			// Load the groud data containing a list of all the roman numbers
			// from 1 to 3999 and their decimal equivalent
			reader = new BufferedReader(new InputStreamReader(
					RomanConverterTest.class
							.getResourceAsStream("/RomanNumberList.csv")));
			String line = null;
			while ((line = reader.readLine()) != null) {
				String[] data = line.split(",");
				int expectedDecimalNumber = Integer.parseInt(data[0]);
				String romanNumber = data[1];
				int actualDecimalNumber = RomanNumberUtils
						.getDecimalNumberFromRoman(romanNumber);
				//Check if value calculated is same as correct/expected value
				Assert.assertEquals("Error for roman number : " + romanNumber,
						expectedDecimalNumber, actualDecimalNumber);
			}
		} finally {
			if (reader != null) {
				reader.close();
			}
		}
	}

}
