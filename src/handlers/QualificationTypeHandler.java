/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package handlers;

import dbc.DatabaseConnection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import javafx.collections.ObservableList;
import model.Qualification;
import model.QualificationType;

/**
 *
 * @author Yousef
 */
public class QualificationTypeHandler {

    private static QualificationTypeHandler instance;
    private ArrayList<QualificationType> qualificationTypes;

    public void addQualificationTypes(ObservableList<QualificationType> qualificationTypes)
            throws SQLException {
        Statement stmt = DatabaseConnection.getInstance().getConnection().createStatement();

        String sql = "insert into qualificationType() values";
        for (int i = 0; i < qualificationTypes.size(); i++) {

            int id = qualificationTypes.get(i).getId();
            String type = qualificationTypes.get(i).getType();

            sql += "(" + id + ",'" + type + "'";
            if (i < qualificationTypes.size() - 1) {
                sql += "),";
            } else {
                sql += ");";
            }

        }
        stmt.execute(sql);
        stmt.close();
    }
    
    public ArrayList<QualificationType> getQualificationTypes() throws SQLException, ClassNotFoundException {

        Statement stmt = DatabaseConnection.getInstance().getConnection().createStatement();

        String sql = "select * from qualificationtype;";

        ResultSet rs = stmt.executeQuery(sql);

        while (rs.next()) {
            int id = rs.getInt("id");
            String type = ("type");

            qualificationTypes.add(new QualificationType(id, type));
        }

        rs.close();
        stmt.close();

        return qualificationTypes;
    }

    public static QualificationTypeHandler getInstance() {
        if (instance == null) {
            instance = new QualificationTypeHandler();
        }
        return instance;
    }

}
