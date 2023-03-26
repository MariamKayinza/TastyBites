package com.example.tastybites;

public class Order {
    private String foodName;
    private String restaurantName;
    private String totalPrice;
    private String status;

    public Order(String foodName, String restaurantName, String totalPrice, String status) {
        this.foodName = foodName;
        this.restaurantName = restaurantName;
        this.totalPrice = totalPrice;
        this.status = status;
    }

    public String getFoodName() {
        return foodName;
    }

    public void setFoodName(String foodName) {
        this.foodName = foodName;
    }

    public String getRestaurantName() {
        return restaurantName;
    }

    public void setRestaurantName(String restaurantName) {
        this.restaurantName = restaurantName;
    }

    public String getTotalPrice() {
        return totalPrice;
    }

    public void setTotalPrice(String totalPrice) {
        this.totalPrice = totalPrice;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }
}
