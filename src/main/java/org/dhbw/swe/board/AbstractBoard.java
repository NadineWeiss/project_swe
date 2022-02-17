package org.dhbw.swe.board;

import java.awt.Color;

public abstract class AbstractBoard implements BoardInterface
{
    public void makeMove(final int from, final int to) {
        if (this.getBoard().get(to).getGamePiece() != null) {
            final GamePieceInterface gamePiece = this.getBoard().get(to).getGamePiece();
            final FieldInterface field = (FieldInterface)this.getBoard().stream().filter(x -> x.getType().equals((Object)this.getInitType(gamePiece.color())) && x.getGamePiece() == null).findFirst().get();
            field.setGamePiece(gamePiece);
            this.getBoard().get(to).setGamePiece((GamePieceInterface)null);
        }
        this.getBoard().get(to).setGamePiece(this.getBoard().get(from).getGamePiece());
        this.getBoard().get(from).setGamePiece((GamePieceInterface)null);
    }

    private FieldType getInitType(final Color color) {
        if (color.equals(Color.RED)) {
            return FieldType.REDINIT;
        }
        if (color.equals(Color.BLUE)) {
            return FieldType.BLUEINIT;
        }
        if (color.equals(Color.GREEN)) {
            return FieldType.GREENINIT;
        }
        return FieldType.YELLOWINIT;
    }
}