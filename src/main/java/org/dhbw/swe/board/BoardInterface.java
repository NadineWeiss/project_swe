package org.dhbw.swe.board;

import java.util.List;

public interface BoardInterface {

    List<FieldInterface> board();
    List<FieldInterface> makeMove(FieldInterface from, FieldInterface to);
    ControlMechanismInterface getControlMechanism();

}
