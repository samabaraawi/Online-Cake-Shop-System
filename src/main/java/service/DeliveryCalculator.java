package service;

public class DeliveryCalculator {

    public static double calculateDeliveryFee(String address) {

        if (address == null || address.isBlank()) {
            return 0;
        }

        String lowerAddress = address.toLowerCase();

        if (lowerAddress.contains("ramallah")) {
            return 3.0;
        }

        if (lowerAddress.contains("nablus")) {
            return 5.0;
        }

        if (lowerAddress.contains("al-bireh")
                || lowerAddress.contains("albireh")) {
            return 2.0;
        }

        return 4.0;
    }
}