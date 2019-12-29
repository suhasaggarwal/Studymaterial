package com.thoughtworks.problem1.impl;

import com.thoughtworks.problem1.impl.romannumbers.RomanNumberUtils;

/**
 * Represents the output value
 * 
 * @author suhas
 */
class OutputValue {

	private String romanSymbols;
	private Double currencyCredit;

	/**
	 * Creates an object of OutputValue to represent a simple numerical value
	 * corresponding to roman symbols
	 * 
	 * @param romanSymbols
	 *            roman symbols corresponding to Inter-galactic symbols
	 */
	public OutputValue(String romanSymbols) {
		this.romanSymbols = romanSymbols;
	}

	/**
	 * Creates an object of OutputValue to represent net credit value
	 * corresponding to roman symbols and a currency
	 * 
	 * @param romanSymbols
	 *            roman symbols corresponding to Inter-galactic symbols
	 * @param currencyCredit
	 *            currency credit value
	 */
	public OutputValue(String romanSymbols, Double currencyCredit) {
		this.romanSymbols = romanSymbols;
		this.currencyCredit = currencyCredit;
	}

	/*
	 * Returns Numeric Equivalent of Intergalactic character symbol combination
	 * (roman number) or Net credit value of money i.e Intergalactic character
	 * symbol combination with currency unit
	 */

	public String toString() {

		// Calculates Numeric value of roman number string
		int romanNumberInDecimal = RomanNumberUtils
				.getDecimalNumberFromRoman(romanSymbols);
		if (currencyCredit == null) {
			// Since there is no currency supplied just convert this numerical
			// value to string
			return Integer.toString(romanNumberInDecimal);
		} else {

			// Multiplies currency credit with Numeric value of roman number
			// string to get net credit
			double value = currencyCredit * romanNumberInDecimal;
			String strValue = "";
			if (value == (int) value) {
				// Doing this so that decimal is removed from a double value
				// which represents an int value (e.g. 42.0 becomes 42)
				strValue = Integer.toString((int) value);
			} else {
				strValue = Double.toString(value);
			}
			return strValue + Constants.CREDITS;
		}
	}

}
