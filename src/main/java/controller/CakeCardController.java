package controller;
import javafx.fxml.FXML;
import javafx.event.ActionEvent;
import model.Cake;
import util.SceneManager;
import util.Session;
import javafx.scene.image.Image;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.image.ImageView;
import model.Cake;

public class CakeCardController {
    private Cake cake;


    @FXML
    private ImageView cakeImage;

    @FXML
    private Label cakeName;

    @FXML
    private Label cakeDescription;

    @FXML
    private Label cakeCategory;

    @FXML
    private Label cakePrice;

    @FXML
    private Button orderButton;
    @FXML
    private void orderCake(ActionEvent event){

        Session.setSelectedCake(cake);

        SceneManager.switchScene("StandardOrder.fxml");

    }

    public void setCake(Cake cake) {
        this.cake = cake;
        cakeName.setText(cake.getName());
        cakeDescription.setText(cake.getDescription());
        cakeCategory.setText("Category: " + cake.getCategory());
        cakePrice.setText("$" + cake.getPrice());

        if (cake.getImagePath() != null &&
                getClass().getResource(cake.getImagePath()) != null) {

            Image image = new Image(
                    getClass().getResourceAsStream(cake.getImagePath())
            );

            cakeImage.setImage(image);
        }
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