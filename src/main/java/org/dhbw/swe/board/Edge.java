package org.dhbw.swe.board;

import java.util.Objects;

public class Edge {

    private int node;
    private Direction direction;

    public Edge(int node, Direction direction) {
        this.node = node;
        this.direction = direction;
    }

    public int getNode() {
        return node;
    }

    public void setNode(int node) {
        this.node = node;
    }

    public Direction getDirection() {
        return direction;
    }

    public void setDirection(Direction direction) {
        this.direction = direction;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Edge edge = (Edge) o;
        return node == edge.node && direction == edge.direction;
    }

    @Override
    public int hashCode() {
        return Objects.hash(node, direction);
    }

    @Override
    public String toString() {
        return "Edge{" +
                "node=" + node +
                ", direction=" + direction +
                '}';
    }
}
