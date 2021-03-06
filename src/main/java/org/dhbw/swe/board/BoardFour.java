package org.dhbw.swe.board;

import org.dhbw.swe.graph.FieldType;
import org.dhbw.swe.graph.Graph;

import java.awt.Color;
import java.util.Arrays;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class BoardFour extends AbstractBoard{

    private List<FieldInterface> board;

    public BoardFour(ControlMechanismInterface controlMechanism) {

        controlMechanismInterface = controlMechanism;

    }

    @Override
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

        board = result;
    }

    @Override
    public List<FieldInterface> getBoard() {

        return board;

    }

    @Override
    public List<Optional<Color>> getColorBoard() {

        List<Optional<Color>> result = new ArrayList<>();

        for(FieldInterface fieldInterface : board){

            if(fieldInterface.containsGamePiece()){

                result.add(Optional.ofNullable(fieldInterface.getGamePiece().getColor()));

            }else{

                result.add(Optional.empty());

            }

        }

        return result;

    }

    @Override
    public List<Integer> getGamePiecePositions(Color color) {

        List<Integer> positions = new ArrayList<>();

        for(FieldInterface field : board){

            if(field.getGamePiece() != null && field.getGamePiece().getColor().equals(color)){

                positions.add(this.board.indexOf(field));

            }

        }

        return positions;

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

}
