
package war;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;

/**
 *
 * @author João
 */
public class Market {
    private ArrayList<Transport> availableTransports;
    private HashMap<String, MarketWeapon> availableWeapons;    
    
    
    public int calculateSupply(int geoPolStatus, boolean categoryStatus){
        //Logica que calcula o suprimento 
        return 1;
    }
    public int calculateDemmand(int geoPolStatus, boolean categoryStatus){
        //Logica que calcula a demanda
        return 2;
    }
    
    public Market(int geoPolStatus, boolean mrkStatus[], WeaponDictionary wpnDic/*, TransportDictionary transpDic*/) {
        
        this.availableTransports = new ArrayList<>();
        this.availableWeapons = new HashMap<String, MarketWeapon>();
        
        
        HashMap wMap = (HashMap) wpnDic.getDictionary();
        
        /**
         * Cria um Iterador para iterar pela copia de dictonary Weapon.
         * Inicializa uma MarketWeapon para cada weapon disponível no dicionário.
         * Insere a MarketWeapon com a Key da Weapon equivalente no HashMap Available Weapons
         */
        Iterator it = wMap.entrySet().iterator();
        while(it.hasNext()){
            HashMap.Entry entry = (HashMap.Entry<String, Weapon>)it.next();
            String key = (String)entry.getKey();
            Weapon wpn = (Weapon)entry.getValue();
            
            int sup = calculateSupply(geoPolStatus, mrkStatus[wpn.category]);
            int dem = calculateDemmand(geoPolStatus, mrkStatus[wpn.category]);
            
            MarketWeapon mrktWpn = new MarketWeapon(wpn, sup, dem);
            this.availableWeapons.put(key, mrktWpn);
            
            it.remove();
        }
        
        System.out.println("Market Created");
    }
    
    
}
