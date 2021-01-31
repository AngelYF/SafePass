package por.ayf.eng.sp.view.comp;

import java.awt.BorderLayout;
import java.awt.Toolkit;

import javax.swing.JDialog;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.JOptionPane;
import javax.swing.JTextField;
import javax.swing.JLabel;
import javax.swing.JButton;

import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;

import javax.swing.JPasswordField;

import por.ayf.eng.sp.database.SQLManager;
import por.ayf.eng.sp.database.SQLQuery;
import por.ayf.eng.sp.database.bean.BeanRegistry;
import por.ayf.eng.sp.util.Util;
import por.ayf.eng.sp.view.ViewMainWindow;

/**
 *  JDialog will create a new registry.
 * 
 *  @author: Ángel Yagüe Flor.
 *  @version: 3.0.
 */

public class ComponentViewCreatePass extends JDialog {
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
	private JButton btnShowPassword;	
	private JLabel lblQuestion;
	private JTextField tfQuestion;
	private JLabel lblAnswerQuestion;
	private JPasswordField tfpAnswerQuestion;
	private JButton btnShowAnswer;	
	private JButton btnCreate;		
	private JButton btnCancel;
	
	private SQLManager sqlManager;
	
	public ComponentViewCreatePass(ViewMainWindow ventana, boolean modal, SQLManager sqlManager, boolean carga) {
		super(ventana, modal);
		this.sqlManager = sqlManager;
		initComponents();
	}
	
	private void showPassword() {
		if(tfpPassword.getEchoChar() == (char) 0) { // If is hide.
			tfpPassword.setEchoChar('*');
			btnShowPassword.setText("Mostrar");
		} else {								
			tfpPassword.setEchoChar((char) 0);
			btnShowPassword.setText("Ocultar");
		}
	}
	
	private void showAnswer() {
		if(tfpAnswerQuestion.getEchoChar() == (char) 0) { // If is hide.
			tfpAnswerQuestion.setEchoChar('*');
			btnShowAnswer.setText("Mostrar");
		} else {								
			tfpAnswerQuestion.setEchoChar((char) 0);
			btnShowAnswer.setText("Ocultar");
		}
	}
	
	private void createRegistry() {
		// If is filled the name, will can create the registry. Else, will see a message of info.
		if(!tfName.getText().equals("")) { 
			// Almacenate the data of the fields in the registry that want create.
			String password = "";
			String answer = "";
			
			for(char letra : tfpPassword.getPassword()) {
				password += letra;
			}
			
			for(char letra : tfpAnswerQuestion.getPassword()) {
				answer += letra;
			}
			
			BeanRegistry registro = new BeanRegistry(tfName.getText(),
											 tfUsername.getText(),
											 tfEmail.getText(),
											 password,
											 tfQuestion.getText(),
											 answer,
											 null,
											 null);
			
			SQLQuery sqlQuery = new SQLQuery();
			sqlQuery.insertRegistry(sqlManager, registro);

			ViewMainWindow.refreshList();
			dispose();
		} else {
			Util.showMessage(ViewMainWindow.class, "El registro debe tener un nombre para ser creado.", JOptionPane.INFORMATION_MESSAGE, null);
		}
	}
	
	private void initComponents() {
		setTitle("Crear registro");
		setIconImage(Toolkit.getDefaultToolkit().getImage("src/main/resources/images/icon.png"));
		setBounds(100, 100, 430, 340);
		setLocationRelativeTo(null); 			// Center the view.
		setResizable(false); 					// Cannot resizable.
		getContentPane().setLayout(new BorderLayout());
		contentPanel.setBorder(new EmptyBorder(5, 5, 5, 5));
		getContentPane().add(contentPanel, BorderLayout.CENTER);
		contentPanel.setLayout(null);	
		
		// Name:
		
		lblName = new JLabel("Nombre ");
		lblName.setBounds(30, 30, 169, 14);
		contentPanel.add(lblName);
		
		tfName = new JTextField();
		tfName.setBounds(30, 55, 169, 20);
		tfName.setColumns(10);
		contentPanel.add(tfName);
		
		// Username:
		
		lblUsername = new JLabel("Nombre de usuario");
		lblUsername.setBounds(30, 86, 169, 14);
		contentPanel.add(lblUsername);
		
		tfUsername = new JTextField();
		tfUsername.setBounds(30, 111, 169, 20);
		tfUsername.setColumns(10);
		contentPanel.add(tfUsername);
		
		// Email:
		
		lblEmail = new JLabel("Email");
		lblEmail.setBounds(30, 142, 169, 14);
		contentPanel.add(lblEmail);
		
		tfEmail = new JTextField();
		tfEmail.setBounds(30, 167, 169, 20);
		tfEmail.setColumns(10);
		contentPanel.add(tfEmail);
		
		// Password:
		
		lblPassword = new JLabel("Password");
		lblPassword.setBounds(30, 198, 169, 14);
		contentPanel.add(lblPassword);
		
		tfpPassword = new JPasswordField();
		tfpPassword.setEchoChar('*');
		tfpPassword.setText("");
		tfpPassword.setBounds(30, 223, 169, 20);
		contentPanel.add(tfpPassword);
		
		btnShowPassword = new JButton("Mostrar");
		btnShowPassword.setBounds(30, 254, 95, 23);
		btnShowPassword.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				showPassword();
			}
		});
		contentPanel.add(btnShowPassword);
		
		// Question:
		
		lblQuestion = new JLabel("Pregunta secreta");
		lblQuestion.setBounds(224, 142, 169, 14);
		contentPanel.add(lblQuestion);
		
		tfQuestion = new JTextField();
		tfQuestion.setColumns(10);
		tfQuestion.setBounds(224, 167, 169, 20);
		contentPanel.add(tfQuestion);
		
		// Answer Question:
		
		lblAnswerQuestion = new JLabel("Respuesta secreta");
		lblAnswerQuestion.setBounds(224, 198, 169, 14);
		contentPanel.add(lblAnswerQuestion);
		
		tfpAnswerQuestion = new JPasswordField();
		tfpAnswerQuestion.setText("");
		tfpAnswerQuestion.setEchoChar('*');
		tfpAnswerQuestion.setBounds(224, 223, 169, 20);
		contentPanel.add(tfpAnswerQuestion);
		
		btnShowAnswer = new JButton("Mostrar");
		btnShowAnswer.setBounds(224, 254, 95, 23);
		btnShowAnswer.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				showAnswer();
			}
		});
		contentPanel.add(btnShowAnswer);
		
		// Buttons:
		
		btnCreate = new JButton("Crear");
		btnCreate.setBounds(224, 54, 169, 23);
		btnCreate.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {	
				createRegistry();
			}
		});
		contentPanel.add(btnCreate);
		
		btnCancel = new JButton("Cancelar");
		btnCancel.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				dispose();
			}
		});
		btnCancel.setBounds(224, 88, 169, 23);
		contentPanel.add(btnCancel);
	}
}
