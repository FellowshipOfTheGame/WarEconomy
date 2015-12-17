
package javafxStuff;

import javafxStuff.MainMenuController;
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
import javafx.scene.control.TextField;
import javafx.stage.Stage;

/**
 * FXML Controller class
 * @briefing Classe controladora do menu de novo jogo
 * @author João Victor L. da S. Guimarães
 * @date 09/12/2015
 * @version 0.1
 * @phase I
 */
public class NewGameController implements Initializable {
    @FXML
    Button newGamebtn;
    @FXML
    Button mainMenubtn;
    @FXML
    TextField txt;
    
    @FXML
    /**
     * Cria um novo arquivo de save (xml, talvez). Então muda para a cena do jogo em si.
     * O metodo de Initialize do controlador da cena de jogo então lerá o arquivo e criará o objeto do jogador.
     */
    void startGame(){
        /*TODO - Criação de save*/
        /*Switch para o jogo em si*/
        Parent root = null;
        Stage stg = (Stage)newGamebtn.getScene().getWindow();
        String filename = "Game.fxml";
        
        try {
        root = FXMLLoader.load(getClass().getResource(filename));
        } catch (IOException ex) {
            Logger.getLogger(MainMenuController.class.getName()).log(Level.SEVERE, null, ex);
        }
        
        /*Scene game = new Scene(root);
        //Cria a cena do novo jogo
        stg.hide();
        stg.setScene(game);
        stg.show();*/
        
        stg.getScene().setRoot(root);
    }
    

    public void switchToMainMenu(ActionEvent event){
        
        Parent root = null; //Referencia a outra tela
        Stage stg = (Stage)mainMenubtn.getScene().getWindow();//Referencia ao Stage atual
        String filename = "MainMenu.fxml";

        try {
        root = FXMLLoader.load(getClass().getResource(filename));
        } catch (IOException ex) {
            Logger.getLogger(MainMenuController.class.getName()).log(Level.SEVERE, null, ex);
        }
        
        stg.getScene().setRoot(root);
    }
    
    
    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
    }    
    
}
