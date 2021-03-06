/*
 * SellAction: uma ação que um GameCharacter comprou alguma MarketWeapon
 */
package war.turn;

import war.GameCharacter;
import war.PlayerCharacter;
import war.MarketWeapon;
import war.Storable;

import java.lang.IllegalArgumentException;
import static war.Evidence.generateEvidence;
import static war.TestManager.successTest;

/**
 * @briefing Classe da ação de comprar armas no mercado negro
 * @author gilzoide
 * @date 14/04/2016
 * @version 0.1
 * @phase I
 */
public class SellAction extends Action {
	/**
	 * Qual arma está sendo vendida (wrapper do mercado)
	 */
	MarketWeapon wpn;

	/**
	 * De onde arma vendida será retirada
	 */
	Storable store;

	/**
	 * Quantidade da arma que será vendida
	 */
	int quantity;

	/**
	 * Ctor
	 *
	 * @param player Player, pra subtrair os dinheiros
	 * @param actor Qual personagem que tá executando a ação
	 * @param weapon Qual arma está sendo vendida
	 * @param quantity Quantidade a ser vendida
	 * @param store De onde arma vendida será retirada
	 */
	public SellAction (PlayerCharacter player, GameCharacter actor, MarketWeapon weapon, int quantity, Storable store) {
		super (player, actor, false);
		if (quantity <= 0) {
			throw new IllegalArgumentException ("[SellAction] Quantidade de armas vendidadas não pode ser menor que 1 (" + quantity + ")");
		}
		this.wpn = weapon;
		this.quantity = quantity;
		this.store = store;
	}

	@Override
	public void execute () {
		// setta nova oferta do mercado
		wpn.setDemand (wpn.getDemand () - quantity);
		// retira do armazém
		store.remove (wpn.getWpnName (), quantity);
		// e tira os dinheiros do player
                
                int base = quantity * wpn.getSellPrice();
                int bonus = (actor.getBarter() * base)/100; //Porcentagem do barter
		player.setFunds (true, base + bonus );
                //Aumenta notoriedade
                player.setNotoriety(true, quantity * wpn.getWpn().getNotInc());
	
                //Teste para gerar ou nao evidencias.
                if(successTest(actor.getCurrentPos().getOpRisk(), -actor.getIntrigue()))
                    generateEvidence(actor.getCurrentPos(), this);
        }

	@Override
	public String toString () {
		StringBuilder sb = new StringBuilder ();
		sb.append ("SellAction: \"");
		sb.append (actor.getName ());
		sb.append ("\" vendeu ");
		sb.append (quantity);
		sb.append (" \"");
		sb.append (wpn.getWpnName ());
		sb.append ("\", que estava armazenada(s) em \"");
		sb.append (store.getName ());
		sb.append ("\".");
		return sb.toString ();
	}

    @Override
    public String getShortDesc() {
        return ("Sell Action");
    }
    
    @Override
    public void cancel(){}
}


