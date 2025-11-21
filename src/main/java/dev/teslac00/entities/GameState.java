/**
 * ---------------------------------------------------------------
 * Project : Ping_Pong
 * File    : GameState
 * Author  : Vikas Kumar
 * Created : 21-11-2025
 * ---------------------------------------------------------------
 */
package dev.teslac00.entities;

import static dev.teslac00.util.Constants.VIEWPORT_WIDTH;

public class GameState {

    private final Ball ball;
    private boolean playerWon = false;
    private boolean gameOver = false;

    public GameState(Ball ball) {
        this.ball = ball;
    }

    public void update(double deltaTime) {
        float limitX = (VIEWPORT_WIDTH / 2.0f) - ball.transform.scale.x;
        if (ball.transform.position.x >= limitX) {    // check right screen collision
            gameOver = true;
            playerWon = true;
        } else if (ball.transform.position.x <= -limitX) {  // check left screen collision
            gameOver = true;
            playerWon = false;
        }
    }

    public boolean isGameOver() {
        return gameOver;
    }

    public boolean isPlayerWon() {
        return playerWon;
    }
}
