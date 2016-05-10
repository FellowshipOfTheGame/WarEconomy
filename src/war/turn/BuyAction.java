/*
 * BuyAction: uma ação que um GameCharacter comprou alguma MarketWeapon
 */
package war.turn;

import war.GameCharacter;
import war.PlayerCharacter;
import war.MarketWeapon;
import war.Storable;

import java.lang.IllegalArgumentException;

/**
 * @briefing Classe da ação de comprar armas no mercado negro
 * @author gilzoide
 * @date 14/04/2016
 * @version 0.1
 * @phase I
 */
public class BuyAction extends Action {
	/**
	 * Qual arma está sendo comprada (wrapper do mercado)
	 */
	MarketWeapon wpn;

	/**
	 * Onde arma comprada será armazenada
	 */
	Storable store;

	/**
	 * Quantidade da arma que será comprada
	 */
	int quantity;

	/**
	 * Ctor
	 *
	 * @param player Player, pra subtrair os dinheiros
	 * @param actor Qual personagem que tá executando a ação
	 * @param weapon Qual arma está sendo comprada
	 * @param quantity Quantidade a ser comprada
	 * @param store Onde compra será armazenada
	 */
	public BuyAction (PlayerCharacter player, GameCharacter actor, MarketWeapon weapon, int quantity, Storable store) {
		super (player, actor);
		if (quantity <= 0) {
			throw new IllegalArgumentException ("[BuyAction] Quantidade de armas compradas não pode ser menor que 1 (" + quantity + ")");
		}
		this.wpn = weapon;
		this.quantity = quantity;
		this.store = store;
	}

	@Override
	public void execute () {
		// setta nova oferta do mercado
		wpn.setSupply (wpn.getSupply () - quantity);
		// guarda no armazém
		store.store (wpn.getWpn (), quantity);
		// e tira os dinheiros do player
		player.setFunds (false, quantity * wpn.getBuyPrice ());
	}

	@Override
	public String toString () {
		StringBuilder sb = new StringBuilder ();
		sb.append ("BuyAction: \"");
		sb.append (actor.getName ());
		sb.append ("\" comprou ");
		sb.append (quantity);
		sb.append (" \"");
		sb.append (wpn.getWpnName ());
		sb.append ("\", armazenada(s) em \"");
		sb.append (store.getName ());
		sb.append ("\".");
		return sb.toString ();
	}

    @Override
    public String getShortDesc() {
        return ("Buy Action");
    }
}

