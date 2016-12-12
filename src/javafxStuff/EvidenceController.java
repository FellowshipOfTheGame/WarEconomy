/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package javafxStuff;


import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import war.Evidence;

/**
 * FXML Controller class
 *
 * @author João
 */
public class EvidenceController {
    
    @FXML TableView<Evidence> evidenceTable;//Inventory, Left Storable Cargo Table
    @FXML TableColumn<Evidence, String> causeColumn;
    @FXML TableColumn<Evidence, String> diffColumn;
    @FXML TableColumn<Evidence, String> riskColumn;
    @FXML TableColumn<Evidence, String> timeColumn;
    
    @FXML 
    
    Evidence target;


    /**myTableView.setPlaceholder(new Label("My table is empty message"));
     * Initializes the controller class.
     */
    public void initialize(ObservableList<Evidence> evidences) {
        target = null;
        
        if(evidences == null)
           evidenceTable.setPlaceholder(new Label("There are no traces of evidences within this region")) ;
        else{ //inicializa a tabela
            evidences.stream().forEach((e) -> {
                e.printEvidence();
            });
            
            causeColumn.setCellValueFactory(new PropertyValueFactory<>("causeString"));
            diffColumn.setCellValueFactory(new PropertyValueFactory<>("difficultyString"));
            riskColumn.setCellValueFactory(new PropertyValueFactory<>("heatString"));
            timeColumn.setCellValueFactory(new PropertyValueFactory<>("timerString"));
            
            evidenceTable.setItems(evidences);

        }
    }    
    
}
