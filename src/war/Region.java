/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package war;

import java.util.ArrayList;
import java.util.Random;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import static javafxStuff.GameController.checkIntRange;

/**
 *
 * @author João
 */
public class Region {
    
    
    private String name;
    /*Abstração da estabilidade política de uma região.  Calculado a partir dos relacionamentos entre as facções.*/
    private int geoPolStatus;
    
    /*Risco operacional em uma região*/
    private int opRisk; //Valor do risco atual da região.
    private int opRiskBase; //Valor que Oprisk sempre tenderá. Determinado pela situação geopolítica da região.
    
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
    
    public Connection getConnection(Region reg){
        for (Connection connection : adjacent){
            if(connection.getDestination() == reg)
                return connection;
        }
        System.out.println("Error: connection not found");
        return null;        
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

    /*
        ter uma variável que armazena um valor "base" de OpRisk. Esse valor é determinado pelo estado geopolítico da região.
        OpRisk pode mudar temporariamente de acordo com certos buffs e debuffs, mas conforme a região é atualizada no update region, 
        o valor de oprisk irá voltar lentamente ao valor base.
    */
    public void setOpRisk (int opRisk) {
        if(opRisk > 99)
            this.opRisk = 99;
        else if(opRisk < 1)
            this.opRisk = 1;
        else
            this.opRisk = opRisk;
    }


    /**
     * Método usado para atualizar todos os valores de uma região após o fim de turno.
     * Estado geopolítico varia de acordo com relacionamentos entre facções.
     * Modifica os valores de opRiskBase baseado em mudanças no estado geoPolítico.
     */
    public void updateRegion(){
        
        //Atualiza o valor de opRisk, sempre tendendo ao valor de base.
        if(opRisk != opRiskBase) {
            System.out.println("Update opRisk");
         
            //Gera um valor de 1 a 5 para retornar à base
            Random diceRoll = new Random();
            //random.nextInt(max - min + 1) + min
            int result = diceRoll.nextInt(5) + 1;
            
            if(opRisk > opRiskBase){
            
                while( ! checkIntRange(0, 100, opRisk-result, false) ) {
                    result = diceRoll.nextInt(5) + 1;
                }
                opRisk = opRisk - result;
                System.out.println("Update Region: Updated OP = " + opRisk);
            }
            else {
                while( ! checkIntRange(0, 100, opRisk+result, false) ) {
                    result = diceRoll.nextInt(5) + 1;
                }
                
                opRisk = opRisk + result;
                System.out.println("Update Region: Updated OP = " + opRisk);
                
            }
        }
        
        //-----------------------
        
    }

    
    
    
    @Override
    public String toString(){
        return this.name;
    }

    public Region(String name, int geoPolStatus, int opRisk, boolean mrkStatus[], WeaponDictionary wpnDic) {
        
        this.name = name;
        this.geoPolStatus = geoPolStatus;
        
        this.opRiskBase = opRisk;
        this.opRisk = opRisk;
        
        
        this.adjacent = new ArrayList<>();
        this.localWarehouse = null;//Inicialmente, todas as regiões não tem warehouses
        
        this.marketStatus = mrkStatus;
        
        Market m = new Market(geoPolStatus, mrkStatus, wpnDic);
        this.localMarket = m;
                
    }
    
    
}
