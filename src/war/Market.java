
package war;

import java.util.ArrayList;

/**
 *
 * @author João
 */
public class Market {
    private ArrayList<Transport> availableTransports;
    private ArrayList<MarketWeapon> availableWeapons;    

    public Market(WeaponDictionary wpnDic/*, TransportDictionary transpDic*/) {
        
        this.availableTransports = new ArrayList<>();
        this.availableWeapons = new ArrayList<>();
        
        for (int i = 0; i<wpnDic.getAllWeapons().size(); i++){
            //inicializa uma MarketWeapon por Weapon do dicionario
        }
        
        
    }
    
    
}
