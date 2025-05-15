package com.example.g21;

import java.util.ArrayList;
import java.util.List;

/**
 * Player class - represents player and dealer
 * Name Berk Enul
 * Date 15.05.2025
 */
public class Player {

    private List<Card> hand = new ArrayList<>();


    public void addCard(Card card) {
        hand.add(card);
    }


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


    public void reset() {
        hand.clear();
    }


    public List<Card> getHand() {
        return hand;
    }
}