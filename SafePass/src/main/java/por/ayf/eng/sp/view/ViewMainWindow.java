package por.ayf.eng.sp.view;

import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JPasswordField;
import javax.swing.border.EmptyBorder;
import javax.swing.filechooser.FileNameExtensionFilter;

import java.awt.BorderLayout;
import java.awt.Toolkit;

import javax.swing.DefaultListModel;
import javax.swing.JFileChooser;
import javax.swing.JMenuBar;
import javax.swing.JMenu;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
import javax.swing.KeyStroke;

import java.awt.event.KeyEvent;
import java.io.File;
import java.nio.file.Files;
import java.util.List;
import java.awt.event.InputEvent;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;

import javax.swing.JButton;
import javax.swing.JList;
import javax.swing.JScrollPane;

import por.ayf.eng.sp.database.SQLManager;
import por.ayf.eng.sp.database.SQLQuery;
import por.ayf.eng.sp.database.bean.BeanRegistry;
import por.ayf.eng.sp.security.Encrypter;
import por.ayf.eng.sp.util.Util;
import por.ayf.eng.sp.view.comp.ComponentViewConsultPass;
import por.ayf.eng.sp.view.comp.ComponentViewCreatePass;
import por.ayf.eng.sp.view.comp.ComponentViewCreator;
import por.ayf.eng.sp.view.comp.ComponentViewModifyPass;

/**
 *  Class will contain the JFrame of the main window.
 * 
 *  @author: Ángel Yagüe Flor.
 *  @version: 3.0.
 */

public class ViewMainWindow extends JFrame {
	private static final long serialVersionUID = 1L;	
	
	private JPanel contentPane;								
	private JMenuBar menuBar;								
	private JMenu jmFile;								
	private JMenuItem jmiNew;								
	private JMenuItem jmiLoad;
	private JMenuItem jmiSave;	
	private JMenuItem jmiExit;								
	private JMenu jmHelp;									
	private JMenuItem mntmAbout;					
	private JButton newPass;								
	private JButton editPass;								
	private JButton consultPass;							
	private JButton deletePass;								
	private JScrollPane scrollPane;							
	private JList<String> list;								
	private static DefaultListModel<String> model;			
	
	private String path = null;										
	private boolean loaded = false;					
	private String keyWord = "";
	private static SQLManager sqlManager = null;	
	
	public ViewMainWindow() {
		sqlManager = SQLManager.getInstance();
		initComponents();
	}
	
	private void newDatabase() {	
		JFileChooser jFChooser = new JFileChooser();
		FileNameExtensionFilter filter = new FileNameExtensionFilter(".sqlite", "sqlite"); 

		jFChooser.setDialogTitle("Crear fichero de base de datos");
		jFChooser.setFileFilter(filter);
		jFChooser.setAcceptAllFileFilterUsed(false);
		jFChooser.setFileSelectionMode(JFileChooser.FILES_ONLY);
		jFChooser.setMultiSelectionEnabled(false);
	
		int seleccion = jFChooser.showSaveDialog(contentPane);

		if(seleccion == JFileChooser.APPROVE_OPTION){
			
			if(jFChooser.getSelectedFile().exists()) {
				path = jFChooser.getSelectedFile().getAbsolutePath();
				
				File existFile = new File(path);
				existFile.delete();
			} else {
				path = jFChooser.getSelectedFile().getAbsolutePath() + ".sqlite";
			}
			
			do {
	        	JPanel panelAux = new JPanel(new BorderLayout());
	        	JLabel lblAux = new JLabel("Escriba su clave de encriptado.");
	        	JPasswordField jpfPasswordAux = new JPasswordField();
	        	panelAux.add(lblAux, BorderLayout.NORTH);
	        	panelAux.add(jpfPasswordAux, BorderLayout.SOUTH);
	        	int option = JOptionPane.showConfirmDialog(null, panelAux, "Entrada", JOptionPane.OK_CANCEL_OPTION, JOptionPane.INFORMATION_MESSAGE);
	
	        	if (option == JOptionPane.OK_OPTION) {
	        		keyWord = new String(jpfPasswordAux.getPassword());
	        	} else {
	        		return;
	        	}
	        } while(keyWord == null || keyWord.equals(""));
		
			try {
				sqlManager.createDatabase(path);
				loaded = true;
			} catch (Exception e) {
				Util.showMessage(getClass(), "Error al crear la base de datos.", JOptionPane.ERROR_MESSAGE, e);
			}
		} else {
			return;
		}
	}
	
	private void loadDatabase() {
		JFileChooser jFChooser = new JFileChooser();
		FileNameExtensionFilter filtro = new FileNameExtensionFilter(".sqlite", "sqlite"); 

		jFChooser.setDialogTitle("Cargar fichero de base de datos");
		jFChooser.setFileFilter(filtro);
		jFChooser.setAcceptAllFileFilterUsed(false);
		jFChooser.setFileSelectionMode(JFileChooser.FILES_ONLY);
		jFChooser.setMultiSelectionEnabled(false);
	
		int seleccion = jFChooser.showOpenDialog(contentPane);

		if(seleccion == JFileChooser.APPROVE_OPTION){
			path = jFChooser.getSelectedFile().getAbsolutePath();

			do {
	        	JPanel panelAux = new JPanel(new BorderLayout());
	        	JLabel lblAux = new JLabel("¿Cuál su clave de encriptado?");
	        	JPasswordField jpfPasswordAux = new JPasswordField();
	        	panelAux.add(lblAux, BorderLayout.NORTH);
	        	panelAux.add(jpfPasswordAux, BorderLayout.SOUTH);
	        	int option = JOptionPane.showConfirmDialog(null, panelAux, "Entrada", JOptionPane.OK_CANCEL_OPTION, JOptionPane.QUESTION_MESSAGE);

	        	if (option == JOptionPane.OK_OPTION) {
	        		keyWord = new String(jpfPasswordAux.getPassword());
	        	} else {
	        		return;
	        	}
	        } while(keyWord == null || keyWord.equals(""));
			
			try {
				Encrypter.aesDecrypt(keyWord, path);
				sqlManager.loadDatabase(path);
			} catch (Exception e) {
				Util.showMessage(getClass(), "Error al cargar base de datos.", JOptionPane.ERROR_MESSAGE, e);
				return;
			}
			
			loaded = true;
		} else {
			loaded = false;
			return;
		}
		
		refreshList();
	}
	
	private void saveDatabase() {
		JFileChooser jFChooser = new JFileChooser();
		FileNameExtensionFilter filtro = new FileNameExtensionFilter(".sqlite", "sqlite"); 

		jFChooser.setDialogTitle("Guardar fichero de base de datos");
		jFChooser.setFileFilter(filtro);
		jFChooser.setAcceptAllFileFilterUsed(false);
		jFChooser.setFileSelectionMode(JFileChooser.FILES_ONLY);
		jFChooser.setMultiSelectionEnabled(false);
	
		int seleccion = jFChooser.showSaveDialog(contentPane);

		if(seleccion == JFileChooser.APPROVE_OPTION){
			String newPath = jFChooser.getSelectedFile().getAbsolutePath();
			
			if(!path.equals(newPath)) {
				newPath += ".sqlite";
				File newFile = new File(newPath);
				
				File oldFile = new File(path);
				try {
					Files.copy(oldFile.toPath(), newFile.toPath());
				} catch (Exception ex) {
					Util.showMessage(getClass(), "Error al guardar base de datos.", JOptionPane.ERROR_MESSAGE, ex);
				}
				
				path = newPath;
			}
			
			try {
				sqlManager.saveDatabase();
				Encrypter.aesEncrypt(keyWord, path);
			} catch (Exception ex) {
				Util.showMessage(getClass(), "Error al guardar base de datos.", JOptionPane.ERROR_MESSAGE, ex);
			}

		}
	}
	
	private void creator() {
		new ComponentViewCreator(this, true).setVisible(true);
	}
	
	private void createPass() {
		// Check the database is active.
		if(path != null && loaded) {
			new ComponentViewCreatePass(this, true, sqlManager, loaded).setVisible(true);
		} else {
			Util.showMessage(getClass(), "Debe cargar la base de datos previamente.", JOptionPane.WARNING_MESSAGE, null);
		}
	}
	
	private void modifyPass() {	
		// Check the database is active.
		if(path != null && loaded) {
			// If I'm selected a registry, then can edit.
			if(list.getSelectedIndex() != -1) { 
				new ComponentViewModifyPass(this, true, sqlManager, list.getSelectedValue()).setVisible(true);
			}
		} else {
			Util.showMessage(getClass(), "Debe cargar la base de datos previamente.", JOptionPane.WARNING_MESSAGE, null);
		}
	}
	
	private void consultPass() {
		// Check the database is active.
		if(path != null && loaded) {
			// If I'm selected a registry, then can see.
			if(list.getSelectedIndex() != -1) { 
				new ComponentViewConsultPass(this, true, sqlManager, list.getSelectedValue()).setVisible(true);
			}
		} else {
			Util.showMessage(getClass(), "Debe cargar la base de datos previamente.", JOptionPane.WARNING_MESSAGE, null);
		}
	}
	
	private void deletePass() {
		if(!loaded) {
			return;
		}

		// If there are elements in the list, can delete.
		if(model.getSize() > 0 && list.getSelectedIndex() != -1) {
			
			// Will generate a dialog for confirm if you desire delete the registry.
			String respuestas[] = {"Sí", "No"}; 	// Answers
            int opcion = JOptionPane.showOptionDialog(null,
                    "¿Está seguro de que desea eliminar este registro?",
                    "Eliminar registro",
                    JOptionPane.YES_NO_OPTION,
                    JOptionPane.QUESTION_MESSAGE,
                    null,
                    respuestas,
                    respuestas[1]); // Option by defect.
            
            
			if(opcion == 0) { // If is yes, we delete the registry.
				// Once we have the index, we delete the element of the JList and the List of Registry.
				SQLQuery sqlQuery = new SQLQuery();
				sqlQuery.deleteRegistry(sqlManager, list.getSelectedValue());
				
				model.remove(list.getSelectedIndex());
				refreshList();
			}
		}
	}
	
	public static void refreshList() {
		// Clean the list and regenerate the elements of the list.
		model.clear();
		
		SQLQuery sqlQuery = new SQLQuery();
		List<BeanRegistry> registers = sqlQuery.selectAllRegistry(sqlManager);
		
		for(int i = 0; i < registers.size(); i++) {
			model.addElement(registers.get(i).getName());;
		}
	}
	
	private void initComponents() {
		setIconImage(Toolkit.getDefaultToolkit().getImage(getClass().getResource("/images/icon.png")));
		setTitle("SafePass");
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 555, 270);
		setLocationRelativeTo(null); // Center the view.
		setResizable(false);
		
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);
		
		// Menu:
		
		menuBar = new JMenuBar();
		menuBar.setBounds(0, 0, 722, 21);
		contentPane.add(menuBar);
		
		jmFile = new JMenu("Archivo");
		menuBar.add(jmFile);
		
		jmiNew = new JMenuItem("Nuevo base de datos        ");
		jmiNew.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				newDatabase(); 
			}
		});
		jmiNew.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_N, InputEvent.ALT_MASK));
		jmFile.add(jmiNew);
		
		jmiLoad = new JMenuItem("Cargar base de datos         ");
		jmiLoad.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				loadDatabase();
			}
		});
		jmiLoad.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_C, InputEvent.ALT_MASK));
		jmFile.add(jmiLoad);
		
		jmiSave = new JMenuItem("Guardar base de datos         ");
		jmiSave.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				saveDatabase();
			}
		});
		jmiSave.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_G, InputEvent.ALT_MASK));
		jmFile.add(jmiSave);
		
		jmFile.addSeparator();
		
		jmiExit = new JMenuItem("Salir          ");
		jmiExit.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				System.exit(1);
			}
		});
		jmiExit.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_F4, InputEvent.ALT_MASK));
		jmFile.add(jmiExit);
		
		jmHelp = new JMenu("Ayuda");
		menuBar.add(jmHelp);
		
		mntmAbout = new JMenuItem("Acerca de SafePass          ");
		mntmAbout.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				creator();
			}
		});
		mntmAbout.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_F1, 0));
		jmHelp.add(mntmAbout);
		
		// Buttons:
		
		newPass = new JButton("Crear");
		newPass.setBounds(420, 32, 119, 23);
		newPass.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				createPass();
			}
		});
		contentPane.add(newPass);
		
		editPass = new JButton("Editar");
		editPass.setBounds(420, 66, 119, 23);
		editPass.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				modifyPass();
			}
		});
		contentPane.add(editPass);
		
		consultPass = new JButton("Ver");
		consultPass.setBounds(420, 100, 119, 23);
		consultPass.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				consultPass();
			}
		});
		contentPane.add(consultPass);
		
		deletePass = new JButton("Eliminar");
		deletePass.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				deletePass();
			}
		});
		deletePass.setBounds(420, 134, 119, 23);
		contentPane.add(deletePass);
		
		// List:
		
		scrollPane = new JScrollPane();
		scrollPane.setBounds(10, 32, 400, 200);
		contentPane.add(scrollPane);
		
		model = new DefaultListModel<String>(); 
		list = new JList<String>(model);
		scrollPane.setViewportView(list);
		
		// See the content:
		setVisible(true);			 
	}
}

