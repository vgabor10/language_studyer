package terminal_interface;

import common.Logger;
import dictionary.Card;
import dictionary.CardTester;
import java.io.Console;
import java.text.DecimalFormat;

public class CardTesterScreen {
    
    private CardTester cardTester;
    
    private final Logger logger = new Logger();
    
    public void setCardTester(CardTester ct) {
        cardTester = ct;
    }
    
    public void performTest() {

	Console console = System.console();
	DecimalFormat df = new DecimalFormat("#.00");

        while (cardTester.isMoreCardToTest()) {

            cardTester.moveToNextCardToQuestion();
            
            Card actualQuestionedCard = cardTester.getActualQuestionedCard();
            
            System.out.print("\033[H\033[2J");
            System.out.println(cardTester.numberOfCardsQuestioned() 
                    + "\\" + cardTester.getNumberOfQuestions());
            System.out.println("-------------------------------");
            System.out.println(actualQuestionedCard.definition);
            String answer = console.readLine();

            cardTester.setUserAnswer(answer);
            
            if (!cardTester.getStandardAnswerToLastQuestion().equals(answer) &&
                    !cardTester.isUserAnswerRight()) {
                
                do {
                    System.out.print("\033[H\033[2J");
                    System.out.println(cardTester.numberOfCardsQuestioned() 
                        + "\\" + cardTester.getNumberOfQuestions());
                    System.out.println("-------------------------------");
                    System.out.println(actualQuestionedCard.definition);
                    System.out.println(actualQuestionedCard.term);
                    answer = console.readLine();
		} while (!answer.equals(actualQuestionedCard.term));
            }
            
            if (!cardTester.getStandardAnswerToLastQuestion().equals(answer) &&
                    cardTester.isUserAnswerRight()) {
                
                do {
                    System.out.print("\033[H\033[2J");
                    System.out.println(cardTester.numberOfCardsQuestioned() 
                        + "\\" + cardTester.getNumberOfQuestions());
                    System.out.println("-------------------------------");
                    System.out.println(actualQuestionedCard.definition);
                    System.out.println("RIGHT, but the following word was tought:");
                    System.out.println(actualQuestionedCard.term);
                    answer = console.readLine();
                } while (!answer.equals(actualQuestionedCard.term));
            }
	} 
    }
}
