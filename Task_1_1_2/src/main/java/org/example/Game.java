package org.example;

import java.util.List;
import java.util.Scanner;

/**
 * The {@code Game} class implements the core logic of a blackjack game.
 */
public class Game {
    private final Deck deck;
    private Player player;
    private Player dealer;
    private ScoreBoard scoreBoard;


    /**
     * Enum for game results.
     *
     */
    public enum GameResult {
        PLAYER_BUSTED,
        DEALER_BUSTED,
        PLAYER_WIN,
        DEALER_WIN,
        TIE
    }

    /**
     * Initializes a new {@code Game} object by creating a new deck and two players:
     * one for the player and one for the dealer.
     */
    public Game() {
        deck = new Deck();
        player = new Player("Player", false);
        dealer = new Player("Dealer", true);
        scoreBoard = new ScoreBoard();
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
        boolean repeat = true;

        while (repeat) {
            resetHands();

            player.takeCard(deck.dealCard());
            player.takeCard(deck.dealCard());
            dealer.takeCard(deck.dealCard());
            dealer.takeCard(deck.dealCard());

            System.out.println("Welcome to blackjack");
            showPlayerHand(player, true);
            showPlayerHand(dealer, false);

            if (player.hasBlackjack()) {
                System.out.println(player.getName() + " has Blackjack! You win!");
                scoreBoard.incrementPlayerScore();  // Increment player's score
                System.out.println("Current Score -> " + scoreBoard);
                repeat = gameContinue();
                continue;
            }

            // Player's turn
            while (playerTurn()) {
                if (player.isBusted()) {
                    System.out.println(player.getName() + " busted! You lose.");
                    scoreBoard.incrementDealerScore();  // Increment dealer's score
                    System.out.println("Current Score -> " + scoreBoard);
                    repeat = gameContinue();
                    break;
                }
            }

            if (!player.isBusted()) {
                dealerTurn();
            } else {
                continue;
            }

            GameResult result = determineWinnerResult();
            handleWinnerResult(result);

            repeat = gameContinue();
        }

        System.out.println("Final Score -> " + scoreBoard);
        System.out.println("Thank you for playing!");
    }

    /**
     * Resets the hands of both the player and the dealer for the next round.
     */
    public void resetHands() {
        player.clearHand();
        dealer.clearHand();
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
    public void dealerTurn() {
        System.out.println("\nDealer's turn.");
        showPlayerHand(dealer, true);
        System.out.println("\tDealer hand value: " + dealer.getHandValue());

        while (dealer.getHandValue() < 17) {
            System.out.println("\tDealer hits.");
            dealer.takeCard(deck.dealCard());
            showPlayerHand(dealer, true);
            System.out.println("\tDealer hand value: " + dealer.getHandValue());
        }

        if (dealer.isBusted()) {
            System.out.println("\tDealer busted! You win.");
        } else {
            System.out.println("\tDealer stands.");
        }
        System.out.println("\tFinal Dealer hand value: " + dealer.getHandValue());
    }

    /**
     * Compares the player's and dealer's hands to determine the winner of the round.
     *
     * @return A string indicating the result of the round.
     */
    public GameResult determineWinnerResult() {
        final int playerScore = player.getHandValue();
        final int dealerScore = dealer.getHandValue();

        if (player.isBusted()) {
            return GameResult.PLAYER_BUSTED;
        } else if (dealer.isBusted()) {
            return GameResult.DEALER_BUSTED;
        } else if (playerScore > dealerScore) {
            return GameResult.PLAYER_WIN;
        } else if (playerScore < dealerScore) {
            return GameResult.DEALER_WIN;
        } else {
            return GameResult.TIE;
        }
    }


    /**
     * Handles the winner result based on the string result returned from determineWinnerResult.
     * Updates scores and prints the result.
     *
     * @param result the result of the round ("PLAYER_WIN", "DEALER_WIN", "TIE", etc.)
     */
    public void handleWinnerResult(GameResult result) {
        switch (result) {
            case PLAYER_BUSTED:
                System.out.println("You lose. You busted.");
                scoreBoard.incrementDealerScore();  // Dealer wins
                break;
            case DEALER_BUSTED:
                System.out.println("Dealer busted! You win.");
                scoreBoard.incrementPlayerScore();  // Player wins
                break;
            case PLAYER_WIN:
                System.out.println("You win!");
                scoreBoard.incrementPlayerScore();
                break;
            case DEALER_WIN:
                System.out.println("Dealer wins.");
                scoreBoard.incrementDealerScore();
                break;
            case TIE:
                System.out.println("It's a tie!");
                break;
        }
        System.out.println("Current Score -> " + scoreBoard);  // Use ScoreBoard's toString()
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

    /**
     * Shows player hand.
     *
     * @param player the player class
     * @param showAll boolean
     */
    public void showPlayerHand(Player player, boolean showAll) {
        StringBuilder handString = new StringBuilder(player.getName() + "'s cards: [");
        List<Card> hand = player.getHand();

        for (int i = 0; i < hand.size(); i++) {
            if (!showAll && i == 1 && player.isDealer()) {
                hand.get(i).setClosed(true); // Mark the card as hidden
            } else {
                hand.get(i).setClosed(false);
            }
            handString.append(hand.get(i).toString());

            if (i != hand.size() - 1) {
                handString.append(", ");
            }
        }

        handString.append("]");

        System.out.println(handString);

        if (!player.isDealer()) {
            System.out.println("\tYour hand value: " + player.getHandValue());
        }
    }

    /**
     * Returns player.
     *
     * @return player.
     */
    public Player getPlayer() {
        return player;
    }

    /**
     * Returns dealer.
     *
     * @return dealer.
     */
    public Player getDealer() {
        return dealer;
    }

    /**
     * Returns scoreboard for tests.
     *
     * @return scoreBoard.
     */
    public ScoreBoard getScoreBoard() {
        return scoreBoard;
    }

}
