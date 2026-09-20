package controller;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TextField;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.FlowPane;
import model.Cake;
import service.CakeService;
import util.SceneManager;

import java.io.IOException;

public class BrowseCakesController {

    @FXML
    private FlowPane cakesContainer;

    @FXML
    private TextField searchField;

    @FXML
    private ComboBox<String> categoryBox;

    private final CakeService cakeService = CakeService.getInstance();

    @FXML
    public void initialize() {

        categoryBox.getItems().addAll(
                "All",
                "Birthday",
                "Wedding"
        );

        categoryBox.setValue("All");

        loadCakes();

        searchField.textProperty().addListener((obs, oldValue, newValue) -> searchCake());

        categoryBox.setOnAction(e -> searchCake());

    }

    private void loadCakes() {

        searchCake();

    }

    @FXML
    private void searchCake() {

        cakesContainer.getChildren().clear();

        String keyword = searchField.getText() == null
                ? ""
                : searchField.getText().trim().toLowerCase();

        String category = categoryBox.getValue();

        for (Cake cake : cakeService.getAvailableCakes()) {

            boolean matchesSearch =
                    cake.getName().toLowerCase().contains(keyword)
                            || cake.getFlavor().toLowerCase().contains(keyword)
                            || cake.getCategory().toLowerCase().contains(keyword);

            boolean matchesCategory =
                    category.equals("All")
                            || cake.getCategory().equalsIgnoreCase(category);

            if (matchesSearch && matchesCategory) {

                try {

                    FXMLLoader loader =
                            new FXMLLoader(getClass().getResource("/view/CakeCard.fxml"));

                    BorderPane card = loader.load();

                    CakeCardController controller = loader.getController();

                    controller.setCake(cake);

                    cakesContainer.getChildren().add(card);

                } catch (IOException e) {

                    e.printStackTrace();

                }

            }

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