package service;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import model.Review;

public class ReviewService {

    private static ReviewService instance;

    private final ObservableList<Review> reviews =
            FXCollections.observableArrayList();

    private ReviewService() {
    }

    public static ReviewService getInstance() {

        if (instance == null) {
            instance = new ReviewService();
        }

        return instance;
    }

    public void submitReview(Review review) {

        reviews.add(review);

    }

    public ObservableList<Review> getAllReviews() {

        return reviews;

    }

}