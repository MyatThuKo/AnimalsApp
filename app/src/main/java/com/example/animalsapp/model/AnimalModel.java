package com.example.animalsapp.model;

public class AnimalModel {
    public String name;
    public Taxonomy taxonomy;
    public String location;
    public Speed speed;
    public String diet;
    public String lifespan;
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
    Speed metric;
    String imperial;
}
