package view.schema;

import javafx.geometry.Orientation;
import javafx.scene.control.Label;
import javafx.scene.layout.TilePane;

/**
 * Indeholder en række med ugens dage fra mandag til fredag. Der skal altså være
 * 6 tiles i alt. I den første skal der stå hvilken uge skemaet gælder for.
 * I resten skal der stå Mandag, Tirsdag, ..., Fredag.
 * OBS: Lørdag og søndag skal ikke med i headeren, fordi de vagter der gælder i
 * weekender aldrig er tildelt rum, så lørdag og søndagsvagter vil blive tilføjet
 * som rækker i bunden af skemaet.
 * @author Benjamin
 */
public class ScheduleHeader extends TilePane{
    
    private int week;
    
    //Opslagstabel til at få navne på ugedagene.
    public static final String[] WEEK_DAY_NAMES = 
    {"MANDAG", "TIRSDAG", "ONSDAG","TORSDAG","FREDAG"};
    
    public ScheduleHeader(int week){
        this.week = week;
        this.setOrientation(Orientation.HORIZONTAL);
        LabelTile weekLabel = new LabelTile("UGE " + week);
        
        this.getChildren().add(weekLabel);
        
        //Tilføj labels for hver dag på ugen mandag-fredag:
        for (int i = 0; i < 5; i++) {
            this.getChildren().add(new LabelTile(WEEK_DAY_NAMES[i]));
        }
        
    }
}
