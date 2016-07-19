package disc_operation_handlers;

import dictionary.Card;
import dictionary.CardContainer;
import grammar_book.Example;
import grammar_book.GrammarAnswerData;
import grammar_book.GrammarAnswerDataContainer;
import grammar_book.GrammarBook;
import grammar_book.GrammarItem;
import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import study_item_objects.AnswerData;
import study_item_objects.AnswerDataContainer;

public class LanguageDataLoader {

    private CardContainer cardContainer;
    private AnswerDataContainer answerDataContainer;

    private GrammarBook grammarBook;
    private GrammarAnswerDataContainer grammarAnswerDataContainer;

    private LanguageFilesDataHandler languageFilesDataHendler;

    public void loadLanguageDataWithIndex(int languageIndex) {
        languageFilesDataHendler.setStudyedLanguageIndex(languageIndex);
        this.loadAllLanguageDataFromDisc();
    }

    public void setCardContainer(CardContainer cc) {
        cardContainer = cc;
    }

    public void setAnswerDataContainer(AnswerDataContainer a) {
        answerDataContainer = a;
    }

    public void setGrammarBook(GrammarBook a) {
        grammarBook = a;
    }
    
    public void setLanguageFilesDataHandler(LanguageFilesDataHandler a) {
        languageFilesDataHendler = a;
    }

    public void setGrammarAnswerDataContainer(GrammarAnswerDataContainer a) {
        grammarAnswerDataContainer = a;
    }

    public void loadCardContainerFromDisc() {
        try {        
            cardContainer.clear();
            
            String filePath = languageFilesDataHendler.getStudiedLanguageCardDataPath();
            BufferedReader br = new BufferedReader(new FileReader(filePath));
            String strLine;
            while ((strLine = br.readLine()) != null) {
                String[] cardVariables = strLine.split("\t");

                Card card = new Card();
                card.index = Integer.parseInt(cardVariables[0]);
                card.term = cardVariables[1];
                card.definition = cardVariables[2];
                if (3 < cardVariables.length) {
                    card.group = cardVariables[3];
                }

                cardContainer.addCard(card);
            }
        } catch (FileNotFoundException e) {
            System.err.println("unable to find card data file");
        } catch (IOException e) {
            System.err.println("exception in loadCardContainer function");
        }
    }

    public void loadAnswerDataFromDisc() {
        try {
            answerDataContainer.clear();
            
            String filePath = languageFilesDataHendler.getStudiedLanguageAnswerDataPath();
            String strLine;
            BufferedReader br = new BufferedReader(new FileReader(filePath));
            while ((strLine = br.readLine()) != null) {
                AnswerData answerData = new AnswerData();
                answerData.setDataFromString(strLine);
                answerDataContainer.addAnswerData(answerData);
            }
        } catch (FileNotFoundException e) {
            System.err.println("Unable to find answer data file");
        } catch (IOException e) {
            System.err.println("exception loadAnswerDataFromDisc function");
        }
    }

    public void loadGrammarBookFromDisc() {
        try {
            grammarBook.clear();
            
            String filePath = languageFilesDataHendler.getStudiedLanguageGrammarBookPath();
            BufferedReader br = new BufferedReader(new FileReader(filePath));
            String strLine = br.readLine();
            
            int grammarItemOrderIndex = 0;
            
            GrammarItem grammarItem = new GrammarItem();
            
            while (strLine != null) {
                
                if (strLine.startsWith("\\title{")) {
                    if (grammarItemOrderIndex != 0) {
                        grammarBook.addGrammarItem(grammarItem);
                        grammarItem = new GrammarItem();
                    }
                    grammarItemOrderIndex++;
                    
                    String titleString = strLine.substring(7, strLine.length() - 1);
                    
                    grammarItem.title.setTitleFromString(titleString);
                }
                
                if (strLine.startsWith("GrammarItemIndex")) {
                    grammarItem.index = Integer.parseInt(strLine.substring(strLine.indexOf("=") + 2));
                }

                if (strLine.startsWith("%")) {
                    grammarItem.comments = strLine;
                }

                if (strLine.equals("\\" + "begin{desc}")) {
                    strLine = br.readLine();

                    while (!strLine.equals("\\" + "end{desc}")) {
                        grammarItem.description = grammarItem.description + strLine + "\n";
                        strLine = br.readLine();
                    }

                    if (grammarItem.description.endsWith("\n")) {
                        grammarItem.description
                                = grammarItem.description.substring(0, grammarItem.description.length() - 1);
                    }
                }

                if (strLine.equals("\\" + "begin{exmp}")) {
                    strLine = br.readLine();

                    while (!strLine.equals("\\" + "end{exmp}")) {
                        String[] strings = strLine.split(" \\| ");
                        Example example = new Example();

                        example.index = Integer.parseInt(strings[0]);
                        example.hun = strings[2];
                        example.foreign = strings[1];

                        grammarItem.addExample(example);

                        strLine = br.readLine();
                    }
                }

                strLine = br.readLine();
            }
            
            grammarBook.addGrammarItem(grammarItem);
            
        } catch (FileNotFoundException e) {
            System.err.println("unable to find grammar book file");
        } catch (IOException e) {
            System.err.println("exception at loadGrammarBookFromDisc function");
        }
    }

    public void loadGrammarAnswerDataFromDisc() {
        try {
            grammarAnswerDataContainer.clear();
            
            String filePath = languageFilesDataHendler.getStudiedLanguageGrammarAnswerDataPath();
            String strLine;
            BufferedReader br = new BufferedReader(new FileReader(filePath));
            while ((strLine = br.readLine()) != null) {
                GrammarAnswerData grammarAnswerData = new GrammarAnswerData();
                grammarAnswerData.setDataFromString(strLine);
                grammarAnswerDataContainer.addAnswerData(grammarAnswerData);
            }
        } catch (FileNotFoundException e) {
            System.err.println("unable to find grammar answer data file");
        } catch (IOException e) {
            System.err.println("exception at loadGrammarAnswerDataFromDisc function");
        }
    }

    public void loadAllLanguageDataFromDisc() {
        loadCardContainerFromDisc();
        loadAnswerDataFromDisc();
        loadGrammarBookFromDisc();
        loadGrammarAnswerDataFromDisc();
    }

}
