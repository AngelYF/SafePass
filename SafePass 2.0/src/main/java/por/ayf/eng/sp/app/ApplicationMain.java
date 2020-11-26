package por.ayf.eng.sp.app;

import java.util.logging.Level;
import java.util.logging.Logger;

import javax.swing.UIManager;

import por.ayf.eng.sp.view.ViewMainWindow;

/**
 *  Main class will execute the application.
 * 
 *  @author: Ángel Yagüe Flor.
 *  @version: 2.0.
 *
 */

public class ApplicationMain {
	public static void main(String[] args) {
		try { // This try-catch will change the regular aparence of JFrame of Java.
			
           // UIManager.setLookAndFeel("javax.swing.plaf.metal.MetalLookAndFeel"); 				Other type of view.
           // UIManager.setLookAndFeel("com.sun.java.swing.plaf.nimbus.NimbusLookAndFeel");  	Other.
			
           UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName()); // By defect.
           new ViewMainWindow(); 
        } 
		catch (Exception ex) {
          Logger.getLogger(ViewMainWindow.class.getName()).log(Level.SEVERE, null, ex);
        } 
	}
}
