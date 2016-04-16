package dictionary;

import java.util.*;
import java.text.DecimalFormat;
import java.lang.Math;

public class AnswerDataStatisticsMaker {

	private AnswerDataContainer answerDataContainer;
	private CardContainer cardContainer;

	public void setAnswerDataContainer(AnswerDataContainer ac) {
		answerDataContainer = ac;
	}

	public void setCardContainer(CardContainer cc) {
		cardContainer = cc;
	}

	public int numberOfAnswersAtDay(int day) {
		int numberOfAnswers = 0;
		for (int i=0; i<answerDataContainer.numberOfAnswers(); i++) {
			if (milisecToDay(answerDataContainer.getAnswerData(i).date) == day) {
				numberOfAnswers++;
			}
		}
		return numberOfAnswers;
	}

	public int numberOfRightAnswersAtDay(int day) {
		int numberOfAnswers = 0;
		for (int i=0; i<answerDataContainer.numberOfAnswers(); i++) {
			if (answerDataContainer.getAnswerData(i).isRight 
				&& milisecToDay(answerDataContainer.getAnswerData(i).date) == day) {
				numberOfAnswers++;
			}
		}
		return numberOfAnswers;
	}

	public double percentageOfRightAnswersAtDay(int day) {
		if (numberOfAnswersAtDay(day) != 0) {
			return (double)numberOfRightAnswersAtDay(day) * 100.0/(double)numberOfAnswersAtDay(day);
		}
		else {
			return 0.0;
		}
	}

	public void toScreenPercentageOfRightAnswers() {
		if (answerDataContainer.numberOfAnswers() != 0) {
			int sum = 0;
			for (int i=0; i<answerDataContainer.numberOfAnswers(); i++) {
				if (answerDataContainer.getAnswerData(i).isRight) {
					sum++;
				}
			}
			DecimalFormat df = new DecimalFormat("#.00");
			System.out.println("percantage of right answers: " + df.format((double)sum/(double)answerDataContainer.numberOfAnswers() * 100.0) + "%");
		}
		else {
			System.out.println("percantage of right answers: -1.0%");
		}
	}

	public void toScreenNumberOfCardsQuestioned() {
		System.out.println("number of cards questioned: " + answerDataContainer.numberOfQuestionedCards());
	}

	public void toScreenProgressByCards() {
		System.out.println("card | right answer percentage (number of answers)");
		AnswerDataByCardsContainer answerDataByCardsContainer = new AnswerDataByCardsContainer();
		answerDataByCardsContainer.loadDataFromAnswerDataContainer(answerDataContainer);

		AnswerDataByCard[] datasToSort = answerDataByCardsContainer.toArray();
		Arrays.sort(datasToSort, new AnswerDataByCardComparatorByRateOfRightAnswers());
		String out = "";
		for (int i=0; i<answerDataByCardsContainer.numberOfCards(); i++) {
			out = datasToSort[i].toStringShowingPercentage(cardContainer);
			System.out.println(out);
		}
	}

	public void toScreenProgress(int numberOfMeasurementPoints) {	//suppose, that data is ordered by time
		DecimalFormat df = new DecimalFormat("#.00");
		System.out.print("progress of right answers: ");

		Set<Integer> measurementPoints = new HashSet<Integer>();
		for (int i=1; i <= numberOfMeasurementPoints; i++) {
			int a = (int)Math.floor(((double)i * (double)(answerDataContainer.numberOfAnswers()) )/ (double)(numberOfMeasurementPoints)) - 1;
			measurementPoints.add(a);
		}

		int numberOfRightAnswers = 0;
		int numberOfAnswers = 0;
		for (int i=0; i < answerDataContainer.numberOfAnswers(); i++) {
			if (answerDataContainer.getAnswerData(i).isRight) {
				numberOfRightAnswers++;
			}
			numberOfAnswers++;

			if (measurementPoints.contains(i)) {
				double d = (double)numberOfRightAnswers/(double)numberOfAnswers * 100.0;
				System.out.print(df.format(d) + "%   ");
				numberOfRightAnswers = 0;
				numberOfAnswers = 0;
			}
		}
		System.out.println();
	}

	public int milisecToDay(long milisecTime) {
		int timeZone = 1;
		return (int)((milisecTime + (long)(timeZone * 1000 * 3600))/(long)(1000*3600*24));
	}

	public void toScreenActualDateInFormat1() {	//for debug, date works according to timeZone GMT
		Date date = new Date();
		long now = date.getTime();
		int numberOfDay = (int)(now/(long)(1000*3600*24));
		long milisecsToday = (int)(now - (long)(numberOfDay * (1000*3600*24)));
		int numberOfHoursToday = (int)(milisecsToday/(long)(1000*3600));
		System.out.println(numberOfDay + " " + milisecsToday + " " + numberOfHoursToday);
		System.out.println(date.toString());
	}

	public void toSreenNumberOfCards() {
		System.out.println("number of cards: " + Integer.toString(cardContainer.numberOfCards()));
	}

	public void toSreenNumberOfAnswers() {
		System.out.println("number of answers: " + Integer.toString(answerDataContainer.data.size()));
	}

	public void toScreenNumberOfAnswersGivenLastDays(int numberOfDays) {
		Date date = new Date();
		int today = milisecToDay(date.getTime());
		System.out.println("number of answers given last days (number of days before today - number od answers (percentage of right answers)): ");
		DecimalFormat df = new DecimalFormat("#.00");
		for (int i=0; i<numberOfDays; i++) {
			System.out.println(i + " - " +numberOfAnswersAtDay(today-i) + " (" + df.format(percentageOfRightAnswersAtDay(today-i)) + "%)");
		}
	}

	public void toScreenHistogram() {
		AnswerDataByCardsContainer answerDataByCardsContainer = new AnswerDataByCardsContainer();
		answerDataByCardsContainer.loadDataFromAnswerDataContainer(answerDataContainer);

		int [] numberOfCardsInCategory = new int[10];

		for (int i=0; i<answerDataByCardsContainer.numberOfCards();i++) {
			double rateOfRightAnswers =  answerDataByCardsContainer.getAnswerDataByCard(i).countRightAnswerRate();

			if (rateOfRightAnswers == 1.0) {
				numberOfCardsInCategory[9]++;
			}
			else {
				numberOfCardsInCategory[(int)Math.floor(10.0 * rateOfRightAnswers)]++;
			}
		}

		System.out.println("Histogram of cards by right answer rate (category ---> number of cards in category (percentage of cards)):");

		for (int i=9; 0<=i;i--) {
			DecimalFormat df = new DecimalFormat("#.00");
			System.out.println(i*10 + "% - " + (i+1) * 10 + "% ---> " + numberOfCardsInCategory[i] + " (" 
				+ df.format((double)numberOfCardsInCategory[i] * 100.0 / (double)answerDataByCardsContainer.numberOfCards()) 					+ "%)");
		}
	}

	public void toSreenAverageAnswerRateOfCards() {
		AnswerDataByCardsContainer answerDataByCardsContainer = new AnswerDataByCardsContainer();
		answerDataByCardsContainer.loadDataFromAnswerDataContainer(answerDataContainer);
		double sum = 0.0;
		for (int i=0; i<answerDataByCardsContainer.numberOfCards(); i++) {
			sum = sum + answerDataByCardsContainer.getAnswerDataByCard(i).countRightAnswerRate();
		}
		DecimalFormat df = new DecimalFormat("#.00");
		System.out.println("awerage answer rate of cards: " 
			+ df.format(sum * 100.0/ (double)answerDataByCardsContainer.numberOfCards()) + "%");
	}

	public void toScreenHardestWords(int numberOfWords) {
		AnswerDataByCardsContainer answerDataByCardsContainer = new AnswerDataByCardsContainer();
		answerDataByCardsContainer.loadDataFromAnswerDataContainer(answerDataContainer);

		if (numberOfWords <= answerDataByCardsContainer.numberOfCards()) {
			AnswerDataByCard[] datasToSort = answerDataByCardsContainer.toArray();
			Arrays.sort(datasToSort, new AnswerDataByCardComparatorByRateOfRightAnswers());
			String out = "";

			for (int i=0; i<numberOfWords; i++) {
				out = datasToSort[answerDataByCardsContainer.numberOfCards() - 1 - i].toStringShowingPercentage(cardContainer);
				System.out.println(out);
			}
		}
	}

}
