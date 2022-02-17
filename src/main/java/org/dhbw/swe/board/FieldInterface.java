package org.dhbw.swe.board;

import java.util.List;

public interface FieldInterface {

    FieldType getType();

    GamePieceInterface getGamePiece();

    void setType(final FieldType fieldType);

    void setGamePiece(final GamePieceInterface gamePieceInterface);

}
