/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package javafxStuff;


import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import war.GameCharacter;
import war.PlayerCharacter;
import war.turn.Turn;

/**
 * FXML Controller class
 *
 * @author João
 */
public class AgntAssassinateController {
    @FXML Label stats;  //Usado para mostrar os valores de probabilidade de sucesso, etc.
    //@FXML TextField donation; //Campo para quantidade de fundos a ser usada no suborno.
    @FXML Button executeAssassination;
   // @FXML ComboBox<Investigator> regInvest;
    
    Turn turn;
    GameCharacter actor;
    PlayerCharacter player;
    //Investigator Target;
    
    /***
     * Atualiza os stats baseados nas habilidades do actor, do alvo e na quantidade de dinheiro.
     */
    public void updateStats(){
    
    }
    
    
    /**
     * Initializes the controller class.
     */
    public void initialize(Turn turn, PlayerCharacter player, GameCharacter actor) {
        this.turn = turn;
        this.player = player;
        this.actor = actor;
    }    
    
}
