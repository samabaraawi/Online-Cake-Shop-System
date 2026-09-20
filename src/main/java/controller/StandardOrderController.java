package controller;

import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.VBox;
import model.Cake;
import model.Promotion;
import service.PromotionCalculator;
import util.SceneManager;
import util.Session;
import model.Order;
import service.OrderService;
import service.StandardOrderService;
import service.DeliveryCalculator;


public class StandardOrderController {

    private StandardOrderService standardOrderService =
            new StandardOrderService();

    private OrderService orderService = OrderService.getInstance();

    @FXML
    private ImageView cakeImage;

    @FXML
    private Label cakeName;

    @FXML
    private Label cakeDescription;
    @FXML
    private Label deliveryFeeLabel;

    @FXML
    private Label cakePrice;

    @FXML
    private Label totalPriceLabel;

    @FXML
    private Label quantityLabel;

    @FXML
    private RadioButton deliveryRadio;

    @FXML
    private RadioButton pickupRadio;

    @FXML
    private VBox deliverySection;

    @FXML
    private VBox pickupSection;

    @FXML
    private TextField addressField;

    @FXML
    private TextField phoneField;

    @FXML
    private ComboBox<String> branchComboBox;
    @FXML
    private Label originalPriceLabel;

    @FXML
    private Label promotionLabel;

    @FXML
    private DatePicker pickupDatePicker;

    @FXML
    private ComboBox<String> pickupTimeComboBox;

    @FXML
    private TextField customerNameField;

    @FXML
    private Label cakeFlavor;

    @FXML
    private Label cakeSize;

    private int quantity = 1;

    @FXML
    public void initialize() {

        Cake cake = Session.getSelectedCake();

        if (cake == null)
            return;

        cakeName.setText(cake.getName());
        cakeDescription.setText(cake.getDescription());
        cakeFlavor.setText("Flavor : " + cake.getFlavor());
        cakeSize.setText("Size : " + cake.getSize());
        double originalPrice = cake.getPrice();

        double discountedPrice =
                PromotionCalculator.calculatePrice(originalPrice);

        originalPriceLabel.setText(
                "$" + String.format("%.2f", originalPrice)
        );

        cakePrice.setText(
                "$" + String.format("%.2f", discountedPrice)
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

        if (cake.getImagePath() != null &&
                getClass().getResource(cake.getImagePath()) != null) {

            cakeImage.setImage(
                    new Image(getClass().getResourceAsStream(cake.getImagePath()))
            );
        }

        branchComboBox.getItems().addAll(
                "Ramallah Branch",
                "Al-Bireh Branch",
                "Nablus Branch"
        );

        pickupTimeComboBox.getItems().addAll(
                "10:00 AM",
                "12:00 PM",
                "2:00 PM",
                "4:00 PM",
                "6:00 PM"
        );

        deliveryRadio.setOnAction(e -> showDeliverySection());
        pickupRadio.setOnAction(e -> showPickupSection());

        quantityLabel.setText(String.valueOf(quantity));

        showDeliverySection();

        updateTotalPrice();
        deliveryFeeLabel.setText("$0.00");

        addressField.textProperty().addListener((obs, oldValue, newValue) -> {
            updateTotalPrice();
        });
    }

    @FXML
    private void increaseQuantity() {

        quantity++;

        quantityLabel.setText(String.valueOf(quantity));

        updateTotalPrice();
    }

    @FXML
    private void decreaseQuantity() {

        if (quantity > 1) {

            quantity--;

            quantityLabel.setText(String.valueOf(quantity));

            updateTotalPrice();
        }
    }

    private void updateTotalPrice() {

        Cake cake = Session.getSelectedCake();

        if (cake == null) {
            return;
        }

        double discountedPrice =
                PromotionCalculator.calculatePrice(
                        cake.getPrice()
                );

        double cakeTotal = discountedPrice * quantity;

        double deliveryFee = 0;

        if (deliveryRadio.isSelected()
                && !addressField.getText().isBlank()) {

            deliveryFee =
                    DeliveryCalculator.calculateDeliveryFee(
                            addressField.getText()
                    );

        }

        deliveryFeeLabel.setText(
                "$" + String.format("%.2f", deliveryFee)
        );

        double finalTotal = cakeTotal + deliveryFee;

        totalPriceLabel.setText(
                "$" + String.format("%.2f", finalTotal)
        );

    }

    private void showDeliverySection() {

        deliverySection.setVisible(true);
        deliverySection.setManaged(true);

        pickupSection.setVisible(false);
        pickupSection.setManaged(false);
        updateTotalPrice();
    }

    private void showPickupSection() {

        deliverySection.setVisible(false);
        deliverySection.setManaged(false);

        pickupSection.setVisible(true);
        pickupSection.setManaged(true);
        updateTotalPrice();
    }

    @FXML
    private void continueToPayment() {

        if (deliveryRadio.isSelected()) {

            if (customerNameField.getText().isBlank()
                    || addressField.getText().isBlank()
                    || phoneField.getText().isBlank()) {

                showAlert("Please complete all required information.");

                return;
            }

        } else {

            if (customerNameField.getText().isBlank()
                    || branchComboBox.getValue() == null
                    || pickupDatePicker.getValue() == null
                    || pickupTimeComboBox.getValue() == null) {

                showAlert("Please complete all required information.");

                return;
            }

        }

        Cake cake = Session.getSelectedCake();

        boolean delivery = deliveryRadio.isSelected();

        Order order = standardOrderService.createOrder(

                cake,
                quantity,
                delivery,
                addressField.getText(),
                phoneField.getText(),
                branchComboBox.getValue(),
                pickupDatePicker.getValue() == null
                        ? ""
                        : pickupDatePicker.getValue().toString(),
                pickupTimeComboBox.getValue()

        );

        // Store customer name in the order
        order.setCustomerName(customerNameField.getText());
        if (deliveryRadio.isSelected()) {

            order.setDeliveryFee(
                    DeliveryCalculator.calculateDeliveryFee(
                            addressField.getText()
                    )
            );

        } else {

            order.setDeliveryFee(0);

        }


        orderService.createOrder(order);

        SceneManager.switchScene("Payment.fxml");

    }

    private void showAlert(String message) {

        Alert alert = new Alert(Alert.AlertType.WARNING);

        alert.setTitle("Missing Information");

        alert.setHeaderText(null);

        alert.setContentText(message);

        alert.showAndWait();

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