/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package javafxStuff;

import java.net.URL;
import java.util.ResourceBundle;
import javafx.beans.value.ObservableValue;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.MenuButton;
import javafx.scene.control.MenuItem;
import javafx.scene.control.TableCell;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableColumn.CellDataFeatures;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.text.Text;
import war.MarketWeapon;
import war.PlayerCharacter;
import war.World;

/**
 * FXML Controller class
 * @briefing Classe que controlará a lógica principal do jogo
 * @author João Victor L. da S. Guimarães
 * @date 10/12/2015
 * @version 0.1
 * @phase I
 */

public class GameController implements Initializable {
    
    /*ELEMENTOS DE FXML*/
    @FXML Button bt;
    
    //TAB de MERCADO NEGRO
    @FXML ImageView selectedWpnImg;
    @FXML Text selectedWpnDescr;
    @FXML TableView<MarketWeapon> marketTable;
    @FXML TableColumn<MarketWeapon, String> nameColumn;
    @FXML TableColumn<MarketWeapon, String> catColumn;
    @FXML TableColumn<MarketWeapon, Integer> sellColumn;
    @FXML TableColumn<MarketWeapon, Integer> buyColumn;    
    @FXML TableColumn<MarketWeapon, Integer> supColumn;
    @FXML TableColumn<MarketWeapon, Integer> demColumn;      
    
    
    //TAB do MAPA
    @FXML MenuItem trvEmm;
    @FXML MenuItem trvOsea;
    @FXML MenuItem trvYuk;    
    @FXML MenuItem trvWel;    
    
    
    @FXML Label guiFunds;
    @FXML Label guiHeat;
    @FXML Label guiPlayerPos;    
    
/*------------------------------------------------------------------------------*/    
    /*ATRIBUTOS*/
    PlayerCharacter player;
    World world;
    
    MarketWeapon selectedWeapon;

/*------------------------------------------------------------------------------*/    
    /*METODOS*/
    @FXML
    public void selectMarketWeapons(){
        if(marketTable.getSelectionModel().getSelectedIndex() >=0 ){//Clicou em um index valido
            if(this.selectedWeapon != marketTable.getSelectionModel().getSelectedItem()) {//Arma diferente da 
                //seta a referencia à arma clicada
                this.selectedWeapon = marketTable.getSelectionModel().getSelectedItem(); 
                //Seta a imageview à foto da arma clicada
                String imagePath = "/images/" + this.selectedWeapon.getWpnCat() + "/" + this.selectedWeapon.getWpnName() + ".png";
                System.out.println("Clicou na " + this.selectedWeapon.getWpnName() + "-chan" + "\n" + imagePath);
                Image updatedWpnImg = new Image(imagePath, false);
                this.selectedWpnImg.setImage(updatedWpnImg);
                //Seta o texto da descrição para a arma clicada
                this.selectedWpnDescr.setText(this.selectedWeapon.getWpn().getDescription());
            }

        }
    
    }
    
    
    public void initializeMarketTable(TableView marketTable){
        
        nameColumn.setCellValueFactory(new PropertyValueFactory<MarketWeapon, String>("wpnName"));
        catColumn.setCellValueFactory(new PropertyValueFactory<MarketWeapon, String>("wpnCat"));

        sellColumn.setCellValueFactory(new PropertyValueFactory<MarketWeapon, Integer>("sellPrice"));
        buyColumn.setCellValueFactory(new PropertyValueFactory<MarketWeapon, Integer>("buyPrice"));
        supColumn.setCellValueFactory(new PropertyValueFactory<MarketWeapon, Integer>("supply"));
        demColumn.setCellValueFactory(new PropertyValueFactory<MarketWeapon, Integer>("demand"));             
        
        ObservableList obl = (ObservableList) this.player.getCurrentPos().getLocalMarket().getAvailableWeapons();
        
        marketTable.setItems(obl);
    }    
    
    @FXML
    /*Quando chegar a hora, monta uma trade-mission*/
    public void playerTravel(ActionEvent e){
       
        
        if(e.getSource() == trvEmm)
            player.setCurrentPos(this.world.getRegion(0));
        else if(e.getSource() == trvWel)
            player.setCurrentPos(this.world.getRegion(1));
        else if(e.getSource()==trvYuk)
            player.setCurrentPos(this.world.getRegion(2));
        else if(e.getSource()==trvOsea)
            player.setCurrentPos(this.world.getRegion(3));      
        this.updateEssentialInfo();
    }
    
    /*Atualiza info no HUD*/
    private void updateEssentialInfo(){
        this.guiHeat.setText("HEAT: " + this.player.getHeat());
        this.guiFunds.setText("FUNDS: "+ this.player.getFunds());
        this.guiPlayerPos.setText("CURRENT POSITION: " + this.player.getCurrentPos().getName());
    }
    
    /**
     * Initializes the controller class.
     * Incializa o jogador e outras informações baseada na string que será passada do menu de new/load game. A string é o nome do arquivo de save
     *
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        this.selectedWeapon = null;
        this.world = new World();
        this.player = new PlayerCharacter("default",this.world.getRegion(0));
        this.updateEssentialInfo();
        this.initializeMarketTable(marketTable);
    }    
    
}
