package controller;

import javafx.fxml.FXML;
import javafx.scene.control.*;
import model.Order;
import model.Review;
import service.OrderService;
import service.ReviewService;
import util.SceneManager;

public class ReviewController {

    @FXML
    private Label orderLabel;

    @FXML
    private Label cakeLabel;

    @FXML
    private ComboBox<Integer> ratingBox;

    @FXML
    private TextArea commentArea;

    private final OrderService orderService =
            OrderService.getInstance();

    private final ReviewService reviewService =
            ReviewService.getInstance();

    @FXML
    public void initialize() {

        Order order = orderService.getCurrentOrder();

        if (order == null)
            return;

        orderLabel.setText("Order ID : " + order.getOrderID());

        cakeLabel.setText("Cake : " + order.getCake().getName());

        ratingBox.getItems().addAll(1,2,3,4,5);

        ratingBox.setValue(5);

    }

    @FXML
    private void submitReview() {

        if (ratingBox.getValue() == null) {

            Alert alert =
                    new Alert(Alert.AlertType.WARNING);

            alert.setHeaderText(null);

            alert.setContentText("Please select rating.");

            alert.showAndWait();

            return;
        }

        Order order = orderService.getCurrentOrder();

        Review review = new Review();

        review.setOrderID(order.getOrderID());

        review.setCakeName(order.getCake().getName());

        review.setRating(ratingBox.getValue());

        review.setComment(commentArea.getText());

        reviewService.submitReview(review);

        Alert alert =
                new Alert(Alert.AlertType.INFORMATION);

        alert.setHeaderText(null);

        alert.setContentText("Review submitted successfully!");

        alert.showAndWait();

        SceneManager.goHome();

    }

}