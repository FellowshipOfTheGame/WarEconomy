/*
 * Turn: um turno, composto de várias ações, que podem ser desfeitas até que se
 * finalize o turno
 */
package war.turn;

import war.GameCharacter;

import java.util.ArrayList;
import java.util.ListIterator;
import java.lang.IndexOutOfBoundsException;
import java.lang.RuntimeException;
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
	/// Flag que mostra se turno já acabou
	/// Um turno só pode ser modificado (suas ações) se esse não tiver acabado
	private boolean isOver;

	/**
	 * Ctor
	 */
	public Turn () {
		immediateActions = new ArrayList<Action> ();
		endTurnActions = new ArrayList<Action> ();
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
				return aux;
			}
		}
                
		endTurnActions.add (a);
		return null;
	}


	/**
	 * Remove uma ação da lista de ações, tomando como base o índice dado.
	 * 
	 * Note que se uma ação é feita por um Character, qualquer ação feita pelo
	 * mesmo também será removida, visto que a ordem das ações na lista é como
	 * fosse cronológica, no jogo.
	 *
	 * @param index Índice da ação a ser removida
	 * @throws RuntimeException se turno já foi finalizado
	 */
	public void removeAction (int index) {
		if (!isOver) {
			try {
				// pega a lista de ações a partir da que se quer remover;
				// se tiver fora da lista, exceção
				ListIterator<Action> it = immediateActions.listIterator (index);
				// primeira ação (cabeça do iterador), será removida
				Action aux = it.next ();
				// salva o Character ator, pra removermos suas ações seguintes
				GameCharacter actor = aux.getCharacter ();

				it.remove ();

				// percorre os próximos elementos, apagando ação se ator for o mesmo
				while (it.hasNext ()) {
					aux = it.next ();
					if (aux.getCharacter () == actor) {
						it.remove ();
					}
				}
			}
			catch (IndexOutOfBoundsException e) {
				System.out.println ("Ação índice " + index + " não encontrada!");
			}
		}
		else {
			throw new RuntimeException ("[Turn.addAction] Turno já foi finalizado");
		}
	}
	/**
	 * Remove uma ação específica da lista de ações, chamando o overload acima
	 *
	 * @param a Ação a ser removida
	 * @throws RuntimeException se turno já foi finalizado
	 *
	 * @see #removeAction(int)
	 */
	public void removeAction (Action a) {
		removeAction (immediateActions.indexOf (a));
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
		for (Action act : endTurnActions) {
			System.out.println ("  + " + act);
		}
		// reset nos ArrayLists
		immediateActions.clear ();
		endTurnActions.clear ();
	}
        
        
	/**
	 * Cria e retorna a observablelist de ações feitas em um turno, para montar tabela de ações.
	 * @return actionObl lista observável de ações.
	 */
	public ObservableList<Action> getActionsObl(){
		ObservableList<Action> actionObl = FXCollections.observableArrayList();
		// adiciona ações imediatas
		immediateActions.stream()
				.forEach(action -> {
					actionObl.add(action);
				});
		// adiciona ações de fim de turno
		endTurnActions.stream()
				.forEach(action -> {
					actionObl.add(action);
				});
		return actionObl;
	}
}
