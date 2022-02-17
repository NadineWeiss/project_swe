package org.dhbw.swe.board;

public abstract class AbstractControlMechanism implements ControlMechanismInterface
{
    protected Graph graph;

    public AbstractControlMechanism() {
        this.graph = Graph.INSTANCE;
    }
}
