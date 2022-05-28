package org.dhbw.swe.board;

import java.awt.*;

public class GamePiece implements GamePieceInterface{

    private final Color color;

    public GamePiece(Color color) {
        this.color = color;
    }

    @Override
    public Color getColor() {
        return this.color;
    }

}
