/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package control.comparators;

import control.Xray;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.logging.Level;
import java.util.logging.Logger;
import model.Room;
import model.RoomQualification;
import model.TimeInvestment;

/* @author Benjamin */
public class RoomAmountComparator implements Comparator<TimeInvestment> {

    private ArrayList<RoomQualification> roomQualifications;

    public RoomAmountComparator(ArrayList<RoomQualification> roomQualifications) {
        this.roomQualifications = roomQualifications;
    }

    @Override
    public int compare(TimeInvestment s1, TimeInvestment s2) {
        //Sammenlign først de to vagters datoer.
        int result = s1.getStartTime().compareTo(s2.getStartTime());
        if (result == 0) {
            //Hvis datoerne passer så sammenlign timer.
            result = s1.getHours().compareTo(s2.getHours());
            if (result == 0) {
                //Hvis timerne passer, så sammenlign minutter.
                result = s1.getMinutes().compareTo(s2.getMinutes());
                if (result == 0) {
                    //Find dernæst antallet af de rum som de to medarbejdere der 
                    //hører til de to vagter har adgang til igennem sine kvalifikationer.
                    ArrayList<Room> firstEmpRooms;
                        firstEmpRooms = Xray.getInstance().getEmployeeRooms(s1.getEmployee(), roomQualifications);
                    ArrayList<Room> nextEmpRooms = Xray.getInstance().getEmployeeRooms(s2.getEmployee(), roomQualifications);
                    int firstQualAmount = firstEmpRooms.size();
                    int nextQualAmount = nextEmpRooms.size();

                    //Sammenlign den første medarbejders mængde rum med den anden medarbejder.
                    result = firstQualAmount - nextQualAmount;

                    //Hvis de er ens så sorter efter fornavn.
                    if (result == 0) {
                        result = s1.getEmployee().getFirstName().compareTo(s2.getEmployee().getFirstName());
                        //Hvis fornavnene er ens, så sorter efter efternavn.
                        if (result == 0) {
                            result = s1.getEmployee().getLastName().compareTo(s2.getEmployee().getLastName());
                        }
                    }
                }
            }
        }
        return result;
    }

}
