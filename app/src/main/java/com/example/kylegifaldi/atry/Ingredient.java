package com.example.kylegifaldi.atry;

/**
 * Created by kylegifaldi on 4/23/18.
 */

//package info.androidhive.recyclerview;


public class Ingredient {

    private String title, genre;

    public Ingredient() {
    }

    public Ingredient(String title, String genre) {
        this.title = title;
        this.genre = genre;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String name) {
        this.title = name;
    }



    public String getGenre() {
        return genre;
    }

    public void setGenre(String genre) {
        this.genre = genre;
    }

}
