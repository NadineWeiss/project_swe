package org.dhbw.swe.board;

import java.awt.*;
import java.util.List;
import java.util.Optional;

public interface BoardInterface{

    List<FieldInterface> getField();
    List<Optional<Color>> getColorField();
    List<Integer> getGamePiecePositions(Color color);

    Color checkWin();
    Optional<Integer> calculateTurn(int fieldIndex, int dice);

    void initBoard(int playerNumber);
    void makeMove(int from, int to);

    boolean isAllowedToRedice(Color color);
    boolean isTurnPossible(Color color, int dice);

    void calculateAlgorithmMove(final Color color, int dice);
    int getAlgorithmMoveFrom();
    int getAlgorithmMoveTo();

    static BoardInterface initBoardInterface(int maxPlayers) {

        if(maxPlayers == 4){

            return new BoardFour();

        }

        throw new RuntimeException("There is no board with maximum " + maxPlayers + " players");
    }

}
