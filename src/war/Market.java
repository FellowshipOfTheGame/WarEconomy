
package war;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.Iterator;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

/**
 *
 * @author João
 */
public class Market {
    private ArrayList<Transport> availableTransports;
    private HashMap<String, MarketWeapon> availableWeapons;    
    
    
    /**
     * Retorna o inteiro equivalente da categoria da arma
     * Entre 0-7, segue a ordem do GDD
     */
    public int getCategoryIndex(String catName){
        if(catName.equalsIgnoreCase("Pistols"))
            return 0;
        else if(catName.equalsIgnoreCase("Rifles"))
            return 1;
        else if(catName.equalsIgnoreCase("Explosives"))
            return 2;
        else if(catName.equalsIgnoreCase("Heavy Weapons"))
            return 3;        
        else if(catName.equalsIgnoreCase("Artillery"))
            return 4;
        else if(catName.equalsIgnoreCase("Ground Vehicles"))
            return 5;
        else if(catName.equalsIgnoreCase("Air Vehicles"))
            return 6;
        else if(catName.equalsIgnoreCase("WMDs"))
            return 7;        
        else
            return -1;//Vai causar um erro
    }
    
    private int calculateSupply(int geoPolStatus, boolean categoryStatus){
        //Logica que calcula o suprimento 
        return 2;
    }
    private int calculateDemmand(int geoPolStatus, boolean categoryStatus){
        //Logica que calcula a demanda
        return 2;
    }

    public ArrayList<Transport> getAvailableTransports() {
        return availableTransports;
    }

    public ObservableList<MarketWeapon>getAvailableWeapons() {//Retorna uma observable list para montar a tabela
        Iterator it = this.availableWeapons.entrySet().iterator();
        ObservableList<MarketWeapon> obl = FXCollections.observableArrayList();
        
        while(it.hasNext()){
            HashMap.Entry entry = (HashMap.Entry<String, MarketWeapon>)it.next();
            MarketWeapon wpn = (MarketWeapon)entry.getValue();
            obl.add(wpn);
        }
        
        return obl;
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
            
            int index = getCategoryIndex(wpn.category);
            
            int sup = calculateSupply(geoPolStatus, mrkStatus[index]);
            int dem = calculateDemmand(geoPolStatus, mrkStatus[index]);
            
            MarketWeapon mrktWpn = new MarketWeapon(wpn, sup, dem);
            System.out.println("New Weapon " + mrktWpn.getWpnName());
            this.availableWeapons.put(key, mrktWpn);
            
        }
        
        System.out.println("Market Created");
    }
    
    
}
