package controller;

import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.layout.VBox;
import model.Order;
import model.Promotion;
import service.OrderService;
import model.Payment;
import model.Order;
import service.PaymentService;
import service.OrderService;
import service.PromotionCalculator;
import util.SceneManager;

public class PaymentController {

    @FXML
    private Label cakeLabel;

    @FXML
    private Label quantityLabel;

    @FXML
    private Label methodLabel;

    @FXML
    private Label totalLabel;

    @FXML
    private RadioButton cashRadio;

    @FXML
    private RadioButton cardRadio;

    @FXML
    private VBox cardSection;

    @FXML
    private TextField holderField;

    @FXML
    private TextField cardNumberField;

    @FXML
    private TextField expiryField;

    @FXML
    private PasswordField cvvField;

    @FXML
    private Label originalPriceLabel;

    @FXML
    private Label promotionLabel;
    @FXML
    private Label deliveryFeeLabel;

    private OrderService orderService = OrderService.getInstance();
    private PaymentService paymentService = PaymentService.getInstance();
    @FXML
    public void initialize() {

        Order order = orderService.getCurrentOrder();

        if (order == null)
            return;

        cakeLabel.setText("Cake : " + order.getCake().getName());

        quantityLabel.setText("Quantity : " + order.getQuantity());

        methodLabel.setText("Method : " + order.getFulfillmentMethod());

        double originalPrice = order.getCake().getPrice();

        double discountedPrice =
                PromotionCalculator.calculatePrice(originalPrice);

        double total =
                (discountedPrice * order.getQuantity())
                        + order.getDeliveryFee();

        deliveryFeeLabel.setText(
                "$" + String.format("%.2f", order.getDeliveryFee())
        );
        originalPriceLabel.setText(
                "$" + String.format("%.2f", originalPrice)
        );

        Promotion promotion =
                PromotionCalculator.getActivePromotion();

        if (promotion != null) {

            promotionLabel.setText(
                    "🎉 " +
                            promotion.getTitle() +
                            " (-" +
                            (int) promotion.getDiscount() +
                            "%)"
            );

        } else {

            promotionLabel.setText("No Active Promotion");

        }

        totalLabel.setText(
                "$" + String.format("%.2f", total)
        );

        cashRadio.setOnAction(e -> showCash());

        cardRadio.setOnAction(e -> showCard());

        showCash();

    }

    private void showCash() {

        cardSection.setVisible(false);
        cardSection.setManaged(false);

    }

    private void showCard() {

        cardSection.setVisible(true);
        cardSection.setManaged(true);

    }

    @FXML
    private void completePayment() {

        Order order = orderService.getCurrentOrder();

        if (order == null) {

            Alert alert = new Alert(Alert.AlertType.ERROR);

            alert.setHeaderText(null);

            alert.setContentText("No order found.");

            alert.showAndWait();

            return;
        }

        Payment payment = new Payment();

        double discountedPrice =
                PromotionCalculator.calculatePrice(
                        order.getCake().getPrice());

        double total =
                (discountedPrice * order.getQuantity())
                        + order.getDeliveryFee();
        payment.setAmount(total);

        if (cashRadio.isSelected()) {

            payment.setPaymentMethod("Cash");

        }

        else {

            if (holderField.getText().isBlank()
                    || cardNumberField.getText().isBlank()
                    || expiryField.getText().isBlank()
                    || cvvField.getText().isBlank()) {

                Alert alert =
                        new Alert(Alert.AlertType.WARNING);

                alert.setHeaderText(null);

                alert.setContentText("Please complete all card information.");

                alert.showAndWait();

                return;
            }

            payment.setPaymentMethod("Credit Card");

            payment.setCardHolder(holderField.getText());

            payment.setCardNumber(cardNumberField.getText());

            payment.setExpiryDate(expiryField.getText());

            payment.setCvv(cvvField.getText());

        }

        paymentService.processPayment(payment);

        order.setOrderStatus("Paid");

        SceneManager.switchScene("OrderConfirmation.fxml");

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