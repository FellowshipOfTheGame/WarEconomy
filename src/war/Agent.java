/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package war;

/**
 *
 * @author João
 */
public class Agent extends GameCharacter{
    private int wage;
    private int loyalty; //Medida entre 0-9



    public int getWage() {
        return wage;
    }

    public void setWage(int wage) {
        this.wage = wage;
    }

    public int getLoyalty() {
        return loyalty;
    }

    public void setLoyalty(int loyalty) {
        this.loyalty = loyalty;
    }    

    public void setName(String name) {
        this.name = name;
    }
    
    @Override
    /**
     * Método que cria uma string a partir de todos os stats, incluindo salário e lealdade.
     * @return string com os stats
     */
    public String getStatsString(){
        String stats = ("NAME: " + name + "\n"
                + "INTRIGUE: " + intrigue + "\n"
                + "BARTER: " + barter + "\n"
                + "INVESTIGATION: " + investigation + "\n"
                + "WAGE: " + wage + "\n"
                + "LOYALTY: " + loyalty
                );
        return stats;
    }
    
    /**
     *Construtor de agente.
     */
        public Agent(Region startingRegion) {
            
        this.name = "Agente";
        this.currentPos = startingRegion;
        this.wage = 1;
        this.loyalty = 4;
    }
}
