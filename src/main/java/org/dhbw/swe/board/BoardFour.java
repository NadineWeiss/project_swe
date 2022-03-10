package org.dhbw.swe.board;

import org.dhbw.swe.graph.FieldType;
import org.dhbw.swe.graph.Graph;

import java.awt.Color;
import java.util.Arrays;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class BoardFour extends AbstractBoard{

    private List<FieldInterface> field;
    //private ControlMechanismInterface controlMechanismInterface;

    public BoardFour() {

        controlMechanismInterface = new ControlMechanismFour();

    }

    public void initBoard(final int playerNumber) {

        final List<FieldInterface> result = new ArrayList<>();
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

        this.field = result;
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

        return controlMechanismInterface;

    }

    public List<FieldInterface> getField() {

        return field;

    }

    public List<Optional<Color>> getColorField() {

        List<Optional<Color>> result = new ArrayList<>();

        for(FieldInterface fieldInterface : field){

            if(fieldInterface.containsGamePiece()){

                result.add(Optional.ofNullable(fieldInterface.getGamePiece().color()));

            }else{

                result.add(Optional.empty());

            }

        }

        return result;

    }

}
