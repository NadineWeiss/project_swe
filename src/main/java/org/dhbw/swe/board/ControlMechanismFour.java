package org.dhbw.swe.board;

import java.util.stream.Collector;
import java.util.stream.Collectors;
import java.util.HashMap;
import java.util.Map;
import java.util.Iterator;
import java.util.Arrays;
import java.awt.Color;
import java.util.List;

public class ControlMechanismFour extends AbstractControlMechanism
{
    public Color checkWin(final List<FieldInterface> field) {
        final List<Color> colors = Arrays.asList(Color.RED, Color.YELLOW, Color.GREEN, Color.BLUE);
        for (final Color color : colors) {
            if (field.stream().filter(x -> x.getType().equals(getTargetType(color)) && x.getGamePiece() != null).count() == 4) {
                return color;
            }
        }
        return null;
    }

    public Map<GamePieceInterface, Integer> calculateTurns(final Color color, final List<FieldInterface> field, final int jump) {

        final Map<GamePieceInterface, Integer> result = new HashMap<>();
        if (jump == 6 && field.stream()
                .filter(x -> x.getType().equals(getInitType(color)))
                .anyMatch(x -> x.getGamePiece() != null) && !field.stream()
                    .filter(x -> x.getType().equals(getStartType(color)))
                    .anyMatch(x -> x.getGamePiece() != null && x.getGamePiece().color().equals(color))) {

            final List<FieldInterface> initFields = field.stream()
                    .filter(x -> x.getType().equals(getInitType(color)) && x.getGamePiece() != null)
                    .collect(Collectors.toList());

            for (final FieldInterface initField : initFields) {

                result.put(initField.getGamePiece(), this.getStartPosition(color));

            }

            return result;
        }
        final FieldInterface startField = field.stream()
                .filter(x -> x.getType().equals(getStartType(color)))
                .findFirst().get();

        if (field.stream()
                .filter(x -> x.getType().equals(getInitType(color)))
                .anyMatch(x -> x.getGamePiece() != null)
                && startField.getGamePiece() != null
                && startField.getGamePiece().color().equals(color)
                && ((field.get(this.getTargetPosition(Graph.INSTANCE.four.get(field.indexOf(startField)), jump, color)).getGamePiece() != null
                && !field.get(this.getTargetPosition(Graph.INSTANCE.four.get(field.indexOf(startField)), jump, color)).getGamePiece().color().equals(color))
                || field.get(this.getTargetPosition(Graph.INSTANCE.four.get(field.indexOf(startField)), jump, color)).getGamePiece() == null)) {

            return Map.of(startField.getGamePiece(), this.getTargetPosition(Graph.INSTANCE.four.get(field.indexOf(startField)), jump, color));

        }

        final List<Node> currentPositions = this.getCurrentPosition(color, field).stream()
                .filter(x -> !x.getType().equals((Object)this.getInitType(color))).collect(Collectors.toList());

        for (final Node node : currentPositions) {

            final int target = this.getTargetPosition(node, jump, color);

            if (!currentPositions.stream().map(x -> Graph.INSTANCE.four.indexOf(x)).anyMatch(x -> x == target) && target != -1) {

                result.put(field.get(this.graph.four.indexOf(node)).getGamePiece(), target);

            }

        }

        return result;

    }

    public boolean isAllowedToRedice(final Color color, final List<FieldInterface> field) {

        final List<FieldInterface> initFields = field.stream().filter(x -> x.getType().equals(getInitType(color)) && x.getGamePiece() != null).collect(Collectors.toList());
        final List<FieldInterface> targetFields = field.stream().filter(x -> x.getType().equals(getTargetType(color)) && x.getGamePiece() != null).collect(Collectors.toList());

        boolean cleanedUp = true;
        for (final FieldInterface targetField : targetFields) {
            if (this.getTargetPosition(Graph.INSTANCE.four.get(field.indexOf(targetField)), 1, color) != -1) {
                cleanedUp = false;
            }
        }
        return cleanedUp && initFields.size() + targetFields.size() == 4;

    }

    private FieldType getInitType(final Color color) {
        if (color.equals(Color.RED)) {
            return FieldType.REDINIT;
        }
        if (color.equals(Color.BLUE)) {
            return FieldType.BLUEINIT;
        }
        if (color.equals(Color.GREEN)) {
            return FieldType.GREENINIT;
        }
        return FieldType.YELLOWINIT;
    }

    private FieldType getStartType(final Color color) {
        if (color.equals(Color.RED)) {
            return FieldType.REDSTART;
        }
        if (color.equals(Color.BLUE)) {
            return FieldType.BLUESTART;
        }
        if (color.equals(Color.GREEN)) {
            return FieldType.GREENSTART;
        }
        return FieldType.YELLOWSTART;
    }

    private FieldType getEndType(final Color color) {
        if (color.equals(Color.RED)) {
            return FieldType.REDEND;
        }
        if (color.equals(Color.BLUE)) {
            return FieldType.BLUEEND;
        }
        if (color.equals(Color.GREEN)) {
            return FieldType.GREENEND;
        }
        return FieldType.YELLOWEND;
    }

    private FieldType getTargetType(final Color color) {
        if (color.equals(Color.RED)) {
            return FieldType.REDTARGET;
        }
        if (color.equals(Color.BLUE)) {
            return FieldType.BLUETARGET;
        }
        if (color.equals(Color.GREEN)) {
            return FieldType.GREENTARGET;
        }
        return FieldType.YELLOWTARGET;
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
            if (currentNode.getType().equals((Object)this.getEndType(color))) {
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