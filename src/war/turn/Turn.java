/*
 * Turn: um turno, composto de várias ações, que podem ser desfeitas até que se
 * finalize o turno
 */
package war.turn;

import war.Character;

import java.util.ArrayList;
import java.util.ListIterator;
import java.lang.IndexOutOfBoundsException;
import java.lang.RuntimeException;

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
	/// Coleção de ações do turno
	private ArrayList<Action> actions;
	/// Flag que mostra se turno já acabou
	/// Um turno só pode ser modificado (suas ações) se esse não tiver acabado
	private boolean isOver;

	/**
	 * Ctor
	 */
	public Turn () {
		actions = new ArrayList<Action> ();
		isOver = false;
	}

	/**
	 * Adiciona uma ação ao turno
	 *
	 * @param a Ação a ser adicionada
	 * @throws RuntimeException se turno já foi finalizado
	 */
	public void addAction (Action a) {
		if (!isOver) {
			actions.add (a);
			a.execute ();
		}
		else {
			throw new RuntimeException ("[Turn.addAction] Turno já foi finalizado");
		}
	}

	/**
	 * Remove uma ação da lista de ações, tomando como base o índice dado
	 * 
	 * Note que se uma ação é feita por um Character, qualquer ação feita pelo
	 * mesmo também será removida, visto que a ordem das ações na lista é como
	 * fosse cronológica, no jogo.
	 * <p>
	 * Lembre-se que ações removidas serão revertidas!
	 *
	 * @param index Índice da ação a ser removida
	 * @throws RuntimeException se turno já foi finalizado
	 */
	public void removeAction (int index) {
		if (!isOver) {
			try {
				// pega a lista de ações a partir da que se quer remover;
				// se tiver fora da lista, exceção
				ListIterator<Action> it = actions.listIterator (index);
				// primeira ação (cabeça do iterador), será removida
				Action aux = it.next ();
				// salva o Character ator, pra removermos suas ações seguintes
				Character actor = aux.getCharacter ();

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
		removeAction (actions.indexOf (a));
	}

	/**
	 * Acaba um turno, fazendo com que ações sejam permanentes
	 *
	 * Note que qualquer tentativa de modificação do turno solta um
	 * RuntimeException, já que turnos finalizados devem ser imutáveis
	 *
	 * @throws RuntimeException se turno já foi finalizado
	 */
	public void endTurn () {
		if (!isOver) {
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
		actions.clear ();
	}
}