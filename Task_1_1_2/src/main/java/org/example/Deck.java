package org.example;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * The {@code Deck} class represents a deck
 * of 52 playing cards used in a blackjack game.
 * It provides methods to initialize,
 * shuffle, and deal cards to players.
 */
public class Deck {
    private List<Card> cards;

    /**
     * Initializes a new deck of 52 cards. The deck consists of cards from 4 suits
     * ("Hearts", "Diamonds", "Clubs", "Spades") and 13 ranks (2-10, J, Q, K, A).
     * After initialization, the deck is automatically shuffled.
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

        shuffle();  // Shuffle the deck after creation
    }

    /**
     * Shuffles the deck of cards using the
     * {@code Collections.shuffle()} method to randomize the order.
     */
    public void shuffle() {
        Collections.shuffle(cards);
    }

    /**
     * Deals a card from the deck. Removes and returns the last card from the deck.
     * If the deck is empty, it will throw an exception (handled elsewhere).
     *
     * @return the {@code Card} object representing the card dealt from the deck
     */
    public Card dealCard() {
        return cards.remove(cards.size() - 1);  // Deals the last card
    }

    /**
     * Возвращает количество карт в колоде.
     *
     * @return количество карт в колоде
     */
    public int getCardCount() {
        return cards.size();
    }
}
