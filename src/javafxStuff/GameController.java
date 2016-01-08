/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package javafxStuff;

import java.net.URL;
import java.util.ResourceBundle;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.MenuItem;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.text.Text;
import war.MarketWeapon;
import war.PlayerCharacter;
import war.Region;
import war.Storable;
import war.Warehouse;
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
    @FXML Label totalBuyPrice;
    @FXML Label ReqCargoSpace;
    @FXML Label marketName;
    @FXML Label transactionOutput;
    @FXML Label usedCargoSpace;
    @FXML Label qtyStored;
    @FXML Label totalSellPrice;    
    @FXML Button buyButton;
    @FXML Button sellButton;
    @FXML TextField qtyField; //Compra
    @FXML TextField qtyField1; //Venda
    @FXML ComboBox<Storable> purchaseDestination;
    @FXML ComboBox<Storable> saleSource; //Armazém ou transportes que armazenam as armas
    //@FXML ComboBox<Faction> saleDestination;
    
    
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
    
    
    //TAB de MERCADO NEGRO------------------------------------------------------
    @FXML
    public void selectMarketWeapons(){
        if(marketTable.getSelectionModel().getSelectedIndex() >=0 ){//Clicou em um index valido
            if(this.selectedWeapon != marketTable.getSelectionModel().getSelectedItem()) {//Arma diferente da 
                //seta a referencia à arma clicada
                this.selectedWeapon = marketTable.getSelectionModel().getSelectedItem(); 
                //Seta a imageview à foto da arma clicada
                String imagePath = "/images/" + this.selectedWeapon.getWpnCat() + "/" + this.selectedWeapon.getWpnName() + ".png";
                //System.out.println("Clicou na " + this.selectedWeapon.getWpnName() + "-chan" + "\n" + imagePath);//DEBUG
                Image updatedWpnImg = new Image(imagePath, false);
                this.selectedWpnImg.setImage(updatedWpnImg);
                //Seta o texto da descrição para a arma clicada
                this.selectedWpnDescr.setText(this.selectedWeapon.getWpn().getDescription());
            }
        }
    }


    
    private void initializeMarketTab(TableView marketTable, Region region){
        //Inicializa a tabela
        nameColumn.setCellValueFactory(new PropertyValueFactory<MarketWeapon, String>("wpnName"));
        catColumn.setCellValueFactory(new PropertyValueFactory<MarketWeapon, String>("wpnCat"));

        sellColumn.setCellValueFactory(new PropertyValueFactory<MarketWeapon, Integer>("sellPrice"));
        buyColumn.setCellValueFactory(new PropertyValueFactory<MarketWeapon, Integer>("buyPrice"));
        supColumn.setCellValueFactory(new PropertyValueFactory<MarketWeapon, Integer>("supply"));
        demColumn.setCellValueFactory(new PropertyValueFactory<MarketWeapon, Integer>("demand"));             
        
        ObservableList obl = (ObservableList) region.getLocalMarket().getAvailableWeapons();
        this.marketName.setText(this.player.getCurrentPos().getName()+"'s Black Market");
        marketTable.setItems(obl);
        
        
        //Inicializa os destinos de compra. Devem incluir Warehouse local e qualquer veículo do jogador que esta na região
        ObservableList<Storable> storableObl = FXCollections.observableArrayList();
        if(region.getLocalWarehouse() != null){
            storableObl.add(region.getLocalWarehouse());
        }
        storableObl.addAll(this.player.getTransports(region));//Adiciona todos os transportes presentes na região em questão
        purchaseDestination.setItems(storableObl);
        saleSource.setItems(storableObl);
    }    
    
    
    @FXML
    /**
     * Método para vender itens no mercado
     */
    public void sell(){
        
        Storable source = saleSource.getValue();
        //Faction destination = saleDestination.getValue();
        int demmand = selectedWeapon.getDemand();
        
        if(selectedWeapon != null){
            if(qtyValidation(qtyField1)){ //Item, quantidade, fonte válidos
                if(source != null){
                    int qty = Integer.parseInt(qtyField1.getText());//quantidade a ser vendida
                    int availableQty = source.getWeaponQuantity(selectedWeapon.getWpnName());//Retorna -1 se a arma não existe. 

                    if(qty <= availableQty){//Quantidade a ser vendida é menor ou igual à quantidade disponível na fonte. Se a arma não existe, o -1 impede a entrada nesse if (qty é sempre >= 0)

                        if(qty <= demmand){//Quantidade menor ou igual á demanda do mercado
                            selectedWeapon.setDemand(demmand-qty);

                            marketTable.getColumns().get(0).setVisible(false);//Atualiza a tablelist
                            marketTable.getColumns().get(0).setVisible(true);


                            source.remove(selectedWeapon.getWpnName(), qty);
                            player.addFunds(qty * selectedWeapon.getSellPrice());
                            
                            qtyField1.setText("");                            
                            saleSource.setValue(null);
                            
                            transactionOutput.setText("");
                            
                            updateSellInfo();
                            updateEssentialInfo();
                        }
                        else
                            transactionOutput.setText("Insufficient Demmand");
                    }else
                        transactionOutput.setText("Insufficient Wares");
                }  
                else
                    transactionOutput.setText("Invalid destination");
            }
            else
                transactionOutput.setText("Invalid quantity");
        }
        else
            transactionOutput.setText("Invalid weapon");
    }
    
    @FXML
    /**
     * Método de usado para comprar um item do mercado
     */
    public void buy(){

        Storable destination = purchaseDestination.getValue();
        
        if(selectedWeapon != null && qtyValidation(qtyField) && destination!=null){ //Item e destino selecionados e qty é Integer positivo
            int qty = Integer.parseInt(qtyField.getText());//quantidade a ser comprada
            int supply = selectedWeapon.getSupply();
            
            if(supply >= qty){ //Mercado tem oferta suficiente
                if(player.getFunds() >= (selectedWeapon.getBuyPrice() * qty)){//Jogador tem fundos suficientes
                    
                    //Verifica se tem espaço suficiente no armazém/veículo
                    if(qty * selectedWeapon.getWpn().getSize() + destination.getUsedCapacity() <= destination.getTotalCapacity()){
                        selectedWeapon.setSupply(supply-qty);//Seta nova oferta do mercado
                        marketTable.getColumns().get(0).setVisible(false);//Atualiza a tablelist
                        marketTable.getColumns().get(0).setVisible(true);
                        qtyField.setText("");
                        
                        destination.store(selectedWeapon.getWpn(), qty);
                        player.subtractFunds(qty * selectedWeapon.getBuyPrice());
                        
                        transactionOutput.setText("");
                        purchaseDestination.setValue(null);
                        updateEssentialInfo();
                        updateBuyInfo();

                    }
                    else
                        transactionOutput.setText("Insufficient Space");
                }
                else
                    transactionOutput.setText("Insufficient Funds");
            }
            else
                transactionOutput.setText("Insufficient Supply");
        }
        else
            transactionOutput.setText("Invalid weapon, destination or quantity");
    }

    
    /**
     * Verifica se o texto do input é um inteiro positivo
     * @param input
     * @return True Se é um inteiro positivo
     */
    private boolean qtyValidation (TextField input){
        try{
            int qty = Integer.parseInt(input.getText());
            if(qty > 0)
                return true;
            else
                return false;
        }catch(NumberFormatException e){
            //input.setText("invalid input");
            //System.out.println("Invalid input");
            return false;
        }
    }
    
    
    
    @FXML
    //
    public void updateSellInfo(){
        
        if(selectedWeapon != null && qtyValidation(qtyField1)){
            int qty = Integer.parseInt(qtyField1.getText());//quantidade a ser comprada
            totalSellPrice.setText("Total Price: " + qty*selectedWeapon.getSellPrice());
        }
        else{
            totalSellPrice.setText("Total Price: ");
        }
        
        if(saleSource.getValue() != null){
            qtyStored.setText("Quantity Stored: " + saleSource.getValue().getWeaponQuantity(selectedWeapon.getWpnName()));
        }
        else{
            qtyStored.setText("Quantity Stored: "); 
        }
    }
    
    
    @FXML
    //
    public void updateBuyInfo(){
        if(selectedWeapon != null && qtyValidation(qtyField)){
            int qty = Integer.parseInt(qtyField.getText());//quantidade a ser comprada
            totalBuyPrice.setText("Total Price: " + qty*selectedWeapon.getBuyPrice());
            ReqCargoSpace.setText("Required Cargo Space: " + qty*selectedWeapon.getWpn().getSize());
        }
        else{
            totalBuyPrice.setText("Total Price: ");
            ReqCargoSpace.setText("Required Cargo Space: ");
        }
        
        if(purchaseDestination.getValue() != null){
            Storable dest = purchaseDestination.getValue();
            usedCargoSpace.setText("Used Cargo Space: " + dest.getUsedCapacity() +"/ " + dest.getTotalCapacity() );
        }
        else{
            usedCargoSpace.setText("Used Cargo Space: ");
        }
    }
    
    
    
    //GERAIS--------------------------------------------------------------------
    
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
        this.selectedWeapon = null;
        this.initializeMarketTab(marketTable, player.getCurrentPos());
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
        this.initializeMarketTab(marketTable, player.getCurrentPos());
    }    
    
}
