package org.dhbw.swe.board;

import org.dhbw.swe.graph.FieldType;

import java.awt.*;
import java.util.Map;
import java.util.Optional;

public abstract class AbstractBoard implements BoardInterface {

    protected ControlMechanismInterface controlMechanismInterface;

    public void makeMove(final int from, final int to) {

        if(from == to){
            return;
        }

        if (getBoard().get(to).getGamePiece() != null) {

            final GamePieceInterface gamePiece = getBoard().get(to).getGamePiece();
            final FieldInterface field = getBoard().stream()
                    .filter(x -> x.getType().equals(FieldType.getInitType(gamePiece.color())) && x.getGamePiece() == null)
                    .findFirst()
                    .get();

            field.setGamePiece(gamePiece);
            getBoard().get(to).setGamePiece(null);

        }

        getBoard().get(to).setGamePiece(getBoard().get(from).getGamePiece());
        getBoard().get(from).setGamePiece(null);

    }

    public boolean isAllowedToRedice(Color color){

        return controlMechanismInterface.isAllowedToRedice(color, getBoard());
    }

    public Map<GamePieceInterface, Integer> calculateMoves(Color color, int dice){

        return controlMechanismInterface.calculateMoves(color, getBoard(), dice);

    }

    public Color checkWin(){

        return controlMechanismInterface.checkWin(getBoard());

    }

    public Optional<Integer> calculateMove(int fieldIndex, int dice){

        return controlMechanismInterface.calculateMove(fieldIndex, getBoard(), dice);

    }

    public boolean isMovePossible(Color color, int dice){

        return controlMechanismInterface.isMovePossible(color, getBoard(), dice);

    }

    public void calculateAlgorithmMove(final Color color, int dice){

        controlMechanismInterface.calculateAlgorithmMove(color, getBoard(), dice);

    }

    public int getAlgorithmMoveFrom(){


        return controlMechanismInterface.getAlgorithmMoveFrom();
    }

    public int getAlgorithmMoveTo(){

        return controlMechanismInterface.getAlgorithmMoveTo();

    }

}