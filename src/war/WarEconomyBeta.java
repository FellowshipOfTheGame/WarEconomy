/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package war;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.input.KeyCombination;
import javafx.stage.Stage;

/**
 * @author João Victor L. da S. Guimarães
 * @date 09/12/2015
 * @version 0.1 
 * @phase I
 */

public class WarEconomyBeta extends Application {
    
    @Override
    public void start(Stage stage) throws Exception {
        Parent root = FXMLLoader.load(getClass().getResource("/javafxStuff/MainMenu.fxml"));
        
        Scene scene = new Scene(root);
        
        stage.setScene(scene);
        stage.setMaximized(true);
        //stage.setFullScreen(true);//Seta fullscreen
        //stage.setFullScreenExitHint("");//Seta a mensagem de ESC para vazio. ESC ainda tira do fullscreen
        //stage.setFullScreenExitKeyCombination(KeyCombination.NO_MATCH); Desabilita o Esc para sair do fullscreen
        stage.show();
    }

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        launch(args);
    }
    
}
