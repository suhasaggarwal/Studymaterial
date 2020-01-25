package com.balazsholczer.collections;

import java.util.Map;
import java.util.TreeMap;

public class TreeMapExample {

	/**
	 * 	TreeMap -> implementation of the Map interface !!!
	 * 
	 * 		~ the concrete implementation is a red-black tree
	 * 
	 * 		- the map is sorted according to the natural ordering of its keys
	 * 			or we can use the Comparable for custom objects
	 * 
	 * 		- O(logN) guaranteed running times because of the balanced tree implementation
	 * 					get()  ,  put()   ,  remove()
	 * 
	 * 			AGAIN this data structure is not synchronized !!!
	 * 
	 * 
	 * 
	 */
	
	public static void main(String[] args) {
		
		Map<Integer, Integer> map = new TreeMap<>();
		
		map.put(4, 12);
		map.put(-4, 45);
		map.put(12, 78);
		map.put(2, 13);
		map.put(1, 21);
		
		for(Integer s : map.keySet())
			System.out.println(s+"-"+map.get(s));
		
	}
}
