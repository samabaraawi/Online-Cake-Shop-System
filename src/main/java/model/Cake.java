package model;

public class Cake {

    private int cakeID;
    private String name;
    private String description;
    private String flavor;

    private String size;
    private double price;
    private String category;
    private boolean available;
    private String imagePath;


    public Cake() {
    }

    public Cake(int cakeID,
                String name,
                String description,
                String flavor,
                String size,
                double price,
                String category,
                boolean available,
                String imagePath){

        this.cakeID = cakeID;
        this.name = name;
        this.description = description;
        this.flavor = flavor;
        this.size = size;
        this.price = price;
        this.category = category;
        this.available = available;
        this.imagePath = imagePath;
    }

    public int getCakeID() {
        return cakeID;
    }

    public void setCakeID(int cakeID) {
        this.cakeID = cakeID;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getFlavor() {
        return flavor;
    }

    public void setFlavor(String flavor) {
        this.flavor = flavor;
    }

    public String getSize() {
        return size;
    }

    public void setSize(String size) {
        this.size = size;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public boolean isAvailable() {
        return available;
    }

    public void setAvailable(boolean available) {
        this.available = available;
    }

    public String getImagePath() {
        return imagePath;
    }

    public void setImagePath(String imagePath) {
        this.imagePath = imagePath;
    }
}