package dictionary;

import java.util.*;
import java.text.DecimalFormat;
import java.lang.Math;
import java.io.*;	//!!!
import java.io.FileWriter;

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

	public long evaluatePractisingTime(Vector<Long> answerDates) {	//supposed, that dates are sorted
		long practisingTime = 0;
		long intervalStartTime = answerDates.get(0);

		for (int i=1; i<answerDates.size(); i++) {
			if (answerDates.get(i) - answerDates.get(i-1) > 30000) {
				practisingTime = practisingTime + (answerDates.get(i-1) - intervalStartTime) + 5000;
				intervalStartTime = answerDates.get(i);
			}
		}

		practisingTime = practisingTime	+ (answerDates.lastElement() - intervalStartTime) + 5000;

		return practisingTime;
	}

	public void toScreenPractisingTime() {
		Vector<Long> answerDates = new Vector<Long>();
		for (int i=0; i<answerDataContainer.numberOfAnswers(); i++) {
			answerDates.add(answerDataContainer.getAnswerData(i).date);
		}

		long practisingTime = evaluatePractisingTime(answerDates);
		int hours = (int)practisingTime / 3600000;
		int minutes = (int)((practisingTime - 3600000 * hours)/60000);
		System.out.println("practising time: " + hours + " hours " + minutes + " minutes");
	}

	public void toScreenPractisingTimeByDays() {
		if (answerDataContainer.numberOfAnswers() != 0) {
			Vector<Long> answerDates = new Vector<Long>();
			int actualDay = milisecToDay(answerDataContainer.getAnswerData(0).date);

			for (int i=1; i < answerDataContainer.numberOfAnswers(); i++) {
				int newDay = milisecToDay(answerDataContainer.getAnswerData(i).date);
				if (actualDay == newDay) {
					answerDates.add(answerDataContainer.getAnswerData(i).date);
				}
				else {
					long practisingTime = evaluatePractisingTime(answerDates);
					int hours = (int)practisingTime / 3600000;
					int minutes = (int)((practisingTime - 3600000 * hours)/60000);
					System.out.println(actualDay + "\t" + hours + " hours " + minutes + " minutes");

					actualDay = newDay;
					answerDates.clear();
					answerDates.add(answerDataContainer.getAnswerData(i).date);
				}
			}

			long practisingTime = evaluatePractisingTime(answerDates);
			int hours = (int)practisingTime / 3600000;
			int minutes = (int)((practisingTime - 3600000 * hours)/60000);
			System.out.println(actualDay + "\t" + hours + " hours " + minutes + " minutes");
		}
	}

	public void toScreenNumberOfQuestionsOfLeastStudiedCard() {	// it does not caunt the not studied cards!!!???
		AnswerDataByCardsContainer answerDataByCardsContainer = new AnswerDataByCardsContainer();
		answerDataByCardsContainer.loadDataFromAnswerDataContainer(answerDataContainer);

		Set<Integer> testedCardIndexes = answerDataByCardsContainer.getTestedCardIndexes();
		int numberOfQuestionsOfLeastStudiedCard = Integer.MAX_VALUE;
		for (int index : testedCardIndexes) {
			int numberOfAnswers = answerDataByCardsContainer.getAnswerDataByCardByIndex(index).numberOfAnswers();
			if (numberOfAnswers < numberOfQuestionsOfLeastStudiedCard) {
				numberOfQuestionsOfLeastStudiedCard = numberOfAnswers;
			}
		}

		System.out.println("number of questions of least studied card: " + numberOfQuestionsOfLeastStudiedCard);
	}

	public HashMap<Integer, Integer> evaluateHistogramOfCardsByNumberOfAnswers() {
		AnswerDataByCardsContainer answerDataByCardsContainer = new AnswerDataByCardsContainer();
		answerDataByCardsContainer.loadDataFromAnswerDataContainer(answerDataContainer);

		HashMap<Integer, Integer> histogramOfCardsByNumberOfAnswers = new HashMap<Integer, Integer>();

		Set<Integer> testedCardIndexes = answerDataByCardsContainer.getTestedCardIndexes();
		int numberOfQuestionsOfLeastStudiedCard = Integer.MAX_VALUE;
		for (int index : testedCardIndexes) {
			int numberOfAnswers = answerDataByCardsContainer.getAnswerDataByCardByIndex(index).numberOfAnswers();
			if (histogramOfCardsByNumberOfAnswers.containsKey(numberOfAnswers)) {
				int numberOfCards = histogramOfCardsByNumberOfAnswers.get(numberOfAnswers) + 1;
				histogramOfCardsByNumberOfAnswers.remove(numberOfAnswers);
				histogramOfCardsByNumberOfAnswers.put(numberOfAnswers,numberOfCards);
			}
			else {
				histogramOfCardsByNumberOfAnswers.put(numberOfAnswers,1);
			}
		}

		return histogramOfCardsByNumberOfAnswers;
	}

	public void toScreenHistogramOfCardsByNumberOfAnswers() {
		System.out.println("Histogram of cards by number of answers (number of answers ---> number of cards with given answers number)");
		HashMap<Integer, Integer> histogramOfCardsByNumberOfAnswers = evaluateHistogramOfCardsByNumberOfAnswers();
		
		for (int i : histogramOfCardsByNumberOfAnswers.keySet()) {
			System.out.println(i + " ---> " +histogramOfCardsByNumberOfAnswers.get(i));
		}
	}

	public double probabilityOfRightAnswerAtXthAnswer(int x) {	//TODO: implement
		AnswerDataByCardsContainer answerDataByCardsContainer = new AnswerDataByCardsContainer();
		answerDataByCardsContainer.loadDataFromAnswerDataContainer(answerDataContainer);

		int numberOfAnswers = 0;
		int numberOfRightAnswers = 0;

		Set<Integer> testedCardIndexes = answerDataByCardsContainer.getTestedCardIndexes();

		for (int i : testedCardIndexes) {
			if (x < answerDataByCardsContainer.getAnswerDataByCardByIndex(i).numberOfAnswers()) {
				if (answerDataByCardsContainer.getAnswerDataByCardByIndex(i).getAnswer(x).isRight) {
					numberOfRightAnswers++;
				}
				numberOfAnswers++;
			}
		}

		if (numberOfAnswers == 0) {
			return -1;
		}
		else {
			return (double)numberOfRightAnswers/(double)numberOfAnswers;
		}
	}

	public void toScreenProbabilityOfRightAnswerAtFirstSecondEtcAnswers() {	//TODO: implement
		System.out.println("Xth answer ---> probability:");
		DecimalFormat df = new DecimalFormat("#.000");
		for (int x=0; x<30; x++) {
			System.out.println((x+1) + " ---> " + df.format(probabilityOfRightAnswerAtXthAnswer(x)));
		}
	}

	public void toScreenHistogramOfCardsByNumberOfAnswersConsideredLowCategories() {
		System.out.println("Histogram of cards by number of answers (number of answers ---> number of cards with given answers number)");
		HashMap<Integer, Integer> histogramOfCardsByNumberOfAnswers = evaluateHistogramOfCardsByNumberOfAnswers();
		
		int numberOfCardsInBigCategories = 0;
		for (int i : histogramOfCardsByNumberOfAnswers.keySet()) {
			if (i <= 30) {
				System.out.println(i + " ---> " + histogramOfCardsByNumberOfAnswers.get(i));
			}
			else {
				numberOfCardsInBigCategories = numberOfCardsInBigCategories + histogramOfCardsByNumberOfAnswers.get(i);
			}
		}

		System.out.println("30< ---> " + numberOfCardsInBigCategories);
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

	public void toSreenLastQuestionedCardDate() {
		Set<Integer> cardIndexes = new HashSet<Integer>();
		int i = answerDataContainer.numberOfAnswers() - 1;
		int numberOfQuestionedCards = answerDataContainer.numberOfQuestionedCards();
		while ((int)cardIndexes.size() < numberOfQuestionedCards) {
			cardIndexes.add(answerDataContainer.getAnswerData(i).index);
			i--;
		}
		Date date=new Date(answerDataContainer.getAnswerData(i+1).date);
		System.out.println("date of last questioned card: " + date);
	}

	public void toSreenNumberOfStudyingDays() {
		Set<Integer> studyingDays = new HashSet<Integer>();
		for (int i=0; i < answerDataContainer.numberOfAnswers(); i++) {
			studyingDays.add(milisecToDay(answerDataContainer.getAnswerData(i).date));
		}
		System.out.println("number of studying days: " + studyingDays.size());
	}

	public void toScreenNumberOfAnswersGivenLastDays(int numberOfDays) {
		Date date = new Date();
		int today = milisecToDay(date.getTime());
		System.out.println("number of answers given last days (number of days before today - number of answers (percentage of right answers)): ");
		DecimalFormat df = new DecimalFormat("#.00");
		for (int i=0; i<numberOfDays; i++) {
			System.out.println(i + " - " +numberOfAnswersAtDay(today-i) + " (" + df.format(percentageOfRightAnswersAtDay(today-i)) + "%)");
		}
	}

	public int milisecToHour(long milisec) {
		return (int)Math.floor( (int)(milisec % (1000*3600*24)) / (int)(1000*3600));
	}

	public void toScreenNumberOfGivenAnswersByHours() {
		int[] numberOfGivenAnswersByHours = new int[24];

		//Date date = new Date();
		//System.out.println(milisecToHour(date.getTime() + 3600*1000));	//for test: related to time zone

		for (int i=0; i < answerDataContainer.numberOfAnswers(); i++) {
			int hour = milisecToHour(answerDataContainer.getAnswerData(i).date);
			numberOfGivenAnswersByHours[hour]++;
		}

		System.out.println("number of given answers by hours /hour TAB # of answers/:");
		for (int i=0; i < 24; i++) {
			System.out.println(i + "\t" + numberOfGivenAnswersByHours[i]);
		}
	}

	public void toScreenHistogram() {
		AnswerDataByCardsContainer answerDataByCardsContainer = new AnswerDataByCardsContainer();
		answerDataByCardsContainer.loadDataFromAnswerDataContainer(answerDataContainer);

		int [] numberOfCardsInCategory = new int[10];

		for (int index : answerDataByCardsContainer.getTestedCardIndexes()) {
			double rateOfRightAnswers =  answerDataByCardsContainer.getAnswerDataByCardByIndex(index).countRightAnswerRate();

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

	public void toScreenHistogramOfCardAnswerRatesByDays() {
		AnswerDataByCardsContainer answerDataByCardsContainer = new AnswerDataByCardsContainer();
		answerDataByCardsContainer.loadDataFromAnswerDataContainer(answerDataContainer);

		Map<Integer,Histogram> data = new HashMap<Integer,Histogram>();

		Set<Integer> studyingDays = answerDataByCardsContainer.getStudyingDays();
		for (int day : studyingDays) {
			Histogram histogram = answerDataByCardsContainer.getHistogramAtDay(day);
			data.put(day, histogram);
		}


		Set<Integer> keys = data.keySet();
		SortedSet<Integer> sortedDays = new TreeSet<Integer>(keys);
		for (int day : sortedDays) {
			System.out.println(day + "\t" + data.get(day).toStringHorisontally());
		}
	}

	public void toFileHistogramOfCardAnswerRatesByDays(String filePath) {
		AnswerDataByCardsContainer answerDataByCardsContainer = new AnswerDataByCardsContainer();
		answerDataByCardsContainer.loadDataFromAnswerDataContainer(answerDataContainer);

		Map<Integer,Histogram> data = new HashMap<Integer,Histogram>();

		Set<Integer> studyingDays = answerDataByCardsContainer.getStudyingDays();
		for (int day : studyingDays) {
			Histogram histogram = answerDataByCardsContainer.getHistogramAtDay(day);
			data.put(day, histogram);
		}


		Set<Integer> keys = data.keySet();
		SortedSet<Integer> sortedDays = new TreeSet<Integer>(keys);

		try {
			FileWriter fw = new FileWriter(filePath,false);	//the true will append the new data
			for (int day : sortedDays) {
				fw.write(day + "\t" + data.get(day).toStringHorisontally() + "\n");
			}
			fw.close();
		}
		catch(IOException ioe) {
			System.err.println("IOException: " + ioe.getMessage());
		}
	}


	public void toSreenAverageAnswerRateOfCards() {
		AnswerDataByCardsContainer answerDataByCardsContainer = new AnswerDataByCardsContainer();
		answerDataByCardsContainer.loadDataFromAnswerDataContainer(answerDataContainer);
		double sum = 0.0;
		for (int index : answerDataByCardsContainer.getTestedCardIndexes()) {
			sum = sum + answerDataByCardsContainer.getAnswerDataByCardByIndex(index).countRightAnswerRate();
		}
		DecimalFormat df = new DecimalFormat("#.00");
		System.out.println("awerage answer rate of cards: " 
			+ df.format(sum * 100.0/ (double)answerDataByCardsContainer.numberOfCards()) + "%");
	}

	public void toSreenLongestIntervallSizeOfRightAnswers() {
		int longestIntervallSize = 0;
		int actualIntervallSize = 0;
		for (int i=0; i<answerDataContainer.numberOfAnswers(); i++) {
			if (answerDataContainer.getAnswerData(i).isRight) {
				actualIntervallSize++;
			}
			else {
				if (actualIntervallSize > longestIntervallSize) {
					longestIntervallSize = actualIntervallSize;
				}
				actualIntervallSize = 0;
			}
		}
		if (actualIntervallSize > longestIntervallSize) {
			longestIntervallSize = actualIntervallSize;
		}

		System.out.println("longest right answer intervall size: " + longestIntervallSize);
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
