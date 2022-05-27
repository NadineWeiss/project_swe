package org.dhbw.swe.board;

import java.util.List;
import java.util.Map;
import java.awt.Color;
import java.util.Random;
import java.util.stream.Collectors;

public class Algorithm {

    private int moveFrom;
    private int moveTo;

    public void calculateMove(Color color, Map<GamePieceInterface, Integer> possibleMoves, List<FieldInterface> field) {

        Map<GamePieceInterface, Integer> gamePieces = possibleMoves.entrySet().stream()
                .filter(x -> x.getKey().getColor().equals(color))
                .collect(Collectors.toMap(x -> x.getKey(), x -> x.getValue()));

        int randomIndex = new Random().nextInt(gamePieces.size());

        GamePieceInterface originalGamePiece = (GamePieceInterface) gamePieces.keySet().toArray()[randomIndex];
        for(FieldInterface fieldInterface : field){

            if(fieldInterface.containsGamePiece() && fieldInterface.getGamePiece().equals(originalGamePiece))
                moveFrom = field.indexOf(fieldInterface);

        }

        moveTo = (int) gamePieces.values().toArray()[randomIndex];

    }

    public int getMoveFrom() {
        return moveFrom;
    }

    public int getMoveTo() {
        return moveTo;
    }

}
