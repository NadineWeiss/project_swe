package org.dhbw.swe.board;

import org.dhbw.swe.graph.FieldType;

public interface FieldInterface {

    FieldType getType();

    GamePieceInterface getGamePiece();

    void setType(final FieldType fieldType);

    void setGamePiece(final GamePieceInterface gamePieceInterface);

    boolean containsGamePiece();

}
