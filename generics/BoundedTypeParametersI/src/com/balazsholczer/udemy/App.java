package com.balazsholczer.udemy;

public class App {

	/**
	 * There may be times when you want to restrict the types that can be used 
	 * as type arguments in a parameterized type. For example, a method that operates
	 * 	 on numbers might only want to accept instances of Number or its subclasses. 
	 * This is what bounded type parameters are for.
	 * 
	 * 	bounded type parameters allow you to invoke methods defined in the bounds
	 * 		For example extends Comparable -> we can use compareTo() method to compare items !!!
	 * 
	 * Type parameter can have multiple bounds:
				<T extends B1 & B2 & B3>
				
				Important for algorithms !!!
				
						for example we cant use ' > ; < ; == ' operators on T we have to make sure they are numbers
							or compare two strings we have to make sure they are strings !!!
	 * 
	 */
	
	public static <T extends Comparable<T>> T calculateMin(T t1, T t2) {
		
		if( t1.compareTo(t2) < 0 )
			return t1;
		
		return t2;
		
	}
	
	public static void main(String[] args) {
		
		System.out.println( calculateMin(100, 23));
		
	}
}
