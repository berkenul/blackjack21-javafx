package com.example.g21;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * Class that manages game logic
 * Name: Berk Enul
 * Date: 15.05.2025
 */
public class GameLogic {

    private List<Card> deck = new ArrayList<>();
    private int index = 0;


    public GameLogic() {
        createDeck();
        shuffle();
    }


    private void createDeck() {

        String[] suits = { "C", "D", "H", "S" };
        String[] ranks = { "A", "2", "3", "4", "5", "6", "7", "8", "9", "10", "J", "Q", "K" };


        for (String suit : suits) {
            for (String rank : ranks) {
                int value;

                if (rank.equals("A")) {
                    value = 1;
                } else if (rank.equals("J") || rank.equals("Q") || rank.equals("K")) {
                    value = 10;
                } else {
                    value = Integer.parseInt(rank);
                }


                deck.add(new Card(rank + suit, value));
            }
        }
    }


    public void shuffle() {
        Collections.shuffle(deck);
        index = 0;
    }


    public Card drawCard() {

        if (index >= deck.size()) {
            shuffle();
        }
        return deck.get(index++);
    }
}