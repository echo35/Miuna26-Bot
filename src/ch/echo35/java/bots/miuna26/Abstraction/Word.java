package ch.echo35.java.bots.miuna26.Abstraction;

/**
 * Created by echo35 on 9/15/16.
 */
public class Word {
	private String string;
	private WordType wordType;

	public Word(String string, WordType type) {
		this.string = string;
		this.wordType = type;
	}

	public String getString() {
		return this.string;
	}

	public WordType getWordType() {
		return this.wordType;
	}

	public String toString() {
		return getString();
	}
}
