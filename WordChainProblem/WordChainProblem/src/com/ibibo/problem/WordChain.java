package com.ibibo.problem;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Queue;
import java.util.Set;

public class WordChain {

	public static void main(String [] args) throws IOException
	{
		 Map<Integer, List<String>> dictionary = new HashMap<Integer, List<String>>();
		 BufferedReader reader = null;
		 try
		 {
			 reader = new BufferedReader(new InputStreamReader(
					 WordChain.class
						.getResourceAsStream("/WordsEn.txt")));
			 String line = null;
			 while((line = reader.readLine())!= null)
			 {
				 if(dictionary.containsKey(line.length()))
				 {
					 dictionary.get(line.length()).add(line);
				 }
				 else
				 {
					 dictionary.put(line.length(), new ArrayList<String>());
					 dictionary.get(line.length()).add(line);
				 }
			 }
		 }
		 finally
		 {
			 reader.close();
		 }
		 List<String> chain = getWordChain("cat", "dog", dictionary);
		 System.out.println(chain);
	}

	public static List<String> getWordChain(String startWord, String endWord,
			Map<Integer, List<String>> dictionary) {
		Queue<WordNode> queue = new LinkedList<WordNode>();
		Set<String> wordSet = new HashSet<String>();
		queue.offer(new WordNode(startWord, null));
		wordSet.add(startWord);
		WordNode endWordNode = null;
		while (!queue.isEmpty()) {
			WordNode currWordNode = queue.poll();
			if (currWordNode.getCurrentWord().equals(endWord)) {
				endWordNode = currWordNode;
				break;
			} else {
				List<String> candidateWords = dictionary.get(currWordNode
						.getCurrentWord().length());
				for (String candidateWord : candidateWords) {
					if (!wordSet.contains(candidateWord)) {
						int distance = 0;
						for (int i = 0; i < candidateWord.length(); i++) {
							if (candidateWord.charAt(i) != currWordNode
									.getCurrentWord().charAt(i)) {
								distance++;
								if (distance > 1) {
									break;
								}
							}
						}
						if (distance == 1) {
							queue.offer(new WordNode(candidateWord,
									currWordNode));
							wordSet.add(candidateWord);
						}
					}
				}
			}
		}

		List<String> wordChain = new ArrayList<String>();
		if (endWordNode != null) {
			WordNode currNode = endWordNode;
			while (currNode != null) {
				wordChain.add(currNode.getCurrentWord());
				currNode = currNode.getPreviousWord();
			}
		}

		return wordChain;

	}
}
