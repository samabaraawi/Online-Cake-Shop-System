package controller;

import javafx.fxml.FXML;
import javafx.scene.control.Label;
import model.CustomCake;
import service.CustomCakeService;
import util.SceneManager;

public class CustomCakeConfirmationController {

    @FXML
    private Label sizeLabel;

    @FXML
    private Label flavorLabel;

    @FXML
    private Label fillingLabel;

    @FXML
    private Label frostingLabel;

    @FXML
    private Label methodLabel;

    @FXML
    private Label statusLabel;

    @FXML
    public void initialize() {

        CustomCake request =
                CustomCakeService.getInstance().getCurrentRequest();

        if(request == null)
            return;

        sizeLabel.setText("Size : " + request.getSize());

        flavorLabel.setText("Flavor : " + request.getFlavor());

        fillingLabel.setText("Filling : " + request.getFilling());

        frostingLabel.setText("Frosting : " + request.getFrosting());

        methodLabel.setText("Fulfillment : " +
                request.getFulfillmentMethod());

        statusLabel.setText("Status : " +
                request.getRequestStatus());

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