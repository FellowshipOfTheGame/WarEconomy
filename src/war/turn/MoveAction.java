/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package war.turn;

import java.util.ArrayList;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import war.Connection;
import static war.Evidence.generateEvidence;
import war.GameCharacter;
import war.PlayerCharacter;
import war.Region;
import war.TestManager;
import war.Transport;

/**
 * Ação de Movimentar transportes.
 * @author João
 */
public class MoveAction extends Action{

    
//==============================================================================
/*VARIAVEIS*/    
    Transport transport;
    private int weightTraveled;//Distância percorrida até o momento na conexão 
    private ArrayList<Region> route; //Rota que o transporte deve seguir.
    
    
 
//==============================================================================
/*MÉTODOS*/    
    
    /**
     * Adiciona uma nova região na rota do transporte. 
     * Se não havia rota, a rota é criada, caso contrário, apenas adiciona
     * @param reg = nova região destino
    */
    
    public void addRouteStop(Region reg) {
        System.out.println(route);
        if(route==null){
            route = new ArrayList<>();
            route.add(reg);
            System.out.println("nova ArrayList, rota estabelecida");
        }
        else{
            route.add(reg);
            System.out.println("Parada adicionada");
        }
    }   
     
    
    /**
     * Remove o destino final da rota do transporte.
     * Se não há mais nada na rota, transforma rota em null
     */
    public void removeRouteStop() {
        if(route != null){
            if(!route.isEmpty()) {
                route.remove(route.size()-1);//Remove a ultima posição da rota
                if (route.isEmpty()){ //Rota vazia, ação cancelada
                    route = null;
                    transport.setAction(null);
                    System.out.println("parada removida, rota = " + route);
                }
            }
        }
    }

    public ArrayList<Region> getRoute() {
        return route;
    }
    
    public String getRouteString(){
            
            String routeString = "En route from: " + transport.getCurrentPos().getName();
            if(route != null){
                for(Region region : route){
                    if(region != null)
                        routeString = routeString + " -> " + region.getName() ;
                }
            }   
            System.out.println(routeString);
            return routeString;

    }
    
    
    /**
     * Verifica, a partir de todas regiões adjacentes da posição atual, quais
     * regiões o transporte pode se mover.
     * @return obl = lista observável com as regiões moviveis. Usada para montar 
     * a ComboBox do menu de transportes. Se necessário, modificar aqui para considerar o caso de duas conexões entre 2 paises
     
    public ObservableList<Region> getMovableAdjacent(){
        
        ObservableList<Region> obl = FXCollections.observableArrayList();
        if(route == null){//Transporte em standby
            if("land".equals(type)){//Transporte terrestre
                for(Connection connection : transport.getCurrentPos().getAdjacent()){
                    if(connection.isLand()){
                        obl.add(connection.getDestination());
                      
                    }
                }
            }
            else if ("sea".equals(type)){//Transporte marinh
                for(Connection connection : transport.getCurrentPos().getAdjacent()){
                    if(!connection.isLand()){
                        obl.add(connection.getDestination());
                    }
                }
            }
            else{//Transporte aéreo
                for(Connection connection : transport.getCurrentPos().getAdjacent()){
                    obl.add(connection.getDestination());
                }
            }    
        }
        
        else{ //Transporte em rota
            Region destination = route.get(route.size()-1);//Ultima posição da rota
            if("land".equals(type)){//Transporte terrestre
                for(Connection connection : destination.getAdjacent()){
                    if(connection.isLand()){
                        obl.add(connection.getDestination());
                    }
                }
            }
            else if ("sea".equals(type)){//Transporte marinho
                for(Connection connection : destination.getAdjacent()){
                    if(!connection.isLand()){
                        obl.add(connection.getDestination());
                    }
                }
            }
            else{//Transporte aéreo
                for(Connection connection : destination.getAdjacent()){
                    obl.add(connection.getDestination());
                }
            }          
        }
        return obl;
    }*/
    
    /**
     * Verifica, a partir de todas regiões adjacentes da posição atual, quais
 regiões o transporte pode se mover.
     */
    @Override
    public void execute() {
        boolean result = true;
        if(route != null){
            System.out.println(transport.getName() + " Moving !");
            
            if(transport.getCurrentConnection() == null){//Transporte vai sair agora da sua posição atual
                transport.setCurrentConnection ( transport.getCurrentPos().getConnection(route.get(0)) );//Pega a conexao entre a posição atual e a primeira região da rota
                weightTraveled = transport.getSpeed();
                System.out.println("entering con: " + transport.getCurrentConnection());
                result = TestManager.makeNoiseTest(transport);
            }
            else{//Transporte já está em movimento
                weightTraveled += transport.getSpeed();
                System.out.println("Proceeding " + weightTraveled +"/" + transport.getCurrentConnection().getWeight());
                result = TestManager.makeNoiseTest(transport);
            }
            
            //VERIFICAÇÕES DE CHEGADA
            if(weightTraveled == transport.getCurrentConnection().getWeight()){//percorreu exatamente toda a distancia
                weightTraveled = 0;
                transport.setCurrentPos(route.get(0));//Chegou no destino
                route.remove(0);//Remove da rota
                
                if(route.size() > 0){//O caminho ainda não acabou
                    transport.setCurrentConnection( transport.getCurrentPos().getConnection(route.get(0)) );//Seta a nova conexão atual
                }
                
                else{//Caminho acabou
                    System.out.println("Arrived at destination");
                    cancel();
                }
            }
            
            //Percorreu mais do que a distância de uma dada conexão 
            else if(weightTraveled > transport.getCurrentConnection().getWeight() ){ 
                transport.setCurrentPos(route.get(0)); //nova posição
                route.remove(0);//remove da rota
                if(route.size() > 0){//O caminho ainda não acabou.
                    weightTraveled = weightTraveled - transport.getCurrentConnection().getWeight() ;//Distancia percorrida é a diferença do total com o peso da conexão atual "antiga"
                    transport.setCurrentConnection( transport.getCurrentPos().getConnection(route.get(0)) );//Nova conexão atual
                }else{//Caminho acabou
                    System.out.println("Arrived at destination");
                    cancel();
                }
            }
            
        }
        
        if(result == false) {
            /*
            GERAÇÃO DE PISTA
            */
            generateEvidence(transport.getCurrentPos(), this);
        }
        
    }

    @Override
    public String toString() {
        return "Moved a transport";
    }

    @Override
    public String getShortDesc() {
        return "Moved a transport";
    }
        
    @Override
    public void cancel(){
        transport.setCurrentConnection(null);
        transport.setAction(null);
        transport = null;
        player = null;
        actor = null;
    }
    
    
    /**
     * Ctor. 
     * @param player
     * @param transport
     */
    public MoveAction(PlayerCharacter player, Transport transport) {
        super(player, null, true);
        
        this.transport = transport;
        this.weightTraveled = 0;
        this.route = null;
    }
}
