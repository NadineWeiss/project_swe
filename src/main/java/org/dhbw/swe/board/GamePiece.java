package org.dhbw.swe.board;

import java.awt.*;

public class GamePiece implements GamePieceInterface{

    private Color color;

    public GamePiece(final Color color) {
        this.color = color;
    }

    @Override
    public Color color() {
        return this.color;
    }
}
