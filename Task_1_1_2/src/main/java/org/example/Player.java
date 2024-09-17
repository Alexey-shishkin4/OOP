package org.example;

import java.util.List;
import java.util.ArrayList;


/*
 * Player class for blackjack game.
 */
public class Player {
    private List<Card> hand;
    private String name;
    private boolean isDealer;

    /*
     * Init player class.
     */
    public Player(String name, boolean isDealer) {
        this.name = name;
        this.isDealer = isDealer;
        hand = new ArrayList<>();
    }

    /*
     * Take card.
     */
    public void takeCard(Card card) {
        hand.add(card);
    }

    /*
     * Count value of cards in hand.
     */
    public int getHandValue() {
        int totalValue = 0;
        int aces = 0;

        // Подсчет суммы карт в руке
        for (Card card : hand) {
            totalValue += card.getValue();
            if (card.getRank().equals("A")) {
                aces++;
            }
        }

        // Если сумма превышает 21 и есть тузы, уменьшаем их значение до 1
        while (totalValue > 21 && aces > 0) {
            totalValue -= 10;
            aces--;
        }

        return totalValue;
    }

    /*
     * Check value of cards in hand > 21
     */
    public boolean isBusted() {
        return getHandValue() > 21;
    }

    /*
     * Check blackjack.
     */
    public boolean hasBlackjack() {
        return hand.size() == 2 && getHandValue() == 21;
    }

    /*
     * Print player hand.
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
        if (!isDealer)
            System.out.println("Your hand value: " + getHandValue());
    }

    /*
     * Return player name.
     */
    public String getName() {
        return name;
    }
}
