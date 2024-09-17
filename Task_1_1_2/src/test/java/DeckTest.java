package org.example;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertThrows;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;


/**
 * Unit tests for the Desk class.
 */
class DeckTest {
    private Deck deck;

    @BeforeEach
    void setUp() {
        deck = new Deck();
    }

    @Test
    void testDeckInitialization() {
        assertEquals(52, deck.getCardCount(),
                "Deck should be initialized with 52 cards");
    }

    @Test
    void testDealCard() {
        int initialSize = deck.getCardCount();
        Card dealtCard = deck.dealCard();
        assertNotNull(dealtCard, "Dealt card should not be null");
        assertEquals(initialSize - 1, deck.getCardCount(),
                "Deck size should decrease by 1 after dealing a card");
    }

    @Test
    void testShuffle() {
        Deck unshuffledDeck = new Deck();
        deck.shuffle();

        boolean isSameOrder = true;
        for (int i = 0; i < deck.getCardCount(); i++) {
            if (!deck.dealCard().equals(unshuffledDeck.dealCard())) {
                isSameOrder = false;
                break;
            }
        }

        assertFalse(isSameOrder,
                "The order of cards should change after shuffling");
    }

    @Test
    void testDealCardWhenDeckIsEmpty() {
        for (int i = 0; i < 52; i++) {
            deck.dealCard();
        }

        assertThrows(IndexOutOfBoundsException.class,
                () -> deck.dealCard(), "Should throw an exception when dealing from an empty deck");
    }
}
