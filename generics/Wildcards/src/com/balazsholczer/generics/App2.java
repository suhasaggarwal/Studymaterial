package com.balazsholczer.generics;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.List;

public class App2 {

	/**
	 * 2.) UPPER BOUNDED WILDCARDS	"? extends T"

		We want to use the previous method in a generic form so for List<Rectangle> or for List<Circle>
			and want to make it work	// both of them extends Shape !!!		

		drawAll(List<? extends Shape> l)

			Now this method accepts list of any subclass of Shape so
				we can use it for List<Circle> or List<Rectangle> as well

		Shape is the upper bound of the wildcard
			We can print BUT we can not add for example Circle !!!

				l.add(new Circle()) compile time error !!! we can read but cannot add item because
						the ? extends Shape is unknown subtype of Shape 
							Compiler does not know whether it is a Circle or a Rectangle

			+ it is an upper bound

			List<? extends Number> l = new ArrayList<Integer>();
			List<? extends Number> l = new ArrayList<Double>();
			List<? extends Number> l = new ArrayList<Number>();
				All the subclasses are valid here
		
				You can READ only List<Number> cannot read List<Double> because it may be a List<Integer>
					or List<Number> as well

				You can't add anything -> can't add double because it may be a List<Integer> !!!
			YOU CANT ADD ANY OBJECT TO A '? EXTENDS T' BECAUSE YOU CAN'T GUARANTEE WHAT KIND
				OF LIST IT IS REALLY POINTING TO
	 * 
	 */
	

	public static void sum(List<? extends Number> list) {
		double sum = 0.0;
		
		for(Number n : list) {
			sum = sum + n.doubleValue();
		}
		
		System.out.println("The sum is: " + sum);
	}
	
	public static void main(String[] args) {

		sum( Arrays.asList(1.1,2.2,3.3,4.5));
		
	}
}
