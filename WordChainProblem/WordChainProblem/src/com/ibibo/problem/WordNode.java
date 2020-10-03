package com.ibibo.problem;

public class WordNode {

	private String currentWord;
	private WordNode previousWord;

	public String getCurrentWord() {
		return currentWord;
	}

	public WordNode getPreviousWord() {
		return previousWord;
	}

	public WordNode(String currentWord, WordNode previousWord) {
		super();
		this.currentWord = currentWord;
		this.previousWord = previousWord;
	}
}
