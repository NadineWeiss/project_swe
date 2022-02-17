package org.dhbw.swe.board;

import java.util.List;

public interface BoardInterface
{
    List<FieldInterface> getBoard();

    void makeMove(final int p0, final int p1);

    ControlMechanismInterface getControlMechanism();

    void initBoard(final int p0);
}
