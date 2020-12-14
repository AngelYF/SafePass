package por.ayf.eng.sp.database;

import java.io.File;
import java.sql.Connection;
import java.sql.SQLException;

import javax.swing.JOptionPane;

import org.sqlite.SQLiteDataSource;
import org.sqlite.SQLiteJDBCLoader;

import por.ayf.eng.sp.database.bean.BeanUser;
import por.ayf.eng.sp.util.Util;

/**
 *  Class SQLManager will manage the SQL Connection.
 * 
 *  @author: Ángel Yagüe Flor.
 *  @version: 1.0.
 */

public class SQLManager {
	private static SQLManager sqlManager = null; 		// Singleton
	private SQLQuery sqlQuery = null;
	private SQLiteDataSource dataSource = null;
	private Connection connection = null;
	
	private BeanUser user;
	private String username = "";
	private String password = "";
	
	protected SQLManager() {
		
	}
	
	public static SQLManager getInstance() {
		if(sqlManager == null) {
			sqlManager = new SQLManager();
		}
		
		return sqlManager;
	}
	
	public void createDatabase(String url) throws Exception {
        do {
        	username = JOptionPane.showInputDialog("¿Cuál es su nombre de usuario?");
        } while(username == null || username.equals(""));
        
        do {
        	password = JOptionPane.showInputDialog("¿Cuál es su password?");
        } while(password == null || password.equals(""));
		
        Util.showMessage(SQLManager.class, 
        		"\nDatos de conexión: \n\nUsuario:       " + username +"\nPassword:    " + password + "\n\n", 
        		JOptionPane.INFORMATION_MESSAGE, 
        		null);
        
        user = new BeanUser(username, password);
        
        connect(url);
    	createTables();
	}
	
	public void loadDatabase(String url) throws Exception {
		int attempts = 0;
		
		connect(url);
		BeanUser user = sqlQuery.selectUser(sqlManager);
		
		do {
        	username = JOptionPane.showInputDialog("¿Cuál es su nombre de usuario?");
        	password = JOptionPane.showInputDialog("¿Cuál es su password?");
        	
        	if(user.getUsername().equals(username) && user.getPassword().equals(password)) {
        		return;
        	} else {
        		attempts++;
        	}
        } while(attempts != 3);

		deleteDatabase(url);
	}
	
	private void deleteDatabase(String url) {
		File database = new File(url);
		
		try {
			if(database.exists()) {
				disconnect();
				database.delete();
				Util.showMessage(SQLManager.class, "Número de intentos excedido. Por seguridad, se ha borrado su base de datos.", JOptionPane.INFORMATION_MESSAGE, null);
			} else {
				Util.showMessage(SQLManager.class, "Error, su base de datos no existe.", JOptionPane.ERROR_MESSAGE, null);
			}
		}	
		catch(Exception e) {
			Util.showMessage(SQLManager.class, "Error al verificar su base de datos.", JOptionPane.ERROR_MESSAGE, e);
		}
	}
	
	private void connect(String url) throws Exception {
		SQLiteJDBCLoader.initialize();
        dataSource = new SQLiteDataSource();
        sqlQuery = new SQLQuery();
        
    	dataSource.setUrl("jdbc:sqlite:" + url);
    	connection = dataSource.getConnection();
    	
    	if(connection == null) {
    		Util.showMessage(SQLManager.class, "Error al conectar a la base de datos.", JOptionPane.ERROR_MESSAGE, null);
    	}
	}
	
	private void disconnect() {
		try {
			connection.close();
		} catch (SQLException e) {
			Util.showMessage(SQLManager.class, "Error al desconectar a la base de datos.", JOptionPane.ERROR_MESSAGE, e);
		}
	}
	
	public Connection getConnection() {
		return connection;
	}
	
	private void createTables() {
		try {
			sqlQuery.createTableRegistry(sqlManager);
			sqlQuery.createTableUser(sqlManager);
			sqlQuery.insertUser(sqlManager, user);	
		} catch (Exception e) {
			Util.showMessage(SQLManager.class, "Error al crear las tablas de la base de datos.", JOptionPane.ERROR_MESSAGE, e);
		}
	}

}
