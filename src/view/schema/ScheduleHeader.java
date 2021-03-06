package view.schema;

import javafx.collections.ObservableList;
import javafx.geometry.Orientation;
import javafx.scene.Node;
import javafx.scene.control.Label;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Priority;
import javafx.scene.layout.TilePane;

/**
 * Indeholder en række med ugens dage fra mandag til fredag. Der skal altså være
 * 6 tiles i alt. I den første skal der stå hvilken uge skemaet gælder for. I
 * resten skal der stå Mandag, Tirsdag, ..., Fredag. OBS: Lørdag og søndag skal
 * ikke med i headeren, fordi de vagter der gælder i weekender aldrig er tildelt
 * rum, så lørdag og søndagsvagter vil blive tilføjet som rækker i bunden af
 * skemaet.
 *
 * @author Benjamin
 */
public class ScheduleHeader extends HBox implements HasChildren{

    //Opslagstabel til at få navne på ugedagene.
    public static final String[] WEEK_DAY_NAMES
            = {"MANDAG", "TIRSDAG", "ONSDAG", "TORSDAG", "FREDAG", "LØRDAG", "SØNDAG"};

    public ScheduleHeader(int week) {

        RoomTile weekLabel = new RoomTile("UGE " + week);

        this.getChildren().add(weekLabel);

        //Tilføj labels for hver dag på ugen mandag-fredag:
        for (int i = 0; i < 5; i++) {
            this.getChildren().add(new RoomTile(WEEK_DAY_NAMES[i]));
        }
        
    }
    
    @Override
    public Node[] getChildrenList(){
        ObservableList<Node> sliChildren = this.getChildren();

            Node[] childrenAsArray = new Node[sliChildren.size()];

            for (int i = 0; i < sliChildren.size(); i++) {
                childrenAsArray[i] = sliChildren.get(i);
            }
        return childrenAsArray;
    }
}
