package org.example;

import java.util.ArrayList;
import java.util.List;

/**
 * The {@code Player} class represents a player in a blackjack game.
 * A player can be either the actual player
 * or the dealer, and this class manages the player's hand,
 * tracks the hand value, and provides
 * methods for checking the status of the hand (e.g., busted or blackjack).
 */
public class Player {
    private List<Card> hand;
    private String name;
    private boolean isDealer;

    /**
     * Initializes a new {@code Player} with a name and
     * a boolean indicating if the player is the dealer.
     *
     * @param name     the name of the player
     * @param isDealer {@code true} if the player is the dealer,
     * {@code false} if the player is a regular player
     */
    public Player(String name, boolean isDealer) {
        this.name = name;
        this.isDealer = isDealer;
        hand = new ArrayList<>();
    }

    /**
     * Adds a card to the player's hand.
     *
     * @param card the {@code Card} object to be added to the player's hand
     */
    public void takeCard(Card card) {
        hand.add(card);
    }

    /**
     * Calculates and returns the total value of the player's hand.
     *
     * @return the total value of the player's hand
     */
    public int getHandValue() {
        int totalValue = 0;
        int aces = 0;

        // Calculate total hand value and count aces
        for (Card card : hand) {
            totalValue += card.getValue();
            if (card.getRank().equals("A")) {
                aces++;
            }
        }

        // Adjust aces value if the total exceeds 21
        while (totalValue > 21 && aces > 0) {
            totalValue -= 10;
            aces--;
        }

        return totalValue;
    }

    /**
     * Checks if the player's hand value exceeds 21, indicating a bust.
     *
     * @return {@code true} if the hand value exceeds 21, otherwise {@code false}
     */
    public boolean isBusted() {
        return getHandValue() > 21;
    }

    /**
     * Checks if the player has a blackjack, which occurs
     * when the player has exactly two cards
     * and the hand value equals 21.
     *
     * @return {@code true} if the player has a blackjack, otherwise {@code false}
     */
    public boolean hasBlackjack() {
        return hand.size() == 2 && getHandValue() == 21;
    }

    /**
     * Displays the player's hand.
     *
     * @param showAll {@code true} if all cards should be shown.
     */
    public void showHand(boolean showAll) {
        System.out.println(name + "'s cards:");
        for (int i = 0; i < hand.size(); i++) {
            if (!showAll && i == 1 && isDealer) {
                System.out.println("[Hidden]");
            } else {
                System.out.println(hand.get(i));
            }
        }
        if (!isDealer) {
            System.out.println("Your hand value: " + getHandValue());
        }
    }

    /**
     * Returns the name of the player.
     *
     * @return the player's name as a {@code String}
     */
    public String getName() {
        return name;
    }
}
