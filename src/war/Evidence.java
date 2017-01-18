/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package war;

import static war.TestManager.rollDie;
import war.law.Investigator;
import war.turn.*;

/**
 *
 * @author João
 */
public class Evidence {
    
    private int difficultyModifier;//Modificador negativo ao teste de Intriga para encontra a evidência ou não.
    private int requiredHits;
    private int timer;
    private int heatInc;
    
    private boolean isVisible; //Se a evidência foi encontrada ou não.
    private Region reg; //A região aonde a evidência está localizada.
    private Action cause;//Ação que causou a geração da evidência.
    
    private Investigator investigator;//Ponteiro para o investigador dessa evidência. Só 1 por evidência
    
    /*private SimpleStringProperty difficultyString;
    private SimpleStringProperty timerString;
    private SimpleStringProperty causeString;
    private SimpleStringProperty heatString;*/


    public Action getCause() {
        return cause;
    }
    
    public Class getCauseType() {
        System.out.println("CAUSEEE : " + cause.toString());
        return cause.getClass();
    }    

    public int getDificultyModifier() {
        return difficultyModifier;
    }

    public void setDificultyModifier(int dificultyModifier) {
        this.difficultyModifier = dificultyModifier;
    }

    public int getRequiredHits() {
        return requiredHits;
    }

    public void setRequiredHits(int requiredHits) {
        this.requiredHits = requiredHits;
    }

    public int getTimer() {
        return timer;
    }

    public void setTimer(int timer) {
        this.timer = timer;
    }

    public int getHeatInc() {
        return heatInc;
    }

    public void setHeatInc(int heatInc) {
        this.heatInc = heatInc;
    }

    public boolean isIsVisible() {
        return isVisible;
    }

    public void setIsVisible(boolean isVisible) {
        this.isVisible = isVisible;
    }

    public Region getReg() {
        return reg;
    }

    public void setReg(Region reg) {
        this.reg = reg;
    }

    /*public String getDifficultyString() {
        this.difficultyString = new SimpleStringProperty("diff");
        return this.difficultyString.get();
    }*/
    
    public String getDifficultyString(){
        
        String str = "";
        
        if(difficultyModifier >= 30)
            str = "Trivial";
        
        else if ( 30 > difficultyModifier && difficultyModifier > 0)
            str = "Favorable";        
        
        else if (difficultyModifier == 0)
            str = "Average";
        
        else if ( 0 > difficultyModifier && difficultyModifier > -30)
            str = "Chalenging";
        
        else if (difficultyModifier <= -30)
            str = "Very Hard";        
        return str;
    }

    public String getTimerString() {
        return "Time remaining: " + timer;
    }

    public String getCauseString() {
        return cause.getShortDesc();
    }

    public String getHeatString() {
        
        String str = "";
        
        if(heatInc >= 30)
            str = "Highly Dangerous";
        
        else if ( 30 > heatInc && heatInc > 15)
            str = "Dangerous";        
        
        else if (heatInc < 15)
            str = "Threatening";
        
        return str;
    }
    
    public void setInvestigator(Investigator inv){
        this.investigator = inv;
    }
    
    public Investigator getInvestigator(){
        return this.investigator;
    }
    
    public void printEvidence(){
        System.out.println("\tDificulty " + this.difficultyModifier 
                + " Timer " + this.timer 
                + " HeatInc " + this.heatInc
                + " RH " + this.requiredHits);
    }
    
    /***
     * Método utilizado para decrementar o timer da evidência.
     * @return boolean para manter(true) ou não na lista de evidências da região
     */
    public boolean decrementTimer(){
        this.timer--;
        if(timer > 0) {
            this.printEvidence();
            return true;
        }
        else
            return false;
    }
    
    /**
     * Método chamado para gerar uma evidência.
     * Ele é chamado nas ações que podem acarretar a criação de uma evidência,
     * como as de compra, venda, moveTransports(), etc.
     * @param reg Região em que a evidência ficará
     * @param cause A Ação que causou a evidência
     * @return 
     */
    public static boolean generateEvidence(Region reg, Action cause){
        

        int dificultyModifier;//Modificador negativo ao teste de Intriga para encontra a evidência ou não.
        int requiredHits;
        int timer ;
        int heatInc;
    
        
        //Calcular atributos da evidencia baseado nas informações da causa
        if (cause == null){//açao de movimento de transporte 
            dificultyModifier = 10;
            timer = rollDie(6);
            heatInc = rollDie(5);
            requiredHits = rollDie(2);           
        }

        else{//demais acoes
            //variar por causa e as informações dela.

            /*if(cause.getClass() == BuyAction.class || cause.getClass() == SellAction.class){

            }
            else if(cause.getClass())*/

            dificultyModifier = 0;
            timer = rollDie(6);
            heatInc = rollDie(5);
            requiredHits = rollDie(2);          
        }
        
        //--------
        
        //Não há uma evidencida do mesmo tipo na região.
        Evidence evi = reg.getEvidenceByCause(cause);
        if(evi == null){
            evi = new Evidence(reg, cause, dificultyModifier, timer, heatInc, requiredHits);
            reg.addEvidence(evi);
        }
        //Há uma evidência do mesmo tipo
        else {
            evi.setHeatInc(evi.getHeatInc() + heatInc);
            evi.setDificultyModifier(evi.getDificultyModifier() + dificultyModifier);
            evi.setRequiredHits(evi.getRequiredHits() + requiredHits);
            evi.setTimer(evi.getTimer() + timer);
            evi.printEvidence();
        }
        return false;
    }
    

    /*ctor*/
    public Evidence(Region reg, Action cause, int dificulty, int timer, int heatInc, int requiredHits) {
        this.reg = reg;
        this.cause = cause;
        this.isVisible = false;
        this.difficultyModifier = dificulty;
        this.timer = timer;
        this.heatInc = heatInc;
        this.requiredHits = requiredHits;
        this.investigator = null;
        
        if(cause != null)
            System.out.println("Nova Evidencia: REG " + reg.getName() + " CAUSE: " + cause.getShortDesc());
        else
            System.out.println("Nova Evidencia: REG " + reg.getName() + " CAUSE: Mov de transporte");

        printEvidence();
    }
    
    
    
    
}
