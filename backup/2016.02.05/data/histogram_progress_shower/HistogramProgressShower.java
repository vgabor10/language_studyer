import java.util.*;
import java.io.Console;
import java.text.DecimalFormat;

public class HistogramProgressShower {


	public static void main(String[] args) {
		AnswerDataByCardsContainer answerDataByCardsContainer = new AnswerDataByCardsContainer();
		answerDataByCardsContainer.loadDataFromFile("../language_studyer/data/german_language_data/german_answer_data_file.txt");
		int numberOfStudyingDays = answerDataByCardsContainer.getNumberOfStudyingDays();
		System.out.println("number of studying days: " + numberOfStudyingDays);


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

}
