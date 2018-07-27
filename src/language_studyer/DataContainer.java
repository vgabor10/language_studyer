package language_studyer;

public class DataContainer {
    
    protected StudyItemContainer studyItemContainer;
    private AnswerDataContainer answerDataContainer = new AnswerDataContainer();
    private CategoryContainer categoryContainer = new CategoryContainer();
    private StudyStrategy studyStrategy = new StudyStrategy();
    
    public StudiedDataContainer studiedDataContainer = new StudiedDataContainer();
    
    protected StudyItemContainer getStudyItemContainer() {
        return this.studyItemContainer;
    }
    
    public void setAnswerDataContainer(AnswerDataContainer adc) {
        this.answerDataContainer = adc;
    }
    
    public AnswerDataContainer getAnswerDataContainer() {
        return this.answerDataContainer;
    }
    
    public void setCategoryContainer(CategoryContainer cc) {
        this.categoryContainer = cc;
    }
    
    public CategoryContainer getCategoryContainer() {
        return this.categoryContainer;
    }
    
    public void setStudyStrategy(StudyStrategy ss) {
        this.studyStrategy = ss;
    }
    
    public StudyStrategy getStudyStrategy() {
        return this.studyStrategy;
    }
    
    public void updateStudiedData() {   //TODO: it can be forgotten to call, make safer
        studiedDataContainer.clear();
        
        for (int i=0; i<studyItemContainer.numberOfStudyItems(); i++) {
            StudyItem studyItem = studyItemContainer.getStudyItemByOrder(i);
            if (studyItem.containsAnyCategoryIndex(
                    studyStrategy.cardCategoryRestrictions)) {
                studiedDataContainer.studiedCardIndexes.add(studyItem.index);
            }
        }
        
        for (int i=0; i<answerDataContainer.numberOfAnswers(); i++) {
            AnswerData answerData = answerDataContainer.getAnswerData(i);
            if (studiedDataContainer.studiedCardIndexes.contains(answerData.index)) {
                studiedDataContainer.studiedAnswerDataContainer.addAnswerData(answerData);
                studiedDataContainer.studiedAnswerDataByStudyItemContainer.addAnswerData(answerData);
            }
        }        
    }
    
    public void clear() {
        studyItemContainer.clear(); //  TODO: it can occure, that studyItemContainer does not exist 
        answerDataContainer.clear();
        categoryContainer.clear();
        studyStrategy.clear();
        studiedDataContainer.clear();
    }
}
