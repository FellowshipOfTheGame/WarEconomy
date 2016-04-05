/*
 * TravelAction: uma ação que transporta um Character entre regiões
 */
package war.turn;

import war.GameCharacter;
import war.PlayerCharacter;
import war.Connection;

import java.lang.IllegalArgumentException;

/**
 * @briefing Classe da ação de transporte de personagens
 * @author gilzoide
 * @date 28/03/2016
 * @version 0.1
 * @phase I
 */
public class TravelAction extends Action {
	/**
	 * Conexão da viagem
	 */
	Connection travel;
	/**
	 * Ctor
	 *
	 * Personagem que tá viajando: de currentPos → newPos
	 *
	 * @param actor Qual personagem que tá executando a ação
	 * @param newPos Nova posição
	 */
	public TravelAction (PlayerCharacter player, GameCharacter actor, Connection travel) {
		super (player, actor);
		if (actor.getCurrentPos () != travel.getOrigin ()) {
			throw new IllegalArgumentException ("[TravelAction] Character \"" + actor.getName () + "\" não está na posição inicial da Connection");
		}
		this.travel = travel;
	}

	@Override
	public void execute () {
		player.setFunds (false, travel.getWeight ());
		actor.setCurrentPos (travel.getDestination ());
	}
}