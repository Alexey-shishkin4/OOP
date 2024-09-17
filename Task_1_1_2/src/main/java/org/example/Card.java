package org.example;

/**
 * The {@code Card} class represents a single card in a deck of playing cards for a blackjack game.
 * Each card has a rank (2-10, J, Q, K, A) and a suit (Hearts, Diamonds, Clubs, Spades).
 * The class also provides methods to retrieve the card's rank, suit, and point value based on the rules of blackjack.
 */
public class Card {
    private String rank;  // The rank of the card (2-10, J, Q, K, A)
    private String suit;  // The suit of the card (Hearts, Diamonds, Clubs, Spades)

    /**
     * Initializes a new {@code Card} object with the specified rank and suit.
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
     * Returns the point value of the card based on its rank according to blackjack rules.
     * <ul>
     *   <li>Ranks 2-10 return their numeric value (e.g., rank 5 returns 5).</li>
     *   <li>Face cards (J, Q, K) return 10.</li>
     *   <li>An Ace (A) returns 11 (or 1, depending on game rules, but handled elsewhere).</li>
     * </ul>
     *
     * @return the point value of the card as an {@code int}
     */
    public int getValue() {
        return switch (rank) {
            case "2", "3", "4", "5", "6", "7", "8", "9", "10" -> Integer.parseInt(rank);
            case "J", "Q", "K" -> 10;
            case "A" -> 11;
            default -> 0;
        };
    }

    /**
     * Returns a string representation of the card, including both its rank and suit.
     * For example, "A of Hearts" or "10 of Spades".
     *
     * @return a {@code String} representing the card's rank and suit
     */
    @Override
    public String toString() {
        return rank + " of " + suit;
    }
}
