package por.ayf.eng.sp.database;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import javax.swing.JOptionPane;

import por.ayf.eng.sp.database.bean.BeanRegistry;
import por.ayf.eng.sp.database.bean.BeanUser;
import por.ayf.eng.sp.util.Util;

/**
 *  Class SQLManager will manage the SQL queries.
 * 
 *  @author: Ángel Yagüe Flor.
 *  @version: 1.0.
 */

public class SQLQuery {
	
	public void createTableRegistry(SQLManager sqlManager) {		
		Connection connection = sqlManager.getConnection();
		Statement sentencia;
		
		try {
			sentencia = connection.createStatement();
			sentencia.execute("CREATE TABLE REGISTRY (ID INTEGER PRIMARY KEY AUTOINCREMENT, "
													+ "NAME TEXT(140) NOT NULL UNIQUE,"
													+ "USERNAME TEXT(50) NOT NULL," 
													+ "EMAIL TEXT(50) NOT NULL,"
													+ "PASSWORD TEXT(50) NOT NULL);");
		} catch (Exception e) {
			Util.showMessage(SQLQuery.class, "Error al crear la tabla de registros de la base de datos.", JOptionPane.ERROR_MESSAGE, e);
		}
	}
	
	public void createTableUser(SQLManager sqlManager) {		
		Connection connection = sqlManager.getConnection();
		Statement sentencia;
		
		try {
			sentencia = connection.createStatement();
			sentencia.execute("CREATE TABLE USER (USERNAME TEXT(50) PRIMARY KEY NOT NULL, "
												+ "PASSWORD TEXT(50) NOT NULL);");
		} catch (Exception e) {
			Util.showMessage(SQLQuery.class, "Error al crear la tabla de usuarios de la base de datos.", JOptionPane.ERROR_MESSAGE, e);
		}
	}
	
	public void insertRegistry(SQLManager sqlManager, BeanRegistry registry) {		
		Connection connection = sqlManager.getConnection();
		Statement sentencia;
		
		try {
			sentencia = connection.createStatement();
			sentencia.execute("INSERT INTO REGISTRY (NAME, USERNAME, EMAIL, PASSWORD) VALUES ('" + registry.getName() + "', '" + registry.getUsername() + "', '" + registry.getEmail() + "', '" + registry.getPassword() + "');");	
		} catch (Exception e) {
			Util.showMessage(SQLQuery.class, "Error al insertar nuevo usuario de la base de datos.", JOptionPane.ERROR_MESSAGE, e);
		}
	}
	
	public void insertUser(SQLManager sqlManager, BeanUser user) {		
		Connection connection = sqlManager.getConnection();
		Statement sentencia;
		
		try {
			sentencia = connection.createStatement();
			sentencia.execute("INSERT INTO USER (USERNAME, PASSWORD) VALUES ('" + user.getUsername() + "', '" + user.getPassword() + "');");	
		} catch (Exception e) {
			Util.showMessage(SQLQuery.class, "Error al insertar nuevo usuario de la base de datos.", JOptionPane.ERROR_MESSAGE, e);
		}
	}
	
	public void updateRegistry(SQLManager sqlManager, BeanRegistry registry, String oldName) {		
		Connection connection = sqlManager.getConnection();
		Statement sentencia;
		
		try {
			sentencia = connection.createStatement();
			sentencia.execute("UPDATE REGISTRY SET NAME = '" + registry.getName() + "', USERNAME = '" + registry.getUsername() + "', EMAIL = '" + registry.getEmail() + "', PASSWORD = '" + registry.getPassword() + "' WHERE NAME = '" + oldName + "';");	
		} catch (Exception e) {
			Util.showMessage(SQLQuery.class, "Error al actualizar el registro de la base de datos.", JOptionPane.ERROR_MESSAGE, e);
		}
	}
	
	public List<BeanRegistry> selectAllRegistry(SQLManager sqlManager) {
		List<BeanRegistry> list = new ArrayList<BeanRegistry>();
		Connection connection = sqlManager.getConnection();
		Statement sentencia;
		
		try {
			sentencia = connection.createStatement(ResultSet.TYPE_FORWARD_ONLY, ResultSet.CONCUR_READ_ONLY);
			ResultSet result = sentencia.executeQuery("SELECT * FROM REGISTRY ORDER BY ID;");	
			
			BeanRegistry registry;
			while(result.next()) {
				registry = new BeanRegistry(result.getString("NAME"), result.getString("USERNAME"), result.getString("EMAIL"), result.getString("PASSWORD"));
				list.add(registry);
            }
		} catch (Exception e) {
			Util.showMessage(SQLQuery.class, "Error al obtener los registros de la base de datos.", JOptionPane.ERROR_MESSAGE, e);
		}
		
		return list;
	}
	
	public BeanRegistry selectRegistryByName(SQLManager sqlManager, String name) {
		Connection connection = sqlManager.getConnection();
		Statement sentencia;
		BeanRegistry registry = null;
		
		try {
			sentencia = connection.createStatement();
			ResultSet result = sentencia.executeQuery("SELECT * FROM REGISTRY WHERE NAME = '" + name + "';");	

			while(result.next()) {
				registry = new BeanRegistry(result.getString("NAME"), result.getString("USERNAME"), result.getString("EMAIL"), result.getString("PASSWORD"));
            }
		} catch (Exception e) {
			Util.showMessage(SQLQuery.class, "Error al obtener el registro de la base de datos.", JOptionPane.ERROR_MESSAGE, e);
		}
		
		return registry;
	}
	
	public BeanUser selectUser(SQLManager sqlManager) {
		Connection connection = sqlManager.getConnection();
		Statement sentencia;
		BeanUser user = null;
		
		try {
			sentencia = connection.createStatement();
			ResultSet result = sentencia.executeQuery("SELECT * FROM USER;");	

			while(result.next()) {
				user = new BeanUser(result.getString("USERNAME"), result.getString("PASSWORD"));
            }
		} catch (Exception e) {
			Util.showMessage(SQLQuery.class, "Error al obtener el usuario de la base de datos.", JOptionPane.ERROR_MESSAGE, e);
		}
		
		return user;
	}
	
	public void deleteRegistry(SQLManager sqlManager, String name) {		
		Connection connection = sqlManager.getConnection();
		Statement sentencia;
		
		try {
			sentencia = connection.createStatement();
			sentencia.execute("DELETE FROM REGISTRY WHERE NAME = '" + name + "';");	
		} catch (Exception e) {
			Util.showMessage(SQLQuery.class, "Error al eliminar el registro de la base de datos.", JOptionPane.ERROR_MESSAGE, e);
		}
	}
}
