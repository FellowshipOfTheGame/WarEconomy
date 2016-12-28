/*
 * TravelAction: uma ação que transporta um Character entre regiões
 */
package war.turn;

import war.GameCharacter;
import war.PlayerCharacter;
import war.Connection;

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
	 * Conexão da viagem
	 */
	Connection connection;
        
        /**
         * Destino da viagem, usada na maneira alternativa de Travel, em que em um turno o actor viaja para qualquer lugar do mundo.
         */
        Region destination;
        
        /***
         * Origem da viagem.
         */
        Region origin;
        
	/**
	 * Ctor
	 *
	 * Personagem que tá viajando: de currentPos → newPos
	 *
	 * @param player Jogador
	 * @param actor Qual personagem que tá executando a ação
	 * @param newPos Nova posição
	 */
	public TravelAction (PlayerCharacter player, GameCharacter actor, Connection connection) {
		super (player, actor, false);
		if (actor.getCurrentPos () != connection.getOrigin ()) {
			throw new IllegalArgumentException ("[TravelAction] Character \"" + actor.getName () + "\" não está na posição inicial da Connection");
		}
		this.connection = connection;
		this.origin = connection.getOrigin();
		this.destination = connection.getDestination();
	}
        
        /***
         * Construtor alternativo
         * Modifica a posição do actor para a região de uma vez só.
         * @param player
         * @param actor
         * @param destination 
         */
        public TravelAction (PlayerCharacter player, GameCharacter actor, Region destination){
            super (player, actor, false);
            this.origin = actor.getCurrentPos();
            this.destination = destination;
            
        }
        
	@Override
	public void execute () {
            if(destination!=null){
		if(connection != null){//Alternativa em que o personagem percorre as conexões
			player.setFunds (false, connection.getWeight ());
			actor.setCurrentPos (destination);
			actor.setEndTurnAction(null);
		}
		else{//Alternativa em que o personagem chega em um turno
			player.setFunds(false, 1);
			actor.setCurrentPos(destination);
			actor.setEndTurnAction(null);
		}
            }
	}

	@Override
	public String toString () {
		StringBuilder sb = new StringBuilder ();
		sb.append ("TravelAction: \"");
		sb.append (actor.getName ());
		sb.append ("\" foi de \"");
		sb.append (origin.getName ());
		sb.append ("\" para \"");
		sb.append (destination.getName ());
		sb.append ("\".");
		return sb.toString ();
	}

    @Override
    public String getShortDesc() {
        return("Travel Action");
    }
    
    @Override
    public void cancel(){
    }
}
