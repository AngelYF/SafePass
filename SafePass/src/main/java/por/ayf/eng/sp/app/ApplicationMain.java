package por.ayf.eng.sp.app;

import java.util.logging.Level;
import java.util.logging.Logger;

import javax.swing.JOptionPane;
import javax.swing.UIManager;

import por.ayf.eng.sp.view.ViewMainWindow;

/**
 *  Main class will execute the application.
 * 
 *  @author: Ángel Yagüe Flor.
 *  @version: 2.1.
 */

public class ApplicationMain {
	public static void main(String[] args) {
		try { // This try-catch will change the regular aparence of JFrame of Java.
//	        UIManager.setLookAndFeel("javax.swing.plaf.metal.MetalLookAndFeel"); 				// Other type of view.
//	        UIManager.setLookAndFeel("com.sun.java.swing.plaf.nimbus.NimbusLookAndFeel");  		// Other.
//	        UIManager.setLookAndFeel("com.sun.java.swing.plaf.windows.WindowsLookAndFeel");  	// Other.
//	        UIManager.setLookAndFeel("com.sun.java.swing.plaf.motif.MotifLookAndFeel");  		// Other.
			
	        UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName()); 				// By defect.
	        new ViewMainWindow(); 
        } 
		catch (Exception ex) {
			Logger.getLogger(ViewMainWindow.class.getName()).log(Level.SEVERE, "Ha ocurrido un error al iniciar la aplicación.", ex);
			
			JOptionPane.showMessageDialog(null,
				 	"Ha ocurrido un error al iniciar la aplicación.", 
					"Error",
					JOptionPane.ERROR_MESSAGE);
        } 
	}
}
