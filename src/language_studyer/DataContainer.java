package language_studyer;

public class DataContainer {
    
    private StudyItemContainer studyItemContainer = new StudyItemContainer();
    private AnswerDataContainer answerDataContainer = new AnswerDataContainer();
    private CategoryContainer categoryContainer = new CategoryContainer();
    private StudyStrategy studyStrategy = new StudyStrategy();

    public AuxiliaryDataContainer auxiliaryDataContainer = new AuxiliaryDataContainer();
    
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
    
    public void fillAuxiliaryDataContainer() {
        auxiliaryDataContainer.clear();
        
        for (int i=0; i<studyItemContainer.numberOfStudyItems(); i++) {
            StudyItem studyItem = studyItemContainer.getStudyItemByOrder(i);
            if (studyItem.containsAnyCategoryIndex(
                    studyStrategy.cardCategoryRestrictions)) {
                auxiliaryDataContainer.studiedCardIndexes.add(studyItem.index);
            }
        }
        
        for (int i=0; i<answerDataContainer.numberOfAnswers(); i++) {
            AnswerData answerData = answerDataContainer.getAnswerData(i);
            if (auxiliaryDataContainer.studiedCardIndexes.contains(answerData.index)) {
                auxiliaryDataContainer.studiedAnswerDataContainer.addAnswerData(answerData);
                auxiliaryDataContainer.studiedAnswerDataByStudyItemContainer.addAnswerData(answerData);
            }
        }        
    }
    
    public void clear() {
        studyItemContainer.clear();
        answerDataContainer.clear();
        categoryContainer.clear();
        studyStrategy.clear();
        auxiliaryDataContainer.clear();
    }
}
