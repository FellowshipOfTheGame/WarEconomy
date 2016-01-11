
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
public class Transport implements Storable{
    private String name;
    private int price;
    private String type;//air, land, sea
    private int speed;
    private int noise;
    private int upkeep;
    private Region currentPos;
    private int totalCapacity;
    private int usedCapacity;//Capacidade sendo utilizada pelas armas.    
    private HashMap<String, PlayerWeapon> cargo;//Quais armas serão carregadas em uma trade mission.

    
    @Override
    public void store(Weapon wpn, int qty) {
        //Pressupondo que as armas caberão no transporte. Verificação para isso deve ser feita pelo método buy() de GameController
        PlayerWeapon pwpn = this.cargo.get(wpn.getName());
        
        //Arma ainda não existe no inventário do transporte
        if(pwpn == null){
            pwpn = new PlayerWeapon(wpn, wpn.getSize()*qty, qty);//Cria uma nova arma de jogador.
            this.cargo.put(wpn.getName(), pwpn);
            this.usedCapacity += wpn.getSize()*qty;
            System.out.println("nova PlayerWeapon " + wpn.getName());
        }
        
        //Arma já existe no inventário do transporte, simplesmente aumenta a quantidade, se couber
        else{
            pwpn.setQty(pwpn.getQty() + qty);
            System.out.println("nova PlayerWeapon " + wpn.getName());
        }    
    }

    @Override
    public void remove(String wpnName, int qty) {
        
        PlayerWeapon pwpn = this.cargo.get(wpnName);
        
        this.usedCapacity -= pwpn.getWpn().getSize()*qty;//Libera Espaço        
        
        if(pwpn.getQty() - qty == 0){ //Não haverá mais dessas armas no estoque
            this.cargo.remove(wpnName); //Remove do Map
            System.out.println("Removendo " + wpnName +  " do Map");
        }
        else
            pwpn.setQty(pwpn.getQty() - qty);

    }
    
    @Override
    public int getTotalCapacity() {
        return totalCapacity;
    }

    @Override
    public int getUsedCapacity() {
        return usedCapacity;
    }

    @Override
    public void setUsedCapacity(int usedCapacity) {
        this.usedCapacity = usedCapacity;
    }

    @Override
    public Region getCurrentPos() {
        return currentPos;
    }

    @Override
    public int getWeaponQuantity(String wpnName) {
        //Pesquisa no HashMap pela arma
        PlayerWeapon wpn = this.cargo.get(wpnName);
        if(wpn == null){
            return 0;
        }
        else{
            return wpn.getQty();
        }
    }

    public int getUpkeep() {
        return upkeep;
    }

    @Override
    public String getName() {
        return name;
    }
    
    

    @Override
    public ObservableList<PlayerWeapon> getWeapons() {
        Iterator it = this.cargo.entrySet().iterator();
        ObservableList<PlayerWeapon> obl = FXCollections.observableArrayList();
        
        while(it.hasNext()){
            HashMap.Entry entry = (HashMap.Entry<String, PlayerWeapon>)it.next();
            PlayerWeapon wpn = (PlayerWeapon)entry.getValue();
            obl.add(wpn);
        }
        
        return obl;
    }

    
    
    @Override
    public String toString() {
        return this.name;
    }
    
        public Transport(String name, int price, String type, int speed, int noise, int upkeep, Region currentPos, int totalCapacity){
        this.name = name;
        this.price = price;
        this.type = type;
        this.speed = speed;
        this.noise = noise;
        this.upkeep = upkeep;
        this.currentPos = currentPos;
        this.totalCapacity = totalCapacity;
        this.usedCapacity = 0;
        this.cargo = new HashMap<>();

    }

}
