/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package war.economy.beta;

import java.util.ArrayList;

/**
 *
 * @author João
 */
public class Region {
    private String name;
    private int geoPolStatus;
    private int opRisk;
    private int marketStatus;
    private ArrayList<Connection> adjacent;
    /*
    private ArrayList factions
    private ArrayList adjacent
    private ArrayList factionRelations
    private LocalWarehouse
    */

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
    
    
    
    
    public void addAdjacent(Connection c){
        this.adjacent.add(c);
    }
    

    public Region(String name) {
        
        this.adjacent = new ArrayList<Connection>();
        this.name = name;
        this.geoPolStatus = 5;
        this.marketStatus = 2;
        this.opRisk = 1;
    }
    
    
}
