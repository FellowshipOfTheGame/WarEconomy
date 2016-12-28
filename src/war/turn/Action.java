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
        /// Indicador se a ação deve ser re-agendada no próximo turno.
        protected Boolean reschedule;
	
	/**
	 * Ctor
	 *
	 * @param player Jogador
	 * @param actor Qual personagem que tá executando a ação
         * @param reschedule true se a ação deve ser reagendada no próximo turno
	 */
	public Action (PlayerCharacter player, GameCharacter actor, Boolean reschedule) {
		this.actor = actor;
		this.player = player;
                this.reschedule = reschedule;
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
         * GETTER pro jogador
         * @return PlayerCharacter player
         */
        public PlayerCharacter getPlayer() {
            return player;
        }
        
        /**
         * Getter para saber se uma ação é ou não remarcável automaticamente.
         * @return 
         */
        public Boolean getReschedule() {
            return reschedule;
        }

	/**
	 * Executa a ação
	 */
	public abstract void execute ();

	/**
	 * Obriga ações a serem Stringificáveis, pra descrição
	 * 
	 * @return String representando ação
	 */
	@Override
	public abstract String toString ();
        
	/***
	 * Getter para pegar a descrição curta da ação usada na tabela de ações do turno.
	 * 
	 * @return Descrição curta da ação
	 */
	public abstract String getShortDesc();
        
        /**
         * Método para realizar quaisquer ações necessárias para que a ação seja
         * abortada / cancelada com sucesso. DEVE DEIXAR PLAYER COMO NULO.
         */
        public abstract void cancel();
}
