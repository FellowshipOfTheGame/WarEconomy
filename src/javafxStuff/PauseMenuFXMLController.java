/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package javafxStuff;

import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.control.Button;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;


/**
 * FXML Controller class
 *
 * @author João
 */
public class PauseMenuFXMLController {

    GameController gm;
    
    @FXML
    Button btn; //Usado para referenciar o controle
    @FXML
    AnchorPane wnd;
    
    @FXML
    public void goToMainMenu(){
        
        Parent root = null; //Referencia a outra tela
        Stage sb = (Stage)gm.wnd.getScene().getWindow();//Referencia ao Stage atual
        String filename = "MainMenu.fxml";
        
        try {
            root = FXMLLoader.load(getClass().getResource(filename));
        } catch (IOException ex) {
            Logger.getLogger(MainMenuController.class.getName()).log(Level.SEVERE, null, ex);
        }
        sb.getScene().setRoot(root);
        
        sb = (Stage)btn.getScene().getWindow();
        sb.close();
    }
    
    public void saveGame(){
    
    }
    
    public void loadGame() {
    
    }
    
    public void quitGame() {
        
        Stage sb = (Stage)gm.wnd.getScene().getWindow();//use any one object
        sb.close();
        sb = (Stage)btn.getScene().getWindow();
        sb.close();
    }
    
    /**
     * Initializes the controller class.
     * @param gm Controlador principal do jogo
     */
    public void initialize(GameController gm) {
        this.gm = gm;
        
        wnd.addEventFilter(KeyEvent.KEY_PRESSED, (KeyEvent k) -> {
            //close window
            if(k.getCode() == KeyCode.ESCAPE){
                Stage sb = (Stage)btn.getScene().getWindow();//use any one object
                sb.close();
            }
        });
        
    }    
    
}
