package com.example.g21;

import javafx.scene.image.Image;

/**
 * Card class - represents cards in the game
 * Name: Berk Enul
 * Date: 15.05.2025
 */
public class Card {

    private String name;
    private int value;
    private Image image;


    public Card(String name, int value) {
        this.name = name;
        this.value = value;

        try {
            this.image = new Image(getClass().getResourceAsStream("/com/example/g21/images/" + name + ".png"));
        } catch (Exception e) {
            System.out.println("Could not load card image: " + name);
        }
    }


    public int getValue() {
        return value;
    }


    public Image getImage() {
        return image;
    }


    public String getName() {
        return name;
    }
}