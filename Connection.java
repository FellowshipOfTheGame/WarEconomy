/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package war.economy.beta;

/**
 *
 * @author João
 */
public class Connection {
    private Region regionA;
    private Region regionB;
    
    private boolean land;/*true significa conexao por terra*/
    private int weight;

    public Connection(Region regionA, Region regionB, boolean land, int weight) {
        this.regionA = regionA;
        this.regionB = regionB;
        this.land = land;
        this.weight = weight;
    }
    
    
}
