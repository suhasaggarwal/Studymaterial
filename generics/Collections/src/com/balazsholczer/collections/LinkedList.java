package com.balazsholczer.collections;

import java.util.List;

public class LinkedList {

	/**
	 * It is another implementation of the List interface
	 * 		~ implements List, Queue, Deque interfaces + ...
	 * 
	 * 	It uses references -> each item has a reference to the next item + store the 
	 * 			data itself
	 * 
	 * 			34  ->  100  ->  -12  ->  1  single linked list
	 * 			34  <-> 100  <-> -12 <-> 1  doubly-linked list
	 * 
	 * 		- NO RANDOM ACCESS !!!
	 * 		- not synchronized
	 * 		- we can remove items very efficiently / O(1) when removing from the beginning / end
	 * 		- no sequential access !!! if we want to find an item we have to iterate through
	 * 			the list until we find it / O(N)
	 * 		- remove operation is very efficient: just update the references/pointers O(1)
	 * 
	 * 		- so we should use a linkedList for remove-heavy applications !!!
	 */
	
	public static void main(String[] args) {
		
		List<Integer> list = new java.util.LinkedList<>();
		
		list.add(3);
		list.add(10);
		list.add(20);
		
		list.remove(0); // this is why we like linkedList !!!!
		System.out.println( list.get(1)); // not that fast
		
		System.out.println(list.size());
		System.out.println(list.isEmpty());
		
		
		for(Integer i : list)
			System.out.println(i);
		
	}
}
