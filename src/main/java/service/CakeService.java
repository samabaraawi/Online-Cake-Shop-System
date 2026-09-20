package service;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import model.Cake;

public class CakeService {

    private static final CakeService instance = new CakeService();

    private final ObservableList<Cake> cakes =
            FXCollections.observableArrayList();

    private CakeService() {

        cakes.add(new Cake(

                1,

                "Chocolate Cake",

                "Rich chocolate sponge with cream",

                "Chocolate",

                "Medium",

                20.0,

                "Birthday",

                true,

                "/images/chocolate.jpg"
        ));

        cakes.add(new Cake(

                2,

                "Vanilla Cake",

                "Vanilla cake with strawberry filling",

                "Vanilla",

                "Small",

                18.5,

                "Birthday",

                true,

                "/images/vanilla.jpg"
        ));

        cakes.add(new Cake(

                3,

                "Red Velvet",

                "Cream cheese frosting",

                "Red Velvet",

                "Large",

                25.0,

                "Wedding",

                false,

                "/images/redvelvet.jpg"
        ));

    }

    public static CakeService getInstance() {

        return instance;

    }

    public ObservableList<Cake> getAvailableCakes() {

        ObservableList<Cake> available =
                FXCollections.observableArrayList();

        for (Cake cake : cakes) {

            if (cake.isAvailable()) {

                available.add(cake);

            }

        }

        return available;

    }

    public ObservableList<Cake> getAllCakes() {

        return cakes;

    }

    public void addCake(Cake cake) {

        cakes.add(cake);

    }

    public void deleteCake(Cake cake) {

        cakes.remove(cake);

    }

    public int getNextCakeID() {

        int max = 0;

        for (Cake cake : cakes) {

            if (cake.getCakeID() > max) {

                max = cake.getCakeID();

            }

        }

        return max + 1;

    }

}