
package javafxStuff;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.stage.Stage;

/**
 * FXML Controller class
 * @briefing Classe controladora do menu de tutorial
 * @author João Victor L. da S. Guimarães
 * @date 09/12/2015
 * @version 0.1
 * @phase I
 */


public class TutController implements Initializable {
    
    @FXML
    Button mainMenubtn;
    
    
    /**
    *Metodo que retorna ao menu principal
    */
    @FXML
    public void switchToMainMenu(ActionEvent event){
        
        Parent root = null; //Referencia a outra tela
        Stage stg = (Stage)mainMenubtn.getScene().getWindow();//Referencia ao Stage atual
        String filename = "MainMenu.fxml";//Por default, "aponta" para o proprio menu

        try {
        root = FXMLLoader.load(getClass().getResource(filename));
        } catch (IOException ex) {
            Logger.getLogger(MainMenuController.class.getName()).log(Level.SEVERE, null, ex);
        }
        
        stg.getScene().setRoot(root);
        /*
        Scene scn = new Scene(root);
        stg.setScene(scn);
        stg.setMaximized(true);
        stg.show();
        */
    }
    
    
    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
    }    
    
}
