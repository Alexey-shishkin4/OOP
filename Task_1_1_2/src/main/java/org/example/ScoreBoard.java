package org.example;

public class ScoreBoard {
    private int playerScore;
    private int dealerScore;

    public ScoreBoard() {
        this.playerScore = 0;
        this.dealerScore = 0;
    }

    public void incrementPlayerScore() {
        playerScore++;
    }

    public void incrementDealerScore() {
        dealerScore++;
    }

    public int getPlayerScore() {
        return playerScore;
    }

    public int getDealerScore() {
        return dealerScore;
    }

    @Override
    public String toString() {
        return "Player: " + playerScore + " | Dealer: " + dealerScore;
    }
}
