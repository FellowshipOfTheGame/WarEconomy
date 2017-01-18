
package war.law;

import java.util.Random;
import war.Evidence;
import war.PlayerCharacter;
import war.Region;
import war.TestManager;

/**
 * Classe para os investigadores que vasculahrão o mundo buscando evidências.
 * 
 * @author João
 */
public class Investigator{
    
    private final String name; //String aleatória alfanumérica para identificar o agente
    private final String agency; //Nome da agência dele. Simbolizando o nível de ameaça
    private final int investigation;//Stat utilizado nos testes de investigação dele
    private final int loyalty;//
    private Region currentRegion;
    //flag booleana usada para determinar se o investigador está investigando evidências
    private Evidence currentInvestigation;
    private int currentInvestigationHits;
    
    public String getName() {
        return name;
    }

    public String getAgency() {
        return agency;
    }

    public int getInvestigation() {
        return investigation;
    }

    public int getLoyalty() {
        return loyalty;
    }

    public Region getCurrentRegion() {
        return currentRegion;
    }

    
    
    
    public String toString() {
        StringBuilder sb = new StringBuilder ();
        sb.append ("Investigator: \"");
        sb.append (getName ());
        sb.append (" from: " );
        sb.append (getAgency());
        sb.append ("\" Position \"");
        sb.append (currentRegion.getName ());
        sb.append ("\".");
        return sb.toString ();
    }

    public void move(){
        if(currentInvestigation == null){
            Random diceRoll = new Random();
            int result = diceRoll.nextInt(currentRegion.getAdjacent().size());
            this.currentRegion = currentRegion.getAdjacent().get(result).getDestination();
            System.out.println("[INVESTIGATOR]: " + getName() + " moveu "  + currentRegion.getName());
        }
    }
    
    public void investigate (PlayerCharacter player){
        if(currentInvestigation == null){//Não esta fazendo nenhuma investigação agora
            
            if(currentRegion.getEvidences().size() != 0){//Existe uma evidência na região
                System.out.println("[INVESTIGATOR]: " + getName() + " INVESTIGANDO EM "  + currentRegion.getName());
                
                for(Evidence evi : currentRegion.getEvidences()){
                    if(evi.getInvestigator() == null){//primeira evidência disponível
                        currentInvestigation = evi;
                        evi.setInvestigator(this);
                        currentInvestigationHits = 0;
                    }
                }
 
            }
        }
        
        else{//Está fazendo alguma investigação agora
            if(TestManager.successTest(investigation, currentInvestigation.getDificultyModifier() ) ){
                currentInvestigationHits++;
                
                if(currentInvestigationHits == currentInvestigation.getRequiredHits()){
                    player.setHeat(true, currentInvestigation.getHeatInc());
                    currentRegion.removeEvidence(currentInvestigation);
                    currentInvestigation = null;
                    currentInvestigationHits = 0;
                }
            }
        }
    }
       
    /**
     * Ctor da classe de investigador.
     * @param name
     * @param agency
     * @param investigation
     * @param loyalty
     * @param currentRegion 
     */
    
    public Investigator(String name, String agency, Region currentRegion, int investigation, int loyalty) {
        this.name = name;
        this.agency = agency;
        this.currentRegion = currentRegion;
        this.investigation = investigation;
        this.loyalty = loyalty;
        this.currentInvestigation = null;
        this.currentInvestigationHits = 0;
        System.out.println("" + this.toString());
    }
}
