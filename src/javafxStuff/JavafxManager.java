/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package javafxStuff;

import javafx.collections.ObservableList;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Modality;
import javafx.stage.Stage;
import war.Region;

/**
 *
 * @author João
 */
public class JavafxManager {
    
    

    public static void openBlackMarketWindow(GameController gc, Region reg){
    
            try {
            FXMLLoader fxmlLoader = new FXMLLoader(JavafxManager.class.getResource("BlackMarketFXML.fxml"));
            Parent root1 = (Parent) fxmlLoader.load();
            Stage stage = new Stage();

            //Referencia o controlador do blackmarket para passar a região para inicializar a tabela e valores
            BlackMarketController controller = fxmlLoader.<BlackMarketController>getController();
            controller.initialize(gc, reg);

            stage.initModality(Modality.APPLICATION_MODAL);//Bloqueia outras janelas até fechar essa
            stage.setScene(new Scene(root1));  
            stage.showAndWait();
        } catch(Exception e) {
           e.printStackTrace();
          }
            
    }
    
    
    public static void openEvidencesWindow(ObservableList evidences){
            try {
            FXMLLoader fxmlLoader = new FXMLLoader(JavafxManager.class.getResource("EvidenceFXML.fxml"));
             Parent root1 = (Parent) fxmlLoader.load();
             Stage stage = new Stage();

             //Referencia o controlador da janela de evidencias para passar a região para inicializar a tabela e valores
             EvidenceController controller = fxmlLoader.<EvidenceController>getController();
             controller.initialize(evidences); //passa a lista observavel de evidências na região

             stage.initModality(Modality.APPLICATION_MODAL);//Bloqueia outras janelas até fechar essa
             stage.setScene(new Scene(root1));  
             stage.showAndWait();

        } catch(Exception e) {
           e.printStackTrace();
        }
    }
    
    
    
    
    public void openTransMarket(){
        try {
        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("TransportMarketFXML.fxml"));
                Parent root1 = (Parent) fxmlLoader.load();
                Stage stage = new Stage();
               
                //Referencia o controlador do blackmarket para passar a região para inicializar a tabela e valores
                TransportMarketController controller = fxmlLoader.<TransportMarketController>getController();
                controller.initialize();//Por enquanto só seta para NAFRAN
                
                stage.initModality(Modality.APPLICATION_MODAL);//Bloqueia outras janelas até fechar essa
                stage.setScene(new Scene(root1));  
                stage.showAndWait();
                
        } catch(Exception e) {
           e.printStackTrace();
        }
    }
}
