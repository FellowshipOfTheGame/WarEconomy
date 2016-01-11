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
    /*Abstração da estabilidade política de uma região.*/
    private int geoPolStatus;
    
    private int opRisk;

    private Market localMarket; 
        
    /* vetor de booleanos que indica se um pais é produtor(TRUE) 
    ou consumidor(FALSE) de uma categoria de armas. Segue a ORDEM PRESENTE NO GDD e EXCLUI armas únicas*/
    private boolean marketStatus[];
    
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


    public Warehouse getLocalWarehouse() {
        return localWarehouse;
    }

    public Market getLocalMarket() {
        return localMarket;
    }

    public boolean[] getMarketStatus() {
        return marketStatus;
    }

    public ArrayList<Connection> getAdjacent() {
        return adjacent;
    }
    
    
   
    public boolean buildWarehouse() {
        if(this.localWarehouse == null){
            Warehouse w = new Warehouse(this);
            this.localWarehouse = w;
            return true;
        }
        else
            return false;//Ja existe warehouse na regiao
    }
    
    
    public void addAdjacent(Connection c){
        this.adjacent.add(c);
    }
    
    
    @Override
    public String toString(){
        return this.name;
    }

    public Region(String name, int geoPolStatus, int opRisk, boolean mrkStatus[], WeaponDictionary wpnDic) {
        
        this.name = name;
        this.geoPolStatus = geoPolStatus;
        this.opRisk = opRisk;
        
        
        this.adjacent = new ArrayList<>();
        this.localWarehouse = null;//Inicialmente, todas as regiões não tem warehouses
        
        this.marketStatus = mrkStatus;
        
        Market m = new Market(geoPolStatus, mrkStatus, wpnDic);
        this.localMarket = m;
        
    }
    
    
}
