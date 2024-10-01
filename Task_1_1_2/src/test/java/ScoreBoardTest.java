package org.example;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;


/**
 * Unit tests for the ScoreBoard class.
 */
public class ScoreBoardTest {
    private ScoreBoard scoreBoard;

    @BeforeEach
    public void setUp() {
        scoreBoard = new ScoreBoard();
    }

    @Test
    public void testInitialScore() {
        assertEquals(0, scoreBoard.getPlayerScore(),
                "Initial player score should be 0");
        assertEquals(0, scoreBoard.getDealerScore(),
                "Initial dealer score should be 0");
    }

    @Test
    public void testIncrementPlayerScore() {
        scoreBoard.incrementPlayerScore();
        assertEquals(1, scoreBoard.getPlayerScore(),
                "Player score should be 1 after increment");
    }

    @Test
    public void testIncrementDealerScore() {
        scoreBoard.incrementDealerScore();
        assertEquals(1, scoreBoard.getDealerScore(),
                "Dealer score should be 1 after increment");
    }

    @Test
    public void testMultipleIncrements() {
        scoreBoard.incrementPlayerScore();
        scoreBoard.incrementPlayerScore();
        scoreBoard.incrementDealerScore();

        assertEquals(2, scoreBoard.getPlayerScore(),
                "Player score should be 2 after two increments");
        assertEquals(1, scoreBoard.getDealerScore(),
                "Dealer score should be 1 after one increment");
    }

    @Test
    public void testToString() {
        scoreBoard.incrementPlayerScore();
        scoreBoard.incrementDealerScore();
        scoreBoard.incrementDealerScore();

        String expectedString = "Player: 1 | Dealer: 2";
        assertEquals(expectedString, scoreBoard.toString(),
                "Scoreboard toString() should match expected format");
    }
}
