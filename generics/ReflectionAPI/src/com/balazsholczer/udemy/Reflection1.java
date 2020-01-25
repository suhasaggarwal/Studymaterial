package com.balazsholczer.udemy;

import java.io.Serializable;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;
import java.lang.reflect.Field;
import java.lang.reflect.Method;

public class Reflection1 {

	/**
	 * 
	 * What is reflection?
	 * 
	 * Reflection is a language's ability to inspect and dynamically call
	 * classes, methods, attributes
	 * 
	 * getClass() -> we can get the class ... we do not know at compile time
	 * 
	 * Why to use reflection?
	 * 
	 * Reflection is important since it lets you write programs that do not have
	 * to "know" everything at compile time, making them more dynamic, since
	 * they can be tied together at runtime !!!
	 * 
	 * - lots of modern frameworks uses reflection extensively for this reason -
	 * one very common use case in Java is the usage with annotations
	 * 
	 * JUnit -> will use reflection to look through your classes for methods
	 * tagged with the @Test annotation -> will then call them when running the
	 * unit test !!!
	 * 
	 */

	public static void main(String[] args) {

		// in order to inspect the class we have to get a Class instance

		Class<Person> personClass = null;

		try {
			personClass = (Class<Person>) Class.forName("com.balazsholczer.udemy.Person");
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		}
		
//		Class[] interfaces = personClass.getInterfaces();
//		
//		for(Class c : interfaces)
//			System.out.println(c.getName());
//		
//		System.out.println( personClass.getSuperclass().getName() );
		
//		Method[] fields = personClass.getDeclaredMethods();
//		
//		for(Method f : fields) {
//			f.setAccessible(true);
//			System.out.println(f.getName());
//		}
		
		// System.out.println(personClass.getPackage());
		//
		// Field[] fields = personClass.getFields();
		//
		// for(Field field : fields)
		// System.out.println(field.getName()+" - "+field.getType());

//		Method[] methods = personClass.getMethods();
//		
//		for(Method m : methods)
//			System.out.println(m.getName()+" return type: "+m.getReturnType());
		
		Method[] methods = personClass.getMethods();
		
		for(Method m :methods) {
			if( m.isAnnotationPresent(MyAnnotation.class) )
				System.out.println(m.getName());
				
		}
		
	}
}

@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.METHOD)
@interface MyAnnotation {
	public String name();
}

class Person  {

	private String name;
	private int age;

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public int getAge() {
		return age;
	}

	public void setAge(int age) {
		this.age = age;
	}

	@MyAnnotation(name="myAnnotation")
	public String returnName() {
		return this.name+" is the name!";
	}
}