package controller;

import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import model.Order;
import service.OrderService;
import util.SceneManager;

public class AcceptOrdersController {

    @FXML
    private TableView<Order> ordersTable;

    @FXML
    private TableColumn<Order,Integer> idColumn;

    @FXML
    private TableColumn<Order,String> cakeColumn;

    @FXML
    private TableColumn<Order,String> statusColumn;

    @FXML
    private ComboBox<String> statusBox;

    private final OrderService orderService =
            OrderService.getInstance();

    @FXML
    public void initialize() {

        idColumn.setCellValueFactory(
                new PropertyValueFactory<>("orderID"));

        cakeColumn.setCellValueFactory(cell ->
                new javafx.beans.property.SimpleStringProperty(
                        cell.getValue().getCake().getName()));

        statusColumn.setCellValueFactory(
                new PropertyValueFactory<>("orderStatus"));

        ordersTable.setItems(orderService.getAllOrders());

        statusBox.getItems().addAll(

                "Confirmed",
                "Preparing",
                "Ready For Pickup",
                "Out For Delivery",
                "Delivered",
                "Cancelled",
                "Rejected"

        );

        statusBox.setValue("Confirmed");

    }

    @FXML
    private void acceptOrder() {

        Order order = ordersTable.getSelectionModel().getSelectedItem();

        if(order == null){

            Alert alert =
                    new Alert(Alert.AlertType.WARNING);

            alert.setHeaderText(null);

            alert.setContentText("Please select an order.");

            alert.showAndWait();

            return;

        }

        order.setOrderStatus(statusBox.getValue());
        ordersTable.refresh();

        Alert alert =
                new Alert(Alert.AlertType.INFORMATION);

        alert.setHeaderText(null);

        alert.setContentText("Order accepted successfully.");

        alert.showAndWait();

    }
    @FXML
    private void updateStatus() {

        Order order =
                ordersTable.getSelectionModel().getSelectedItem();

        if (order == null) {

            Alert alert =
                    new Alert(Alert.AlertType.WARNING);

            alert.setHeaderText(null);

            alert.setContentText("Please select an order.");

            alert.showAndWait();

            return;

        }

        order.setOrderStatus(statusBox.getValue());

        ordersTable.refresh();

        Alert alert =
                new Alert(Alert.AlertType.INFORMATION);

        alert.setHeaderText(null);

        alert.setContentText("Order status updated successfully.");

        alert.showAndWait();

    }


    @FXML
    private void goBack(){

        SceneManager.goBack();

    }

    @FXML
    private void goHome(){

        SceneManager.goHome();

    }

}