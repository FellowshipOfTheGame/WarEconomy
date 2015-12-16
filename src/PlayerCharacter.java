
package war.economy.beta;

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
    /*
    private ArrayList<Warehouses> warehouses
    private ArrayList<Transports> transports
    */

    public int getFunds() {
        return funds;
    }

    public int getHeat() {
        return heat;
    }

    public void setFunds(int funds) {
        this.funds = funds;
    }

    public void setHeat(int heat) {
        this.heat = heat;
    }

    
    
    
    /*
    Construtor para new game
    @param string para o nome
    @param Posicao de inicio do jogador
    */
    public PlayerCharacter(String name, Region startingPos) {
        this.funds = 0;
        this.heat = 0;
        this.intrigue = 0;
        this.barter = 0;
        this.name = name;
        this.currentPos = startingPos;
        System.out.println("New player " + name + " character chreated at " + startingPos.getName());
    }

    
    
}
