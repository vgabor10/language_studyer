import common.Logger;
import graphic_user_interface.MainFrame;

public class LanguageStudyer {

	public static void main(String args[]) {

		Logger logger = new Logger();
		logger.setLogFile();

		logger.debug("start program");
		new MainFrame().setVisible(true);
	}
    
}
