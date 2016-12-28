
package war;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import war.turn.MoveAction;

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
    Connection currentConnection;
    
    private MoveAction action;
    
    private int totalCapacity;
    private int usedCapacity;//Capacidade sendo utilizada pelas armas.    
    private HashMap<String, PlayerWeapon> cargo;//Quais armas serão carregadas em uma trade mission.

//==============================================================================
/*GETTERS, SETTERS E ARMAZENAMENTO*/
    
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

    public void setCurrentPos(Region currentPos) {
        this.currentPos = currentPos;
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

    public int getSpeed() {
        return speed;
    }
    

    public MoveAction getAction() {
        return action;
    }

    public void setAction(MoveAction action) {
        this.action = action;
    }
    
    public Connection getCurrentConnection() {
        return currentConnection;
    }

    public void setCurrentConnection(Connection currentConnection) {
        this.currentConnection = currentConnection;
    }
    
    
    public int getNoise() {
        return noise;
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
        if(action!=null)
            return "En Route";
        else
            return "Standing By";
    }


//==============================================================================
/*MOVIMENTO E ROTAS*/
    
    /**
     * Verifica, a partir de todas regiões adjacentes da posição atual, quais
     * regiões o transporte pode se mover.
     * @return obl = lista observável com as regiões moviveis. Usada para montar 
     * a ComboBox do menu de transportes. Se necessário, modificar aqui para considerar o caso de duas conexões entre 2 paises
     */
    public ObservableList<Region> getMovableAdjacent(){
        
        ObservableList<Region> obl = FXCollections.observableArrayList();
        
        if(action == null){//Transporte em standby
            if("land".equals(type)){//Transporte terrestre
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
            else if ("sea".equals(type)){//Transporte marinh
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
            if(action.getRoute() != null){
                Region destination = action.getRoute().get(action.getRoute().size()-1);//Ultima posição da rota

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
        }
        return obl;
    }
    
   

    

    @Override
    public String toString() {
        return this.name;
    }
    /**
     * ctor.
     * @param name
     * @param price
     * @param type
     * @param speed
     * @param noise
     * @param upkeep
     * @param currentPos
     * @param totalCapacity 
     */
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
        this.cargo = new HashMap<>();
        
        this.currentConnection = null;

        this.action = null;
        
    }


}
