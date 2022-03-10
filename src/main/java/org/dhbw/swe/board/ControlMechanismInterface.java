package org.dhbw.swe.board;

import java.awt.*;
import java.util.List;
import java.util.Map;
import java.util.Optional;

public interface ControlMechanismInterface {

    boolean isAllowedToRedice(Color color, List<FieldInterface> field);
    Color checkWin(List<FieldInterface> field);
    Optional<Integer> calculateTurn(int fieldIndex, final List<FieldInterface> field, int dice);
    boolean isTurnPossible(Color color, final List<FieldInterface> field, int dice);
    Map<GamePieceInterface, Integer> calculateTurns(Color color, List<FieldInterface> field, int jump);
    void calculateAlgorithmMove(final Color color, final List<FieldInterface> field, int dice);
    int getAlgorithmMoveFrom();
    int getAlgorithmMoveTo();

}
