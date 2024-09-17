package org.example;
import java.util.List;
import java.util.ArrayList;
import java.util.Collections;


/*
 * Deck class for blackjack game.
 */
public class Deck {
    private List<Card> cards;

    /*
     * init Deck.
     */
    public Deck() {
        cards = new ArrayList<>();
        String[] suits = {"Hearts", "Diamonds", "Clubs", "Spades"};
        String[] ranks = {"2", "3", "4", "5", "6", "7", "8", "9", "10", "J", "Q", "K", "A"};

        for (String suit : suits) {
            for (String rank : ranks) {
                cards.add(new Card(rank, suit));
            }
        }

        shuffle();
    }

    /*
     * Shuffle cards.
     */
    public void shuffle() {
        Collections.shuffle(cards);
    }

    /*
     * Deal card.
     */
    public Card dealCard() {
        return cards.removeLast();
    }
}
