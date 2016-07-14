package study_item_objects;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;
import java.util.Vector;

public class StudyItemContainer {

    private final Vector<StudyItem> data;
    private final Map<Integer,Integer> studyItemIndexToOrderIndex;

    public StudyItemContainer() {
        data = new Vector<>();
        studyItemIndexToOrderIndex = new HashMap<>();
    }

    public int numberOfStudyItems() {
        return data.size();
    }

    public StudyItem getStudyItemByOrder(int orderIndex) {	//TODO: make no accessable from CardContainer
        return data.get(orderIndex);
    }

    public StudyItem getStudyItemByIndex(int index) {
            return data.get(studyItemIndexToOrderIndex.get(index));
    }

    public Set<Integer> getStudyItemIndexes() {
        Set<Integer> studyItemIndexes = new HashSet<>();

        for (int i=0; i<data.size(); i++) {
            studyItemIndexes.add(data.get(i).index);
        }
        return studyItemIndexes;
    }

    public void addStudyItem(StudyItem si) {
        data.add(si);
        studyItemIndexToOrderIndex.put(si.index, numberOfStudyItems()-1);
    }

    private void refreshMapVariable() {
        studyItemIndexToOrderIndex.clear();
        for (int i=0; i<data.size(); i++) {
            studyItemIndexToOrderIndex.put(data.get(i).index, i);
        }
    }

    public void removeStudyItemWithOrderIndex(int orderIndex) {
        data.remove(orderIndex);
        refreshMapVariable();
    }

    public void removeStudyItemWithIndex(int studyItemIndex) {
        removeStudyItemWithOrderIndex(studyItemIndexToOrderIndex.get(studyItemIndex));
    }

    public void clear() {
        data.clear();
    }

    public void toScreen() {
        for(int i=0; i<data.size(); i++){
            System.out.println(data.get(i).toString());
        }
    }
}
