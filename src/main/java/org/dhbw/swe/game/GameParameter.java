package org.dhbw.swe.game;

import org.dhbw.swe.board.BoardInterface;

import java.awt.*;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class GameParameter {

    private BoardInterface board;
    private Color turn;
    private int playerNumber;
    private List<Color> algoColors;
    private Map<Player, Color> playerColors;

    public GameParameter(final BoardInterface board, final Color turn, final int playerNumber) {
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

    public void setPlayerColors(List<Player> players) {

        Map<Player, Color> playerColors = new HashMap<>();

        if (players.size() == 1) {
            playerColors.put(players.get(0), Color.RED);
        }
        else if (players.size() == 2) {
            playerColors.put(players.get(0), Color.RED);
            playerColors.put(players.get(1), Color.BLUE);
        }
        else if (players.size() == 3) {
            playerColors.put(players.get(0), Color.RED);
            playerColors.put(players.get(1), Color.YELLOW);
            playerColors.put(players.get(2), Color.BLUE);
        }
        else {
            playerColors.put(players.get(0), Color.RED);
            playerColors.put(players.get(1), Color.YELLOW);
            playerColors.put(players.get(2), Color.GREEN);
            playerColors.put(players.get(3), Color.BLUE);
        }

        this.playerColors = playerColors;

    }

    public void setPlayerColors(Map<Player, Color> playerColors) {

        this.playerColors = playerColors;

    }

}
