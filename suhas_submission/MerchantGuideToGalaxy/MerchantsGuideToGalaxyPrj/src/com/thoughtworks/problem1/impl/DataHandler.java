package com.thoughtworks.problem1.impl;

import java.util.ArrayList;
import java.util.List;

/**
 * Splits problem data in two divisions: <br/>
 * 1.) First half is information needed to answer queries i.e <br/>
 * A) Intergalactic symbol to roman number mapping and (e.g. prok is V) <br/>
 * B) some values of money i.e Intergalatic symbols followed by currency unit
 * (e.g. glob glob Silver is 34 Credits) <br/>
 * 2.) Second half is : <br/>
 * A) queries which ask for numeric equivalent of Intergalatic symbol
 * combination e.g. how much is pish tegj glob glob ? (can be solved using 1A)
 * B) Equivalent credits or value of Intergalactic symbols followed by currency
 * unit e.g. how many Credits is glob prok Silver ? (can be solved using 1A and
 * 1B)<br/>
 * First half (input) is given to input handler and second half (queries) to
 * query handler for processing
 * 
 * @author suhas
 */
public class DataHandler {

	/**
	 * Processes the problem data and generates the output
	 * 
	 * @param problemData
	 *            list of lines in problem data
	 * @return list of output lines
	 */
	public static List<String> process(List<String> problemData) {
		// First process the input data
		InputHandler inputHandler = new InputHandler();
		int index = 0;
		for (index = 0; index < problemData.size(); index++) {
			if (problemData.get(index).endsWith(Constants.QUERY_SUFFIX)) {
				// This is a query as it ends with '?', hence break now as input
				// data is finished
				break;
			} else {
				// This is an input data, give it to Input handler
				inputHandler.handle(problemData.get(index));
			}
		}

		// Get the input notes from the inputHandler which contains processed
		// information from input data
		InputNotes inputNotes = inputHandler.getInputNotes();

		// Create query handler to handle queries
		QueryHandler queryHandler = new QueryHandler(inputNotes);

		List<String> output = new ArrayList<String>();

		// Gives queries to query Handler and store the output
		// Note: Current value of index is already at the first query
		for (; index < problemData.size(); index++) {
			output.add(queryHandler.getOutput(problemData.get(index)));
		}

		// Return the output
		return output;
	}
}
