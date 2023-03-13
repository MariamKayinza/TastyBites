package com.example.tastybites;

import org.json.JSONArray;

import java.util.ArrayList;
import java.util.List;

public class MyRepo {
    private String id;
    private String name;
    private String address;
    private int rating;
    private List foodType;
    private ArrayList  foods;
    private String image;

    private String data;



    public MyRepo(String id , String name , int rating, String address, String image,  JSONArray array_food) {
        this.id = id;
        this.name = name;
        this.address = address;
        this.rating = rating;
        this.image = image;
        this.data = array_food.toString();


    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name){
        this.name = name;
    }
    public int getRating() {
        return rating;
    }

    public void setRating(int rating) {
        this.rating = rating;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address){
        this.address = address;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public void setData(String data) {
        this.data = data;
    }

    public String getData() {
        return data;
    }

//    public ArrayList getFoods() {
//        return getFoods();
//    }
//
//    public void setFoods(ArrayList foods) {
//        this.foods = foods;
//    }
//
//    public List getFoodType() {
//        return getFoods();
//    }
//
//    public void setFoodType(List foods) {
//        this.foodType = foodType;
//    }
    @Override
    public String toString() {
        return "Repo {" +
                "id=" + id +
                ",address='" + address + '\''+
                ",name='" + name + '\'' +
//                ",foods='" + foods + '\''+
//                ",foodType='" + foodType + '\'' +

                ",rating='" + rating + '\'' +

                '}';
    }
}
