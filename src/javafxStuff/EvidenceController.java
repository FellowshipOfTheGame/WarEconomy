/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package javafxStuff;


import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.TableView;
import war.Evidence;

/**
 * FXML Controller class
 *
 * @author João
 */
public class EvidenceController{
    
    @FXML TableView<Evidence> evidenceTable;//Inventory, Left Storable Cargo Table

    

    /**myTableView.setPlaceholder(new Label("My table is empty message"));
     * Initializes the controller class.
     */
    public void initialize(ObservableList<Evidence> evidences) {
        if(evidences == null)
           evidenceTable.setPlaceholder(new Label("There are no traces of evidences within this region")) ;
    }    
    
}
