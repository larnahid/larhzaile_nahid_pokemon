package com.example.pokemonapp;

import java.io.Serializable;

public class Pokemon implements Serializable {
    private String name;
    private String details;
    private String image;

    public Pokemon(String name, String details, String image) {
        this.name = name;
        this.details = details;
        this.image = image;
    }

    public String getName() {
        return name;
    }

    public String getDetails() {
        return details;
    }

    public String getImage() {
        return image;
    }
}
