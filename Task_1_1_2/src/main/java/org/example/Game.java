package org.example;
import java.util.Scanner;


/*
 * Class for implementing the game blackjack.
 */
public class Game {
    private Deck deck;
    private Player player;
    private Player dealer;

    /*
     * Create a new desk and players.
     */
    public Game() {
        deck = new Deck();
        player = new Player("Player", false);
        dealer = new Player("Dealer", true);
    }

    /*
     * Start a game.
     */
    public void play() {
        int[] scores = {0, 0};
        boolean repeat = true;

        while (repeat) {
            resetHands();

            // Начальная раздача
            player.takeCard(deck.dealCard());
            player.takeCard(deck.dealCard());
            dealer.takeCard(deck.dealCard());
            dealer.takeCard(deck.dealCard());

            System.out.println("Welcome to blackjack");
            player.showHand(true);
            dealer.showHand(false);

            if (player.hasBlackjack()) {
                System.out.println(player.getName() + " has Blackjack! You win!");
                scores[0]++;
                System.out.println("Current Score -> Player: " + scores[0] + " | Dealer: " + scores[1]);
                repeat = gameContinue();
                continue;
            }

            while (playerTurn()) {
                if (player.isBusted()) {
                    System.out.println(player.getName() + " busted! You lose.");
                    scores[1]++;
                    System.out.println("Current Score -> Player: " + scores[0] + " | Dealer: " + scores[1]);
                    repeat = gameContinue();
                    break;
                }
            }

            if (!player.isBusted()) {
                dealerTurn();
            }

            determineWinner(scores);

            repeat = gameContinue();
        }

        System.out.println("Final Score -> Player: " + scores[0] + " | Dealer: " + scores[1]);
        System.out.println("Thank you for playing!");
    }

    /*
     * Reset hands for new round.
     */
    private void resetHands() {
        player = new Player("Player", false);
        dealer = new Player("Dealer", true);
    }

    /*
     * Ask player to continue game.
     */
    private boolean gameContinue() {
        Scanner scanner = new Scanner(System.in);
        System.out.print("Do you want to continue playing or finish? (c/e):");
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

    /*
     * Check player turn.
     */
    private boolean playerTurn() {
        Scanner scanner = new Scanner(System.in);
        System.out.print("Do you want to hit or stand? (h/s): ");
        String action = scanner.nextLine();

        if (action.equalsIgnoreCase("h")) {
            player.takeCard(deck.dealCard());
            player.showHand(true);
            return true;
        } else if (action.equalsIgnoreCase("s")) {
            return false;
        } else {
            System.out.println("Invalid input, please type 'h' or 's'.");
            return playerTurn();
        }
    }

    /*
     * Dealer turn.
     */
    private void dealerTurn() {
        System.out.println("\nDealer's turn.");
        dealer.showHand(true);

        while (dealer.getHandValue() < 17) {
            System.out.println("Dealer hits.");
            dealer.takeCard(deck.dealCard());
            dealer.showHand(true);
        }

        if (dealer.isBusted()) {
            System.out.println("Dealer busted! You win.");
        } else {
            System.out.println("Dealer stands.");
        }
    }

    /*
     * Determine Winner in round and edit scores.
     */
    private void determineWinner(int[] scores) {
        int playerScore = player.getHandValue();
        int dealerScore = dealer.getHandValue();

        System.out.println("\nFinal hands:");
        player.showHand(true);
        dealer.showHand(true);
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

        System.out.println("Current Score -> Player: " + scores[0] + " | Dealer: " + scores[1]);
    }

    /*
     * main method.
     */
    public static void main(String[] args) {
        Game game = new Game();
        game.play();
    }
}