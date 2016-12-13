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
     * @param mod modificadores situacionais. Somar é deixar o teste mais fácil ao aumentar o número alvo
     * @return True se passou no teste, false se falhou no teste
     */
    public static boolean successTest(int atr, int mod){
                
        if( GameController.checkIntRange(1, 100, atr, true) ){//Verificando se o número está dentro do limite estabelecido.
        
            Random diceRoll = new Random();
            //random.nextInt(max - min + 1) + min
            int result = diceRoll.nextInt(100) + 0;

            System.out.println("Jogou dado!\t RESULT: " + result + " atr+mods: " + (atr+mod));
            if(result <= atr + mod){
                return true;//Sucesso
            }
            else
                return false;//Falha
        }
        else {
            System.err.println("Atr fora de Range");
            return false;
        }
    }
    
    /**
     * INCOMPLETO.
     * Realiza um teste oposto entre duas entidades.
     * @param atr1 Atributo da entidade 1
     * @param posMod1 Modificador positivo da entidade 1
     * @param negMod1 Modificador negativo da entidade 1
     * @param atr2 Atributo da entidade 2
     * @param posMod2 Modificador positivo da entidade 2
     * @param negMod2 Modificador negativo da entidade 2
     * @return 
     */
    public static boolean opositeTest(int atr1, int posMod1, int negMod1 , int atr2, int posMod2, int negMod2){
       
        if( GameController.checkIntRange(1, 100, atr1, true) && GameController.checkIntRange(1, 100, atr2, true)){//Verificando se o número está dentro do limite estabelecido.
        
            Random diceRoll = new Random();
            //random.nextInt(max - min + 1) + min
            int result1 = diceRoll.nextInt(100) + 0;
            int result2 = diceRoll.nextInt(100) + 0;

            //Entidade 1 passou no teste e a entidade 2 falhou
            if(result1 <= atr1 + posMod1 - negMod1 && result2 > atr2 + posMod2 - negMod2)
                return false;
            
            //Entidade 2 passou no teste e a entidade 1 falhou
            else if(result1 > atr1 + posMod1 - negMod1 && result2 <= atr2 + posMod2 - negMod2)
                return true;
            
            //Ambos passaram, seleciona o menor, em caso de empate, seleciona o 1
            else if (result1 <= atr1 + posMod1 - negMod1 && result2 <= atr2 + posMod2 - negMod2) {
                
                if(result1 < result2)
                    return false;
                else if(result2 < result1)
                    return true;
                else if(result1 == result2)
                    return false;
            }
            
            //Ambos falharam
            else{
                
            }
        }
        else 
            System.err.println("Atr fora de Range");
        
        return false;
    }
    
    public static boolean prolongedTest(){
        return false;
    }
    
    /**
     * Joga um dado e retorna o resultado entre 1 e n.
     * @param n Numéro de faces do dado
     * @return resultado do dado. Entre 1 e n
     */
    public static int rollDie(int n){
        //random.nextInt(max - min + 1) + min
        Random diceRoll = new Random();
        int result = diceRoll.nextInt(n) + 1;
        return result;
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

            int atr = t.getCurrentConnection().getTravelRisk();
            int posMod = t.getNoise(); //Noise pode ser negativo também nesse caso.
            int negMod = 0 ;//+ t.getCurrentConnection().getTravelRiskDebuff();
            
            //Conexão passou no teste.
            if( successTest(atr, posMod - negMod) ){
                System.out.println("Transporte Falhou teste de noise, pista pode ser gerada");
                return false;
            }
            else
                return true;
        }
        return true;
    }
}
