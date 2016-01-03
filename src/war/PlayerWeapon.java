
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

    public Weapon getWpn() {
        return wpn;
    }

    public int getTotalsize() {
        return totalsize;
    }

    public void setTotalsize(int totalsize) {
        this.totalsize = totalsize;
    }

    public int getQty() {
        return qty;
    }

    public void setQty(int qty) {
        this.qty = qty;
    }
    
    
    
}
