package dictionary;

import study_item_objects.answer_data_by_study_item_comparators.AnswerDataByStudyItemComparatorByNumberOfAnswers;
import study_item_objects.AnswerDataContainer;
import study_item_objects.AnswerDataByStudyItemContainer;
import study_item_objects.AnswerDataByStudyItem;
import common.Logger;
import java.util.Arrays;
import java.util.Collections;
import java.util.HashSet;
import java.util.Random;
import java.util.Set;
import study_item_objects.AnswerData;
import study_item_objects.answer_data_by_study_item_comparators.AnswerDataByStudyItemComparatorByLastStudyDate;
import study_item_objects.answer_data_by_study_item_comparators.AnswerDataByStudyItemComparatorByRateOfRightAnswers;

public class CardChooser {

    private CardContainer cardContainer;
    private AnswerDataContainer answerDataContainer;

    private final Random randomGenerator = new Random();
    private AnswerDataByStudyItemContainer answerDataByStudyItemsContainer = new AnswerDataByStudyItemContainer();;
    private final Logger logger = new Logger();
    
    public void setData(DataContainer ddc) {
        cardContainer = ddc.cardContainer;
        answerDataContainer = ddc.answerDataContainer;
        
        answerDataByStudyItemsContainer.loadDataFromAnswerDataContainer(answerDataContainer);
    }

    public void setData(DataContainer ddc, Set<Integer> cardCategoris) {
        cardContainer = new CardContainer();
        answerDataContainer = new AnswerDataContainer();

        for (int i=0; i<ddc.cardContainer.numberOfCards(); i++) {
            Card card = ddc.cardContainer.getCardByOrder(i);
            if (!Collections.disjoint(cardCategoris, card.categoryIndexes)) {
                cardContainer.addCard(card);
            }
        }
        
        Set<Integer> cardIndexes = cardContainer.getCardIndexes();
        for (int i=0; i<ddc.answerDataContainer.numberOfAnswers(); i++) {
            AnswerData answerData = ddc.answerDataContainer.getAnswerData(i);
            if (cardIndexes.contains(answerData.index)) {
                answerDataContainer.addAnswerData(answerData);
                answerDataByStudyItemsContainer.addAnswerData(answerData);
            }
        }
    }
    
    private int getRandomCardIndex(
       Set<Integer> cardIndexesFromChoose, Set<Integer> omittedCardIndexes) {

        cardIndexesFromChoose.removeAll(omittedCardIndexes);
        
        if (!cardIndexesFromChoose.isEmpty()) {
        
            int r = randomGenerator.nextInt(cardIndexesFromChoose.size());

            int i = 0;
            for (int cardIndex : cardIndexesFromChoose) {
                if (i == r) {
                    return cardIndex;
                }
                i++;
            }
        }
        else {
            return -1;
        }
        
        return -2;
    }

    public Set<Integer> getFormerlyQuestionedCardIndexes(
        int numberOfCards, Set<Integer> omittedCardIndexes) {
        
        Set<Integer> notQuestionedCardIndexes = cardContainer.getCardIndexes();
        notQuestionedCardIndexes.removeAll(answerDataByStudyItemsContainer.getTestedStudyItemIndexes());
        
        Set<Integer> outCardIndexes = new HashSet<>();        
        for (int cardIndex : notQuestionedCardIndexes) {
            if (outCardIndexes.size() == numberOfCards) {
                return outCardIndexes;
            }
            else {
                if (!omittedCardIndexes.contains(cardIndex)) {
                    outCardIndexes.add(cardIndex);
                    omittedCardIndexes.add(cardIndex);
                }
            }
            
        }
        
        AnswerDataByStudyItem[] datasToSort = answerDataByStudyItemsContainer.toArray();
        Arrays.sort(datasToSort, new AnswerDataByStudyItemComparatorByLastStudyDate());

        for (AnswerDataByStudyItem answerDataByStudyItem : datasToSort) {
            int cardIndex = answerDataByStudyItem.getStudyItemIndex();
            
            if (outCardIndexes.size() == numberOfCards) {
                return outCardIndexes;
            }
            else {
                if (!omittedCardIndexes.contains(cardIndex)) {
                    outCardIndexes.add(cardIndex);
                    omittedCardIndexes.add(cardIndex);
                }
            }
        }

        return outCardIndexes;
    }

    public Set<Integer> getRandomHardestCardIndexes(
            double hardestWordRate, int numberOfCards, Set<Integer> omittedCardIndexes) {

        AnswerDataByStudyItem[] datasToSort = answerDataByStudyItemsContainer.toArray();
        Arrays.sort(datasToSort,
                Collections.reverseOrder(new AnswerDataByStudyItemComparatorByRateOfRightAnswers()));

        Set<Integer> cardIndexesFromChoose = new HashSet<>();
        for (int i = 0; i < Math.floor((double) answerDataByStudyItemsContainer.numberOfStudiedStudyItems() * hardestWordRate); i++) {
            cardIndexesFromChoose.add(datasToSort[i].getStudyItemIndex());
        }

        Set<Integer> outCardIndexes = new HashSet<>();
        for (int i = 0; i < numberOfCards; i++) {
            int cardIndex = getRandomCardIndex(cardIndexesFromChoose, omittedCardIndexes);
            outCardIndexes.add(cardIndex);
            omittedCardIndexes.add(cardIndex);
        }

        return outCardIndexes;
    }

    public Set<Integer> getRandomHardestCardIndex2(
            int numberOfHardestCards, int numberOfCardsGet, Set<Integer> omittedCardIndexes) {

        AnswerDataByStudyItem[] datasToSort = answerDataByStudyItemsContainer.toArray();
        Arrays.sort(datasToSort,
                Collections.reverseOrder(new AnswerDataByStudyItemComparatorByRateOfRightAnswers()));

        Set<Integer> cardIndexesFromChoose = new HashSet<>();
        for (int i = 0; i < 100; i++) {
            cardIndexesFromChoose.add(datasToSort[i].getStudyItemIndex());
        }

        Set<Integer> outCardIndexes = new HashSet<>();
        for (int i = 0; i < numberOfCardsGet; i++) {
            int cardIndex = getRandomCardIndex(cardIndexesFromChoose, omittedCardIndexes);
            outCardIndexes.add(cardIndex);
            omittedCardIndexes.add(cardIndex);
        }

        return outCardIndexes;
    }

    public Set<Integer> getCardIndexesWithLestSignificantAnswerRate(
            int numberOfCards, Set<Integer> omitedCardIndexes) {

        AnswerDataByStudyItem[] datasToSort = answerDataByStudyItemsContainer.toArray();
        Arrays.sort(datasToSort, new AnswerDataByStudyItemComparatorByNumberOfAnswers());

        Set<Integer> outCardIndexes = new HashSet<>();

        int i = 0;
        while (outCardIndexes.size() != numberOfCards && i < datasToSort.length) {
            if (!omitedCardIndexes.contains(datasToSort[i].getStudyItemIndex())) {
                outCardIndexes.add(datasToSort[i].getStudyItemIndex());
            }
            i++;
        }

        return outCardIndexes;
    }

    public Set<Integer> getCardIndexesAmongCardsWithThe100LestSignificantAnswerRate(
            int numberOfCards, Set<Integer> omittedCardIndexes) {

        if (answerDataByStudyItemsContainer.numberOfStudiedStudyItems() < 100) {
            return null;
        } else {
            AnswerDataByStudyItem[] datasToSort = answerDataByStudyItemsContainer.toArray();
            Arrays.sort(datasToSort, new AnswerDataByStudyItemComparatorByNumberOfAnswers());

            Set<Integer> cardIndexesFromChoose = new HashSet<>();
            for (int i = 0; i < 100; i++) {
                cardIndexesFromChoose.add(datasToSort[i].getStudyItemIndex());
            }
            
            Set<Integer> outCardIndexes = new HashSet<>();
            for (int i = 0; i < numberOfCards; i++) {
                int cardIndex = getRandomCardIndex(cardIndexesFromChoose, omittedCardIndexes);
                outCardIndexes.add(cardIndex);
                omittedCardIndexes.add(cardIndex);
            }

            return outCardIndexes;
        }
    }

    public Set<Integer> getRandomCardIndexes(
            int numberOfCards, Set<Integer> omittedCardIndexes) {
        
        Set<Integer> outCardIndexes = new HashSet<>();

        Set<Integer> cardIndexesFromChoose = cardContainer.getCardIndexes();
        for (int i = 0; i < numberOfCards; i++) {
            int cardIndex = getRandomCardIndex(cardIndexesFromChoose, omittedCardIndexes);
            outCardIndexes.add(cardIndex);
            omittedCardIndexes.add(cardIndex);
        }

        return outCardIndexes;
    }

    private Set<Integer> getCardIndexesWithMinAnswerRateAndPlusSome (
            double minAnswerRate, int plusNumberOfCards) {

        Set<Integer> cardIndexes = new HashSet<>();

        for (int index : answerDataByStudyItemsContainer.getTestedStudyItemIndexes()) {

            AnswerDataByStudyItem answerDataByStudyItem
                    = answerDataByStudyItemsContainer.getAnswerDataByStudyItemByIndex(index);

            if (minAnswerRate <= answerDataByStudyItem.countRightAnswerRate()) {
                cardIndexes.add(answerDataByStudyItem.getStudyItemIndex());
            }
        }

        int plusNumberOfCardsAdded = 0;
        int i = 0;
        while (i < cardContainer.numberOfCards() && plusNumberOfCardsAdded < plusNumberOfCards) {
            Card card = cardContainer.getCardByOrder(i);
            if (!cardIndexes.contains(card.index)) {
                cardIndexes.add(card.index);
                plusNumberOfCardsAdded++;
            }
            i++;
        }

        return cardIndexes;
    }

    public Set<Integer> getCardIndexes(StudyStrategyHandler studyStrategyDataHandler) {
        Set<Integer> cardsToTestIndexes = new HashSet<>();
        Set<Integer> omittedCardIndexes = new HashSet<>();

        if (studyStrategyDataHandler.studyingGradually) {
            omittedCardIndexes = cardContainer.getCardIndexes();
            Set<Integer> cardIndexesToTest = getCardIndexesWithMinAnswerRateAndPlusSome(0.5, 100);
            omittedCardIndexes.removeAll(cardIndexesToTest);
            
            logger.debug("number of cards from choose: " + cardIndexesToTest.size());
        }

        Set<Integer> indexesToAdd;

        logger.debug("start evaluate formerly questioned card indexes");
        
        indexesToAdd = getFormerlyQuestionedCardIndexes(
                studyStrategyDataHandler.numberOfLatestQuestionedCards, omittedCardIndexes);
        cardsToTestIndexes.addAll(indexesToAdd);

        logger.debug("card indexes: " + indexesToAdd);

        ////////////////////////////////////////////////////
        
        logger.debug("start evaluate random hardest card indexes");
        indexesToAdd = getRandomHardestCardIndexes(
                0.2, studyStrategyDataHandler.numberOfCardsFromTheLeastKnown20Percent, omittedCardIndexes);
        cardsToTestIndexes.addAll(indexesToAdd);
        
        logger.debug("card indexes: " + indexesToAdd);

        ////////////////////////////////////////////////////
        
        logger.debug("start evaluate random hardest card indexes 2");
        
        indexesToAdd = getRandomHardestCardIndex2(
                100, studyStrategyDataHandler.numberOfCardsFromTheLeastKnown100, omittedCardIndexes);
        cardsToTestIndexes.addAll(indexesToAdd);

        logger.debug("card indexes: " + indexesToAdd);
        
        ////////////////////////////////////////////////////
        
        logger.debug("start evaluate card indexes from the 100 lest significant answer rate");
        
        indexesToAdd = getCardIndexesAmongCardsWithThe100LestSignificantAnswerRate(
                studyStrategyDataHandler.numberOfCardsAmongTheLeastSignificantAr, omittedCardIndexes);
        cardsToTestIndexes.addAll(indexesToAdd);

        logger.debug("card indexes: " + indexesToAdd);
        
        ////////////////////////////////////////////////////

        logger.debug("start evaluate random  card indexes");
        
        indexesToAdd = getRandomCardIndexes(
                studyStrategyDataHandler.numberOfRandomCards, omittedCardIndexes);
        cardsToTestIndexes.addAll(indexesToAdd);

        logger.debug("card indexes: " + indexesToAdd);

        return cardsToTestIndexes;
    }

}
