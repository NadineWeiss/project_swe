package org.dhbw.swe.game;

import org.dhbw.swe.board.BoardInterface;

import java.awt.*;
import java.util.List;
import java.util.Map;

public interface GameParameterInterface {

    BoardInterface getBoard();
    void setBoard(final BoardInterface board);
    Color getTurn();
    void setTurn(final Color turn);
    int getPlayerNumber();
    void setPlayerNumber(final int playerNumber);
    List<Color> getAlgoColors();
    void setAlgoColors(List<Color> algoColors);
    Map<Player, Color> getPlayerColors();
    Map<String, Color> getPlayerNameColors();
    void setPlayerColors(Map<Player, Color> playerColors);
}
