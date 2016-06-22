package terminal_interface;

import common.*;

import java.util.*;
import java.io.Console;

import java.lang.reflect.*;

public class AdditionalStatisticsShower {

	protected AnswerDataStatisticsMaker answerDataStatisticsMaker;
	protected Console console = System.console();

	protected Vector<Method> indexAndStatisticsMethods = new Vector<Method>();
	protected Vector<String> indexAndStatisticsCaption = new Vector<String>();

	protected Logger logger = new Logger();

	public AdditionalStatisticsShower() {
		try{
			Method method;

			method = AdditionalStatisticsShower.class.getDeclaredMethod("toScreenHistogramOfStudyItemAnswerRatesByDays");
			indexAndStatisticsMethods.add(method);
			indexAndStatisticsCaption.add("histogram of study item answers rates by days");

			method = AdditionalStatisticsShower.class.getDeclaredMethod("toScreenPractisingTimeByDays");
			indexAndStatisticsMethods.add(method);
			indexAndStatisticsCaption.add("practising time by days");

			method = AdditionalStatisticsShower.class.getDeclaredMethod("toScreenHistogramOfStudyItemsByNumberOfAnswersConsideredLowCategories");
			indexAndStatisticsMethods.add(method);
			indexAndStatisticsCaption.add("histogram of study items by number of answers considered low categoris");

			method = AdditionalStatisticsShower.class.getDeclaredMethod("toScreenNumberOfIndividualStudyItemsQuestionedByDays");
			indexAndStatisticsMethods.add(method);
			indexAndStatisticsCaption.add("number of individual study items questioned by days");

			method = AdditionalStatisticsShower.class.getDeclaredMethod("toFileHistogramOfStudyItemAnswerRatesByDays");
			indexAndStatisticsMethods.add(method);
			indexAndStatisticsCaption.add("to file histogram of study items answers rates by days");

			method = AdditionalStatisticsShower.class.getDeclaredMethod("toScreenHistogramOfStudyItemsByNumberOfAnswers");
			indexAndStatisticsMethods.add(method);
			indexAndStatisticsCaption.add("histogram of study items by number of answers");

			method = AdditionalStatisticsShower.class.getDeclaredMethod("toScreenProbabilityOfRightAnswerAtFirstSecondEtcAnswers");
			indexAndStatisticsMethods.add(method);
			indexAndStatisticsCaption.add("probability of right answer at first, second,... answers");

			method = AdditionalStatisticsShower.class.getDeclaredMethod("toScreenNumberOfGivenAnswersByHours");
			indexAndStatisticsMethods.add(method);
			indexAndStatisticsCaption.add("number of given answers by hours");

			method = AdditionalStatisticsShower.class.getDeclaredMethod("toSreenLongestIntervallSizeOfRightAnswers");
			indexAndStatisticsMethods.add(method);
			indexAndStatisticsCaption.add("longest right answer intervall size");
		} catch(Exception ex) {}
		
	}

	public void setAnswerDataStatisticsMaker(AnswerDataStatisticsMaker am) {
		answerDataStatisticsMaker = am;
	}

	public void toScreenProgressByStudyItems() {
		System.out.print("\033[H\033[2J");
		answerDataStatisticsMaker.toScreenProgressByStudyItems();
		console.readLine();
	}

	public void toScreenHistogramOfStudyItemsByNumberOfAnswers() {
		System.out.print("\033[H\033[2J");
		answerDataStatisticsMaker.toScreenHistogramOfStudyItemsByNumberOfAnswers();
		console.readLine();
	}

	public void toScreenHistogramOfStudyItemsByNumberOfAnswersConsideredLowCategories() {
		System.out.print("\033[H\033[2J");
		answerDataStatisticsMaker.toScreenHistogramOfStudyItemsByNumberOfAnswersConsideredLowCategories();
		console.readLine();
	}

	public void toScreenPractisingTimeByDays() {
		System.out.print("\033[H\033[2J");
		answerDataStatisticsMaker.toScreenPractisingTimeByDays();
		console.readLine();
	}

	public void toScreenNumberOfIndividualStudyItemsQuestionedByDays() {
		System.out.print("\033[H\033[2J");
		Map<Integer,Integer> data = answerDataStatisticsMaker.numberOfIndividualStudyItemsQuestionedByDays();

		for (int day : data.keySet()) {
			System.out.println(day + "\t" + data.get(day));
		}

		console.readLine();
	}

	public void toScreenHistogramOfStudyItemAnswerRatesByDays() {
		System.out.print("\033[H\033[2J");
		StringTabular stringTabular = answerDataStatisticsMaker.getHistogramOfStudyItemAnswerRatesByDays();
		for (int i=0; i<stringTabular.numberOfRows(); i++) {
			System.out.println(stringTabular.getNiceTabularRowInString(i));
		}
		console.readLine();
	}

	public void toScreenNumberOfGivenAnswersByHours() {
		System.out.print("\033[H\033[2J");
		answerDataStatisticsMaker.toScreenNumberOfGivenAnswersByHours();
		console.readLine();
	}

	public void toFileHistogramOfStudyItemAnswerRatesByDays() {
		System.out.print("\033[H\033[2J");
		answerDataStatisticsMaker.toFileHistogramOfStudyItemAnswerRatesByDays("../data/temporary_data/histogram_of_card_answer_rates_by_days.txt");
		System.out.print("data has benn saved to file: data/temporary_data/histogram_of_card_answer_rates_by_days.txt");
		console.readLine();
	}

	public void toSreenLongestIntervallSizeOfRightAnswers() {
		System.out.print("\033[H\033[2J");
		answerDataStatisticsMaker.toSreenLongestIntervallSizeOfRightAnswers();
		console.readLine();
	}

	public void toScreenProbabilityOfRightAnswerAtFirstSecondEtcAnswers() {
		System.out.print("\033[H\033[2J");
		answerDataStatisticsMaker.toScreenProbabilityOfRightAnswerAtFirstSecondEtcAnswers();
		console.readLine();
	}

	public void showStatisticsChooser() {	//TODO: use here somehow method reference

		String input = "";
		int choice;

		do {
			System.out.print("\033[H\033[2J");
			for (int i=0; i<indexAndStatisticsMethods.size(); i++) {
				System.out.println(i + " - " + indexAndStatisticsCaption.get(i));
			}

			input = console.readLine();

			try{
				choice = Integer.parseInt(input);

				if (0 <= choice && choice <= indexAndStatisticsMethods.size()-1) {
					try{
					indexAndStatisticsMethods.get(choice).invoke(this);
					} catch (Exception e) {}
				}

			} catch (NumberFormatException e) {}


		} while (!input.equals(""));
	}
}
