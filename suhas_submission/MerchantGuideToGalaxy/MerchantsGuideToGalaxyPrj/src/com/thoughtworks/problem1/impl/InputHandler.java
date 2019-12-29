package com.thoughtworks.problem1.impl;

import com.thoughtworks.problem1.impl.romannumbers.RomanNumberUtils;

/**
 * Analysis Input data and feeds two component of input notes: <br/>
 * A.) Adds mapping currency unit with its credit value in
 * CurrencyToCreditMapping Map <br/>
 * B.) Adds the mapping of interGalactic Symbol to Roman Symbol in
 * InterGalacticSymbolToRomanSymbolMapping Map
 * 
 * @author suhas
 */
class InputHandler {

	private InputNotes inputNotes = new InputNotes();

	public InputNotes getInputNotes() {
		return inputNotes;
	}

	/**
	 * Analysis input Data, if input data ends with "Credits" it can be used to
	 * generate CurrencyToCreditMapping otherwise input data is related to
	 * InterGalactic Symbol to Roman Symbol mapping and can be used to generate
	 * the same
	 * 
	 * @param inputData
	 *            inputData
	 */
	public void handle(String inputData) {
		if (inputData.endsWith(Constants.CREDITS)) {
			// Input data ends with credit, generate currency to credit mapping

			// Remove "Credits" from input data and split the data using "is" as
			// delimiter into symbols followed by currency and credit value
			// e.g. "glob glob Silver is 34 Credits" will give the first part as
			// "glob glob Silver" and second part as "34"
			String[] data = inputData.substring(0,
					inputData.length() - Constants.CREDITS.length()).split(
					Constants.IS);
			if (data.length == 2) {
				// Split out all the symbols and currency
				String[] entities = data[0].split(" ");
				StringBuilder romanSymbols = new StringBuilder();

				// Builds Roman Number string from Intergalactic symbol
				// combination by analyzing input data

				for (int i = 0; i < entities.length - 1; i++) {
					// Going till "entities.length - 1" since last value will be
					// currency and not a symbol
					// Parsing InterGalactic symbols and getting their
					// equivalent roman symbols
					String interGalacticSymbol = entities[i].trim();
					Character romanSymbol = inputNotes
							.getInterGalacticSymbolToRomanSymbolMap().get(
									interGalacticSymbol);
					if (romanSymbol == null) {
						throw new RuntimeException(
								"No roman symbol found for Intergalactic symbol "
										+ interGalacticSymbol);
					} else {
						romanSymbols.append(romanSymbol.toString());
					}
				}
				// Get the currency name (e.g. Gold, Silver etc)
				String currency = entities[entities.length - 1];
				Double credits = null;

				// Obtain credit value of sum of money with currency unit by
				// analyzing input data
				try {
					// Get the credit value of money
					credits = Double.parseDouble(data[1].trim());
				} catch (NumberFormatException e) {
					throw new RuntimeException(data[1].trim()
							+ " is not a valid value for Credits");
				}

				// Calculates Numeric Equivalent of Roman number built above
				// from InterGalactic symbols
				int number = RomanNumberUtils
						.getDecimalNumberFromRoman(romanSymbols.toString());

				// Net credit value is divided with Numeric Equivalent of roman
				// number to get value of currency unit for e.g. gold and is put
				// in map
				inputNotes.addCurrencyToCreditMapping(currency, credits
						/ number);
			} else {
				// Input data is invalid as it contains multiple "is"
				throw new RuntimeException("Invalid input data : " + inputData);
			}
		} else {
			// Input data is a simple InterGalactic Symbol to roman symbol
			// mapping
			String[] data = inputData.split(Constants.IS);
			if (data.length == 2) {
				String interGalacticSymbol = data[0].trim();
				String romanSymbol = data[1].trim();
				// InterGalactic symbol to roman number map is prepared by
				// analysing input data
				inputNotes.addInterGalacticSymbolToRomanSymbolMapping(
						interGalacticSymbol, romanSymbol);
			} else {
				// Input data is invalid as it contains multiple "is"
				throw new RuntimeException("Invalid input data : " + inputData);
			}
		}
	}
}
