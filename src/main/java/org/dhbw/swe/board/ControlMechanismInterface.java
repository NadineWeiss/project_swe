package org.dhbw.swe.board;

import java.awt.*;
import java.util.List;
import java.util.Map;
import java.util.Optional;

public interface ControlMechanismInterface {

    boolean isAllowedToRedice(Color color, List<FieldInterface> board);
    Color checkWin(List<FieldInterface> board);
    Optional<Integer> calculateMove(int fieldIndex, final List<FieldInterface> board, int dice);
    boolean isMovePossible(Color color, final List<FieldInterface> board, int dice);
    Map<GamePieceInterface, Integer> calculateMoves(Color color, List<FieldInterface> board, int jump);
    void calculateAlgorithmMove(final Color color, final List<FieldInterface> board, int dice);
    int getAlgorithmMoveFrom();
    int getAlgorithmMoveTo();

}
