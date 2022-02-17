package org.dhbw.swe.game;

import org.dhbw.swe.board.FieldInterface;
import java.util.List;
import org.dhbw.swe.board.GamePieceInterface;
import java.util.Map;
import java.awt.Color;

public class Algorithm {

    private static int moveFrom;
    private static int moveTo;

    public static void calculateMove(final Color color, final Map<GamePieceInterface, Integer> possibleMoves, final List<FieldInterface> field) {
        final GamePieceInterface gamePiece = possibleMoves.entrySet().stream().findFirst().get().getKey();
        final Object obj;
        Algorithm.moveFrom = field.indexOf(field.stream().filter(x -> x.getGamePiece() != null && x.getGamePiece().equals(x)).findFirst().get());
        Algorithm.moveTo = possibleMoves.entrySet().stream().findFirst().get().getValue();
    }

    public static int getMoveFrom() {
        return Algorithm.moveFrom;
    }

    public static void setMoveFrom(final int moveFrom) {
        Algorithm.moveFrom = moveFrom;
    }

    public static int getMoveTo() {
        return Algorithm.moveTo;
    }

    public static void setMoveTo(final int moveTo) {
        Algorithm.moveTo = moveTo;
    }

}
