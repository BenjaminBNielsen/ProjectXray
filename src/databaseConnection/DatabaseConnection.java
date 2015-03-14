/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package databaseConnection;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

/* @author Benjamin */

public class DatabaseConnection {
    private static DatabaseConnection instance;
    private Connection connection;
    
    public static DatabaseConnection getInstance(){
        if(instance == null){
            instance = new DatabaseConnection();
        }
        return instance;
    }

    public Connection getConnection() throws SQLException {
        if(connection == null){
            String path = "jdbc:mysql://localhost:3306/projectToolDb";
            String uName = "root";
            String uPassword = "";
            connection = DriverManager.getConnection(path,uName,uPassword);
        }
        return connection;
    }
}
