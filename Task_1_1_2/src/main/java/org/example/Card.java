package org.example;

/**
 * The {@code Card} class represents a single card in a
 * deck of playing cards for a blackjack game.
 * Each card has a rank (2-10, J, Q, K, A)
 * and a suit (Hearts, Diamonds, Clubs, Spades).
 * The class also provides methods to
 * retrieve the card's rank, suit, and point value based on the rules of blackjack.
 */
public class Card {
    private String rank;  // The rank of the card (2-10, J, Q, K, A)
    private String suit;  // The suit of the card (Hearts, Diamonds, Clubs, Spades)
    private boolean closed; // hidden or visible

    /**
     * Initializes a new {@code Card} object
     * with the specified rank and suit.
     *
     * @param rank the rank of the card (2-10, J, Q, K, A)
     * @param suit the suit of the card (Hearts, Diamonds, Clubs, Spades)
     */
    public Card(String rank, String suit) {
        this.rank = rank;
        this.suit = suit;
    }

    /**
     * Returns the rank of the card.
     *
     * @return the rank of the card as a {@code String}
     */
    public String getRank() {
        return rank;
    }

    /**
     * Returns the suit of the card.
     *
     * @return the suit of the card as a {@code String}
     */
    public String getSuit() {
        return suit;
    }

    /**
     * Returns the point value of the card
     * based on its rank according to blackjack rules.
     *
     * @return the point value of the card as an {@code int}
     */
    public int getValue() {
        switch (rank) {
            case "2": case "3": case "4": case "5": case "6":
            case "7": case "8": case "9": case "10":
                return Integer.parseInt(rank);
            case "J": case "Q": case "K":
                return 10;
            case "A":
                return 11;
            default:
                return 0;
        }
    }

    /**
     * Returns a string representation of the card,
     * including both its rank and suit.
     * For example, "A of Hearts" or "10 of Spades".
     *
     * @return a {@code String} representing the card's rank and suit
     */
    @Override
    public String toString() {
        if (closed) {
            return "[Hidden]";
        }
        return rank + " of " + suit;
    }
}
