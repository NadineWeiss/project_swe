package org.dhbw.swe.game;

import java.awt.Color;
import org.dhbw.swe.board.BoardInterface;

public class GameParameter {

    private BoardInterface board;
    private Color turn;
    private int playerNumber;

    public GameParameter(final BoardInterface board, final Color turn, final int playerNumber) {
        this.board = board;
        this.turn = turn;
        this.playerNumber = playerNumber;
    }

    public BoardInterface getBoard() {
        return this.board;
    }

    public void setBoard(final BoardInterface board) {
        this.board = board;
    }

    public Color getTurn() {
        return this.turn;
    }

    public void setTurn(final Color turn) {
        this.turn = turn;
    }

    public int getPlayerNumber() {
        return this.playerNumber;
    }

    public void setPlayerNumber(final int playerNumber) {
        this.playerNumber = playerNumber;
    }

}
