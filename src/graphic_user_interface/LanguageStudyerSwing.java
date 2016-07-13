package graphic_user_interface;

import common.Logger;
import graphic_user_interface.*;
import terminal_interface.*;

public class LanguageStudyerSwing {

	public void run() {

		Logger logger = new Logger();
		logger.setLogFile();

		logger.debug("start program");
		new MainFrame().setVisible(true);
	}
    
}
