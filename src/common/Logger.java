package common;

import java.util.*;
import java.io.*;

public class Logger {

	private final String logFilePath = "../log_files/log_file.txt";

	public final boolean isDebug;

	public Logger() {
		isDebug = true;
	}

	public void setLogFile() {
		try {
			FileWriter fw = new FileWriter(logFilePath,false);
			fw.write("");
			fw.close();
		}
		catch(IOException ioe) {
			System.err.println("IOException: " + ioe.getMessage());
		}
	}

	private void writeToLogFile(String str) {
		try {
			FileWriter fw = new FileWriter(logFilePath,true);
			fw.write(str);
			fw.close();
		}
		catch(IOException ioe) {
			System.err.println("IOException: " + ioe.getMessage());
		}
	}

	public void debugActualTime() {
		if (isDebug) {
			Date date = new Date();
			long now = date.getTime();
			writeToLogFile("actual time: " + now + "\n");
		}
	}

	public void debug(String debugMassage) {
		if (isDebug) {
			Date date = new Date();
			long now = date.getTime();

			writeToLogFile(now + ": " + debugMassage + "\n");
		}
	}

}
