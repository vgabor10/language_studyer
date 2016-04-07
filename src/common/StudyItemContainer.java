package common;

import java.util.*;
import java.io.File;
import java.io.FileWriter;
import java.io.FileReader;
import java.io.BufferedWriter;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.FileNotFoundException;
import java.lang.Math;
import java.text.DecimalFormat;

public class StudyItemContainer {

	private Vector<StudyItem> data = new Vector<StudyItem>();

	private Map<Integer,Integer> studyItemIndexToOrderIndex = new HashMap<Integer,Integer>();

	public StudyItemContainer() {
	}

	public int numberOfStudyItems() {
		return data.size();
	}

	public StudyItem getStudyItemByOrder(int orderIndex) {
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

	public void removeStudyItemWithOrderIndex(int orderIndex) {
		data.remove(orderIndex);
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
