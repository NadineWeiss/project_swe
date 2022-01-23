package org.dhbw.swe.board;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Objects;

public class Node {

    private FieldType type;
    private List<Edge> edges;

    public Node(FieldType type, List<Edge> edges) {
        this.type = type;
        this.edges = edges;
    }

    public void addEdge(Edge edge){

        if(edges == null) {
            edges = Arrays.asList(edge);
        }else{
            List<Edge> list = new ArrayList<>();
            list.addAll(edges);
            list.add(edge);
            edges = list;
        }

    }

    public FieldType getType() {
        return type;
    }

    public void setType(FieldType type) {
        this.type = type;
    }

    public List<Edge> getEdges() {
        return edges;
    }

    public void setEdges(List<Edge> edges) {
        this.edges = edges;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Node node = (Node) o;
        return type == node.type && Objects.equals(edges, node.edges);
    }

    @Override
    public int hashCode() {
        return Objects.hash(type, edges);
    }

    @Override
    public String toString() {
        return "Node{" +
                "type=" + type +
                ", edges=" + edges +
                '}';
    }
}
