package com.balazsholczer.udemy;

import java.nio.channels.ShutdownChannelGroupException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

class Store<T> {
	
	private T item;
	
	public void setStoreItem(T item) {
		this.item = item;
	}
	
	public T getItem() {
		return this.item;
	}
	
	@Override
	public String toString() {
		return ""+this.item.toString();
	}
}

class Hashtable<K, V> {
	
	private K key;
	private V value;
	
	public Hashtable(K key, V value) {
		this.key = key;
		this.value = value;
	}
	
	@Override
	public String toString() {
		return key.toString()+"-"+value.toString();
	}
}

public class App {
	
	public static void main(String[] args) {
		
		// raw type AVOID IT !!!
		//Store store = new Store();
		//store.setStoreItem(45);
		
//		Store<Integer> stringStore = new Store<>();// "diamond operator"
//		stringStore.setStoreItem(45);
//		
//		Integer item =  stringStore.getItem();
//		
//		System.out.println(item);
		
		//Hashtable<String, Integer> hashtable = new Hashtable<>("Hello world!", 55);
		//System.out.println(hashtable);
		
	}
}
