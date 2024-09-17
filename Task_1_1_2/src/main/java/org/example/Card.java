package org.example;


/*
 * Card class for blackjack game.
 */
public class Card {
    private String rank;  // Масть
    private String suit;  // Значение карты

    /*
     * Init Card.
     */
    public Card(String rank, String suit) {
        this.rank = rank;
        this.suit = suit;
    }

    /*
     * Return card rank.
     */
    public String getRank() {
        return rank;
    }

    /*
     * Return card suit.
     */
    public String getSuit() {
        return suit;
    }

    /*
     * Return value by rank.
     */
    public int getValue() {
        return switch (rank) {
            case "2", "3", "4", "5", "6", "7", "8", "9", "10" -> Integer.parseInt(rank);
            case "J", "Q", "K" -> 10;
            case "A" -> 11;
            default -> 0;
        };
    }

    @Override
    /*
     * Str class.
     */
    public String toString() {
        return rank + " of " + suit;
    }

}
