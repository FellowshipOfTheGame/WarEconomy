/*
 * TravelAction: uma ação que transporta um Character entre regiões
 */
package war.turn;

import war.Character;
import war.Region;

/**
 * @briefing Classe da ação de transporte de personagens
 * @author gilzoide
 * @date 28/03/2016
 * @version 0.1
 * @phase I
 */
public class TravelAction extends Action {
	/**
	 * Região de origem
	 */
	Region from;
	/**
	 * Região de destino
	 */
	Region to;
	/**
	 * Ctor
	 *
	 * Personagem que tá viajando: de currentPos → newPos
	 *
	 * @param actor Qual personagem que tá executando a ação
	 * @param newPos Nova posição
	 */
	public TravelAction (Character actor, Region newPos) {
		super (actor);
		// atualiza percurso
		this.from = actor.getCurrentPos ();
		this.to = newPos;
	}

	public void execute () {
		actor.setCurrentPos (this.to);
	}
}
