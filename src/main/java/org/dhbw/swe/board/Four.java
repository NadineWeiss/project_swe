package org.dhbw.swe.board;

import java.awt.Color;
import java.util.Arrays;
import java.util.ArrayList;
import java.util.List;

public class Four extends AbstractBoard{

    private List<FieldInterface> board;
    private ControlMechanismInterface controlMechanismInterface;

    public Four() {
        this.controlMechanismInterface = (ControlMechanismInterface)new ControlMechanismFour();
    }

    public void initBoard(final int playerNumber) {

        final List<FieldInterface> result = new ArrayList<FieldInterface>();
        final List<String> colors = Arrays.asList("RED", "GREEN", "YELLOW", "BLUE").subList(0, playerNumber);

        Graph.INSTANCE.four.forEach(x -> {
            String type = x.getType().toString();
            if (type.contains("INIT") && colors.contains(type.substring(0, type.length() - 4))) {
                FieldInterface field = new Field(x.getType(), new GamePiece(this.getColor(x.getType())));
                result.add(field);
            }
            else {
                result.add(new Field(x.getType()));
            }
        });

        this.board = result;
    }

    private Color getColor(final FieldType type) {
        if (type.toString().contains("RED")) {
            return Color.RED;
        }
        if (type.toString().contains("BLUE")) {
            return Color.BLUE;
        }
        if (type.toString().contains("GREEN")) {
            return Color.GREEN;
        }
        return Color.YELLOW;
    }

    public ControlMechanismInterface getControlMechanism() {
        return this.controlMechanismInterface;
    }

    public List<FieldInterface> getBoard() {
        return this.board;
    }
}
