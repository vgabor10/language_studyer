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
		GrammarBookLoader grammarBookLoader = new GrammarBookLoader();
		GrammarBoofFileFormatChecker grammarBoofFileFormatChecker = new GrammarBoofFileFormatChecker();
		boolean isCorrect = grammarBoofFileFormatChecker.generalCheck(settingsHandler.getStudiedLanguageGrammarBookPath());
		if (isCorrect == false) {
			System.out.println("grammar book format is wrong");
			console.readLine();
		}

		grammarBookLoader.setGrammarBook(grammarBook);
		grammarBookLoader.loadGrammarBookFromFile(settingsHandler.getStudiedLanguageGrammarBookPath());

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
		System.out.println();
		System.out.println("GRAMMAR BOOK");
		System.out.println("10 - practicing");
		System.out.println("11 - read grammar book");
		System.out.println("12 - basic statistics");
		System.out.println("13 - percentage of right answers by grammar items");
		System.out.println("14 - modificate grammar item on frame TODO: implemet");
		System.out.println("15 - delete grammarItem");
		System.out.println("16 - grammarItemChooser test");
		System.out.println();
		System.out.println("SETTINGS");
		System.out.println("20 - set language to study");
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
			answerDataStatisticsMaker.toScreenNumberOfQuestionsOfLeastStudiedCard();
			answerDataStatisticsMaker.toSreenLastQuestionedCardDate();
			answerDataStatisticsMaker.toSreenNumberOfStudyingDays();
			answerDataStatisticsMaker.toScreenPractisingTime();
			answerDataStatisticsMaker.toScreenPercentageOfRightAnswers();
			answerDataStatisticsMaker.toSreenAverageAnswerRateOfCards();
			answerDataStatisticsMaker.toScreenProgress(10);
			answerDataStatisticsMaker.toScreenNumberOfAnswersGivenLastDays(10);
			answerDataStatisticsMaker.toScreenHistogram();

			console.readLine();
		}

		if (choice.equals("3")) {	//additional statistics

			String choice2;

			do {

			AnswerDataContainer answerDataContainer = new AnswerDataContainer();
			answerDataContainer.loadDataFromFile(settingsHandler.getStudiedLanguageAnswerDataPath());
			System.out.print("\033[H\033[2J");
			AnswerDataStatisticsMaker answerDataStatisticsMaker = new AnswerDataStatisticsMaker();
			answerDataStatisticsMaker.setAnswerDataContainer(answerDataContainer);
			answerDataStatisticsMaker.setCardContainer(cardContainer);

			System.out.println("1 - progress by words");
			System.out.println("2 - hardest words");
			System.out.println("3 - histogram of cards by number of answers");
			System.out.println("4 - histogram of cards by number of answers considered low categories");
			System.out.println("5 - practising time by days");
			System.out.println("6 - probability of right answer at first, second,... answers");
			System.out.println("7 - histogram of card answers rates by days");
			System.out.println("8 - number of given answers by hours");
			System.out.println("9 - to file histogram of card answers rates by days");
			System.out.println("10 - longest right answer intervall size");
			choice2 = console.readLine();

			if (choice2.equals("1")) {
				System.out.print("\033[H\033[2J");
				answerDataStatisticsMaker.toScreenProgressByCards();
				console.readLine();
			}

			if (choice2.equals("2")) {
				System.out.print("\033[H\033[2J");
				answerDataStatisticsMaker.toScreenHardestWords(30);
				console.readLine();
			}

			if (choice2.equals("3")) {
				System.out.print("\033[H\033[2J");
				answerDataStatisticsMaker.toScreenHistogramOfCardsByNumberOfAnswers();
				console.readLine();
			}

			if (choice2.equals("4")) {
				System.out.print("\033[H\033[2J");
				answerDataStatisticsMaker.toScreenHistogramOfCardsByNumberOfAnswersConsideredLowCategories();
				console.readLine();
			}

			if (choice2.equals("5")) {
				System.out.print("\033[H\033[2J");
				answerDataStatisticsMaker.toScreenPractisingTimeByDays();
				console.readLine();
			}		

			if (choice2.equals("6")) {
				System.out.print("\033[H\033[2J");
				answerDataStatisticsMaker.toScreenProbabilityOfRightAnswerAtFirstSecondEtcAnswers();
				console.readLine();
			}

			if (choice2.equals("7")) {
				System.out.print("\033[H\033[2J");
				answerDataStatisticsMaker.toScreenHistogramOfCardAnswerRatesByDays();
				console.readLine();
			}

			if (choice2.equals("8")) {
				System.out.print("\033[H\033[2J");
				answerDataStatisticsMaker.toScreenNumberOfGivenAnswersByHours();
				console.readLine();
			}

			if (choice2.equals("9")) {
				System.out.print("\033[H\033[2J");
				answerDataStatisticsMaker.toFileHistogramOfCardAnswerRatesByDays(
					"../data/temporary_data/histogram_of_card_answer_rates_by_days.txt");
				System.out.print(
					"data has benn saved to file: data/temporary_data/histogram_of_card_answer_rates_by_days.txt");
				console.readLine();
			}

			if (choice2.equals("10")) {
				System.out.print("\033[H\033[2J");
				answerDataStatisticsMaker.toSreenLongestIntervallSizeOfRightAnswers();
				console.readLine();
			}

			} while (!choice2.equals(""));
		}

		if (choice.equals("4")) {

			String choice2;

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
				cardContainer.toScreenCardsWithSameTerm();

				console.readLine();
			}

			if (choice2.equals("2")) {
				System.out.print("\033[H\033[2J");
				cardContainer.toScreenData();
				cardContainer.loadDataFromFile(settingsHandler.getStudiedLanguageCardDataPath());
				console.readLine();
			}

			if (choice2.equals("3")) {
				System.out.print("\033[H\033[2J");
				System.out.println("type cardIndex:");
				String inString = console.readLine();
				int cardIndex = Integer.parseInt(inString);
				cardContainer.toScreenCardWithGivenCardIndex(cardIndex);

				console.readLine();
			}

			if (choice2.equals("4")) {
				System.out.print("\033[H\033[2J");
				System.out.println("type definition part:");
				String definitionPart = console.readLine();
				System.out.println("results:");
				cardContainer.toScreenCardsWithGivenDefinitionPart(definitionPart);
			
				console.readLine();
			}

			if (choice2.equals("5")) {
				AnswerDataContainer answerDataContainer = new AnswerDataContainer();
				answerDataContainer.loadDataFromFile(settingsHandler.getStudiedLanguageAnswerDataPath());

				System.out.print("\033[H\033[2J");
				System.out.println("type term prefix:");
				String prefix = console.readLine();
				System.out.println("cards with given term prefix /cardIndex term definition | percentageOfRightAnswers  (numberOfAnswers)/:");
				cardContainer.toScreenCardsWithGivenTermPrefix(prefix, answerDataContainer);

				console.readLine();
			}

			} while (!choice2.equals(""));
		}

		if (choice.equals("5")) {
			System.out.print("\033[H\033[2J");
			System.out.println("1 - merge cards with same data");
			System.out.println("2 - remove card by index //TODO: implement");

			String choice2 = console.readLine();

			if (choice2.equals("1")) {
				System.out.print("\033[H\033[2J");
				DictionaryDataModificator dictionaryDataModificator = new DictionaryDataModificator();
				dictionaryDataModificator.setCardContainer(cardContainer);
				AnswerDataContainer answerDataContainer = new AnswerDataContainer();
				answerDataContainer.loadDataFromFile(settingsHandler.getStudiedLanguageAnswerDataPath());
				dictionaryDataModificator.setAnswerDataContainer(answerDataContainer);
				dictionaryDataModificator.mergeCardsWithSameData();

				console.readLine();
			}

			if (choice2.equals("2")) {
				System.out.print("\033[H\033[2J");
				System.out.println("type the cardIndex of card, which you would like to remove");
				try {
					int cardIndex = Integer.parseInt(console.readLine());
					System.out.println("are you sure, that, you would like to remove the folloving card? (y/n)");
					System.out.println(cardContainer.getCard(cardIndex).toStringData());
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

		if (choice.equals("7")) {
			AnswerDataContainer answerDataContainer = new AnswerDataContainer();
			answerDataContainer.loadDataFromFile(settingsHandler.getStudiedLanguageAnswerDataPath());


			String s = "";
			do {
				System.out.print("\033[H\033[2J");
				System.out.println("type term part, or x to quit:");
				s = console.readLine();
				if (!s.equals("x")) {
					System.out.println("cards with given term prefix /cardIndex term definition | percentageOfRightAnswers  (numberOfAnswers)/:");
					cardContainer.toScreenCardsWithGivenTermPart(s, answerDataContainer);

					console.readLine();
				}
			} while (!s.equals("x"));
		}

		if (choice.equals("10")) {
			System.out.print("\033[H\033[2J");
			grammarBook.toScreenTableOfContents();
			System.out.println();
			System.out.println("index of gramarItem you would like to study:");
			try{
				int orderIndex = Integer.parseInt(console.readLine());
				if (0 <= orderIndex && orderIndex < grammarBook.numberOfGrammarItems()) {
					GrammarTester grammarTester = new GrammarTester();
					grammarTester.setGrammarBook(grammarBook);
					grammarTester.performTest(orderIndex, 10);
				}
				else {
					System.out.print("\033[H\033[2J");
					System.out.println("there is not exists GrammarItem with given index");
					console.readLine();
				}
			} catch (NumberFormatException e) {
			}
		}

		if (choice.equals("11")) {	//read grammar book
			String a;
			do {
				System.out.print("\033[H\033[2J");
				grammarBook.toScreenTableOfContents();
				System.out.println();
				System.out.println("choose a grammar item index, or ENTER to back:");
				a = console.readLine();
				if (!a.equals("")) {
					System.out.print("\033[H\033[2J");
					System.out.println(grammarBook.getGrammarItem(Integer.parseInt(a)).toString());
					console.readLine();
				}
			} while (!a.equals(""));
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
			System.out.println("type the index of grammar item, which you would like to modificate:");

			GrammarItemModificatorFrame grammarItemModificatorFrame = new GrammarItemModificatorFrame();
			//grammarItemModificatorFrame.setGrammarItemTitle("aaa");
			grammarItemModificatorFrame.createAndShowGUI();
		}

		if (choice.equals("15")) {
			System.out.print("\033[H\033[2J");
			System.out.println("type the index of grammar item, which you would like to delete:");

			int grammarItemIndex = Integer.parseInt(console.readLine());

			System.out.println("are you sure you would like to delete this GrammarItem? (y/n)");
			System.out.println(grammarBook.getGrammarItem(grammarItemIndex).title);

			/*if (console.readLine().equals("y")) {
				/*GrammarDataModificator grammarDataModificator = new GrammarDataModificator();
				grammarDataModificator.setGrammarBook(grammarBook);

				GrammarAnswerDataContainer grammarAnswerDataContainer = new GrammarAnswerDataContainer();
				grammarAnswerDataContainer.loadDataFromFile(settingsHandler.getStudiedLanguageGrammarAnswerDataPath());
				grammarDataModificator.setGrammarAnswerDataContainer(grammarAnswerDataContainer);

				//grammarDataModificator.

				System.out.println("GrammarItem has been deleted");
			}*/

			console.readLine();
		}

		if (choice.equals("16")) {
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
	}
}
