package org.dhbw.swe.board;

import java.util.List;

public interface FieldInterface {

    List<FieldType> type();
    GamePieceInterface gamePiece();
    void setGamePiece(GamePiece gamePiece);
}
