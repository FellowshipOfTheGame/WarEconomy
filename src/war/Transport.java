
package war;

import java.util.ArrayList;

/**
 *
 * @author João
 */
public class Transport {
    private String name;
    private int price;
    private String type;//air, land, sea
    private int speed;
    private int noise;
    private int upkeep;
    private Region currentPos;
    private ArrayList<PlayerWeapon> cargo;//Quais armas serão carregadas em uma trade mission.

    public Transport(String name, int price, String type, int speed, int noise, int upkeep, Region currentPos) {
        this.name = name;
        this.price = price;
        this.type = type;
        this.speed = speed;
        this.noise = noise;
        this.upkeep = upkeep;
        this.currentPos = currentPos;
        this.cargo = new ArrayList<>();
    }
    

    
}
