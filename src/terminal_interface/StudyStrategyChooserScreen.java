package terminal_interface;

import study_item_objects.AnswerDataContainer;
import dictionary.CardChooser;
import dictionary.CardContainer;
import java.io.Console;
import java.util.HashSet;
import java.util.Set;

public class StudyStrategyChooserScreen {
    
    private AnswerDataContainer answerDataContainer;
    private CardContainer cardContainer;
    private Set<Integer> cardsToTestIndexes = new HashSet<Integer>();
    
    Console console = System.console();
    
    public void setAnswerDataContainer(AnswerDataContainer adc) {
        answerDataContainer = adc;
    }
    
    public void setCardContainer(CardContainer cc) {
        cardContainer = cc;
    }
    
    public Set<Integer> getCardsToTestIndexes() {
        return cardsToTestIndexes;
    }
    
    public void toScreenMenuAndEvaluateCardsToTestIndexes() {
        CardChooser cardChooser = new CardChooser();
        cardChooser.setCardContainer(cardContainer);
        cardChooser.setAnswerDataContainer(answerDataContainer);
        
	System.out.print("\033[H\033[2J");
	System.out.println("practising with:");
	System.out.println("1 - 20 random cards from data base");
	System.out.println("2 - 6 latest studyed cards, 6 cards among the hardest 20%, 8 random cards");
        System.out.println("3 - 4 latest studyed cards, 8 among the hardest 20%, 8 random cards");
        System.out.println("4 - 10 cards from the hardest 100, 4 latest studied cards, 6 random cards");
        System.out.println("5 - 4 latest studyed cards, 8 among hardest 20%, 4 cards with least significant answer rate, 4 random cards");
        System.out.println("6 - 4 latest studyed cards, 8 among hardest 20%, 2 among cards with the 100 least significant answer rate, 6 random cards");
        System.out.println("7 - 4 latest studyed cards, 4 among hardest 20%, 4 from the hardes 100, 2 among cards with the 100 lest significant answer rate, 6 random cards");
        System.out.println("8 - experimantal way of choosing cards");


        String c = console.readLine();  //TODO: implement StudyStrategyChooserScreen
        if (c.equals("1")) {
            cardsToTestIndexes  = cardChooser.chooseCardsToTestIndexesForTest1();
        }
        if (c.equals("2")) {
            cardsToTestIndexes  = cardChooser.chooseCardsToTestIndexesForTest2();
        }
        if (c.equals("3")) {
            cardsToTestIndexes  = cardChooser.chooseCardsToTestIndexesForTest3();
        }
        if (c.equals("4")) {
            cardsToTestIndexes  = cardChooser.chooseCardsToTestIndexesForTest4();
        }
        if (c.equals("5")) {
            cardsToTestIndexes  = cardChooser.chooseCardsToTestIndexesForTest5();
        }
        if (c.equals("6")) {
            cardsToTestIndexes  = cardChooser.chooseCardsToTestIndexesForTest6();
        }
        if (c.equals("7")) {
            cardsToTestIndexes  = cardChooser.chooseCardsToTestIndexesForTest7();
        }
        if (c.equals("8")) {
            cardsToTestIndexes  = cardChooser.chooseCardsToTestIndexesForTest8();
        }        
    }
    
}
