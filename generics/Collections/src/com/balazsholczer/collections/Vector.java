package com.balazsholczer.collections;

import java.util.List;

public class Vector {

	/**
	 * It is similar to ArrayList with two differences
	 * 
	 * 		- Vectors are synchronized: multiple threads can manipulate the content of a 
	 * 			vector without any inconsistency
	 * 
	 * 		- Vectors contain many legacy methods: these methods are not part of the 
	 * 				collections framework
	 * 					~ so lists do not have these methods !!!
	 * 
	 * 		new Vector(capacity, capacityIncrement)
	 * 			 The vector's storage increases in chunks the size of capacityIncrement. An application can increase
	 * 				 the capacity of a vector before inserting a large number of components
	 * 					~ this reduces the amount of incremental reallocation.
	 * 
	 * 			SO: every time the vector becomes full --> the size will be incremented by the capacityIncrement !!!
	 * 
	 */
	
	public static void main(String[] args) {
		
		List<Integer> vector = new java.util.Vector<>(10,5);
		
		vector.add(10);
		vector.add(20);
		vector.add(30);
		vector.add(40);
		
		vector.remove(0);
		
		for(Integer i : vector)
			System.out.println(i);
		
		
	}
}
