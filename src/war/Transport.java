
package war;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Random;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

/**
 *
 * @author João
 */
public class Transport implements Storable{
    private String name;
    private int price;
    private String type;//air, land, sea
    private int speed;
    private int noise;
    private int upkeep;
    
    private Region currentPos;
    private int weightTraveled;//Distância percorrida até o momento na conexão 
    private Connection currentConnection;
    private ArrayList<Region> route; //Rota que o transporte deve seguir.
    
    private int totalCapacity;
    private int usedCapacity;//Capacidade sendo utilizada pelas armas.    
    private HashMap<String, PlayerWeapon> cargo;//Quais armas serão carregadas em uma trade mission.

    
    @Override
    public void store(Weapon wpn, int qty) {
        //Pressupondo que as armas caberão no transporte. Verificação para isso deve ser feita pelo método buy() de GameController
        PlayerWeapon pwpn = this.cargo.get(wpn.getName());
        
        
        if(wpn.getSize() * qty + usedCapacity > totalCapacity){
            System.err.println("INSUFFICIENT CARGO SPACE");
            return;
        }
        
        //Arma ainda não existe no inventário do transporte
        if(pwpn == null){
            pwpn = new PlayerWeapon(wpn, wpn.getSize()*qty, qty);//Cria uma nova arma de jogador.
            this.cargo.put(wpn.getName(), pwpn);
            this.usedCapacity += wpn.getSize()*qty;
            System.out.println("nova PlayerWeapon " + wpn.getName());
        }
        
        //Arma já existe no inventário do transporte, simplesmente aumenta a quantidade, se couber
        else{
            pwpn.setQty(pwpn.getQty() + qty);
            System.out.println("nova PlayerWeapon " + wpn.getName());
        }    
    }

    @Override
    public void remove(String wpnName, int qty) {
        
        PlayerWeapon pwpn = this.cargo.get(wpnName);
        
        this.usedCapacity -= pwpn.getWpn().getSize()*qty;//Libera Espaço        
        
        if(pwpn.getQty() - qty == 0){ //Não haverá mais dessas armas no estoque
            this.cargo.remove(wpnName); //Remove do Map
            System.out.println("Removendo " + wpnName +  " do Map");
        }
        else
            pwpn.setQty(pwpn.getQty() - qty);

    }
    
    @Override
    public int getTotalCapacity() {
        return totalCapacity;
    }

    @Override
    public int getUsedCapacity() {
        return usedCapacity;
    }

    @Override
    public void setUsedCapacity(int usedCapacity) {
        this.usedCapacity = usedCapacity;
    }

    @Override
    public Region getCurrentPos() {
        return currentPos;
    }

    @Override
    public int getWeaponQuantity(String wpnName) {
        //Pesquisa no HashMap pela arma
        PlayerWeapon wpn = this.cargo.get(wpnName);
        if(wpn == null){
            return 0;
        }
        else{
            return wpn.getQty();
        }
    }
    
    public int getUpkeep() {
        return upkeep;
    }

    @Override
    public String getName() {
        return name;
    }

    public String getType() {
        return type;
    }
    
    /**
     * Metodo usado principalmente para a tab de transportes.
     * */
    
    public String getWeaponsString(){
        Iterator it = this.cargo.entrySet().iterator();
        String str = "";
        
        while(it.hasNext()){
            HashMap.Entry entry = (HashMap.Entry<String, PlayerWeapon>)it.next();
            PlayerWeapon wpn = (PlayerWeapon)entry.getValue();
            str += wpn.getWpn().getName() + "x" + wpn.getQty() +"\n";
        }
        
        return str;    
    }
    
    @Override
    public ObservableList<PlayerWeapon> getWeapons() {
        Iterator it = this.cargo.entrySet().iterator();
        ObservableList<PlayerWeapon> obl = FXCollections.observableArrayList();
        
        while(it.hasNext()){
            HashMap.Entry entry = (HashMap.Entry<String, PlayerWeapon>)it.next();
            PlayerWeapon wpn = (PlayerWeapon)entry.getValue();
            obl.add(wpn);
        }
        
        return obl;
    }

    @Override
    public String getDescriptionInfo() {
        return ("Type: " + type.toUpperCase() + "\nSpeed: " + speed +
                "\nNoise: " + noise +"\nUpkeep: " + upkeep + "\nCapacity: " + usedCapacity +  "/" + totalCapacity);
    }

    
    public String getStatusString() {//Getter utilizado para montar a tabela de transportes
        if(route!=null)
            return "En Route";
        else
            return "Standing By";
    }

    public ArrayList<Region> getRoute() {
        return route;
    }
    
    public String getRouteString(){
        String routeString = "En route from: " + currentPos.getName();
        for(Region region : route)
            routeString = routeString + " -> " + region.getName() ;
        System.out.println(routeString);
        return routeString;
    }
    
    /**
     * Adiciona uma nova região na rota do transporte. 
     * Se não havia rota, a rota é criada, caso contrário, apenas adiciona
     * @param reg = nova região destino
     */
    
    public void addRouteStop(Region reg) {
        System.out.println(route);
        if(route==null){
            route = new ArrayList<Region>();
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
        route.remove(route.size()-1);//Remove a ultima posição da rota
        if (route.size() == 0){
            route = null;
            System.out.println("parada removida, rota = " + route);
        }
    }
    
    
    /**
     * Verifica, a partir de todas regiões adjacentes da posição atual, quais
     * regiões o transporte pode se mover.
     * @return obl = lista observável com as regiões moviveis. Usada para montar 
     * a ComboBox do menu de transportes. Se necessário, modificar aqui para considerar o caso de duas conexões entre 2 paises
     */
    public ObservableList<Region> getMovableAdjacent(){
        
        ObservableList<Region> obl = FXCollections.observableArrayList();
        if(route == null){//Transporte em standby
            if(type == "land"){//Transporte terrestre
                for(Connection connection : currentPos.getAdjacent()){
                    if(connection.isLand()){
                        obl.add(connection.getDestination());
                        /*if(connection.getRegionA() != currentPos)
                            obl.add(connection.getRegionA());//Regiao A da conexao é a outra
                        else
                            obl.add(connection.getRegionB());//Região B da conexão é a outra*/
                    }
                }
            }
            else if (type == "sea"){//Transporte marinh
                for(Connection connection : currentPos.getAdjacent()){
                    if(!connection.isLand()){
                        obl.add(connection.getDestination());
                    }
                }
            }
            else{//Transporte aéreo
                for(Connection connection : currentPos.getAdjacent()){
                    obl.add(connection.getDestination());
                }
            }    
        }
        
        else{ //Transporte em rota
            Region destination = route.get(route.size()-1);//Ultima posição da rota
            if(type == "land"){//Transporte terrestre
                for(Connection connection : destination.getAdjacent()){
                    if(connection.isLand()){
                        obl.add(connection.getDestination());
                    }
                }
            }
            else if (type == "sea"){//Transporte marinho
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
    }
    
    /***
     * Método chamado para mover o transporte. Já realiza o teste de noise também
     * @return Resultado do teste de noise
     */
    public boolean move(){
        boolean result = true;
        if(route != null){
            System.out.println(name + " Moving !");
            
            if(currentConnection==null){//Transporte vai sair agora da sua posição atual
                currentConnection = currentPos.getConnection(route.get(0));//Pega a conexao entre a posição atual e a primeira região da rota
                weightTraveled = speed;
                System.out.println("entering con: " + currentConnection);
                result = makeNoiseTest();
            }
            else{//Transporte já está em movimento
                weightTraveled += speed;
                System.out.println("Proceeding " + weightTraveled +"/" + currentConnection.getWeight());
                result = makeNoiseTest();
            }
            
            //VERIFICAÇÕES DE CHEGADA
            if(weightTraveled == currentConnection.getWeight()){//percorreu exatamente toda a distancia
                weightTraveled = 0;
                currentPos = route.get(0);//Chegou no destino
                route.remove(0);//Remove da rota
                
                if(route.size() > 0){//O caminho ainda não acabou
                    currentConnection = currentPos.getConnection(route.get(0));//Seta a nova conexão atual
                    return result;
                }else{//Caminho acabou
                    System.out.println("Arrived at destination");
                    currentConnection = null;
                    route = null;
                    return result;
                }
            }
            else if(weightTraveled > currentConnection.getWeight()){ //Percorreu mais do que a distância de uma dada conexão 
                currentPos = route.get(0); //nova posição
                route.remove(0);//remove da rota
                if(route.size() > 0){//O caminho ainda não acabou.
                    weightTraveled = weightTraveled - currentConnection.getWeight();//Distancia percorrida é a diferença do total com o peso da conexão atual "antiga"
                    currentConnection = currentPos.getConnection(route.get(0));//Nova conexão atual
                    return result;
                }else{//Caminho acabou
                    System.out.println("Arrived at destination");
                    weightTraveled = 0;                  
                    currentConnection = null;
                    route = null;
                    return result;
                }
            }
            
        }
        return true;
    }
    
                
    /***
     * Método que realiza um teste de noise em uma determinada região.
     * Ele é chamado quando o jogador usa "moveTransports()"
     * Teste é feito se o transporte esta carregando armas.
     * @return TRUE se o teste passou e o transporte não chamou atenção das autoridades
     *         FALSE se o transporte falhou e chamou atenção.
     */
    private boolean makeNoiseTest(){
        System.out.println("Making Noise Test");
        //Se o transporte está se movendo e tem carga
        if(currentConnection != null && usedCapacity != 0){
            Random diceRoll = new Random();
            //random.nextInt(max - min + 1) + min
            int result = diceRoll.nextInt(100) + 0;
            int target = currentConnection.getTravelRisk() + this.noise /*-currentConnection.getTravelRiskDebuff()*/;
            System.out.println("Jogou dado!\t RESULT: " + result + " atr: " + target);
            if(result <= target){
                System.out.println("Falhou teste de noise, pista pode ser gerada");
                return false;
            }
            else
                return true;
        }
        return true;
    }
    
    @Override
    public String toString() {
        return this.name;
    }
    
    public Transport(String name, int price, String type, int speed, int noise, int upkeep, Region currentPos, int totalCapacity){
        this.name = name;
        this.price = price;
        this.type = type;
        this.speed = speed;
        this.noise = noise;
        this.upkeep = upkeep;
        this.currentPos = currentPos;
        this.totalCapacity = totalCapacity;
        this.usedCapacity = 0;
        this.weightTraveled = 0;
        this.cargo = new HashMap<>();
        
        this.route = null;
        this.currentConnection = null;

    }


}
