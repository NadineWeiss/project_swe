package org.dhbw.swe.board;

import org.dhbw.swe.graph.FieldType;
import org.dhbw.swe.graph.Graph;
import org.dhbw.swe.graph.Node;

import java.awt.*;
import java.util.List;
import java.util.*;
import java.util.stream.Collectors;

public class ControlMechanismFour extends AbstractControlMechanism {

    public Color checkWin(final List<FieldInterface> board) {
        final List<Color> colors = Arrays.asList(Color.RED, Color.YELLOW, Color.GREEN, Color.BLUE);
        for (final Color color : colors) {
            if (board.stream().filter(x -> x.getType().equals(FieldType.getTargetType(color)) && x.getGamePiece() != null).count() == 4) {
                return color;
            }
        }
        return null;
    }

    public Optional<Integer> calculateMove(int fieldIndex, final List<FieldInterface> board, int dice) {

        GamePieceInterface currentGamePiece = board.get(fieldIndex).getGamePiece();
        Map<GamePieceInterface, Integer> possibleMoves = calculateMoves(currentGamePiece.color(), board, dice);

        for(Map.Entry<GamePieceInterface, Integer> move : possibleMoves.entrySet()){

            if(move.getKey().equals(currentGamePiece))
                return Optional.of(move.getValue());

        }

        return Optional.empty();

    }

    public boolean isMovePossible(Color color, List<FieldInterface> board, int dice){

        return !calculateMoves(color, board, dice).isEmpty();

    }

    public Map<GamePieceInterface, Integer> calculateMoves(Color color, List<FieldInterface> board, int dice) {

        if (needsToMoveFromInitFieldToStartField(color, board, dice)) {
            return calculateMovesFromInitFields(color, board);
        }

        if (needsToMoveFromStartField(color, board, dice)) {
            return calculateMovesFromStartField(color, board, dice);
        }

        return calculateMovesIgnoringStartField(color, board, dice);

    }

    public boolean isAllowedToRedice(Color color, List<FieldInterface> board) {

        final List<FieldInterface> initFields = board.stream().filter(x -> x.getType().equals(FieldType.getInitType(color)) && x.getGamePiece() != null).collect(Collectors.toList());
        final List<FieldInterface> targetFields = board.stream().filter(x -> x.getType().equals(FieldType.getTargetType(color)) && x.getGamePiece() != null).collect(Collectors.toList());

        boolean cleanedUp = true;
        for (final FieldInterface targetField : targetFields) {
            if (this.getTargetPosition(Graph.INSTANCE.four.get(board.indexOf(targetField)), 1, color) != -1) {
                cleanedUp = false;
            }
        }
        return cleanedUp && initFields.size() + targetFields.size() == 4;

    }

    private Map<GamePieceInterface, Integer> calculateMovesFromInitFields(Color color, List<FieldInterface> board){

        final Map<GamePieceInterface, Integer> result = new HashMap<>();

        final List<FieldInterface> initFields = board.stream()
                .filter(x -> x.getType().equals(FieldType.getInitType(color)) && x.getGamePiece() != null)
                .collect(Collectors.toList());

        for (final FieldInterface initField : initFields) {
            result.put(initField.getGamePiece(), this.getStartPosition(color));
        }

        return result;

    }

    private Map<GamePieceInterface, Integer> calculateMovesFromStartField(Color color, List<FieldInterface> board, int dice){

        FieldInterface startField = getStartField(color, board);
        int targetPosition = getTargetPosition(Graph.INSTANCE.four.get(board.indexOf(startField)), dice, color);
        return Map.of(startField.getGamePiece(), targetPosition);

    }

    private Map<GamePieceInterface, Integer> calculateMovesIgnoringStartField(Color color, List<FieldInterface> board, int dice){

        final Map<GamePieceInterface, Integer> result = new HashMap<>();

        final List<Node> currentPositions = this.getCurrentPosition(color, board).stream()
                .filter(x -> !x.getType().equals(FieldType.getInitType(color)))
                .collect(Collectors.toList());

        for (final Node node : currentPositions) {

            final int target = this.getTargetPosition(node, dice, color);
            if (currentPositions.stream().map(x -> Graph.INSTANCE.four.indexOf(x)).noneMatch(x -> x == target)
                    && target != -1) {

                result.put(board.get(this.graph.four.indexOf(node)).getGamePiece(), target);

            }

        }

        return result;

    }

    private FieldInterface getStartField(Color color, List<FieldInterface> board){

        return board.stream()
                .filter(x -> x.getType().equals(FieldType.getStartType(color)))
                .findFirst().get();

    }

    private boolean isMoveFromStartFieldPossible(Color color, List<FieldInterface> board, int dice) {

        FieldInterface startField = getStartField(color, board);
        int targetPosition = getTargetPosition(Graph.INSTANCE.four.get(board.indexOf(startField)), dice, color);
        FieldInterface targetField = board.get(targetPosition);

        boolean targetFieldNotOccupied = targetField.getGamePiece() != null
                && !targetField.getGamePiece().color().equals(color);
        boolean targetFieldEmpty = targetField.getGamePiece() == null;

        return targetFieldNotOccupied || targetFieldEmpty;

    }

    private boolean needsToMoveFromStartField(Color color, List<FieldInterface> board, int dice){

        return anyGamePieceAtInitFields(color, board) && isOwnGamePieceAtStartField(color, board)
                && isMoveFromStartFieldPossible(color, board, dice);

    }

    private boolean needsToMoveFromInitFieldToStartField(Color color, List<FieldInterface> board, int dice){

        return dice == 6 && anyGamePieceAtInitFields(color, board) && !isOwnGamePieceAtStartField(color, board);

    }

    private boolean anyGamePieceAtInitFields(Color color, List<FieldInterface> board){

        return board.stream()
                .filter(x -> x.getType().equals(FieldType.getInitType(color)))
                .anyMatch(x -> x.getGamePiece() != null);

    }

    private boolean isOwnGamePieceAtStartField(Color color, List<FieldInterface> board){

        return board.stream()
                .filter(x -> x.getType().equals(FieldType.getStartType(color)))
                .anyMatch(x -> x.getGamePiece() != null && x.getGamePiece().color().equals(color));

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

    private List<Node> getCurrentPosition(final Color color, final List<FieldInterface> board) {

        return board.stream()
                .filter(x -> x.getGamePiece() != null)
                .filter(x -> x.getGamePiece().color().equals(color))
                .map(x -> board.indexOf(x))
                .map(x -> Graph.INSTANCE.four.get(x))
                .collect(Collectors.toList());

    }

    private int getTargetPosition(Node node, int jump, Color color) {

        Node currentNode = node;
        for (int i = 0; i < jump; ++i) {
            if (currentNode.getType().equals(FieldType.getEndType(color))) {
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