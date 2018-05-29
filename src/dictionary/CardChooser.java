package dictionary;

import language_studyer.answer_data_by_study_item_comparators.AnswerDataByStudyItemComparatorByNumberOfAnswers;
import language_studyer.AnswerDataByStudyItemContainer;
import language_studyer.AnswerDataByStudyItem;
import common.Logger;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.HashSet;
import java.util.List;
import java.util.Random;
import java.util.Set;
import language_studyer.answer_data_by_study_item_comparators.AnswerDataByStudyItemComparatorByLastStudyDate;
import language_studyer.answer_data_by_study_item_comparators.AnswerDataByStudyItemComparatorByRateOfRightAnswers;

public class CardChooser {
    
    private DataContainer dataContainer;
    private Set<Integer> cardIndexesFromChoose;
    private StudyStrategy studyStrategy;
    
    private AnswerDataByStudyItemContainer answerDataByStudyItemsContainer;
    
    private final Random randomGenerator = new Random();
    private final Logger logger = new Logger();
    
    public void setData(DataContainer dataContainer) {
        this.dataContainer = dataContainer;
        this.studyStrategy = dataContainer.studyStrategy;
        
        cardIndexesFromChoose = dataContainer.auxiliaryDataContainer.studiedCardIndexes;
        answerDataByStudyItemsContainer = dataContainer.auxiliaryDataContainer.studiedAnswerDataByStudyItemContainer;
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

    private Set<Integer> getFormerlyQuestionedCardIndexes(
        int numberOfCards, Set<Integer> omittedCardIndexes) {
        
        Set<Integer> notQuestionedCardIndexes = new HashSet<>(cardIndexesFromChoose);
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

    private Set<Integer> getRandomHardestCardIndexes(
            double hardestWordRate, int numberOfCards, Set<Integer> omittedCardIndexes) {

        AnswerDataByStudyItem[] datasToSort = answerDataByStudyItemsContainer.toArray();
        Arrays.sort(datasToSort,
                Collections.reverseOrder(new AnswerDataByStudyItemComparatorByRateOfRightAnswers()));

        Set<Integer> hardestCardIndexes = new HashSet<>();
        for (int i = 0; i < Math.floor((double) answerDataByStudyItemsContainer.numberOfStudiedStudyItems() * hardestWordRate); i++) {
            hardestCardIndexes.add(datasToSort[i].getStudyItemIndex());
        }

        Set<Integer> outCardIndexes = new HashSet<>();
        for (int i = 0; i < numberOfCards; i++) {
            int cardIndex = getRandomCardIndex(hardestCardIndexes, omittedCardIndexes);
            outCardIndexes.add(cardIndex);
            omittedCardIndexes.add(cardIndex);
        }

        return outCardIndexes;
    }

    private Set<Integer> getRandomHardestCardIndex2(
            int numberOfHardestCards, int numberOfCardsGet, Set<Integer> omittedCardIndexes) {

        Set<Integer> outCardIndexes = new HashSet<>();
        if (numberOfCardsGet == 0) return outCardIndexes;
        
        AnswerDataByStudyItem[] datasToSort = answerDataByStudyItemsContainer.toArray();
        Arrays.sort(datasToSort,
                Collections.reverseOrder(new AnswerDataByStudyItemComparatorByRateOfRightAnswers()));

        Set<Integer> hardestCardIndexes = new HashSet<>();
        for (int i = 0; i < numberOfHardestCards; i++) {
            hardestCardIndexes.add(datasToSort[i].getStudyItemIndex());
        }

        for (int i = 0; i < numberOfCardsGet; i++) {
            int cardIndex = getRandomCardIndex(hardestCardIndexes, omittedCardIndexes);
            outCardIndexes.add(cardIndex);
            omittedCardIndexes.add(cardIndex);
        }

        return outCardIndexes;
    }

    private Set<Integer> getCardIndexesWithLestSignificantAnswerRate(
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

    private Set<Integer> getCardIndexesAmongCardsWithThe100LestSignificantAnswerRate(
            int numberOfCards, Set<Integer> omittedCardIndexes) {

        Set<Integer> outCardIndexes = new HashSet<>();

        if (answerDataByStudyItemsContainer.numberOfStudiedStudyItems() < 100) {
            return outCardIndexes;
        }

        AnswerDataByStudyItem[] datasToSort = answerDataByStudyItemsContainer.toArray();
        Arrays.sort(datasToSort, new AnswerDataByStudyItemComparatorByNumberOfAnswers());

        Set<Integer> cardIndexesWithFewAnswers = new HashSet<>();
        for (int i = 0; i < 100; i++) {
            cardIndexesWithFewAnswers.add(datasToSort[i].getStudyItemIndex());
        }

        for (int i = 0; i < numberOfCards; i++) {
            int cardIndex = getRandomCardIndex(cardIndexesWithFewAnswers, omittedCardIndexes);
            outCardIndexes.add(cardIndex);
            omittedCardIndexes.add(cardIndex);
        }

        return outCardIndexes;
    }

    private Set<Integer> getRandomCardIndexes(
            int numberOfCards, Set<Integer> omittedCardIndexes) {
        
        Set<Integer> outCardIndexes = new HashSet<>();

        for (int i = 0; i < numberOfCards; i++) {
            int cardIndex = getRandomCardIndex(this.cardIndexesFromChoose, omittedCardIndexes);
            outCardIndexes.add(cardIndex);
            omittedCardIndexes.add(cardIndex);
        }

        return outCardIndexes;
    }

    /*private Set<Integer> getCardIndexesWithMinAnswerRateAndPlusSome (
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
        while (i < this.cardIndexesFromChoose.size() && plusNumberOfCardsAdded < plusNumberOfCards) {
            Card card = cardContainer.getCardByOrder(i);
            if (!cardIndexes.contains(card.index)) {
                cardIndexes.add(card.index);
                plusNumberOfCardsAdded++;
            }
            i++;
        }

        return cardIndexes;
    }*/

    public List<Integer> getCardIndexes() {
        dataContainer.fillAuxiliaryDataContainer();
        
        List<Integer> cardsToTestIndexes = new ArrayList<>();
        Set<Integer> omittedCardIndexes = new HashSet<>();

        /*if (studyStrategy.studyingGradually) {
            omittedCardIndexes = new HashSet<>(cardIndexesFromChoose);
            Set<Integer> cardIndexesToTest = getCardIndexesWithMinAnswerRateAndPlusSome(0.5, 100);
            omittedCardIndexes.removeAll(cardIndexesToTest);
            
            logger.debug("number of cards from choose: " + cardIndexesToTest.size());
        }*/

        Set<Integer> indexesToAdd;

        logger.debug("start evaluate formerly questioned card indexes");
        
        indexesToAdd = getFormerlyQuestionedCardIndexes(studyStrategy.numberOfLatestQuestionedCards, omittedCardIndexes);
        cardsToTestIndexes.addAll(indexesToAdd);

        logger.debug("card indexes: " + indexesToAdd);

        ////////////////////////////////////////////////////
        
        logger.debug("start evaluate random hardest card indexes");
        indexesToAdd = getRandomHardestCardIndexes(0.4, studyStrategy.numberOfCardsFromTheLeastKnown20Percent, omittedCardIndexes);
        cardsToTestIndexes.addAll(indexesToAdd);
        
        logger.debug("card indexes: " + indexesToAdd);

        ////////////////////////////////////////////////////
        
        logger.debug("start evaluate random hardest card indexes 2");
        
        indexesToAdd = getRandomHardestCardIndex2(100, studyStrategy.numberOfCardsFromTheLeastKnown100, omittedCardIndexes);
        cardsToTestIndexes.addAll(indexesToAdd);

        logger.debug("card indexes: " + indexesToAdd);
        
        ////////////////////////////////////////////////////
        
        logger.debug("start evaluate card indexes from the 100 lest significant answer rate");
        
        indexesToAdd = getCardIndexesAmongCardsWithThe100LestSignificantAnswerRate(studyStrategy.numberOfCardsAmongTheLeastSignificantAr, omittedCardIndexes);
        cardsToTestIndexes.addAll(indexesToAdd);

        logger.debug("card indexes: " + indexesToAdd);
        
        ////////////////////////////////////////////////////

        logger.debug("start evaluate random  card indexes");
        
        indexesToAdd = getRandomCardIndexes(studyStrategy.numberOfRandomCards, omittedCardIndexes);
        cardsToTestIndexes.addAll(indexesToAdd);

        logger.debug("card indexes: " + indexesToAdd);

        java.util.Collections.shuffle(cardsToTestIndexes);
        
        return cardsToTestIndexes;
    }

}
