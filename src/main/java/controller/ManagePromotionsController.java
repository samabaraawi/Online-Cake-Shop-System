package controller;

import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.GridPane;
import model.Promotion;
import service.PromotionService;
import util.SceneManager;

import java.util.Optional;

public class ManagePromotionsController {

    @FXML
    private TableView<Promotion> promotionTable;

    @FXML
    private TableColumn<Promotion, Integer> idColumn;

    @FXML
    private TableColumn<Promotion, String> titleColumn;

    @FXML
    private TableColumn<Promotion, Double> discountColumn;

    @FXML
    private TableColumn<Promotion, String> startDateColumn;

    @FXML
    private TableColumn<Promotion, String> endDateColumn;

    @FXML
    private TableColumn<Promotion, Boolean> activeColumn;

    private final PromotionService promotionService =
            PromotionService.getInstance();

    @FXML
    public void initialize() {

        idColumn.setCellValueFactory(
                new PropertyValueFactory<>("promotionID"));

        titleColumn.setCellValueFactory(
                new PropertyValueFactory<>("title"));

        discountColumn.setCellValueFactory(
                new PropertyValueFactory<>("discount"));

        startDateColumn.setCellValueFactory(
                new PropertyValueFactory<>("startDate"));

        endDateColumn.setCellValueFactory(
                new PropertyValueFactory<>("endDate"));

        activeColumn.setCellValueFactory(
                new PropertyValueFactory<>("active"));

        promotionTable.setItems(
                promotionService.getAllPromotions());

    }

    @FXML
    private void addPromotion() {

        Promotion promotion = showPromotionDialog(null);

        if (promotion != null) {

            promotion.setPromotionID(
                    promotionService.getNextPromotionID());

            promotionService.addPromotion(promotion);

            promotionTable.refresh();

            showInfo("Promotion added successfully.");

        }

    }

    @FXML
    private void editPromotion() {

        Promotion selectedPromotion =
                promotionTable.getSelectionModel().getSelectedItem();

        if (selectedPromotion == null) {

            showWarning("Please select a promotion.");

            return;

        }

        Promotion updatedPromotion =
                showPromotionDialog(selectedPromotion);

        if (updatedPromotion != null) {

            selectedPromotion.setTitle(
                    updatedPromotion.getTitle());

            selectedPromotion.setDiscount(
                    updatedPromotion.getDiscount());

            selectedPromotion.setStartDate(
                    updatedPromotion.getStartDate());

            selectedPromotion.setEndDate(
                    updatedPromotion.getEndDate());

            selectedPromotion.setActive(
                    updatedPromotion.isActive());

            promotionTable.refresh();

            showInfo("Promotion updated successfully.");

        }

    }
    @FXML
    private void deletePromotion() {

        Promotion selectedPromotion =
                promotionTable.getSelectionModel().getSelectedItem();

        if (selectedPromotion == null) {

            showWarning("Please select a promotion.");

            return;

        }

        Alert confirm =
                new Alert(Alert.AlertType.CONFIRMATION);

        confirm.setTitle("Delete Promotion");

        confirm.setHeaderText(null);

        confirm.setContentText(
                "Delete promotion \"" +
                        selectedPromotion.getTitle() + "\" ?"
        );

        Optional<ButtonType> result =
                confirm.showAndWait();

        if (result.isPresent()
                && result.get() == ButtonType.OK) {

            promotionService.deletePromotion(selectedPromotion);

            promotionTable.refresh();

            showInfo("Promotion deleted successfully.");

        }

    }

    private Promotion showPromotionDialog(Promotion promotionToEdit) {

        Dialog<Promotion> dialog = new Dialog<>();

        if (promotionToEdit == null) {

            dialog.setTitle("Add Promotion");

        }

        else {

            dialog.setTitle("Edit Promotion");

        }

        dialog.setHeaderText(null);

        ButtonType saveButton =
                new ButtonType("Save", ButtonBar.ButtonData.OK_DONE);

        dialog.getDialogPane().getButtonTypes().addAll(
                saveButton,
                ButtonType.CANCEL
        );

        TextField titleField = new TextField();

        titleField.setPromptText("Promotion Title");

        TextField discountField = new TextField();

        discountField.setPromptText("Discount %");

        TextField startDateField = new TextField();

        startDateField.setPromptText("Start Date");

        TextField endDateField = new TextField();

        endDateField.setPromptText("End Date");

        CheckBox activeCheckBox =
                new CheckBox("Active");

        if (promotionToEdit != null) {

            titleField.setText(
                    promotionToEdit.getTitle());

            discountField.setText(
                    String.valueOf(
                            promotionToEdit.getDiscount()));

            startDateField.setText(
                    promotionToEdit.getStartDate());

            endDateField.setText(
                    promotionToEdit.getEndDate());

            activeCheckBox.setSelected(
                    promotionToEdit.isActive());

        }

        else {

            activeCheckBox.setSelected(true);

        }

        GridPane grid = new GridPane();

        grid.setHgap(10);

        grid.setVgap(10);

        grid.add(new Label("Title"),0,0);

        grid.add(titleField,1,0);

        grid.add(new Label("Discount"),0,1);

        grid.add(discountField,1,1);

        grid.add(new Label("Start Date"),0,2);

        grid.add(startDateField,1,2);

        grid.add(new Label("End Date"),0,3);

        grid.add(endDateField,1,3);

        grid.add(new Label("Status"),0,4);

        grid.add(activeCheckBox,1,4);

        dialog.getDialogPane().setContent(grid);

        dialog.setResultConverter(button -> {

            if(button == saveButton){

                if(titleField.getText().isBlank()
                        || discountField.getText().isBlank()
                        || startDateField.getText().isBlank()
                        || endDateField.getText().isBlank()){

                    showWarning("Please complete all fields.");

                    return null;

                }

                double discount;

                try{

                    discount =
                            Double.parseDouble(discountField.getText());

                }

                catch (NumberFormatException e){

                    showWarning("Discount must be numeric.");

                    return null;

                }

                Promotion promotion = new Promotion();

                promotion.setTitle(titleField.getText());

                promotion.setDiscount(discount);

                promotion.setStartDate(startDateField.getText());

                promotion.setEndDate(endDateField.getText());

                promotion.setActive(activeCheckBox.isSelected());

                return promotion;

            }

            return null;

        });

        Optional<Promotion> result =
                dialog.showAndWait();

        return result.orElse(null);

    }

    private void showWarning(String message){

        Alert alert =
                new Alert(Alert.AlertType.WARNING);

        alert.setHeaderText(null);

        alert.setContentText(message);

        alert.showAndWait();

    }

    private void showInfo(String message){

        Alert alert =
                new Alert(Alert.AlertType.INFORMATION);

        alert.setHeaderText(null);

        alert.setContentText(message);

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