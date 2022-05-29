package org.dhbw.swe.board;

import org.dhbw.swe.graph.Graph;

import java.awt.*;
import java.util.List;

public abstract class AbstractControlMechanism implements ControlMechanismInterface
{
    protected Graph graph;
    protected Algorithm algorithm;

    public AbstractControlMechanism() {

        this.graph = Graph.INSTANCE;
        algorithm = new Algorithm();

    }

    public void calculateAlgorithmMove(final Color color, final List<FieldInterface> board, int dice){

        algorithm.calculateMove(color, calculateMoves(color, board, dice), board);

    }

    public int getAlgorithmMoveFrom(){

        return algorithm.getMoveFrom();

    }

    public int getAlgorithmMoveTo(){

        return algorithm.getMoveTo();

    }

}
