package org.dhbw.swe.game;

import org.dhbw.swe.board.BoardInterface;

import java.awt.*;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class GameParameter implements GameParameterInterface{

    private BoardInterface board;
    private Color turn;
    private int playerNumber;
    private List<Color> algoColors;
    private Map<Player, Color> playerColors;

    public GameParameter(BoardInterface board, Color turn, int playerNumber) {
        this.board = board;
        this.turn = turn;
        this.playerNumber = playerNumber;
    }

    public GameParameter(BoardInterface board, Color turn, int playerNumber, List<Color> algoColors) {
        this.board = board;
        this.turn = turn;
        this.playerNumber = playerNumber;
        this.algoColors = algoColors;
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

    public List<Color> getAlgoColors() {
        return algoColors;
    }

    public void setAlgoColors(List<Color> algoColors) {
        this.algoColors = algoColors;
    }

    public Map<Player, Color> getPlayerColors() {
        return playerColors;
    }

    public Map<String, Color> getPlayerNameColors() {

        Map<String, Color> playerNameColors = new HashMap<>();

        for(Map.Entry<Player, Color> entry : this.playerColors.entrySet()){

            String name = entry.getKey().getName();
            Color color = entry.getValue();

            playerNameColors.put(name, color);

        }

        return playerNameColors;

    }

    public void setPlayerColors(Map<Player, Color> playerColors) {

        this.playerColors = playerColors;

    }

}
