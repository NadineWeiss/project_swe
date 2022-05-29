package org.dhbw.swe.board;

import org.dhbw.swe.graph.FieldType;
import org.dhbw.swe.graph.Graph;

import java.awt.*;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

public class BoardFourMock extends AbstractBoard{

    private List<FieldInterface> board;

    public BoardFourMock(ControlMechanismInterface controlMechanism) {

        controlMechanismInterface = controlMechanism;

    }

    public BoardFourMock() {

        controlMechanismInterface = new ControlMechanismFour();

    }

    @Override
    public List<FieldInterface> getBoard() {

        return board;

    }

    @Override
    public List<Optional<Color>> getColorBoard() {
        return null;
    }

    @Override
    public List<Integer> getGamePiecePositions(Color color) {
        return null;
    }

    @Override
    public void initBoard(int playerNumber) {

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

        this.board = result;

        //Alle Blauen Figuten in die Zielfelder
        makeMove(12, 68);
        makeMove(13, 69);
        makeMove(14, 70);
        makeMove(15, 71);

        //Zwei Grüne Figur rausstellen
        makeMove(8, 33);
        makeMove(9, 43);

        //Zwei Gelbe Figuten in die Zielfelder stellen
        makeMove(6, 63);
        makeMove(7, 61);

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

}
