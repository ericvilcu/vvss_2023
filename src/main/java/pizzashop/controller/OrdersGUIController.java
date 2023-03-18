package pizzashop.controller;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import pizzashop.gui.PaymentAlert;
import pizzashop.model.MenuDataModel;
import pizzashop.service.PizzaService;

import java.util.Calendar;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

public class OrdersGUIController {
    private static final Logger log = LogManager.getLogger(OrdersGUIController.class);
    @FXML
    private ComboBox<Integer> orderQuantity;
    @FXML
    private TableView<MenuDataModel> orderTable;
    @FXML
    private TableColumn<MenuDataModel,Integer> tableQuantity;
    @FXML
    protected TableColumn<MenuDataModel,String> tableMenuItem;
    @FXML
    private TableColumn<MenuDataModel,Double> tablePrice;
    @FXML
    private Label pizzaTypeLabel;
    @FXML
    private Button addToOrder;
    @FXML
    private Label orderStatus;
    @FXML
    private Button placeOrder;
    @FXML
    private Button orderServed;
    @FXML
    private Button payOrder;
    @FXML
    private Button newOrder;

    private   List<String> orderList = FXCollections.observableArrayList();
    private List<Double> orderPaymentList = FXCollections.observableArrayList();
    public static double getTotalAmount() {
        return totalAmount;
    }
    public static void setTotalAmount(double totalAmount) {
        OrdersGUIController.totalAmount = totalAmount;
    }

    private PizzaService service;
    private int tableNumber;

    protected ObservableList<String> observableList;
    private final TableView<MenuDataModel> table = new TableView<>();

    private static double totalAmount;


    public void setService(PizzaService service, int tableNumber){
        this.service=service;
        this.tableNumber=tableNumber;
        initData();

    }

    private void initData(){
        ObservableList<MenuDataModel> menuData;
        menuData = FXCollections.observableArrayList(service.getMenuData());
        menuData.setAll(service.getMenuData());
        orderTable.setItems(menuData);

        //Controller for Place Order Button
        Calendar now = Calendar.getInstance();
        placeOrder.setOnAction(event ->{
            orderList= menuData.stream()
                    .filter(x -> x.getQuantity()>0)
                    .map(menuDataModel -> menuDataModel.getQuantity() +" "+ menuDataModel.getMenuItem())
                    .collect(Collectors.toList());
            observableList = FXCollections.observableList(orderList);
            KitchenGUIController.order.add("Table" + tableNumber +" "+ orderList.toString());
            orderStatus.setText("Order placed at: " +  now.get(Calendar.HOUR)+":"+now.get(Calendar.MINUTE));
        });

        //Controller for Order Served Button
        orderServed.setOnAction(event ->
                orderStatus.setText("Served at: " + now.get(Calendar.HOUR)+":"+now.get(Calendar.MINUTE))
        );

        //Controller for Pay Order Button
        payOrder.setOnAction(event -> {
            orderPaymentList= menuData.stream()
                    .filter(x -> x.getQuantity()>0)
                    .map(menuDataModel -> menuDataModel.getQuantity()*menuDataModel.getPrice())
                    .collect(Collectors.toList());
            setTotalAmount(orderPaymentList.stream().mapToDouble(e->e).sum());
            orderStatus.setText("Total amount: " + getTotalAmount());
            log.info("--------------------------");
            log.info("Table: " + tableNumber);
            log.info("Total: " + getTotalAmount());
            log.info("--------------------------");
            PaymentAlert pay = new PaymentAlert(service);
            pay.showPaymentAlert(tableNumber, OrdersGUIController.getTotalAmount());
        });
    }

    public void initialize(){

        //populate table view with menuData from OrderGUI
        table.setEditable(true);
        tableMenuItem.setCellValueFactory(
                new PropertyValueFactory<MenuDataModel, String>("menuItem"));
        tablePrice.setCellValueFactory(
                new PropertyValueFactory<MenuDataModel, Double>("price"));
        tableQuantity.setCellValueFactory(
                new PropertyValueFactory<MenuDataModel, Integer>("quantity"));

        //bind pizzaTypeLabel and quantity combo box with the selection on the table view
        orderTable.getSelectionModel().selectedItemProperty().addListener((observable, oldValue, newValue) -> pizzaTypeLabel.textProperty().bind(newValue.menuItemProperty()));

        //Populate Combo box for Quantity
        ObservableList<Integer> quantityValues =  FXCollections.observableArrayList(0, 1, 2,3,4,5);
        orderQuantity.getItems().addAll(quantityValues);
        orderQuantity.setPromptText("Quantity");

        //Controller for Add to order Button
        addToOrder.setOnAction(event ->
            orderTable.getSelectionModel().getSelectedItem().setQuantity(orderQuantity.getValue())
        );

        //Controller for Exit table Button
        newOrder.setOnAction(event -> {
            Alert exitAlert = new Alert(Alert.AlertType.CONFIRMATION, "Exit table?",ButtonType.YES, ButtonType.NO);
            Optional<ButtonType> result = exitAlert.showAndWait();
            if (result.isPresent() && result.get() == ButtonType.YES){
                Stage stage = (Stage) newOrder.getScene().getWindow();
                stage.close();
                }
        });
    }
}
