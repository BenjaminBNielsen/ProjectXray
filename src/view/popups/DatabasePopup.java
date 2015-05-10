/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package view.popups;


import dbc.DatabaseConnection;
import exceptions.DatabaseException;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.PrintWriter;
import java.sql.SQLException;
import java.util.Scanner;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.*;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import view.buttons.PopupMenuButton;



public class DatabasePopup extends PopupWindow {

    private PopupMenuButton createConnection;
    private TextField tHost, tPort, tDbName, tUser;
    private PasswordField tPassword;
    private Label lHost, lPort, lDbName, lUser, lPassword, lErrorMessage;
    private ExceptionPopup exceptionPopup;
    
    public DatabasePopup() {
        exceptionPopup = new ExceptionPopup();
    }

    @Override
    public void display(String title) {
        
        tHost = new TextField();
        tPort = new TextField();
        tDbName = new TextField();
        tUser = new TextField();
        tPassword = new PasswordField();
        
        lHost = new Label("Indtast Host");
        lPort = new Label("Indtast Port");
        lDbName = new Label("Indtast Databasenavn");
        lUser = new Label("Indtast Databasebruger");
        lPassword = new Label("Indtast Databasepassword");
        lErrorMessage = new Label("");
        lErrorMessage.setTextFill(new Color(1,0.3,0.3,1));
        
        VBox vBox = new VBox(15);
        vBox.setAlignment(Pos.BOTTOM_CENTER);
        vBox.setPadding(new Insets(0,0,15,0));
        
        super.addToCenter(vBox);
        vBox.getChildren().addAll(  lHost, tHost,  
                                    lPort, tPort, 
                                    lDbName, tDbName,
                                    lUser, tUser,
                                    lPassword, tPassword,
                                    lErrorMessage
                );
       
        updateTextFields();      
            
        createConnection = new PopupMenuButton("Opret forbindelse");
        createConnection.setOnAction(e -> {
            
            try {
            String filename = "xraydb.txt";
            File file = new File(filename);
            PrintWriter pw = new PrintWriter(file);
            pw.println("Host:");
            pw.println(tHost.getText());
            pw.println("Port:");
            pw.println(tPort.getText());
            pw.println("Dbnavn:");
            pw.println(tDbName.getText());
            pw.println("User:");
            pw.println(tUser.getText());
            pw.println("Password:");
            pw.println(tPassword.getText());
            pw.close();
        } catch (IOException ex) {
            String str = "Databasefilen kunne ikke findes";
            lErrorMessage.setText(str);
        }
            
            try {   
                DatabaseConnection.getInstance().createConnection();
            } catch (DatabaseException ex) {
                exceptionPopup.display(ex.getMessage());
            } 
            if (DatabaseConnection.getInstance().hasConnection() == true) {
                
               super.getStage().close();
            }
            else{
                lErrorMessage.setText("Forkerte oplysninger, prøv igen");
            }
         
        });

        super.addToBottomHBox(createConnection);
        super.display(title);
    }

    private void updateTextFields() {
        String filescan = "xraydb.txt";
        String host = null;
        String port = null;
        String dbNavn = null;
        String user = null;
        String pass = null;
        
        
         String scanfile = "xraydb.txt";
            Scanner textScan = null;

            File sfile = new File(scanfile);
        try {
            textScan = new Scanner(sfile);
        } catch (FileNotFoundException ex) {
            exceptionPopup.display("Der er ingen tekstfil med oplysninger til databasen");
        }
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
            tHost.setText(host);
            tPort.setText(port);
            tDbName.setText(dbNavn);
            tUser.setText(user);
            tPassword.setText(pass);
    }
}
