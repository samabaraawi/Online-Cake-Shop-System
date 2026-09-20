package model;

public class Review {

    private int orderID;
    private String cakeName;
    private int rating;
    private String comment;

    public Review() {
    }

    public Review(int orderID, String cakeName, int rating, String comment) {
        this.orderID = orderID;
        this.cakeName = cakeName;
        this.rating = rating;
        this.comment = comment;
    }

    public int getOrderID() {
        return orderID;
    }

    public void setOrderID(int orderID) {
        this.orderID = orderID;
    }

    public String getCakeName() {
        return cakeName;
    }

    public void setCakeName(String cakeName) {
        this.cakeName = cakeName;
    }

    public int getRating() {
        return rating;
    }

    public void setRating(int rating) {
        this.rating = rating;
    }

    public String getComment() {
        return comment;
    }

    public void setComment(String comment) {
        this.comment = comment;
    }
}