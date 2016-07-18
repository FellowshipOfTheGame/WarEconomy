/*
 * Turn: um turno, composto de várias ações, que podem ser desfeitas até que se
 * finalize o turno
 */
package war.turn;

import war.GameCharacter;

import java.util.ArrayList;
import java.lang.RuntimeException;
import java.util.Iterator;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

/**
 * @briefing Classe dos Turnos, composto de ações
 *
 * Turnos guardam as ações feitas pelo jogador, seja pelo
 * <a href="PlayerCharacter">personagem do jogador</a> ou por seus
 * <a href="Agent">agentes</a>.
 *
 * @author gilzoide
 * @date 01/03/2016
 * @version 0.1
 * @phase I
 */
public class Turn {
	/// Coleção de ações imediatas do turno, aquelas que são executadas ao serem
	/// adicionadas aqui
	private ArrayList<Action> immediateActions;
	/// Coleção de ações de fim de turno, aquelas que são executadas só no fim do turno =P
	private ArrayList<Action> endTurnActions;
	/// Coleção de todas ações possíveis, só pra printar lindão na tabela
	private ObservableList<Action> allActions;
	
	/// Flag que mostra se turno já acabou
	/// Um turno só pode ser modificado (suas ações) se esse não tiver acabado
	private boolean isOver;

	/**
	 * Ctor
	 */
	public Turn () {
		immediateActions = new ArrayList<> ();
		endTurnActions = new ArrayList<> ();
		allActions = FXCollections.observableArrayList ();
		isOver = false;
	}

	/**
	 * Adiciona uma ação imediata ao turno.
	 *
	 * Note que essa ação <b>será</b> executada imediatamente, sem choro xP
	 *
	 * @param a Ação imediata a ser adicionada
	 * @throws RuntimeException se turno já foi finalizado
	 */
	public void addAction (Action a) {
		if (!isOver) {
			immediateActions.add (a);
			a.execute ();
			// atualiza pra mostrar na tabela
			updateAllActions ();
		}
		else {
			throw new RuntimeException ("[Turn.addAction] Turno já foi finalizado");
		}
	}

	/**
	 * Marca uma ação a ser executada no fim do turno.
	 *
	 * Cada GameCharacter só pode marcar uma ação por turno, então esse método
	 * verifica se o tal não tem nada marcado ainda, pra ação poder ser sobreposta.
	 *
	 * @return Ação marcada antiga do GameCharacter, null se não tiver
	 */
	public Action scheduleAction (Action a) {
		GameCharacter actor = a.getCharacter ();
		actor.setEndTurnAction(a);
		
		for (int i = 0; i < endTurnActions.size (); i++) {
			Action aux = endTurnActions.get (i);
			if (actor == aux.getCharacter ()) {
				endTurnActions.set (i, a);
				// atualiza pra mostrar na tabela
				updateAllActions ();
				return aux;
			}
		}
		
		endTurnActions.add (a);
		// atualiza pra mostrar na tabela
		updateAllActions ();
		return null;
	}
        
        /***
         * Remove uma data ação da lista de ações agendadas.
         * 
         * @param a Ação a ser removida. 
         */
        public void abortScheduleAction (Action a) {
            endTurnActions.remove(a);
            allActions.remove(a);
        }
        
	/**
	 * Atualiza ObservableList, pra printar na tabela bonitim
	 */
	private void updateAllActions () {
		allActions.clear ();
		allActions.addAll (immediateActions);
		allActions.addAll (endTurnActions);
	}

	/**
	 * Acaba um turno, fazendo com que ações sejam permanentes.
	 *
	 * Executa ações marcadas como de fim de turno.
	 *
	 * Note que qualquer tentativa de modificação do turno solta um
	 * RuntimeException, já que turnos finalizados devem ser imutáveis.
	 *
	 * Realiza funções de decremento de Notoriedade
	 *
	 * @throws RuntimeException se turno já foi finalizado
	 */
	public void endTurn () {
		if (!isOver) {
			// executa ações de fim de turno
			for (Action act : endTurnActions) {
				act.execute ();
			}
			isOver = true;
		}
		else {
			throw new RuntimeException ("[Turn.addAction] Turno já foi finalizado");
		}
	}

	/**
	 * Resetta um turno, começando um turno novo no mesmo objeto
	 *
	 * Apaga as ações que tavam guardadas
	 */
	public void reset () {
		isOver = false;
		// log
		System.out.println ("[Turn.reset] Ações executadas:");
		for (Action act : immediateActions) {
			System.out.println ("  - " + act);
		}
                
                allActions.removeAll(immediateActions);
                immediateActions.clear();
                
                for (Iterator<Action> it = endTurnActions.iterator(); it.hasNext();){
                    Action act = it.next();
                    if(! act.getReschedule()){
                        allActions.remove(act);
                        act.actor.setEndTurnAction(null);
                        it.remove();
                    }
                }
                
                /*
		for (Action act : endTurnActions) {
			System.out.println ("  + " + act);
                        if(! act.getReschedule()){
                            //Ação não reagendável. Remova das listas e da ligação com o Actor.
                            endTurnActions.remove(act);
                            allActions.remove(act);
                            act.actor.setEndTurnAction(null);
                        }
                }*/
		// reset nas listas de ações
		//immediateActions.clear ();
		//endTurnActions.clear ();
		// nem precisa chamar 'updateAllActions', já que tamo só limpando tudo
		//allActions.clear ();
	}
        
	/**
	 * Cria e retorna a observablelist de ações feitas em um turno, para montar tabela de ações.
	 * @return actionObl lista observável de ações.
	 */
	public ObservableList<Action> getActionsObl (){
		return allActions;
	}
}
