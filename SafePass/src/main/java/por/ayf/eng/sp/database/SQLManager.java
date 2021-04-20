package por.ayf.eng.sp.database;

import java.io.File;
import java.sql.Connection;
import java.sql.SQLException;

import javax.swing.JOptionPane;

import org.sqlite.SQLiteDataSource;
import org.sqlite.SQLiteJDBCLoader;

import por.ayf.eng.sp.util.Util;

/**
 *  Class SQLManager will manage the SQL Connection.
 * 
 *  @author: Ángel Yagüe Flor.
 *  @version: 2.0.
 */

public class SQLManager {
	private static SQLManager sqlManager = null; 		// Singleton
	private SQLQuery sqlQuery = null;
	private SQLiteDataSource dataSource = null;
	private Connection connection = null;
	
	protected SQLManager() {
		
	}
	
	public static SQLManager getInstance() {
		if(sqlManager == null) {
			sqlManager = new SQLManager();
		}
		
		return sqlManager;
	}
	
	public void createDatabase(String url) throws Exception {
        connect(url);
    	createTables();
	}
	
	public void loadDatabase(String path) throws Exception {
		connect(path);
	}
	
	@SuppressWarnings("unused")
	private void deleteDatabase(String path) {
		File database = new File(path);
		
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
	
	public void saveDatabase() {
		try {
			connection.commit();
		} catch (SQLException ex) {
			Util.showMessage(SQLManager.class, "Error al guardar la base de datos.", JOptionPane.ERROR_MESSAGE, ex);
		}
		
		Util.showMessage(SQLManager.class, "Se ha guardado la base de datos.", JOptionPane.INFORMATION_MESSAGE, null);
	}
	
	public void rollbackDatabase() {
		try {
			connection.rollback();
		} catch (SQLException ex) {
			Util.showMessage(SQLManager.class, "Error al hacer rollback a la base de datos.", JOptionPane.ERROR_MESSAGE, ex);
		}
	}
	
	private void connect(String path) throws Exception {
		SQLiteJDBCLoader.initialize();
        dataSource = new SQLiteDataSource();
        sqlQuery = new SQLQuery();
        
    	dataSource.setUrl("jdbc:sqlite:" + path);
    	connection = dataSource.getConnection();
    	connection.setAutoCommit(false);
    	
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
		} catch (Exception e) {
			Util.showMessage(SQLManager.class, "Error al crear las tablas de la base de datos.", JOptionPane.ERROR_MESSAGE, e);
		}
	}

}
