/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package war;

/**
 *
 * @author João
 */
public class Connection {
    private Region orig;
    private Region dest;
    
    private boolean land;/*true significa conexao por terra*/
    private int weight;

    public Region getOrigin() {
        return orig;
    }

    public Region getDestination() {
        return dest;
    }

    public boolean isLand() {
        return land;
    }

    public int getWeight() {
        return weight;
    }

    /***
     * ctor.
     * @param orig região origem da conexão
     * @param dest região destino da conexão
     * @param land Booleano se é ou não terra
     * @param weight peso da conexão.
     */
    
    public Connection(Region orig, Region dest, boolean land, int weight) {
        this.orig = orig;
        this.dest = dest;
        this.land = land;
        this.weight = weight;
    }
    
    
}
