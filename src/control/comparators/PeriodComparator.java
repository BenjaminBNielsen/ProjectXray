/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package control.comparators;

import java.util.Comparator;
import model.Employee;
import model.Room;
import control.RoomAssignmentCounter;
import model.TimePeriod;

/* @author Benjamin */
public class PeriodComparator implements Comparator<TimePeriod> {

    private Employee employee;
    
    public PeriodComparator(Employee employee){
        this.employee = employee;
    }
    
    @Override
    public int compare(TimePeriod t1, TimePeriod t2) {
        //Find room assignment counteren for de to rum der er sluttet til de 
        //to tidsperioder.
        Room t1Room = t1.getRoom();
        RoomAssignmentCounter t1Rac = employee.getRoomAssignmentCounter(t1Room);

        Room t2Room = t2.getRoom();
        RoomAssignmentCounter t2Rac = employee.getRoomAssignmentCounter(t2Room);
        
        //Sammenlign først på forskellen mellem t1 og t2 minimum, i forhold til
        //de passende counts:
        int t1Difference = t1.getMin() - t1Rac.getCount();
        int t2Difference = t2.getMin() - t2Rac.getCount();
        int r = 0;

        //Hvis minimum ikke er opnået for en af de to forskelle:
        if (t1Difference > 0 && t2Difference > 0) {
            r = t2Difference - t1Difference;
            if (r == 0) {
                r = t2Rac.getCount() - t1Rac.getCount();

                if (r == 0) {
                    r = t2.getMin() - t1.getMin();

                }
            }
            //Hvis minimum er opnået, tag da højde for de definerede maximummer.
        } else {
            r = t2Difference - t1Difference;

            if (r == 0) {

                r = t2Rac.getCount() - t1Rac.getCount();
                if (r == 0) {
                    r = t2.getMax() - t1.getMax();
                }
            }
        }

        return r;
    }

}
