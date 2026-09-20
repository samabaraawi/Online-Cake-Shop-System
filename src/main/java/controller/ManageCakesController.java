package controller;

import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.GridPane;
import model.Cake;
import service.CakeService;
import util.SceneManager;

import java.util.Optional;

public class ManageCakesController {

    @FXML
    private TableView<Cake> cakeTable;

    @FXML
    private TableColumn<Cake, Integer> idColumn;

    @FXML
    private TableColumn<Cake, String> nameColumn;

    @FXML
    private TableColumn<Cake, Double> priceColumn;

    @FXML
    private TableColumn<Cake, String> categoryColumn;

    @FXML
    private TableColumn<Cake, Boolean> availableColumn;

    private final CakeService cakeService = CakeService.getInstance();

    @FXML
    public void initialize() {

        idColumn.setCellValueFactory(
                new PropertyValueFactory<>("cakeID")
        );

        nameColumn.setCellValueFactory(
                new PropertyValueFactory<>("name")
        );

        priceColumn.setCellValueFactory(
                new PropertyValueFactory<>("price")
        );

        categoryColumn.setCellValueFactory(
                new PropertyValueFactory<>("category")
        );

        availableColumn.setCellValueFactory(
                new PropertyValueFactory<>("available")
        );

        cakeTable.setItems(cakeService.getAllCakes());
    }

    @FXML
    private void addCake() {

        Cake newCake = showCakeDialog(null);

        if (newCake != null) {

            newCake.setCakeID(cakeService.getNextCakeID());

            cakeService.addCake(newCake);

            cakeTable.refresh();

            showInfo("Cake added successfully.");
        }
    }

    @FXML
    private void editCake() {

        Cake selectedCake =
                cakeTable.getSelectionModel().getSelectedItem();

        if (selectedCake == null) {

            showWarning("Please select a cake to edit.");

            return;
        }

        Cake updatedCake = showCakeDialog(selectedCake);

        if (updatedCake != null) {

            selectedCake.setName(updatedCake.getName());
            selectedCake.setDescription(updatedCake.getDescription());
            selectedCake.setPrice(updatedCake.getPrice());
            selectedCake.setCategory(updatedCake.getCategory());
            selectedCake.setAvailable(updatedCake.isAvailable());
            selectedCake.setImagePath(updatedCake.getImagePath());

            cakeTable.refresh();

            showInfo("Cake updated successfully.");
        }
    }

    @FXML
    private void deleteCake() {

        Cake selectedCake =
                cakeTable.getSelectionModel().getSelectedItem();

        if (selectedCake == null) {

            showWarning("Please select a cake to delete.");

            return;
        }

        Alert confirm =
                new Alert(Alert.AlertType.CONFIRMATION);

        confirm.setTitle("Delete Cake");
        confirm.setHeaderText(null);
        confirm.setContentText(
                "Are you sure you want to delete " +
                        selectedCake.getName() + "?"
        );

        Optional<ButtonType> result = confirm.showAndWait();

        if (result.isPresent()
                && result.get() == ButtonType.OK) {

            cakeService.deleteCake(selectedCake);

            cakeTable.refresh();

            showInfo("Cake deleted successfully.");
        }
    }

    private Cake showCakeDialog(Cake cakeToEdit) {

        Dialog<Cake> dialog = new Dialog<>();

        if (cakeToEdit == null) {
            dialog.setTitle("Add Cake");
        } else {
            dialog.setTitle("Edit Cake");
        }

        dialog.setHeaderText(null);

        ButtonType saveButton =
                new ButtonType("Save", ButtonBar.ButtonData.OK_DONE);

        dialog.getDialogPane().getButtonTypes().addAll(
                saveButton,
                ButtonType.CANCEL
        );

        TextField nameField = new TextField();
        nameField.setPromptText("Cake name");

        TextArea descriptionArea = new TextArea();
        descriptionArea.setPromptText("Description");
        descriptionArea.setPrefRowCount(3);

        TextField priceField = new TextField();
        priceField.setPromptText("Price");

        TextField categoryField = new TextField();
        categoryField.setPromptText("Category");

        TextField imagePathField = new TextField();
        imagePathField.setPromptText("/images/example.jpg");

        CheckBox availableCheckBox =
                new CheckBox("Available");

        if (cakeToEdit != null) {

            nameField.setText(cakeToEdit.getName());
            descriptionArea.setText(cakeToEdit.getDescription());
            priceField.setText(String.valueOf(cakeToEdit.getPrice()));
            categoryField.setText(cakeToEdit.getCategory());
            imagePathField.setText(cakeToEdit.getImagePath());
            availableCheckBox.setSelected(cakeToEdit.isAvailable());

        } else {

            availableCheckBox.setSelected(true);
        }

        GridPane grid = new GridPane();

        grid.setHgap(10);
        grid.setVgap(10);

        grid.add(new Label("Name:"), 0, 0);
        grid.add(nameField, 1, 0);

        grid.add(new Label("Description:"), 0, 1);
        grid.add(descriptionArea, 1, 1);

        grid.add(new Label("Price:"), 0, 2);
        grid.add(priceField, 1, 2);

        grid.add(new Label("Category:"), 0, 3);
        grid.add(categoryField, 1, 3);

        grid.add(new Label("Image Path:"), 0, 4);
        grid.add(imagePathField, 1, 4);

        grid.add(new Label("Availability:"), 0, 5);
        grid.add(availableCheckBox, 1, 5);

        dialog.getDialogPane().setContent(grid);

        dialog.setResultConverter(button -> {

            if (button == saveButton) {

                if (nameField.getText().isBlank()
                        || descriptionArea.getText().isBlank()
                        || priceField.getText().isBlank()
                        || categoryField.getText().isBlank()) {

                    showWarning("Please fill all required fields.");

                    return null;
                }

                double price;

                try {

                    price = Double.parseDouble(priceField.getText());

                } catch (NumberFormatException e) {

                    showWarning("Price must be a valid number.");

                    return null;
                }

                Cake cake = new Cake();

                cake.setName(nameField.getText());
                cake.setDescription(descriptionArea.getText());
                cake.setPrice(price);
                cake.setCategory(categoryField.getText());
                cake.setImagePath(imagePathField.getText());
                cake.setAvailable(availableCheckBox.isSelected());

                return cake;
            }

            return null;
        });

        Optional<Cake> result = dialog.showAndWait();

        return result.orElse(null);
    }

    private void showWarning(String message) {

        Alert alert =
                new Alert(Alert.AlertType.WARNING);

        alert.setTitle("Warning");
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
    }

    private void showInfo(String message) {

        Alert alert =
                new Alert(Alert.AlertType.INFORMATION);

        alert.setTitle("Information");
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