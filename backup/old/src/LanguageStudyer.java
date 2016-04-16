import grammar_book.*;
import dictionary.*;
import settings_handler.*;

import java.util.*;
import java.io.Console;
import java.text.DecimalFormat;

public class LanguageStudyer {

	public static void main(String[] args) {

		Console console = System.console();
		SettingsHandler settingsHandler = new SettingsHandler();
		String choice = "";

		CardContainer cardContainer = new CardContainer();
		cardContainer.loadDataFromFile(settingsHandler.getStudiedLanguageCardDataPath());

		GrammarBook grammarBook = new GrammarBook();
		grammarBook.loadGrammarBookFromFile(settingsHandler.getStudiedLanguageGrammarBookPath());

		do {

		System.out.print("\033[H\033[2J");
		System.out.println("LANGUAGE STUDYER - " + settingsHandler.getStudiedLanguageName().toUpperCase() + " LANGUAGE");
		System.out.println();
		System.out.println("DICTIONARY");
		System.out.println("1 - practicing");
		System.out.println("2 - basic statistics");
		System.out.println("3 - progress by words");
		System.out.println("4 - hardest words");
		System.out.println("5 - add card");
		System.out.println("6 - find cards according to term prefix");
		System.out.println("7 - list cards with same terms");
		System.out.println("8 - merge cards with same data");
		System.out.println("9 - list cards");
		System.out.println();
		System.out.println("GRAMMAR BOOK");
		System.out.println("10 - practicing");
		System.out.println("11 - read grammar book");
		System.out.println("12 - basic statistics");
		System.out.println("13 - percentage of right answers by grammar items");
		System.out.println();
		System.out.println("SETTINGS");
		System.out.println("14 - set language to study");
		System.out.println();
		System.out.println("x - quit");
		choice = console.readLine();

		if (choice.equals("1")) {
			CardTester cardTester = new CardTester();
			cardTester.setCardContainer(cardContainer);

			AnswerDataContainer answerDataContainer = new AnswerDataContainer();
			answerDataContainer.loadDataFromFile(settingsHandler.getStudiedLanguageAnswerDataPath());
			cardTester.setAnswerDataContainer(answerDataContainer);

			System.out.print("\033[H\033[2J");
			System.out.println("1 - practising with 20 random cards from data base (~6 min)");
			System.out.println("2 - practising with 6 latest studyed cards, 6 cards among the hardest ones, 8 random cards (~6 min)");
			String c = console.readLine();
			if (c.equals("1")) {
				cardTester.performTest1();
			}
			if (c.equals("2")) {
				cardTester.performTest2();
			}
		}

		if (choice.equals("2")) {
			DecimalFormat df = new DecimalFormat("#.00");
			AnswerDataContainer answerDataContainer = new AnswerDataContainer();
			answerDataContainer.loadDataFromFile(settingsHandler.getStudiedLanguageAnswerDataPath());

			AnswerDataStatisticsMaker answerDataStatisticsMaker = new AnswerDataStatisticsMaker();
			answerDataStatisticsMaker.setCardContainer(cardContainer);
			answerDataStatisticsMaker.setAnswerDataContainer(answerDataContainer);

			System.out.print("\033[H\033[2J");
			answerDataStatisticsMaker.toSreenNumberOfCards();
			answerDataStatisticsMaker.toSreenNumberOfAnswers();
			answerDataStatisticsMaker.toScreenNumberOfCardsQuestioned();
			answerDataStatisticsMaker.toScreenPercentageOfRightAnswers();
			answerDataStatisticsMaker.toSreenAverageAnswerRateOfCards();
			answerDataStatisticsMaker.toScreenProgress(10);
			answerDataStatisticsMaker.toScreenNumberOfAnswersGivenLastDays(10);
			answerDataStatisticsMaker.toScreenHistogram();

			console.readLine();
		}

		if (choice.equals("3")) {
			AnswerDataContainer answerDataContainer = new AnswerDataContainer();
			answerDataContainer.loadDataFromFile(settingsHandler.getStudiedLanguageAnswerDataPath());
			System.out.print("\033[H\033[2J");
			AnswerDataStatisticsMaker answerDataStatisticsMaker = new AnswerDataStatisticsMaker();
			answerDataStatisticsMaker.setAnswerDataContainer(answerDataContainer);
			answerDataStatisticsMaker.setCardContainer(cardContainer);
			answerDataStatisticsMaker.toScreenProgressByCards();

			console.readLine();
		}

		if (choice.equals("4")) {
			AnswerDataContainer answerDataContainer = new AnswerDataContainer();
			answerDataContainer.loadDataFromFile(settingsHandler.getStudiedLanguageAnswerDataPath());
			System.out.print("\033[H\033[2J");
			AnswerDataStatisticsMaker answerDataStatisticsMaker = new AnswerDataStatisticsMaker();
			answerDataStatisticsMaker.setAnswerDataContainer(answerDataContainer);
			answerDataStatisticsMaker.setCardContainer(cardContainer);
			answerDataStatisticsMaker.toScreenHardestWords(30);

			console.readLine();
		}

		if (choice.equals("5")) {

			String in = "";

			do {

				System.out.print("\033[H\033[2J");
				System.out.println("adding " + settingsHandler.getStudiedLanguageName().toUpperCase() + " language card to database");
				System.out.println("type card to add (termTABdefinition) or x to back:");
				in = console.readLine();

				if (!in.equals("x")) {

					int numberOfTabs = 0;       //check format
					for (int i=0; i<in.length(); i++) {
						if (in.charAt(i) == '\t') numberOfTabs++;
					}

					if (numberOfTabs !=1) {
						System.out.println("format is not appropriate");
						console.readLine();
					}
					else {
					String term = in.split("\t")[0];
		       			String definition = in.split("\t")[1];
		        		Card card = new Card(cardContainer.numberOfCards(), term, definition);
		       			Vector<Integer> foundCardIndexes = cardContainer.findCardsByTerm(term);

		       			if (foundCardIndexes.size() == 0) {
							cardContainer.addCardToContainerAndAppenToDiscFile(card,
								 settingsHandler.getStudiedLanguageCardDataPath());
							System.out.println("card added to data base with index " + Integer.toString(card.index));
							console.readLine();
						}
						else {
							System.out.println("cards found with the given term");
				     		for (int i=0; i<foundCardIndexes.size(); i++) {
					    		System.out.println(cardContainer.getCard(foundCardIndexes.get(i)).toStringData());
				      		}

					  		System.out.println("would you like to add the card to the data base? (y/n)");
	        				if (console.readLine().equals("y")) {
		        				cardContainer.addCardToContainerAndAppenToDiscFile(card, 
								settingsHandler.getStudiedLanguageCardDataPath());
		        				System.out.println("card added to data base with index " + Integer.toString(card.index));     									console.readLine();
			        		}
						}
					}
				}
			} while(!in.equals("x"));
		}

		if (choice.equals("6")) {
			System.out.print("\033[H\033[2J");
			System.out.println("type term prefix:");
			String prefix = console.readLine();
			System.out.println("cards with given term prefix:");
			cardContainer.toScreenCardsWithGivenTermPrefix(prefix);

			console.readLine();
		}

		if (choice.equals("7")) {
			System.out.print("\033[H\033[2J");
			cardContainer.toScreenCardsWithSameTerm();

			console.readLine();
		}

		if (choice.equals("8")) {
			System.out.print("\033[H\033[2J");
			DictionaryDataModificator dictionaryDataModificator = new DictionaryDataModificator();
			dictionaryDataModificator.setCardContainer(cardContainer);
			AnswerDataContainer answerDataContainer = new AnswerDataContainer();
			answerDataContainer.loadDataFromFile(settingsHandler.getStudiedLanguageAnswerDataPath());
			dictionaryDataModificator.setAnswerDataContainer(answerDataContainer);
			dictionaryDataModificator.mergeCardsWithSameData();

			console.readLine();
		}

		if (choice.equals("9")) {
			System.out.print("\033[H\033[2J");
			cardContainer.toScreenData();
			cardContainer.loadDataFromFile(settingsHandler.getStudiedLanguageCardDataPath());
			console.readLine();
		}

		if (choice.equals("10")) {
			System.out.print("\033[H\033[2J");
			System.out.println("How many examples do you want?");
			int numberOfExamples = Integer.parseInt(console.readLine());
			GrammarTester grammarTester = new GrammarTester();
			grammarTester.setGrammarBook(grammarBook);
			grammarTester.performTest(numberOfExamples);
		}

		if (choice.equals("11")) {	//read grammar book
			String a;
			do {
				System.out.print("\033[H\033[2J");
				grammarBook.toScreenTableOfContents();
				System.out.println();
				System.out.println("choose a grammar item index, or type x to back:");
				a = console.readLine();
				if (!a.equals("x")) {
					System.out.print("\033[H\033[2J");
					System.out.println(grammarBook.getGrammarItem(Integer.parseInt(a)).toString());
					console.readLine();
				}
			} while (!a.equals("x"));
		}

		if (choice.equals("12")) {	//basic statistics	
			System.out.print("\033[H\033[2J");
			GrammarAnswerDataStatisticsMaker grammarAnswerDataStatisticsMaker = new GrammarAnswerDataStatisticsMaker();
			GrammarAnswerDataContainer grammarAnswerDataContainer = new GrammarAnswerDataContainer();
			grammarAnswerDataContainer.loadDataFromFile(settingsHandler.getStudiedLanguageGrammarAnswerDataPath());

			grammarAnswerDataStatisticsMaker.setGrammarBook(grammarBook);
			grammarAnswerDataStatisticsMaker.setGrammarAnswerDataContainer(grammarAnswerDataContainer);

			grammarAnswerDataStatisticsMaker.toScreenNumberOfGrammarItems();
			grammarAnswerDataStatisticsMaker.toScreenNumberOfExamples();
			grammarAnswerDataStatisticsMaker.toScreenNumberOfAnswers();
			grammarAnswerDataStatisticsMaker.toScreenPercentageOfRightAnswers();

			console.readLine();
		}

		if (choice.equals("13")) {		//percentage of right answers by grammar items	//TODO: debug
			System.out.print("\033[H\033[2J");
			GrammarAnswerDataStatisticsMaker grammarAnswerDataStatisticsMaker = new GrammarAnswerDataStatisticsMaker();
			GrammarAnswerDataContainer grammarAnswerDataContainer = new GrammarAnswerDataContainer();
			grammarAnswerDataContainer.loadDataFromFile(settingsHandler.getStudiedLanguageGrammarAnswerDataPath());

			grammarAnswerDataStatisticsMaker.setGrammarBook(grammarBook);
			grammarAnswerDataStatisticsMaker.setGrammarAnswerDataContainer(grammarAnswerDataContainer);

			grammarAnswerDataStatisticsMaker.toScreenPercentageOfRightAnswersByGrammarItems();

			console.readLine();
		}

		if (choice.equals("14")) {
			System.out.print("\033[H\033[2J");
			System.out.println("Which language would you like to study?");
			System.out.println("0 - english");
			System.out.println("1 - german");
			int languageToStudyIndex = Integer.parseInt(console.readLine());
			settingsHandler.changeLanguageTostudy(languageToStudyIndex);
			cardContainer.clear();
			cardContainer.loadDataFromFile(settingsHandler.getStudiedLanguageCardDataPath());
		}

		if (choice.equals("x")) {
			System.out.print("\033[H\033[2J");
		}

		} while (!choice.equals("x"));
	}
}
