/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package war;

import java.util.ArrayList;

/**
 *
 * @author João
 */
public class Region {
    private String name;
    private int geoPolStatus;
    private int opRisk;
    //private int marketStatus; vetor?
    private Market localMarket; 
    private ArrayList<Connection> adjacent;
    
    /*private ArrayList factions
    private ArrayList factionRelations */
    private Warehouse localWarehouse;

    
    //GETTERS
    public String getName() {
        return name;
    }

    public int getGeoPolStatus() {
        return geoPolStatus;
    }

    public int getOpRisk() {
        return opRisk;
    }

    public int getMarketStatus() {
        return marketStatus;
    }

    public Warehouse getLocalWarehouse() {
        return localWarehouse;
    }

    
   
    public boolean buildWarehouse() {
        if(this.localWarehouse == null){
            Warehouse w = new Warehouse();
            this.localWarehouse = w;
            return true;
        }
        else
            return false;//Ja existe warehouse na regiao
    }
    
    
    public void addAdjacent(Connection c){
        this.adjacent.add(c);
    }
    

    public Region(String name) {
        
        this.adjacent = new ArrayList<Connection>();
        this.localWarehouse = null;//Inicialmente, todas as regiões não tem warehouses
        
        //Market m = new Market();
        this.localMarket = m;
        
        this.name = name;
        this.geoPolStatus = 5;
        //this.marketStatus = 2;
        this.opRisk = 1;
    }
    
    
}
