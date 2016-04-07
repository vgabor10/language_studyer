import grammar_book.*;
import dictionary.*;
import settings_handler.*;
import common.*;
import terminal_interface.*;

import java.util.*;
import java.io.Console;
import java.text.DecimalFormat;

public class LanguageStudyer {

	public static void main(String[] args) {

		Logger logger = new Logger();
		logger.setLogFile();
		logger.debug("start program");

		Console console = System.console();
		SettingsHandler settingsHandler = new SettingsHandler();
		String choice = "";

		////////////////////// start loading data ////////////////////// 

		CardContainer cardContainer = new CardContainer();
		cardContainer.loadDataFromFile(settingsHandler.getStudiedLanguageCardDataPath());

		TestedCardGroupHandler testedCardGroupHandler = new TestedCardGroupHandler();
		testedCardGroupHandler.setCardContainer(cardContainer);
		testedCardGroupHandler.setCardGroups();

		GrammarBook grammarBook = new GrammarBook();
		GrammarBookLoader grammarBookLoader = new GrammarBookLoader();
		GrammarBookFileFormatChecker grammarBookFileFormatChecker = new GrammarBookFileFormatChecker();
		boolean isCorrect = grammarBookFileFormatChecker.generalCheck(settingsHandler.getStudiedLanguageGrammarBookPath());
		if (isCorrect == false) {
			System.out.println("grammar book format is wrong");
			console.readLine();
		}

		grammarBookLoader.setGrammarBook(grammarBook);
		grammarBookLoader.loadGrammarBookFromFile(settingsHandler.getStudiedLanguageGrammarBookPath());

		AnswerDataContainer answerDataContainer = new AnswerDataContainer();
		answerDataContainer.loadDataFromFile(settingsHandler.getStudiedLanguageAnswerDataPath());

		GrammarAnswerDataContainer grammarAnswerDataContainer = new GrammarAnswerDataContainer();
		grammarAnswerDataContainer.loadDataFromFile(settingsHandler.getStudiedLanguageGrammarAnswerDataPath());

		////////////////////// end loading data ////////////////////// 

		do {

		System.out.print("\033[H\033[2J");
		System.out.println("LANGUAGE STUDYER - " + settingsHandler.getStudiedLanguageName().toUpperCase() + " LANGUAGE");
		System.out.println();
		System.out.println("DICTIONARY");
		System.out.println("1 - practicing");
		System.out.println("2 - basic statistics");
		System.out.println("3 - additional statistics");
		System.out.println("4 - search cards");
		System.out.println("5 - modificate cards");
		System.out.println("6 - add card");
		System.out.println("7 - find cards according to term part");
		System.out.println("8 - set card groups to test");
		System.out.println();
		System.out.println("GRAMMAR BOOK");
		System.out.println("10 - practicing");
		System.out.println("11 - read grammar book");
		System.out.println("12 - basic statistics");
		System.out.println("13 - additional statistics");
		System.out.println("14 - modificate grammar item");
		System.out.println("15 - grammarItemChooser test");
		System.out.println();
		System.out.println("SETTINGS");
		System.out.println("20 - set language to study");
		System.out.println();
		System.out.println("x - quit");
		choice = console.readLine();

		if (choice.equals("1")) {
			CardTester cardTester = new CardTester();

			cardTester.setCardsToTest(testedCardGroupHandler.cardsToTest);
			cardTester.setAnswerDataContainer(answerDataContainer);
			cardTester.setCardChooser();

			System.out.print("\033[H\033[2J");
			System.out.println("practising with:");
			System.out.println("1 - 20 random cards from data base");
			System.out.println("2 - 6 latest studyed cards, 6 cards among the hardest 20%, 8 random cards");
			System.out.println("3 - 4 latest studyed cards, 8 among the hardest 20%, 8 random cards");
			System.out.println("4 - 10 cards from the hardest 100, 4 latest studied cards, 6 random cards");
			System.out.println("5 - 4 latest studyed cards, 8 among hardest 20%, 4 cards with least significant answer rate, 4 random cards");
			System.out.println("6 - 4 latest studyed cards, 8 among hardest 20%, 2 among cards with the 100 least significant answer rate, 6 random cards");
			System.out.println("7 - 4 latest studyed cards, 4 among hardest 20%, 4 from the hardes 100, 2 among cards with the 100 lest significant answer rate, 6 random cards");

			String c = console.readLine();
			if (c.equals("1")) {
				cardTester.performTest1();
			}
			if (c.equals("2")) {
				cardTester.performTest2();
			}
			if (c.equals("3")) {
				cardTester.performTest3();
			}
			if (c.equals("4")) {
				cardTester.performTest4();
			}
			if (c.equals("5")) {
				cardTester.performTest5();
			}
			if (c.equals("6")) {
				cardTester.performTest6();
			}
			if (c.equals("7")) {
				cardTester.performTest7();
			}
		}

		//basic statistics
		if (choice.equals("2")) {
			AnswerDataStatisticsMaker answerDataStatisticsMaker = new AnswerDataStatisticsMaker();
			answerDataStatisticsMaker.setStudyItemContainer(cardContainer);
			answerDataStatisticsMaker.setAnswerDataContainer(answerDataContainer);
			TerminalDictionaryStatisticsShower terminalDictionaryStatisticsShower = new TerminalDictionaryStatisticsShower();
			terminalDictionaryStatisticsShower.setAnswerDataStatisticsMaker(answerDataStatisticsMaker); 
			terminalDictionaryStatisticsShower.toScreenDictionaryBasicStatistics();
			console.readLine();
		}

		//additional statistics
		if (choice.equals("3")) {
			DictionaryAdditionalStatisticsShower dictionaryAdditionalStatisticsShower = new DictionaryAdditionalStatisticsShower();
			AnswerDataStatisticsMaker answerDataStatisticsMaker = new AnswerDataStatisticsMaker();
			answerDataStatisticsMaker.setAnswerDataContainer(answerDataContainer);
			answerDataStatisticsMaker.setStudyItemContainer(cardContainer);
			dictionaryAdditionalStatisticsShower.setAnswerDataStatisticsMaker(answerDataStatisticsMaker);
			dictionaryAdditionalStatisticsShower.showStatisticsChooser();
		}

		if (choice.equals("4")) {

			String choice2;

			CardFinder cardFinder = new CardFinder();
			cardFinder.setCardContainer(cardContainer);
			cardFinder.setAnswerDataContainer(answerDataContainer);

			do {

			System.out.print("\033[H\033[2J");
			System.out.println("1 - list cards with same terms");
			System.out.println("2 - list cards");
			System.out.println("3 - search card by cardIndex");
			System.out.println("4 - search card by definition part");
			System.out.println("5 - search cards according to term prefix");

			choice2 = console.readLine();

			if (choice2.equals("1")) {
				System.out.print("\033[H\033[2J");
				System.out.println("cards with same terms:");
				cardFinder.toScreenCardsWithSameTerm();

				console.readLine();
			}

			if (choice2.equals("2")) {
				System.out.print("\033[H\033[2J");
				cardFinder.toScreenAllCards();
				console.readLine();
			}

			if (choice2.equals("3")) {
				System.out.print("\033[H\033[2J");
				System.out.println("type cardIndex:");
				String inString = console.readLine();
				int cardIndex = Integer.parseInt(inString);
				cardFinder.toScreenCardWithGivenCardIndex(cardIndex);
				console.readLine();
			}

			if (choice2.equals("4")) {
				System.out.print("\033[H\033[2J");
				System.out.println("type definition part:");
				String definitionPart = console.readLine();
				System.out.println("results:");
				cardFinder.toScreenCardsWithGivenDefinitionPart(definitionPart);
				console.readLine();
			}

			if (choice2.equals("5")) {
				System.out.print("\033[H\033[2J");
				System.out.println("type term prefix:");
				String prefix = console.readLine();
				System.out.println("cards with given term prefix /cardIndex term definition | percentageOfRightAnswers  (numberOfAnswers)/:");
				cardFinder.toScreenCardsWithGivenTermPrefix(prefix);
				console.readLine();
			}

			} while (!choice2.equals(""));
		}

		if (choice.equals("5")) {
			System.out.print("\033[H\033[2J");
			System.out.println("1 - merge cards with same data");
			System.out.println("2 - remove card by index //TODO: implement");

			String choice2 = console.readLine();

			DictionaryDataModificator dictionaryDataModificator = new DictionaryDataModificator();
			dictionaryDataModificator.setCardContainer(cardContainer);
			dictionaryDataModificator.setAnswerDataContainer(answerDataContainer);
			dictionaryDataModificator.setSettingsHandler(settingsHandler);

			if (choice2.equals("1")) {
				System.out.print("\033[H\033[2J");
				dictionaryDataModificator.mergeCardsWithSameData();
				console.readLine();
			}

			if (choice2.equals("2")) {
				System.out.print("\033[H\033[2J");
				System.out.println("type the cardIndex of card, which you would like to remove");
				try {
					int cardIndex = Integer.parseInt(console.readLine());
					System.out.println("are you sure, that, you would like to remove the folloving card? (y/n)");
					System.out.println(cardContainer.getCardByIndex(cardIndex).toStringData());
					String a = console.readLine();
					if (a.equals("y")) {
						//TODO: implement
					}
					System.out.println("card had been removed //not implemented");
				} catch (NumberFormatException e) {
					System.out.println("given value is not an integer");
				}
				console.readLine();
			}
		}

		if (choice.equals("6")) {

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
					    		System.out.println(cardContainer.getCardByIndex(foundCardIndexes.get(i)).toStringData());
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

		if (choice.equals("7")) {

			CardFinder cardFinder = new CardFinder();
			cardFinder.setCardContainer(cardContainer);
			cardFinder.setAnswerDataContainer(answerDataContainer);

			String s = "";
			do {
				System.out.print("\033[H\033[2J");
				System.out.println("type term part, or x to quit:");
				s = console.readLine();
				if (!s.equals("x")) {
					cardFinder.toScreenCardsWithGivenTermPart(s);

					console.readLine();
				}
			} while (!s.equals("x"));
		}

		//choose card groups to study
		if (choice.equals("8")) {
			testedCardGroupHandler.toScreenTestedCardGroupsChooser();
		}

		//practising grammar items
		if (choice.equals("10")) {
			System.out.print("\033[H\033[2J");

			System.out.println("1 - practising with given grammar item");
			System.out.println("2 - practising with the last studyed grammar item with at least 10 examples");

			GrammarTester grammarTester = new GrammarTester();
			grammarTester.setGrammarBook(grammarBook);
			grammarTester.setGrammarAnswerDataContainer(grammarAnswerDataContainer);

			String c = console.readLine();
			if (c.equals("1")) {
				System.out.print("\033[H\033[2J");
				grammarBook.toScreenTableOfContents();
				System.out.println();
				System.out.println("index of gramarItem you would like to study:");
				try{
					int orderIndex = Integer.parseInt(console.readLine());
					if (0 <= orderIndex && orderIndex < grammarBook.numberOfGrammarItems()) {
						grammarTester.performTestByOrderIndex(orderIndex, 10);
					}
					else {
						System.out.print("\033[H\033[2J");
						System.out.println("there is not exists GrammarItem with given index");
						console.readLine();
					}
				} catch (NumberFormatException e) {
				}
			}

			if (c.equals("2")) {
				System.out.print("\033[H\033[2J");
				GrammarAnswerDataStatisticsMaker grammarAnswerDataStatisticsMaker = new GrammarAnswerDataStatisticsMaker();

				grammarAnswerDataStatisticsMaker.setGrammarBook(grammarBook);
				grammarAnswerDataStatisticsMaker.setGrammarAnswerDataContainer(grammarAnswerDataContainer);

				int grammarItemIndex = grammarAnswerDataStatisticsMaker.getLastStudiedGrammarItemIndex();
				grammarTester.performTestByGrammarItemIndex(grammarItemIndex, 10);
			}
		}

		//read grammar book
		if (choice.equals("11")) {
			String a;
			do {
				System.out.print("\033[H\033[2J");
				grammarBook.toScreenTableOfContents();
				System.out.println();
				System.out.println("choose a grammar item index, or ENTER to back:");
				a = console.readLine();
				if (!a.equals("")) {
					System.out.print("\033[H\033[2J");
					System.out.println(grammarBook.getGrammarItemByOrder(Integer.parseInt(a)).toString());
					console.readLine();
				}
			} while (!a.equals(""));
		}

		//basic statistics
		if (choice.equals("12")) {
			GrammarAnswerDataStatisticsMaker grammarAnswerDataStatisticsMaker = new GrammarAnswerDataStatisticsMaker();
			grammarAnswerDataStatisticsMaker.setGrammarBook(grammarBook);
			grammarAnswerDataStatisticsMaker.setGrammarAnswerDataContainer(grammarAnswerDataContainer);
			TerminalGrammarStatisticsShower terminalGrammarStatisticsShower = new TerminalGrammarStatisticsShower();
			terminalGrammarStatisticsShower.setGrammarAnswerDataStatisticsMaker(grammarAnswerDataStatisticsMaker); 
			terminalGrammarStatisticsShower.toScreenGrammarBookBasicStatistics();
			console.readLine();
		}

		//additional statistics
		if (choice.equals("13")) {
			GrammarAdditionalStatisticsShower grammarAdditionalStatisticsShower = new GrammarAdditionalStatisticsShower();
			GrammarAnswerDataStatisticsMaker grammarAnswerDataStatisticsMaker = new GrammarAnswerDataStatisticsMaker();
			grammarAnswerDataStatisticsMaker.setAnswerDataContainer(grammarAnswerDataContainer);
			grammarAnswerDataStatisticsMaker.setStudyItemContainer(grammarBook);
			grammarAdditionalStatisticsShower.setGrammarAnswerDataStatisticsMaker(grammarAnswerDataStatisticsMaker);
			grammarAdditionalStatisticsShower.showStatisticsChooser();
		}

		//modificate grammar item
		if (choice.equals("14")) {
			String choice2;
			do {

			System.out.print("\033[H\033[2J");
			System.out.println("1 - delete grammarItem TODO: implement");
			System.out.println("2 - check whether exists answer data with invalid grammar item index");

			choice2 = console.readLine();

			if (choice2.equals("1")) {
				/*System.out.print("\033[H\033[2J");
				System.out.println("type the index of grammar item, which you would like to delete:");

				int grammarItemIndex = Integer.parseInt(console.readLine());

				System.out.println("are you sure you would like to delete this GrammarItem? (y/n)");
				System.out.println(grammarBook.getGrammarItemByIndex(grammarItemIndex).title);*/

				/*if (console.readLine().equals("y")) {
					/*GrammarDataModificator grammarDataModificator = new GrammarDataModificator();
					grammarDataModificator.setGrammarBook(grammarBook);

					GrammarAnswerDataContainer grammarAnswerDataContainer = new GrammarAnswerDataContainer();
					grammarAnswerDataContainer.loadDataFromFile(settingsHandler.getStudiedLanguageGrammarAnswerDataPath());
					grammarDataModificator.setGrammarAnswerDataContainer(grammarAnswerDataContainer);

					//grammarDataModificator.

					System.out.println("GrammarItem has been deleted");
				}*/
			}

			if (choice2.equals("2")) {
				grammarBookFileFormatChecker.setGrammarBook(grammarBook);
				grammarBookFileFormatChecker.setGrammarAnswerDataContainer(grammarAnswerDataContainer);

				System.out.print("\033[H\033[2J");
				grammarBookFileFormatChecker.isThereAnswerDataWithInvalidGrammarItemIndex();

				console.readLine();
			}

			} while (!choice2.equals(""));
		}

		if (choice.equals("15")) {
			System.out.print("\033[H\033[2J");
			GrammarItemChooser grammarItemChooser = new GrammarItemChooser();
			grammarItemChooser.setGrammarBook(grammarBook);
			grammarItemChooser.chooseGrammarItem();
			console.readLine();
		}

		if (choice.equals("20")) {
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

		logger.debug("close program");
	}
}
