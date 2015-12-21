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
public class Weapon {
	public static String[] neededFields = new String[] {
		"basePrice", "margin", "heatInc", "size", "combEfecBonus"
	};
    
	/**
	 * Ctor parametrizado: cria com tudo já ;]
	 */
	public Weapon (String name, int basePrice, int margin, int heatInc, int size, int combEfecBonus, int category) {
		this.name = name;
		this.basePrice = basePrice;
		this.margin = margin;
		this.heatInc = heatInc;
		this.size = size;
		this.combEfecBonus = combEfecBonus;
                this.category = category;
	}
	
	/// Propriedades
    protected String name;
    protected int basePrice;
    protected int margin;
    protected int heatInc;
    protected int size;
    protected int combEfecBonus;
    protected int category;//numero de 0-7 que indica em qual das categorias a arma se encaixa. Usa a ordem que esta no GDD e não inclui armas unicas.
	/// GETTERS
	public String getName () {
		return name;
	}
	public int getBasePrice () {
		return basePrice;
	}
	public int getMargin () {
		return margin;
	}
	public int getHeatInc () {
		return heatInc;
	}
	public int getSize () {
		return size;
	}
	public int getCombEfecBonus () {
		return combEfecBonus;
	}
}
