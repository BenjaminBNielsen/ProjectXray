/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package view.schema;

import javafx.collections.ObservableList;
import javafx.scene.Node;

/**
 *
 * @author Benjamin
 */
public interface HasChildren {
    
    /**
     * Så man er sikker på at den implementerende klasse har en getChildrenList
     * metode.
     * @return En liste af child Node-objekter.
     */
    public Node[] getChildrenList();
    
}
