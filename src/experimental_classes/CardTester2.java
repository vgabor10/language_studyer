package experimental_classes;

import dictionary.*;
import common.*;

import java.util.*;
import java.io.*;
import java.text.SimpleDateFormat;
import java.text.DateFormat;
import java.text.DecimalFormat;

public class CardTester2 {

	private Random randomGenerator = new Random();
	private CardContainer cardContainer;
	private AnswerDataContainer answerDataContainer = new AnswerDataContainer();

	private Logger logger = new Logger();

	public void setCardContainer(CardContainer cc) {
		cardContainer = cc;
	}

	public Card getRandomCard() {
		int orderIndex = randomGenerator.nextInt(cardContainer.numberOfCards());
			
		return cardContainer.getCardByOrder(orderIndex);
	}

	public Set<String> getAcceptabelAnswers(String definition) {
		Set<String> out = new HashSet<String>();

		Set<String> definitionParts = new HashSet<String>(Arrays.asList(definition.split(", ")));

		for (int i=0; i<cardContainer.numberOfCards(); i++) {
			Card card = cardContainer.getCardByOrder(i);

			Set<String> definitionParts2 = new HashSet<String>(Arrays.asList(card.definition.split(", ")));

			definitionParts2.retainAll(definitionParts);

			if (definitionParts2.size() != 0) {
				out.add(card.term);
			}
		}

		return out;
	}

	public void performTest() {
		Console console = System.console();
		String answer = "aaa";

		long startTime = System.currentTimeMillis();

		do {

			System.out.print("\033[H\033[2J");

			Card card = getRandomCard();
			Set<String> acceptabelAnswers = getAcceptabelAnswers(card.definition);

			logger.debug("questioned card: " + card.toString());
			logger.debug("acceptabel answers: " + acceptabelAnswers.toString());

			System.out.println(card.definition);
			answer = console.readLine();

			if (!answer.equals("x")) {

				if (answer.equals(card.term)) {	//right answer
					Date date = new Date();
					answerDataContainer.addElement(card.index, true, date.getTime());
				}
				else {				//wrong answer

					if (acceptabelAnswers.contains(answer)) {
						Date date = new Date();
						answerDataContainer.addElement(-1, true, date.getTime());
						do {
							System.out.print("\033[H\033[2J");
							System.out.println(card.definition);
							System.out.println("RIGHT, but I tought the following word:");
							System.out.println(card.term);
							answer = console.readLine();
						} while (!answer.equals(card.term));
					}
					else {
						Date date = new Date();
						answerDataContainer.addElement(card.index, false, date.getTime());
						do {
							System.out.print("\033[H\033[2J");
							System.out.println(card.definition);
							System.out.println(card.term);
							answer = console.readLine();
						} while (!answer.equals(card.term));
					}
				}
			}
		}
		while (!answer.equals("x"));

		long endTime = System.currentTimeMillis();

		System.out.print("\033[H\033[2J");

		Date date = new Date(endTime - startTime);
		DateFormat formatter = new SimpleDateFormat("mm:ss");
		String dateFormatted = formatter.format(date);

		System.out.println("number of answers: " + answerDataContainer.numberOfAnswers());

		DecimalFormat df = new DecimalFormat("#.000");
		System.out.println("percentage of right answers: " + df.format(answerDataContainer.percentageOfRightAnswers()) + "%");
		System.out.println("used time: " + dateFormatted);

		console.readLine();
	}

}
