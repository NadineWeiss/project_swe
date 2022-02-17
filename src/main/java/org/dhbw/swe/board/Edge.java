package org.dhbw.swe.board;

import java.util.Objects;

public class Edge
{
    private Node target;
    private Direction direction;
    private boolean defaultDir;

    public Edge(final Node target, final Direction direction, final boolean defaultDir) {
        this.target = target;
        this.direction = direction;
        this.defaultDir = defaultDir;
    }

    public Edge(final Direction direction, final boolean defaultDir) {
        this.direction = direction;
        this.defaultDir = defaultDir;
    }

    public Node getTarget() {
        return this.target;
    }

    public void setTarget(final Node target) {
        this.target = target;
    }

    public Direction getDirection() {
        return this.direction;
    }

    public void setDirection(final Direction direction) {
        this.direction = direction;
    }

    public boolean isDefaultDir() {
        return this.defaultDir;
    }

    public void setDefaultDir(final boolean defaultDir) {
        this.defaultDir = defaultDir;
    }

    @Override
    public boolean equals(final Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || this.getClass() != o.getClass()) {
            return false;
        }
        final Edge edge = (Edge)o;
        return this.defaultDir == edge.defaultDir && Objects.equals(this.target, edge.target) && this.direction == edge.direction;
    }

    @Override
    public int hashCode() {
        return Objects.hash(this.target, this.direction, this.defaultDir);
    }

    @Override
    public String toString() {
        return "Edge{" +
                "node=" + target +
                ", direction=" + direction +
                ", defaultDir=" + defaultDir +
                '}';
    }
}
