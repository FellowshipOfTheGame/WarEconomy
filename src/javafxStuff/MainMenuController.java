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
import javafx.scene.control.Label;
import javafx.stage.Stage;

/**
 * FXML Controller class
 * @briefing Classe controladora do menu principal
 * @author João Victor L. da S. Guimarães
 * @date 09/12/2015
 * @version 0.1
 * @phase I
 */


public class MainMenuController implements Initializable {
    
    @FXML
    private Button btn_ng;//New Game
    @FXML
    private Button btn_lg;//Load Game
    @FXML
    private Button btn_tut;//Tutorials
    @FXML
    private Button btn_opt;//Options
    @FXML
    private Button btn_qt;//Quit
    

    
    @FXML
    /**
    *Metodo que troca de tela
    */
    private void switchScreen(ActionEvent event){
        
        Parent root = null; //Referencia a outra tela
        Stage stg = (Stage)btn_ng.getScene().getWindow();//Referencia ao Stage atual
        String filename = "MainMenu.fxml";//Por default, "aponta" para o proprio menu
        if(event.getSource()==btn_ng){//Criar novo jogo
            filename = "NewGame.fxml";
        }
        else if(event.getSource()==btn_lg){//Carregar jogo
        }
        else if(event.getSource()==btn_tut){//Carregar Tutorial
            filename = "Tut.fxml";
        }
        else{//Options
        }
        
        try {
        root = FXMLLoader.load(getClass().getResource(filename));
        } catch (IOException ex) {
            Logger.getLogger(MainMenuController.class.getName()).log(Level.SEVERE, null, ex);
        }
        stg.getScene().setRoot(root);
        
        /*Esse codigo comentado cria uma nova cena
        Scene scn = new Scene(root);
        stg.setScene(scn);
        stg.setMaximized(true);
        stg.show();*/
        
    }
    
    /**
    *Metodo que fecha o jogo
    */
    @FXML
    public void quit(){
        Stage stg = (Stage)btn_qt.getScene().getWindow();//Referencia ao Stage atual
        System.out.println("closed");
        stg.close();
    }
    
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
    }    
    
}
