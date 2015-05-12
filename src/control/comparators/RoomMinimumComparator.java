/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package control.comparators;

import java.util.Comparator;
import model.Employee;
import model.Room;

/**
 *
 * @author Benjamin
 */
public class RoomMinimumComparator implements Comparator<Room> {

    private Employee employee;
    
    public RoomMinimumComparator(Employee employee){
        this.employee = employee;
    }
    
    @Override
    public int compare(Room r1, Room r2) {
        int result = 0;

        //Finder ud af hvor forskellen på antal af allokeringer af rummet, og dens minimumKapacitet.
        int r1Difference = r1.getMinOccupation() - r1.getCount();
        int r2Difference = r2.getMinOccupation() - r2.getCount();

        result = r2Difference - r1Difference;

        if (result == 0) {
            //Sammenlign antal allokeringer.
            result = r2.getCount() - r1.getCount();
            
            if(result == 0){
                //Sammenlign minimumKapacitet, størst minimum kommer først.
                result = r2.getMinOccupation() - r1.getMinOccupation();
            }
        }

        return result;
    }

}
