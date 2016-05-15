/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package javafxStuff;


import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import war.GameCharacter;
import war.MarketWeapon;
import war.PlayerCharacter;
import war.Region;
import war.Storable;
import war.turn.BuyAction;
import war.turn.SellAction;
import war.turn.Turn;

/**
 *
 * @author João
 */
public class BlackMarketController {
    @FXML AnchorPane wnd;
    
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
    @FXML Label usedCargoSpace;
    @FXML Label qtyStored;
    @FXML Label totalSellPrice;    
    @FXML Label totalNotInc;
    @FXML Button buyButton;
    @FXML Button sellButton;
    @FXML TextField qtyField; //Compra
    @FXML TextField qtyField1; //Venda
    @FXML ComboBox<Storable> purchaseDestination;
    @FXML ComboBox<Storable> saleSource; //Armazém ou transportes que armazenam as armas    
    @FXML ComboBox<GameCharacter> agentBox; //Lista todos os agentes naquela região
    @FXML Label barterBonus;
    @FXML Label baseBuyPrice;
    @FXML Label baseSellPrice;
    
    MarketWeapon selectedWeapon;
    Region region;//Referencia para a região
    GameController gc; //Referencia ao Controller principal do jogo para obter os atributos necessários

    
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


    
    public void initializeMarketTab(Region region){
        
        //Inicializa a tabela
        nameColumn.setCellValueFactory(new PropertyValueFactory<>("wpnName"));
        catColumn.setCellValueFactory(new PropertyValueFactory<>("wpnCat"));
        sellColumn.setCellValueFactory(new PropertyValueFactory<>("sellPrice"));
        buyColumn.setCellValueFactory(new PropertyValueFactory<>("buyPrice"));
        supColumn.setCellValueFactory(new PropertyValueFactory<>("supply"));
        demColumn.setCellValueFactory(new PropertyValueFactory<>("demand"));             
        
        ObservableList obl = (ObservableList) region.getLocalMarket().getAvailableWeapons();
        this.marketName.setText(region.getName()+"'s Black Market");
        marketTable.setItems(obl);
        
        
        //Inicializa os destinos de compra. Devem incluir Warehouse local e qualquer veículo do jogador que esta na região
        ObservableList<Storable> storableObl = FXCollections.observableArrayList();
        if(region.getLocalWarehouse() != null){
            storableObl.add(region.getLocalWarehouse());
        }
        storableObl.addAll(gc.player.getTransports(region));//Adiciona todos os transportes presentes na região em questão
        purchaseDestination.setItems(storableObl);
        saleSource.setItems(storableObl);
        
        ObservableList<GameCharacter> agents = gc.player.getAgentObl(region);
        agentBox.setItems(agents);
    }    
    
        @FXML
    /**
     * Método para vender itens no mercado
     */
    public void sell(){
        GameCharacter dealer = agentBox.getValue();
        Storable source = saleSource.getValue();
        //Faction destination = saleDestination.getValue();
        int demmand = selectedWeapon.getDemand();
        
        if(dealer != null){
            if(selectedWeapon != null){
                if(gc.qtyValidation(qtyField1)){ //Item, quantidade, fonte válidos
                    if(source != null){
                        int qty = Integer.parseInt(qtyField1.getText());//quantidade a ser vendida
                        int availableQty = source.getWeaponQuantity(selectedWeapon.getWpnName());//Retorna -1 se a arma não existe. 

                        if(qty <= availableQty){//Quantidade a ser vendida é menor ou igual à quantidade disponível na fonte. Se a arma não existe, o -1 impede a entrada nesse if (qty é sempre >= 0)

                            if(qty <= demmand){//Quantidade menor ou igual á demanda do mercado
                                // adiciona a venda, e já executa-a
                                gc.turn.addAction (new SellAction (gc.player, dealer, selectedWeapon, qty, source));

                                marketTable.getColumns().get(0).setVisible(false);//Atualiza a tablelist
                                marketTable.getColumns().get(0).setVisible(true);


                                /*
                                *+++++++++   TESTE DE GERAÇÃO DE PISTA AQUI !!!     +++++++++
                                */

                                qtyField1.setText("");                            
                                saleSource.setValue(null);

                                updateSellInfo();
                                gc.updateEssentialInfo();
                            }
                            else
                                //System.out.println("\nInsufficient Demmand");
                                gc.guiPlayerOutput.appendText("\nInsufficient Demmand");
                        }else
                            //System.out.println("\nInsufficient Wares");
                            gc.guiPlayerOutput.appendText("\nInsufficient Wares");
                    }  
                    else
                        //System.out.println("\nInvalid destination");
                        gc.guiPlayerOutput.appendText("\nInvalid destination");
                }
                else
                    //System.out.println("\nInvalid quantity");
                    gc.guiPlayerOutput.appendText("\nInvalid quantity");
            }
            else                           
                //System.out.println("\nInvalid weapon");
                gc.guiPlayerOutput.appendText("\nInvalid weapon");
        }
        else
            gc.guiPlayerOutput.appendText("\nSelect a Dealer");
    }
    
    @FXML
    /**
     * Método de usado para comprar um item do mercado
     */
    public void buy(){
        
        GameCharacter dealer = agentBox.getValue();
        Storable destination = purchaseDestination.getValue();
        
        if(dealer != null){
            if(selectedWeapon != null && gc.qtyValidation(qtyField) && destination!=null){ //Item e destino selecionados e qty é Integer positivo
                int qty = Integer.parseInt(qtyField.getText());//quantidade a ser comprada
                int supply = selectedWeapon.getSupply();

                if(supply >= qty){ //Mercado tem oferta suficiente
                    if(gc.player.getFunds() >= (selectedWeapon.getBuyPrice() * qty)){//Jogador tem fundos suficientes

                        //Verifica se tem espaço suficiente no armazém/veículo
                        if(qty * selectedWeapon.getWpn().getSize() + destination.getUsedCapacity() <= destination.getTotalCapacity()){
                            // adiciona a compra, e já executa-a
                            gc.turn.addAction (new BuyAction (gc.player, dealer, selectedWeapon, qty, destination));

                            marketTable.getColumns().get(0).setVisible(false);//Atualiza a tablelist
                            marketTable.getColumns().get(0).setVisible(true);
                            qtyField.setText("");

                            /*
                            *+++++++++   TESTE DE GERAÇÃO DE PISTA AQUI !!!     +++++++++
                            */                        

                            purchaseDestination.setValue(null);
                            gc.updateEssentialInfo();
                            updateBuyInfo();

                        }
                        else
                            //System.out.println("\nInsufficient Space");
                           gc.guiPlayerOutput.appendText("\nInsufficient Space");
                    }
                    else
                        //System.out.println("\nInsufficient Funds");
                        gc.guiPlayerOutput.appendText("\nInsufficient Funds");
                }
                else
                  //System.out.println("\\nInsufficient Supply");
                  gc.guiPlayerOutput.appendText("\nInsufficient Supply");
            }
            else
                //System.out.println("Invalid weapon, destination or quantity");
                gc.guiPlayerOutput.appendText("\nInvalid weapon, destination or quantity");
        }
        else
            gc.guiPlayerOutput.appendText("\nSelect a Dealer");
    }


    
    @FXML
    //
    public void updateSellInfo(){
        
        if(selectedWeapon != null && gc.qtyValidation(qtyField1)){
            
            GameCharacter gamechar = agentBox.getValue(); 
            int qty = Integer.parseInt(qtyField1.getText());//quantidade a ser comprada
            
            int base = qty*selectedWeapon.getSellPrice();
            baseSellPrice.setText("Base Price: " + base);
            totalNotInc.setText("Notoriety Increase: " + qty*selectedWeapon.getWpn().getNotInc());
            
            if(gamechar != null){
                
                barterBonus.setText("Barter Bonus: " + gamechar.getBarter() + " %");
                int bonus = (gamechar.getBarter() * base)/100;
                totalSellPrice.setText("Total Price: " + (base + bonus));
            }
            else{
                barterBonus.setText("Barter Bonus: ");
            } 
        }
        else{
            totalSellPrice.setText("Total Price: ");
            totalNotInc.setText("Heat Increase: ");
            
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
        if(selectedWeapon != null && gc.qtyValidation(qtyField)){
            GameCharacter gamechar = agentBox.getValue(); 
            int qty = Integer.parseInt(qtyField.getText());//quantidade a ser comprada
            int base = qty * selectedWeapon.getBuyPrice();
                    
            baseBuyPrice.setText("Base Price: " + base );
            ReqCargoSpace.setText("Required Cargo Space: " + qty*selectedWeapon.getWpn().getSize());
            
            if(gamechar != null){
                
                barterBonus.setText("Barter Bonus: " + gamechar.getBarter() + " %");
                int bonus = (gamechar.getBarter() * base)/100;
                totalBuyPrice.setText("Total Price: " + (base - bonus));
            }
            else{
                barterBonus.setText("Barter Bonus: ");
            }            
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
    
    public void handle(KeyEvent k) {
        // TODO Auto-generated method stub
        KeyCode pressed = k.getCode();
        
        //close window
        if(pressed == KeyCode.T){
            Stage sb = (Stage)marketTable.getScene().getWindow();//use any one object
            sb.close();
        }
        //Up na tab
        if(pressed == KeyCode.W || pressed == KeyCode.KP_UP || pressed == KeyCode.UP){
            System.out.println("UP");
            if(selectedWeapon!=null){
                //seta o novo index
                int newIndex = marketTable.getSelectionModel().getSelectedIndex() - 1;

                marketTable.getFocusModel().focus(newIndex);
                marketTable.getSelectionModel().select(newIndex);
                this.selectedWeapon = marketTable.getSelectionModel().getSelectedItem(); 
                
                String imagePath = "/images/" + this.selectedWeapon.getWpnCat() + "/" + this.selectedWeapon.getWpnName() + ".png";
                Image updatedWpnImg = new Image(imagePath, false);
                this.selectedWpnImg.setImage(updatedWpnImg);
                this.selectedWpnDescr.setText(this.selectedWeapon.getWpn().getDescription());                
            }
        }
        //down na tab
        if(pressed == KeyCode.S || pressed == KeyCode.KP_DOWN || pressed == KeyCode.DOWN){
            //System.out.println("DOWN");
            if(selectedWeapon!=null){
                //seta o novo index
                int newIndex = marketTable.getSelectionModel().getSelectedIndex() + 1;

                marketTable.getFocusModel().focus(newIndex);
                marketTable.getSelectionModel().select(newIndex);
                this.selectedWeapon = marketTable.getSelectionModel().getSelectedItem(); 
                
                String imagePath = "/images/" + this.selectedWeapon.getWpnCat() + "/" + this.selectedWeapon.getWpnName() + ".png";
                Image updatedWpnImg = new Image(imagePath, false);
                this.selectedWpnImg.setImage(updatedWpnImg);
                this.selectedWpnDescr.setText(this.selectedWeapon.getWpn().getDescription());                
            }
        }
    }
    
     /**
     * Initializes the controller class.
     * Incializa o jogador e outras informações baseada na string que será passada do menu de new/load game. A string é o nome do arquivo de save
     *
     * @param player referencia ao jogador
     * @param region referencia a região do mercado negro
     * @param turn referencia ao turno
     */
    public void initialize(GameController gc, Region region) {
        
        //Detecta o ESC e fecha a janela
        wnd.addEventFilter(KeyEvent.KEY_PRESSED, 
                    new EventHandler<KeyEvent>() {
                        @Override
                        public void handle(KeyEvent k){
                            //close window
                            if(k.getCode() == KeyCode.ESCAPE){
                                Stage sb = (Stage)marketTable.getScene().getWindow();//use any one object
                                sb.close();
                            }
                        };
                    });
        
        
        this.region = region;
        this.gc = gc;
        
        selectedWeapon = null;
        selectedWpnImg.setImage(null);
        selectedWpnDescr.setText(null);

        initializeMarketTab(region);

    }
}