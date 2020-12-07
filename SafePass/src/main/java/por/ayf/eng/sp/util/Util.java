package por.ayf.eng.sp.util;

import java.util.logging.Level;
import java.util.logging.Logger;

import javax.swing.JOptionPane;

/**
 *  Class Util with some util methods.
 * 
 *  @author: Ángel Yagüe Flor.
 *  @version: 1.0.
 */

public class Util {
	
	public static void logMessage(String nameClass, String message, int typeMessage, Exception e) {
		switch(typeMessage) {
			case 0:	// ERROR
				Logger.getLogger(nameClass).log(Level.SEVERE, message, e);
				
				JOptionPane.showMessageDialog(null,
						message,
						"Error", 
						JOptionPane.ERROR_MESSAGE);
				break;
			case 1:	// INFORMATION
				JOptionPane.showMessageDialog(null,
						message,
						"Información", 
						JOptionPane.INFORMATION_MESSAGE);
				break;
			case 2:	// WARNING
				JOptionPane.showMessageDialog(null,
						message,
						"Advertencia", 
						JOptionPane.WARNING_MESSAGE);
			case 3:	// QUESTION
				JOptionPane.showMessageDialog(null,
						message,
						"Pregunta", 
						JOptionPane.QUESTION_MESSAGE);
				break;
			default:
				break;
		}	
	}
}
