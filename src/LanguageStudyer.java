import graphic_user_interface.*;
import terminal_interface.*;

public class LanguageStudyer {

	public static void main(String[] args) {

		int interfaceIndex = Integer.parseInt(args[0]);

		if (interfaceIndex == 0) {
			LanguageStudyerTerminal languageStudyerTerminal = new LanguageStudyerTerminal();
			languageStudyerTerminal.run();
		}

		if (interfaceIndex == 1) {
			LanguageStudyerSwing languageStudyerSwing = new LanguageStudyerSwing();
			languageStudyerSwing.run();
		}
	}
    
}
