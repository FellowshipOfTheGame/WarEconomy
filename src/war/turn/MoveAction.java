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
public class MoveAction extends Action{



    @Override
    public void execute() {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public String toString() {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public String getShortDesc() {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }
        
    public MoveAction(PlayerCharacter player, GameCharacter actor, Boolean reschedule) {
        super(player, actor, reschedule);
    }
}
