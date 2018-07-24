package language_studyer;

import common.Logger;
import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;

public class DataLoader {
    
    protected DataContainer dataContainer;
    
    private final Logger logger = new Logger();
    
    public void loadStudyStrategyDataFromDisc(String filePath) {
        try {
            BufferedReader br = new BufferedReader(new FileReader(filePath));
            
            StudyStrategy studyStrategy = dataContainer.getStudyStrategy();
            
            String line;
            while ((line = br.readLine()) != null) {

                if (line.startsWith("numberOfRandomCards: ")) {
                    String s = line.substring(line.lastIndexOf(":") + 2);
                    //System.out.println(s);
                    studyStrategy.numberOfRandomItems = Integer.parseInt(s);
                }
                
                if (line.startsWith("numberOfCardsFromTheLeastKnown20Percent: ")) {
                    String s = line.substring(line.lastIndexOf(":") + 2);
                    //System.out.println(s);
                    studyStrategy.numberOfItemsFromTheLeastKnown20Percent = Integer.parseInt(s);
                }
                
                if (line.startsWith("numberOfCardsFromTheLeastKnown100: ")) {
                    String s = line.substring(line.lastIndexOf(":") + 2);
                    //System.out.println(s);
                    studyStrategy.numberOfItemsFromTheLeastKnown100 = Integer.parseInt(s);
                }

                if (line.startsWith("numberOfCardsWithLeastSignificantAr: ")) {
                    String s = line.substring(line.lastIndexOf(":") + 2);
                    //System.out.println(s);
                    studyStrategy.numberOfItemsAmongTheLeastSignificantAr = Integer.parseInt(s);
                }

                if (line.startsWith("numberOfLatestQuestionedCards: ")) {
                    String s = line.substring(line.lastIndexOf(":") + 2);
                    //System.out.println(s);
                    studyStrategy.numberOfLatestQuestionedItems = Integer.parseInt(s);
                }
                
                if (line.startsWith("studyingGradually: ")) {
                    String s = line.substring(line.lastIndexOf(":") + 2);
                    //System.out.println(s);

                    if (s.equals("false")) {
                        studyStrategy.studyingGradually = false;
                    }
                    
                    if (s.equals("true")) {
                        studyStrategy.studyingGradually = true;
                    }
                }
                
                 if (line.startsWith("cardCategoryRestrictions: ")) {
                     String s = line.substring(line.lastIndexOf(":") + 2);
                     String[] stringCategoryIndexes = s.split(" ");
                     
                     for (String stringCategoryIndes : stringCategoryIndexes) {
                         int categoryIndex = Integer.parseInt(stringCategoryIndes);
                         studyStrategy.cardCategoryRestrictions.add(categoryIndex);
                     }
                }

            }
            
            //studyStrategyIndex = Integer.parseInt(br.readLine());
        } catch (FileNotFoundException e) {
            System.err.println("unable to find the file: " + filePath);
        } catch (IOException e) {
            System.err.println("unable to read the file: fileName");
        }   
    }
    
    public void loadStudyItemIndexesAndCategoryIndexes(String filePath) {
        try {
            BufferedReader br = new BufferedReader(new FileReader(filePath));
            String strLine;
            while ((strLine = br.readLine()) != null) {
                String[] splittedRow = strLine.split("\t");
                int grammarItemIndex = Integer.parseInt(splittedRow[0]);

                for (int i=1; i<splittedRow.length; i++) {
                    int categoryIndex = Integer.parseInt(splittedRow[i]);
                    dataContainer.getStudyItemContainer()
                            .getStudyItemByIndex(grammarItemIndex).categoryIndexes.add(categoryIndex);
                }
            }
        } catch (FileNotFoundException e) {
            System.err.println("unable to find card categories file");
        } catch (IOException e) {
            System.err.println("exception in loadCardCategories function");
        }
    }
    
    public void loadCategoryIndexesAndCategoryNames(String filePath) {
        try {
            BufferedReader br = new BufferedReader(new FileReader(filePath));
            String strLine;
            while ((strLine = br.readLine()) != null) {
                String[] splittedRow = strLine.split("\t");
                int categoryIndex = Integer.parseInt(splittedRow[0]);
                String categoryName = splittedRow[1];

                Category category = new Category();
                category.index = categoryIndex;
                category.name = categoryName;
                
                 logger.debug("category loaded: " + category.toString());

                dataContainer.getCategoryContainer().add(category);
            }
        } catch (FileNotFoundException e) {
            System.err.println("unable to find file: " + filePath);
        } catch (IOException e) {
            System.err.println("exception in load file function");
        }
    }
    
}
