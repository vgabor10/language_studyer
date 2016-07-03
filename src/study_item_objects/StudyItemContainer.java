package common;

import java.util.*;
import java.io.IOException;
import java.io.FileNotFoundException;

public class StudyItemContainer {

	private Vector<StudyItem> data = new Vector<StudyItem>();

	private Map<Integer,Integer> studyItemIndexToOrderIndex = new HashMap<Integer,Integer>();

	public StudyItemContainer() {
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
		Set<Integer> studyItemIndexes = new HashSet<Integer>();

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
			System.out.println(data.elementAt(i).toString());
		}
	}
}
