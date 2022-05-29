package org.dhbw.swe.game;

import org.dhbw.swe.board.BoardInterface;

import java.awt.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class GameParameterMock implements GameParameterInterface{

    private BoardInterface board;
    private Color turn;
    private int playerNumber;

    public GameParameterMock(BoardInterface board) {

        this.board = board;
        this.playerNumber = 4;

    }

    @Override
    public BoardInterface getBoard() {
        return board;
    }

    @Override
    public void setBoard(BoardInterface board) {

    }

    @Override
    public Color getTurn() {
        return turn;
    }

    @Override
    public void setTurn(Color turn) {
        this.turn = turn;
    }

    @Override
    public int getPlayerNumber() {
        return 4;
    }

    @Override
    public void setPlayerNumber(int playerNumber) {

    }

    @Override
    public List<Color> getAlgoColors() {
        return new ArrayList<>();
    }

    @Override
    public void setAlgoColors(List<Color> algoColors) {

    }

    @Override
    public Map<Player, Color> getPlayerColors() {
        return Map.of(new Player("1"), Color.RED,
                new Player("2"), Color.YELLOW,
                new Player("3"),Color.GREEN,
                new Player("4"),Color.BLUE);
    }

    @Override
    public Map<String, Color> getPlayerNameColors() {
        return null;
    }

    @Override
    public void setPlayerColors(Map<Player, Color> playerColors) {

    }

}
