package com.balazsholczer.generics;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

public class App {

	/**
	 * 1.) WILDCARDS

		The problems and the most important fact about generics

			Collection<String> c1 = new ArrayList<>();
			Collection<Object> c2 = c1  it is not valid !!!
				String is an Object but a Collection<String> is not a Collection<Object> !!!
				

		So a List<Employee> is not a List<Person> even when the Employee class extends the Person class
			THIS IS WHY WILDCARDS HAVE CAME TO BE 

		print(Collection<Object> c)
			for i in c
				print i

		It is not generic in the sense that we can not print list of integers or list of doubles
			or list of custom objects -> we have to cast them to Object before calling that method

			COLLECTION OF OBJECT IS NOT A SUPERTYPE OF ALL KINDS OF COLLECTIONS !!!
				The supertype of all lkinds of collections -> wildcard

		print(Collection<?> c)
			for i in c:
				print i		we can do it we can print out whatever we want, but we cannot insert to that 
							collection whatever we want because we don't know what type will be passed in
								Sole exception: NULL

		c.add(new Object()) this will cause compile time error 


		public interface Shape() {
			public void draw();
		}

		class Circle extends Shape {
			draw:
				print "Drawing a circle...";
		}
		
		class Rectangle extends Shape {
			draw:
				print "Drawing a rectangle...";
		}
		

		drawAll(List<Shape> l)
			for i in l
				i.draw()

			This is going to work only for Shapes but not for classes that implements that given
				Shape interface
					--> we can not call this method on List<Rectangle> for example
						THIS IS WHY BOUNDED WILDCARDS HAVE CAME TO BE !!!
		

		-------------------------------------------------------------------------------------------------------	
	 * 
	 * 
	 * 
	 */
	
	public static void print(Collection<? extends Shape> c) {
		for(Object o : c)
			System.out.println(o);
	}
	
	
	public static void main(String[] args) {
		
		List<Circle> l = new ArrayList<>();
		
		App.print(l);
		
	}
}

class Circle implements Shape {

	@Override
	public void draw() {
		
	}
	
}

interface Shape {
	public void draw();
}
