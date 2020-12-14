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
 *  JDialog will modify a registry.
 * 
 *  @author: Ángel Yagüe Flor.
 *  @version: 2.0.
 */

public class ComponentViewModifyPass extends JDialog {
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
	private JButton btnCancel;						
	private JButton btnShow;							
	private JButton btnEdit;							
	
	private String oldName;									
	private SQLManager sqlManager;				

	public ComponentViewModifyPass(ViewMainWindow ventana, boolean modal, SQLManager sqlManager, String oldName) {
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
	
	private void editarRegistro() {
		// If is filled the name, will can edit the registry. Else, will see a message of info.
		if(!tfName.getText().equals("")) {
			// Almacenate the data of the fields in the registry that want modify.
			String password = "";
			
			for(char letra : tfpPassword.getPassword()) {
				password += letra;
			}
			
			BeanRegistry registry = new BeanRegistry(tfName.getText(), 
													tfUsername.getText(),
													tfEmail.getText(),
													password);
			
			SQLQuery sqlQuery = new SQLQuery();
			sqlQuery.updateRegistry(sqlManager, registry, oldName);
			
			// Update the list.
			ViewMainWindow.refreshList();
			dispose();
		} else {
			Util.showMessage(ViewMainWindow.class, "El registro debe tener un nombre para ser editado.", JOptionPane.INFORMATION_MESSAGE, null);
		}
	}
	
	private void initComponents() {
		setTitle("SafePass");
		setIconImage(Toolkit.getDefaultToolkit().getImage("src/main/resources/images/icon.png"));
		setBounds(100, 100, 375, 340);
		setLocationRelativeTo(null); 			// Center the view.
		setResizable(false); 					// Cannot resizable.
		getContentPane().setLayout(new BorderLayout());
		contentPanel.setBorder(new EmptyBorder(5, 5, 5, 5));
		getContentPane().add(contentPanel, BorderLayout.CENTER);
		contentPanel.setLayout(null);	
		
		SQLQuery sqlQuery = new SQLQuery();
		BeanRegistry registry = sqlQuery.selectRegistryByName(sqlManager, oldName);
		
		// Name:
		
		lblName = new JLabel("Nombre ");
		lblName.setBounds(30, 30, 169, 14);
		contentPanel.add(lblName);
		
		tfName = new JTextField();
		tfName.setBounds(30, 55, 169, 20);
		tfName.setText(registry.getName());
		contentPanel.add(tfName);
		tfName.setColumns(10);
		
		// Username:
		
		lblUsername = new JLabel("Nombre de usuario");
		lblUsername.setBounds(30, 86, 169, 14);
		contentPanel.add(lblUsername);
		
		tfUsername = new JTextField();
		tfUsername.setBounds(30, 111, 169, 20);
		tfUsername.setText(registry.getUsername());
		contentPanel.add(tfUsername);
		tfUsername.setColumns(10);
		
		// Email:
		
		lblEmail = new JLabel("Email");
		lblEmail.setBounds(30, 142, 169, 14);
		contentPanel.add(lblEmail);
		
		tfEmail = new JTextField();
		tfEmail.setBounds(30, 167, 169, 20);
		tfEmail.setText(registry.getEmail());
		contentPanel.add(tfEmail);
		tfEmail.setColumns(10);
		
		// Password:
		
		lblPassword = new JLabel("Password");
		lblPassword.setBounds(30, 200, 169, 14);
		contentPanel.add(lblPassword);
		
		tfpPassword = new JPasswordField();
		tfpPassword.setEchoChar('*');
		tfpPassword.setText(registry.getPassword());
		tfpPassword.setBounds(30, 225, 169, 20);
		contentPanel.add(tfpPassword);
		
		// Buttons:
		
		btnEdit = new JButton("Editar");
		btnEdit.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				editarRegistro();
			}
		});
		btnEdit.setBounds(224, 54, 120, 23);
		contentPanel.add(btnEdit);
		
		btnCancel = new JButton("Cancelar");
		btnCancel.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				dispose();
			}
		});
		btnCancel.setBounds(224, 88, 120, 23);
		contentPanel.add(btnCancel);
		
		btnShow = new JButton("Mostrar");
		btnShow.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				showPassword();
			}
		});
		btnShow.setBounds(30, 256, 95, 23);
		contentPanel.add(btnShow);
	}
}