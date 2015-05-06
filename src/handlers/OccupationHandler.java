    /*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package handlers;

import dbc.DatabaseConnection;
import exceptions.DatabaseException;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import model.Occupation;

/**
 *
 * @author Benjamin
 */
public class OccupationHandler {

    private static OccupationHandler instance;

    public static OccupationHandler getInstance() {
        if (instance == null) {
            instance = new OccupationHandler();
        }
        return instance;
    }
    
    public ObservableList<Occupation> getOccupations() throws DatabaseException {
        ObservableList<Occupation> occupations = FXCollections.observableArrayList();
        try {
        Statement stmt = DatabaseConnection.getInstance().getConnection().createStatement();
        
        String sql = "select * from occupation";
        
        ResultSet rs = stmt.executeQuery(sql);
        
        while(rs.next()){
            int id = rs.getInt("id");
            String description = rs.getString("description");
            
            occupations.add(new Occupation(id, description));
        }
        
        for (Occupation occupation : occupations) {
            System.out.println(occupation);
        }
        return occupations;
        } catch(SQLException ex) {
            throw new DatabaseException("Der kunne ikke hentes nogle job.");
        }
    }
    
    public Occupation getOccupation(int id) throws DatabaseException {
        Occupation occupation = null;
        try {
        Statement stmt = DatabaseConnection.getInstance().getConnection().createStatement();
        
        String sql = "select * from occupation where id = " + id;
        
        ResultSet rs = stmt.executeQuery(sql);
        
        if (rs.next()) {
            String desc = rs.getString("description");
            occupation = new Occupation(id, desc);
        }
        
        
        return occupation;
        } catch(SQLException ex) {
            throw new DatabaseException("Der blev ikke fundet noget job med det ID. ");    
        }
    }
}
