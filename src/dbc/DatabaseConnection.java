package dbc;

import java.io.File;
import java.io.FileNotFoundException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

import java.util.Scanner;

public class DatabaseConnection {

    private static DatabaseConnection instance;
    private String driverName;
    private Connection conn;

    private String host;
    private String port;
    private String dbNavn;
    private String user;
    private String pass;
    private String db;

    //Checker om forbindelsen til databasen er oprettet uden problemer.
    private boolean hasConnection;

    public static DatabaseConnection getInstance() {
        if (instance == null) {
            instance = new DatabaseConnection();
        }
        return instance;
    }

    public void createConnection() throws FileNotFoundException, SQLException, ClassNotFoundException {
//Hvis der er forbindelse i forvejen når metoden kaldes, vil den blive lukket.
        if (conn == null) {
        //Sætter et String variable på et filnavn. Derefter sættes en ny fil til navnet, 
            //og den tekstfil der bliver fundet, scannes igennem for Database login info.
            String filename = "xraydb.txt";
            Scanner textScan;

            File file = new File(filename);
            textScan = new Scanner(file);
            while (textScan.hasNext()) {
                switch (textScan.next()) {
                    case "Host:":
                        host = textScan.next();
                        break;
                    case "Port:":
                        port = textScan.next();
                        break;
                    case "Dbnavn:":
                        dbNavn = textScan.next();
                        break;
                    case "User:":
                        user = textScan.next();
                        break;
                    case "Password:":
                        if (textScan.hasNext() == false) {
                            pass = "";
                        } else {
                            pass = textScan.next();
                        }
                        break;
                }
            }
            textScan.close();

            db = "jdbc:mysql://" + host + ":" + port + "/" + dbNavn;

            driverName = "com.mysql.jdbc.Driver";

        Class.forName(driverName);

        conn = DriverManager.getConnection(db, user, pass);

        if(conn != null){
            System.out.println("Der er oprettet forbindelse til databasen");
        }else{
            System.out.println("Der blev ikke oprettet forbindelse...");
        }
        //Hvis programmet når her til uden at støde på exceptions må den have forbindelse.
        hasConnection = true;
        }
    }

    public Connection getConnection() {
        return conn;
    }

    public boolean hasConnection() {
        return hasConnection;
    }
    
    //I forbindelse med testing skal man kunne lukke forbindelsen efter sig.
    public void closeConnection() throws SQLException {
        conn.close();
        conn = null;
    }
}
