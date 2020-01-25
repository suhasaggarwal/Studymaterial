package com.balazsholczer.udemy;

public class App {

	/**
	 * "Producer extends and consumer super" principle

		Producer extends ->  if we want to read from a list we have to declare it
						with extends
					List<? extends T> we can read T values from this list but can not add T
						values to this list !!!

		Consumer super -> if we want to add to a list we should use super
	
					List<? super T> we can add T values to this list but can not read from
						this list because we do not know the type

		If we want to add AND read at the same time: we have to declare the collection with no wildcards
				For example: List<Double> we can add and read doubles !!!
	 * 
	 * 
	 * 
	 */
}
