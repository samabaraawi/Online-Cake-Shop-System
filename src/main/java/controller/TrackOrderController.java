package controller;

import javafx.fxml.FXML;
import javafx.scene.control.Label;
import model.Order;
import service.OrderService;
import util.SceneManager;

public class TrackOrderController {

    @FXML
    private Label orderIdLabel;

    @FXML
    private Label cakeLabel;

    @FXML
    private Label statusLabel;

    @FXML
    private Label methodLabel;

    @FXML
    private Label quantityLabel;

    private final OrderService orderService =
            OrderService.getInstance();

    @FXML
    public void initialize() {

        Order order = orderService.getCurrentOrder();

        if (order == null)
            return;

        orderIdLabel.setText("Order ID : " + order.getOrderID());

        cakeLabel.setText("Cake : " + order.getCake().getName());

        statusLabel.setText("Status : " + order.getOrderStatus());

        methodLabel.setText("Fulfillment : " + order.getFulfillmentMethod());

        quantityLabel.setText("Quantity : " + order.getQuantity());

    }

    @FXML
    private void backHome() {

        SceneManager.switchScene("Main.fxml");

    }

    @FXML
    private void goBack() {

        SceneManager.goBack();

    }

    @FXML
    private void goHome() {

        SceneManager.goHome();

    }

}