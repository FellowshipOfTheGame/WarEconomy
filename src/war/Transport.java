
package war;

import java.util.ArrayList;

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
    private ArrayList<PlayerWeapon> cargo;//Quais armas serão carregadas em uma trade mission.

    @Override
    public void store(Weapon wpn, int qty) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
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

    public Region getCurrentPos() {
        return currentPos;
    }

    
    
    
    
    @Override
    public String toString() {
        return this.name + " (" + this.usedCapacity + "/" + this.totalCapacity + " space used)";
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
        this.cargo = new ArrayList<>();

    }
}
