/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package war.turn;

import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafxStuff.EvidenceController;
import javafxStuff.JavafxManager;
import war.GameCharacter;
import war.PlayerCharacter;

/**
 *
 * @author João
 */
public class SearchDestroyAction extends Action{
    
    int turn;
    

    @Override
    public void execute() {
        
        if(turn == 0){//Primeiro turno de execução
            turn++;
            actor.getCurrentPos().getEvidences();
            
            JavafxManager.openEvidencesWindow(actor.getCurrentPos().getEvidences());
            
            
            /**
             * Setar todas as evidencias na região como visíveis
             * Iniciar o pop-up com a listagem das evidências na região
             * 
             */
            
            
        }
        else{//
        /***
         * Realizar Testes para destruição de evidência
         * Se destruiu a evidência, zere os turnos e abra de novo ?
         */
            turn++;
        }

    }

    @Override
    public String toString() {
		StringBuilder sb = new StringBuilder ();
		sb.append ("Search And Destroy Evidences: \"");
		sb.append (actor.getName ());
		sb.append ("\" investigating \"");
		sb.append (actor.getCurrentPos().getName ());
		return sb.toString ();
    }

    @Override
    public String getShortDesc() {
        return ("Searching and Destroying Evidences");
    }
    
    public SearchDestroyAction(PlayerCharacter player, GameCharacter actor) {
        super(player, actor, true);
        turn = 0;
    }

}
