package com.balazsholczer.introduction;

public class App {

	public static void main(String[] args) {
		
		/**
		 * - in software engineering bugs are simply a fact of life
		 * - some bugs are easier to detect than others
		 * 
		 * 		Compile time bugs -> can be easily detected
		 * 			Compiler's error messages helps to figure out what the problem is 
		 * 				~ we can fix it !!!
		 * 
		 * 		Runtime bugs -> more problematic !!!
		 * 			Don't always surface immediately and it may be
		 * 				 at a point in the program that is far from the actual 
		 * 					cause of the problem
		 * 
		 * 
		 * 	SO WHY GENERICS ARE IMPORTANT?
		 * 		Add stability to your code by making more of your bugs 
		 * 			detectable at compile time !!!
		 * 
		 * - generics enable types (classes and interfaces) to be parameters 
		 * 		when defining classes, interfaces and methods
		 * 			+ we can re-use the same code with different inputs
		 * 
		 * 		public void draw(Car car)   values !!!
		 * 		public void draw( T t)  types !!!
		 * 
		 * 	Generics has many benefits over non-generic code:
		 * 
		 * 		1.) stronger type checks at compile time
		 * 			 If the code violates type safety -> compiler will warn us
		 * 				-> fixing compile time errors are easier to fix !!!
		 * 
		 * 		2.) we can eliminate casting !!!
		 * 				
		 * 				List nameList = new ArrayList();
		 * 				String name = (String) list.get(0);  not a good solution
		 * 
		 * 				List<String> list = new ArrayList<>();
		 * 				String name2 = list.get(0);  good solution
		 * 				
		 * 
		 * 		3.) We can implement generic algorithms, we can reuse them
		 * 				For example: sorting strings / doubles / integers
		 * 						+ easier to read generic code !!!
		 * 		
		 * 
		 * 
		 */
		
	}
}
