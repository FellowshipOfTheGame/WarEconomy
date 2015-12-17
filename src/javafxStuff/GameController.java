/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package javafxStuff;

import java.net.URL;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.MenuButton;
import javafx.scene.control.MenuItem;
import war.PlayerCharacter;
import war.World;

/**
 * FXML Controller class
 * @briefing Classe que controlará a lógica principal do jogo
 * @author João Victor L. da S. Guimarães
 * @date 10/12/2015
 * @version 0.1
 * @phase I
 */

public class GameController implements Initializable {
    
    /*ELEMENTOS DE FXML*/
    @FXML
    Button bt;

    @FXML
    MenuItem trvEmm;
    @FXML
    MenuItem trvOsea;
    @FXML
    MenuItem trvYuk;    
    @FXML
    MenuItem trvWel;    
    
    
    @FXML
    Label guiFunds;
    @FXML
    Label guiHeat;
    @FXML
    Label guiPlayerPos;    
    
/*------------------------------------------------------------------------------*/    
    /*ATRIBUTOS*/
    PlayerCharacter player;
    World world;

/*------------------------------------------------------------------------------*/    
    /*METODOS*/
    
    @FXML
    /*Quando chegar a hora, monta uma trade-mission*/
    public void playerTravel(ActionEvent e){
       
        
        if(e.getSource() == trvEmm)
            player.setCurrentPos(this.world.getRegion(0));
        else if(e.getSource() == trvWel)
            player.setCurrentPos(this.world.getRegion(1));
        else if(e.getSource()==trvYuk)
            player.setCurrentPos(this.world.getRegion(2));
        else if(e.getSource()==trvOsea)
            player.setCurrentPos(this.world.getRegion(3));      
        this.updateEssentialInfo();
    }
    
    /*Atualiza info no HUD*/
    private void updateEssentialInfo(){
        this.guiHeat.setText("HEAT: " + this.player.getHeat());
        this.guiFunds.setText("FUNDS: "+ this.player.getFunds());
        this.guiPlayerPos.setText("CURRENT POSITION: " + this.player.getCurrentPos().getName());
    }
    
    /**
     * Initializes the controller class.
     * Incializa o jogador e outras informações baseada na string que será passada do menu de new/load game. A string é o nome do arquivo de save
     *
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
       this.world = new World();
       this.player = new PlayerCharacter("default",this.world.getRegion(0));
       this.updateEssentialInfo();
    }    
    
}
