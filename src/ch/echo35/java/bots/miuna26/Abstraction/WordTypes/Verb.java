package ch.echo35.java.bots.miuna26.Abstraction.WordTypes;

import ch.echo35.java.bots.miuna26.Abstraction.Word;
import ch.echo35.java.bots.miuna26.Abstraction.WordType;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;

/**
 * Created by echo35 on 9/15/16.
 */
public class Verb extends Word {
	public static Verb AUXILIARY;
	private boolean isAuxiliary = false;
	public boolean isQuizzitive = false;

	private HashMap<String, String[]> forms = new HashMap<>();

	public Verb(String string, String[] forms, boolean isAuxiliary, boolean isQuizzitive)
	{
		super(string.toLowerCase(), WordType.VERB);
		this.isAuxiliary = isAuxiliary;
		this.isQuizzitive = isQuizzitive;
		setForms(forms);
	}

	public Verb(String string, String[] forms) {
		this(string, forms, false, false);
	}

	public ArrayList<String> getForms() {
		ArrayList<String> output = new ArrayList<>();
		for (String[] tense : forms.values()) {
			for (String form : tense) {
				output.add(form);
			}
		}
		return output;
	}

	public String[] getForm(String form) {
		return this.forms.get(form);
	}

	public void setForms(String[] forms) {
		String[] order_basic = ("infinitive; participle; gerund").split("; ");
		for (int i = 0; i < order_basic.length; i++) {
			this.forms.put(order_basic[i], new String[]{forms[i]});
		}

		String infinitive = forms[0].replace("to ", "");
		String participle = forms[1];
		String gerund = forms[2];
		String form1 = forms[3];
		String form2 = forms[4];
		String form3 = forms[5];
		String form4 = forms[6];
		String form5 = forms[7];
		String normal_indicative_present = String.format("%s; %s; %s; %s; %s; %s", form1, form2, form3, form2, form2, form2);
		String normal_indicative_past = String.format("%s; %s; %s; %s; %s; %s", form4, form5, form4, form5, form5, form5);
		String normal_indicative_future = "";
		String normal_indicative_perfect = "";
		String normal_indicative_pluperfect = "";
		String normal_indicative_future_perfect = "";
		String normal_subjunctive_present = "";
		String normal_subjunctive_imperfect = "";
		String normal_subjunctive_perfect = "";
		String normal_subjunctive_pluperfect = "";
		String normal_conditional_present = "";
		String normal_conditional_perfect = "";
		String progressive_indicative_present = "";
		String progressive_indicative_past = "";
		String progressive_indicative_future = "";
		String progressive_indicative_perfect = "";
		String progressive_indicative_pluperfect = "";
		String progressive_indicative_future_perfect = "";
		String progressive_conditional_present = "";
		String progressive_conditional_perfect = "";

		for (int i = 0; i < 6; i++) {
			normal_indicative_future += "; will " + infinitive;
			normal_indicative_perfect += "; " + ((i == 2) ? "has" : "have") + " " + participle;
			normal_indicative_pluperfect += "; had " + participle;
			normal_indicative_future_perfect += "; will have " + participle;
			normal_subjunctive_present += "; " + infinitive;
			normal_subjunctive_imperfect += "; " + form5;
			normal_subjunctive_perfect += "; have " + participle;
			normal_subjunctive_pluperfect += "; had " + participle;
			normal_conditional_present += "; would " + infinitive;
			normal_conditional_perfect += "; would have " + participle;
		}

		this.forms.put("Normal Indicative Present", normal_indicative_present.split("; "));
		this.forms.put("Normal Indicative Past", normal_indicative_past.split("; "));
		this.forms.put("Normal Indicative Future", normal_indicative_future.substring(2).split("; "));
		this.forms.put("Normal Indicative Perfect", normal_indicative_perfect.substring(2).split("; "));
		this.forms.put("Normal Indicative Pluperfect", normal_indicative_pluperfect.substring(2).split("; "));
		this.forms.put("Normal Indicative Future Perfect", normal_indicative_future_perfect.substring(2).split("; "));
		this.forms.put("Normal Subjunctive Present", normal_subjunctive_present.substring(2).split("; "));
		this.forms.put("Normal Subjunctive Imperfect", normal_subjunctive_imperfect.substring(2).split("; "));
		this.forms.put("Normal Subjunctive Perfect", normal_subjunctive_perfect.substring(2).split("; "));
		this.forms.put("Normal Subjunctive Pluperfect", normal_subjunctive_pluperfect.substring(2).split("; "));
		this.forms.put("Normal Conditional Present", normal_conditional_present.substring(2).split("; "));
		this.forms.put("Normal Conditional Perfect", normal_conditional_perfect.substring(2).split("; "));

		for (int i = 0; i < 6; i++) {
			Verb auxiliary = (isAuxiliary)? this : AUXILIARY;
			progressive_indicative_present += "; " + auxiliary.getForm("Normal Indicative Present")[i] + " " + gerund;
			progressive_indicative_past += "; " + auxiliary.getForm("Normal Indicative Past")[i] + " " + gerund;
			progressive_indicative_future += "; " + auxiliary.getForm("Normal Indicative Future")[i] + " " + gerund;
			progressive_indicative_perfect += "; " + auxiliary.getForm("Normal Indicative Perfect")[i] + " " + gerund;
			progressive_indicative_pluperfect += "; " + auxiliary.getForm("Normal Indicative Pluperfect")[i] + " " + gerund;
			progressive_indicative_future_perfect += "; " + auxiliary.getForm("Normal Indicative Future Perfect")[i] + " " + gerund;
			progressive_conditional_present += "; " + auxiliary.getForm("Normal Conditional Present")[i] + " " + gerund;
			progressive_conditional_perfect += "; " + auxiliary.getForm("Normal Conditional Perfect")[i] + " " + gerund;

			this.forms.put("Progressive Indicative Present", progressive_indicative_present.substring(2).split("; "));
			this.forms.put("Progressive Indicative Past", progressive_indicative_past.substring(2).split("; "));
			this.forms.put("Progressive Indicative Future", progressive_indicative_future.substring(2).split("; "));
			this.forms.put("Progressive Indicative Perfect", progressive_indicative_perfect.substring(2).split("; "));
			this.forms.put("Progressive Indicative Pluperfect", progressive_indicative_pluperfect.substring(2).split("; "));
			this.forms.put("Progressive Indicative Future Perfect", progressive_indicative_future_perfect.substring(2).split("; "));
			this.forms.put("Progressive Conditional Present", progressive_conditional_present.substring(2).split("; "));
			this.forms.put("Progressive Conditional Perfect", progressive_conditional_perfect.substring(2).split("; "));
		}
	}
	public String getConjugation() {
		String str = "";
		String[] pronouns = "I; you; he/she; we; (plural) you; they".split("; ");
		for (String tense : this.forms.keySet()) {
			if (Arrays.asList("infinitive; gerund; participle".split("; ")).contains(tense))
				continue;
			str += (tense + " tense") + "\n";
//			str += Arrays.toString(this.forms.get(tense)) + "\n";
			for (int i = 0; i < pronouns.length; i++) {
				str += "\t" + (pronouns[i] + " " + this.forms.get(tense)[i]) + "\n";
			}
		}

		return str;
	}
}
