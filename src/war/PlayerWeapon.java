
package war;

/**
 *
 * @author João
 */
public class PlayerWeapon {
    private Weapon wpn;//Referencia a uma Weapon do mundo. 
    private int totalsize;
    private int qty;

    public PlayerWeapon(Weapon wpn, int totalsize, int qty) {//Objeto deve ser apenas criado quando o jogador COMPRA uma arma do mercado
        this.wpn = wpn;
        this.totalsize = totalsize;
        this.qty = qty;
    }
    
    
}
