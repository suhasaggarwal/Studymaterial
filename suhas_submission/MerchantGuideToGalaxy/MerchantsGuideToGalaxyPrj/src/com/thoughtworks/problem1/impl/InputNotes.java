package com.thoughtworks.problem1.impl;

import java.util.HashMap;
import java.util.Map;

import com.thoughtworks.problem1.impl.romannumbers.RomanNumberUtils;

/**
 * Prepares two maps from Information contained in Input Data which can be used
 * to answer queries. Information includes mapping of Intergalactic symbols to
 * roman numbers and mapping of value with individual currency units (e.g
 * Silver)
 * 
 * @author suhas
 * 
 */
class InputNotes {

	// Stores inter galactic symbol to roman symbol mapping
	private Map<String, Character> interGalacticSymbolToRomanSymbolMap = new HashMap<String, Character>();

	// Stores currency to credit mapping
	private Map<String, Double> currencyToCreditMap = new HashMap<String, Double>();

	/**
	 * Get the InterGalacticSymbolToRomanSymbolMap
	 * 
	 * @return InterGalacticSymbolToRomanSymbolMap
	 */
	public Map<String, Character> getInterGalacticSymbolToRomanSymbolMap() {
		return interGalacticSymbolToRomanSymbolMap;
	}

	/**
	 * Get the currencyToCreditMap
	 * 
	 * @return currencyToCreditMap
	 */
	public Map<String, Double> getCurrencyToCreditMap() {
		return currencyToCreditMap;
	}

	/**
	 * Puts the mapping of Intergalactic symbol with romanSymbol character in a
	 * map
	 * 
	 * @param interGalacticSymbol
	 *            Inter-Galactic Symbol
	 * @param romanSymbol
	 *            Roman Symbol
	 * 
	 */
	public void addInterGalacticSymbolToRomanSymbolMapping(
			String interGalacticSymbol, String romanSymbol) {
		// Roman symbol should be of length 1
		if (romanSymbol.length() == 1) {
			Character romanSymbolCharacter = romanSymbol.charAt(0);
			// Check if it is a valid roman number and if yes add the mapping
			if (RomanNumberUtils.isValidRomanSymbol(romanSymbolCharacter)) {
				interGalacticSymbolToRomanSymbolMap.put(interGalacticSymbol,
						romanSymbolCharacter);
				return;
			}
		}
		throw new RuntimeException("Invalid roman symbol : " + romanSymbol);
	}

	/**
	 * Puts the mapping of Currency unit and its equivalent credit in a map
	 * 
	 * @param currency
	 *            currency name (e.g. Gold, Silver etc)
	 * @param credits
	 *            credit value
	 */
	public void addCurrencyToCreditMapping(String currency, double credits) {
		Double oldCreditsValue = currencyToCreditMap.get(currency);

		// Check is to make sure that currency unit value derived from input
		// statements is same in all the cases for a particular currency

		if (oldCreditsValue == null || oldCreditsValue.equals(credits)) {
			currencyToCreditMap.put(currency, credits);
		} else {
			throw new RuntimeException(
					"Different values of credits supplied for " + currency);
		}

	}
}
