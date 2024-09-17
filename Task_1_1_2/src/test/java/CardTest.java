package org.example;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertEquals;

/**
 * Unit tests for the code Card class.
 */
public class CardTest {

    /**
     * Test to check if the rank of the card is returned correctly.
     */
    @Test
    public void testGetRank() {
        Card card = new Card("A", "Hearts");
        assertEquals("A", card.getRank(),
                "Card rank should be 'A'");
    }

    /**
     * Test to check if the suit of the card is returned correctly.
     */
    @Test
    public void testGetSuit() {
        Card card = new Card("A", "Hearts");
        assertEquals("Hearts", card.getSuit(),
                "Card suit should be 'Hearts'");
    }

    /**
     * Test to check if the correct point value is returned for a number card.
     */
    @Test
    public void testGetValue_NumberCard() {
        Card card = new Card("7", "Diamonds");
        assertEquals(7, card.getValue(),
                "Card value for '7' should be 7");
    }

    /**
     * Test to check if the correct point value is returned for a face card.
     * Face cards should have a value of 10 in blackjack.
     */
    @Test
    public void testGetValue_FaceCard() {
        Card card = new Card("K", "Spades");
        assertEquals(10, card.getValue(),
                "Card value for 'K' should be 10");
    }

    /**
     * Test to check if the correct point value is returned for an ace.
     * An ace should have a value of 11 in blackjack.
     */
    @Test
    public void testGetValue_Ace() {
        Card card = new Card("A", "Clubs");
        assertEquals(11, card.getValue(),
                "Card value for 'A' should be 11");
    }

    /**
     * Test to check the string representation of a card.
     * The format should be "Rank of Suit".
     */
    @Test
    public void testToString() {
        Card card = new Card("A", "Hearts");
        assertEquals("A of Hearts", card.toString(),
                "Card string should be 'A of Hearts'");
    }

    /**
     * Test to check if the point value for an invalid rank is handled correctly.
     * For any rank not in the expected set, the value should be 0.
     */
    @Test
    public void testGetValue_InvalidRank() {
        Card card = new Card("Z", "Hearts");
        assertEquals(0, card.getValue(),
                "Invalid rank 'Z' should return value 0");
    }
}
