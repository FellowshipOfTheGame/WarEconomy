
package javafxStuff;

import javafxStuff.MainMenuController;
import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.animation.Animation;
import javafx.animation.Transition;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import javafx.util.Duration;
import war.PlayerCharacter;

/**
 * FXML Controller class
 * @briefing Classe controladora do menu de novo jogo
 * @author João Victor L. da S. Guimarães
 * @date 09/12/2015
 * @version 0.1
 * @phase I
 */
public class NewGameController implements Initializable {
    
    @FXML Button newGamebtn;
    @FXML Button mainMenubtn;
    
    @FXML Button incIntrigue;
    @FXML Button decIntrigue;
    @FXML Button incBarter;
    @FXML Button decBarter;
    @FXML Button incInvestigation;
    @FXML Button decInvestigation;
    
    @FXML TextField nameText;
    
    @FXML Label newGameText;
    
    @FXML Label atrPoints;
    @FXML Label atrIntriguePoints;
    @FXML Label atrBarterPoints;
    @FXML Label atrInvestigationPoints;
    
    
    
    private String name = "Dealer";//Nome padrão para o jogador.

    private int points = 5;
    private int barter = 20;
    private int intrigue = 20;
    private int investigation = 20;
    private int funds = 500;
    
    private final int upperBound = 40;
    private final int lowerBound = 10;
    
    private final String newGameTextStr = "	The year is 2001.\n" +
    "\n" +
    "	The world has become a very dangerous place. All over the globe, the flames of war burn bright, fueled by those such as yourself.\n" +
    "\n" +
    "	You are an war economist, a gun runner, an opportunist. Your job, to sell weaponry to anyone willing to pay, regardless of their motivations or methods.\n" +
    "To escape the eyes of the law and governments, the hypocrites who blame merchants such as yourself for their incompetence in maintaining peace, is mere routine.\n" +
    "\n" +
    "	Your motto: Death is profitable";

    
    
//==============================================================================    
/*METODOS*/
    
   
    /**
     * Método para fazer animação estilo Typewriter.
     * @author: Harshita Sethi
     * 
     * FONTE: 
     * http://stackoverflow.com/questions/27177137/javafx-typewriter-effect-for-label
     * @param lbl
     * @param descImp 
     */
    
    public void animateText(Label lbl, String descImp) {
        String content = descImp;
        final Animation animation = new Transition() {
            {
                setCycleDuration(Duration.seconds(15));
            }

            @Override
            protected void interpolate(double frac) {
                final int length = content.length();
                final int n = Math.round(length * (float) frac);
                lbl.setText(content.substring(0, n));
            }
        };
        animation.play();
    }
    
    private void update(){
        atrPoints.setText("PICK YOUR STATS\n"+points+" PTS REMAINING");
        atrIntriguePoints.setText(""+intrigue);
        atrBarterPoints.setText(""+barter);
        atrInvestigationPoints.setText(""+investigation);
    }
    
    @FXML
    
    private void incStat(ActionEvent event){
        if(points > 0){
            if(event.getSource().equals(incBarter) && barter < upperBound){
                barter+=5;
                points--;
                update();
            }
            else if(event.getSource().equals(incIntrigue) && intrigue < upperBound){
                intrigue+=5;
                points--;
                update();                
            }
            else if(event.getSource().equals(incInvestigation) && investigation < upperBound){
                investigation+=5;
                points--;
                update();
            }
        }
    }
    
        @FXML
    
    private void decStat(ActionEvent event){
        if(7>points){
            if(event.getSource().equals(decBarter) && barter > lowerBound){
                barter-=5;
                points++;
                update();                
            }
            else if(event.getSource().equals(decIntrigue) && intrigue > lowerBound){
                intrigue-=5;
                points++;
                update();                        
            }
            else if(event.getSource().equals(decInvestigation) && investigation > lowerBound){
                investigation-=5;
                points++;
                update();                        
            }
        }
    }
    
    
    @FXML
    /**
     * Cria um novo arquivo de save (xml, talvez). Então muda para a cena do jogo em si.
     * O metodo de Initialize do controlador da cena de jogo então lerá o arquivo e criará o objeto do jogador.
     */
    private void startGame(){
        /*TODO - Criação de save*/
        /*Switch para o jogo em si*/
        Parent root = null;
        Stage stg = (Stage)newGamebtn.getScene().getWindow();
        String filename = "Game.fxml";
        
        try {
            FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource(filename));

            root = (Parent) fxmlLoader.load();
            //Referencia para o controlador da classe Game Controller
            GameController controller = fxmlLoader.<GameController>getController();
            
            if(nameText.getText().compareTo("") != 0){//Colocou nome próprio
                name = nameText.getText();
            }
            
            controller.initialize(name, barter, intrigue, investigation , funds);
            stg.getScene().setRoot(root);
            
        } catch (IOException ex) {
            Logger.getLogger(MainMenuController.class.getName()).log(Level.SEVERE, null, ex);
        }
        
        /*Scene game = new Scene(root);
        //Cria a cena do novo jogo
        stg.hide();
        stg.setScene(game);
        stg.show();*/
    }
    

    public void switchToMainMenu(ActionEvent event){
        
        Parent root = null; //Referencia a outra tela
        Stage stg = (Stage)mainMenubtn.getScene().getWindow();//Referencia ao Stage atual
        String filename = "MainMenu.fxml";

        try {
        root = FXMLLoader.load(getClass().getResource(filename));
        } catch (IOException ex) {
            Logger.getLogger(MainMenuController.class.getName()).log(Level.SEVERE, null, ex);
        }
        
        stg.getScene().setRoot(root);
    }
    
    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        update();
        animateText(newGameText, newGameTextStr);
    }    
    
}
