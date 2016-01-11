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
public final class World {
    private ArrayList<Region> regions;
	private WeaponDictionary weapons;
    
    /*
    Metodo para criar conexao entre 2 regioes, A e B
    */
    public void createConnection(Region regA, Region regB, boolean land, int weight){
        //Cria a conexão
        Connection con = new Connection(regA, regB, land, weight);
        
        //Referencia as duas regiões com a mesma conexão
        regA.addAdjacent(con);
        regB.addAdjacent(con);
    }
    
    public Region getRegion(int index){
        if(index>= this.regions.size()){
            System.out.println("getRegion com index invalido");
            return null;
        }
        else{
            return this.regions.get(index);
        }
    }
    
    
    /**
     * Método que atualizará o estado dos mercados e produtos ao se passar o turno
     */
    public void updateMarkets(){
        
    }
    
    /*
    public void updateFactions
    
    
    */
    public World() {
        
        this.regions = new ArrayList<Region>();
		this.weapons = new WeaponDictionary ();
		this.weapons.readJSON ("Rifles");

		// Teste dos JSON
		for (Weapon w : weapons.getAllWeapons ()) {
			System.out.println ("Arma: " + w.getName ()
					+ "\n\tbasePrice = " + w.getBasePrice ()
					+ "\n\tmargin = " + w.getMargin ()
					+ "\n\theatInc = " + w.getHeatInc ()
					+ "\n\tsize = " + w.getSize ()
					+ "\n\tcombEfecBonus = " + w.getCombEfecBonus ()
					+ "\n\tdescription = " + w.getDescription()
                                        + "\n\tcategory = " + w.getCategory());
		}
        
        /*Cria e inicializa as regioes do mundo
        PlaceHolder usando o mapa de Strangereal
        */
                
        boolean[] marketStatusTest = {true, false};
        
        Region emm = new Region("Emmeria", 5, 5, marketStatusTest, this.weapons);
        Region wel = new Region("Wellow",4, 4, marketStatusTest, this.weapons);
        Region yuk = new Region("Yuktobania", 3, 3, marketStatusTest, this.weapons);
        Region osea = new Region("Osea", 2, 2, marketStatusTest, this.weapons);
        /*Seta as conexoes entre as regios*/
        this.createConnection(emm, yuk, false, 1);
        this.createConnection(yuk, osea, false, 2);
        this.createConnection(osea, wel, false, 1);
        this.createConnection(emm, wel, false,3);
        
        /*Adiciona as regioes ao arraylist*/
        this.regions.add(emm);//0
        this.regions.add(wel);//1
        this.regions.add(yuk);//2
        this.regions.add(osea);//3
    }
    
    
}
