/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package war.law;

import java.util.ArrayList;
import java.util.Random;
import war.PlayerCharacter;
import war.Region;
import war.World;

/**
 *Classe que gerenciará investigadores e qualquer outra oposição vinda da lei.
 * @author João
 */
public class LawManager {
    private ArrayList<Investigator> investigators;
    
    
    private void spawnInvestigators(int notoriety, World world){
        
        if(9 < notoriety){
        
            
            Random diceRoll = new Random();
            //random.nextInt(max - min + 1) + min
            int result = diceRoll.nextInt(100);            
            
            String name = "Agent " + result;

            String agency;

            Region spawnRegion = world.getRegion(2); //Por enquanto, default é Callisto

            int investigation;
            int loyalty;

            //Agências governamentais locais. Número máximo de investigadores = X
            if(notoriety < 50){ 
                //Stats mínimos de 15 e máximos de 45
                investigation = diceRoll.nextInt(45 - 15 + 1) + 15;
                loyalty = diceRoll.nextInt(45 - 15 + 1) + 15;
                agency = spawnRegion.getName() + " Govt.";
                
                investigators.add(new Investigator(name, agency, spawnRegion, investigation, loyalty ) );
            }

        }
        
        /*
        Método que realizará a decisão de criar ou não novos investigadores,
        baseado no nível de notoriedade e no número de investigadores já em jogo.
        */
    }
    

    public void execute(PlayerCharacter player, World world){
        //Método para fazer os investigadores agirem?
        spawnInvestigators(player.getNotoriety(), world);
    }
    
    public LawManager() {
        this.investigators = new ArrayList<>();
    }
    
    
}
