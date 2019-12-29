package com.thoughtworks.problem1;

import java.io.IOException;

import org.junit.Test;

/**
 * Junit test case for Output generation for queries given
 * 
 * @author suhas
 * 
 */
public class ServiceTest {
	private static final String INPUT_FILE_LOCATION = "input/problemData.txt";
	private static final String OUTPUT_FILE_LOCATION = "output/problemOut.txt";

	@Test
	public void test() throws IOException {
		Service.process(INPUT_FILE_LOCATION, OUTPUT_FILE_LOCATION);
	}
}
