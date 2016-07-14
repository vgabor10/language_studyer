package disc_operation_handlers;

import dictionary.Card;
import dictionary.CardContainer;
import grammar_book.Example;
import grammar_book.GrammarAnswerData;
import grammar_book.GrammarAnswerDataContainer;
import grammar_book.GrammarBook;
import grammar_book.GrammarItem;
import grammar_book.GrammarItemTitle;
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
    
    private LanguageFilesDataHendler languageFilesDataHendler = new LanguageFilesDataHendler();
    
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
    public void setGrammarAnswerDataContainer(GrammarAnswerDataContainer a) {
        grammarAnswerDataContainer = a;
    }
    public void setLanguageFilesDataHendler(LanguageFilesDataHendler a) {
        languageFilesDataHendler = a;
    }
    
    public void loadCardContainerFromDisc() {
        String filePath = languageFilesDataHendler.getStudiedLanguageCardDataPath();
        
        BufferedReader br;
        String strLine;
        try {
            br = new BufferedReader( new FileReader(filePath));
            while( (strLine = br.readLine()) != null){
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
            System.err.println("Unable to find the file: fileName");
        } catch (IOException e) {
            System.err.println("Unable to read the file: fileName");
        }
    }
     
    public void loadAnswerDataFromDisc() {
        String filePath = languageFilesDataHendler.getStudiedLanguageAnswerDataPath();
        String strLine;
        try {
            BufferedReader br = new BufferedReader( new FileReader(filePath));
            while( (strLine = br.readLine()) != null){
                AnswerData answerData = new AnswerData();
                answerData.setDataFromString(strLine);
                answerDataContainer.addAnswerData(answerData);
            }
        } catch (FileNotFoundException e) {
            System.err.println("Unable to find the file: fileName");
        } catch (IOException e) {
            System.err.println("Unable to read the file: fileName");
        }
    }
    
    public void loadGrammarBookFromDisc() {
        String filePath = languageFilesDataHendler.getStudiedLanguageGrammarBookPath();
        
        String strLine;
        String str;

        try {
            BufferedReader br = new BufferedReader( new FileReader(filePath));

            strLine = br.readLine();

            while(!strLine.equals("\\begin{document}")) {
                if (grammarBook.preambulum.length() == 0) {
                    grammarBook.preambulum = strLine;
                }
                else {
                    grammarBook.preambulum = grammarBook.preambulum + "\n" + strLine;
                }
                strLine = br.readLine();
            }

            GrammarItem grammarItem = new GrammarItem();;

            while(strLine != null) {

                if (strLine.startsWith("GrammarItemIndex")) {
                    grammarItem.index = Integer.parseInt(strLine.substring(strLine.indexOf("=") + 2));
                }

                if (strLine.startsWith("\\" + "section")) {
                    if (!grammarItem.isEmptyExcludingTitle()) {
                        if (10 <= grammarItem.numberOfExamples()) {
                            grammarBook.addGrammarItem(grammarItem);
                        }
                        grammarItem = new GrammarItem();
                    }

                    String s = strLine.substring(9);
                    String section = s.substring(0, s.indexOf("}"));

                    grammarItem.title.deleteCategoriesFromDebth(0);
                    grammarItem.title.setSection(section);
                }

                if (strLine.startsWith("\\" + "subsection")) {
                    if (!grammarItem.isEmptyExcludingTitle()) {
                        if (10 <= grammarItem.numberOfExamples()) {
                            grammarBook.addGrammarItem(grammarItem);
                        }

                        GrammarItemTitle title = new GrammarItemTitle(grammarItem.title);
                        grammarItem = new GrammarItem();
                        grammarItem.title = title;
                    }

                    String s = strLine.substring(12);
                    String subsection = s.substring(0, s.indexOf("}"));

                    grammarItem.title.deleteCategoriesFromDebth(1);
                    grammarItem.title.setSubsection(subsection);
                }

                if (strLine.startsWith("\\" + "subsubsection")) {
                    if (!grammarItem.isEmptyExcludingTitle()) {
                        if (10 <= grammarItem.numberOfExamples()) {
                            grammarBook.addGrammarItem(grammarItem);
                        }

                        GrammarItemTitle title = new GrammarItemTitle(grammarItem.title);
                        grammarItem = new GrammarItem();
                        grammarItem.title = title;
                    }

                    String s = strLine.substring(15);
                    String subsubsection = s.substring(0, s.indexOf("}"));

                    grammarItem.title.deleteCategoriesFromDebth(2);
                    grammarItem.title.setSubsubsection(subsubsection);
                }

                if (strLine.startsWith("\\" + "paragraph")) {
                    if (!grammarItem.isEmptyExcludingTitle()) {
                        if (10 <= grammarItem.numberOfExamples()) {
                            grammarBook.addGrammarItem(grammarItem);
                        }

                        GrammarItemTitle title = new GrammarItemTitle(grammarItem.title);
                        grammarItem = new GrammarItem();
                        grammarItem.title = title;
                    }

                    String s = strLine.substring(11);
                    String subsubsection = s.substring(0, s.indexOf("}"));

                    grammarItem.title.deleteCategoriesFromDebth(3);
                    grammarItem.title.setParagraph(subsubsection);
                }

                if (strLine.startsWith("\\" + "end{document}")) {
                    if (!grammarItem.isEmptyExcludingTitle()) {
                        if (10 <= grammarItem.numberOfExamples()) {
                            grammarBook.addGrammarItem(grammarItem);
                        }
                    }
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
                            = grammarItem.description.substring(0, grammarItem.description.length()-1);
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

        } catch (FileNotFoundException e) {
            System.err.println("Unable to find the file: fileName");
        } catch (IOException e) {
            System.err.println("Unable to read the file: fileName");
        }   
    }
    
    public void loadGrammarAnswerDataFromDisc() {
        String filePath = languageFilesDataHendler.getStudiedLanguageGrammarAnswerDataPath();
        
        String strLine;
        try {
            BufferedReader br = new BufferedReader( new FileReader(filePath));
            while( (strLine = br.readLine()) != null){
                GrammarAnswerData grammarAnswerData = new GrammarAnswerData();
                grammarAnswerData.setDataFromString(strLine);
                grammarAnswerDataContainer.addAnswerData(grammarAnswerData);
            }
        } catch (FileNotFoundException e) {
            System.err.println("Unable to find the file: fileName");
        } catch (IOException e) {
            System.err.println("Unable to read the file: fileName");
        }
    }
    
    public void loadAllLanguageDataFromDisc() {
        loadCardContainerFromDisc();
        loadAnswerDataFromDisc();
        loadGrammarBookFromDisc();
        loadGrammarAnswerDataFromDisc();
    }
    
}
