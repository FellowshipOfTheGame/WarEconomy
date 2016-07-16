/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package war;

import java.util.Random;
import javafxStuff.GameController;

/**
 *
 * @author João
 */
public class TestManager {
    
    
    /*========================================================================*/
    /*FUNÇÕES GENÉRICAS*/
    
    
    /***
     * Realiza um teste de sucesso padrão. 
     * Joga 1d100 e verifica se o valor esta baixo de atr +- modifiers
     * @param atr Atributo a ser testado
     * @param posMod Modificadores positivos, aumentam atr para o teste.
     * @param negMod Modificadores negativos, diminuem atr para o teste.
     * @return True se passou no teste, false se falhou no teste
     */
    public static boolean successTest(int atr, int posMod, int negMod){
        
        if( GameController.checkIntRange(atr, 1, 100, true) ){//Verificando se o número está dentro do limite estabelecido.
        
            Random diceRoll = new Random();
            //random.nextInt(max - min + 1) + min
            int result = diceRoll.nextInt(100) + 0;

            System.out.println("Jogou dado!\t RESULT: " + result + " atr: " + atr);
            if(result <= atr + posMod - negMod){
                return true;//Sucesso
            }
            else
                return false;//Falha
        }
        else
            System.err.println("Atr fora de Range");
            return false;
    }
    
    public static boolean opositeTest(){
        return false;
    }
    
    public static boolean prolongedTest(){
        return false;
    }
    
    
    
    
    /*========================================================================*/
    /*TESTES ESPECÍFICOS*/
    
    
    
    /***
     * Método que realiza um teste de noise em uma determinada região.
     * Ele é chamado quando o jogador usa "moveTransports()"
     * Teste é feito se o transporte esta carregando armas.
     * @return TRUE se o teste passou e o transporte não chamou atenção das autoridades
     *         FALSE se o transporte falhou e chamou atenção.
     */
    public static boolean makeNoiseTest(Transport t){
        System.out.println("Making Noise Test");
        //Se o transporte está se movendo e tem carga
        if(t.getCurrentConnection() != null && t.getUsedCapacity() != 0){
            //random.nextInt(max - min + 1) + min
            int atr = t.getCurrentConnection().getTravelRisk();
            int posMod = t.getNoise(); //Noise pode ser negativo também nesse caso.
            int negMod = 0 ;//+ t.getCurrentConnection().getTravelRiskDebuff();
            
            //Conexão passou no teste.
            if( successTest(atr, posMod, negMod) ){
                System.out.println("Transporte Falhou teste de noise, pista pode ser gerada");
                return false;
            }
            else
                return true;
        }
        return true;
    }
}
