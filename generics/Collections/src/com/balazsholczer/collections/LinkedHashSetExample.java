package com.balazsholczer.collections;

public class LinkedHashSetExample {

	/**
	 * 	Set is a collection that contains no duplicate elements
	 * 		~ so there is no pair of elements e1 and e2 such that
	 * 			e1.equals(e2)
	 * 
	 * 		1.) HashSet
	 *  				It contains a HashMap in the background 
	 * 					
	 * 			  - it makes no guarantees as to the iteration order of the set
	 * 					It does not guarantee that the order will remain constant over time
	 *
	 *			  - HashSet allows null element
	 *			  - if the hash function is "perfect" --> the operations have O(1)
	 *					constant running time
	 *						add(), remove(), contains(), size()
	 *			  - not synchronized
	 *
	 *			Iteration performance: iterating over a HashSet requires time proportional
	 *				to the size of the set + capacity
	 *					So: number of items + number of buckets
	 *						NOT TO SET THE INITIAL CAPACITY TOO HIGH !!!
	 *
	 *			
	 *		2.) LinkedHashSet: extends HashSet class + implements Set interface
	 *
	 *				It has predictable iteration order
	 *					It maintains a doubly-linked list between all of its items
	 *						~ thats why the iteration order is well defined
	 *								"insertion order"
	 *
	 *				We use LinkedHashSet when we copy a set
	 *				Not synchronized
	 *			
	 *				O(1)   --> add() ; remove() ; contains()
	 *					It is a bit slower than HashSet because of the additional cost
	 *						of maintaining a linked list
	 *
	 *				THE ITERATION RUNNING TIME IS UNAFFECTED BY THE CAPACITY
	 *					Iteration performance -> number of items that counts !!!
	 * 
	 * 		3.) TreeSet: red-black tree implementation
	 * 				O(logN) running times for most of the operations
	 * 					It is a sorted data structure !!!
	 * 
	 * 				TreeSet is practically implemented using TreeMap instance
	 * 				
	 *
	 */
	
	public static void main(String[] args) {
		
	}
}
