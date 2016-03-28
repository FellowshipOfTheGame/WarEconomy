
package war;

import java.util.ArrayList;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

/**
 * FXML Controller class
 * @briefing Classe do personagem do jogador
 * @author João Victor L. da S. Guimarães
 * @date 10/12/2015
 * @version 0.1
 * @phase I
 */

public class PlayerCharacter extends Character{
    private int funds;
    private int heat;
    
    private int warehouseUpkeep;
    private int agentUpkeep;
    private int transportUpkeep;
    
    private ArrayList<Warehouse> warehouses;
    private ArrayList<Agent> agents;
    private ArrayList<Transport> transports;

    public int getAgentUpkeep() {
        return agentUpkeep;
    }

    public int getTransportUpkeep() {
        return transportUpkeep;
    }

    public int getWarehouseUpkeep() {
        return warehouseUpkeep;
    }
    
    

    public ArrayList<Agent> getAgents() {
        return agents;
    }
    
    public int getFunds() {
        return funds;
    }

    public int getHeat() {
        return heat;
    }

    public void subtractFunds(int value) {
        int funds = this.getFunds() - value;
        if(funds<0){
            this.funds = 0;
        }
        else{
            this.funds = funds;
        }
        System.out.println("New funds:" + this.funds);
    }
    
    public void addFunds(int value) {
        int funds = this.getFunds() + value;
        this.funds = funds;
    }
    
    public void subtractHeat(int heatInc) {
        int heat = this.getHeat() - heatInc;
        if(heat<0){
            this.heat = 0;
        }
        else{
            this.heat = heat;
        }
    }
    
    public void addHeat(int heatInc) {
        int heat = this.getHeat() + heatInc;
        if(heat >= 100){
            System.out.println("Game over");
        }
        else{
            this.heat = heat;
        }
    }

    public ArrayList<Transport> getTransports() {
        return transports;
    }
    
    /**
     * Retorna apenas os transportes localizados em uma determinada região.
     * @param region //Região em questão para procurar
     * @return 
     */
    public ArrayList<Transport> getTransports(Region region) {
        ArrayList<Transport> compatibleTransports = new ArrayList<>();
        for (Transport transport : transports) {
            if(transport.getCurrentPos() == region){
                compatibleTransports.add(transport);
            }
        }
        return compatibleTransports;
    }
    
    
    public void moveTransports(){
        for(Transport transport : transports){
            transport.move();
        }
    }
    
    /***
     * Retorna lista Observavel de todos os Storables.
     * Utilizada para montar a tabela de inventário.
     */
    public ObservableList<Storable>getStorableObl() {//Retorna uma observable list para montar a tabela
        ObservableList<Storable> obl = FXCollections.observableArrayList();
        
        for(Warehouse warehouse : warehouses){
            obl.add(warehouse);
        }
        
        for (Transport transport : transports) {
            obl.add(transport);
        }
        
        return obl;
    }
    
    /***
     * Retorna lista Observavel de todos os Storables em uma determinada região, com a exceção de um.
     * @param sto = storable exceção, já selecionado
     * @param reg = região para procurar
     * Utilizada para montar a comboBox de "Mover" do inventário.
     */
    public ObservableList<Storable>getStorableObl(Region reg, Storable sto) {//Retorna uma observable list para montar a tabela
        ObservableList<Storable> obl = FXCollections.observableArrayList();
        
        for(Warehouse warehouse : warehouses){
            if(warehouse.getCurrentPos()==reg && warehouse != sto)
                obl.add(warehouse);
        }
        for (Transport transport : transports) {
            if(transport.getCurrentPos()==reg && transport != sto)
                obl.add(transport);
        }
        return obl;
    }
    
    
    public ObservableList<Transport>getTransportObl() {//Retorna uma observable list para montar a tabela da tab de transportes
        ObservableList<Transport> obl = FXCollections.observableArrayList();
        
        for (Transport transport : transports) {
            obl.add(transport);
        }
        
        return obl;
    }
    
    
    /**
     * Métodos a serem chamado toda a vez que o jogador adiciona um novo transporte, agente ou armazém novo.
     * Acrescenta o upkeep do novo asset ao upkeep total daquela categoria.
     */
    public void addTransport(Transport trnsp){
        this.transports.add(trnsp);
        this.transportUpkeep += trnsp.getUpkeep();
    }
    
    
    public void addAgent(Agent agnt){
        this.agents.add(agnt);
        this.agentUpkeep += agnt.getWage();
    }
    
    public void addWarehouse(Warehouse wareh){
        this.warehouses.add(wareh);
        this.warehouseUpkeep += wareh.getUpkeep();
    }
    
    /*
    Construtor para NEW GAME
    @param string para o nome
    @param Posicao de inicio do jogador
    */
    public PlayerCharacter(String name, Region startingPos) {
        this.funds = 200;
        this.heat = 0;
        this.intrigue = 0;
        this.barter = 0;
        this.name = name;
        this.currentPos = startingPos;
        
        this.warehouses = new ArrayList<Warehouse>();
        this.agents = new ArrayList<Agent>();
        this.transports = new ArrayList<Transport>();
        
        //Constroi a warehouse da regiao inicial do jogador e a inclui na lista de Warehouses
        boolean buildWarehouse = startingPos.buildWarehouse(); 
        System.out.println("buildWarehouse: " + buildWarehouse);
        this.addWarehouse(startingPos.getLocalWarehouse());
        
        //Constroi e insere o primeiro transporte do jogador.
        Transport t = new Transport("Teco Teco",0,"air",1, 1, 1, startingPos, 1);//TEMPORARIO
        this.transports.add(t);
        
        System.out.println("New player " + name + " character chreated at " + startingPos.getName());
    }

    
    
}
