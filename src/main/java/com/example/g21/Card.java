package com.example.g21;

import javafx.scene.image.Image;

/**
 * Card class - represents cards in the game
 *
 * @author Berk Enul
 * @date 15.05.2025
 */
public class Card {

    private String name;
    private int value;
    private Image image;

    /**
     * Constructor for Card
     *
     * @param name name of the card
     * @param value value of the card
     */
    public Card(String name, int value) {
        this.name = name;
        this.value = value;

        try {
            this.image = new Image(getClass().getResourceAsStream("/com/example/g21/images/" + name + ".png"));
        } catch (Exception e) {
            System.out.println("Could not load card image: " + name);
        }
    }

    /**
     * Gets card value
     *
     * @return the value
     */
    public int getValue() {
        return value;
    }

    /**
     * Gets card image
     *
     * @return the image
     */
    public Image getImage() {
        return image;
    }

    /**
     * Gets card name
     *
     * @return the name
     */
    public String getName() {
        return name;
    }
}