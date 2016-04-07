package common;

import java.util.*;
import java.text.DecimalFormat;
import java.lang.Math;

public class Histogram {

	private int[] data = new int[10];

	Histogram() {
		for (int i=0; i<10; i++) {
			data[i] = 0;
		}
	}

	public void addAnElementByRightAnswerRate(double rightAnswerRate) {
		if (rightAnswerRate == 1.0) {
			data[9]++;
		}
		else {
			data[(int)Math.floor(rightAnswerRate*10.0)]++;
		}
	}

	public String toStringHorisontally(String separatorString) {
		String out = Integer.toString(data[0]);
		for (int i=1; i<10; i++) {
			out = out + separatorString + data[i];
		}
		return out;
	}

}
