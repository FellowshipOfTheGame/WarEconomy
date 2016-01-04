
package war;

import java.util.ArrayList;

/**
 * FXML Controller class
 * @briefing Classe do personagem do jogador
 * @author João Victor L. da S. Guimarães
 * @date 10/12/2015
 * @version 0.1
 * @phase I
 */

public class PlayerCharacter extends Character{
    private int funds;
    private int heat;
    
    private ArrayList<Warehouse> warehouses;
    private ArrayList<Agent> agents;
    private ArrayList<Transport> transports;
    

    public int getFunds() {
        return funds;
    }

    public int getHeat() {
        return heat;
    }

    public void subtractFunds(int value) {
        int funds = this.getFunds() - value;
        this.funds = funds;
        System.out.println("New funds:" + this.funds);
    }
    
    public void addFunds(int value) {
        int funds = this.getFunds() + value;
        this.funds = funds;
    }
    
    public void setHeat(int heat) {
        this.heat = heat;
    }

    
    
    
    /*
    Construtor para NEW GAME
    @param string para o nome
    @param Posicao de inicio do jogador
    */
    public PlayerCharacter(String name, Region startingPos) {
        this.funds = 100;
        this.heat = 0;
        this.intrigue = 0;
        this.barter = 0;
        this.name = name;
        this.currentPos = startingPos;
        
        this.warehouses = new ArrayList<Warehouse>();
        this.agents = new ArrayList<Agent>();
        this.transports = new ArrayList<Transport>();
        
        //Constroi a warehouse da regiao inicial do jogador e a inclui na lista de Warehouses
        boolean buildWarehouse = startingPos.buildWarehouse(); 
        System.out.println("buildWarehouse: " + buildWarehouse);
        this.warehouses.add(startingPos.getLocalWarehouse());
        
        //Constroi e insere o primeiro transporte do jogador.
        Transport t = new Transport("Truck",0,"land",1, 1, 1, startingPos);//TEMPORARIO
        this.transports.add(t);
        
        System.out.println("New player " + name + " character chreated at " + startingPos.getName());
    }

    
    
}
