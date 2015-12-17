
package war;

import java.util.ArrayList;


/**
 *
 * @author João
 */
public class Warehouse {

    private int security;
    private int capacity;
    private ArrayList<PlayerWeapon> wares; //Lista das armas do jogador armazenadas nesse armazem

    public int getSecurity() {
        return security;
    }

    public int getCapacity() {
        return capacity;
    }

    public void upgradeCapacity(){
        //Melhoria da capacidade, temporariamente em incrementos de 5. Sujeito a mudanca
        this.capacity = this.capacity + 5;
    }
    
    
    public Warehouse() {
        this.wares = new ArrayList<>();
        this.capacity = 5;
        this.security = 5;
    }
    
    
}
