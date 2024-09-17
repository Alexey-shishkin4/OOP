package org.example;

public class Card {
    private String rank;  // Масть
    private String suit;  // Значение карты

    public Card(String rank, String suit) {
        this.rank = rank;
        this.suit = suit;
    }

    public String getRank() {
        return rank;
    }

    public String getSuit() {
        return suit;
    }

    public int getValue() {
        return switch (rank) {
            case "2", "3", "4", "5", "6", "7", "8", "9", "10" -> Integer.parseInt(rank);
            case "J", "Q", "K" -> 10;
            case "A" -> 11;
            default -> 0;
        };
    }

    @Override
    public String toString() {
        return rank + " of " + suit;
    }

}
