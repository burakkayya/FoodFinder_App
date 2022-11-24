package com.example.foodfinder;

import android.net.Uri;
import android.widget.ImageView;

public class Food {
    private String image;
    private String name;
    private String ingredients;
    private String description;
    private String time;
    private String id;
    private String favStatus;

    public Food(String img,String name,String ingredients,String description,String time,String favStatus,String id){
        this.image=img;
        this.name=name;
        this.ingredients=ingredients;
        this.description=description;
        this.time=time;
        this.favStatus=favStatus;
        this.id=id;

    }
    public Food( ){
        this.favStatus="0";
    }
    public String getImage(){
        return image;
    }
    public void setImage(String image){
        this.image=image;
    }

    public String getName(){
        return name;
    }
    public void setName(String name){
        this.name=name;
    }

    public String getIngredients(){
        return ingredients;
    }
    public void setIngredients(String ingredients){ this.ingredients=ingredients; }

    public String getDescription(){
        return description;
    }
    public void setDescription(String description){ this.description=description; }

    public String getTime(){
        return time;
    }
    public void setTime(String time){ this.time=time; }

    public String getFavStatus() { return favStatus; }
    public void setFavStatus(String favStatus) { this.favStatus = favStatus; }

    public String getId() { return id; }
    public void setId(String id) { this.id = id; }
}
