package org.dhbw.swe.visualization;

import org.dhbw.swe.game.Context;
import org.dhbw.swe.game.ObserverContext;
import org.dhbw.swe.graph.Graph;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionListener;
import java.util.List;
import java.util.Map;
import java.util.Optional;

public interface GameFrameInterface {

    void newGame(List<Optional<Color>> board);
    int getPlayerNumber();
    int getAlgoNumber(int playerNumber);
    String getSelectedFile(List<String> fileChoices);
    void winner(Color color);
    Map<String, Boolean> getPlayers(int playerNumber, List<String> playerSelection);
    void setPlayerNames(Map<String, Color> playerNames, List<Color> algoColors);
    void diced(int diceValue, Color turnColor);
    void markAdditionalField(int index);
    void setTurn(Color color, boolean algo);
    void setGamePieces(List<Optional<Color>> board);

}
