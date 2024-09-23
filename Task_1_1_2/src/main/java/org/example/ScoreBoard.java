package org.example;

/**
 * The {@code ScoreBoard} class implements the core logic of a blackjack game.
 */
public class ScoreBoard {
    private int playerScore;
    private int dealerScore;

    /**
     * Init of ScoreBoard.
     */
    public ScoreBoard() {
        this.playerScore = 0;
        this.dealerScore = 0;
    }

    /**
     * Increment of playerScore.
     */
    public void incrementPlayerScore() {
        playerScore++;
    }

    /**
     * Increment Dealer Score.
     */
    public void incrementDealerScore() {
        dealerScore++;
    }

    /**
     * Returns player score for tests.
     *
     * @return player score.
     */
    public int getPlayerScore() {
        return playerScore;
    }

    /**
     * Returns dealer score for tests.
     *
     * @return dealer score.
     */
    public int getDealerScore() {
        return dealerScore;
    }

    /**
     * Returns String score.
     *
     * @return String score.
     */
    @Override
    public String toString() {
        return "Player: " + playerScore + " | Dealer: " + dealerScore;
    }
}
