/*
 * Action: um movimento (ação), a ser armazenado em cada turno.
 */
package war.turn;

import war.GameCharacter;
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
	protected GameCharacter actor;
	/// O player, que é pra descontar os dinheiros e talz
	protected PlayerCharacter player;
	
	/**
	 * Ctor
	 *
	 * @param actor Qual personagem que tá executando a ação
	 */
	public Action (PlayerCharacter player, GameCharacter actor) {
		this.actor = actor;
		this.player = player;
	}

	/**
	 * GETTER pro ator (quem agiu)
	 *
	 * @return Character ator
	 */
	public GameCharacter getCharacter () {
		return this.actor;
	}

	/**
	 * Executa a ação
	 */
	public abstract void execute ();

	/**
	 * Obriga ações a serem Stringificáveis, pra descrição
	 */
	public abstract String toString ();
        
        /***
         * Getter para pegar a descrição curta da ação usada na tabela de ações do turno.
         */
        public abstract String getShortDesc();
}
