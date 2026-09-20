package util;

import model.Cake;

public class Session {

    private static Cake selectedCake;

    public static Cake getSelectedCake() {
        return selectedCake;
    }

    public static void setSelectedCake(Cake cake) {
        selectedCake = cake;
    }

}