/*
 * Action: um movimento (ação), a ser armazenado em cada turno.
 */
package war.turn;

import war.Character;
import war.PlayerCharacter;

/**
 * @briefing Classe abstrata das Ações de Characters, que compõem os turnos
 * @author gilzoide
 * @date 29/02/2016
 * @version 0.1
 * @phase I
 */
public abstract class Action {
	/// Quem está fazendo a ação
	protected Character actor;
	/// O player, que é pra descontar os dinheiros e talz
	protected PlayerCharacter player;
	
	/**
	 * Ctor
	 *
	 * @param actor Qual personagem que tá executando a ação
	 */
	public Action (PlayerCharacter player, Character actor) {
		this.actor = actor;
		this.player = player;
	}

	/**
	 * GETTER pro ator (quem agiu)
	 *
	 * @return Character ator
	 */
	Character getCharacter () {
		return this.actor;
	}

	/**
	 * Executa a ação
	 */
	public abstract void execute ();
}