package org.dhbw.swe.board;


import org.dhbw.swe.graph.FieldType;

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

    @Override
    public FieldType getType() {
        return this.type;
    }

    @Override
    public GamePieceInterface getGamePiece() {
        return this.gamePiece;
    }

    @Override
    public void setType(final FieldType type) {
        this.type = type;
    }

    @Override
    public void setGamePiece(final GamePieceInterface gamePiece) {
        this.gamePiece = gamePiece;
    }

    @Override
    public boolean containsGamePiece() {
        if(gamePiece != null)
            return true;

        return false;
    }

}
