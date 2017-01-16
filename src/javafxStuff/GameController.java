/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package javafxStuff;

import java.util.ArrayList;
import java.util.Random;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.MenuItem;
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
import javafx.scene.text.Text;
import javafx.scene.Scene;
import javafx.scene.control.SingleSelectionModel;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.VBox;
import javafx.stage.Modality;
import javafx.stage.Stage;
import war.*;
import static war.TestManager.rollDie;
import war.law.LawManager;
import war.turn.*;

/**
 * FXML Controller class
 * @briefing Classe que controlará a lógica principal do jogo
 * @author João Victor L. da S. Guimarães
 * @date 10/12/2015
 * @version 0.1
 * @phase I
 */

public class GameController{
    
//==============================================================================
    /*ELEMENTOS DE FXML*/
    //GERAIS
    @FXML TabPane pane;
    @FXML Tab map;
    @FXML Tab blackMarket;
    @FXML Tab inventory;
    @FXML Tab transports;
    Label mapTabText;
    StackPane stp;
    @FXML VBox wnd; //Container principal da janela do jogo
    
    
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
    @FXML MenuItem blNaf;
    @FXML MenuItem blCol;
    @FXML MenuItem blCal;    
    @FXML MenuItem blUra;
    @FXML MenuItem blAur;    
    @FXML MenuItem blRut;
    
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
    
    @FXML Button transBuy;
    @FXML Button transDest;
    @FXML Button transSell;
    
    @FXML ComboBox<Region> adjacentRegions;
    @FXML Label tranCargo;
    @FXML Label tranRoute;
    
    //TAB de AGENTES
    @FXML Label agentStats;
    @FXML Label agentOrder;
    @FXML TableView<GameCharacter> agentTable;
    @FXML TableColumn<GameCharacter, String> agentNameCol; //Transport, Name Column
    @FXML TableColumn<GameCharacter, String> agentPosCol; //Agent, Position Column
    @FXML TableColumn<GameCharacter, String> agentOrderCol; //Agent, Current Order Column
    @FXML ComboBox<Region> agentChangePos; 
    @FXML Button setMove; 

    
    //TAB DE TURNO
    @FXML TableView<Action> turnTable;
    @FXML TableColumn<Action, String> turnShortCol;
    @FXML Text turnActionDesc; //Para descrição detalhada da ação realizada
    @FXML Label turnActionName; //Para o nome da ação.

    //INFO ESSENCIAL
    @FXML TextArea guiPlayerOutput;
    @FXML Label guiFunds;
    @FXML Label guiHeat;
    @FXML Label guiPlayerPos;    
    @FXML Label guiNotoriety;
    @FXML Label guiCurrentTurn;
    @FXML Button endTurnBtn;
    
//==============================================================================    
/*VARIÁVEIS*/
    
    PlayerCharacter player;
    World world;
    Turn turn; /// Turno atual, que guardará as ações feitas
    LawManager law;//Gerenciador para autoridades e investigadores
    
    MarketWeapon selectedWeapon;
    
    PlayerWeapon invSelectedWpn;//Inventory, selected weapon
    Boolean invWpnSide; //TRUE = ESQUERDA, FALSE = DIREITA
    Storable invSelectLeft;//Selected Storable, para o inventário
    Storable invSelectRight;//Selected Storable, para o inventário
    
    Transport tranSelectedTransport;//Selected Transport, para a tab de Transportes
    
    GameCharacter selectedCharacter;
    Action selectedAction;

    
    int currentTurn;

//==============================================================================    
/*METODOS*/
    
    
    //==========================================================================
    //TAB DE INVENTÁRIO---------------------------------------------------------
    //==========================================================================
    
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

        if(e.getSource() == invLeftStorable && invLeftStorable!=null ){
            invSelectLeft = invLeftStorable.getValue();
            //System.out.println(invSelectLeft);
            if(invSelectLeft != null) {
                ObservableList obl = (ObservableList) invSelectLeft.getWeapons();
                invLeftCol.setCellValueFactory(new PropertyValueFactory<>("InventoryInfo"));
                invLeftTable.setItems(obl);
                invLeftDesc.setText(invSelectLeft.getDescriptionInfo());
            }
        }
        else if(e.getSource() == invRightStorable && invRightStorable != null){
            invSelectRight = invRightStorable.getValue();
            //System.out.println(invSelectRight);
            if(invSelectRight != null) {
                ObservableList obl = (ObservableList) invSelectRight.getWeapons();
                invRightCol.setCellValueFactory(new PropertyValueFactory<>("InventoryInfo"));
                invRightTable.setItems(obl);
                invRightDesc.setText(invSelectRight.getDescriptionInfo());
            }
        }        
    }
    @FXML
    /**
     * Método para seleção de armas na tabela Esquerda.
     */

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
                    +"\nHeat Increase: " + invSelectedWpn.getWpn().getNotInc()
                );
                invWpnSide=true;
            }
        }
    }

    
    @FXML
    /**
     * Método para seleção de armas na tabela Direita.
     */
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
                    +"\nHeat Increase: " + invSelectedWpn.getWpn().getNotInc()
                );
                invWpnSide=false;
            }        
        }
    }
    
    
    /**
     * Método a ser chamado após mover ou destruir armas. Atualiza todos os elementos relevantes da tab
     */
    @FXML
    public void updateInventoryTab(){
        ObservableList obl;
        invOpQty.setText(null);
        
        invLeftTable.getColumns().clear();
        invLeftTable.getColumns().addAll(invLeftCol);
        if(invSelectLeft != null){
            obl = (ObservableList) invSelectLeft.getWeapons();
            invLeftCol.setCellValueFactory(new PropertyValueFactory<>("InventoryInfo"));
            invLeftTable.setItems(obl);         
            invLeftDesc.setText(invSelectLeft.getDescriptionInfo());
        }
        
        invRightTable.getColumns().clear();
        invRightTable.getColumns().addAll(invRightCol);
        
        if(invSelectRight != null) {
            obl = (ObservableList) invSelectRight.getWeapons();
            invRightCol.setCellValueFactory(new PropertyValueFactory<>("InventoryInfo"));
            invRightTable.setItems(obl);        
            invRightDesc.setText(invSelectRight.getDescriptionInfo());
        }

    }

        
    /**
     * Verifica se o texto do input é um inteiro positivo
     * @param input
     * @return True Se é um inteiro positivo
     */
    public boolean qtyValidation (TextField input){
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
    
    /**
     * Método usado para mover armas de um Storable para outro.
     */
    
    @FXML
    public void invMoveWeapons(){
        Storable source = null;
        Storable destination = null;
        
        //Verificação de quem é fonte e quem é destino
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
        //Verificação da validade da operação
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
                    player.setHeat(false,(invSelectedWpn.getWpn().getNotInc() * qty)/2);
                    updateEssentialInfo();
                    updateInventoryTab();
                }
            }
        }
    }
    
    //==========================================================================
    //TAB DE TRANSPORTES--------------------------------------------------------
    //==========================================================================
    
    
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
    public void updateTransportTab(){
        tranRoute.setText("");
        
        tranSelectedTransport = transportsTable.getSelectionModel().getSelectedItem(); 
        
        if(tranSelectedTransport != null){
            
            adjacentRegions.setItems(tranSelectedTransport.getMovableAdjacent());
            
            if(tranSelectedTransport.getAction() == null){//Sem ordens de movimento
                tranRoute.setText("Standing By");                
                
            }
            else{//Com ordens de movimento
                
                tranRoute.setText(tranSelectedTransport.getAction().getRouteString());
            
            }
            
            tranCargo.setText(tranSelectedTransport.getWeaponsString());
        }

        transportsTable.getColumns().get(0).setVisible(false);
        transportsTable.getColumns().get(0).setVisible(true);
    }
    
    
   
    /**
     * Seleção de uma nova parada para a rota do Transporte
     */
    @FXML
    public void addRouteStop(){
        Region newStop = adjacentRegions.getValue();
        
        if(newStop != null){
            
            if(tranSelectedTransport.getAction() == null){//Não havia ordens até então
                
                MoveAction ma = new MoveAction(player, tranSelectedTransport);
                ma.addRouteStop(newStop);
                turn.scheduleAction(ma);
                tranSelectedTransport.setAction(ma);
                
            }
            else{
                tranSelectedTransport.getAction().addRouteStop(newStop);
            }

            adjacentRegions.setValue(null);
            updateTransportTab();
        }
    }
    
    @FXML
    public void removeRouteStop(){
        
        if(tranSelectedTransport != null){
            
            if(tranSelectedTransport.getAction() != null){//Necessário uma ação para remover da rota
                tranSelectedTransport.getAction().removeRouteStop();
            }
        }        
        adjacentRegions.setValue(null);
        updateTransportTab();        
    }
    
    
    /***
     * Abre a janela de mercado de transportes
     */
    public void openTransMarket(){
       /*chamar método no JavafxManager*/
    }

    @FXML
    public void editCargo(){
        if(tranSelectedTransport != null){
            
            invSelectLeft = tranSelectedTransport;
            invSelectRight = null;
            
            SingleSelectionModel<Storable> sm = invLeftStorable.getSelectionModel();
            sm.select(tranSelectedTransport);
            invLeftStorable.setSelectionModel(sm);
            
            //Deixa o item da direita como vazio para ser selecionado pelo jogador
            sm = invRightStorable.getSelectionModel();
            sm.select(null);
            invRightStorable.setSelectionModel(sm);
            invRightDesc.setText("");
            switchTab(inventory);    
            
            invRightTable.setItems(null);
        }
    }
    

    //==========================================================================
    //TAB DE AGENTES------------------------------------------------------------
    //==========================================================================
    
    public void initializeAgentTab(){
            //Inicializa a tabela
        agentNameCol.setCellValueFactory(new PropertyValueFactory<>("name"));
        agentOrderCol.setCellValueFactory(new PropertyValueFactory<>("endTurnActionDesc"));
        agentPosCol.setCellValueFactory(new PropertyValueFactory<>("currentPos"));

        ObservableList obl = player.getAgents ();
        agentTable.setItems(obl);
        
    }
     
    /**
     * Método para printar os stats do personagem selecionado.
     */
    public void printCharacterStats() {
        agentStats.setText(selectedCharacter.getStatsString());
        if(selectedCharacter.getEndTurnAction() != null)
            agentOrder.setText(selectedCharacter.getEndTurnAction().toString());
        else
            agentOrder.setText("Standing By");    
    }
    
    /***
     * Método para atualizar a Tab de agente
     */
    @FXML
    public void updateAgentTab(){
        agentTable.getColumns().clear();
        agentTable.getColumns().addAll(agentNameCol, agentPosCol, agentOrderCol);
        
        agentNameCol.setCellValueFactory(new PropertyValueFactory<>("name"));
        agentOrderCol.setCellValueFactory(new PropertyValueFactory<>("endTurnActionDesc"));
        agentPosCol.setCellValueFactory(new PropertyValueFactory<>("currentPos"));
        
        ObservableList obl = player.getAgents ();
        agentTable.setItems(obl);
        
        //Se há um agente selecionado, prepara a lista de regiões adjacentes
        if(selectedCharacter != null){
            
            printCharacterStats();
            
            obl = (ObservableList) world.getRegions(selectedCharacter.getCurrentPos());
            agentChangePos.setItems(obl);
        }
    }

    
    @FXML
    /**
     * Método para selecionar um personagem da tabela.
     */
    public void selectCharacter(){
        if(agentTable.getSelectionModel().getSelectedIndex() >=0 ){
            if(selectedCharacter != agentTable.getSelectionModel().getSelectedItem()) {
                //seta a referencia ao personagem clicado
                selectedCharacter = agentTable.getSelectionModel().getSelectedItem(); 
                
                //Seta regiões viajáveis
                ObservableList obl = (ObservableList) world.getRegions(selectedCharacter.getCurrentPos());
                agentChangePos.setItems(obl);
                printCharacterStats();
            }
        }
    }
    
    @FXML
    /***
     * Método para fazer scheduling de viagem de um personagem para outra região.
     * REDUZIR FUNDOS DA VIAGEM, QUANTO MAIS LONGE, MAIS CARO
     */
    public void scheduleTravelAction(){
        if (selectedCharacter != null) {
            if(agentChangePos!=null){
                Region destination = agentChangePos.getValue();
                if(destination!=null){
                    turn.scheduleAction(new TravelAction (player, selectedCharacter, destination));

                    agentChangePos.setValue(null);
                    updateAgentTab();
                }
            }
            else{
                guiPlayerOutput.appendText("\nSelect a Region");
            }
        }
        else {
            guiPlayerOutput.appendText("\nSelect a Character !");
        }
    }
    
    
    @FXML
    /**
     * Método para abortar uma ação que foi agendada.
     */
    public void abortAction(){
        if(selectedCharacter != null){
            if(selectedCharacter.getEndTurnAction() != null){
                turn.abortScheduleAction( selectedCharacter.getEndTurnAction() );
                selectedCharacter.setEndTurnAction(null);
                updateAgentTab();
            }
            else
                guiPlayerOutput.appendText("\nCharacter has no orders !");
        }
        else{
            guiPlayerOutput.appendText("\nSelect a Character !");
        }
    }

    @FXML
    /**
     * Método para abrir a janela de ordem de suborno.
     */
    public void openBribe(){
        /*if(selectedCharacter != null){//Clicou em um index valido na tabela esquerda
            try {
            FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("AgntBribeFXML.fxml"));
                    Parent root1 = (Parent) fxmlLoader.load();
                    Stage stage = new Stage();
                    stage.setResizable(false);
                    AgntBribeController controller = fxmlLoader.<AgntBribeController>getController();
                    controller.initialize(turn, player, selectedCharacter);//Por enquanto só seta para NAFRAN

                    stage.initModality(Modality.APPLICATION_MODAL);//Bloqueia outras janelas até fechar essa
                    stage.setScene(new Scene(root1));  
                    stage.showAndWait();
            } catch(Exception e) {
               e.printStackTrace();
              }
        }
        else
            guiPlayerOutput.appendText("\nPlease, select an agent");*/
    }
    
    @FXML
    /***
     * Método para abrir a janela de ordem de assassinato.
     */
    public void openAssassinate(){/*
        if(selectedCharacter != null){//Clicou em um index valido na tabela esquerda
            try {
            FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("AgntAssassinateFXML.fxml"));
                    Parent root1 = (Parent) fxmlLoader.load();
                    Stage stage = new Stage();
                    stage.setResizable(false);
                    AgntAssassinateController controller = fxmlLoader.<AgntAssassinateController>getController();
                    controller.initialize(turn, player, selectedCharacter);//Por enquanto só seta para NAFRAN

                    stage.initModality(Modality.APPLICATION_MODAL);//Bloqueia outras janelas até fechar essa
                    stage.setScene(new Scene(root1));  
                    stage.showAndWait();
            } catch(Exception e) {
               e.printStackTrace();
              }
        }
        else
            guiPlayerOutput.appendText("\nPlease, select an agent");*/
    }
    
    @FXML
    /***
     * Usado no menu para agendar uma ação de busca e destruição de evidências.
     */
    public void scheduleSearchDestroyEviAction(){
        if(selectedCharacter != null){
            turn.scheduleAction(new SearchDestroyAction(player, selectedCharacter));
            updateAgentTab();
        }
        else
            guiPlayerOutput.appendText("\nPlease, select an agent");
    }
    
    
    /***
     * Usado no menu para agendar uma ação de Overwatch.
     */
    @FXML
    public void scheduleOverwatchAction(){
        if(selectedCharacter != null){//Clicou em um index valido na tabela esquerda
           turn.scheduleAction(new OverwatchAction (player, selectedCharacter));
           updateAgentTab();           
        }
        else
            guiPlayerOutput.appendText("\nPlease, select an agent");
    }
        
    //==========================================================================
    //TAB DE TURNOS-------------------------------------------------------------
    //==========================================================================
    
    public void initializeTurnTab(){
        //Inicializa a tabela
        turnShortCol.setCellValueFactory(new PropertyValueFactory<>("shortDesc")); //Turn short Description

        ObservableList obl = turn.getActionsObl();
        turnTable.setItems(obl);
    }    
    
    
    @FXML
    /**
     * Método para selecionar um personagem da tabela.
     */
    public void selectAction(){
        if(turnTable.getSelectionModel().getSelectedIndex() >=0 ){//Clicou em um index valido na tabela esquerda
            if(selectedAction != turnTable.getSelectionModel().getSelectedItem()) {
                //seta a referencia ao personagem clicado
                selectedAction = turnTable.getSelectionModel().getSelectedItem(); 
                turnActionDesc.setText(selectedAction.toString());
                turnActionName.setText(selectedAction.getShortDesc());
            }
        }
    }
    
    
    //==========================================================================
    //GERAIS--------------------------------------------------------------------
    //==========================================================================
    
    
    /**
     * Método usado para trocar a Tab selecionada.
     * @param tab Tab destino
     */
    private void switchTab(Tab tab) {
        //pane.getSelectionModel().select(inventory);
        SingleSelectionModel<Tab> selectionModel = pane.getSelectionModel();
        selectionModel.select(inventory);
    }
    
    
    @FXML
    public void openPauseMenu(){
        JavafxManager.openPauseMenu(this);
    }
    
    
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
        
        //player:---------------------------------------------------------------
        player.setFunds(false, player.getAgentUpkeep() + 
                player.getTransportUpkeep() + 
                player.getWarehouseUpkeep());//Subtrai os upkeeps dos fundos do jogador.
        player.decrementNotoriety();
        player.moveTransports();
        
        guiPlayerOutput.clear();

        //Autoridades:----------------------------------------------------------
        //Rodar ações das autoridades
        law.execute(player, world);
        
        //Turns:----------------------------------------------------------------
        turnActionDesc.setText("");
        turnActionName.setText("");
        
        //world:----------------------------------------------------------------
        currentTurn ++;
        world.updateMarkets();
        world.updateRegions();        
        
        // finaliza Turn, e o reseta pro próximo
        turn.endTurn ();
        turn.reset ();
        
        //initializeAgentTab();
        updateAgentTab();
        updateTransportTab();
       
        //updateInventoryTab();

        updateEssentialInfo();
    }
    
    @FXML
    /***
     * Método para abrir o mercado negro de uma região em uma subjanela.
     * A Sub-janela se comunicará com o controller principal do jogo para trocar informações.
     * 
     */
    private void openBL(ActionEvent ev){
        
        Region whereTo = null;

        if(ev.getSource() == blNaf)
            whereTo = world.getRegion(0);
        else if(ev.getSource() == blCol)
            whereTo = world.getRegion(1);
        else if(ev.getSource()==blCal)
            whereTo = world.getRegion(2);
        else if(ev.getSource()==blUra)
            whereTo = world.getRegion(3);
        else if(ev.getSource()==blRut)
            whereTo = world.getRegion(4);
        else if(ev.getSource()==blAur)
            whereTo = world.getRegion(5);
        
        JavafxManager.openBlackMarketWindow(this, whereTo);
        
    }
    
    @FXML
    /*Temporário, para debugging*/
    public void playerTravel(ActionEvent e) {
		// Região da qual player tá saindo
		Region whereFrom = player.getCurrentPos ();
		// Região pra qual player tá viajando
		Region whereTo = null;

        if(e.getSource() == trvNaf)
            whereTo = world.getRegion(0);
        else if(e.getSource() == trvCol)
            whereTo = world.getRegion(1);
        else if(e.getSource()==trvCal)
            whereTo = world.getRegion(2);
        else if(e.getSource()==trvUra)
            whereTo = world.getRegion(3);
        else if(e.getSource()==trvRut)
            whereTo = world.getRegion(4);
        else if(e.getSource()==trvAur)
            whereTo = world.getRegion(5);

		// se rolou viagem, adiciona tal ação
		if (whereTo != null) {
			// verifica primeiro se tem a conexão, né
			Connection con = whereFrom.getConnection (whereTo);
			if (con != null) {
				turn.addAction (new TravelAction (player, player, con));
			}
			else {
				System.err.println ("[GameController.playerTravel] Conexão \"" +
						whereFrom.getName () + " -> " + whereTo.getName () +
						"\" não existe!");
			}
		}
        
        updateEssentialInfo();
        
        selectedAction = null;
        selectedCharacter = null;
        selectedWeapon = null;
    }
    
    /*Atualiza info no HUD*/
    public void updateEssentialInfo(){
        guiHeat.setText("HEAT: " + player.getHeat());
        guiFunds.setText("FUNDS: "+ player.getFunds());
        guiPlayerPos.setText("CURRENT POSITION: " + player.getCurrentPos().getName());
        guiCurrentTurn.setText("CURRENT TURN: " + currentTurn);
        guiNotoriety.setText("NOTORIETY: " + player.getNotoriety());
    }
    
    /***
     * Método genérico para verificar se um inteiro se encontra dentro da faixa entre min e max.
     * @param min Valor mínimo
     * @param max Valor máximo
     * @param num Número a ser comparado.
     * @param orEqual Se o valor pode ser igual ao minimo ou máximo
     * @return TRUE se estiver na faixa
     */
    public static boolean checkIntRange(int min, int max, int num, boolean orEqual){
        if(orEqual){
            if( min <= num && num <= max)
                return true;
            else
                return false;
        }
        else{
            if( min < num && num < max)
                return true;
            else
                return false;
        }
    }
    
    /***
     * Método que realiza o retorno de um número dado ao seu valor base através
     * de incrementos ou decrementos de valores aleatórios.
     * @param min Menor valor que o número pode assumir
     * @param max Maior valor que o número pode assumir
     * @param num Número a ser modificado
     * @param numBase Valro base do número
     * @param orEqual Se ele pode ser igual aos limites da faixa de valor
     * @param diceType Número máximo do valor do "dado" a 
     * ser incrementado/decrementado Considerando o valor mínimo do dado como 1
     * @return num+resultado do dado.
     */
    public static int returnToBase(int min, int max, int num, int numBase, boolean orEqual, int diceType){
        int diceRoll = rollDie(diceType);
        
        if(num < numBase) {
            while( ! checkIntRange(min, max, num + diceRoll, orEqual) ) {
                    diceRoll = rollDie(diceType);
            }
            return num + diceRoll;
        }
        
        else if(num > numBase) {
            while( ! checkIntRange(min, max, num - diceRoll, orEqual) ) {
                    diceRoll = rollDie(diceType);
            }
            return num - diceRoll;
        }
        
        else{
            return num;
        }
    }
    
    /**
     * Initializes the controller class.
     * Incializa o jogador e outras informações baseada na string que será passada do menu de new/load game. A string é o nome do arquivo de save
     *
     * @param name nome do personagem do jogador
     * @param barter quantidade de pontos de barter
     * @param intrigue quantidade de pontos de intrigue
     * @param investigation quantidade de pontos de investigation
     * @param funds  quantidade de fundos
     */
    public void initialize(String name, int barter, int intrigue, int investigation, int funds) {
       /* mapTabText = new Label();
        mapTabText.setText("MAP");
        mapTabText.setRotate(90);
        stp  = new StackPane(new Group(mapTabText));
        stp.setPrefWidth(80);
        stp.setPrefHeight(80);
        map.setGraphic(stp);*/
        
        //Auxs:-----------------------------------------------------------------
        selectedWeapon = null;
        
        invSelectRight= null;
        invSelectLeft= null;
        invSelectedWpn = null;
        
        tranSelectedTransport = null;
        
        selectedCharacter = null;
        selectedAction = null;
        
        //Componentes:----------------------------------------------------------
        world = new World();
        turn = new Turn ();
        law = new LawManager();
                
        player = new PlayerCharacter(name, world.getRegion(0), barter, intrigue, investigation , funds);

        
        currentTurn = 0;
        
        updateEssentialInfo();
        initializeTransportsTab();
        initializeInventoryTab();
        initializeAgentTab();
        player.addAgent (new Agent (player.getCurrentPos ()));
        initializeTurnTab();
        
        
        //Detecta o ESC e abre o pause
        wnd.addEventFilter(KeyEvent.KEY_PRESSED, (KeyEvent k) -> {
            //close window
            if(k.getCode() == KeyCode.ESCAPE){
                JavafxManager.openPauseMenu(this);
            }
        });
    }    
    
}
