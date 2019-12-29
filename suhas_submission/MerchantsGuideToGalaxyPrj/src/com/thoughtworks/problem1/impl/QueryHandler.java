package com.thoughtworks.problem1.impl;

/**
 * Analyses two type of Queries one which is for Numeric value of Intergalactic
 * symbol combination (e.g. how much is pish tegj glob glob ?) and one which ask
 * for net credit value corresponding to money given i.e Intergalactic symbol
 * combination with currency unit (e.g. how many Credits is glob prok Silver ?)
 * 
 * @author suhas
 */
class QueryHandler {

	private InputNotes inputNotes;

	/**
	 * Creates an object of Query Handler
	 * 
	 * @param inputNotes
	 *            notes from input containing inter-galactic symbol to roman
	 *            symbol map and currency to credit map
	 */
	public QueryHandler(InputNotes inputNotes) {
		this.inputNotes = inputNotes;
	}

	/**
	 * Gets the output corresponding to a query
	 * 
	 * @param query
	 *            query
	 * @return Analyses query and returns output accordingly
	 */
	public String getOutput(String query) {

		// Deals with query that asks for numeric value of Intergalactic symbol
		// combination (e.g. how much is pish tegj glob glob ?)
		if (query.startsWith(Constants.NON_CREDIT_QUERY_PREFIX)) {
			StringBuilder romanSymbols = new StringBuilder();
			// Get the inter-galactic symbols from the query
			// Doing a substring till length - 1 as last char in query will be
			// '?'
			String[] interGalacticSymbolsInQuery = query.substring(
					Constants.NON_CREDIT_QUERY_PREFIX.length(),
					query.length() - 1).split(" ");

			// Converts Intergalaticsymbol combination to roman number string
			// equivalent using mapping from input notes
			for (String interGalacticSymbol : interGalacticSymbolsInQuery) {
				Character romanSymbol = inputNotes
						.getInterGalacticSymbolToRomanSymbolMap().get(
								interGalacticSymbol.trim());
				if (romanSymbol == null) {
					// Query is invalid as we can't find roman symbol
					// corresponding to intergalactic symbol
					return Constants.INVALID_QUERY_MESSAGE;
				} else {
					romanSymbols.append(romanSymbol);
				}
			}

			// Returns Numeric Value of roman number string
			return new OutputValue(romanSymbols.toString()).toString();

		} else if (query.startsWith(Constants.CREDIT_QUERY_PREFIX)) {

			// Deals with query that asks for net credit value of money given
			// i.e Intergalatic symbol combination with currency unit (e.g. how
			// many Credits is glob prok Silver ?)

			StringBuilder romanSymbols = new StringBuilder();
			Double currencyCredit;

			// Get the inter-galactic symbols and currency from the query
			// Doing a substring till length - 1 as last char in query will be
			// '?'
			String[] entitiesInQuery = query.substring(
					Constants.CREDIT_QUERY_PREFIX.length(), query.length() - 1)
					.split(" ");
			// Builds Roman Number String from Intergalactic character
			// combination (going till length -1 since last value will be
			// currency (e.g. Gold)
			for (int i = 0; i < entitiesInQuery.length - 1; i++) {
				Character romanSymbol = inputNotes
						.getInterGalacticSymbolToRomanSymbolMap().get(
								entitiesInQuery[i].trim());
				if (romanSymbol == null) {
					// Query is invalid as we can't find roman symbol
					// corresponding to intergalactic symbol
					return Constants.INVALID_QUERY_MESSAGE;
				} else {
					romanSymbols.append(romanSymbol);
				}
			}
			// Obtains currency from input query
			String currency = entitiesInQuery[entitiesInQuery.length - 1];
			// Get the credit value of currency from input Notes
			currencyCredit = inputNotes.getCurrencyToCreditMap().get(currency);

			if (currencyCredit == null) {
				// Query is invalid as we can't find credit value corresponding
				// to currency
				return Constants.INVALID_QUERY_MESSAGE;
			}
			// Returns net credit value of money given
			return new OutputValue(romanSymbols.toString(), currencyCredit)
					.toString();

		} else {
			// Query is invalid as it does not represent either of the two types
			// of queries
			return Constants.INVALID_QUERY_MESSAGE;
		}
	}
}
