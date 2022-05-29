package org.dhbw.swe.board;

import java.awt.*;
import java.util.List;
import java.util.Optional;

public interface BoardInterface{

    List<FieldInterface> getBoard();
    List<Optional<Color>> getColorBoard();
    List<Integer> getGamePiecePositions(Color color);

    Color checkWin();
    Optional<Integer> calculateMove(int fieldIndex, int dice);

    void initBoard(int playerNumber);
    void makeMove(int from, int to);

    boolean isAllowedToRedice(Color color);
    boolean isMovePossible(Color color, int dice);

    void calculateAlgorithmMove(final Color color, int dice);
    int getAlgorithmMoveFrom();
    int getAlgorithmMoveTo();

    static BoardInterface initBoardInterface(int maxPlayers) {

        if(maxPlayers == 4){

            return new BoardFour(new ControlMechanismFour());

        }

        throw new RuntimeException("There is no board with maximum " + maxPlayers + " players");
    }

}
