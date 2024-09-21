package org.example;

import java.util.List;

import java.util.Scanner;

/**
 * The {@code Game} class implements the core logic of a blackjack game.
 */
public class Game {
    private Deck deck;
    private Player player;
    private Player dealer;

    /**
     * Initializes a new {@code Game} object by creating a new deck and two players:
     * one for the player and one for the dealer.
     */
    public Game() {
        deck = new Deck();
        player = new Player("Player", false);
        dealer = new Player("Dealer", true);
    }

    /**
     * Starts the game loop, which includes dealing cards,
     * handling turns for the player and the dealer,
     * determining the winner, and asking the player
     * if they wish to continue playing or end the game.
     * Scores are tracked for both the player
     * and the dealer over multiple rounds.
     */
    public void play() {
        int[] scores = {0, 0};  // Scores for player and dealer
        boolean repeat = true;

        while (repeat) {
            resetHands();

            // Initial card dealing
            player.takeCard(deck.dealCard());
            player.takeCard(deck.dealCard());
            dealer.takeCard(deck.dealCard());
            dealer.takeCard(deck.dealCard());

            System.out.println("Welcome to blackjack");
            showPlayerHand(player, true);
            showPlayerHand(dealer, false);

            if (player.hasBlackjack()) {
                System.out.println(player.getName() + " has Blackjack! You win!");
                scores[0]++;
                System.out.println("Current Score -> Player: "
                        + scores[0] + " | Dealer: " + scores[1]);
                repeat = gameContinue();
                continue;
            }

            // Player's turn
            while (playerTurn()) {
                if (player.isBusted()) {
                    System.out.println(player.getName() + " busted! You lose.");
                    scores[1]++;
                    System.out.println("Current Score -> Player: "
                            + scores[0] + " | Dealer: " + scores[1]);
                    repeat = gameContinue();
                    break;
                }
            }

            if (!player.isBusted()) {
                dealerTurn();
            }

            // Determine the round winner
            determineWinner(scores);

            repeat = gameContinue();
        }

        System.out.println("Final Score -> Player: "
                + scores[0] + " | Dealer: " + scores[1]);
        System.out.println("Thank you for playing!");
    }

    /**
     * Resets the hands of both the player and the dealer for the next round.
     */
    private void resetHands() {
        player = new Player("Player", false);
        dealer = new Player("Dealer", true);
    }

    /**
     * Prompts the player to decide whether to continue playing or finish the game.
     *
     * @return {@code true} if the player wants to continue, {@code false} to end the game
     */
    private boolean gameContinue() {
        Scanner scanner = new Scanner(System.in);
        System.out.print("Do you want to continue playing or finish? (c/e): ");
        String action = scanner.nextLine();

        if (action.equalsIgnoreCase("c")) {
            return true;
        } else if (action.equalsIgnoreCase("e")) {
            return false;
        } else {
            System.out.println("Invalid input, please type 'c' or 'e'.");
            return gameContinue();
        }
    }

    /**
     * Handles the player's turn. The player is asked to choose
     * whether to "hit" (take a card) or "stand" (end their turn).
     *
     * @return {@code true} if the player chooses to hit, {@code false} to end their turn
     */
    private boolean playerTurn() {
        Scanner scanner = new Scanner(System.in);
        System.out.print("Do you want to hit or stand? (h/s): ");
        String action = scanner.nextLine();

        if (action.equalsIgnoreCase("h")) {
            player.takeCard(deck.dealCard());
            showPlayerHand(player, true);
            return true;
        } else if (action.equalsIgnoreCase("s")) {
            return false;
        } else {
            System.out.println("Invalid input, please type 'h' or 's'.");
            return playerTurn();
        }
    }

    /**
     * Handles the dealer's turn. The dealer keeps
     * drawing cards until their hand value is at least 17.
     * If the dealer's hand exceeds 21, they bust.
     */
    private void dealerTurn() {
        System.out.println("\nDealer's turn.");
        showPlayerHand(dealer, true);

        while (dealer.getHandValue() < 17) {
            System.out.println("Dealer hits.");
            dealer.takeCard(deck.dealCard());
            showPlayerHand(dealer, true);
        }

        if (dealer.isBusted()) {
            System.out.println("Dealer busted! You win.");
        } else {
            System.out.println("Dealer stands.");
        }
    }

    /**
     * Compares the player's and dealer's hands to
     * determine the winner of the round. Updates the scores accordingly.
     *
     * @param scores an array storing the current scores for both the player and dealer
     */
    private void determineWinner(int[] scores) {
        final int playerScore = player.getHandValue();
        final int dealerScore = dealer.getHandValue();

        System.out.println("\nFinal hands:");
        showPlayerHand(player, true);
        showPlayerHand(dealer, true);
        System.out.println(player.getName() + " score: " + playerScore);
        System.out.println(dealer.getName() + " score: " + dealerScore);

        if (player.isBusted()) {
            System.out.println("You lose.");
            scores[1]++;
        } else if (dealer.isBusted()) {
            System.out.println("You win!");
            scores[0]++;
        } else if (playerScore > dealerScore) {
            System.out.println("You win!");
            scores[0]++;
        } else if (playerScore < dealerScore) {
            System.out.println("Dealer wins.");
            scores[1]++;
        } else {
            System.out.println("It's a tie!");
        }

        System.out.println("Current Score -> Player: "
                + scores[0] + " | Dealer: " + scores[1]);
    }

    /**
     * The main method to run the blackjack game.
     *
     * @param args the command-line arguments (not used)
     */
    public static void main(String[] args) {
        Game game = new Game();
        game.play();
    }

    public void showPlayerHand(Player player, boolean showAll) {
        System.out.println(player.getName() + "'s cards:");
        List<Card> hand = player.getHand();

        for (int i = 0; i < hand.size(); i++) {
            if (!showAll && i == 1 && player.isDealer()) {
                System.out.println("[Hidden]");
            } else {
                System.out.println(hand.get(i));
            }
        }

        if (!player.isDealer()) {
            System.out.println("Your hand value: " + player.getHandValue());
        }
    }
}
