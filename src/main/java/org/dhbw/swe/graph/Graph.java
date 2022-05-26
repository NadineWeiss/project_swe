package org.dhbw.swe.graph;

import java.util.Arrays;
import java.awt.Color;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public enum Graph
{
    INSTANCE;

    private Map<Integer, Direction> dirs;
    public List<Node> four;

    Graph() {
        this.dirs = Map.of(0, Direction.UP, 90, Direction.RIGHT, 180, Direction.DOWN, 270, Direction.LEFT);
        this.four = this.initFour();
    }

    private List<Node> initFour() {
        final List<Node> result = new ArrayList<Node>();
        result.addAll(this.getInit());
        result.addAll(this.getQuarter(Color.RED, 0));
        result.addAll(this.getQuarter(Color.YELLOW, 90));
        result.addAll(this.getQuarter(Color.GREEN, 180));
        result.addAll(this.getQuarter(Color.BLUE, 270));
        result.addAll(this.getTarget(Color.RED, 0));
        result.addAll(this.getTarget(Color.YELLOW, 90));
        result.addAll(this.getTarget(Color.GREEN, 180));
        result.addAll(this.getTarget(Color.BLUE, 270));
        this.addTargetToEdges(result);
        return result;
    }

    private List<Node> getQuarter(final Color color, final int rotation) {
        final List<Node> result = new ArrayList<Node>();
        final List<Edge> edges = new ArrayList<Edge>();
        edges.add(new Edge(this.rotateDir(Direction.RIGHT, rotation), true));
        for (int i = 0; i < 4; ++i) {
            edges.add(new Edge(this.rotateDir(Direction.DOWN, rotation), true));
        }
        for (int i = 0; i < 4; ++i) {
            edges.add(new Edge(this.rotateDir(Direction.RIGHT, rotation), true));
        }
        if (rotation == 270) {
            edges.add(new Edge(this.rotateDir(Direction.DOWN, rotation), true));
        }
        else {
            edges.add(new Edge(this.rotateDir(Direction.DOWN, rotation), true));
        }
        for (final Edge edge : edges) {
            result.add(new Node(FieldType.NEUTRAL, Arrays.asList(edge)));
        }
        result.get(0).setType(GraphUtilities.getEndType(color));
        result.get(1).setType(GraphUtilities.getStartType(color));
        result.get(0).addEdge(new Edge(this.rotateDir(Direction.DOWN, rotation), false));
        return result;
    }

    private List<Node> getTarget(final Color color, final int rotation) {
        final List<Node> result = new ArrayList<>();
        result.add(new Node(GraphUtilities.getTargetType(color), Arrays.asList(new Edge(this.rotateDir(Direction.DOWN, rotation), true))));
        result.add(new Node(GraphUtilities.getTargetType(color), Arrays.asList(new Edge(this.rotateDir(Direction.DOWN, rotation), true))));
        result.add(new Node(GraphUtilities.getTargetType(color), Arrays.asList(new Edge(this.rotateDir(Direction.DOWN, rotation), true))));
        result.add(new Node(GraphUtilities.getTargetType(color), new ArrayList<Edge>()));
        return result;
    }

    private List<Node> getInit() {

        final List<Node> result = new ArrayList<>();
        final List<Color> colors = Arrays.asList(Color.RED, Color.YELLOW, Color.GREEN, Color.BLUE);

        colors.stream().forEach(color -> {
            for (int i = 0; i < 4; ++i) {
                result.add(new Node(GraphUtilities.getInitType(color), new ArrayList<>()));
            }
        });

        return result;

    }

    private void addTargetToEdges(final List<Node> result) {
        final List<Integer> indicesToIgnore = Arrays.asList(55, 59, 63, 67, 71);
        for (final Node node : result) {
            final int index = result.indexOf(node);
            if (!indicesToIgnore.contains(index) && index >= 16) {
                node.getDefaultEdge().setTarget(result.get(index + 1));
            }
        }
        result.get(55).getDefaultEdge().setTarget(result.get(16));
        result.get(16).getSpecialEdge().setTarget(result.get(56));
        result.get(26).getSpecialEdge().setTarget(result.get(60));
        result.get(36).getSpecialEdge().setTarget(result.get(64));
        result.get(46).getSpecialEdge().setTarget(result.get(68));
    }

    private Direction rotateDir(final Direction direction, final int rotation) {
        int degree = direction.getDegree() + rotation;
        if (degree >= 360) {
            degree -= 360;
        }
        return this.dirs.get(degree);
    }

}