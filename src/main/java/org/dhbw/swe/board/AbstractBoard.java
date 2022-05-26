package org.dhbw.swe.board;

import org.dhbw.swe.graph.GraphUtilities;

import java.awt.*;
import java.util.Map;
import java.util.Optional;

public abstract class AbstractBoard implements BoardInterface {

    protected ControlMechanismInterface controlMechanismInterface;

    public void makeMove(final int from, final int to) {

        if(from == to){
            return;
        }

        if (getField().get(to).getGamePiece() != null) {

            final GamePieceInterface gamePiece = getField().get(to).getGamePiece();
            final FieldInterface field = getField().stream()
                    .filter(x -> x.getType().equals(GraphUtilities.getInitType(gamePiece.color())) && x.getGamePiece() == null)
                    .findFirst()
                    .get();

            field.setGamePiece(gamePiece);
            getField().get(to).setGamePiece(null);

        }

        getField().get(to).setGamePiece(getField().get(from).getGamePiece());
        getField().get(from).setGamePiece(null);

    }

    public boolean isAllowedToRedice(Color color){

        return controlMechanismInterface.isAllowedToRedice(color, getField());
    }

    public Map<GamePieceInterface, Integer> calculateTurns(Color color, int dice){

        return controlMechanismInterface.calculateTurns(color, getField(), dice);

    }

    public Color checkWin(){

        return controlMechanismInterface.checkWin(getField());

    }

    public Optional<Integer> calculateTurn(int fieldIndex, int dice){

        return controlMechanismInterface.calculateTurn(fieldIndex, getField(), dice);

    }

    public boolean isTurnPossible(Color color, int dice){

        return controlMechanismInterface.isTurnPossible(color, getField(), dice);

    }

    public void calculateAlgorithmMove(final Color color, int dice){

        controlMechanismInterface.calculateAlgorithmMove(color, getField(), dice);

    }

    public int getAlgorithmMoveFrom(){


        return controlMechanismInterface.getAlgorithmMoveFrom();
    }

    public int getAlgorithmMoveTo(){

        return controlMechanismInterface.getAlgorithmMoveTo();

    }

}