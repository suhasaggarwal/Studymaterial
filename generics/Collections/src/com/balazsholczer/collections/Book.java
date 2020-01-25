package com.balazsholczer.collections;

public class Book {
//implements Comparable<Book>{

	private String authorName;
	private String title;
	private int numOfPages;

	public Book() {

	}

	public Book(String authorName, String title, int numOfPages) {
		this.authorName = authorName;
		this.title = title;
		this.numOfPages = numOfPages;
	}

	public String getAuthorName() {
		return authorName;
	}

	public void setAuthorName(String authorName) {
		this.authorName = authorName;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public int getNumOfPages() {
		return numOfPages;
	}

	public void setNumOfPages(int numOfPages) {
		this.numOfPages = numOfPages;
	}

	@Override
	public String toString() {
		return this.authorName + "-" + this.title + "-" + this.numOfPages;
	}

//	@Override
//	public int compareTo(Book otherBook) {
//		return -Integer.compare(this.numOfPages, otherBook.getNumOfPages());
//	}
}
