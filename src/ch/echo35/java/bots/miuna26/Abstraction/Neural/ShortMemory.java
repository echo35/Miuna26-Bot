package ch.echo35.java.bots.miuna26.Abstraction.Neural;

import ch.echo35.java.bots.miuna26.Grammar;
import ch.echo35.java.bots.miuna26.Utilities.LogLevel;
import ch.echo35.java.bots.miuna26.Utilities.Utils;

import java.util.Arrays;
import java.util.HashMap;
import java.util.List;

/**
 * Created by echo35 on 9/15/16.
 */
public class ShortMemory {
	private HashMap<String, String> phrases = new HashMap();

	public HashMap<String, String> getPhrases() {
		return phrases;
	}

	public void addPhrase(String phrase) {
		this.phrases.put(phrase, getClauseRule(phrase)[0]);
	}

	public String askQuestion(String question) {
		for (String phrase : phrases.keySet()) {
			List<String> rules = Arrays.asList(getClauseRule(question));
			Utils.log(LogLevel.DEBUG, "Question Rules: " + rules);
			for (String rule : rules) {
				Utils.log(LogLevel.DEBUG, "Testing Rule: " + rule);
				if (rule.equals("INTERROGATOR:QUIZ:SUBJECT:QUIZ:OBJECT")) {
					Utils.log(LogLevel.DEBUG, "Subject: " + getComponent(phrase, "SUBJECT") + "|" + getComponent(rule, question,
							"SUBJECT"));
					Utils.log(LogLevel.DEBUG, "Object: " + getComponent(phrase, "OBJECT") + "|" + getComponent(rule, question, "OBJECT"));
					for (int i = 0; i < phrase.split(":").length; i++) {
						if (getComponent(rule, question, "SUBJECT").equalsIgnoreCase(getComponent(phrase, "SUBJECT"))
								&& getComponent(rule, question, "OBJECT").equalsIgnoreCase(getComponent(phrase, "OBJECT")))
							return getComponent(phrase, "VERB");
					}
				} else if (rule.equals("INTERROGATOR:QUIZ:SUBJECT:VERB")
						|| rule.equals("INTERROGATOR:VERB:SUBJECT")
						|| rule.equals("INTERROGATOR:VERB:SUBJECT:QUIZ")) {
					Utils.log(LogLevel.DEBUG, "Subject: " + getComponent(phrase, "SUBJECT") + "|" + getComponent(rule, question, "SUBJECT"));
					Utils.log(LogLevel.DEBUG, "Verb: " + getComponent(phrase, "VERB") + "|" + getComponent(rule, question, "VERB"));
					for (int i = 0; i < phrase.split(":").length; i++) {
						if (getComponent(rule, question, "SUBJECT").equalsIgnoreCase(getComponent(phrase, "SUBJECT"))
								&& getComponent(rule, question, "VERB").equalsIgnoreCase(getComponent(phrase, "VERB")))
							return getComponent(phrase, "OBJECT");
					}
				} else if (rule.equals("INTERROGATOR:VERB:OBJECT")) {
					Utils.log(LogLevel.DEBUG, "Object: " + getComponent(phrase, "OBJECT") + "|" + getComponent(rule, question, "OBJECT"));
					Utils.log(LogLevel.DEBUG, "Verb: " + getComponent(phrase, "VERB") + "|" + getComponent(rule, question, "VERB"));
					for (int i = 0; i < phrase.split(":").length; i++) {
						if (getComponent(rule, question, "OBJECT").equalsIgnoreCase(getComponent(phrase, "OBJECT"))
								&& getComponent(rule, question, "VERB").equalsIgnoreCase(getComponent(phrase, "VERB")))
							return getComponent(phrase, "SUBJECT");
					}
				} else if (rule.equalsIgnoreCase("QUIZ:SUBJECT:VERB:OBJECT")
						|| rule.equalsIgnoreCase("VERB:OBJECT:SUBJECT")
						|| rule.equalsIgnoreCase("VERB:SUBJECT:OBJECT")) {
					Utils.log(LogLevel.DEBUG, "Subject: " + getComponent(phrase, "SUBJECT") + "|" + getComponent(rule, question, "SUBJECT"));
					Utils.log(LogLevel.DEBUG, "Object: " + getComponent(phrase, "OBJECT") + "|" + getComponent(rule, question, "OBJECT"));
					Utils.log(LogLevel.DEBUG, "Verb: " + getComponent(phrase, "VERB") + "|" + getComponent(rule, question, "VERB"));
					for (int i = 0; i < phrase.split(":").length; i++) {
						if (getComponent(rule, question, "SUBJECT").equalsIgnoreCase(getComponent(phrase, "SUBJECT"))
								&& getComponent(rule, question, "OBJECT").equalsIgnoreCase(getComponent(phrase, "OBJECT"))
								&& getComponent(rule, question, "VERB").equalsIgnoreCase(getComponent(phrase, "VERB")))
							return "yes";
					}
				}
			}

			String rule = phrases.get(phrase);
			if (rule.equalsIgnoreCase("QUIZ:SUBJECT:VERB:OBJECT")
					|| rule.equalsIgnoreCase("VERB:OBJECT:SUBJECT")
					|| rule.equalsIgnoreCase("VERB:SUBJECT:OBJECT")) {
				return "no";
			}
		}

		return "I don't know";
	}

	public String getComponent(String phrase, String component) {
		return getComponent(phrases.get(phrase), phrase, component);
	}

	public static String getComponent(String rule, String phrase, String component) {
		if (rule.length() != 0) {
			String filter = Grammar.filterClauseByRule(rule, phrase);
			return filter.split(":")[Arrays.asList(rule.split(":")).indexOf(component)];
		}

		return "-1";
	}


	public static String[] getClauseRule(String phrase) {
		return Grammar.getClauseFormat(phrase);
	}

}
