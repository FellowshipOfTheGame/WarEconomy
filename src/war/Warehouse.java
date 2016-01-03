
package war;

import java.util.ArrayList;
import java.util.HashMap;


/**
 *
 * @author João
 */
public class Warehouse implements Storable{

    private int security;
    private int totalCapacity;
    private int usedCapacity;//Capacidade sendo utilizada pelas armas.
    private HashMap<String, PlayerWeapon> wares;     //Lista das armas do jogador armazenadas nesse armazem

    public int getSecurity() {
        return security;
    }

    public int getTotalCapacity() {
        return totalCapacity;
    }

    public void upgradeCapacity(){
        //Melhoria da capacidade, temporariamente em incrementos de 5. Sujeito a mudanca
        this.totalCapacity = this.totalCapacity + 5;
    }

    public int getUsedCapacity() {
        return usedCapacity;
    }

    public void setUsedCapacity(int usedCapacity) {
        this.usedCapacity = usedCapacity;
    }
    
    
    @Override
    public void store(Weapon wpn, int qty) {
        //Pressupondo que as armas caberão no armazém. Verificação para isso deve ser feita pelo método buy() de GameController
        PlayerWeapon pwpn = this.wares.get(wpn.getName());
        
        //Arma ainda não existe no inventário do armazém
        if(pwpn == null){
            pwpn = new PlayerWeapon(wpn, wpn.getSize()*qty, qty);//Cria uma nova arma de jogador.
            this.wares.put(wpn.getName(), pwpn);
            this.usedCapacity += wpn.getSize()*qty;
            System.out.println("nova PlayerWeapon " + wpn.getName());
        }
        
        //Arma já existe no inventário do armazém, simplesmente aumenta a quantidade, se couber
        else{
            pwpn.setQty(pwpn.getQty() + qty);
            System.out.println("nova PlayerWeapon " + wpn.getName());
        }    
    }

    
    public Warehouse() {
        this.wares = new HashMap<String, PlayerWeapon>();
        this.totalCapacity = 5;
        this.usedCapacity = 0;
        this.security = 5;
    }


    
}
