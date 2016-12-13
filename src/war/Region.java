/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package war;

import java.util.ArrayList;
import java.util.Iterator;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import static javafxStuff.GameController.returnToBase;
import static war.TestManager.rollDie;
import static war.TestManager.successTest;
import war.turn.Action;

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

    /*Lista de todas as evidências */
    private ObservableList<Evidence> evidences;

    
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
   
    /***
     * Método que retorna a evidência na região causada por uma determinada ação.
     * Apenas deve existir uma evidência por causa em uma determinada região.
     * @param act Ação que causou a evidência
     * @return Retorna a evidência se ela existir, ou nulo se ela não existir.
     */
    public Evidence getEvidenceByCause (Action act){
       for (Evidence e : evidences){
           if(e.getCauseType() == act.getClass())
               return e;
       }
       return null;
    }
    
    
    public ObservableList<Evidence> getEvidences () {
        return evidences;
    }
    
    
    /***
     * Método a ser usado no update region para decrementar o timer de todas as 
     * evidencias na região e remove-las caso o timer zere.
     */
    private void decrementEvidenceTimer(){
    
        for (Iterator<Evidence> it = evidences.iterator(); it.hasNext();){
            Evidence e = it.next();
            if(! e.decrementTimer()){
                
                e.setReg(null);
                it.remove();
                System.out.println("Timer zerado, removendo evidencia");
            }
        }
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
    
    public void addEvidence(Evidence e){
        this.evidences.add(e);
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
     * Método utilizado para testar o encontro de uma evidência pelas autoridades.
     * Método de teste apenas
     */
    public void testEvidenceInvestigation(){
        if(!evidences.isEmpty()){
            if(successTest(opRisk, 0)) {
                Evidence evi = evidences.get(0);
                evi.getCause().getPlayer().setHeat(true, evi.getHeatInc());
                evidences.remove(evi);
            }
        }
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
            
            opRisk = returnToBase(0, 100, opRisk, opRiskBase, false, 5);
        }
        
        //-----------------------
        //teste
        testEvidenceInvestigation();
        //-----------------------
        //reduz timer em todas as evidências.
        decrementEvidenceTimer();
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
        this.evidences = FXCollections.observableArrayList ();
        this.localWarehouse = null;//Inicialmente, todas as regiões não tem warehouses
        
        this.marketStatus = mrkStatus;
        
        Market m = new Market(geoPolStatus, mrkStatus, wpnDic);
        this.localMarket = m;
                
    }
    
    
}
