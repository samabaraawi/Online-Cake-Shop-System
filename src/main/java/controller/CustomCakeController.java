package controller;

import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.layout.VBox;
import javafx.stage.FileChooser;
import model.CustomCake;
import service.CustomCakeService;
import util.SceneManager;

import java.io.File;
import java.time.LocalDate;

public class CustomCakeController {

    @FXML
    private TextField customerNameField;

    @FXML
    private ComboBox<String> sizeComboBox;

    @FXML
    private ComboBox<String> flavorComboBox;

    @FXML
    private ComboBox<String> fillingComboBox;

    @FXML
    private ComboBox<String> frostingComboBox;

    @FXML
    private TextArea messageArea;

    @FXML
    private TextArea instructionsArea;

    @FXML
    private Label imageNameLabel;

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
    private DatePicker pickupDatePicker;

    @FXML
    private ComboBox<String> pickupTimeComboBox;

    private File selectedImage;

    @FXML
    public void initialize() {

        sizeComboBox.getItems().addAll(
                "Small",
                "Medium",
                "Large"
        );

        flavorComboBox.getItems().addAll(
                "Chocolate",
                "Vanilla",
                "Red Velvet"
        );

        fillingComboBox.getItems().addAll(
                "Chocolate",
                "Strawberry",
                "Vanilla Cream"
        );

        frostingComboBox.getItems().addAll(
                "Buttercream",
                "Cream Cheese",
                "Whipped Cream"
        );

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



        deliveryRadio.setOnAction(e -> showDelivery());

        pickupRadio.setOnAction(e -> showPickup());

        showDelivery();

    }

    private void showDelivery() {

        deliverySection.setVisible(true);
        deliverySection.setManaged(true);

        pickupSection.setVisible(false);
        pickupSection.setManaged(false);

    }

    private void showPickup() {

        deliverySection.setVisible(false);
        deliverySection.setManaged(false);

        pickupSection.setVisible(true);
        pickupSection.setManaged(true);

    }

    @FXML
    private void chooseImage() {

        FileChooser chooser = new FileChooser();

        chooser.setTitle("Choose Reference Image");

        chooser.getExtensionFilters().add(

                new FileChooser.ExtensionFilter(
                        "Images",
                        "*.png",
                        "*.jpg",
                        "*.jpeg"
                )

        );

        selectedImage = chooser.showOpenDialog(null);

        if (selectedImage != null) {

            imageNameLabel.setText(
                    selectedImage.getName()
            );

        }

    }

    @FXML
    private void submitRequest() {

        if (customerNameField.getText().isBlank()
                || sizeComboBox.getValue() == null
                || flavorComboBox.getValue() == null
                || fillingComboBox.getValue() == null
                || frostingComboBox.getValue() == null) {

            showAlert("Please complete all customer and cake information.");

            return;
        }

        if (selectedImage == null) {

            showAlert("Please upload a reference image.");

            return;
        }

        if (deliveryRadio.isSelected()) {

            if (addressField.getText().isBlank()
                    || phoneField.getText().isBlank()) {

                showAlert("Please enter the delivery information.");

                return;
            }

        } else {

            if (branchComboBox.getValue() == null
                    || pickupDatePicker.getValue() == null
                    || pickupTimeComboBox.getValue() == null) {

                showAlert("Please complete the pickup information.");

                return;
            }

        }

        // 48 Hours Rule
        LocalDate selectedDate;

        if (deliveryRadio.isSelected()) {

            selectedDate = LocalDate.now().plusDays(2);

        } else {

            selectedDate = pickupDatePicker.getValue();

            if (selectedDate.isBefore(LocalDate.now().plusDays(2))) {

                showAlert("Custom cake orders require at least 48 hours notice.");

                return;

            }

        }

        CustomCake request = new CustomCake();

        request.setCustomerName(
                customerNameField.getText()
        );

        request.setSize(
                sizeComboBox.getValue()
        );

        request.setFlavor(
                flavorComboBox.getValue()
        );

        request.setFilling(
                fillingComboBox.getValue()
        );

        request.setFrosting(
                frostingComboBox.getValue()
        );

        request.setMessage(
                messageArea.getText()
        );

        request.setSpecialInstructions(
                instructionsArea.getText()
        );

        request.setReferenceImagePath(
                selectedImage.getAbsolutePath()
        );

        if (deliveryRadio.isSelected()) {

            request.setFulfillmentMethod("Delivery");

            request.setAddress(
                    addressField.getText()
            );

            request.setPhoneNumber(
                    phoneField.getText()
            );

            request.setPickupDate(
                    LocalDate.now().plusDays(2).toString()
            );

        } else {

            request.setFulfillmentMethod("Pickup");

            request.setBranch(
                    branchComboBox.getValue()
            );

            request.setPickupDate(
                    pickupDatePicker.getValue().toString()
            );

            request.setPickupTime(
                    pickupTimeComboBox.getValue()
            );

        }

        CustomCakeService
                .getInstance()
                .submitRequest(request);

        SceneManager.switchScene(
                "CustomCakeConfirmation.fxml"
        );

    }

    private void showAlert(String message) {

        Alert alert = new Alert(Alert.AlertType.WARNING);

        alert.setHeaderText(null);

        alert.setTitle("Missing Information");

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