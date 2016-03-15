package grammar_book;

public class Description {

	public String strData = "";

	private String strDataToReadableTabular(String inStr) {
		String[][] tabularData = new String[100][100];

		for (int i=0; i<100; i++) {
			for (int j=0; j<100; j++) {
				tabularData[i][j] = "|||";
			}
		}

		inStr = inStr.replace("\\" + "begin{tabular}", "");
		inStr = inStr.replace("\\" + "end{tabular}", "");
		inStr = inStr.replace("\\" + "\\", "|");
		inStr = inStr.substring(inStr.indexOf("}") + 1);

		int column = 0;
		int row = 0;
		String s = "";
		for (int i=0; i<inStr.length(); i++) {
			if (inStr.charAt(i) == '&') {
				tabularData[row][column] = s.trim();
				s = "";
				column++;
			}
			else
			if (inStr.charAt(i) == '|') {
				tabularData[row][column] = s.trim();
				s = "";
				row++;
				column = 0;
			}
			else {
				if (inStr.charAt(i) != '\t') {
					s = s + inStr.charAt(i);
				}
			}
		}

		int numberOfColumns = 0;
		int numberOfRows = 0;
		while (!tabularData[numberOfRows][0].equals("|||")) numberOfRows++;
		while (!tabularData[0][numberOfColumns].equals("|||")) numberOfColumns++;

		/////////////////// forming tabular /////////////////

		for (int k=0; k<numberOfColumns;k++) {

		int maxLengthInARow = 0;
		for (int i=0; i<numberOfRows; i++) {
			if (tabularData[i][k].length() > maxLengthInARow) {
				maxLengthInARow = tabularData[i][k].length();
			}
		}

		for (int i=0; i<numberOfRows; i++) {
			for (int j=tabularData[i][k].length(); j<maxLengthInARow; j++) {
				tabularData[i][k] = tabularData[i][k] + " ";
			}
			tabularData[i][k] = tabularData[i][k] + " | ";
		}

		}

		/////////////////// forming tabular /////////////////

		String outStr = "";
		for (int i=0; i<numberOfRows; i++) {
			for (int j=0; j<numberOfColumns; j++) {
				outStr = outStr + tabularData[i][j] + "";
			}
			outStr = outStr + "\n";
		}

		return outStr;
	}

	public String getInReadingForm() {

		String outStr = strData;

		outStr = outStr.replace("\t", "");
		outStr = outStr.replace("\\" + "begin{itemize}\n", "");	//TODO: !!!! can be other wors in the row
		outStr = outStr.replace("\\" + "end{itemize}\n", "");
		outStr = outStr.replace("\\" + "item", "\t* ");

		if (outStr.indexOf("\\" + "begin{tabular}") != -1) {
			String tabularStr = outStr.substring(outStr.indexOf("\\" + "begin{tabular}"), outStr.indexOf("\\" + "end{tabular}") + 13);
			String readableTabularStr = strDataToReadableTabular(tabularStr);
			outStr = outStr.replace(tabularStr, readableTabularStr);

			//System.out.println("tabularStr: " + tabularStr);	//log
			//System.out.println("readableTabularStr: " + readableTabularStr);	//log
			//System.out.println("outStr: " + outStr);	//log
		}

		return outStr;
	}
}
