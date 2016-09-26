package dictionary;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

public class ExampleSentenceAssigner {
    
    public DictionaryDataContainer dictionaryDataContainer ;
    
    public List<String> exampeSentences = new ArrayList<>();
    public Map<Integer, Set<String>> exampeSentencesByCardIndexes = new HashMap<>();

    public ArrayList<Integer> getCardIndexesWithLessExampleSentencesThen(
            int maxNumberOfExampleSentences) {
        
        ArrayList<Integer> cardIndexes = new ArrayList<>();
        
        for (int i=0; i<dictionaryDataContainer.cardContainer.numberOfCards(); i++) {
            Card card = dictionaryDataContainer.cardContainer.getCardByOrder(i);
            if (card.exampleSentences.size() < maxNumberOfExampleSentences) {
                cardIndexes.add(card.index);
            }
        }
        
        System.out.println("number of cards with too few example sentences: " + cardIndexes.size());
        return cardIndexes;
    }
    
    public void fillExampeSentencesFromDDC() {
        CardContainer cardContainer = dictionaryDataContainer.cardContainer;
        for (int i=0; i<cardContainer.numberOfCards(); i++) {
            Card card = cardContainer.getCardByOrder(i);
            
            for (String exampleSentence : card.exampleSentences) {
                exampeSentences.add(exampleSentence);
            }
        }
    }
    
    public String getSuggestion(int cardIndex) {
        Card card = dictionaryDataContainer.cardContainer.getCardByIndex(cardIndex);
        
        String cardTermComparition = card.term;
        cardTermComparition = cardTermComparition.toLowerCase();
        if (cardTermComparition.startsWith("r ") || cardTermComparition.startsWith("e ") || cardTermComparition.startsWith("s ")) {
            cardTermComparition = cardTermComparition.substring(2);
        }

        if (cardTermComparition.startsWith("h. ") || cardTermComparition.startsWith("i. ")) {
            cardTermComparition = cardTermComparition.substring(3);
        }
        
        if (cardTermComparition.endsWith("en")) {
            cardTermComparition 
                    = cardTermComparition.substring(0,cardTermComparition.length()-2);
        }

        Collections.shuffle(exampeSentences);
        for (String exampleSentence : exampeSentences) {
            String exampleSentenceForComparition = exampleSentence.toLowerCase();

            if (exampleSentenceForComparition.contains(cardTermComparition)
                    && !card.exampleSentences.contains(exampleSentence)) {
                return exampleSentence;
            }
        }
        
        return "";
    }
    
    /*public void loadExampleSentencesFromFile(String filePath) {
        try {
            exampeSentences.clear();

            BufferedReader br = new BufferedReader(new FileReader(filePath));
            String strLine;
            while ((strLine = br.readLine()) != null) {
                exampeSentences.add(strLine);
            }
        } catch (FileNotFoundException e) {
            System.err.println("unable to find example_sentences file");
        } catch (IOException e) {
            System.err.println("exception in loadCardContainer function");
        }
    }*/

    /*public void saveCardContainerDataToFile() {
        String filePath = "../data_to_integrate/example_sentences.tmp";
        File oldFile;
        oldFile = new File(filePath);
        oldFile.delete();

        try {
            FileWriter fw = new FileWriter(filePath, false);	//the true will append the new data
            for (int cardIndex : exampeSentencesByCardIndexes.keySet()) {

                for (String exampleSentence : exampeSentencesByCardIndexes.get(cardIndex)) {
                    fw.write(cardIndex + "\t" + exampleSentence + "\n");
                }
            }
            fw.close();
        } catch (IOException ioe) {
            System.err.println("IOException: " + ioe.getMessage());
        }
    }*/

    /*public void assignCardsAndExampleSentences1() {
        loadExampleSentencesFromFile("../data_to_integrate/example_sentences.tmp");

        exampeSentencesByCardIndexes.clear();
        int numberOfAssignations = 0;

        for (int i = 0; i < cardContainer.numberOfCards(); i++) {
            Card card = cardContainer.getCardByOrder(i);

            String cardTerm = cardContainer.getCardByOrder(i).term;
            cardTerm = cardTerm.toLowerCase();
            if (cardTerm.startsWith("r ") || cardTerm.startsWith("e ") || cardTerm.startsWith("s ")) {
                cardTerm = cardTerm.substring(2);
            }

            if (cardTerm.startsWith("h. ") || cardTerm.startsWith("i. ")) {
                cardTerm = cardTerm.substring(3);
            }

            for (String exampleSentence : exampeSentences) {
                String exampleSentenceForComparition = exampleSentence;
                exampleSentenceForComparition = exampleSentenceForComparition.toLowerCase();

                if (exampleSentenceForComparition.startsWith(cardTerm + " ")
                        || exampleSentenceForComparition.contains(" " + cardTerm + " ")
                        || exampleSentenceForComparition.endsWith(" " + cardTerm + ".")) {

                    if (!exampeSentencesByCardIndexes.containsKey(card.index)) {
                        exampeSentencesByCardIndexes.put(card.index, new HashSet<String>());
                        exampeSentencesByCardIndexes.get(card.index).add(exampleSentence);
                        numberOfAssignations++;
                    } else if (exampeSentencesByCardIndexes.get(card.index).size() < 10) {

                        if (exampeSentencesByCardIndexes.get(card.index).contains(exampleSentence)) {
                            System.out.println(exampleSentence);
                        }

                        exampeSentencesByCardIndexes.get(card.index).add(exampleSentence);
                        numberOfAssignations++;
                    }
                }
            }

        }

        System.out.println("number of assignations: " + numberOfAssignations);
        System.out.println("number of cards with at least one example: " + exampeSentencesByCardIndexes.keySet().size());
        saveCardContainerDataToFile();
    }*/
}
