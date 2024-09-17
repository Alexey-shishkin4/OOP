package org.example;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.junit.jupiter.api.Assertions.assertFalse;


/**
 * Unit tests for the Player class.
 */
class PlayerTest {
    private Player player;
    private Player dealer;
    private Card cardAceOfSpades;
    private Card cardTenOfHearts;
    private Card cardTwoOfDiamonds;

    @BeforeEach
    void setUp() {
        player = new Player("John", false);
        dealer = new Player("Dealer", true);
        cardAceOfSpades = new Card("A", "Spades");
        cardTenOfHearts = new Card("10", "Hearts");
        cardTwoOfDiamonds = new Card("2", "Diamonds");
    }

    @Test
    void testTakeCard() {
        player.takeCard(cardAceOfSpades);
        assertEquals(11, player.getHandValue(),
                "Player's hand should contain one card.");
    }

    @Test
    void testGetHandValueWithAce() {
        player.takeCard(cardAceOfSpades);
        player.takeCard(cardTenOfHearts);
        assertEquals(21, player.getHandValue(),
                "Hand value should be 21 with Ace and 10.");
    }

    @Test
    void testGetHandValueWithMultipleAces() {
        player.takeCard(cardAceOfSpades);
        player.takeCard(cardAceOfSpades);
        player.takeCard(cardTenOfHearts);
        assertEquals(12, player.getHandValue(),
                "Hand value should be 12 with two Aces and 10.");
    }

    @Test
    void testIsBusted() {
        player.takeCard(cardTenOfHearts);
        player.takeCard(cardTenOfHearts);
        player.takeCard(cardTwoOfDiamonds);
        assertTrue(player.isBusted(),
                "Player should be busted with hand value exceeding 21.");
    }

    @Test
    void testHasBlackjack() {
        player.takeCard(cardAceOfSpades);
        player.takeCard(cardTenOfHearts);
        assertTrue(player.hasBlackjack(),
                "Player should have blackjack with Ace and 10.");
    }

    @Test
    void testHasBlackjackWithMoreThanTwoCards() {
        player.takeCard(cardAceOfSpades);
        player.takeCard(cardTenOfHearts);
        player.takeCard(cardTwoOfDiamonds);
        assertFalse(player.hasBlackjack(),
                "Player should not have blackjack with more than two cards.");
    }

    @Test
    void testShowHandForDealer() {
        dealer.takeCard(cardAceOfSpades);
        dealer.takeCard(cardTenOfHearts);

        // Capture system output for verification
        java.io.ByteArrayOutputStream outContent =
                new java.io.ByteArrayOutputStream();
        System.setOut(new java.io.PrintStream(outContent));

        dealer.showHand(false);
        String expectedOutput = "Dealer's cards:\nA of Spades\n[Hidden]\n";
        assertTrue(outContent.toString().contains(expectedOutput),
                "Dealer's hand should hide the second card.");
    }

    @Test
    void testShowHandForPlayer() {
        player.takeCard(cardAceOfSpades);
        player.takeCard(cardTenOfHearts);

        // Capture system output for verification
        java.io.ByteArrayOutputStream outContent = new java.io.ByteArrayOutputStream();
        System.setOut(new java.io.PrintStream(outContent));

        player.showHand(true);
        String expectedOutput =
                "John's cards:\nA of Spades\n10 of Hearts\nYour hand value: 21\n";
        assertTrue(outContent.toString().contains(expectedOutput),
                "Player's hand should display all cards.");
    }
}
