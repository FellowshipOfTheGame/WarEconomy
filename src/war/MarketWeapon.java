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
public class MarketWeapon {
        private Weapon wpn;//Referencia a uma Weapon do mundo. 
        private int buyPrice;
        private int sellPrice;
        private int supply;
        private int demand;

    public MarketWeapon(Weapon wpn, int supply, int demand) {
        this.wpn = wpn;
        this.supply = supply;
        this.demand = demand;
    }
        
        
}
