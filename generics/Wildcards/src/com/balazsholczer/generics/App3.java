package com.balazsholczer.generics;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class App3 {

	/**
	 * 
	 * 3.) LOWER BOUNDED WILDCARDS  "? super T"
		
		You can specify an upper bound or a lower bound but cannot specify both 

			If you use '? super Integer' -> you can use any super type of Integer so Number or Object as well

		List<? super Integer> l = new ArrayList<Integer>();
		List<? super Integer> l = new ArrayList<Object>();
		List<? super Integer> l = new ArrayList<Number>();

		Reading --> not guaranteed an Integer because it may be pointing to a Number
				The only guarantee is an Object or a subclass of object 
						~ you can cast to Object !!!
						
		Writing --> you can not add Object or Serializable to a List<? super Number> even though
						Number extends Object and Serializable
						
						You can add Integer or Double or ... but using List<Double> would
								be too restrictive !!!
	 * 
	 * 
	 */ //EditorFactory   ViewerFactory  extends AbstarctFactory
	
	public static void show(List<? super Number> list) {
		
		list.add(new Float(1));
		
		for(Object o : list)
			System.out.println(o);
		
	}
	
	public static void main(String[] args) {
		
		List<Serializable> list = new ArrayList<>();
		list.add("Adam");
		list.add("Joe");
		list.add("Joel");
		App3.show(list);
		
		
	}
}
