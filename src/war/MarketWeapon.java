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
        
        private String wpnName;
        private String wpnCat;
        
        private int buyPrice;
        private int sellPrice;
        private int supply;
        private int demand;
        
    public int calculateBuyPrice(){
        return wpn.getBasePrice() + 1;
    }
    public int calculateSellPrice(){
        return wpn.getBasePrice() + 1;
    }

    public MarketWeapon(Weapon wpn, int supply, int demand) {
        this.wpn = wpn;
        this.supply = supply;
        this.demand = demand;
        this.buyPrice = calculateBuyPrice();
        this.sellPrice = calculateSellPrice();
        
        this.wpnName = wpn.getName();
        this.wpnCat = wpn.getCategory();
    }
    
    
    //GETTERS
    public Weapon getWpn() {
        return wpn;
    }

    public int getBuyPrice() {
        return buyPrice;
    }

    public int getSellPrice() {
        return sellPrice;
    }

    public int getSupply() {
        return supply;
    }

    public int getDemand() {
        return demand;
    }

    public String getWpnName() {
        return wpnName;
    }

    public String getWpnCat() {
        return wpnCat;
    }
        
    
    
        
}
