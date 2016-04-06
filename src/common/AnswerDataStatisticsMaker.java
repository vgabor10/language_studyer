package common;

import java.util.*;
import java.text.DecimalFormat;		//TODO: will not be nessessery in the future
import java.lang.Math;
import java.io.*;

public class AnswerDataStatisticsMaker {

	protected AnswerDataContainer answerDataContainer;
	protected StudyItemContainer studyItemContainer;

	public void setAnswerDataContainer(AnswerDataContainer ac) {
		answerDataContainer = ac;
	}

	public void setStudyItemContainer(StudyItemContainer sc) {
		studyItemContainer = sc;
	}

	public int numberOfAnswersAtDay(int day) {
		int numberOfAnswers = 0;
		GeneralFunctions generalFunctions = new GeneralFunctions();

		for (int i=0; i<answerDataContainer.numberOfAnswers(); i++) {
			if (generalFunctions.milisecToDay(answerDataContainer.getAnswerData(i).date) == day) {
				numberOfAnswers++;
			}
		}
		return numberOfAnswers;
	}

	public int numberOfRightAnswersAtDay(int day) {
		int numberOfAnswers = 0;
		GeneralFunctions generalFunctions = new GeneralFunctions();

		for (int i=0; i<answerDataContainer.numberOfAnswers(); i++) {
			if (answerDataContainer.getAnswerData(i).isRight 
				&& generalFunctions.milisecToDay(answerDataContainer.getAnswerData(i).date) == day) {
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

	public double percentageOfRightAnswers() {
		if (answerDataContainer.numberOfAnswers() != 0) {
			int sum = 0;
			for (int i=0; i<answerDataContainer.numberOfAnswers(); i++) {
				if (answerDataContainer.getAnswerData(i).isRight) {
					sum++;
				}
			}
			return (double)sum/(double)answerDataContainer.numberOfAnswers() * 100.0;
		}
		else {
			return -1;
		}
	}

	public int numberOfStudyItemsQuestioned() {
		Set<Integer> StudyItemInexes = new HashSet<Integer>();
		for (int i=0; i< answerDataContainer.numberOfAnswers(); i++) {
			StudyItemInexes.add(answerDataContainer.getAnswerData(i).index);
		}

		return StudyItemInexes.size();
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

	public int[] practisingTime() {
		Vector<Long> answerDates = new Vector<Long>();
		for (int i=0; i<answerDataContainer.numberOfAnswers(); i++) {
			answerDates.add(answerDataContainer.getAnswerData(i).date);
		}

		long practisingTime = evaluatePractisingTime(answerDates);
		int hours = (int)practisingTime / 3600000;
		int minutes = (int)((practisingTime - 3600000 * hours)/60000);

		int[] out = {hours,minutes}; 
		return out;
	}

	public void toScreenPractisingTimeByDays() {
		if (answerDataContainer.numberOfAnswers() != 0) {
			Vector<Long> answerDates = new Vector<Long>();

			GeneralFunctions generalFunctions = new GeneralFunctions();
			int actualDay = generalFunctions.milisecToDay(answerDataContainer.getAnswerData(0).date);

			for (int i=1; i < answerDataContainer.numberOfAnswers(); i++) {
				int newDay = generalFunctions.milisecToDay(answerDataContainer.getAnswerData(i).date);
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

	public int numberOfQuestionsOfLeastStudiedStudyItem() {		// it does not caunt the not studied StudyItems!!!???
		AnswerDataByStudyItemsContainer answerDataByStudyItemsContainer = new AnswerDataByStudyItemsContainer();
		answerDataByStudyItemsContainer.loadDataFromAnswerDataContainer(answerDataContainer);

		Set<Integer> testedStudyItemIndexes = answerDataByStudyItemsContainer.getTestedStudyItemIndexes();
		int numberOfQuestionsOfLeastStudiedStudyItem = Integer.MAX_VALUE;
		for (int index : testedStudyItemIndexes) {
			int numberOfAnswers = answerDataByStudyItemsContainer.getAnswerDataByStudyItemByIndex(index).numberOfAnswers();
			if (numberOfAnswers < numberOfQuestionsOfLeastStudiedStudyItem) {
				numberOfQuestionsOfLeastStudiedStudyItem = numberOfAnswers;
			}
		}

		return numberOfQuestionsOfLeastStudiedStudyItem;
	}

	public HashMap<Integer, Integer> evaluateHistogramOfStudyItemsByNumberOfAnswers() {
		AnswerDataByStudyItemsContainer answerDataByStudyItemsContainer = new AnswerDataByStudyItemsContainer();
		answerDataByStudyItemsContainer.loadDataFromAnswerDataContainer(answerDataContainer);

		HashMap<Integer, Integer> histogramOfStudyItemsByNumberOfAnswers = new HashMap<Integer, Integer>();

		Set<Integer> testedStudyItemIndexes = answerDataByStudyItemsContainer.getTestedStudyItemIndexes();
		int numberOfQuestionsOfLeastStudiedStudyItem = Integer.MAX_VALUE;
		for (int index : testedStudyItemIndexes) {
			int numberOfAnswers = answerDataByStudyItemsContainer.getAnswerDataByStudyItemByIndex(index).numberOfAnswers();
			if (histogramOfStudyItemsByNumberOfAnswers.containsKey(numberOfAnswers)) {
				int numberOfStudyItems = histogramOfStudyItemsByNumberOfAnswers.get(numberOfAnswers) + 1;
				histogramOfStudyItemsByNumberOfAnswers.remove(numberOfAnswers);
				histogramOfStudyItemsByNumberOfAnswers.put(numberOfAnswers,numberOfStudyItems);
			}
			else {
				histogramOfStudyItemsByNumberOfAnswers.put(numberOfAnswers,1);
			}
		}

		return histogramOfStudyItemsByNumberOfAnswers;
	}

	public void toScreenHistogramOfStudyItemsByNumberOfAnswers() {
		System.out.println("Histogram of StudyItems by number of answers (number of answers ---> number of StudyItems with given answers number)");
		HashMap<Integer, Integer> histogramOfStudyItemsByNumberOfAnswers = evaluateHistogramOfStudyItemsByNumberOfAnswers();
		
		for (int i : histogramOfStudyItemsByNumberOfAnswers.keySet()) {
			System.out.println(i + " ---> " +histogramOfStudyItemsByNumberOfAnswers.get(i));
		}
	}

	public double probabilityOfRightAnswerAtXthAnswer(int x) {
		AnswerDataByStudyItemsContainer answerDataByStudyItemsContainer = new AnswerDataByStudyItemsContainer();
		answerDataByStudyItemsContainer.loadDataFromAnswerDataContainer(answerDataContainer);

		int numberOfAnswers = 0;
		int numberOfRightAnswers = 0;

		Set<Integer> testedStudyItemIndexes = answerDataByStudyItemsContainer.getTestedStudyItemIndexes();

		for (int i : testedStudyItemIndexes) {
			if (x < answerDataByStudyItemsContainer.getAnswerDataByStudyItemByIndex(i).numberOfAnswers()) {
				if (answerDataByStudyItemsContainer.getAnswerDataByStudyItemByIndex(i).getAnswer(x).isRight) {
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

	public void toScreenProbabilityOfRightAnswerAtFirstSecondEtcAnswers() {
		System.out.println("Xth answer ---> probability:");
		DecimalFormat df = new DecimalFormat("#.000");
		for (int x=0; x<30; x++) {
			System.out.println((x+1) + " ---> " + df.format(probabilityOfRightAnswerAtXthAnswer(x)));
		}
	}

	public void toScreenHistogramOfStudyItemsByNumberOfAnswersConsideredLowCategories() {
		System.out.println("Histogram of StudyItems by number of answers (number of answers ---> number of StudyItems with given answers number)");
		HashMap<Integer, Integer> histogramOfStudyItemsByNumberOfAnswers = evaluateHistogramOfStudyItemsByNumberOfAnswers();
		
		int numberOfStudyItemsInBigCategories = 0;
		for (int i : histogramOfStudyItemsByNumberOfAnswers.keySet()) {
			if (i <= 30) {
				System.out.println(i + " ---> " + histogramOfStudyItemsByNumberOfAnswers.get(i));
			}
			else {
				numberOfStudyItemsInBigCategories = numberOfStudyItemsInBigCategories + histogramOfStudyItemsByNumberOfAnswers.get(i);
			}
		}

		System.out.println("30< ---> " + numberOfStudyItemsInBigCategories);
	}

	public void toScreenProgressByStudyItems() {
		System.out.println("StudyItem | right answer percentage (number of answers)");
		AnswerDataByStudyItemsContainer answerDataByStudyItemsContainer = new AnswerDataByStudyItemsContainer();
		answerDataByStudyItemsContainer.loadDataFromAnswerDataContainer(answerDataContainer);

		AnswerDataByStudyItem[] datasToSort = answerDataByStudyItemsContainer.toArray();
		Arrays.sort(datasToSort, new AnswerDataByStudyItemComparatorByRateOfRightAnswers());
		String out = "";
		for (int i=0; i<answerDataByStudyItemsContainer.numberOfStudyItems(); i++) {
			out = datasToSort[i].toStringShowingPercentage(studyItemContainer);
			System.out.println(out);
		}
	}

	//suppose, that data is ordered by time
	public void toScreenProgress(int numberOfMeasurementPoints) {	//TODO: not too informative statistics, should be deleted???
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

	public int numberOfStudyItems() {
		return studyItemContainer.numberOfStudyItems();
	}

	public int numberOfAnswers() {
		return answerDataContainer.numberOfAnswers();
	}

	public int numberOfQuestionedStudyItems() {
		Set<Integer> studyItemIndexes = new HashSet<Integer>();
		for (int i=0; i<answerDataContainer.numberOfAnswers(); i++) {
			studyItemIndexes.add(answerDataContainer.getAnswerData(i).index);
		}
		return studyItemIndexes.size();
	}

	public long getLastQuestionedStudyItemDate() {
		Set<Integer> StudyItemIndexes = new HashSet<Integer>();
		int i = answerDataContainer.numberOfAnswers() - 1;
		int numberOfQuestionedStudyItems = numberOfQuestionedStudyItems();
		while ((int)StudyItemIndexes.size() < numberOfQuestionedStudyItems) {
			StudyItemIndexes.add(answerDataContainer.getAnswerData(i).index);
			i--;
		}
		
		return answerDataContainer.getAnswerData(i+1).date;
	}

	public int getNumberOfStudyingDays() {
		Set<Integer> studyingDays = new HashSet<Integer>();
		GeneralFunctions generalFunctions = new GeneralFunctions();

		for (int i=0; i < answerDataContainer.numberOfAnswers(); i++) {
			studyingDays.add(generalFunctions.milisecToDay(answerDataContainer.getAnswerData(i).date));
		}

		return studyingDays.size();
	}

	public Vector<Integer> toScreenNumberOfAnswersGivenLastDays(int numberOfDays) {
		Date date = new Date();
		GeneralFunctions generalFunctions = new GeneralFunctions();
		int today = generalFunctions.milisecToDay(date.getTime());

		Vector<Integer> out = new Vector<Integer>();
		for (int i=0; i<numberOfDays; i++) {
			out.add(numberOfAnswersAtDay(today-i));
		}

		return out;
	}

	public void toScreenNumberOfGivenAnswersByHours() {
		int[] numberOfGivenAnswersByHours = new int[24];
		GeneralFunctions generalFunctions = new GeneralFunctions();

		//Date date = new Date();
		//System.out.println(generalFunctionsmilisecToHour(date.getTime() + 3600*1000));	//for test: related to time zone

		for (int i=0; i < answerDataContainer.numberOfAnswers(); i++) {
			int hour = generalFunctions.milisecToHour(answerDataContainer.getAnswerData(i).date);
			numberOfGivenAnswersByHours[hour]++;
		}

		System.out.println("number of given answers by hours /hour TAB # of answers/:");
		for (int i=0; i < 24; i++) {
			System.out.println(i + "\t" + numberOfGivenAnswersByHours[i]);
		}
	}

	public int[] getHistogramOfStudyItemsByAnswerRate() {
		AnswerDataByStudyItemsContainer answerDataByStudyItemsContainer = new AnswerDataByStudyItemsContainer();
		answerDataByStudyItemsContainer.loadDataFromAnswerDataContainer(answerDataContainer);

		int [] numberOfStudyItemsInCategory = new int[10];

		for (int index : answerDataByStudyItemsContainer.getTestedStudyItemIndexes()) {
			double rateOfRightAnswers =  answerDataByStudyItemsContainer.getAnswerDataByStudyItemByIndex(index).countRightAnswerRate();

			if (rateOfRightAnswers == 1.0) {
				numberOfStudyItemsInCategory[9]++;
			}
			else {
				numberOfStudyItemsInCategory[(int)Math.floor(10.0 * rateOfRightAnswers)]++;
			}
		}

		return numberOfStudyItemsInCategory;

		/*System.out.println("Histogram of StudyItems by right answer rate (category ---> number of StudyItems in category (percentage of StudyItems)):");

		for (int i=9; 0<=i;i--) {
			DecimalFormat df = new DecimalFormat("#.00");
			System.out.println(i*10 + "% - " + (i+1) * 10 + "% ---> " + numberOfStudyItemsInCategory[i] + " (" 
				+ df.format((double)numberOfStudyItemsInCategory[i] * 100.0 / (double)answerDataByStudyItemsContainer.numberOfStudyItems()) 					+ "%)");
		}*/
	}

	public void toScreenHistogramOfStudyItemAnswerRatesByDays() {
		AnswerDataByStudyItemsContainer answerDataByStudyItemsContainer = new AnswerDataByStudyItemsContainer();
		answerDataByStudyItemsContainer.loadDataFromAnswerDataContainer(answerDataContainer);

		Map<Integer,Histogram> data = new HashMap<Integer,Histogram>();

		Set<Integer> studyingDays = answerDataByStudyItemsContainer.getStudyingDays();
		for (int day : studyingDays) {
			Histogram histogram = answerDataByStudyItemsContainer.getHistogramAtDay(day);
			data.put(day, histogram);
		}


		Set<Integer> keys = data.keySet();
		SortedSet<Integer> sortedDays = new TreeSet<Integer>(keys);
		for (int day : sortedDays) {
			System.out.println(day + "\t" + data.get(day).toStringHorisontally());
		}
	}

	public void toFileHistogramOfStudyItemAnswerRatesByDays(String filePath) {
		AnswerDataByStudyItemsContainer answerDataByStudyItemsContainer = new AnswerDataByStudyItemsContainer();
		answerDataByStudyItemsContainer.loadDataFromAnswerDataContainer(answerDataContainer);

		Map<Integer,Histogram> data = new HashMap<Integer,Histogram>();

		Set<Integer> studyingDays = answerDataByStudyItemsContainer.getStudyingDays();
		for (int day : studyingDays) {
			Histogram histogram = answerDataByStudyItemsContainer.getHistogramAtDay(day);
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


	public double averageAnswerRateOfStudyItems() {
		AnswerDataByStudyItemsContainer answerDataByStudyItemsContainer = new AnswerDataByStudyItemsContainer();
		answerDataByStudyItemsContainer.loadDataFromAnswerDataContainer(answerDataContainer);
		double sum = 0.0;
		for (int index : answerDataByStudyItemsContainer.getTestedStudyItemIndexes()) {
			sum = sum + answerDataByStudyItemsContainer.getAnswerDataByStudyItemByIndex(index).countRightAnswerRate();
		}
		return sum * 100.0/ (double)answerDataByStudyItemsContainer.numberOfStudyItems();
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
		AnswerDataByStudyItemsContainer answerDataByStudyItemsContainer = new AnswerDataByStudyItemsContainer();
		answerDataByStudyItemsContainer.loadDataFromAnswerDataContainer(answerDataContainer);

		if (numberOfWords <= answerDataByStudyItemsContainer.numberOfStudyItems()) {
			AnswerDataByStudyItem[] datasToSort = answerDataByStudyItemsContainer.toArray();
			Arrays.sort(datasToSort, new AnswerDataByStudyItemComparatorByRateOfRightAnswers());
			String out = "";

			for (int i=0; i<numberOfWords; i++) {
				out = datasToSort[answerDataByStudyItemsContainer.numberOfStudyItems() - 1 - i].toStringShowingPercentage(studyItemContainer);
				System.out.println(out);
			}
		}
	}

}
