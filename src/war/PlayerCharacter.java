
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

public class PlayerCharacter extends GameCharacter{
    
    private int funds;
    private int heat;
    private int notoriety;
    
    private int warehouseUpkeep;
    private int agentUpkeep;
    private int transportUpkeep;
    
    private ArrayList<Warehouse> warehouses;
    private ArrayList<GameCharacter> agents;


    private ArrayList<Transport> transports;
    
    public int getNotoriety() {
        return notoriety;
    }
    
    public int getAgentUpkeep() {
        return agentUpkeep;
    }

    public int getTransportUpkeep() {
        return transportUpkeep;
    }

    public int getWarehouseUpkeep() {
        return warehouseUpkeep;
    }
    /***
     * 
     * @param includePlayer booleano que pergunta se o player sera incluso na resposta
     * @return 
     */
    public ArrayList<GameCharacter> getAgents() {
        return agents;
    }
    
    //Cria a observablelist de agentes (incluind o próprio personagem do jogador) para montar a tabela de agentes.
    public ObservableList<GameCharacter> getAgentObl(){
        ObservableList<GameCharacter> agntObl = FXCollections.observableArrayList();
        agents.stream()
                .forEach(agent -> {agntObl.add(agent);
                });
        agntObl.add(this);
        System.out.println(agntObl);
        return agntObl;
    }
    
    
    public int getFunds() {
        return funds;
    }
    
    public int getHeat() {
        return heat;
    }
    
    /**
     * Setter para os fundos.
     * @param add bool true para adicionar, false para subtrair
     * @param value inteiro com a quantidade para realizar a operação
     * 
     */
    public void setFunds(boolean add, int value) {
        if(add){
            int funds = this.getFunds() + value;
            this.funds = funds;
        }
        else{
            int funds = this.getFunds() - value;
            if(funds<0){
                this.funds = 0;
            }
            else{
                this.funds = funds;
            }
            System.out.println("New funds:" + this.funds);
        }
    }
    
    /**
     * Setter para Heat.
     * @param add bool true para adicionar, false para subtrair
     * @param value inteiro com a quantidade para realizar a operação
     */
    public void setHeat(boolean add, int value){
        if(add){
            int heatCheck = this.getHeat() + value;
            if(heatCheck >= 100){
                System.out.println("Game over");
            }
            else{
                this.heat = heatCheck;
            }
        }
        else{
            int heat = this.getHeat() - value;
            if(heat<0){
                this.heat = 0;
            }
            else{
                this.heat = heat;
            }        
        }
    }
    
    /**
     * Setter para Notoriedade.
     * @param add bool true para adicionar, false para subtrair
     * @param value inteiro com a quantidade para realizar a operação
     * 
     */
    public void setNotoriety(boolean add, int value) {
        if(add){
            int noto = this.getNotoriety() + value;
            //Aumenta a notoriedade apenas se ela for menor do que 100
            if(noto < 100){           
                System.out.println("adding notorietiy: " + value);
                this.notoriety = noto;
            }
        }
        else{
            int noto = this.getNotoriety() - value;
            if(noto<0){
                this.notoriety = 0;
            }
            else{
                System.out.println("subtracting notorietiy: " + value);
                this.notoriety = noto;
            }        
        }
    }
        
    /***
     * Decrementa a notoriedade do player conforme turnos passam.
     * 
     */
    public void decrementNotoriety(){
            int noto = this.getNotoriety() - 1;
            //Aumenta a notoriedade apenas se ela for menor do que 100
            if(noto < 100 && noto >= 0){
                this.notoriety = noto;
            }    
    }
    
    public ArrayList<Transport> getTransports() {
        return transports;
    }
    
    /**
     * Retorna apenas os transportes localizados em uma determinada região.
     * @param region //Região em questão para procurar
     * @return compatibleTransports  //Lista de transportes compatíveis
     */
    public ArrayList<Transport> getTransports(Region region) {
        ArrayList<Transport> compatibleTransports = new ArrayList<>();
        transports.stream()
                    .filter((transport) -> (transport.getCurrentPos() == region))
                    .forEach((transport) -> {
                        compatibleTransports.add(transport);
                    });
        return compatibleTransports;
    }
    
    /**
     * Método usado ao finalizar turno para mover todos os transportes.
     * Realiza uma iteração na lista de transportes do jogador e manda todos se moverem.
     * Cada um realiza também o teste de noise.
     * Método utilizado na função "endTurn()" de GameController
     */
    public void moveTransports(){
        transports.stream().forEach((transport) -> {
            if(transport.getRoute()!=null){
                boolean success = transport.move();
                
                //SE O TRANSPORTE FALHOU NO TESTE DE NOISE
                if(!success){
                    /***
                     * Rodar testes de geração de pista em uma das regiões.
                     */
                }
            }
            
        });
    }
    
    /***
     * Utilizada para montar a tabela de inventário.
     * @return Retorna lista Observavel de todos os Storables.
     */
    public ObservableList<Storable>getStorableObl() {//Retorna uma observable list para montar a tabela
        ObservableList<Storable> obl = FXCollections.observableArrayList();
        
        warehouses.stream().forEach((warehouse) -> {
            obl.add(warehouse);
        });
        
        transports.stream().forEach((transport) -> {
            obl.add(transport);
        });
        
        return obl;
    }

    
    /***
     * Retorna lista Observavel de todos os Storables em uma determinada região, com a exceção de um.
     * @param sto = storable exceção, já selecionado
     * @param reg = região para procurar
     * Utilizada para montar a comboBox de "Mover" do inventário.
     * @return lista Observavel de todos os Storables em uma determinada região, com a exceção de um.
     */
    public ObservableList<Storable>getStorableObl(Region reg, Storable sto) {//Retorna uma observable list para montar a tabela
        ObservableList<Storable> obl = FXCollections.observableArrayList();
        
        warehouses.stream()
                .filter((warehouse) -> (warehouse.getCurrentPos()==reg && warehouse != sto))
                .forEach((warehouse) -> {
                    obl.add(warehouse);
                });
        transports.stream()
                .filter((transport) -> (transport.getCurrentPos()==reg && transport != sto))
                .forEach((transport) -> {
                        obl.add(transport);
                });
        return obl;
    }
    
    
    public ObservableList<Transport>getTransportObl() {//Retorna uma observable list para montar a tabela da tab de transportes
        ObservableList<Transport> obl = FXCollections.observableArrayList();
        
        transports.stream()
                .forEach((transport) -> {
                    obl.add(transport);
                });
        
        return obl;
    }
    
    
    /**
     * Métodos a serem chamado toda a vez que o jogador adiciona um novo transporte, agente ou armazém novo.
     * Acrescenta o upkeep do novo asset ao upkeep total daquela categoria.
     * @param trnsp Transporte a ser adicionado
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
        this.investigation = 2;
        this.heat = 0;
        this.intrigue = 0;
        this.barter = 0;
        this.name = name;
        this.currentPos = startingPos;
        
        this.warehouses = new ArrayList<Warehouse>();
        this.agents = new ArrayList<GameCharacter>();
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
