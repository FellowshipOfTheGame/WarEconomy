/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package war;

import war.turn.Action;

import java.lang.IllegalArgumentException;

/**
 * FXML Controller class
 * @briefing Classe dos personagens. Abstrata, pai do personagem do jogador e dos agentes.
 * @author João Victor L. da S. Guimarães
 * @date 09/12/2015
 * @version 0.1
 * @phase I
 */
public class GameCharacter {
    protected String name;
    protected int intrigue;
    protected int barter;
    protected int investigation;

	/// Ação marcada pra rolar ao fim do turno, suceptível a mudanças
    private Action endTurnAction = null;

    public int getInvestigation() {
        return investigation;
    }

    public void setInvestigation(int investigation) {
        this.investigation = investigation;
    }
    protected Region currentPos;

    public String getName() {
        return name;
    }

    public int getIntrigue() {
        return intrigue;
    }

    public int getBarter() {
        return barter;
    }

    public void setIntrigue(int intrigue) {
        this.intrigue = intrigue;
    }

    public void setBarter(int barter) {
        this.barter = barter;
    }

    public Region getCurrentPos() {
        return currentPos;
    }

    /**
     * GETTER pra ação marcada pro fim do turno
     */
    public Action getEndTurnAction () {
            return endTurnAction;
    }
    
    public String getEndTurnActionDesc(){
        if(endTurnAction == null)
            return "Standing By";
        else
            return endTurnAction.getShortDesc();
    }
    
    /**
     * SETTER pra ação marcada pro fim de turno
     */
    public void setEndTurnAction (Action action) {
            // se ator da ação não for esse aqui, tá errado
            if (action != null && action.getCharacter () != this) {
                    throw new IllegalArgumentException ("[GameCharacter.setEndTurnAction] Character \""
                                    + name + "\" não é o ator da Action de fim de turno dada");
            }

            this.endTurnAction = action;
    }
    
    /**
     * Método que cria uma string a partir de todos os stats.
     * @return string com os stats
     */
    public String getStatsString(){
        String stats = ("NAME: " + name + "\n"
                + "INTRIGUE: " + intrigue + "\n"
                + "BARTER: " + barter + "\n"
                + "INVESTIGATION: " + investigation + "\n"
                );
        return stats;
    }
    
    /*Necessario para o produto final?*/
    public void setCurrentPos(Region currentPos) {
        this.currentPos = currentPos;
    }
    
    /***
     * Método toString.
     * @return retorna o nome do personagem
     */
    public String toString(){
        return name;
    }
}
