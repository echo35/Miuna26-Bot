package ch.echo35.java.bots.miuna26;

import ch.echo35.java.bots.miuna26.Abstraction.Neural.ShortMemory;
import ch.echo35.java.bots.miuna26.Abstraction.Word;
import ch.echo35.java.bots.miuna26.Abstraction.WordTypes.Verb;
import ch.echo35.java.bots.miuna26.Utilities.Dictionary;
import ch.echo35.java.bots.miuna26.Utilities.Info;
import ch.echo35.java.bots.miuna26.Utilities.Utils;

import java.util.ArrayList;
import java.util.Scanner;

/**
 * Created by echo35 on 9/15/16.
 */
public class MainDRV {
	public static void main(String[] args) {
		Utils.log(String.format("Starting %s version %s", Info.getName(), Info.getVersion()));
//		Utils.disabledLogLevels.add(LogLevel.DEBUG);
		initDictionaries();
		ShortMemory mem = new ShortMemory();
		Scanner input = new Scanner(System.in);
		while (input.hasNext()) {
			String string = input.nextLine();
			if (string.startsWith("set ")) {
				mem.addPhrase(string.substring(4));
				Utils.log("OK");
				continue;
			}

			Utils.proofread(mem.askQuestion(string));
		}
	}

	private static void initDictionaries() {
		Dictionary dictionary1 = new Dictionary();
		String words = "";

		for (String interrogative_pronoun : "who; what; when; why; where; how".split("; "))
			words += (interrogative_pronoun + ":interrogator/");
		for (String preposition : "aboard; about; above; across; after; against; along; amid; among; anti; around; as; at; before; behind; below; beneath; beside; besides; between; beyond; but; by; concerning; considering; despite; down; during; except; excepting; excluding; following; for; from; in; inside; into; like; minus; near; of; off; on; onto; opposite; outside; over; past; per; plus; regarding; round; save; since; than; through; to; toward; towards; under; underneath; unlike; until; up; upon; versus; via; with; within; without".split("; "))
			words += (preposition + ":preposition/");
		for (String possessive : "my; your; his; her; their; our".split("; "))
			words += (possessive + ":possessive/");
		for (String pronoun : "i; me; you; he; she; him; her; they; them; us; we; it".split("; "))
			words += (pronoun + ":pronoun/");
		for (String article : "this; that; those; the; a; an".split("; "))
			words += (article + ":article/");
		for (String adverb : "enough; sort of; kind of; finally; basically; hopefully".split("; "))
			words += (adverb + ":adverb/");
		for (String adjective : "awesome; happy; tired; sleepy; sick; sore; worse; ill; ok; glad; better; cool; over; new; kind; interesting; additive; fun".split("; "))
			words += (adjective + ":adjective/");
		for (String noun : "echo; school; class; english; cookie; mom; name; ramen; project; throat; order; weekend; night; prototype; synth; idea; user; interface; pattern; generator; layer; stuff; wave; sound".split("; "))
			words += (noun + ":noun/");
		for (String proper_noun : "matthew; kyle; elmood".split("; "))
			words += (proper_noun + ":proper_noun/");
		for (String conjunction : "for; and; nor; but; or; yet; so".split("; "))
			words += (conjunction + ":conjunction/");

		ArrayList<Word> wordList = Utils.convertWordList(words.split("/"));

		for (Verb verb : initVerbs())
			wordList.add(verb);
		dictionary1.addWords(wordList);
	}

	public static ArrayList<Verb> initVerbs() {
		ArrayList<Verb> verbList = new ArrayList<>();

		Verb.AUXILIARY = new Verb("to be", "to be; been; being; am; are; is; was; were".split("; "), true, true);
		verbList.add(Verb.AUXILIARY);
		verbList.add(new Verb("to feel", "to feel; felt; feeling; feel; feel; feels; felt; felt; felt".split("; ")));
		verbList.add(new Verb("to make", "to make; made; making; make; makes; made; made; made".split("; ")));
		verbList.add(new Verb("to become", "to become; become; becoming; become; becomes; became; became; became".split("; ")));
		verbList.add(new Verb("to eat", "to eat; eaten; eating; eat; eat; eats; ate; ate".split("; ")));
		verbList.add(new Verb("to drink", "to drink; drunk; drinking; drink; drink; drinks; drank; drank".split("; ")));
		verbList.add(new Verb("to drive", "to drive; driven; driving; drive; drive; drives; drove; drove".split("; ")));
		verbList.add(new Verb("to come", "to come; come; coming; come; come; comes; came; came".split("; ")));
		verbList.add(new Verb("to go", "to go; gone; going; go; go; goes; went; went".split("; ")));
		verbList.add(new Verb("to do", "to do; done; doing; do; do; does; did; did".split("; "), false, true));
		verbList.add(new Verb("to like", "to like; liked; liking; like; like; likes; liked; liked".split("; ")));

//		Utils.log(verbList.get(8).getConjugation());

		return verbList;
	}
}
