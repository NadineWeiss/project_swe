package org.dhbw.swe.board;

import org.dhbw.swe.graph.Graph;
import org.dhbw.swe.graph.GraphUtilities;
import org.dhbw.swe.graph.Node;

import java.awt.*;
import java.util.List;
import java.util.*;
import java.util.stream.Collectors;

public class ControlMechanismFour extends AbstractControlMechanism {

    public Color checkWin(final List<FieldInterface> field) {
        final List<Color> colors = Arrays.asList(Color.RED, Color.YELLOW, Color.GREEN, Color.BLUE);
        for (final Color color : colors) {
            if (field.stream().filter(x -> x.getType().equals(GraphUtilities.getTargetType(color)) && x.getGamePiece() != null).count() == 4) {
                return color;
            }
        }
        return null;
    }

    public Optional<Integer> calculateTurn(int fieldIndex, final List<FieldInterface> field, int dice) {

        GamePieceInterface currentGamePiece = field.get(fieldIndex).getGamePiece();
        Map<GamePieceInterface, Integer> possibleMoves = calculateTurns(currentGamePiece.color(), field, dice);

        for(Map.Entry<GamePieceInterface, Integer> move : possibleMoves.entrySet()){

            if(move.getKey().equals(currentGamePiece))
                return Optional.of(move.getValue());

        }

        return Optional.empty();

    }

    public boolean isTurnPossible(Color color, final List<FieldInterface> field, int dice){

        if(calculateTurns(color, field, dice).isEmpty())
            return false;

        return true;

    }

    public Map<GamePieceInterface, Integer> calculateTurns(final Color color, final List<FieldInterface> field, final int dice) {

        final Map<GamePieceInterface, Integer> result = new HashMap<>();

        //Falls eine 6 gewürfelt wurde und eine Figur der Farbe im Haus ist, muss aus dem Haus rausgesprungen werden
        if (dice == 6 && field.stream()
                .filter(x -> x.getType().equals(GraphUtilities.getInitType(color)))
                .anyMatch(x -> x.getGamePiece() != null) && !field.stream()
                    .filter(x -> x.getType().equals(GraphUtilities.getStartType(color)))
                    .anyMatch(x -> x.getGamePiece() != null && x.getGamePiece().color().equals(color))) {

            //Besetzten Felder im Haus herausfinden
            final List<FieldInterface> initFields = field.stream()
                    .filter(x -> x.getType().equals(GraphUtilities.getInitType(color)) && x.getGamePiece() != null)
                    .collect(Collectors.toList());

            //Die möglichen Züge aus dem Haus zurückgeben
            for (final FieldInterface initField : initFields) {

                result.put(initField.getGamePiece(), this.getStartPosition(color));

            }

            return result;
        }

        final FieldInterface startField = field.stream()
                .filter(x -> x.getType().equals(GraphUtilities.getStartType(color)))
                .findFirst().get();

        //Das Feld vor dem Haus muss freigemacht werden, wenn da eine eigene Figur steht
        //Ausführlich: Falls Figuren im Haus & das Feld vor dem Haus durch eigene Figur besetzt ist &
        //             das Feld, auf das die Figur vor dem eigenen Haus springen könnte, nicht durch eigene Figur besetzt oder leer ist
        if (field.stream()
                .filter(x -> x.getType().equals(GraphUtilities.getInitType(color)))
                .anyMatch(x -> x.getGamePiece() != null)
                && startField.getGamePiece() != null
                && startField.getGamePiece().color().equals(color)
                && ((field.get(this.getTargetPosition(Graph.INSTANCE.four.get(field.indexOf(startField)), dice, color)).getGamePiece() != null
                && !field.get(this.getTargetPosition(Graph.INSTANCE.four.get(field.indexOf(startField)), dice, color)).getGamePiece().color().equals(color))
                || field.get(this.getTargetPosition(Graph.INSTANCE.four.get(field.indexOf(startField)), dice, color)).getGamePiece() == null)) {

            return Map.of(startField.getGamePiece(), this.getTargetPosition(Graph.INSTANCE.four.get(field.indexOf(startField)), dice, color));

        }

        //Alle Möglichkeiten auf dem Spielfeld zurückgeben
        final List<Node> currentPositions = this.getCurrentPosition(color, field).stream()
                .filter(x -> !x.getType().equals(GraphUtilities.getInitType(color))).collect(Collectors.toList());

        for (final Node node : currentPositions) {

            final int target = this.getTargetPosition(node, dice, color);

            if (!currentPositions.stream().map(x -> Graph.INSTANCE.four.indexOf(x)).anyMatch(x -> x == target) && target != -1) {

                result.put(field.get(this.graph.four.indexOf(node)).getGamePiece(), target);

            }

        }

        return result;

    }

    public boolean isAllowedToRedice(final Color color, final List<FieldInterface> field) {

        final List<FieldInterface> initFields = field.stream().filter(x -> x.getType().equals(GraphUtilities.getInitType(color)) && x.getGamePiece() != null).collect(Collectors.toList());
        final List<FieldInterface> targetFields = field.stream().filter(x -> x.getType().equals(GraphUtilities.getTargetType(color)) && x.getGamePiece() != null).collect(Collectors.toList());

        boolean cleanedUp = true;
        for (final FieldInterface targetField : targetFields) {
            if (this.getTargetPosition(Graph.INSTANCE.four.get(field.indexOf(targetField)), 1, color) != -1) {
                cleanedUp = false;
            }
        }
        return cleanedUp && initFields.size() + targetFields.size() == 4;

    }

    private int getStartPosition(final Color color) {
        if (color.equals(Color.RED)) {
            return 17;
        }
        if (color.equals(Color.BLUE)) {
            return 47;
        }
        if (color.equals(Color.GREEN)) {
            return 37;
        }
        return 27;
    }

    private List<Node> getCurrentPosition(final Color color, final List<FieldInterface> field) {

        return field.stream()
                .filter(x -> x.getGamePiece() != null)
                .filter(x -> x.getGamePiece().color().equals(color))
                .map(x -> field.indexOf(x))
                .map(x -> (Node)Graph.INSTANCE.four.get(x))
                .collect(Collectors.toList());

    }

    private int getTargetPosition(final Node node, final int jump, final Color color) {
        Node currentNode = node;
        for (int i = 0; i < jump; ++i) {
            if (currentNode.getType().equals((Object)GraphUtilities.getEndType(color))) {
                currentNode = currentNode.getSpecialEdge().getTarget();
            }
            else {
                if (currentNode.getEdges().isEmpty()) {
                    return -1;
                }
                currentNode = currentNode.getDefaultEdge().getTarget();
            }
        }
        return this.graph.four.indexOf(currentNode);
    }
}