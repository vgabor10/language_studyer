package dictionary;

import common.*;

import java.util.*;
import java.io.Console;

public class TestedCardGroupHandler {	//TODO: rename CardsToTestHandler 

	private CardContainer cardContainer;
	public CardContainer cardsToTest = new CardContainer();
	private Map<String, Boolean> isCardGroupsTested = new HashMap<String, Boolean>();

	public void setCardContainer(CardContainer cc) {
		cardContainer = cc;
	}

	public void setCardGroups() {
		for (int i=0; i<cardContainer.numberOfCards(); i++) {
			Card card = cardContainer.getCardByOrder(i);
			if (!isCardGroupsTested.containsKey(card.group)) {
				if (card.group.equals("no group")) {
					isCardGroupsTested.put("no group", true);
				}
				else {
					isCardGroupsTested.put(card.group, false);
				}
			}

			if (card.group.equals("no group")) {
				cardsToTest.addCard(card);
			}
		}
	}

	private void refreshCardsToTestAccordingToMapVariable() {
		cardsToTest.clear();
		for (int i=0; i<cardContainer.numberOfCards(); i++) {
			Card card = cardContainer.getCardByOrder(i);
			if (isCardGroupsTested.get(card.group)) {
				cardsToTest.addCard(card);
			}
		}
	}

	public void toScreenTestedCardGroupsChooser() {

		Console console = System.console();
		String s;

		do {

		System.out.print("\033[H\033[2J");
		System.out.println("change card groups to test, or type ENTER to go back:");

		int index = 0;
		Vector<String> indexAndGroupName = new Vector<String>();
		for (String groupName: isCardGroupsTested.keySet()) {
			System.out.println(index + " - " + groupName + " - " + isCardGroupsTested.get(groupName));
			indexAndGroupName.add(groupName);
			index++;
		}

		s = console.readLine();

		if (!s.equals("")) {
			index = Integer.parseInt(s);
			String groupName = indexAndGroupName.elementAt(index);
			boolean isTested = isCardGroupsTested.get(groupName);
			isCardGroupsTested.remove(groupName);
			isCardGroupsTested.put(groupName,!isTested);
			refreshCardsToTestAccordingToMapVariable();
		}

		} while (!s.equals(""));
	}
}
