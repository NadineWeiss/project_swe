package org.dhbw.swe.board;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

public class Node {

    private FieldType type;
    private List<Edge> edges;

    public Node(final FieldType type, final List<Edge> edges) {
        this.type = type;
        this.edges = edges;
    }

    public void addEdge(final Edge edge) {
        if (this.edges == null) {
            this.edges = Arrays.asList(edge);
        }
        else {
            final List<Edge> list = new ArrayList<Edge>();
            list.addAll(this.edges);
            list.add(edge);
            this.edges = list;
        }
    }

    public FieldType getType() {
        return this.type;
    }

    public void setType(final FieldType type) {
        this.type = type;
    }

    public List<Edge> getEdges() {
        return this.edges;
    }

    public void setEdges(final List<Edge> edges) {
        this.edges = edges;
    }

    public Edge getDefaultEdge() {
        return this.edges.stream().filter(x -> x.isDefaultDir()).findFirst().get();
    }

    public Edge getSpecialEdge() {
        return this.edges.stream().filter(x -> !x.isDefaultDir()).findFirst().get();
    }

    @Override
    public String toString() {
        return "Node{" +
                "type=" + type +
                ", edges=" + edges +
                '}';
    }
}
