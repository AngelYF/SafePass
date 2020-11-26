package por.ayf.eng.sp.view.comp;

import java.awt.BorderLayout;
import java.awt.Toolkit;

import javax.swing.JDialog;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.JTextField;
import javax.swing.JLabel;
import javax.swing.JButton;

import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;

import javax.swing.JPasswordField;

import por.ayf.eng.sp.database.SQLManager;
import por.ayf.eng.sp.database.SQLQuery;
import por.ayf.eng.sp.database.bean.BeanRegistry;
import por.ayf.eng.sp.view.ViewMainWindow;

/**
 *  JDialog will see a registry.
 * 
 *  @author: Ángel Yagüe Flor.
 *  @version: 2.0.
 */

public class ComponentViewConsultPass extends JDialog {
	private static final long serialVersionUID = 1L;	
	
	private final JPanel contentPanel = new JPanel(); 	
	private JLabel lblName;							
	private JTextField tfName;						
	private JLabel lblUsername;					
	private JTextField tfUsername;				
	private JLabel lblEmail;							
	private JTextField tfEmail;							
	private JLabel lblPassword;							
	private JPasswordField tfpPassword;					
	private JButton btnShow;							
	private JButton btnBack;							
	
	private SQLManager sqlManager;
	private String oldName;

	public ComponentViewConsultPass(ViewMainWindow ventana, boolean modal, SQLManager sqlManager, String oldName) {
		super(ventana, modal);
		this.sqlManager = sqlManager;
		this.oldName = oldName;
		initComponents();
	}
	
	private void showPassword() {
		if(tfpPassword.getEchoChar() == (char) 0) { // If is hide.
			tfpPassword.setEchoChar('*');
			btnShow.setText("Mostrar");
		} else {									
			tfpPassword.setEchoChar((char) 0);
			btnShow.setText("Ocultar");
		}
	}

	private void initComponents() {
		setTitle("SafePass");
		setIconImage(Toolkit.getDefaultToolkit().getImage("src/main/resources/images/icon.png"));
		setBounds(100, 100, 375, 340);
		setLocationRelativeTo(null); 		// Center the view.
		setResizable(false); 				// Cannot resizable.
		getContentPane().setLayout(new BorderLayout());
		contentPanel.setBorder(new EmptyBorder(5, 5, 5, 5));
		getContentPane().add(contentPanel, BorderLayout.CENTER);
		contentPanel.setLayout(null);	
		
		lblName = new JLabel("Nombre ");
		lblName.setBounds(30, 30, 169, 14);
		contentPanel.add(lblName);
		
		SQLQuery sqlQuery = new SQLQuery();
		BeanRegistry registry = sqlQuery.selectRegistryByName(sqlManager, oldName);
		
		tfName = new JTextField();
		tfName.setBounds(30, 55, 169, 20);
		tfName.setText(registry.getName());
		contentPanel.add(tfName);
		tfName.setColumns(10);
		
		lblUsername = new JLabel("Nombre de usuario");
		lblUsername.setBounds(30, 86, 169, 14);
		contentPanel.add(lblUsername);
		
		tfUsername = new JTextField();
		tfUsername.setBounds(30, 111, 169, 20);
		tfUsername.setText(registry.getUsername()); 
		contentPanel.add(tfUsername);
		tfUsername.setColumns(10);
		
		lblEmail = new JLabel("Email");
		lblEmail.setBounds(30, 142, 46, 14);
		contentPanel.add(lblEmail);
		
		tfEmail = new JTextField();
		tfEmail.setBounds(30, 167, 169, 20);
		tfEmail.setText(registry.getEmail()); 
		contentPanel.add(tfEmail);
		tfEmail.setColumns(10);
		
		lblPassword = new JLabel("Password");
		lblPassword.setBounds(30, 198, 46, 14);
		contentPanel.add(lblPassword);
		
		tfpPassword = new JPasswordField();
		tfpPassword.setEchoChar('*');
		tfpPassword.setText(registry.getPassword()); 
		tfpPassword.setBounds(30, 223, 169, 20);
		contentPanel.add(tfpPassword);
		
		btnShow = new JButton("Mostrar");
		btnShow.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				showPassword();
			}
		});
		btnShow.setBounds(30, 254, 95, 23);
		contentPanel.add(btnShow);
		
		btnBack = new JButton("Volver");
		btnBack.setBounds(224, 54, 120, 23);
		btnBack.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				dispose();
			}
		});
		contentPanel.add(btnBack);
	}
}
