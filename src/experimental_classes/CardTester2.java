package experimental_classes;

import dictionary.*;

import java.util.*;
import java.util.*;
import java.io.Console;
import java.text.DecimalFormat;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.text.DecimalFormat;

public class CardTester2 {

	private Random randomGenerator = new Random();
	private CardContainer cardContainer;

	public void setCardContainer(CardContainer cc) {
		cardContainer = cc;
	}

	public Card getRandomCard() {
		int orderIndex = randomGenerator.nextInt(cardContainer.numberOfCards());
			
		return cardContainer.getCardByOrder(orderIndex);
	}

	public void performTest() {
		Console console = System.console();
		String answer = "aaa";

		do {

			System.out.print("\033[H\033[2J");

			Card card = getRandomCard();
			System.out.println(card.definition);
			answer = console.readLine();

			if (!answer.equals("x")) {

				if (answer.equals(card.term)) {	//right answer
					//System.out.println("RIGHT");
				}
				else {				//wrong answer
					do {
						System.out.print("\033[H\033[2J");
						System.out.println(card.definition);
						System.out.println(card.term);
						answer = console.readLine();
					} while (!answer.equals(card.term));
				}
			}
		}
		while (!answer.equals("x")); 
	}

}
