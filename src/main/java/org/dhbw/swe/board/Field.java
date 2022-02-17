package org.dhbw.swe.board;

import java.util.List;

public class Field implements FieldInterface{

    private FieldType type;
    private GamePieceInterface gamePiece;

    public Field(final FieldType type) {
        this.type = type;
    }

    public Field(final FieldType type, final GamePieceInterface gamePiece) {
        this.type = type;
        this.gamePiece = gamePiece;
    }

    public FieldType getType() {
        return this.type;
    }

    public GamePieceInterface getGamePiece() {
        return this.gamePiece;
    }

    public void setType(final FieldType type) {
        this.type = type;
    }

    public void setGamePiece(final GamePieceInterface gamePiece) {
        this.gamePiece = gamePiece;
    }

}
