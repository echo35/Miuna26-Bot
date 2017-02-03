package ch.echo35.java.bots.miuna26.Utilities;

import ch.echo35.java.bots.miuna26.Abstraction.Word;
import ch.echo35.java.bots.miuna26.Abstraction.WordType;

import java.util.ArrayList;

/**
 * Created by echo35 on 9/15/16.
 */
public class Utils {

	public static ArrayList<LogLevel> disabledLogLevels = new ArrayList<>();

	public static void proofread(Object line) {
		String string = line.toString(); //.replace("you", "T|-|()\\_/").replace("your", "\\_/|2").replace("i", "you").replace("me", "you").replace("my", "your").replace("\\_/|2", "my").replace("T|-|()\\_/", "I");
		log(LogLevel.INFO, string);
	}

	public static void log(Object line) {
		log(LogLevel.INFO, line);
	}

	public static void log(LogLevel logLevel, Object line) {
		if (!disabledLogLevels.contains(logLevel)) {
			String formattedLine = String.format("[%s] %s", logLevel.toString(), line.toString());
			System.out.println(formattedLine);
		}
	}

	public static ArrayList<Word> convertWordList(String[] wordList) {
		ArrayList<Word> convertedWordList = new ArrayList<>();
		for (String definition : wordList) {
			String string = definition.split(":")[0].toLowerCase();
			WordType type = WordType.valueOf(definition.split(":")[1].toUpperCase());
			Word word = new Word(string, type);
			convertedWordList.add(word);
		}
		return convertedWordList;
	}
}
