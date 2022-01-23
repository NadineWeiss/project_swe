package org.dhbw.swe.board;

import java.util.ArrayList;
import java.util.List;

public class Four extends AbstractBoard{

    private List<FieldInterface> initBoard(int playerNumber){

        //todo
        return new ArrayList<FieldInterface>();

    }

    private List<FieldInterface> initBoard(int playerNumber, List<FieldInterface> field){

        //todo
        return new ArrayList<FieldInterface>();

    }

    @Override
    public List<FieldInterface> board() {
        return null;
    }

    @Override
    public List<FieldInterface> makeMove(FieldInterface from, FieldInterface to) {
        return null;
    }

    @Override
    public ControlMechanismInterface getControlMechanism() {
        return null;
    }
}
