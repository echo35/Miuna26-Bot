package ch.echo35.java.bots.miuna26.Utilities;

import ch.echo35.java.bots.miuna26.Abstraction.Word;
import ch.echo35.java.bots.miuna26.Abstraction.WordType;
import ch.echo35.java.bots.miuna26.Abstraction.WordTypes.Verb;

import java.util.ArrayList;

/**
 * Created by echo35 on 9/15/16.
 */
public class Dictionary {
	public static ArrayList<Dictionary> dictionaries = new ArrayList<>();

	private ArrayList<Word> knownWords = new ArrayList<>();

	public Dictionary() {
		dictionaries.add(this);
	}

	public void addWords(ArrayList<Word> words) {
		for (Word word : words) {
			knownWords.add(word);
		}
	}

	public ArrayList<Word> getKnownWords() {
		return knownWords;
	}

	public ArrayList<Word> getWord(String string) {
		ArrayList<Word> results = new ArrayList<>();
		for (Word word : knownWords) {
			if (word.getWordType() == WordType.VERB) {
				Verb verb = (Verb) word;
				for (String form : verb.getForms()) {
					if (form.equalsIgnoreCase(string.toLowerCase())) {
						if (!results.contains(word)) {
							results.add(word);
						}
					}
				}
			}
			else if (word.getString().equalsIgnoreCase(string.toLowerCase())) {
				results.add(word);
			}
		}
		return results;
	}

	public static ArrayList<Word> search(String string) {
		ArrayList<Word> results = new ArrayList<>();
		for (Dictionary dictionary : dictionaries)
			for (Word word : dictionary.getWord(string))
				results.add(word);

		return results;
	}
}
