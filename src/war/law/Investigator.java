
package war.law;

import war.GameCharacter;
import war.Region;

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
        
        System.out.println("" + this.toString());
    }
}
