package graphic_user_interface;

import graphic_user_interface.*;
import experimental_classes.*;
import common.*;
import terminal_interface.*;

public class LanguageStudyerSwing {

	public void run() {

		Logger logger = new Logger();
		logger.setLogFile();

		logger.debug("start program");
		new NewJFrame().setVisible(true);
	}
    
}
