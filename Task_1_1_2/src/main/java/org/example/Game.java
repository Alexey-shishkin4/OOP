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
     * Enum for game results
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

            // Check for initial blackjack
            if (player.hasBlackjack()) {
                System.out.println(player.getName() + " has Blackjack! You win!");
                scores[0]++;
                System.out.println("Current Score -> Player: "
                        + scores[0] + " | Dealer: " + scores[1]);
                repeat = gameContinue();
                continue;  // Move to next round
            }

            // Player's turn
            while (playerTurn()) {
                if (player.isBusted()) {
                    System.out.println(player.getName() + " busted! You lose.");
                    scores[1]++;
                    System.out.println("Current Score -> Player: "
                            + scores[0] + " | Dealer: " + scores[1]);
                    repeat = gameContinue();
                    continue;
                }
            }

            // If the player is not busted, play dealer's turn
            if (!player.isBusted()) {
                dealerTurn();
            }

            // Determine the round winner
            GameResult result = determineWinnerResult();
            handleWinnerResult(result, scores);

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
        player.clearHand();  // Clear the player's hand
        dealer.clearHand();  // Clear the dealer's hand
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
        System.out.println("Dealer hand value: " + dealer.getHandValue());
    }

    /**
     * Compares the player's and dealer's hands to determine the winner of the round.
     *
     * @return A string indicating the result of the round: "PLAYER_WIN", "DEALER_WIN", "TIE", or "PLAYER_BUSTED", "DEALER_BUSTED"
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
     * @param scores the current scores for player and dealer
     */
    private void handleWinnerResult(GameResult result, int[] scores) {
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

    public void showPlayerHand(Player player, boolean showAll) {
        StringBuilder handString = new StringBuilder(player.getName() + "'s cards: [");
        List<Card> hand = player.getHand();

        for (int i = 0; i < hand.size(); i++) {
            if (!showAll && i == 1 && player.isDealer()) {
                handString.append("<Hidden card>");
            } else {
                handString.append(hand.get(i).getSuit())
                        .append(" (").append(hand.get(i).getValue()).append(")");
            }

            if (i != hand.size() - 1) {
                handString.append(", ");
            }
        }

        handString.append("]");

        System.out.println(handString);

        if (!player.isDealer()) {
            System.out.println("Your hand value: " + player.getHandValue());
        }
    }

    public Player getPlayer() {
        return player;
    }

    public Player getDealer() {
        return dealer;
    }

}
