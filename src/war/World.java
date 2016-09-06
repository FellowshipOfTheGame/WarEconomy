/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package war;

import java.util.ArrayList;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

/**
 *
 * @author João
 */
public final class World {
    private ObservableList<Region> regions;
    private WeaponDictionary weapons;
    
    /*
    Metodo para criar conexao entre 2 regioes, A e B
    */
    public void createConnection(Region regA, Region regB, boolean land, int weight, int risk){
        //Cria duas instâncias de conexão. 
        regA.addAdjacent (new Connection (regA, regB, land, weight, risk));
        regB.addAdjacent (new Connection (regB, regA, land, weight, risk));
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
     * Método para retornar uma observablelist com todas as regiões.
     * @param dontInclude uma região que não deve ser incluida no retorno
     * @return observableList de todas as regiões exceto dontInclude
     */
    public ObservableList<Region> getRegions (Region dontInclude){
        
        ObservableList<Region> obl = FXCollections.observableArrayList();
        regions.stream()
                .forEach(region -> {
                    if(region != dontInclude)
                        obl.add(region);
                });    
        return obl;
    }
    
    /**
     * Método que atualizará o estado dos mercados e produtos ao se passar o turno
     */
    public void updateMarkets(){
        
    }
    
    /*
    public void updateFactions
    
    
    */
    
    /**
     * Método que atualizará o estado dos mercados e produtos ao se passar o turno
     */
    public void updateRegions(){
        regions.stream()
                .forEach(region -> {
                    region.updateRegion();
                });      
    }
    
    public World() {
        
        this.regions = FXCollections.observableArrayList ();
		this.weapons = new WeaponDictionary ();
		this.weapons.readJSON ("Rifles");

		// Teste dos JSON
		for (Weapon w : weapons.getAllWeapons ()) {
			System.out.println ("Arma: " + w.getName ()
					+ "\n\tbasePrice = " + w.getBasePrice ()
					+ "\n\tmargin = " + w.getMargin ()
					+ "\n\tnotInc = " + w.getNotInc ()
					+ "\n\tsize = " + w.getSize ()
					+ "\n\tcombEfecBonus = " + w.getCombEfecBonus ()
					+ "\n\tdescription = " + w.getDescription()
                                        + "\n\tcategory = " + w.getCategory());
		}
        
        /*Cria e inicializa as regioes do mundo
        PlaceHolder usando o mapa de Strangereal
        */
                
        boolean[] marketStatusTest = {true, false};
        
        Region naf = new Region("Nafran", 5, 30, marketStatusTest, this.weapons);
        Region col = new Region("Columbia",4, 40, marketStatusTest, this.weapons);
        Region cal = new Region("Callisto", 3, 80, marketStatusTest, this.weapons);
        Region ura = new Region("Uraliya", 2, 30, marketStatusTest, this.weapons);
        Region rut = new Region("Ruthenia", 2, 45, marketStatusTest, this.weapons);
        Region aur = new Region("Auria", 2, 30, marketStatusTest, this.weapons);
        
        
        /*Seta as conexoes entre as regios*/
        this.createConnection(naf, col, true, 1, 30);
        this.createConnection(naf, aur, false, 2, 30);
        
        this.createConnection(col, cal, false, 1, 30);
        this.createConnection(col, ura, false,3, 30);
        
        this.createConnection(cal, ura, false,1, 30);
        this.createConnection(cal, rut, false,1, 30);
        
        this.createConnection(ura, rut, false,1, 30);
        this.createConnection(ura, aur, false,1, 30);
        
        this.createConnection(rut, aur, false,1, 30);
        
        /*Adiciona as regioes ao arraylist*/
        this.regions.add(naf);
        this.regions.add(col);
        this.regions.add(cal);
        this.regions.add(ura);
        this.regions.add(rut);
        this.regions.add(aur);
        
        System.out.println(regions);
    }
    
    
}
