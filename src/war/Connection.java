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
    
    private int travelRisk; 
    //private int travelRiskDebuff // para quando o jogador colocar um agente em Safeguard nessa conexão.

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

    public int getTravelRisk() {
        return travelRisk;
    }

    public void setTravelRisk(int travelRisk) {
        this.travelRisk = travelRisk;
    }

    
    
    /***
     * ctor.
     * @param orig região origem da conexão
     * @param dest região destino da conexão
     * @param land Booleano se é ou não terra
     * @param weight peso da conexão.
     * @param risk risco de viagem da conexão
     */
    
    public Connection(Region orig, Region dest, boolean land, int weight, int risk) {
        this.orig = orig;
        this.dest = dest;
        this.land = land;
        this.weight = weight;
        this.travelRisk = risk;
    }
    
    
}
