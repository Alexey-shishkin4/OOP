package org.example;

import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

/**
 * Unit tests for the {@code Game} class.
 */
public class GameTest {

    private Game game;

    @BeforeEach
    public void setUp() {
        game = new Game();  // Initialize the game before each test
    }

    @Test
    public void testInitialSetup() {
        // Ensure the game initializes with non-null player, dealer, and deck
        assertNotNull(game);
    }

    @Test
    public void testPlayerBlackjack() {
        // Test scenario where player starts with a blackjack
        Player player = game.getPlayer();
        player.takeCard(new Card("A", "Hearts"));
        player.takeCard(new Card("K", "Spades"));
        assertTrue(player.hasBlackjack(), "Player should have Blackjack");
    }

    @Test
    public void testPlayerBust() {
        // Test scenario where the player busts
        Player player = game.getPlayer();
        player.takeCard(new Card("10", "Diamonds"));
        player.takeCard(new Card("8", "Hearts"));
        player.takeCard(new Card("5", "Clubs"));
        assertTrue(player.isBusted(), "Player should bust with hand value > 21");
    }

    @Test
    public void testDealerHitsUntil17() {
        // Simulate the dealer's turn to check if they hit until reaching 17
        game.dealerTurn();

        Player dealer = game.getDealer();
        assertTrue(dealer.getHandValue() >= 17,
                "Dealer should have hand value of at least 17");
    }

    @Test
    public void testPlayerWinsWithHigherScore() {
        // Test scenario where the player wins with a higher score than the dealer
        Player player = game.getPlayer();
        Player dealer = game.getDealer();

        player.takeCard(new Card("10", "Hearts"));
        player.takeCard(new Card("7", "Diamonds"));
        dealer.takeCard(new Card("9", "Clubs"));
        dealer.takeCard(new Card("7", "Hearts"));

        String result = game.determineWinnerResult();
        assertEquals("PLAYER_WIN", result, "Player should win with higher score");
    }

    @Test
    public void testPlayerAndDealerTie() {
        // Test scenario where player and dealer tie
        Player player = game.getPlayer();
        Player dealer = game.getDealer();

        player.takeCard(new Card("10", "Hearts"));
        player.takeCard(new Card("8", "Diamonds"));
        dealer.takeCard(new Card("10", "Clubs"));
        dealer.takeCard(new Card("8", "Hearts"));

        String result = game.determineWinnerResult();
        assertEquals("TIE", result, "Player and dealer should have the same score and tie");
    }
}
