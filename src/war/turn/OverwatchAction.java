/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package war.turn;

import war.GameCharacter;
import war.PlayerCharacter;

/**
 *
 * @author João
 */
public class OverwatchAction extends Action{



    @Override
    public void execute() {
        
        int origOp = this.actor.getCurrentPos().getOpRisk();
        System.out.println("Overwatch: origOP = " + origOp);
        int newOp = origOp - this.actor.getIntrigue();
        this.actor.getCurrentPos().setOpRisk(newOp);
        System.out.println("Overwatch: newOP = " + actor.getCurrentPos().getOpRisk());

    }

    @Override
    public String toString() {
		StringBuilder sb = new StringBuilder ();
		sb.append ("Overwatch: \"");
		sb.append (actor.getName ());
		sb.append ("\" vigiando \"");
		sb.append (actor.getCurrentPos().getName ());
		return sb.toString ();
    }

    @Override
    public String getShortDesc() {
        return ("Overwatch");
    }
    
    
    /**
     * Ctor
     *
     *
     * @param player Jogador
     * @param actor Qual personagem que tá executando a ação
     */
    public OverwatchAction(PlayerCharacter player, GameCharacter actor) {
        super(player, actor);
    }
}
