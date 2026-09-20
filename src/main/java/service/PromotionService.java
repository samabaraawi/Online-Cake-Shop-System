package service;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import model.Promotion;

public class PromotionService {

    private static final PromotionService instance =
            new PromotionService();

    private final ObservableList<Promotion> promotions =
            FXCollections.observableArrayList();

    private PromotionService() {

    }

    public static PromotionService getInstance() {

        return instance;

    }

    public ObservableList<Promotion> getAllPromotions() {

        return promotions;

    }

    public void addPromotion(Promotion promotion) {

        promotions.add(promotion);

    }

    public void deletePromotion(Promotion promotion) {

        promotions.remove(promotion);

    }

    public int getNextPromotionID() {

        int max = 0;

        for (Promotion promotion : promotions) {

            if (promotion.getPromotionID() > max) {

                max = promotion.getPromotionID();

            }

        }

        return max + 1;

    }

}