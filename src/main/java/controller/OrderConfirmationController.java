package controller;

import javafx.fxml.FXML;
import javafx.scene.control.Label;
import model.Order;
import model.Payment;
import service.OrderService;
import service.PaymentService;
import util.SceneManager;

public class OrderConfirmationController {

    @FXML
    private Label orderIdLabel;

    @FXML
    private Label cakeLabel;

    @FXML
    private Label quantityLabel;

    @FXML
    private Label methodLabel;

    @FXML
    private Label paymentLabel;

    @FXML
    private Label statusLabel;

    @FXML
    private Label totalLabel;

    private final OrderService orderService =
            OrderService.getInstance();

    private final PaymentService paymentService =
            PaymentService.getInstance();

    @FXML
    public void initialize() {

        Order order = orderService.getCurrentOrder();

        Payment payment = paymentService.getCurrentPayment();

        if(order == null || payment == null)
            return;

        orderIdLabel.setText("Order ID : " + order.getOrderID());

        cakeLabel.setText("Cake : " + order.getCake().getName());

        quantityLabel.setText("Quantity : " + order.getQuantity());

        methodLabel.setText("Fulfillment : " + order.getFulfillmentMethod());

        paymentLabel.setText("Payment : " + payment.getPaymentMethod());

        statusLabel.setText("Status : " + order.getOrderStatus());

        totalLabel.setText("Total : $" +
                String.format("%.2f", payment.getAmount()));

    }

    @FXML
    private void backHome() {

        SceneManager.switchScene("Main.fxml");

    }

    @FXML
    private void trackOrder() {

        SceneManager.switchScene("TrackOrder.fxml");

    }

    @FXML
    private void leaveReview() {

        SceneManager.switchScene("Review.fxml");

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