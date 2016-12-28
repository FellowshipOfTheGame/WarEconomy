
package war;

import java.util.ArrayList;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import static war.Evidence.generateEvidence;

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
    
    private ObservableList<Warehouse> warehouses;
    private ObservableList<GameCharacter> agents;
    private ObservableList<Transport> transports;
    // ObservableList com todos os Storages, nada mais que `transports ++ warehouses`
    private ObservableList<Storable> allStorages;
    
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
    /**
     * @return ObservableList com todos agentes
     */
    public ObservableList<GameCharacter> getAgents() {
        return agents;
    }
	/**
	 * Lista de agentes filtrada pela região
	 *
	 * @param r Região requisitada
	 */
    public ObservableList<GameCharacter> getAgents (Region r) {
        return agents.filtered (agnt -> agnt.getCurrentPos () == r);
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
            //System.out.println("New funds:" + this.funds);
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
                //System.out.println("adding notoriety: " + value);
                this.notoriety = noto;
            }
        }
        else{
            int noto = this.getNotoriety() - value;
            if(noto<0){
                this.notoriety = 0;
            }
            else{
                //System.out.println("subtracting notoriety: " + value);
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
    
    public ObservableList<Transport> getTransports() {
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
        /*transports.stream().forEach((transport) -> {
            if(transport.getRoute()!=null){
                boolean success = transport.move();
                
                //SE O TRANSPORTE FALHOU NO TESTE DE NOISE
                if(!success){
                    //Gerar evidência na posição do transporte.
                    generateEvidence(transport.getCurrentPos(), null);
                }
            }
            
        });*/
    }
    
    /***
     * Utilizada para montar a tabela de inventário.
	 * 
     * @return Retorna lista Observavel de todos os Storables.
     */
    public ObservableList<Storable> getStorableObl() {
        return allStorages;
    }

    
    /***
     * Retorna lista Observavel de todos os Storables em uma determinada região, com a exceção de um.
     * @param sto = storable exceção, já selecionado
     * @param reg = região para procurar
     * Utilizada para montar a comboBox de "Mover" do inventário.
     * @return lista Observavel de todos os Storables em uma determinada região, com a exceção de um.
     */
    public ObservableList<Storable>getStorableObl (Region reg, Storable sto) {//Retorna uma observable list para montar a tabela
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
    
    
    public ObservableList<Transport> getTransportObl() {//Retorna uma observable list para montar a tabela da tab de transportes
        return transports;
    }
    
    
    /**
     * Métodos a serem chamado toda a vez que o jogador adiciona um novo transporte, agente ou armazém novo.
     * Acrescenta o upkeep do novo asset ao upkeep total daquela categoria.
     * @param trnsp Transporte a ser adicionado
     */
    public void addTransport(Transport trnsp){
        this.transports.add(trnsp);
        this.transportUpkeep += trnsp.getUpkeep();
		// atualiza lista pra printar na tabela
		updateAllStorages ();
    }
    
    
    public void addAgent(Agent agnt){
        this.agents.add(agnt);
        this.agentUpkeep += agnt.getWage();
    }
    
    public void addWarehouse(Warehouse wareh){
        this.warehouses.add(wareh);
        this.warehouseUpkeep += wareh.getUpkeep();
		// atualiza lista pra printar na tabela
		updateAllStorages ();
    }
	
	/**
	 * Atualiza ObservableList de Storages, pra printar na tabela bonitim
	 */
	private void updateAllStorages () {
		allStorages.clear ();
		allStorages.addAll (warehouses);
		allStorages.addAll (transports);
	}
    
    /*
    Construtor para NEW GAME
    @param string para o nome
    @param Posicao de inicio do jogador
    */
    public PlayerCharacter(String name, Region startingPos, int barter, int intrigue, int investigation , int funds) {
        this.funds = funds;
        this.investigation = investigation;
        this.heat = 0;
        this.intrigue = intrigue;
        this.barter = barter;
        this.name = name;
        this.currentPos = startingPos;
        this.notoriety = 8;
        
        this.warehouses = FXCollections.observableArrayList ();
        this.transports = FXCollections.observableArrayList ();
		this.allStorages = FXCollections.observableArrayList ();
		this.agents = FXCollections.observableArrayList ();
		// adiciona o próprio personagem
		this.agents.add (this);
        
        //Constroi a warehouse da regiao inicial do jogador e a inclui na lista de Warehouses
        boolean buildWarehouse = startingPos.buildWarehouse(); 
        System.out.println("buildWarehouse: " + buildWarehouse);
        this.addWarehouse(startingPos.getLocalWarehouse());
        
        //Constroi e insere o primeiro transporte do jogador.
        Transport t = new Transport("Teco Teco",0,"air",1, 10, 1, startingPos, 6);//TEMPORARIO
        this.transports.add(t);
        
        System.out.println("New player " + name + " character chreated at " + startingPos.getName());
    }
    
}
