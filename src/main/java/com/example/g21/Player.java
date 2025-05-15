package com.example.g21;

import java.util.ArrayList;
import java.util.List;

/**
 * Player class - represents player and dealer
 *
 * @author Berk Enul
 * @date 15.05.2025
 */
public class Player {

    private List<Card> hand = new ArrayList<>();

    /**
     * Adds a card to the player's hand
     *
     * @param card the card to add
     */
    public void addCard(Card card) {
        hand.add(card);
    }

    /**
     * Calculates total value of hand
     *
     * @return total value
     */
    public int getTotal() {
        int total = 0;
        int aceCount = 0;

        for (Card card : hand) {
            if (card.getValue() == 1) {
                aceCount++;
            }
            total += card.getValue();
        }

        while (aceCount > 0 && total + 10 <= 21) {
            total += 10;
            aceCount--;
        }

        return total;
    }

    /**
     * Clears player's hand
     */
    public void reset() {
        hand.clear();
    }

    /**
     * Gets all cards in hand
     *
     * @return the list of cards
     */
    public List<Card> getHand() {
        return hand;
    }
}