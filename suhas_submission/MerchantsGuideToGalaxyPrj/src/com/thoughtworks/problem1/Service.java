package com.thoughtworks.problem1;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import com.thoughtworks.problem1.impl.DataHandler;

/**
 * Main entry point of application. Takes the problem data and generates the
 * output.
 * 
 * @author suhas
 * 
 */
public class Service {

	/**
	 * Processes the problem data given line by line in form a list and returns
	 * a list of output lines
	 * 
	 * @param problemData
	 *            list of lines in problem data
	 * @return list of output lines
	 */
	public static List<String> process(List<String> problemData) {
		// calling data handler to process data
		return DataHandler.process(problemData);
	}

	/**
	 * Processes the problem data given in form of input file and writes the
	 * output in the output file supplied
	 * 
	 * @param inputFile
	 *            path to input file
	 * @param outputFile
	 *            path to output file
	 * @throws IOException
	 */
	public static void process(String inputFile, String outputFile)
			throws IOException {
		BufferedReader reader = null;
		BufferedWriter writer = null;
		try {
			// reading the problem data
			reader = new BufferedReader(new FileReader(inputFile));
			List<String> problemData = new ArrayList<String>();
			String data = null;
			while ((data = reader.readLine()) != null) {
				problemData.add(data);
			}

			// calling data handler to process data
			List<String> output = process(problemData);

			// writing the output
			writer = new BufferedWriter(new FileWriter(outputFile));
			for (String outputData : output) {
				writer.write(outputData + "\n");
			}
			writer.flush();
		} finally {
			if (reader != null) {
				reader.close();
			}
			if (writer != null) {
				writer.close();
			}
		}
	}
}
