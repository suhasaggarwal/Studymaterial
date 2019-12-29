package com.thoughtworks.problem1.impl.romannumbers;

import java.util.HashMap;
import java.util.Map;
import java.util.regex.Pattern;

/**
 * Utility to convert Roman Number String to Number equivalent and to check if
 * roman number is valid
 * 
 * @author suhas
 */
public class RomanNumberUtils {

	private static Map<Character, Integer> romanToDecimalMap = new HashMap<Character, Integer>();

	// Roman to Decimal Map which contains mapping of roman to Decimal
	// Equivalent

	static {
		romanToDecimalMap.put('I', 1);
		romanToDecimalMap.put('V', 5);
		romanToDecimalMap.put('X', 10);
		romanToDecimalMap.put('L', 50);
		romanToDecimalMap.put('C', 100);
		romanToDecimalMap.put('D', 500);
		romanToDecimalMap.put('M', 1000);
	}

	public static boolean isValidRomanSymbol(char symbol) {
		return romanToDecimalMap.containsKey(symbol);
	}

	private static final Pattern[] invalidRomanPatterns = {
			Pattern.compile("[^IVXLCDM]"), /*
											 * Matches any character NOT in
											 * I,V,X,L,C,D,M which are roman
											 * symbols and hence is not valid
											 * roman character
											 */
			Pattern.compile("([IXCM])\\1\\1\\1+"), /*
													 * Matches 4 or more
													 * consecutive occurences of
													 * I,X,C,M which is invalid
													 */
			Pattern.compile("I[^VXI]"), /*
										 * Matches I followed by something other
										 * than V,X,I which is invalid
										 */
			Pattern.compile("X[DM]"), /*
									 * Matches X followed by D or M which is
									 * invalid
									 */
			Pattern.compile("V[^I]"), /*
									 * Matches V followed by something other
									 * than I which is invalid
									 */
			Pattern.compile("L[^XVI]"), /*
										 * Matches L followed by something other
										 * than X,V or I which is invalid
										 */
			Pattern.compile("D[M]") /*
									 * Matches D followed by M which is invalid
									 */};

	/**
	 * Checks is supplied roman string is valid
	 * 
	 * @param romanNumber
	 *            roman number string
	 * @return true if valid, false otherwise
	 */
	public static boolean isRomanNumberValid(String romanNumber) {
		for (Pattern invalidPattern : invalidRomanPatterns) {
			if (invalidPattern.matcher(romanNumber).find()) {
				// If any of the invalid pattern matches then roman string is
				// invalid
				return false;
			}
		}

		// Validates that D,L,V should not occur more than once
		int occurencesOfD = 0;
		int occurencesOfL = 0;
		int occurencesOfV = 0;
		for (char romanCh : romanNumber.toCharArray()) {
			if (romanCh == 'D') {
				occurencesOfD++;
			} else if (romanCh == 'L') {
				occurencesOfL++;
			} else if (romanCh == 'V') {
				occurencesOfV++;
			}
		}

		if (occurencesOfD > 1 || occurencesOfL > 1 || occurencesOfV > 1) {
			return false;
		}

		return true;
	}

	/**
	 * Gets the decimal number corresponding to the roman number string
	 * 
	 * @param romanNumberString
	 *            roman number string
	 * @return Decimal number corresponding to the roman number string
	 */
	public static int getDecimalNumberFromRoman(String romanNumberString) {

		// Check is roman number string is valid
		if (!isRomanNumberValid(romanNumberString)) {
			return -1;
		}
		// Calculate the decimal value
		int sum = 0;
		int i;
		// Going till length - 1 because we don't want to compare last string
		// character with anything.
		for (i = 0; i < romanNumberString.length() - 1; i++) {
			if (romanToDecimalMap.get(romanNumberString.charAt(i)) < romanToDecimalMap
					.get(romanNumberString.charAt(i + 1))) {
				// subtracting since smaller roman symbol is followed by bigger
				// roman symbol (e.g. IV)
				sum += -romanToDecimalMap.get(romanNumberString.charAt(i));
			} else {
				sum += romanToDecimalMap.get(romanNumberString.charAt(i));
			}
		}
		//Adding the value of last roman symbol character (Least significant digit)
		sum += romanToDecimalMap.get(romanNumberString.charAt(i));
		return sum;
	}
}
