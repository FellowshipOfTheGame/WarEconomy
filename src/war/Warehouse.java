
package war;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;


/**
 *
 * @author João
 */
public class Warehouse implements Storable{

    private int security;
    private int upkeep;
    private int totalCapacity;
    private int usedCapacity;//Capacidade sendo utilizada pelas armas.
    private HashMap<String, PlayerWeapon> wares;     //Lista das armas do jogador armazenadas nesse armazem
    private final Region region; //Final, imutável. Variável usada para a tab de inventário.

    public int getSecurity() {
        return security;
    }
    
    @Override
    public int getTotalCapacity() {
        return totalCapacity;
    }

    public void upgradeCapacity(){
        //Melhoria da capacidade, temporariamente em incrementos de 5. Sujeito a mudanca
        this.totalCapacity = this.totalCapacity + 5;
        this.upkeep = this.upkeep + 5;
    }

    @Override
    public int getUsedCapacity() {
        return usedCapacity;
    }

    @Override
    public void setUsedCapacity(int usedCapacity) {
        this.usedCapacity = usedCapacity;
    }

    public int getUpkeep() {
        return upkeep;
    }
    
    @Override
    public String toString() {
        return "Local Warehouse";
    }
    
    
    @Override
    public int getWeaponQuantity(String wpnName) {
        //Pesquisa no HashMap pela arma
        PlayerWeapon wpn = this.wares.get(wpnName);
        if(wpn ==null){
            return 0;
        }
        else{
            return wpn.getQty();
        }
    }
    
    @Override
    public Region getCurrentPos() {
        return region;
    }
    
    @Override
    public String getName(){
        return "Local Warehouse";
    }
    
    @Override
    public void store(Weapon wpn, int qty) {
        //Pressupondo que as armas caberão no armazém. Verificação para isso deve ser feita pelo método buy() de GameController
        PlayerWeapon pwpn = this.wares.get(wpn.getName());
        
        if(wpn.getSize() * qty + usedCapacity > totalCapacity){
            System.err.println("INSUFFICIENT CARGO SPACE");
            return;
        }
        
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

    @Override
    public void remove(String wpnName, int qty) {
        
        PlayerWeapon pwpn = this.wares.get(wpnName);
        this.usedCapacity -= pwpn.getWpn().getSize()*qty;//Libera Espaço
        
        if(pwpn.getQty() - qty == 0){ //Não haverá mais dessas armas no estoque
            this.wares.remove(wpnName); //Remove do Map
            
            System.out.println("Removendo " + wpnName +  " do Map");
        }
        else
            pwpn.setQty(pwpn.getQty() - qty);
    }
    
    
    @Override
    public ObservableList<PlayerWeapon> getWeapons() {
        Iterator it = this.wares.entrySet().iterator();
        ObservableList<PlayerWeapon> obl = FXCollections.observableArrayList();
        
        while(it.hasNext()){
            HashMap.Entry entry = (HashMap.Entry<String, PlayerWeapon>)it.next();
            PlayerWeapon wpn = (PlayerWeapon)entry.getValue();
            obl.add(wpn);
        }
        
        return obl;
    }
    
    @Override
    public String getDescriptionInfo() {
        return("LOCAL WAREHOUSE" + "\nUpkeep: " + this.upkeep + "\nSecurity: " + security + "\nCapacity: " + usedCapacity + "/" + totalCapacity);
    }

    
    
    public Warehouse(Region region) {
        this.wares = new HashMap<String, PlayerWeapon>();
        this.totalCapacity = 5;
        this.usedCapacity = 0;
        this.upkeep = 5;
        this.security = 5;
        this.region = region;
    }


}
