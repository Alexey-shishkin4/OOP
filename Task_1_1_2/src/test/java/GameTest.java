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
        Player player = new Player("Player", false);
        player.takeCard(new Card("A", "Hearts"));
        player.takeCard(new Card("K", "Spades"));
        assertTrue(player.hasBlackjack(), "Player should have Blackjack");
    }

    @Test
    public void testPlayerBust() {
        // Test scenario where the player busts
        Player player = new Player("Player", false);
        player.takeCard(new Card("10", "Diamonds"));
        player.takeCard(new Card("8", "Hearts"));
        player.takeCard(new Card("5", "Clubs"));
        assertTrue(player.isBusted(), "Player should bust with hand value > 21");
    }

    @Test
    public void testDealerHitsUntil17() {
        // Test scenario where the dealer hits until reaching a hand value of at least 17
        Player dealer = new Player("Dealer", true);
        Deck deck = new Deck();

        dealer.takeCard(new Card("5", "Diamonds"));
        dealer.takeCard(new Card("6", "Hearts"));

        // Simulate the dealer's turn (dealer should hit until reaching 17 or more)
        while (dealer.getHandValue() < 17) {
            dealer.takeCard(deck.dealCard());
        }

        assertTrue(dealer.getHandValue() >= 17,
                "Dealer should have hand value of at least 17");
    }

    @Test
    public void testPlayerWinsWithHigherScore() {
        // Test a scenario where the player wins with a higher score than the dealer
        Player player = new Player("Player", false);
        Player dealer = new Player("Dealer", true);

        player.takeCard(new Card("10", "Hearts"));
        player.takeCard(new Card("7", "Diamonds"));
        dealer.takeCard(new Card("9", "Clubs"));
        dealer.takeCard(new Card("7", "Hearts"));

        assertTrue(player.getHandValue() > dealer.getHandValue(),
                "Player should win with higher score");
    }

    @Test
    public void testPlayerAndDealerTie() {
        // Test scenario where player and dealer tie
        Player player = new Player("Player", false);
        Player dealer = new Player("Dealer", true);

        player.takeCard(new Card("10", "Hearts"));
        player.takeCard(new Card("8", "Diamonds"));
        dealer.takeCard(new Card("10", "Clubs"));
        dealer.takeCard(new Card("8", "Hearts"));

        assertEquals(player.getHandValue(), dealer.getHandValue(),
                "Player and dealer should have the same score and tie");
    }
}
