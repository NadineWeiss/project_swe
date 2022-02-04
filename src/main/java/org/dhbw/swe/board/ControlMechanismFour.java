package org.dhbw.swe.board;

import org.dhbw.swe.game.Player;

import java.awt.*;
import java.util.List;
import java.util.Map;
import java.util.Random;
import java.util.stream.Collectors;

public class ControlMechanismFour extends AbstractControlMechanism {

    public int dice(){

        return new Random().nextInt(6) + 1;

    }

    public Map<GamePieceInterface, Integer> calculateTurns(Player player, List<FieldInterface> field, int jump){

        if (jump == 6 && field.stream()
                .filter(x -> x.type().equals(getInitType(player.getColor())))
                .anyMatch(x -> x.gamePiece() != null)){

            return Map.of(field.stream()
                    .filter(x -> x.type().equals(getInitType(player.getColor())) && x.gamePiece() != null)
                    .findFirst().get().gamePiece(), getStartPosition(player.getColor()));

        }

        for (Node node : getCurrentPosition(player.getColor(), field)){

        }

        return null;
    }

    private FieldType getInitType(Color color){

        if(color.equals(Color.RED))
            return FieldType.REDINIT;
        if(color.equals(Color.BLUE))
            return FieldType.BLUEINIT;
        if(color.equals(Color.GREEN))
            return FieldType.GREENINIT;
        return FieldType.YELLOWINIT;

    }

    private FieldType getStartType(Color color){

        if(color.equals(Color.RED))
            return FieldType.REDSTART;
        if(color.equals(Color.BLUE))
            return FieldType.BLUESTART;
        if(color.equals(Color.GREEN))
            return FieldType.GREENSTART;
        return FieldType.YELLOWSTART;

    }

    private FieldType getEndType(Color color){

        if(color.equals(Color.RED))
            return FieldType.REDEND;
        if(color.equals(Color.BLUE))
            return FieldType.BLUEEND;
        if(color.equals(Color.GREEN))
            return FieldType.GREENEND;
        return FieldType.YELLOWEND;

    }

    private FieldType getTargetType(Color color){

        if(color.equals(Color.RED))
            return FieldType.REDTARGET;
        if(color.equals(Color.BLUE))
            return FieldType.BLUETARGET;
        if(color.equals(Color.GREEN))
            return FieldType.GREENTARGET;
        return FieldType.YELLOWTARGET;

    }

    private int getStartPosition(Color color){

        if(color.equals(Color.RED))
            return 17;
        if(color.equals(Color.BLUE))
            return 47;
        if(color.equals(Color.GREEN))
            return 37;

        return 27;

    }

    private List<Node> getCurrentPosition(Color color, List<FieldInterface> field){

        return field.stream().filter(x -> x.gamePiece() != null)
                .filter(x -> x.gamePiece().color().equals(color))
                .map(x -> field.indexOf(x.gamePiece()))
                .map(x -> graph.four.get(x))
                .collect(Collectors.toList());

    }

    private int getTargetPosition(Node node, int jump, Color color){

        Node currentNode = node;

        for (int i = 0; i < jump; i++){

            if (currentNode.getType().equals(getEndType(color))){

            } else {

                currentNode = graph.four.get(currentNode.getEdges().get(0).getNode());

            }

        }

        return jump;
    }

}
