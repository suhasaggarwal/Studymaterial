package com.balazsholczer.collections;

import java.util.Comparator;

public class BookComparator implements Comparator<Book> {

	@Override
	public int compare(Book b1, Book b2) {
		return b1.getAuthorName().compareTo(b2.getAuthorName());
	}
}
