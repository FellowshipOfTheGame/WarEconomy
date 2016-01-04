/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package war;

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
    
    public int getTotalCapacity();

    public int getUsedCapacity();

    public void setUsedCapacity(int usedCapacity);
    
}
