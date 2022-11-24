package com.example.foodfinder;

import java.io.Serializable;

public class Ingredient implements Serializable {
    private String name;
    private String image;

    public Ingredient(String name){
        this.name=name;
    }
    public Ingredient( ){

    }

    public String getName(){
        return name;
    }
    public void setName(String name){
        this.name=name;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }
}
