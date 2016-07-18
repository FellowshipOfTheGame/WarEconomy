/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package war;

import java.util.Random;
import war.turn.Action;

/**
 *
 * @author João
 */
public class Evidence {
    
    int dificultyModifier;//Modificador negativo ao teste de Intriga para encontra a evidência ou não.
    int requiredHits;
    int timer;
    int heatInc;
    
    boolean isVisible; //Se a evidência foi encontrada ou não.
    Region reg; //A região aonde a evidência está localizada.
    Action cause;//Ação que causou a geração da evidência.




    public Action getCause() {
        return cause;
    }
    
    
    public Class getCauseType() {
        return cause.getClass();
    }    
        /**
     * Método chamado para gerar ou não uma evidência.
     * @return 
     */
    
    
    public static boolean generateEvidence(Region reg, Action cause, int intrigue){
        
        return false;
    }
    
    /*ctor*/

    public Evidence(Region reg, Action cause) {
        this.reg = reg;
        this.cause = cause;
        this.isVisible = false;
        
        Random diceRoll = new Random();
        //Não há uma evidencida do mesmo tipo na região.
        if(reg.getEvidenceByCause(cause) == null){
            
            if (cause == null){//açao de movimento de transporte 
                dificultyModifier = 10;
                //random.nextInt(max - min + 1) + min
                timer = diceRoll.nextInt(6 - 1 + 1) + 1;
                heatInc = diceRoll.nextInt(5 - 1 + 1) + 1;
                requiredHits = diceRoll.nextInt(2 - 1 + 1) + 1; ;                
            }
            
            else{

            }
        }
    
    }
    
    
    
    
}
