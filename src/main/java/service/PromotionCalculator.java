package service;

import model.Promotion;

public class PromotionCalculator {

    public static double calculatePrice(double originalPrice) {

        Promotion promotion = getActivePromotion();

        if (promotion != null) {

            return originalPrice -
                    (originalPrice * promotion.getDiscount() / 100);

        }

        return originalPrice;

    }

    public static Promotion getActivePromotion() {

        PromotionService promotionService =
                PromotionService.getInstance();

        for (Promotion promotion :
                promotionService.getAllPromotions()) {

            if (promotion.isActive()) {

                return promotion;

            }

        }

        return null;

    }

}