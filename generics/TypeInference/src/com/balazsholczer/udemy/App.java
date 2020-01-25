package com.balazsholczer.udemy;

import java.util.ArrayList;
import java.util.List;

public class App {

	/**
	 * - compiler's ability to look at each method invocation and corresponding declaration to 
	 * 		determine the type argument/arguments ( such as T ) that make the invocation applicable
	 * 
	 * - the inference algorithm determines: the types of the arguments 
	 * 				+ the type that the result is being assigned or returned if available !!!
	 * 
	 * - the inference algorithm tries to find the most specific type that works with all of the arguments !!!
	 * 
	 * 		public <T> T getData(T t1, T t2){
	 * 			return t2;
	 * 		}
	 * 
	 * 		Serializable s = getData( "Hello world" , new ArrayList<String>() );   !!!
	 * 
	 * 
	 * 	Because of the type inference algorithm, we can use instantiation like this:
	 * 
	 * 			List<String> list = new ArrayList<>();
	 * 
	 * 	Generic methods and type interference:
	 * 		 Enables us to invoke a generic method as you would an ordinary method, 
	 * 				without specifying a type between angle brackets
	 */
	
	public static <T> void addStore(T t, List<Bucket<T>> list) {
		Bucket<T> bucket = new Bucket<>();
		bucket.setItem(t);
		list.add(bucket);
		System.out.println(t.toString()+" has been added to the list...");
	}
	
	public static void main(String[] args) {
		
		List<Bucket<String>> list = new ArrayList<>();
		
		App.addStore("Adam", list);
		
		// "type witness"
		App.<String>addStore("Daniel", list);
		
	}
}

class Bucket<T> {
	
	private T item;
	
	public T getItem() {
		return this.item;
	}
	
	public void setItem(T item) {
		this.item = item;
	}
}
