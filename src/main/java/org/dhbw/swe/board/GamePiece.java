package org.dhbw.swe.board;

import java.awt.*;
import java.util.Objects;

public final class GamePiece implements GamePieceInterface{

    private final Color color;

    public GamePiece(final Color color) {
        this.color = color;
    }

    @Override
    public Color color() {
        return this.color;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        GamePiece gamePiece = (GamePiece) o;
        return color.equals(gamePiece.color);
    }

    @Override
    public int hashCode() {
        return Objects.hash(color);
    }
}
