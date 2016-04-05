/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package war;

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

    /*Necessario para o produto final?*/
    public void setCurrentPos(Region currentPos) {
        this.currentPos = currentPos;
    }
    
}
