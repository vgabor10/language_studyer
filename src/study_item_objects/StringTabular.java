package study_item_objects;

import common.Logger;
import java.util.*;

public class StringTabular {	//TODO: take to terminal interface package

	private Vector<String> tabularByRows = new Vector<String>();
	private Vector<Integer> widestItemLengthByColumn =  new Vector<Integer>();

	private Logger logger = new Logger();

	private String separatorString = "";

	public StringTabular() {
	}

	public void setSeparatorString(String ss) {
		separatorString = ss;
	}

	public int numberOfRows() {
		return tabularByRows.size();
	}

	public int numberOfColumns() {
		return widestItemLengthByColumn.size();
	}

	public void addRowInString(String row) {
		tabularByRows.add(row);

		String[] splittedRow = row.split(separatorString);

		for (int i=0; i<splittedRow.length; i++) {
			if (widestItemLengthByColumn.size() < i+1) {
				widestItemLengthByColumn.add(0);
			}

			if (widestItemLengthByColumn.get(i) < splittedRow[i].length()) {
				widestItemLengthByColumn.remove(i);
				widestItemLengthByColumn.insertElementAt(splittedRow[i].length(), i);
			}
		}
	}

	public String expandStringWithSpaces(String s, int lengthOfOutString) {
		String out = s;
		for (int i=1; i<=lengthOfOutString-s.length(); i++) {
			out = out + " ";
		}
		return out;
	}

	public String getNiceTabularRowInString(int rowIndex) {
		String row = tabularByRows.get(rowIndex);
		String[] splittedRow = row.split(separatorString);

		String out = expandStringWithSpaces(splittedRow[0], widestItemLengthByColumn.get(0));
		for (int i=1; i<numberOfColumns(); i++) {
			out = out + " | " + expandStringWithSpaces(splittedRow[i], widestItemLengthByColumn.get(i));
		}

		return out;
	}

	/*public String getNiceTabularInString() {
	}*/
}
