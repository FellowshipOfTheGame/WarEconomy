/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package war;

import java.util.HashMap;
import java.util.Iterator;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

/**
 *
 * @author João
 */
public interface Storable {
    
     /**
     * Armazena as armas compradas. 
     * 
     * @param wpn arma comprada
     * @param qty quantidade comprada
     */
    
    public void store(Weapon wpn, int qty);
    
    public void remove(String wpnName, int qty);//Remove armas do estoque
    
    public int getTotalCapacity();

    public int getUsedCapacity();

    public void setUsedCapacity(int usedCapacity);
    
    public int getWeaponQuantity(String wpnName);
    
    public ObservableList<PlayerWeapon> getWeapons();
    
    //Ambos métodos abaixo são necessários para a tab de inventário
    public String getName();
    public Region getCurrentPos();
}
