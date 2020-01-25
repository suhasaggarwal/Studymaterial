package com.balazsholczer.collections;

import java.util.HashMap;
import java.util.Map;

public class HashMapExample {

	/**
	 * An object that maps keys to values. A map cannot contain duplicate keys,
	 * 		 each key can map to at most one value
	 * 
	 * Some map implementations, like the TreeMap class, make specific guarantees as
	 * 	 to their order
	 * 			~ others, like the HashMap class --> do not !!!
	 * 
	 * 		TreeMap: it is sorted 
	 * 		Hashtable: it is synchronized + it is slow !!!
	 * 		HashMap: is not synchronized
	 * 			Hashtable does not allow null keys or values.  HashMap allows one null key and
	 * 				 any number of null values
	 * 			
	 * Hashfunction -> ~ O(1)
	 * 
	 */
	
	public static void main(String[] args) {
		
		Map<String, String> map = new HashMap<>();
		
		map.put("adamuser", "adam@gmail.com");
		map.put("kevinuser", "kevin@gmail.com");
		map.put("anna", "anna@gmail.com");
		
		System.out.println( map.get("kevinuser") );
		
	}
}
