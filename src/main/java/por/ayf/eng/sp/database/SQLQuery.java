package por.ayf.eng.sp.database;

import por.ayf.eng.sp.database.bean.BeanRegistry;
import por.ayf.eng.sp.util.Util;

import javax.swing.*;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

/**
 * Class SQLManager will manage the SQL queries.
 *
 * @author: Ángel Yagüe Flor.
 * @version: 2.0.
 */

public class SQLQuery {

    public void createTableRegistry(SQLManager sqlManager) {
        Connection connection = sqlManager.getConnection();
        Statement sentencia;

        try {
            sentencia = connection.createStatement();
            sentencia.execute("CREATE TABLE REGISTRY (ID INTEGER PRIMARY KEY AUTOINCREMENT, "
                    + "NAME TEXT(140) NOT NULL UNIQUE, "
                    + "USERNAME TEXT(50) NOT NULL, "
                    + "EMAIL TEXT(50) NOT NULL, "
                    + "PASSWORD TEXT(50) NOT NULL, "
                    + "QUESTION TEXT(50), "
                    + "ANSWER_QUESTION TEXT(50), "
                    + "DATE_CREATE TEXT NOT NULL, "
                    + "DATE_UPDATE TEXT NOT NULL);");
        } catch (Exception e) {
            Util.showMessage(SQLQuery.class, "Error al crear la tabla de registros de la base de datos.", JOptionPane.ERROR_MESSAGE, e);
        }
    }

    public void insertRegistry(SQLManager sqlManager, BeanRegistry registry) {
        Connection connection = sqlManager.getConnection();
        Statement sentencia;

        try {
            sentencia = connection.createStatement();
            sentencia.execute("INSERT INTO REGISTRY (NAME, USERNAME, EMAIL, PASSWORD, QUESTION, ANSWER_QUESTION, DATE_CREATE, DATE_UPDATE) "
                    + "VALUES ('" + registry.getName() + "', '" + registry.getUsername() + "', '" + registry.getEmail() + "', '" + registry.getPassword() + "', '" + registry.getQuestion() + "', '" + registry.getAnswerQuestion() + "', DATETIME('NOW'), DATETIME('NOW'));");
        } catch (Exception e) {
            Util.showMessage(SQLQuery.class, "Error al insertar nuevo usuario de la base de datos.", JOptionPane.ERROR_MESSAGE, e);
        }
    }

    public void updateRegistry(SQLManager sqlManager, BeanRegistry registry, String oldName) {
        Connection connection = sqlManager.getConnection();
        Statement sentencia;

        try {
            sentencia = connection.createStatement();
            sentencia.execute("UPDATE REGISTRY SET NAME = '" + registry.getName() + "', USERNAME = '" + registry.getUsername() + "', EMAIL = '" + registry.getEmail() + "', PASSWORD = '" + registry.getPassword() + "', QUESTION = '" + registry.getQuestion() + "', ANSWER_QUESTION = '" + registry.getAnswerQuestion() + "', DATE_UPDATE = DATETIME('NOW') WHERE NAME = '" + oldName + "';");
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
            while (result.next()) {
                registry = new BeanRegistry(result.getString("NAME"), result.getString("USERNAME"), result.getString("EMAIL"), result.getString("PASSWORD"), result.getString("QUESTION"), result.getString("ANSWER_QUESTION"), result.getString("DATE_CREATE"), result.getString("DATE_UPDATE"));
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

            while (result.next()) {
                registry = new BeanRegistry(result.getString("NAME"), result.getString("USERNAME"), result.getString("EMAIL"), result.getString("PASSWORD"), result.getString("QUESTION"), result.getString("ANSWER_QUESTION"), result.getString("DATE_CREATE"), result.getString("DATE_UPDATE"));
            }
        } catch (Exception e) {
            Util.showMessage(SQLQuery.class, "Error al obtener el registro de la base de datos.", JOptionPane.ERROR_MESSAGE, e);
        }

        return registry;
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
