package com.balazsholczer.collections;

import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.Map;

public class LinkedHashMapExample {

	/**
	 * 	It has the same features as we have seen for HashMap
	 * 
	 * 		BUT for HashMap -> no guarantees about the iteration order. 
	 * 				It can even change completely when new elements are added
	 * 
	 * 				~ there is a doubly-linked list between all of its entries: so the order will be the order
	 * 					in which we have inserted the items "insertion order"
	 * 
	 * 				~ LinkedHashMap is not synchronized as well !!!
	 * 
	 * 		LinkedHashMap -> we can iterate in the order in which the entries were put into the map
	 * 
	 */
	
	public static void main(String[] args) {
		
		Map<String, Integer> linkedHashMap = new LinkedHashMap<>();
		linkedHashMap.put("a", 1);
		linkedHashMap.put("b", 2);
		linkedHashMap.put("c", 3);
		linkedHashMap.put("d", 4);
		linkedHashMap.put("e", 5);
		linkedHashMap.put("f", 6);
		linkedHashMap.put("g", 7);
		linkedHashMap.put("h", 8);
		linkedHashMap.put("i", 9);
		linkedHashMap.put("j", 10);
		linkedHashMap.put("k", 11);
		linkedHashMap.put("l", 12);
		linkedHashMap.put("m", 13);
		linkedHashMap.put("n", 14);
		linkedHashMap.put("o", 15);
		
		for(Map.Entry<String, Integer> entry : linkedHashMap.entrySet()) {
			System.out.println(entry.getKey()+"-"+entry.getValue());
		}
		
	}
}
