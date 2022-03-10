package org.dhbw.swe.graph;

public enum Direction {

    UP(0), RIGHT(90), DOWN(180), LEFT(270);

    private int degree;

    Direction(int degree) {

        this.degree = degree;

    }

    public int getDegree() {

        return degree;

    }
}
