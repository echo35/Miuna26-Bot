package ch.echo35.java.bots.miuna26;

import ch.echo35.java.bots.miuna26.Abstraction.Word;
import ch.echo35.java.bots.miuna26.Abstraction.WordType;
import ch.echo35.java.bots.miuna26.Abstraction.WordTypes.Verb;
import ch.echo35.java.bots.miuna26.Utilities.Dictionary;
import ch.echo35.java.bots.miuna26.Utilities.LogLevel;
import ch.echo35.java.bots.miuna26.Utilities.Utils;

import java.util.ArrayList;
import java.util.Arrays;

/**
 * Created by echo35 on 9/15/16.
 */
public final class Grammar {
	private static String validSubjects = ":NOUN:VERB:PROPER_NOUN:PRONOUN:INTERROGATOR:";
	private static String validObjects = ":NOUN:VERB:PROPER_NOUN:PRONOUN:ADJECTIVE:";

	public static String[] clauseRules = {
			"INTERROGATOR:QUIZ:SUBJECT:QUIZ:OBJECT",
			"INTERROGATOR:QUIZ:SUBJECT:VERB",
			"INTERROGATOR:VERB:SUBJECT:QUIZ",
			"QUIZ:SUBJECT:VERB:OBJECT",
			"VERB:OBJECT:SUBJECT",
			"VERB:SUBJECT:OBJECT",
			"INTERROGATOR:VERB:SUBJECT",
			"INTERROGATOR:VERB:OBJECT",
			"SUBJECT:VERB:OBJECT"
	};

	public static String[] getClauseFormat(String phrase) {
		String matches = "";
		phrase = stripClause(phrase);

		Utils.log(LogLevel.DEBUG, "Phrase: " + phrase);
		for (int i = 0; i < clauseRules.length; i++) {
			if (Grammar.matchesClauseFormat(clauseRules[i], phrase) > 0.8)
				matches += "_" + clauseRules[i];
		}

		Utils.log(LogLevel.DEBUG, "Matches: " + matches);
		return matches.substring(1).split("_");
	}

	public static String stripClause(String clause) {
		return stripClause(clause, new WordType[] {WordType.ARTICLE, WordType.PREPOSITION, WordType.ADVERB, WordType.POSSESSIVE});
	}

	public static String stripClause(String clause, WordType[] exclude) {
		for (String string : clause.split(":"))
			for (Word word : Dictionary.search(string))
				if (Arrays.asList(exclude).contains(word.getWordType()))
					clause = clause.replace(string, "");
		while (clause.contains("::"))
			clause = clause.replaceAll("::", ":");
		if (clause.startsWith(":")) clause = clause.substring(1);
		if (clause.endsWith(":")) clause = clause.substring(clause.length() - 1);
		return clause;
	}

	public static double matchesClauseFormat(String rule, String clause) {
		clause = stripClause(clause);
		double score = 0;
		int cindex = 0;
		int rindex = 0;
		while (true) {
			if (rindex >= rule.replace("::", ":").split(":").length || cindex >= clause.split(":").length)
				break;
			String rtype = rule.split(":")[rindex];
			String cstring = clause.split(":")[cindex];
			ArrayList<Word> cword = Dictionary.search(cstring.split(" ")[cstring.split(" ").length-1]);
			boolean zero = false;
			if (rtype.length() == 0) {
				rtype = rule.split(":")[rindex+1];
				zero = true;
			}

			for (Word word : cword) {
				if (rtype.equals("SUBJECT") && validSubjects.contains(":" + word.getWordType().toString() + ":") ||
						rtype.equals("OBJECT") && validObjects.contains(":" + word.getWordType().toString() + ":") ||
						(rtype.equals("QUIZ") && word.getWordType().toString() == "VERB" && ((Verb) word).isQuizzitive) ||
						rtype.equals(word.getWordType().toString())) {
//					Utils.log("cmp " + cstring + " (" + word.getWordType().toString() + "), " + rtype + " -> PASS");
					score++;
					break;
				}
//				Utils.log("cmp " + cstring + " (" + word.getWordType().toString() + "), " + rtype + " -> FAIL");
			}

			if (zero)
				rindex--;

			cindex++;
			rindex++;
		}

//		Utils.log("rule " + rule + ", score: " + score/(double) (rule.replaceAll("::", ":").split(":").length));
		return score/(double) (rule.replaceAll("::", ":").split(":").length);
	}

	public static String filterClauseByRule(String rule, String clause) {
		clause = stripClause(clause);
		String filteredClause = rule;
		int cindex = 0;
		int rindex = 0;
		while (true) {
			if (rindex >= rule.replace("::", ":").split(":").length || cindex >= clause.split(":").length)
				break;

			String rtype = rule.split(":")[rindex];
			String cstring = clause.split(":")[cindex];
			ArrayList<Word> cword = Dictionary.search(cstring);
			boolean zero = false;
			if (rtype.length() == 0) {
				rtype = rule.split(":")[rindex+1];
				zero = true;
			}

			for (Word word : cword) {
				if (rtype.equals("SUBJECT") && validSubjects.contains(":" + word.getWordType().toString() + ":"))
					filteredClause = filteredClause.replace(rtype, word.getString());
				else if (rtype.equals("OBJECT") && validObjects.contains(":" + word.getWordType().toString() + ":"))
					filteredClause = filteredClause.replace(rtype, word.getString());
				else if (rtype.equals(word.getWordType().toString()))
					filteredClause = filteredClause.replace(rtype, word.getString());
			}

			if (zero)
				rindex--;

			cindex++;
			rindex++;
		}

		return filteredClause;
	}

//	public static double matchesClauseFormat(String clause, String dissect) {
//		double score = 0;
//		int cindex = 0;
//		int dindex = 0;
//		while (true) {
//			if (dindex >= dissect.split(":").length)
//				break;
//
//			String cword = clause.split(":")[cindex];
//			if (cword.length() == 0) {
//				cword = clause.split(":")[cindex+1];
//				cindex--;
//			}
//
//			String dword = dissect.split(":")[dindex];
//
//			Utils.log("cmp " + cword + ", " + dword);
//
//			if (cword.equals(dword)) score++;
//
//			cindex++;
//			dindex++;
//		}
//
//		return score/(double) (clause.replaceAll("::", ":").split(":").length);
//	}

	public static double getClauseComponent(String clause, String dissect, String rule, String component) {
		double score = 0;
		int cindex = 0;
		int dindex = 0;
		while (true) {
			if (dindex >= dissect.split(":").length)
				break;

			String cword = clause.split(":")[cindex];
			if (cword.length() == 0) {
				cword = clause.split(":")[cindex+1];
				cindex--;
			}

			String dword = dissect.split(":")[dindex];

			Utils.log("cmp " + cword + ", " + dword);

			if (cword.equals(dword)) score++;

			cindex++;
			dindex++;
		}

		return score/(double) (clause.replaceAll("::", ":").split(":").length);
	}

	public static String dissect(String phrase) {
		return dissect(phrase, new WordType[] {});
	}

	public static String dissect(String phrase, WordType[] excluded) {
		String dissectedPhrase = phrase.replace(":", ":");
		for (Dictionary dic : Dictionary.dictionaries) {
//			Utils.log(dic.getKnownWords().toString());
			for (String str_word : phrase.split(":")) {
				ArrayList<Word> words = dic.getWord(str_word);
				String results = "";
				for (Word word : words) {
					results += ", " + word.getWordType() + "/" + word.getString();
				}
				if (words.size() == 0) {
					Utils.log(words.size() + " results for '" + str_word + "'.");
					continue;
				}
				Utils.log(words.size() + " results for '" + str_word + "': " + results.substring(2));
				Word word = words.get(0); // Assumes one result.
				dissectedPhrase = dissectedPhrase.replace(str_word, (Arrays.asList(excluded).contains(word.getWordType()) ? "" : word.getWordType().toString()));
			}
		}
		return dissectedPhrase; //.substring(1, dissectedPhrase.length()-1);
	}
}
