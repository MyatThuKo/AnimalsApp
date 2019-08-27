package com.example.animalsapp.model;

import com.google.gson.annotations.SerializedName;

public class AnimalModel {
    public String name;
    public Taxonomy taxonomy;
    public String location;
    public Speed speed;
    public String diet;

    @SerializedName("lifespan")
    public String lifeSpan;

    @SerializedName("image")
    public String imageURL;

    public AnimalModel(String name) {
        this.name = name;
    }
}

class Taxonomy {
    String kingdom;
    String order;
    String family;
}

class Speed {
    String metric;
    String imperial;
}
