/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package control.comparators;

import java.util.Comparator;
import model.Room;

/**
 *
 * @author Benjamin
 */
public class RoomMaximumComparator implements Comparator<Room>{

    @Override
    public int compare(Room r1, Room r2) {
        //Sammenlign de to rum på hvor mange pladser der er tilbage i rummet.
        //der hvor der er flest pladser skal sættest forrest i listen.
        int r1Difference = r1.getMaxOccupation() - r1.getCount();
        int r2Difference = r2.getMaxOccupation() - r2.getCount();
        
        int result = r2Difference - r1Difference;
        
        if(result == 0){
            result = r2.getMaxOccupation() - r1.getMaxOccupation();
        }
        
        return result;
    }
    
}

