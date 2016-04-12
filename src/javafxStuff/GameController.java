/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package javafxStuff;

import java.net.URL;
import java.util.ArrayList;
import java.util.ResourceBundle;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.geometry.Insets;
import javafx.scene.Group;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.MenuItem;
import javafx.scene.control.SingleSelectionModel;
import javafx.scene.control.Tab;
import javafx.scene.control.TabPane;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.StackPane;
import javafx.scene.shape.Circle;
import javafx.scene.text.Text;
import war.GameCharacter;
import war.MarketWeapon;
import war.PlayerCharacter;
import war.PlayerWeapon;
import war.Region;
import war.Storable;
import war.Transport;
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
    //GERAIS
    @FXML TabPane pane;
    @FXML Tab map;
    @FXML Tab blackMarket;
    @FXML Tab inventory;
    @FXML Tab transports;
    Label mapTabText;
    StackPane stp;
    
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
    @FXML Label usedCargoSpace;
    @FXML Label qtyStored;
    @FXML Label totalSellPrice;    
    @FXML Label totalHeatInc;
    @FXML Button buyButton;
    @FXML Button sellButton;
    @FXML TextField qtyField; //Compra
    @FXML TextField qtyField1; //Venda
    @FXML ComboBox<Storable> purchaseDestination;
    @FXML ComboBox<Storable> saleSource; //Armazém ou transportes que armazenam as armas
    //@FXML ComboBox<Faction> saleDestination;
    
    //TAB do INVENTÁRIO
    @FXML ComboBox<Storable> invLeftStorable; //Inventory, Left Storable
    @FXML TableView<PlayerWeapon> invLeftTable;//Inventory, Left Storable Cargo Table
    @FXML TableColumn<PlayerWeapon, String> invLeftCol;    //Inventory, Cargo Column
    @FXML Label invLeftDesc;//Inventory, Left Description
    @FXML ComboBox<Storable> invRightStorable; //Inventory, Right Storable
    @FXML TableView<PlayerWeapon> invRightTable;//Inventory, Right Storable Cargo Table
    @FXML TableColumn<PlayerWeapon, String> invRightCol;    //Inventory, Cargo Column
    @FXML Label invRightDesc;//Inventory, Right Description
    
    @FXML ImageView invWpnImg; //Inventory, Weapon Image    
    @FXML Text invWpnDes; //Inventory, Weapon Description
    @FXML TextField invOpQty; //Inventory, Operation Quantity
    @FXML Button invDestBtn; //Inventory, Destroy Button
    @FXML Button invMoveBtn; //Inventory, Move Button

    
    //TAB do MAPA
    @FXML MenuItem trvNaf;
    @FXML MenuItem trvCol;
    @FXML MenuItem trvCal;    
    @FXML MenuItem trvUra;
    @FXML MenuItem trvRut;
    @FXML MenuItem trvAur;
    
    //TAB de TRANSPORTES
    
    @FXML TableView<Transport> transportsTable;
    @FXML TableColumn<Transport, String> tranLocCol; // Transport, Location Column
    @FXML TableColumn<Transport, String> tranNameCol; // Transport, Name Column
    @FXML TableColumn<Transport, String> tranTypeCol; // Transport, Type Column
    @FXML TableColumn<Transport, String> tranStatCol; // Transport, Status Column
    
    @FXML ComboBox<Region> adjacentRegions;
    @FXML Text tranCargo;
    @FXML Text tranRoute;
    
    //TAB de AGENTES
    @FXML Label agentStats;
    @FXML TableView<GameCharacter> agentTable;
    @FXML TableColumn<GameCharacter, String> agentNameCol; //Transport, Name Column
    @FXML TableColumn<GameCharacter, String> agentPosCol; //Agent, Position Column
    @FXML TableColumn<GameCharacter, String> agentOrderCol; //Agent, Current Order Column
    
    //INFO ESSENCIAL
    @FXML TextArea guiPlayerOutput;
    @FXML Label guiFunds;
    @FXML Label guiHeat;
    @FXML Label guiPlayerPos;    
    @FXML Label guiNotoriety;
    @FXML Label guiCurrentTurn;
    @FXML Button endTurnBtn;
    
/*------------------------------------------------------------------------------*/    
    /*ATRIBUTOS*/
    PlayerCharacter player;
    World world;
    
    MarketWeapon selectedWeapon;
    
    PlayerWeapon invSelectedWpn;//Inventory, selected weapon
    Boolean invWpnSide; //TRUE = ESQUERDA, FALSE = DIREITA
    Storable invSelectLeft;//Selected Storable, para o inventário
    Storable invSelectRight;//Selected Storable, para o inventário
    
    Transport tranSelectedTransport;//Selected Transport, para a tab de Transportes
    
    Character selectedCharacter;
    
    int currentTurn;

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


    
    private void initializeMarketTab(Region region){
        
        //Inicializa a tabela
        nameColumn.setCellValueFactory(new PropertyValueFactory<>("wpnName"));
        catColumn.setCellValueFactory(new PropertyValueFactory<>("wpnCat"));
        sellColumn.setCellValueFactory(new PropertyValueFactory<>("sellPrice"));
        buyColumn.setCellValueFactory(new PropertyValueFactory<>("buyPrice"));
        supColumn.setCellValueFactory(new PropertyValueFactory<>("supply"));
        demColumn.setCellValueFactory(new PropertyValueFactory<>("demand"));             
        
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
                            player.setFunds(true, qty * selectedWeapon.getSellPrice());
                            
                            /*
                            *+++++++++   TESTE DE GERAÇÃO DE PISTA AQUI !!!     +++++++++
                            */
                            
                            qtyField1.setText("");                            
                            saleSource.setValue(null);
                            
                            updateSellInfo();
                            updateEssentialInfo();
                        }
                        else
                            guiPlayerOutput.appendText("\nInsufficient Demmand");
                    }else
                        guiPlayerOutput.appendText("\nInsufficient Wares");
                }  
                else
                    guiPlayerOutput.appendText("\nInvalid destination");
            }
            else
                guiPlayerOutput.appendText("\nInvalid quantity");
        }
        else
            guiPlayerOutput.appendText("\nInvalid weapon");
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

                        player.setFunds(false, qty * selectedWeapon.getBuyPrice());

                        /*
                        *+++++++++   TESTE DE GERAÇÃO DE PISTA AQUI !!!     +++++++++
                        */                        
                        
                        purchaseDestination.setValue(null);
                        updateEssentialInfo();
                        updateBuyInfo();

                    }
                    else
                        guiPlayerOutput.appendText("\nInsufficient Space");
                }
                else
                    guiPlayerOutput.appendText("\nInsufficient Funds");
            }
            else
                guiPlayerOutput.appendText("\nInsufficient Supply");
        }
        else
            guiPlayerOutput.appendText("\nInvalid weapon, destination or quantity");
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
            totalHeatInc.setText("Heat Increase: " + qty*selectedWeapon.getWpn().getHeatInc());
            
        }
        else{
            totalSellPrice.setText("Total Price: ");
            totalHeatInc.setText("Heat Increase: ");
            
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
            totalHeatInc.setText("Heat Increase: " + qty*selectedWeapon.getWpn().getHeatInc());
        }
        else{
            totalBuyPrice.setText("Total Price: ");
            ReqCargoSpace.setText("Required Cargo Space: ");
            totalHeatInc.setText("Heat Increase: ");

        }
        
        if(purchaseDestination.getValue() != null){
            Storable dest = purchaseDestination.getValue();
            usedCargoSpace.setText("Used Cargo Space: " + dest.getUsedCapacity() +"/ " + dest.getTotalCapacity() );
        }
        else{
            usedCargoSpace.setText("Used Cargo Space: ");
        }
    }
    
    
    //TAB DE INVENTÁRIO---------------------------------------------------------
    
    public void initializeInventoryTab(){

        invLeftStorable.setValue(null);
        invRightStorable.setValue(null);
        
        ObservableList obl = (ObservableList) player.getStorableObl(player.getCurrentPos(), null);//Adiciona os transportes da região
                
        invRightStorable.setItems(obl);
        invLeftStorable.setItems(obl);
        
        invLeftTable.getColumns().get(0).setVisible(false);//Atualiza a tablelist
        invLeftTable.getColumns().get(0).setVisible(true);
        
        invRightTable.getColumns().get(0).setVisible(false);//Atualiza a tablelist
        invRightTable.getColumns().get(0).setVisible(true);
        
        invRightDesc.setText("");
        invLeftDesc.setText("");

    }
    

    /***
     * Seleção do Storable de um dos lados da tela.
     * @param e Qual das duas ComboBoxes clicou
     */
    @FXML
    public void invSelectStorable(ActionEvent e){

        if(e.getSource() == invLeftStorable && invLeftStorable!=null){
            invSelectLeft = invLeftStorable.getValue();
            System.out.println(invSelectLeft);
            ObservableList obl = (ObservableList) invSelectLeft.getWeapons();
            invLeftCol.setCellValueFactory(new PropertyValueFactory<>("InventoryInfo"));
            invLeftTable.setItems(obl);
            invLeftDesc.setText(invSelectLeft.getDescriptionInfo());
        }
        else if(e.getSource() == invRightStorable && invRightStorable != null){
            invSelectRight = invRightStorable.getValue();
            System.out.println(invSelectRight);
            ObservableList obl = (ObservableList) invSelectRight.getWeapons();
            invRightCol.setCellValueFactory(new PropertyValueFactory<>("InventoryInfo"));
            invRightTable.setItems(obl);
            invRightDesc.setText(invSelectRight.getDescriptionInfo());
        }        
    }
    
    /**
     * Método para seleção de armas na tabela Esquerda.
     */
    
    @FXML
    public void invSelectWeaponsLeft(){
        if(invLeftTable.getSelectionModel().getSelectedIndex() >=0 ){//Clicou em um index valido na tabela esquerda
            if(invSelectedWpn != invLeftTable.getSelectionModel().getSelectedItem()) {//Arma diferente da 
                //seta a referencia à arma clicada
                invSelectedWpn = invLeftTable.getSelectionModel().getSelectedItem(); 
                //Seta a imageview à foto da arma clicada
                String imagePath = "/images/" + this.invSelectedWpn.getWpn().getCategory() + "/" + this.invSelectedWpn.getWpn().getName() + ".png";
                //System.out.println("Clicou na " + this.selectedWeapon.getWpnName() + "-chan" + "\n" + imagePath);//DEBUG
                Image updatedWpnImg = new Image(imagePath, false);
                invWpnImg.setImage(updatedWpnImg);
                //Seta o texto da descrição para a arma clicada
                invWpnDes.setText(this.invSelectedWpn.getWpn().getDescription()
                    +"\nCategory: " + invSelectedWpn.getWpn().getCategory().toUpperCase() 
                    +"\nCombat Effectiveness Bonus: " + invSelectedWpn.getWpn().getCombEfecBonus()
                    +"\nHeat Increase: " + invSelectedWpn.getWpn().getHeatInc()
                );
                invWpnSide=true;
            }
        }
    }
    /**
     * Método para seleção de armas na tabela Direita.
     */
    @FXML
    public void invSelectWeaponsRight(){
        if (invRightTable.getSelectionModel().getSelectedIndex() >= 0){
            if(invSelectedWpn != invRightTable.getSelectionModel().getSelectedItem()) {//Arma diferente da 
                //seta a referencia à arma clicada
                invSelectedWpn = invRightTable.getSelectionModel().getSelectedItem(); 
                //Seta a imageview à foto da arma clicada
                String imagePath = "/images/" + this.invSelectedWpn.getWpn().getCategory() + "/" + this.invSelectedWpn.getWpn().getName() + ".png";
                //System.out.println("Clicou na " + this.selectedWeapon.getWpnName() + "-chan" + "\n" + imagePath);//DEBUG
                Image updatedWpnImg = new Image(imagePath, false);
                invWpnImg.setImage(updatedWpnImg);
                //Seta o texto da descrição para a arma clicada
                invWpnDes.setText(this.invSelectedWpn.getWpn().getDescription()
                    +"\nCategory: " + invSelectedWpn.getWpn().getCategory().toUpperCase() 
                    +"\nCombat Effectiveness Bonus: " + invSelectedWpn.getWpn().getCombEfecBonus()
                    +"\nHeat Increase: " + invSelectedWpn.getWpn().getHeatInc()
                );
                invWpnSide=false;
            }        
        }
    }
    
    
    /**
     * Método a ser chamado após mover ou destruir armas. Atualiza todos os elementos relevantes da tab
     */
    
    public void updateInventoryTab(){
        System.out.println("ATUALIZANDO");
        
        invOpQty.setText(null);
        
        invLeftTable.getColumns().clear();
        invLeftTable.getColumns().addAll(invLeftCol);
        ObservableList obl = (ObservableList) invSelectLeft.getWeapons();
        invLeftCol.setCellValueFactory(new PropertyValueFactory<>("InventoryInfo"));
        invLeftTable.setItems(obl);         
        invLeftDesc.setText(invSelectLeft.getDescriptionInfo());
        
        invRightTable.getColumns().clear();
        invRightTable.getColumns().addAll(invRightCol);
        obl = (ObservableList) invSelectLeft.getWeapons();
        invRightCol.setCellValueFactory(new PropertyValueFactory<>("InventoryInfo"));
        invRightTable.setItems(obl);        
        invRightDesc.setText(invSelectRight.getDescriptionInfo());
    }

    /**
     * Método usado para mover armas de um Storable para outro.
     */
    
    @FXML
    public void invMoveWeapons(){
        Storable source = null;
        Storable destination = null;
        
        if(invSelectRight != null && invSelectLeft != null){
            
            if(invSelectedWpn != null && invWpnSide==false){
               source = invSelectRight;
               destination = invSelectLeft;
               System.out.println("RIGHT->LEFT");
            }
            else if(invSelectedWpn != null && invWpnSide==true){
                source = invSelectLeft;
                destination = invSelectRight;
                System.out.println("LEFT->RIGHT");
            }                
        }
        
        if(source != null && destination != null){
            if(qtyValidation(invOpQty)){//Quantidade valida
                   int qty = Integer.parseInt(invOpQty.getText());
                   if(invSelectedWpn.getQty() >= qty){
                       if(qty * invSelectedWpn.getWpn().getSize() + 
                               destination.getUsedCapacity() <= destination.getTotalCapacity()){
                            
                            destination.store(invSelectedWpn.getWpn(), qty);
                            source.remove(invSelectedWpn.getWpn().getName(), qty);
                            updateInventoryTab();
                       }else
                            guiPlayerOutput.appendText("\nInsuficient Capacity");
                   }else
                        guiPlayerOutput.appendText("\nInvalid Quantity");
               }else
                    guiPlayerOutput.appendText("\nIInvalid Quantity");
            }else
            guiPlayerOutput.appendText("\nInvalid  Transport/Warehosue");
        }
    
    
    
    /**
     * Método usado para destruir armas de um Storable e reduzir heat.
     */
    
    @FXML
    public void invDestroyWeapons(){
        Storable target = null;
        if(invSelectedWpn != null && invWpnSide==false){
           target = invSelectRight;
        }
        else if(invSelectedWpn != null && invWpnSide==true){
            target = invSelectLeft;
        }   
        if(target !=null){
            if (qtyValidation (invOpQty)){    //Quantidade é um inteiro positivo.
                int qty = Integer.parseInt(invOpQty.getText());
                if(invSelectedWpn != null && invSelectedWpn.getQty() >= qty){
                    target.remove(invSelectedWpn.getWpn().getName(), qty);
                    player.setHeat(false,(invSelectedWpn.getWpn().getHeatInc() * qty)/2);
                    updateEssentialInfo();
                    updateInventoryTab();
                }
            }
        }
    }
    
    //TAB DE TRANSPORTES--------------------------------------------------------

    public void initializeTransportsTab(){
    //    transportsTable;
        tranLocCol.setCellValueFactory(new PropertyValueFactory<>("currentPos")); // Transport, Location Column
        tranNameCol.setCellValueFactory(new PropertyValueFactory<>("name")); // Transport, Name Column
        tranTypeCol.setCellValueFactory(new PropertyValueFactory<>("type")); // Transport, Type Column
        tranStatCol.setCellValueFactory(new PropertyValueFactory<>("statusString")); //Transport, Status Column
        
        ObservableList obl = (ObservableList) player.getTransportObl();
        transportsTable.setItems(obl);        
    }
    
    @FXML
    public void selectTransport(){
        if(transportsTable.getSelectionModel().getSelectedIndex() >=0 ){//Clicou em um index valido
            if(tranSelectedTransport != transportsTable.getSelectionModel().getSelectedItem()) {//Transporte diferente do atual é clicado
                updateTransportTab();
            }
        }
    }
    
    @FXML
    /**
     * Seleção de uma nova parada para a rota do Transporte
     */
    public void addRouteStop(){
        Region newStop = adjacentRegions.getValue();
        if(newStop != null){
            tranSelectedTransport.addRouteStop(newStop);

            adjacentRegions.setValue(null);
            updateTransportTab();
        }
    }
    
    @FXML
    public void removeRouteStop(){
        tranSelectedTransport.removeRouteStop();
        adjacentRegions.setValue(null);
        updateTransportTab();        
    }
    
    public void updateTransportTab(){
    
        tranSelectedTransport = transportsTable.getSelectionModel().getSelectedItem(); 
        if(tranSelectedTransport != null){
            ArrayList<Region> route = tranSelectedTransport.getRoute();
            //Inicializa a combobox de regiões
            adjacentRegions.setItems(tranSelectedTransport.getMovableAdjacent());
            if(route == null){ //Standby, setar vizinhos da posição atual
                tranRoute.setText("Standing By");
            }
            else{ //Inicializar a partir das regiões adjacentes ao destino
                tranRoute.setText(tranSelectedTransport.getRouteString());
            }
            tranCargo.setText(tranSelectedTransport.getWeaponsString());
        }

        transportsTable.getColumns().get(0).setVisible(false);
        transportsTable.getColumns().get(0).setVisible(true);
    }
/*
    @FXML
    public void editCargo(){
        if(tranSelectedTransport != null){
            invSelectedStorable = tranSelectedTransport;
            
            updateInventoryTab();       
            
            inventoryTable.getSelectionModel().select(invSelectedStorable);           
            pane.getSelectionModel().select(inventory);
        }
    }*/
    

    
//TAB DE AGENTES--------------------------------------------------------
    
    public void initializeAgentTab(){
            //Inicializa a tabela
        agentNameCol.setCellValueFactory(new PropertyValueFactory<>("name"));
        agentOrderCol.setCellValueFactory(new PropertyValueFactory<>("name"));
        agentPosCol.setCellValueFactory(new PropertyValueFactory<>("currentPos"));

        ObservableList obl = player.getAgentObl();
        agentTable.setItems(obl);
    }
    
    @FXML
    public void selectCharacter(){
    
    }
    
//GERAIS--------------------------------------------------------------------
    
    @FXML
    /**
     * Método para finalização de turno.
     * Inclui recálculo dos estoques e preços dos mercados, 
     * O jogo se passa em turnos, cada turno representa aproximadamente uma semana de tempo real. 
     * Transportes cobrirão uma distância igual à sua velocidade dentro de um turno. 
     * Durante o turno, o jogador pode realizar suas ações, como realizar transações econômicas ou movimentar seus agentes e transportes pelo mundo.
     * Turnos podem ser finalizados pelo botão de finalizar turno (End Turn). 
     * Ao finalizar um turno, o jogo realizará toda a computação necessária para atualizar o estado do mundo, realizando as ações de facções e etc. 
     * No início de cada turno, os fundos do jogador sofrem um decremento igual ao Upkeep de Agentes e Transportes, além disso Eventos podem ocorrer.
     */
    public void endTurn(){
        currentTurn ++;
        world.updateMarkets();
        //world.updateFactions();
        
        player.setFunds(false, player.getAgentUpkeep() + player.getTransportUpkeep() + player.getWarehouseUpkeep());//Subtrai os upkeeps dos fundos do jogador.
        player.moveTransports();
        
        guiPlayerOutput.clear();
        
        updateTransportTab();
        //updateInventoryTab();
        initializeMarketTab(player.getCurrentPos());
        
        updateEssentialInfo();
    }
    
    
    @FXML
    /*Temporário, para debugging*/
    public void playerTravel(ActionEvent e){

        if(e.getSource() == trvNaf)
            player.setCurrentPos(world.getRegion(0));
        else if(e.getSource() == trvCol)
            player.setCurrentPos(world.getRegion(1));
        else if(e.getSource()==trvCal)
            player.setCurrentPos(world.getRegion(2));
        else if(e.getSource()==trvUra)
            player.setCurrentPos(world.getRegion(3));  
        else if(e.getSource()==trvRut)
            player.setCurrentPos(world.getRegion(4));          
        else if(e.getSource()==trvAur)
            player.setCurrentPos(world.getRegion(5));  
        
        updateEssentialInfo();
        
        selectedCharacter = null;
        selectedWeapon = null;
        selectedWpnImg.setImage(null);
        selectedWpnDescr.setText(null);
        initializeMarketTab(player.getCurrentPos());
    }
    
    /*Atualiza info no HUD*/
    private void updateEssentialInfo(){
        guiHeat.setText("HEAT: " + player.getHeat());
        guiFunds.setText("FUNDS: "+ player.getFunds());
        guiPlayerPos.setText("CURRENT POSITION: " + player.getCurrentPos().getName());
        guiCurrentTurn.setText("CURRENT TURN: " + currentTurn);
        guiNotoriety.setText("NOTRIETY: " + player.getNotoriety());
    }
    
    /**
     * Initializes the controller class.
     * Incializa o jogador e outras informações baseada na string que será passada do menu de new/load game. A string é o nome do arquivo de save
     *
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
       /* mapTabText = new Label();
        mapTabText.setText("MAP");
        mapTabText.setRotate(90);
        stp  = new StackPane(new Group(mapTabText));
        stp.setPrefWidth(80);
        stp.setPrefHeight(80);
        map.setGraphic(stp);*/
        
        selectedWeapon = null;
        
        invSelectRight= null;
        invSelectLeft= null;
        invSelectedWpn = null;
        
        tranSelectedTransport = null;
        
        world = new World();
        player = new PlayerCharacter("default",this.world.getRegion(0));
        
        currentTurn = 0;
        
        updateEssentialInfo();
        initializeMarketTab(player.getCurrentPos());
        initializeTransportsTab();
        initializeInventoryTab();
        initializeAgentTab();
    }    
    
}
